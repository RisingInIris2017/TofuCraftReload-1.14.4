package baguchan.mcmod.tofucraft.client.render.item;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.init.TofuItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuShieldItemRender extends ItemStackTileEntityRenderer {
    private final static ResourceLocation ISHI_TEXTURE = new ResourceLocation(TofuCraftCore.MODID, "textures/entity/tofuishi_shield.png");
    private final static ResourceLocation METAL_TEXTURE = new ResourceLocation(TofuCraftCore.MODID, "textures/entity/tofumetal_shield.png");


    private final ShieldModel modelShield = new ShieldModel();

    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        super.render(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        if (itemStackIn.getItem() == TofuItems.TOFUISHI_SHIELD) {
            matrixStackIn.push();
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);
            IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelShield.getRenderType(ISHI_TEXTURE), false, itemStackIn.hasEffect());
            this.modelShield.func_228294_b_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);

            this.modelShield.func_228293_a_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);


            matrixStackIn.pop();
        } else if (itemStackIn.getItem() == TofuItems.TOFUMETAL_SHIELD) {
            matrixStackIn.push();
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);
            IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelShield.getRenderType(METAL_TEXTURE), false, itemStackIn.hasEffect());
            this.modelShield.func_228294_b_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);

            this.modelShield.func_228293_a_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);


            matrixStackIn.pop();
        }
    }

}