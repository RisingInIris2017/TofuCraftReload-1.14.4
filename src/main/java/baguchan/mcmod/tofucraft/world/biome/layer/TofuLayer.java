package baguchan.mcmod.tofucraft.world.biome.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum TofuLayer implements IAreaTransformer0 {
    INSTANCE;

    protected static final int OCEAN = Registry.BIOME.getId(Biomes.OCEAN);

    public int apply(INoiseRandom p_215735_1_, int p_215735_2_, int p_215735_3_) {
        //int biome = Registry.BIOME.getId(TofuWorldBiomeProvider.biomes[p_215735_1_.random(TofuWorldBiomeProvider.biomes.length)]);

        return 1;

    }
}