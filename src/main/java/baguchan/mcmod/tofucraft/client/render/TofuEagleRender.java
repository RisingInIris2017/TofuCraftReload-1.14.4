package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.TofuEagleModel;
import baguchan.mcmod.tofucraft.entity.TofuEagleEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class TofuEagleRender extends MobRenderer<TofuEagleEntity, TofuEagleModel<TofuEagleEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofueagle.png");

    public TofuEagleRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new TofuEagleModel<>(), 0.3F);
    }

    protected void preRenderCallback(TofuEagleEntity entitylivingbaseIn, float partialTickTime) {
        float f1 = (float) entitylivingbaseIn.getRenderScale();
        GlStateManager.scalef(f1, f1, f1);
    }

    protected ResourceLocation getEntityTexture(TofuEagleEntity entity) {
        return TEXTURES;
    }

}