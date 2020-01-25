package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.TofunianModel;
import baguchan.mcmod.tofucraft.client.render.layer.EyelidLayer;
import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;

public class TofunianRender extends MobRenderer<TofunianEntity, TofunianModel> {

    public TofunianRender(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new TofunianModel(), 0.5F);
        this.shadowOpaque = 0.4f;
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new ElytraLayer<>(this));
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new EyelidLayer<>(this, new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofunian/tofunian_eye.png")));
    }

    @Override
    public ResourceLocation getEntityTexture(TofunianEntity entity) {
        String role = "";
        if (entity.getRole() != TofunianEntity.Roles.TOFUNIAN) {
            role = entity.getRole().name().toLowerCase() + "_";
        }
        return new ResourceLocation(TofuCraftCore.MODID + ":textures/mob/tofunian/" + role + "tofunian.png");
    }
}