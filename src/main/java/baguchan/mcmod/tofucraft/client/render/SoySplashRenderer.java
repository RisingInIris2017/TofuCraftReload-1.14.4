package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.entity.SoySplashEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class SoySplashRenderer extends EntityRenderer<SoySplashEntity> {
    private static final ResourceLocation SPLASH_TEXTURE = new ResourceLocation(TofuCraftCore.MODID, "textures/blocks/soymilk_flow.png");

    public SoySplashRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(SoySplashEntity splash, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        int alpha = (int) (splash.getFloatHeight() / splash.getMaxHeight() * 200);

        if (0 < alpha) {
            matrixStackIn.push();
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(-180.0F));
            matrixStackIn.scale(0.0625F, 0.0625F, 0.0625F);

            IVertexBuilder builder = bufferIn.getBuffer(RenderType.getEntityTranslucentCull(SPLASH_TEXTURE));
            MatrixStack.Entry entry = matrixStackIn.getLast();
            Matrix4f posMatrix = entry.getMatrix();
            Matrix3f normalMatrix = entry.getNormal();

            //body
            int length = (int) (1 * splash.getFloatHeight() * 16);
            int topWidth = 8;
            int bottomWidth = 4;
            //front
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, bottomWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.15625F, packedLightIn);
            //back
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, -bottomWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.0F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            //right
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.0F, 0.15625F, packedLightIn);
            //left
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            //main
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.0F, 0.15625F, packedLightIn);

            //body2
            length = (int) (1 * splash.getFloatHeight() * 10);
            topWidth = 12;
            bottomWidth = 6;
            //front2
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, bottomWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.15625F, packedLightIn);
            //back2
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, -bottomWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.0F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            //right2
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.0F, 0.15625F, packedLightIn);
            //left2
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            //main2
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.0F, 0.15625F, packedLightIn);

            //body3
            length = (int) (1 * splash.getFloatHeight() * 6);
            topWidth = 16;
            bottomWidth = 8;
            //front3
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, bottomWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.15625F, packedLightIn);
            //back3
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, -bottomWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.0F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            //right3
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.0F, 0.15625F, packedLightIn);
            //left3
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            //main3
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.5F, 0.0F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F, packedLightIn);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.0F, 0.15625F, packedLightIn);

            matrixStackIn.pop();

        }
        super.render(splash, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public void pointBuilder(Matrix4f posMatrix, Matrix3f normalMatrix, IVertexBuilder builder, int x, int y, int z, int alpha, float u, float v, int packedLightIn) {
        builder.pos(posMatrix, x, y, z).color(255, 255, 255, alpha).tex(u, v)
                .overlay(0, 32).lightmap(packedLightIn).normal(normalMatrix, 0, 1, 0).endVertex();
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(SoySplashEntity entity) {
        return null;
    }
}
