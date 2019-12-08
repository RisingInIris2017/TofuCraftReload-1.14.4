package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.init.TofuTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class TofuBushBlock extends BushBlock {
    public TofuBushBlock(Properties properties) {
        super(properties);
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isIn(TofuTags.Blocks.TOFUTERRAIN);
    }
}
