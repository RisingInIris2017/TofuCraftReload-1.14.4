package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.ZundamaModel;
import baguchan.mcmod.tofucraft.entity.multipart.ZunsyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ZunsyRender extends MobRenderer<ZunsyEntity, ZundamaModel<ZunsyEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/zunsy.png");

    public ZunsyRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ZundamaModel<>(), 0.3F);
        //this.addLayer(new GlowLayer<>(this, new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofugandlem/tofugandlem_eye.png")));
    }

    @Override
    protected void applyRotations(ZunsyEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);

        float tick = entityLiving.ticksExisted + partialTicks;

        matrixStackIn.translate(0F, 0.05F - MathHelper.sin(tick * 0.12F) * 0.1F, 0F);
    }

    public ResourceLocation getEntityTexture(ZunsyEntity entity) {
        return TEXTURES;
    }
}
