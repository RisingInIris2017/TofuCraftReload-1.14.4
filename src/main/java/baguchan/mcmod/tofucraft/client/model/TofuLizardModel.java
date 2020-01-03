package baguchan.mcmod.tofucraft.client.model;

import baguchan.mcmod.tofucraft.entity.TofuLizardEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

public class TofuLizardModel<T extends TofuLizardEntity> extends EntityModel<T> {
    public RendererModel body;
    public RendererModel head;
    public RendererModel body2;
    public RendererModel legR;
    public RendererModel legL;
    public RendererModel handL;
    public RendererModel handR;
    public RendererModel tail;
    public RendererModel handL2;
    public RendererModel handR2;
    public RendererModel tail2;
    public RendererModel jaw;

    public TofuLizardModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.legL = new RendererModel(this, 28, 0);
        this.legL.setRotationPoint(2.0F, 5.0F, 0.0F);
        this.legL.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.legR = new RendererModel(this, 28, 0);
        this.legR.setRotationPoint(-2.0F, 5.0F, 0.0F);
        this.legR.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.handR = new RendererModel(this, 36, 0);
        this.handR.setRotationPoint(3.0F, -5.0F, -3.0F);
        this.handR.addBox(0.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.tail2 = new RendererModel(this, 22, 7);
        this.tail2.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.tail2.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(tail2, 0.36425021489121656F, 0.0F, 0.0F);
        this.handR2 = new RendererModel(this, 36, 0);
        this.handR2.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.handR2.addBox(0.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(handR2, -0.6829473363053812F, 0.0F, 0.0F);
        this.body2 = new RendererModel(this, 0, 15);
        this.body2.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.body2.addBox(-3.5F, 0.0F, -2.0F, 7, 5, 4, 0.0F);
        this.setRotateAngle(body2, -0.5009094953223726F, 0.0F, 0.0F);
        this.jaw = new RendererModel(this, 28, 25);
        this.jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jaw.addBox(-3.0F, 0.0F, -8.0F, 6, 2, 8, 0.0F);
        this.setRotateAngle(jaw, 0.22759093446006054F, 0.0F, 0.0F);
        this.head = new RendererModel(this, 0, 24);
        this.head.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.head.addBox(-3.0F, -3.0F, -8.0F, 6, 3, 8, 0.0F);
        this.handL = new RendererModel(this, 36, 0);
        this.handL.setRotationPoint(-5.0F, -5.0F, -3.0F);
        this.handL.addBox(0.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.body = new RendererModel(this, 0, 0);
        this.body.setRotationPoint(0.0F, 7.0F, -1.0F);
        this.body.addBox(-3.0F, 0.0F, -2.5F, 6, 10, 5, 0.0F);
        this.setRotateAngle(body, 0.5009094953223726F, 0.0F, 0.0F);
        this.tail = new RendererModel(this, 22, 7);
        this.tail.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.tail.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(tail, 0.7740535232594852F, 0.0F, 0.0F);
        this.handL2 = new RendererModel(this, 36, 0);
        this.handL2.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.handL2.addBox(0.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(handL2, -0.6829473363053812F, 0.0F, 0.0F);
        this.body2.addChild(this.legL);
        this.body2.addChild(this.legR);
        this.body2.addChild(this.handR);
        this.tail.addChild(this.tail2);
        this.handR.addChild(this.handR2);
        this.body.addChild(this.body2);
        this.head.addChild(this.jaw);
        this.body2.addChild(this.handL);
        this.body2.addChild(this.tail);
        this.handL.addChild(this.handL2);
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.head.render(scale);
        this.body.render(scale);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        float f = 1.0F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.legR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

        this.handR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
        this.handL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
        this.handR.rotateAngleZ = 0.0F;
        this.handL.rotateAngleY = 0.0F;
        this.handL.rotateAngleZ = 0.0F;

        this.handR.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handL.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handR.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.handL.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}