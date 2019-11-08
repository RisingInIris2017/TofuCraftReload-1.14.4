package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.init.TofuTileEntitys;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class TofuChestBlock extends ChestBlock {

    public TofuChestBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Nullable
    public TileEntity createNewTileEntity(IBlockReader world) {
        return TofuTileEntitys.TOFUCHEST.create();
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}