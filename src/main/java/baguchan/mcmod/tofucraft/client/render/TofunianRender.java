package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.TofunianModel;
import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class TofunianRender extends BipedRenderer<TofunianEntity, TofunianModel> {

    public TofunianRender(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new TofunianModel(), 0.5F);
        this.shadowOpaque = 0.4f;
    }

    @Override
    protected ResourceLocation getEntityTexture(TofunianEntity entity) {
        String role = "";
        if (entity.getRole() == TofunianEntity.Roles.GUARD) {
            role = "hunter_";
        }
        return new ResourceLocation(TofuCraftCore.MODID + ":textures/mob/tofunian/" + role + "tofunian.png");
    }
}