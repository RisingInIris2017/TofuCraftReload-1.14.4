package baguchan.mcmod.tofucraft.client.render.tileentity;

import baguchan.mcmod.tofucraft.tileentity.TofuChestTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuChestItemRender extends ItemStackTileEntityRenderer {
    private final TofuChestTileEntity chest = new TofuChestTileEntity();


    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        super.render(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        TileEntityRendererDispatcher.instance.renderItem(this.chest, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}