package baguchan.mcmod.tofucraft.client.model;

import baguchan.mcmod.tofucraft.entity.TofuTurretEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class TofuTurretModel<T extends TofuTurretEntity> extends SegmentedModel<T> {
    public ModelRenderer core;
    public ModelRenderer spike;
    public ModelRenderer spike2;
    public ModelRenderer spike3;
    public ModelRenderer spike4;

    public TofuTurretModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.spike = new ModelRenderer(this, 0, 0);
        this.spike.setRotationPoint(-3.0F, 3.0F, -3.5F);
        this.spike.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(spike, -0.7853981633974483F, 0.0F, 0.7853981633974483F);
        this.spike2 = new ModelRenderer(this, 0, 0);
        this.spike2.setRotationPoint(3.0F, 3.0F, -3.5F);
        this.spike2.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(spike2, -0.7853981633974483F, 0.0F, -0.7853981633974483F);
        this.core = new ModelRenderer(this, 0, 0);
        this.core.setRotationPoint(0.0F, 19.0F, 0.0F);
        this.core.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
        this.spike4 = new ModelRenderer(this, 0, 0);
        this.spike4.setRotationPoint(-3.0F, 3.0F, 3.5F);
        this.spike4.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(spike4, 0.7853981633974483F, -0.017453292519943295F, 0.7853981633974483F);
        this.spike3 = new ModelRenderer(this, 0, 0);
        this.spike3.setRotationPoint(3.0F, 3.0F, 3.5F);
        this.spike3.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(spike3, 0.7853981633974483F, -0.017453292519943295F, -0.7853981633974483F);
        this.core.addChild(this.spike);
        this.core.addChild(this.spike2);
        this.core.addChild(this.spike4);
        this.core.addChild(this.spike3);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.core);
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