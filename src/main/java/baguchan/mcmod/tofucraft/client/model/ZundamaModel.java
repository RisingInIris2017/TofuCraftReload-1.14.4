package baguchan.mcmod.tofucraft.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ZundamaModel<T extends LivingEntity> extends EntityModel<T> {
    public ModelRenderer core;
    public ModelRenderer gel;

    public ZundamaModel() {
        super(RenderType::entityTranslucent);
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.core = new ModelRenderer(this, 0, 0);
        this.core.setRotationPoint(0.0F, 18.0F, 0.0F);
        this.core.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
        this.gel = new ModelRenderer(this, 0, 12);
        this.gel.setRotationPoint(0.0F, 18.0F, 0.0F);
        this.gel.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.getParts().forEach((p_228272_8_) -> {
            p_228272_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.core, this.gel);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
