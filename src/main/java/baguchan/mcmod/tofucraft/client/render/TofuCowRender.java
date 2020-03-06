package baguchan.mcmod.tofucraft.client.render;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.render.layer.TofuCowPlantLayer;
import baguchan.mcmod.tofucraft.entity.TofuCowEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuCowRender extends MobRenderer<TofuCowEntity, CowModel<TofuCowEntity>> {
    private static final ResourceLocation COW_TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/cow/tofucow.png");
    private static final ResourceLocation ZUNDACOW_TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "textures/mob/cow/tofuzundacow.png");


    public TofuCowRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CowModel<>(), 0.7F);
        this.addLayer(new TofuCowPlantLayer<>(this));
    }

    public ResourceLocation getEntityTexture(TofuCowEntity entity) {
        if (entity.getTofuCowType() == TofuCowEntity.Type.ZUNDA) {
            return ZUNDACOW_TEXTURES;
        } else {
            return COW_TEXTURES;
        }
    }
}