package baguchan.mcmod.tofucraft.client.model;

import baguchan.mcmod.tofucraft.entity.TofuGandlemEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class TofuGandlemModel<T extends TofuGandlemEntity> extends SegmentedModel<T> {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer handR;
    public ModelRenderer handL;
    public ModelRenderer core;
    public ModelRenderer core2;
    public ModelRenderer handR2;
    public ModelRenderer handL2;

    public TofuGandlemModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.core2 = new ModelRenderer(this, 32, 16);
        this.core2.setRotationPoint(0.0F, 15.0F, 0.0F);
        this.core2.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
        this.head = new ModelRenderer(this, 32, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.handR2 = new ModelRenderer(this, 14, 21);
        this.handR2.setRotationPoint(-1.0F, 7.0F, 1.0F);
        this.handR2.addBox(-1.5F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.setRotateAngle(handR2, -0.9560913642424937F, 0.0F, 0.0F);
        this.handR = new ModelRenderer(this, 0, 21);
        this.handR.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.handR.addBox(-2.5F, 0.0F, -2.0F, 3, 9, 4, 0.0F);
        this.handL = new ModelRenderer(this, 0, 21);
        this.handL.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.handL.addBox(-0.5F, 0.0F, -2.0F, 3, 9, 4, 0.0F);
        this.handL2 = new ModelRenderer(this, 14, 21);
        this.handL2.setRotationPoint(1.0F, 7.0F, 1.0F);
        this.handL2.addBox(-1.5F, 0.0F, -2.0F, 3, 8, 4, 0.0F);
        this.setRotateAngle(handL2, -0.9560913642424937F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-5.0F, 0.0F, -3.0F, 10, 15, 6, 0.0F);
        this.core = new ModelRenderer(this, 32, 16);
        this.core.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.core.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 4, 0.0F);
        this.body.addChild(this.core2);
        this.body.addChild(this.head);
        this.handR.addChild(this.handR2);
        this.body.addChild(this.handR);
        this.body.addChild(this.handL);
        this.handL.addChild(this.handL2);
        this.body.addChild(this.core);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);

        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);

        this.handR2.rotateAngleX = -0.9560913642424937F;
        this.handL2.rotateAngleX = -0.9560913642424937F;

        this.handR.rotateAngleX = 0.0F;
        this.handL.rotateAngleX = 0.0F;
        this.handR.rotateAngleY = 0.0F;
        this.handL.rotateAngleY = 0.0F;
        this.handR.rotateAngleZ = 0.0F;
        this.handL.rotateAngleZ = 0.0F;

        float f = ageInTicks - (float) entityIn.ticksExisted;

        float f1 = entityIn.getAttackingAnimationScale(f);
        f1 = f1 * f1;

        float f2 = entityIn.getSpellCastingAnimationScale(f);
        f2 = f2 * f2;

        float f3 = entityIn.getShootingAnimationScale(f);
        f3 = f3 * f3;


        if (f2 > 0) {
            this.handR.rotateAngleX = (MathHelper.cos(ageInTicks * 0.6662F) * 0.2F) * f2;
            this.handL.rotateAngleX = (MathHelper.cos(ageInTicks * 0.6662F) * 0.2F) * f2;
            this.handR.rotateAngleZ = 2.3561945F * f2;
            this.handL.rotateAngleZ = -2.3561945F * f2;
            this.handR.rotateAngleY = 0.0F;
            this.handL.rotateAngleY = 0.0F;
            this.handR2.rotateAngleX = 0.0F;
            this.handL2.rotateAngleX = 0.0F;
        } else if (f3 > 0) {

            if (entityIn.getPrimaryHand() == HandSide.RIGHT) {
                this.handR2.rotateAngleX = -0.6F + MathHelper.cos(ageInTicks * 0.04F) * 0.15F;
                this.handR.rotateAngleX = -1.2F + MathHelper.cos(ageInTicks * 0.04F) * 0.15F;
            } else {
                this.handL2.rotateAngleX = -0.6F + MathHelper.cos(ageInTicks * 0.04F) * 0.15F;
                this.handL.rotateAngleX = -1.2F + MathHelper.cos(ageInTicks * 0.04F) * 0.15F;
            }
        } else if (f1 > 0) {
            this.handR.rotateAngleZ = 0.0F;
            this.handL.rotateAngleZ = 0.0F;
            this.handR.rotateAngleY = 0.15707964F * f1;
            this.handL.rotateAngleY = -0.15707964F * f1;

            if (entityIn.getPrimaryHand() == HandSide.RIGHT) {
                this.handR.rotateAngleX = -1.6F + MathHelper.cos(ageInTicks * 0.04F) * 0.15F;
            } else {
                this.handL.rotateAngleX = -1.6F + MathHelper.cos(ageInTicks * 0.04F) * 0.15F;
            }

            this.handR.rotateAngleZ += (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F) * f1;
            this.handL.rotateAngleZ -= (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F) * f1;
            this.handR.rotateAngleX += (MathHelper.cos(ageInTicks * 0.09F) * 0.05F) * f1;
            this.handL.rotateAngleX -= (MathHelper.cos(ageInTicks * 0.09F) * 0.05F) * f1;
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
