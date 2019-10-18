package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.entity.TofuFishEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CodModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuFishRender extends MobRenderer<TofuFishEntity, CodModel<TofuFishEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofufish.png");

    public TofuFishRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CodModel<>(), 0.3F);
    }

    protected ResourceLocation getEntityTexture(TofuFishEntity entity) {
        return TEXTURES;
    }

    protected void applyRotations(TofuFishEntity entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
        GlStateManager.rotatef(f, 0.0F, 1.0F, 0.0F);
        if (!entityLiving.isInWater()) {
            GlStateManager.translatef(0.1F, 0.1F, -0.1F);
            GlStateManager.rotatef(90.0F, 0.0F, 0.0F, 1.0F);
        }
    }
}