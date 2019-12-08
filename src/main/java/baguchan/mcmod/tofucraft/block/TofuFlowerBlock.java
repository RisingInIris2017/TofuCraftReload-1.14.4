package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.init.TofuTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class TofuFlowerBlock extends FlowerBlock {
    public TofuFlowerBlock(Effect effect, int i, Properties properties) {
        super(effect, i, properties);
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || state.isIn(TofuTags.Blocks.TOFUTERRAIN);
    }
}
