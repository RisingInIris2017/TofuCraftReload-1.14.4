package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.init.TofuItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class SaltCauldronBlock extends CauldronBlock {
    public static final EnumProperty<Stat> STAT = EnumProperty.create("stat", Stat.class);

    public SaltCauldronBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(STAT, Stat.SALT).with(LEVEL, Integer.valueOf(0)));
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);

        int i = state.get(LEVEL);
        Stat stat = state.get(STAT);
        Item item = itemstack.getItem();
        if (stat == Stat.BITTERN) {
            if (item == Items.GLASS_BOTTLE) {
                if (i > 0 && !worldIn.isRemote) {
                    if (!player.abilities.isCreativeMode) {
                        ItemStack itemstack4 = new ItemStack(TofuItems.BITTERN);
                        player.addStat(Stats.USE_CAULDRON);
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.setHeldItem(handIn, itemstack4);
                        } else if (!player.inventory.addItemStackToInventory(itemstack4)) {
                            player.dropItem(itemstack4, false);
                        } else if (player instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity) player).sendContainerToPlayer(player.container);
                        }
                    }

                    worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    if (i == 1) {
                        this.setWaterLevel(worldIn, pos, Blocks.CAULDRON.getDefaultState(), 0);
                    } else {
                        this.setWaterLevel(worldIn, pos, state, i - 1);
                    }


                }

                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.PASS;

            }
        } else {
            ItemStack salt = new ItemStack(TofuItems.SALT, 4 + worldIn.rand.nextInt(5));

            float f = 0.7F;
            double d0 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.2D + 0.6D;
            double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            ItemEntity itemEntity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, salt);
            itemEntity.setPickupDelay(10);
            worldIn.addEntity(itemEntity);

            worldIn.setBlockState(pos, state.with(STAT, Stat.BITTERN), 3);

            return ActionResultType.SUCCESS;
        }
    }

    public void fillWithRain(World worldIn, BlockPos pos) {
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STAT, LEVEL);
    }

    public static enum Stat implements IStringSerializable {
        SALT(0, "salt"),
        BITTERN(1, "bittern");

        private static final SaltCauldronBlock.Stat[] META_LOOKUP = new SaltCauldronBlock.Stat[values().length];
        private int meta;
        private String name;

        Stat(int meta, String name) {
            this.meta = meta;
            this.name = name;
        }

        public static SaltCauldronBlock.Stat byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }
            return META_LOOKUP[meta];
        }

        public int getMeta() {
            return this.meta;
        }

        @Override
        public String func_176610_l() {
            return this.name;
        }


        static {
            for (SaltCauldronBlock.Stat enumtype : values()) {
                META_LOOKUP[enumtype.getMeta()] = enumtype;
            }
        }

    }
}
