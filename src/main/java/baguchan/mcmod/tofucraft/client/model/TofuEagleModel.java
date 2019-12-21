package baguchan.mcmod.tofucraft.client.model;

import baguchan.mcmod.tofucraft.entity.TofuEagleEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

public class TofuEagleModel<T extends TofuEagleEntity> extends EntityModel<T> {
    public RendererModel body;
    public RendererModel head;
    public RendererModel neck;
    public RendererModel WingL;
    public RendererModel WingR;
    public RendererModel legR;
    public RendererModel legL;
    public RendererModel tail;
    public RendererModel WingL_1;
    public RendererModel WingR_1;
    public RendererModel legR2;
    public RendererModel legL2;
    public RendererModel mouseUp;
    public RendererModel mouseDown;

    public TofuEagleModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.mouseUp = new RendererModel(this, 0, 29);
        this.mouseUp.setRotationPoint(0.0F, -2.0F, -6.0F);
        this.mouseUp.addBox(-1.5F, -2.0F, -3.0F, 3, 2, 3, 0.0F);
        this.tail = new RendererModel(this, 0, 34);
        this.tail.setRotationPoint(0.0F, -2.0F, 12.0F);
        this.tail.addBox(-2.0F, 0.0F, 0.0F, 4, 1, 4, 0.0F);
        this.legR = new RendererModel(this, 92, 0);
        this.legR.setRotationPoint(-2.0F, 3.0F, 6.0F);
        this.legR.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
        this.WingL = new RendererModel(this, 38, 0);
        this.WingL.setRotationPoint(2.0F, -2.0F, 5.0F);
        this.WingL.addBox(0.0F, 0.0F, -4.0F, 7, 1, 9, 0.0F);
        this.legL2 = new RendererModel(this, 103, 0);
        this.legL2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.legL2.addBox(-2.0F, 0.0F, -1.0F, 3, 2, 3, 0.0F);
        this.WingL_1 = new RendererModel(this, 38, 10);
        this.WingL_1.setRotationPoint(7.0F, 0.0F, 0.0F);
        this.WingL_1.addBox(0.0F, 0.0F, -4.0F, 6, 1, 5, 0.0F);
        this.WingR = new RendererModel(this, 66, 0);
        this.WingR.setRotationPoint(-3.0F, -2.0F, 5.0F);
        this.WingR.addBox(-6.0F, 0.0F, -4.0F, 7, 1, 9, 0.0F);
        this.head = new RendererModel(this, 20, 18);
        this.head.setRotationPoint(0.0F, 15.6F, -1.0F);
        this.head.addBox(-2.5F, -5.0F, -6.0F, 5, 5, 5, 0.0F);
        this.mouseDown = new RendererModel(this, 20, 28);
        this.mouseDown.setRotationPoint(0.0F, -2.0F, -6.0F);
        this.mouseDown.addBox(-1.0F, 0.0F, -2.0F, 2, 1, 2, 0.0F);
        this.neck = new RendererModel(this, 0, 18);
        this.neck.setRotationPoint(0.0F, 17.0F, 0.0F);
        this.neck.addBox(-2.5F, -2.5F, -5.0F, 5, 5, 5, 0.0F);
        this.setRotateAngle(neck, -0.8196066167365371F, 0.0F, 0.0F);
        this.WingR_1 = new RendererModel(this, 66, 10);
        this.WingR_1.setRotationPoint(-6.0F, 0.0F, 0.0F);
        this.WingR_1.addBox(-6.0F, 0.0F, -4.0F, 6, 1, 5, 0.0F);
        this.body = new RendererModel(this, 0, 0);
        this.body.setRotationPoint(0.0F, 17.0F, -2.0F);
        this.body.addBox(-3.0F, -2.5F, 0.0F, 6, 6, 12, 0.0F);
        this.legL = new RendererModel(this, 92, 0);
        this.legL.setRotationPoint(2.0F, 3.0F, 6.0F);
        this.legL.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
        this.legR2 = new RendererModel(this, 103, 0);
        this.legR2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.legR2.addBox(-1.0F, 0.0F, -1.0F, 3, 2, 3, 0.0F);
        this.head.addChild(this.mouseUp);
        this.body.addChild(this.tail);
        this.body.addChild(this.legR);
        this.body.addChild(this.WingL);
        this.legL.addChild(this.legL2);
        this.WingL.addChild(this.WingL_1);
        this.body.addChild(this.WingR);
        this.head.addChild(this.mouseDown);
        this.WingR.addChild(this.WingR_1);
        this.body.addChild(this.legL);
        this.legR.addChild(this.legR2);
    }

    @Override
    public void render(T entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.head.render(f5);
        this.neck.render(f5);
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        float f = ageInTicks - (float) entityIn.ticksExisted;
        this.body.rotateAngleX = 0.0F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.neck.rotateAngleY = netHeadYaw * 0.017453292F;
        this.neck.rotateAngleX = headPitch * 0.017453292F;
        this.legR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

        this.WingR.rotateAngleZ = -0.425F;
        this.WingL.rotateAngleZ = 0.425F;
        this.WingR_1.rotateAngleZ = -0.55F;
        this.WingL_1.rotateAngleZ = 0.55F;

        if (entityIn.isFlying()) {
            setRotateAngle(WingL, 0.0F, 0.0F, 0.0F - MathHelper.sin(ageInTicks * 1.65F) * (0.25F));
            setRotateAngle(WingR, 0.0F, 0.0F, 0.0F + MathHelper.sin(ageInTicks * 1.65F) * (0.25F));
            setRotateAngle(WingL_1, 0.0F, 0.0F, 0.3491F - MathHelper.sin(ageInTicks * 1.65F) * (0.5F));
            setRotateAngle(WingR_1, 0.0F, 0.0F, -0.3491F + MathHelper.sin(ageInTicks * 1.65F) * (0.5F));
            this.legR.rotateAngleX = 0.2F;
            this.legL.rotateAngleX = 0.2F;
        } else if (this.isSitting || entityIn.isSitting()) {
            this.body.rotateAngleX = -0.8F;
            this.legR.rotateAngleX = 0.4F;
            this.legL.rotateAngleX = 0.4F;
        }
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
