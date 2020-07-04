package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class TofuBerryBlock extends Block implements IGrowable {
    protected static final VoxelShape voxel = VoxelShapes.or(Block.makeCuboidShape(4.5D, 1.0D, 4.5D, 11.5D, 8.0D, 11.5D), Block.makeCuboidShape(7.0D, 8.0D, 7.0D, 9.0D, 16.0D, 9.0D));

    public TofuBerryBlock(Properties properties) {
        super(properties);
    }


    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        for (Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockstate = this.getDefaultState();
                if (blockstate.isValidPosition(context.getWorld(), context.getPos())) {
                    return blockstate;
                }
            }
        }

        return null;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return voxel;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return voxel;
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction direction = func_220277_j(state).getOpposite();
        return vaildStem(worldIn, pos.offset(direction), direction.getOpposite());
    }

    public static boolean vaildStem(IWorldReader worldIn, BlockPos pos, Direction directionIn) {
        BlockState blockstate = worldIn.getBlockState(pos);
        return blockstate.func_235714_a_(BlockTags.LEAVES) || blockstate.getBlock() == TofuBlocks.TOFUTERRAIN || blockstate.getBlock() == TofuBlocks.MOMENTOFU || blockstate.getBlock() == TofuBlocks.TOFUBERRYSTEM;
    }

    protected static Direction func_220277_j(BlockState p_220277_0_) {
        return Direction.DOWN;
    }

    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!state.isValidPosition(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        } else {
            if (rand.nextInt(3) == 0 && worldIn.isAirBlock(pos.down())) {
                worldIn.setBlockState(pos, TofuBlocks.TOFUBERRYSTEM.getDefaultState(), 3);
                worldIn.setBlockState(pos.down(), state, 3);
            }

        }
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.isValidPosition(worldIn, currentPos)) {
            worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
        }

        return func_220277_j(stateIn).getOpposite() == facing && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        int k = 1;
        int l = 4 + rand.nextInt(3);

        for (int i1 = 0; i1 < l; ++i1) {
            BlockPos blockpos = pos.down(i1);
            BlockState blockstate = worldIn.getBlockState(blockpos);
            if (!worldIn.isAirBlock(blockpos.down())) {
                worldIn.setBlockState(blockpos, state, 3);
                return;
            }

            worldIn.setBlockState(blockpos, TofuBlocks.TOFUBERRYSTEM.getDefaultState(), 3);

            if (i1 == l - 1) {
                worldIn.setBlockState(blockpos, state, 3);
            }
            ++k;
        }
    }

}
