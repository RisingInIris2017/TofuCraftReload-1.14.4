package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.TofuLizardModel;
import baguchan.mcmod.tofucraft.entity.TofuLizardEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuLizardRender extends MobRenderer<TofuLizardEntity, TofuLizardModel<TofuLizardEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofulizard.png");

    public TofuLizardRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new TofuLizardModel<>(), 0.5F);
    }

    protected void preRenderCallback(TofuLizardEntity entitylivingbaseIn, float partialTickTime) {
        float f1 = (float) entitylivingbaseIn.getRenderScale();
        GlStateManager.scalef(f1, f1, f1);
    }

    protected ResourceLocation getEntityTexture(TofuLizardEntity entity) {
        return TEXTURES;
    }

}