package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.ResourceLocation;

public class TofuSlimeRender extends SlimeRenderer {
    private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofuslime.png");

    public TofuSlimeRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    protected ResourceLocation getEntityTexture(SlimeEntity entity) {
        return SLIME_TEXTURES;
    }
}
