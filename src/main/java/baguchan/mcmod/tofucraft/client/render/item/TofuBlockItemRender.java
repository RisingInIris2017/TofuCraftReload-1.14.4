package baguchan.mcmod.tofucraft.client.render.item;

import baguchan.mcmod.tofucraft.tileentity.TofuBedTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuBlockItemRender extends ItemStackTileEntityRenderer {
    private final TofuBedTileEntity bed = new TofuBedTileEntity();


    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        super.render(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        TileEntityRendererDispatcher.instance.renderItem(this.bed, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

}