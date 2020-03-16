package baguchan.mcmod.tofucraft.client.model;

import baguchan.mcmod.tofucraft.entity.MorijioEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MorijioModel<T extends MorijioEntity> extends SegmentedModel<T> {
    public ModelRenderer base;
    public ModelRenderer shape2;
    public ModelRenderer shape3;

    public MorijioModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.base = new ModelRenderer(this, 0, 0);
        this.base.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.base.addBox(-3.0F, 0.0F, -3.0F, 6, 2, 6, 0.0F);
        this.shape2 = new ModelRenderer(this, 0, 8);
        this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape2.addBox(-2.0F, -3.0F, -2.0F, 4, 2, 4, 0.0F);
        this.shape3 = new ModelRenderer(this, 0, 14);
        this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape3.addBox(-2.5F, -1.0F, -2.5F, 5, 1, 5, 0.0F);
        this.base.addChild(this.shape2);
        this.base.addChild(this.shape3);
    }

    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.base);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}