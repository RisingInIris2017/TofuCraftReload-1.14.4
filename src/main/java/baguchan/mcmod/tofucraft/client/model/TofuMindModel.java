package baguchan.mcmod.tofucraft.client.model;

import baguchan.mcmod.tofucraft.entity.TofuMindEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TofuMindModel<T extends TofuMindEntity> extends SegmentedModel<T> implements IHasArm, IHasHead {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer handR;
    public ModelRenderer handL;
    public ModelRenderer balanceCore;
    public ModelRenderer core;

    public TofuMindModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.core = new ModelRenderer(this, 28, 26);
        this.core.setRotationPoint(0.0F, 5.3F, -2.6F);
        this.core.addBox(-2.0F, -2.0F, -1.0F, 4, 4, 1, 0.0F);
        this.balanceCore = new ModelRenderer(this, 28, 16);
        this.balanceCore.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.balanceCore.addBox(-2.5F, 0.0F, -2.5F, 5, 5, 5, 0.0F);
        this.handL = new ModelRenderer(this, 14, 18);
        this.handL.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.handL.addBox(-0.5F, 0.0F, -2.0F, 3, 9, 4, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.body.addBox(-5.0F, 0.0F, -3.0F, 10, 12, 6, 0.0F);
        this.handR = new ModelRenderer(this, 0, 18);
        this.handR.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.handR.addBox(-2.5F, 0.0F, -2.0F, 3, 9, 4, 0.0F);
        this.head = new ModelRenderer(this, 32, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.body.addChild(this.core);
        this.body.addChild(this.balanceCore);
        this.body.addChild(this.handL);
        this.body.addChild(this.handR);
        this.body.addChild(this.head);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);

        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);


        this.body.rotateAngleY = 0.0F;
        float f = 1.0F;

        this.handR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
        this.handL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
        this.handR.rotateAngleZ = 0.0F;
        this.handL.rotateAngleY = 0.0F;
        this.handL.rotateAngleZ = 0.0F;


        this.handR.rotateAngleY = 0.0F;
        this.handR.rotateAngleZ = 0.0F;


        if (this.swingProgress > 0.0F) {
            HandSide enumhandside = this.func_217147_a(entityIn);
            ModelRenderer modelrenderer = this.getArmForSide(enumhandside);
            float f1 = this.swingProgress;
            this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;

            if (enumhandside == HandSide.LEFT) {
                this.body.rotateAngleY *= -1.0F;
            }

            this.handR.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.handR.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.handL.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.handL.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.handR.rotateAngleY += this.body.rotateAngleY;
            this.handL.rotateAngleY += this.body.rotateAngleY;
            this.handL.rotateAngleX += this.body.rotateAngleY;
            f1 = 1.0F - this.swingProgress;
            f1 = f1 * f1;
            f1 = f1 * f1;
            f1 = 1.0F - f1;
            float f2 = MathHelper.sin(f1 * (float) Math.PI);
            float f3 = MathHelper.sin(this.swingProgress * (float) Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
            modelrenderer.rotateAngleX = (float) ((double) modelrenderer.rotateAngleX - ((double) f2 * 1.2D + (double) f3));
            modelrenderer.rotateAngleY += this.body.rotateAngleY * 2.0F;
            modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float) Math.PI) * -0.4F;
        }

        this.body.rotateAngleX = 0.0F;

        this.handR.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handL.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handR.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.handL.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }

    protected ModelRenderer getArmForSide(HandSide side) {
        return side == HandSide.LEFT ? this.handL : this.handR;
    }

    public void postRenderArm(float scale, HandSide side) {

    }

    protected HandSide func_217147_a(T p_217147_1_) {
        HandSide handside = p_217147_1_.getPrimaryHand();
        return p_217147_1_.swingingHand == Hand.MAIN_HAND ? handside : handside.opposite();
    }

    @Override
    public void translateHand(HandSide sideIn, MatrixStack matrixStackIn) {
        float f = sideIn == HandSide.RIGHT ? 1.0F : -1.0F;
        ModelRenderer modelrenderer = this.getArmForSide(sideIn);
        modelrenderer.rotationPointX += f;
        modelrenderer.translateRotate(matrixStackIn);
        modelrenderer.rotationPointX -= f;
    }

    @Override
    public ModelRenderer getModelHead() {
        return this.head;
    }

    @OnlyIn(Dist.CLIENT)
    public static enum ArmPose {
        EMPTY,
        ITEM,
        BLOCK,
        BOW_AND_ARROW;
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