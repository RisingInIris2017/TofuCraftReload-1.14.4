package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.MorijioModel;
import baguchan.mcmod.tofucraft.entity.MorijioEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MorijioRender extends EntityRenderer<MorijioEntity> {
    private static final ResourceLocation MORIJIO = new ResourceLocation(TofuCraftCore.MODID, "textures/entity/morijio.png");
    private final MorijioModel<MorijioEntity> model = new MorijioModel<>();

    public MorijioRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    public void render(MorijioEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {


        matrixStackIn.push();
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        matrixStackIn.translate(0.0F, -1.5F, 0.0F);

        this.model.render(entityIn, 0, 0.0F, 0.0F, entityIn.rotationYaw, entityIn.rotationPitch);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(MORIJIO));
        this.model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.DEFAULT_LIGHT, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

    }

    public ResourceLocation getEntityTexture(MorijioEntity entity) {
        return MORIJIO;
    }
}