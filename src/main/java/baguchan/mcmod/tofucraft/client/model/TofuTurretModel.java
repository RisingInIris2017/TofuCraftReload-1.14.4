package baguchan.mcmod.tofucraft.client.model;

import baguchan.mcmod.tofucraft.entity.TofuTurretEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class TofuTurretModel<T extends TofuTurretEntity> extends EntityModel<T> {
    public RendererModel core;
    public RendererModel spike;
    public RendererModel spike2;
    public RendererModel spike3;
    public RendererModel spike4;

    public TofuTurretModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.spike = new RendererModel(this, 0, 0);
        this.spike.setRotationPoint(-3.0F, 3.0F, -3.5F);
        this.spike.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(spike, -0.7853981633974483F, 0.0F, 0.7853981633974483F);
        this.spike2 = new RendererModel(this, 0, 0);
        this.spike2.setRotationPoint(3.0F, 3.0F, -3.5F);
        this.spike2.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(spike2, -0.7853981633974483F, 0.0F, -0.7853981633974483F);
        this.core = new RendererModel(this, 0, 0);
        this.core.setRotationPoint(0.0F, 19.0F, 0.0F);
        this.core.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
        this.spike4 = new RendererModel(this, 0, 0);
        this.spike4.setRotationPoint(-3.0F, 3.0F, 3.5F);
        this.spike4.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(spike4, 0.7853981633974483F, -0.017453292519943295F, 0.7853981633974483F);
        this.spike3 = new RendererModel(this, 0, 0);
        this.spike3.setRotationPoint(3.0F, 3.0F, 3.5F);
        this.spike3.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(spike3, 0.7853981633974483F, -0.017453292519943295F, -0.7853981633974483F);
        this.core.addChild(this.spike);
        this.core.addChild(this.spike2);
        this.core.addChild(this.spike4);
        this.core.addChild(this.spike3);
    }

    @Override
    public void render(T entity, float f, float f1, float f2, float f3, float f4, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.scalef(1.1F, 1.1F, 1.1F);
        this.core.render(scale);
        GlStateManager.popMatrix();
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