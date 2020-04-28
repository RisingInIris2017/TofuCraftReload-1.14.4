package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.model.TofunianModel;
import baguchan.mcmod.tofucraft.client.render.layer.EyelidLayer;
import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;

public class TofunianRender extends MobRenderer<TofunianEntity, TofunianModel> {

    public TofunianRender(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new TofunianModel(), 0.5F);
        this.shadowOpaque = 0.4f;
        this.addLayer(new HeldItemLayer<TofunianEntity, TofunianModel>(this) {
            @Override
            public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, TofunianEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                matrixStackIn.push();

                matrixStackIn.translate(0.0D, -1.0D, 0.0D);

                super.render(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);

                matrixStackIn.pop();
            }
        });
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new ElytraLayer<>(this));
        this.addLayer(new EyelidLayer<>(this, new ResourceLocation(TofuCraftCore.MODID, "textures/mob/tofunian/tofunian_eye.png")));
    }

    @Override
    public ResourceLocation getEntityTexture(TofunianEntity entity) {
        String role = "";
        if (entity.getRole() != TofunianEntity.Roles.TOFUNIAN) {
            role = entity.getRole().name().toLowerCase() + "_";
        }
        return new ResourceLocation(TofuCraftCore.MODID + ":textures/mob/tofunian/" + role + "tofunian.png");
    }
}