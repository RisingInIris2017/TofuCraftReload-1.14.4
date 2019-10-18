package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.entity.projectile.ZundaArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ZundaArrowRender extends ArrowRenderer<ZundaArrowEntity> {
    public static final ResourceLocation RES_ARROW = new ResourceLocation(TofuCraftCore.MODID, "textures/entity/zundaarrow.png");

    public ZundaArrowRender(EntityRendererManager manager) {
        super(manager);
    }

    protected ResourceLocation getEntityTexture(ZundaArrowEntity entity) {
        return RES_ARROW;
    }
}