package baguchan.mcmod.tofucraft.client.model;

import baguchan.mcmod.tofucraft.entity.GrillderEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

public class GrillderModel<T extends GrillderEntity> extends EntityModel<T> {
    public RendererModel body;
    public RendererModel head;
    public RendererModel WingL;
    public RendererModel WingR;
    public RendererModel legR;
    public RendererModel legL;
    public RendererModel WingL_1;
    public RendererModel WingR_1;
    public RendererModel mouseUp;
    public RendererModel mouseDown;

    public GrillderModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.WingL = new RendererModel(this, 38, 0);
        this.WingL.setRotationPoint(3.0F, -2.0F, 5.0F);
        this.WingL.addBox(0.0F, 0.0F, -4.0F, 6, 1, 8, 0.0F);
        this.legL = new RendererModel(this, 92, 0);
        this.legL.setRotationPoint(2.0F, 3.0F, 8.0F);
        this.legL.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.mouseUp = new RendererModel(this, 0, 29);
        this.mouseUp.setRotationPoint(0.0F, -2.0F, -6.0F);
        this.mouseUp.addBox(-2.5F, -2.0F, -5.0F, 5, 2, 5, 0.0F);
        this.WingR = new RendererModel(this, 66, 0);
        this.WingR.setRotationPoint(-3.0F, -2.0F, 5.0F);
        this.WingR.addBox(-6.0F, 0.0F, -4.0F, 6, 1, 8, 0.0F);
        this.head = new RendererModel(this, 0, 17);
        this.head.setRotationPoint(0.0F, 18.0F, -2.0F);
        this.head.addBox(-3.0F, -5.0F, -6.0F, 6, 6, 6, 0.0F);
        this.WingL_1 = new RendererModel(this, 38, 9);
        this.WingL_1.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.WingL_1.addBox(0.0F, 0.0F, -4.0F, 6, 1, 5, 0.0F);
        this.WingR_1 = new RendererModel(this, 66, 9);
        this.WingR_1.setRotationPoint(-6.0F, 0.0F, 0.0F);
        this.WingR_1.addBox(-6.0F, 0.0F, -4.0F, 6, 1, 5, 0.0F);
        this.mouseDown = new RendererModel(this, 20, 28);
        this.mouseDown.setRotationPoint(0.0F, -2.0F, -6.0F);
        this.mouseDown.addBox(-2.5F, 0.0F, -4.0F, 5, 1, 4, 0.0F);
        this.legR = new RendererModel(this, 92, 0);
        this.legR.setRotationPoint(-2.0F, 3.0F, 8.0F);
        this.legR.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.body = new RendererModel(this, 0, 0);
        this.body.setRotationPoint(0.0F, 18.0F, -2.0F);
        this.body.addBox(-4.0F, -2.5F, 0.0F, 8, 6, 11, 0.0F);
        this.body.addChild(this.WingL);
        this.body.addChild(this.legL);
        this.head.addChild(this.mouseUp);
        this.body.addChild(this.WingR);
        this.WingL.addChild(this.WingL_1);
        this.WingR.addChild(this.WingR_1);
        this.head.addChild(this.mouseDown);
        this.body.addChild(this.legR);
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.head.render(scale);
        this.body.render(scale);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        float f = ageInTicks - (float) entityIn.ticksExisted;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.legR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

        this.WingR.rotateAngleZ = ageInTicks - ((float) Math.PI * 0.2F);
        this.WingL.rotateAngleZ = -ageInTicks + ((float) Math.PI * 0.2F);
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