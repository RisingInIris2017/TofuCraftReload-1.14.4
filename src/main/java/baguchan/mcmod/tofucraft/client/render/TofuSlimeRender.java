package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.entity.TofuSlimeEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.ResourceLocation;

public class TofuSlimeRender extends SlimeRenderer {
    private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofuslime.png");

    public TofuSlimeRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    protected void applyRotations(SlimeEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        if (entityLiving instanceof TofuSlimeEntity && ((TofuSlimeEntity) entityLiving).isWeak()) {
            rotationYaw += (float) (Math.cos((double) entityLiving.ticksExisted * 3.25D) * Math.PI * 0.25D);
        }

        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(SlimeEntity entity) {
        return SLIME_TEXTURES;
    }
}
