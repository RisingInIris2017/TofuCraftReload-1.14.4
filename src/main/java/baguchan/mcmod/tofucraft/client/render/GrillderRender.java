package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.GrillderModel;
import baguchan.mcmod.tofucraft.entity.GrillderEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GrillderRender extends MobRenderer<GrillderEntity, GrillderModel<GrillderEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/grillder.png");

    public GrillderRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GrillderModel<>(), 0.3F);
    }

    public float handleRotationFloat(GrillderEntity livingBase, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.flap);
        float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.flapSpeed);
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    protected ResourceLocation getEntityTexture(GrillderEntity entity) {
        return TEXTURES;
    }

}