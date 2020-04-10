package baguchan.mcmod.tofucraft.client.model;

import baguchan.mcmod.tofucraft.entity.TofuChingerEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class TofuChingerModel<T extends TofuChingerEntity> extends SegmentedModel<T> {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer chin;
    public ModelRenderer backlegR;
    public ModelRenderer mainlegR;
    public ModelRenderer backlegL;
    public ModelRenderer mainlegL;

    public TofuChingerModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.mainlegR = new ModelRenderer(this, 8, 10);
        this.mainlegR.setRotationPoint(-2.4F, -1.0F, -2.5F);
        this.mainlegR.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.backlegL = new ModelRenderer(this, 0, 15);
        this.backlegL.setRotationPoint(2.6F, -1.0F, 0.5F);
        this.backlegL.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.mainlegL = new ModelRenderer(this, 8, 15);
        this.mainlegL.setRotationPoint(2.6F, -1.0F, -2.5F);
        this.mainlegL.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 22.0F, 2.0F);
        this.body.addBox(-3.0F, -2.0F, -4.0F, 6, 2, 6, 0.0F);
        this.chin = new ModelRenderer(this, 24, 13);
        this.chin.setRotationPoint(0.0F, -5.0F, 1.0F);
        this.chin.addBox(-4.0F, 0.0F, -8.0F, 8, 3, 10, 0.0F);
        this.backlegR = new ModelRenderer(this, 0, 10);
        this.backlegR.setRotationPoint(-2.4F, -1.0F, 0.5F);
        this.backlegR.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.head = new ModelRenderer(this, 24, 0);
        this.head.setRotationPoint(0.0F, -5.0F, 1.0F);
        this.head.addBox(-4.0F, -3.0F, -8.0F, 8, 3, 10, 0.0F);
        this.body.addChild(this.mainlegR);
        this.body.addChild(this.backlegL);
        this.body.addChild(this.mainlegL);
        this.body.addChild(this.chin);
        this.body.addChild(this.backlegR);
        this.body.addChild(this.head);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ageInTicks - (float) entityIn.ticksExisted;
        float f1 = entityIn.getEatingAnimationScale(f);
        f1 = f1 * f1;

        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F - 0.8F * f1;
        this.chin.rotateAngleY = netHeadYaw * 0.017453292F;
        this.chin.rotateAngleX = headPitch * 0.017453292F;
        this.mainlegR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.mainlegL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.backlegR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.backlegL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
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