package baguchan.mcmod.tofucraft.client.render.item;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.tileentity.TofuBedTileEntity;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuBlockItemRender extends ItemStackTileEntityRenderer {
    private final TofuBedTileEntity bed = new TofuBedTileEntity();

    @Override
    public void renderByItem(ItemStack stack) {
        if (stack.getItem() == Item.getItemFromBlock(TofuBlocks.TOFUBED)) {
            TileEntityRendererDispatcher.instance.renderAsItem(this.bed);
        }
    }
}