package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class SimpleBarrelBlock extends Block {
    public static final IntegerProperty FERM = IntegerProperty.create("ferm", 0, 8);

    public SimpleBarrelBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FERM, 0));
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        if (isUnderWeight(worldIn, pos)) {
            int i = state.get(FERM);
            if (rand.nextInt(5) == 0) {
                if (i < 7) {
                    worldIn.setBlockState(pos, state.with(FERM, Integer.valueOf(i + 1)), 2);
                }
            }
        }
    }

    public boolean isUnderWeight(World world, BlockPos pos) {
        BlockState weightBlock = world.getBlockState(pos.up());
        BlockState baseBlock = world.getBlockState(pos.down());

        boolean isWeightValid = weightBlock != null && (weightBlock.getMaterial() == Material.ROCK || weightBlock.getMaterial() == Material.IRON) && weightBlock != TofuBlocks.ISHITOFU.getDefaultState();
        float baseHardness = baseBlock.getBlockHardness(world, pos.down());
        boolean isBaseValid = baseBlock.isNormalCube(world, pos) && (baseBlock.getMaterial() == Material.ROCK || baseBlock.getMaterial() == Material.IRON || baseHardness >= 1.0F || baseHardness < 0.0F);

        return isWeightValid && isBaseValid;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FERM);
    }
}
