package baguchan.mcmod.tofucraft.client.model;

import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class TofunianModel extends BipedModel<TofunianEntity> {

    public TofunianModel() {
        this(0.0F, false);
    }

    protected TofunianModel(float par1, float par2, int par3, int par4) {
        super(par1, par2, par3, par4);
    }

    public TofunianModel(float par1, boolean par2) {
        super(par1, 0.0F, 64, par2 ? 32 : 64);

//        this.bipedCloak = new ModelRenderer(this, 0, 0);
//        this.bipedCloak.addBox(-5.0F, 8.0F, -1.0F, 8, 11, 1, par1);
//        this.bipedEars = new ModelRenderer(this, 24, 0);
//        this.bipedEars.addBox(-3.0F, 1.0F, -1.0F, 6, 6, 1, par1);

        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1);
        this.bipedHead.addBox(-1.5F, -11.0F, -0.0F, 3, 3, 0, par1);
        this.bipedHead.setRotationPoint(0.0F, 12.0F, 0.0F);

        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1 + 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 11.0F, 0.0F);

        this.bipedBody = new ModelRenderer(this, 8, 16);
        this.bipedBody.addBox(-3.0F, 0.0F, -2.0F, 6, 6, 4, par1);
        this.bipedBody.setRotationPoint(0.0F, 16.0F, 0.0F);

        this.bipedRightArm = new ModelRenderer(this, 28, 16);
        this.bipedRightArm.addBox(-0.0F, -2.0F, -1.0F, 2, 5, 2, par1);
        this.bipedRightArm.setRotationPoint(-5.0F, 18.0F, 0.0F);

        this.bipedLeftArm = new ModelRenderer(this, 28, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-2.0F, -2.0F, -1.0F, 2, 5, 2, par1);
        this.bipedLeftArm.setRotationPoint(5.0F, 18.0F, 0.0F);

        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, par1);
        this.bipedRightLeg.setRotationPoint(-1.4F, 14.0F, 0.0F);

        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, par1);
        this.bipedLeftLeg.setRotationPoint(1.4F, 14.0F, 0.0F);
    }

    @Override
    public void setRotationAngles(TofunianEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        float f6 = 12.0f;

        boolean flag = entityIn.getShakeHeadTicks() > 0;

        if (flag) {
            this.bipedHead.rotateAngleZ = 0.3F * MathHelper.sin(0.45F * ageInTicks);
            this.bipedHead.rotateAngleX = 0.4F;
        } else {
            this.bipedHead.rotateAngleZ = 0.0F;
        }

        this.bipedBody.rotationPointY = 14.0F;
        this.bipedRightArm.rotationPointZ = 0.0F;
        this.bipedLeftArm.rotationPointZ = 0.0F;
        this.bipedLeftArm.rotationPointY = 16.0F;
        this.bipedRightArm.rotationPointY = 16.0F;
        this.bipedRightLeg.rotationPointZ = 0.0F;
        this.bipedLeftLeg.rotationPointZ = 0.0F;
        this.bipedRightLeg.rotationPointY = 6.0F + f6;
        this.bipedLeftLeg.rotationPointY = 6.0F + f6;
        this.bipedHead.rotationPointZ = -0.0F;
        if (isChild) {
            this.bipedHead.rotationPointY = f6 + 1.0F;
        } else {
            this.bipedHead.rotationPointY = f6 + 2.0F;
        }
        this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
        this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
        this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;

        if (this.isChild) {
            this.bipedBody.showModel = false;
            this.bipedRightArm.showModel = false;
            this.bipedLeftArm.showModel = false;
        } else {
            this.bipedBody.showModel = true;
            this.bipedRightArm.showModel = true;
            this.bipedLeftArm.showModel = true;
        }
    }

    protected ModelRenderer getArmForSide(HandSide side) {
        return side == HandSide.LEFT ? this.bipedLeftArm : this.bipedRightArm;
    }
}
