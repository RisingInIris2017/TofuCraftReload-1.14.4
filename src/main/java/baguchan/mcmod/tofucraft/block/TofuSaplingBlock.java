package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.init.TofuTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class TofuSaplingBlock extends SaplingBlock {
    public TofuSaplingBlock(Tree tree, Properties properties) {
        super(tree, properties);
    }

    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        if (!worldIn.isAreaLoaded(pos, 1))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getLight(pos.up()) >= 4 && random.nextInt(7) == 0) {
            this.grow(worldIn, pos, state, random);
        }
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || state.isIn(TofuTags.Blocks.TOFUTERRAIN);
    }
}