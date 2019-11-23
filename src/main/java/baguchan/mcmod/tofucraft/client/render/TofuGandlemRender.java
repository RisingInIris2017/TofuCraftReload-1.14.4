package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.TofuGandlemModel;
import baguchan.mcmod.tofucraft.client.render.layer.GlowLayer;
import baguchan.mcmod.tofucraft.entity.TofuGandlemEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuGandlemRender extends MobRenderer<TofuGandlemEntity, TofuGandlemModel<TofuGandlemEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofugandlem/tofugandlem.png");

    public TofuGandlemRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new TofuGandlemModel<>(), 0.5F);
        this.addLayer(new GlowLayer<>(this, new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofugandlem/tofugandlem_eye.png")));
    }

    protected ResourceLocation getEntityTexture(TofuGandlemEntity entity) {
        return TEXTURES;
    }
}