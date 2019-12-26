package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.TofuMindModel;
import baguchan.mcmod.tofucraft.client.render.layer.GlowLayer;
import baguchan.mcmod.tofucraft.entity.TofuMindEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuMindRender extends MobRenderer<TofuMindEntity, TofuMindModel<TofuMindEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofumind/tofumind.png");

    public TofuMindRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new TofuMindModel<>(), 0.5F);
        this.addLayer(new HeldItemLayer<TofuMindEntity, TofuMindModel<TofuMindEntity>>(this) {
            @Override
            public void render(TofuMindEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
                GlStateManager.pushMatrix();
                GlStateManager.translated(0.0F, 0.4F, 0.0F);
                super.render(entityIn, p_212842_2_, p_212842_3_, p_212842_4_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
                GlStateManager.popMatrix();
            }
        });
        this.addLayer(new GlowLayer<>(this, new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofumind/tofumind_eye.png")));
    }

    @Override
    protected void applyRotations(TofuMindEntity entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);

        float tick = entityLiving.ticksExisted + partialTicks;

        GlStateManager.translatef(0F, -0.1F - MathHelper.sin(tick * 0.12F) * 0.1F, 0F);

    }

    protected ResourceLocation getEntityTexture(TofuMindEntity entity) {
        return TEXTURES;
    }
}