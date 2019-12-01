package baguchan.mcmod.tofucraft.client.model;

import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.HandSide;
import org.lwjgl.opengl.GL11;

public class TofunianModel extends BipedModel<TofunianEntity> {

    public TofunianModel() {
        this(0.0F, false);
    }

    protected TofunianModel(float par1, float par2, int par3, int par4) {
        super(par1, par2, par3, par4);
    }

    public TofunianModel(float par1, boolean par2) {
        super(par1, 0.0F, 64, par2 ? 32 : 64);

//        this.bipedCloak = new RendererModel(this, 0, 0);
//        this.bipedCloak.addBox(-5.0F, 8.0F, -1.0F, 8, 11, 1, par1);
//        this.bipedEars = new RendererModel(this, 24, 0);
//        this.bipedEars.addBox(-3.0F, 1.0F, -1.0F, 6, 6, 1, par1);

        this.bipedHead = new RendererModel(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1);
        this.bipedHead.addBox(-1.5F, -11.0F, -0.0F, 3, 3, 0, par1);
        this.bipedHead.setRotationPoint(0.0F, 12.0F, 0.0F);

        this.bipedHeadwear = new RendererModel(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1 + 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 11.0F, 0.0F);

        this.bipedBody = new RendererModel(this, 8, 16);
        this.bipedBody.addBox(-3.0F, 0.0F, -2.0F, 6, 6, 4, par1);
        this.bipedBody.setRotationPoint(0.0F, 13.0F, 0.0F);

        this.bipedRightArm = new RendererModel(this, 28, 16);
        this.bipedRightArm.addBox(-0.0F, -2.0F, -1.0F, 2, 5, 2, par1);
        this.bipedRightArm.setRotationPoint(-5.0F, 15.0F, 0.0F);

        this.bipedLeftArm = new RendererModel(this, 28, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-2.0F, -2.0F, -1.0F, 2, 5, 2, par1);
        this.bipedLeftArm.setRotationPoint(5.0F, 15.0F, 0.0F);

        this.bipedRightLeg = new RendererModel(this, 0, 16);
        this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, par1);
        this.bipedRightLeg.setRotationPoint(-1.4F, 14.0F, 0.0F);

        this.bipedLeftLeg = new RendererModel(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, par1);
        this.bipedLeftLeg.setRotationPoint(1.4F, 14.0F, 0.0F);
    }


    @Override
    public void render(TofunianEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        if (this.isChild) {
            float f6 = 2.0F;
            GL11.glPushMatrix();
            GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
            //GL11.glTranslatef(0.0F, 12.0F * scale, 0.0F);
            GL11.glTranslatef(0.0F, 16.0F * scale, 0.0F);
            this.bipedHead.render(scale);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
            GL11.glTranslatef(0.0F, 24F * scale, 0.0F);
            //this.bipedBody.render(scale);
            //this.bipedRightArm.render(scale);
            //this.bipedLeftArm.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
            this.bipedHeadwear.render(scale);
            GL11.glPopMatrix();
        } else {
            this.bipedHead.render(scale);
            this.bipedBody.render(scale);
            this.bipedRightArm.render(scale);
            this.bipedLeftArm.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
            this.bipedHeadwear.render(scale);
        }
    }

    @Override
    public void setRotationAngles(TofunianEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        float f6 = 12.0f;

        this.bipedRightArm.rotationPointZ = 0.0F;
        this.bipedLeftArm.rotationPointZ = 0.0F;
        this.bipedRightLeg.rotationPointZ = 0.0F;
        this.bipedLeftLeg.rotationPointZ = 0.0F;
        this.bipedRightLeg.rotationPointY = 6.0F + f6;
        this.bipedLeftLeg.rotationPointY = 6.0F + f6;
        this.bipedHead.rotationPointZ = -0.0F;
        this.bipedHead.rotationPointY = f6 + 1.0F;
        this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
        this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
        this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;
    }

    protected RendererModel getArmForSide(HandSide side) {
        return side == HandSide.LEFT ? this.bipedLeftArm : this.bipedRightArm;
    }
}
