package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.TofuMindModel;
import baguchan.mcmod.tofucraft.client.render.layer.GlowLayer;
import baguchan.mcmod.tofucraft.entity.TofuMindEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuMindRender extends MobRenderer<TofuMindEntity, TofuMindModel<TofuMindEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofumind/tofumind.png");

    public TofuMindRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new TofuMindModel<>(), 0.5F);
        this.addLayer(new HeldItemLayer<TofuMindEntity, TofuMindModel<TofuMindEntity>>(this));
        this.addLayer(new GlowLayer<>(this, new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofumind/tofumind_eye.png")));
    }

    @Override
    protected void applyRotations(TofuMindEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);

        float tick = entityLiving.ticksExisted + partialTicks;

        matrixStackIn.translate(0F, 0.05F - MathHelper.sin(tick * 0.12F) * 0.1F, 0F);
    }

    public ResourceLocation getEntityTexture(TofuMindEntity entity) {
        return TEXTURES;
    }
}