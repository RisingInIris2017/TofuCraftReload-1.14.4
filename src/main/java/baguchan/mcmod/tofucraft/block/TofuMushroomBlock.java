package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuFeatures;
import baguchan.mcmod.tofucraft.init.TofuTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class TofuMushroomBlock extends BushBlock implements IGrowable {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);

    public TofuMushroomBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isIn(TofuTags.Blocks.TOFUTERRAIN);
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {


        if (worldIn.getLight(pos) < 9 && rand.nextInt(50) == 0) {
            func_226940_a_(worldIn, pos, state, rand);
        }

        if (rand.nextInt(25) == 0) {
            addNewMushroom(state, worldIn, pos, rand);
        }
    }

    public void addNewMushroom(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        int i = 5;
        int j = 4;

        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
            if (worldIn.getBlockState(blockpos).getBlock() == this) {
                --i;
                if (i <= 0) {
                    return;
                }
            }
        }

        BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);

        for (int k = 0; k < 4; ++k) {
            if (worldIn.isAirBlock(blockpos1) && state.isValidPosition(worldIn, blockpos1)) {
                pos = blockpos1;
            }

            blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
        }

        if (worldIn.isAirBlock(blockpos1) && state.isValidPosition(worldIn, blockpos1)) {
            worldIn.setBlockState(blockpos1, state, 2);
        }
    }

    public boolean func_226940_a_(ServerWorld p_226940_1_, BlockPos p_226940_2_, BlockState p_226940_3_, Random p_226940_4_) {
        p_226940_1_.removeBlock(p_226940_2_, false);
        ConfiguredFeature<NoFeatureConfig, ?> configuredfeature;
        if (this == TofuBlocks.ZUNDATOFU_MUSHROOM) {
            if (p_226940_4_.nextFloat() < 0.2F) {
                configuredfeature = TofuFeatures.ZUNDAMUSHROOM_BIG.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
            } else {
                configuredfeature = TofuFeatures.ZUNDAMUSHROOM_SMALL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
            }
        } else {
            configuredfeature = TofuFeatures.ZUNDAMUSHROOM_SMALL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
        }

        if (configuredfeature.place(p_226940_1_, p_226940_1_.getChunkProvider().getChunkGenerator(), p_226940_4_, p_226940_2_)) {
            return true;
        } else {
            p_226940_1_.setBlockState(p_226940_2_, this.getDefaultState(), 3);
            return false;
        }
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return rand.nextFloat() < 0.25F;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        func_226940_a_(worldIn, pos, state, rand);
        //addNewMushroom(state,worldIn,pos,rand);
    }

    public boolean needsPostProcessing(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return true;
    }
}
