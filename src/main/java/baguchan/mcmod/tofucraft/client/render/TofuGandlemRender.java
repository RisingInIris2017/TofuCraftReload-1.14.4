package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.TofuGandlemModel;
import baguchan.mcmod.tofucraft.client.render.layer.GlowLayer;
import baguchan.mcmod.tofucraft.entity.TofuGandlemEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuGandlemRender extends MobRenderer<TofuGandlemEntity, TofuGandlemModel<TofuGandlemEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofugandlem/tofugandlem.png");
    private static final ResourceLocation SHOOTING_TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofugandlem/tofugandlem_shooting.png");


    public TofuGandlemRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new TofuGandlemModel<>(), 0.5F);
        this.addLayer(new GlowLayer<>(this, new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofugandlem/tofugandlem_eye.png")));
    }

    @Override
    protected void applyRotations(TofuGandlemEntity entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);

        float tick = entityLiving.ticksExisted + partialTicks;

        GlStateManager.translatef(0F, -0.1F - MathHelper.sin(tick * 0.12F) * 0.1F, 0F);
    }

    protected ResourceLocation getEntityTexture(TofuGandlemEntity entity) {
        if (entity.isShooting()) {
            return SHOOTING_TEXTURES;
        } else {
            return TEXTURES;
        }
    }
}