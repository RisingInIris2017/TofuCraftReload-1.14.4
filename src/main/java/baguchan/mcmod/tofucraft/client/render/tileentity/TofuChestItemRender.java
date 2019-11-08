package baguchan.mcmod.tofucraft.client.render.tileentity;

import baguchan.mcmod.tofucraft.tileentity.TofuChestTileEntity;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class TofuChestItemRender extends ItemStackTileEntityRenderer {
    private final TofuChestTileEntity chest = new TofuChestTileEntity();


    @Override
    public void renderByItem(ItemStack stack) {
        TileEntityRendererDispatcher.instance.renderAsItem(this.chest);
    }
}