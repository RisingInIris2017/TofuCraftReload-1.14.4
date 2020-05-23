package baguchan.mcmod.tofucraft.client.render.layer;

import baguchan.mcmod.tofucraft.entity.TofuGandlemEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;

public class TofuGandlemGlowLayer<T extends TofuGandlemEntity, M extends EntityModel<T>> extends GlowLayer<T, M> {
    public TofuGandlemGlowLayer(IEntityRenderer<T, M> p_i50921_1_, ResourceLocation resourceLocation) {
        super(p_i50921_1_, resourceLocation);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isSleep()) {
            super.render(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        }
    }
}
