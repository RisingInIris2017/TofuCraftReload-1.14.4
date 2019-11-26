package baguchan.mcmod.tofucraft.client.render.layer;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class EyelidLayer<T extends Entity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    private final ResourceLocation TEXTURES;

    public EyelidLayer(IEntityRenderer<T, M> p_i50921_1_, ResourceLocation resourceLocation) {
        super(p_i50921_1_);
        TEXTURES = resourceLocation;
    }

    public void render(T entityIn, float p_212842_2_, float p_212842_3_, float partialTick, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        float f3 = entityIn.ticksExisted + partialTick + entityIn.getEntityId();

        //Close Eyelid
        if (0 > MathHelper.sin(f3 * 0.05F) + MathHelper.sin(f3 * 0.13F) + MathHelper.sin(f3 * 0.7F) + 2.55F || entityIn instanceof LivingEntity && ((LivingEntity) entityIn).isSleeping()) {
            if (!entityIn.isInvisible()) {
                this.bindTexture(TEXTURES);

                this.getEntityModel().setLivingAnimations(entityIn, p_212842_2_, p_212842_3_, partialTick);
                this.getEntityModel().render(entityIn, p_212842_2_, p_212842_3_, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }

    public boolean shouldCombineTextures() {
        return true;
    }
}
