package baguchan.mcmod.tofucraft.client.render.item;

import baguchan.mcmod.tofucraft.tileentity.TofuBedTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuBlockItemRender extends ItemStackTileEntityRenderer {
    private final TofuBedTileEntity bed = new TofuBedTileEntity();

    public void func_239207_a_(ItemStack itemStackIn, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        TileEntityRendererDispatcher.instance.renderItem(this.bed, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}