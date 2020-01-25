package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.TofuChingerModel;
import baguchan.mcmod.tofucraft.entity.TofuChingerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuChingerRender extends MobRenderer<TofuChingerEntity, TofuChingerModel<TofuChingerEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofuchinger.png");

    public TofuChingerRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new TofuChingerModel<>(), 0.3F);
    }


    public void render(TofuChingerEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        this.shadowSize = 0.2F * (float) entity.getChingerSize();
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    protected void preRenderCallback(TofuChingerEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float f1 = (float) entitylivingbaseIn.getChingerSize();
        matrixStackIn.scale(1.0F + 0.2F * f1, 1.0F + 0.2F * f1, 1.0F + 0.2F * f1);
    }

    public ResourceLocation getEntityTexture(TofuChingerEntity entity) {
        return TEXTURES;
    }

}