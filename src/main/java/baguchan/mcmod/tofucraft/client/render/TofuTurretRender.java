package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.TofuTurretModel;
import baguchan.mcmod.tofucraft.client.render.layer.GlowLayer;
import baguchan.mcmod.tofucraft.entity.TofuTurretEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuTurretRender extends MobRenderer<TofuTurretEntity, TofuTurretModel<TofuTurretEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofuturret/tofuturret.png");

    public TofuTurretRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new TofuTurretModel<>(), 0.45F);
        this.addLayer(new GlowLayer<>(this, new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofuturret/tofuturret_eye.png")));
    }

    protected ResourceLocation getEntityTexture(TofuTurretEntity entity) {
        return TEXTURES;
    }
}