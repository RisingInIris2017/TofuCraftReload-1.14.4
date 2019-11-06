package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.TofuSpiderModel;
import baguchan.mcmod.tofucraft.entity.TofuSpiderEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuSpiderRender extends MobRenderer<TofuSpiderEntity, TofuSpiderModel<TofuSpiderEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofuspider/tofuspider.png");

    public TofuSpiderRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new TofuSpiderModel<>(), 0.5F);
    }

    protected void preRenderCallback(TofuSpiderEntity entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scalef(1.5F, 1.5F, 1.5F);
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }

    protected ResourceLocation getEntityTexture(TofuSpiderEntity entity) {
        return TEXTURES;
    }
}