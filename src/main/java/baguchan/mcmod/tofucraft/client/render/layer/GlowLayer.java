package baguchan.mcmod.tofucraft.client.render.layer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GlowLayer<T extends Entity, M extends EntityModel<T>> extends AbstractEyesLayer<T, M> {
    private final ResourceLocation TEXTURES;

    public GlowLayer(IEntityRenderer<T, M> p_i50921_1_, ResourceLocation resourceLocation) {
        super(p_i50921_1_);
        TEXTURES = resourceLocation;
    }

    public RenderType getRenderType() {
        return RenderType.getEyes(TEXTURES);
    }
}
