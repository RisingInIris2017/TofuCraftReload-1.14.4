package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class TofuFallingBlockEntity extends FallingBlockEntity {
    private BlockState fallTile = Blocks.SAND.getDefaultState();

    public TofuFallingBlockEntity(EntityType<? extends TofuFallingBlockEntity> p_i50218_1_, World p_i50218_2_) {
        super(p_i50218_1_, p_i50218_2_);
    }

    public TofuFallingBlockEntity(World world, double posX, double posY, double posZ, BlockState defaultState) {
        super(world, posX, posY, posZ, defaultState);
        this.fallTile = defaultState;
    }


    public TofuFallingBlockEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(TofuEntitys.TOFU_FALLING_BLOCK, world);
    }

    public void tick() {
        if (this.fallTile.isAir()) {
            this.remove();
        } else {
            this.prevPosX = this.getPosX();
            this.prevPosY = this.getPosY();
            this.prevPosZ = this.getPosZ();
            Block block = this.fallTile.getBlock();
            if (this.fallTime++ == 0) {
                BlockPos blockpos = new BlockPos(this.getPositionVec());
                if (this.world.getBlockState(blockpos).getBlock() == block) {
                    this.world.removeBlock(blockpos, false);
                }
            }

            if (!this.hasNoGravity()) {
                this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
            }

            this.move(MoverType.SELF, this.getMotion());
            if (!this.world.isRemote) {
                BlockPos blockpos1 = new BlockPos(this.getPositionVec());
                boolean flag = this.fallTile.getBlock() instanceof ConcretePowderBlock;
                boolean flag1 = flag && this.world.getFluidState(blockpos1).isTagged(FluidTags.WATER);
                double d0 = this.getMotion().lengthSquared();
                if (flag && d0 > 1.0D) {
                    BlockRayTraceResult blockraytraceresult = this.world.rayTraceBlocks(new RayTraceContext(new Vector3d(this.prevPosX, this.prevPosY, this.prevPosZ), new Vector3d(this.getPosX(), this.getPosY(), this.getPosZ()), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.SOURCE_ONLY, this));
                    if (blockraytraceresult.getType() != RayTraceResult.Type.MISS && this.world.getFluidState(blockraytraceresult.getPos()).isTagged(FluidTags.WATER)) {
                        blockpos1 = blockraytraceresult.getPos();
                        flag1 = true;
                    }
                }

                if (!this.onGround && !flag1) {
                    if (!this.world.isRemote && (this.fallTime > 100 && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.fallTime > 600)) {
                        if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            this.entityDropItem(block);
                        }

                        this.remove();
                    }
                } else {
                    BlockState blockstate = this.world.getBlockState(blockpos1);
                    this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
                    if (blockstate.getBlock() != Blocks.MOVING_PISTON) {
                        this.remove();
                        boolean flag2 = blockstate.isReplaceable(new DirectionalPlaceContext(this.world, blockpos1, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                        boolean flag3 = this.fallTile.isValidPosition(this.world, blockpos1);
                        if (flag2 && flag3) {
                            if (this.fallTile.func_235901_b_(BlockStateProperties.WATERLOGGED) && this.world.getFluidState(blockpos1).getFluid() == Fluids.WATER) {
                                this.fallTile = this.fallTile.with(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
                            }

                            if (this.world.setBlockState(blockpos1, this.fallTile, 3)) {
                                if (block instanceof FallingBlock) {
                                    ((FallingBlock) block).onEndFalling(this.world, blockpos1, this.fallTile, blockstate, this);
                                }

                                if (this.tileEntityData != null && this.fallTile.hasTileEntity()) {
                                    TileEntity tileentity = this.world.getTileEntity(blockpos1);
                                    if (tileentity != null) {
                                        CompoundNBT compoundnbt = tileentity.write(new CompoundNBT());

                                        for (String s : this.tileEntityData.keySet()) {
                                            INBT inbt = this.tileEntityData.get(s);
                                            if (!"x".equals(s) && !"y".equals(s) && !"z".equals(s)) {
                                                compoundnbt.put(s, inbt.copy());
                                            }
                                        }

                                        tileentity.func_230337_a_(this.fallTile, compoundnbt);
                                        tileentity.markDirty();
                                    }
                                }
                            } else if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                this.entityDropItem(block);
                            }
                        } else if (block instanceof FallingBlock) {
                            ((FallingBlock) block).onBroken(this.world, blockpos1, this);
                        }
                    }
                }
            }

            this.setMotion(this.getMotion().scale(0.98D));
        }
    }

    public BlockState getBlockState() {
        return this.fallTile;
    }
}
