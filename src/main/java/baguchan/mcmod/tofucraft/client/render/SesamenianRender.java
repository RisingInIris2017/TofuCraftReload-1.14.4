package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.SesamenianModel;
import baguchan.mcmod.tofucraft.entity.SesamenianEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;

public class SesamenianRender extends MobRenderer<SesamenianEntity, SesamenianModel> {

    public static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID + ":textures/mob/sesamenian/sesamenian.png");

    public SesamenianRender(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new SesamenianModel(), 0.5F);
        this.shadowOpaque = 0.4f;
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new ElytraLayer<>(this));
        this.addLayer(new HeldItemLayer<>(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(SesamenianEntity entity) {

        return TEXTURES;
    }
}