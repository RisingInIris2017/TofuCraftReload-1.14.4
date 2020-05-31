package baguchan.mcmod.tofucraft.world.biome.layer;

import baguchan.mcmod.tofucraft.init.TofuBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum TofuMixRiverLayer implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    private static final int RIVER = Registry.BIOME.getId(TofuBiomes.TOFU_RIVER);

    public int apply(INoiseRandom p_215723_1_, IArea p_215723_2_, IArea p_215723_3_, int p_215723_4_, int p_215723_5_) {
        int i = p_215723_2_.getValue(this.func_215721_a(p_215723_4_), this.func_215722_b(p_215723_5_));
        int j = p_215723_3_.getValue(this.func_215721_a(p_215723_4_), this.func_215722_b(p_215723_5_));
        if (j == RIVER) {
            return Registry.BIOME.getId(TofuBiomes.TOFU_RIVER);
        } else {
            return i;
        }
    }
}