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
        //max float height is 6
        int alpha = (int) (splash.getFloatHeight() / 6 * 192);

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
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, bottomWidth, alpha, 0.5F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.5F, 0.15625F);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.15625F);
            //back
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, -bottomWidth, alpha, 0.0F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.0F, 0.15625F);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F);
            //right
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.0F, 0.15625F);
            //left
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.15625F);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F);
            //main
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.5F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.0F, 0.15625F);

            //body2
            length = (int) (1 * splash.getFloatHeight() * 6);
            topWidth = 14;
            bottomWidth = 6;
            //front2
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, bottomWidth, alpha, 0.5F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.5F, 0.15625F);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.15625F);
            //back2
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, -bottomWidth, alpha, 0.0F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.0F, 0.15625F);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F);
            //right2
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.0F, 0.15625F);
            //left2
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, -bottomWidth, alpha, 0.5F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, -bottomWidth, -length, bottomWidth, alpha, 0.0F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.15625F);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F);
            //main2
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, topWidth, alpha, 0.0F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, topWidth, alpha, 0.5F, 0.0F);
            this.pointBuilder(posMatrix, normalMatrix, builder, topWidth, 0, -topWidth, alpha, 0.5F, 0.15625F);
            this.pointBuilder(posMatrix, normalMatrix, builder, -topWidth, 0, -topWidth, alpha, 0.0F, 0.15625F);

            matrixStackIn.pop();

        }
        super.render(splash, entityYaw, partialTicks, matrixStackIn, bufferIn, 240);
    }

    public void pointBuilder(Matrix4f posMatrix, Matrix3f normalMatrix, IVertexBuilder builder, int x, int y, int z, int alpha, float u, float v) {
        builder.pos(posMatrix, x, y, z).color(255, 255, 255, alpha).tex(u, v)
                .overlay(0, 32).lightmap(240).normal(normalMatrix, 0, 1, 0).endVertex();
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(SoySplashEntity entity) {
        return null;
    }
}
