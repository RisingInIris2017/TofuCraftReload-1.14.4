package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.init.TofuItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class WheatBowlBlock extends Block {
    public static VoxelShape WHEATBOWL_AABB = Block.makeCuboidShape(1.0f, 0.0f, 1.0f, 15.0f, 3F, 15.0f);

    public static final EnumProperty<Stat> STAT = EnumProperty.create("stat", Stat.class);
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 4);

    public WheatBowlBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(STAT, Stat.EMPTY).with(LEVEL, 0));
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.PASS;
        } else {
            ItemStack itemHeld = player.getHeldItem(handIn);
            Stat stat = this.getStat(state);
            if (stat == Stat.KOUJI) {
                if (state.get(LEVEL) > 0) {
                    ItemStack kouji = new ItemStack(TofuItems.KOUJI, state.get(LEVEL));

                    float f = 0.7F;
                    double d0 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                    double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.2D + 0.6D;
                    double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                    ItemEntity itemEntity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, kouji);
                    itemEntity.setPickupDelay(10);
                    worldIn.addEntity(itemEntity);

                    worldIn.playSound(null, pos, SoundEvents.BLOCK_COMPOSTER_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                worldIn.setBlockState(pos, state.with(STAT, Stat.EMPTY).with(LEVEL, 0), 3);
            } else if (itemHeld.getItem() == TofuItems.KOUJIBASE) {
                if (stat == Stat.EMPTY) {
                    if (!player.isCreative()) {
                        itemHeld.shrink(1);
                    }
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_COMPOSTER_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    worldIn.setBlockState(pos, state.with(STAT, Stat.KOUJIBASE).with(LEVEL, 1), 3);
                } else if (stat == Stat.KOUJIBASE && state.get(LEVEL) < 4) {
                    if (!player.isCreative()) {
                        itemHeld.shrink(1);
                    }
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_COMPOSTER_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    worldIn.setBlockState(pos, state.with(LEVEL, state.get(LEVEL) + 1), 3);
                }
            }


            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {

        Stat stat = getStat(state);

        if (stat == Stat.KOUJIBASE) {
            float f = this.calcWarmth(worldIn, pos);

            if (f > 0.0F && rand.nextInt((int) (27.0F / f) + 1) == 0) {
                worldIn.setBlockState(pos, state.with(STAT, Stat.KOUJI), 2);
            }
        }
    }

    //Change the ease of fermentation by warmth
    private float calcWarmth(World world, BlockPos pos) {
        Biome biome = world.getBiome(pos);
        boolean isUnderTheSun = world.canBlockSeeSky(pos);
        boolean isRaining = world.isRaining();
        boolean isDaytime = world.getDayTime() % 24000 < 12000;
        float humidity = biome.getDownfall();
        float temperature = biome.getTemperature(pos);
        float rate;

        if (isUnderTheSun && isRaining) {
            rate = 0.0F;
        } else {
            if (isDaytime && isUnderTheSun) {
                rate = 1.5F;
            } else {
                rate = world.getLight(pos) > 9 ? 0.5F + (0.05F * (world.getLight(pos) - 8)) : 0.0F;
            }

            rate *= humidity < 0.2D ? 3.0D : humidity < 0.7D ? 1.5D : humidity < 0.9 ? 0.5D : 0.25D;
            rate *= temperature < 0.0D ? 0.0D : temperature < 0.6D ? 0.5D : temperature < 1.0D ? 1.0D : 2.5D;
        }
        return rate;
    }

    public Stat getStat(BlockState meta) {

        if (meta.getBlock() == this) {
            return meta.get(STAT);
        } else {
            return Stat.NA;
        }
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 100;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STAT, LEVEL);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return WHEATBOWL_AABB;
    }

    public static enum Stat implements IStringSerializable {
        EMPTY(0, "empty"),
        KOUJIBASE(1, "koujibase"),
        KOUJI(2, "kouji"),
        NA(3, "na");

        private static final Stat[] META_LOOKUP = new Stat[values().length];
        private int meta;
        private String name;

        Stat(int meta, String name) {
            this.meta = meta;
            this.name = name;
        }

        public static Stat byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }
            return META_LOOKUP[meta];
        }

        public int getMeta() {
            return this.meta;
        }

        static {
            for (Stat enumtype : values()) {
                META_LOOKUP[enumtype.getMeta()] = enumtype;
            }
        }

        @Override
        public String func_176610_l() {
            return this.name;
        }
    }
}
