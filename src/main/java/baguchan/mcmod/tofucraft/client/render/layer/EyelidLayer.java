package baguchan.mcmod.tofucraft.client.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class EyelidLayer<T extends Entity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    private final ResourceLocation TEXTURES;

    public EyelidLayer(IEntityRenderer<T, M> p_i50921_1_, ResourceLocation resourceLocation) {
        super(p_i50921_1_);
        TEXTURES = resourceLocation;
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        float f3 = entitylivingbaseIn.ticksExisted + partialTicks + entitylivingbaseIn.getEntityId();

        //Close Eyelid
        if (0 > MathHelper.sin(f3 * 0.05F) + MathHelper.sin(f3 * 0.13F) + MathHelper.sin(f3 * 0.7F) + 2.55F || entitylivingbaseIn instanceof LivingEntity && ((LivingEntity) entitylivingbaseIn).isSleeping()) {
            if (!entitylivingbaseIn.isInvisible()) {
                IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(this.getEntityTexture(entitylivingbaseIn)));

                this.getEntityModel().setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
                this.getEntityModel().setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

                this.getEntityModel().render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(T entityIn) {
        return TEXTURES;
    }
}
