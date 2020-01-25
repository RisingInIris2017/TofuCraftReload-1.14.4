package baguchan.mcmod.tofucraft.client.render.layer;

import baguchan.mcmod.tofucraft.entity.TofuCreeperEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuCreeperChargeLayer extends EnergyLayer<TofuCreeperEntity, CreeperModel<TofuCreeperEntity>> {
    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final CreeperModel<TofuCreeperEntity> creeperModel = new CreeperModel<>(2.0F);

    public TofuCreeperChargeLayer(IEntityRenderer<TofuCreeperEntity, CreeperModel<TofuCreeperEntity>> p_i50947_1_) {
        super(p_i50947_1_);
    }

    protected float func_225634_a_(float p_225634_1_) {
        return p_225634_1_ * 0.01F;
    }

    protected ResourceLocation func_225633_a_() {
        return LIGHTNING_TEXTURE;
    }

    protected EntityModel<TofuCreeperEntity> func_225635_b_() {
        return this.creeperModel;
    }
}