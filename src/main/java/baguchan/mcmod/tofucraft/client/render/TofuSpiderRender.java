package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.TofuSpiderModel;
import baguchan.mcmod.tofucraft.entity.TofuSpiderEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuSpiderRender extends MobRenderer<TofuSpiderEntity, TofuSpiderModel<TofuSpiderEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofuspider/tofuspider.png");

    public TofuSpiderRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new TofuSpiderModel<>(), 0.5F);
    }

    @Override
    protected void preRenderCallback(TofuSpiderEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(1.5F, 1.5F, 1.5F);
        super.preRenderCallback(entitylivingbaseIn, matrixStackIn, partialTickTime);
    }

    public ResourceLocation getEntityTexture(TofuSpiderEntity entity) {
        return TEXTURES;
    }
}