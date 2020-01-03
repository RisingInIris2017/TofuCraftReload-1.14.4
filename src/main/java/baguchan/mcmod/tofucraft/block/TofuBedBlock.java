package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.tileentity.TofuBedTileEntity;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class TofuBedBlock extends BedBlock {
    public TofuBedBlock(Properties properties) {
        super(DyeColor.WHITE, properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(PART, BedPart.FOOT).with(OCCUPIED, Boolean.valueOf(false)));
    }


    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return true;
        } else {
            if (state.get(PART) != BedPart.HEAD) {
                pos = pos.offset(state.get(HORIZONTAL_FACING));
                state = worldIn.getBlockState(pos);
                if (state.getBlock() != this) {
                    return true;
                }
            }

            net.minecraftforge.common.extensions.IForgeDimension.SleepResult sleepResult = worldIn.dimension.canSleepAt(player, pos);
            if (sleepResult != net.minecraftforge.common.extensions.IForgeDimension.SleepResult.BED_EXPLODES) {
                if (sleepResult == net.minecraftforge.common.extensions.IForgeDimension.SleepResult.DENY) return true;
                if (state.get(OCCUPIED)) {
                    player.sendStatusMessage(new TranslationTextComponent("block.minecraft.bed.occupied"), true);
                    return true;
                } else {
                    player.trySleep(pos).ifLeft((p_220173_1_) -> {
                        if (p_220173_1_ != null) {
                            player.sendStatusMessage(p_220173_1_.getMessage(), true);
                        }

                    });
                    return true;
                }
            } else {
                worldIn.removeBlock(pos, false);
                BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING).getOpposite());
                if (worldIn.getBlockState(blockpos).getBlock() == this) {
                    worldIn.removeBlock(blockpos, false);
                }

                worldIn.createExplosion((Entity) null, DamageSource.netherBedExplosion(), (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 5.0F, true, Explosion.Mode.DESTROY);
                return true;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasCustomBreakingProgress(BlockState state) {
        return true;
    }

    public static Optional<Vec3d> func_220172_a(EntityType<?> p_220172_0_, IWorldReader p_220172_1_, BlockPos p_220172_2_, int p_220172_3_) {
        Direction direction = p_220172_1_.getBlockState(p_220172_2_).get(HORIZONTAL_FACING);
        int i = p_220172_2_.getX();
        int j = p_220172_2_.getY();
        int k = p_220172_2_.getZ();

        for (int l = 0; l <= 1; ++l) {
            int i1 = i - direction.getXOffset() * l - 1;
            int j1 = k - direction.getZOffset() * l - 1;
            int k1 = i1 + 2;
            int l1 = j1 + 2;

            for (int i2 = i1; i2 <= k1; ++i2) {
                for (int j2 = j1; j2 <= l1; ++j2) {
                    BlockPos blockpos = new BlockPos(i2, j, j2);
                    Optional<Vec3d> optional = func_220175_a(p_220172_0_, p_220172_1_, blockpos);
                    if (optional.isPresent()) {
                        if (p_220172_3_ <= 0) {
                            return optional;
                        }

                        --p_220172_3_;
                    }
                }
            }
        }

        return Optional.empty();
    }

    protected static Optional<Vec3d> func_220175_a(EntityType<?> p_220175_0_, IWorldReader p_220175_1_, BlockPos p_220175_2_) {
        VoxelShape voxelshape = p_220175_1_.getBlockState(p_220175_2_).getCollisionShape(p_220175_1_, p_220175_2_);
        if (voxelshape.getEnd(Direction.Axis.Y) > 0.4375D) {
            return Optional.empty();
        } else {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_220175_2_);

            while (blockpos$mutableblockpos.getY() >= 0 && p_220175_2_.getY() - blockpos$mutableblockpos.getY() <= 2 && p_220175_1_.getBlockState(blockpos$mutableblockpos).getCollisionShape(p_220175_1_, blockpos$mutableblockpos).isEmpty()) {
                blockpos$mutableblockpos.move(Direction.DOWN);
            }

            VoxelShape voxelshape1 = p_220175_1_.getBlockState(blockpos$mutableblockpos).getCollisionShape(p_220175_1_, blockpos$mutableblockpos);
            if (voxelshape1.isEmpty()) {
                return Optional.empty();
            } else {
                double d0 = (double) blockpos$mutableblockpos.getY() + voxelshape1.getEnd(Direction.Axis.Y) + 2.0E-7D;
                if ((double) p_220175_2_.getY() - d0 > 2.0D) {
                    return Optional.empty();
                } else {
                    float f = p_220175_0_.getWidth() / 2.0F;
                    Vec3d vec3d = new Vec3d((double) blockpos$mutableblockpos.getX() + 0.5D, d0, (double) blockpos$mutableblockpos.getZ() + 0.5D);
                    return p_220175_1_.areCollisionShapesEmpty(new AxisAlignedBB(vec3d.x - (double) f, vec3d.y, vec3d.z - (double) f, vec3d.x + (double) f, vec3d.y + (double) p_220175_0_.getHeight(), vec3d.z + (double) f)) ? Optional.of(vec3d) : Optional.empty();
                }
            }
        }
    }

    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    /**
     * Gets the render layer this block will render on. SOLID for solid blocks, CUTOUT or CUTOUT_MIPPED for on-off
     * transparency (glass, reeds), TRANSLUCENT for fully blended transparency (stained glass)
     */
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, PART, OCCUPIED);
    }


    @OnlyIn(Dist.CLIENT)
    public long getPositionRandom(BlockState state, BlockPos pos) {
        BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING), state.get(PART) == BedPart.HEAD ? 0 : 1);
        return MathHelper.getCoordinateRandom(blockpos.getX(), pos.getY(), blockpos.getZ());
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TofuBedTileEntity();
    }

}