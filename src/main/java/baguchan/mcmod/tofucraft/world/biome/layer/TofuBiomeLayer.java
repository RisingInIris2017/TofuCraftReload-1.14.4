package baguchan.mcmod.tofucraft.world.biome.layer;

import baguchan.mcmod.tofucraft.init.TofuBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public class TofuBiomeLayer implements IC0Transformer {
    private static final int MUSHROOM_FIELDS = Registry.BIOME.getId(Biomes.MUSHROOM_FIELDS);

    public int apply(INoiseRandom context, int value) {

        int i = (value & 3840) >> 8;
        value = value & -3841;
        if (value != MUSHROOM_FIELDS) {
            switch (value) {
                case 1:
                        /*if (i > 0) {
                            return context.random(3) == 0 ? BADLANDS_PLATEAU : WOODED_BADLANDS_PLATEAU;
                        }*/

                    return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.DESERT, context).biome);
                case 2:
                       /* if (i > 0) {
                            return JUNGLE;
                        }*/

                    return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.WARM, context).biome);
                case 3:
                       /* if (i > 0) {
                            return GIANT_TREE_TAIGA;
                        }*/

                    return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.COOL, context).biome);
                case 4:
                    return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.ICY, context).biome);
                default:
                    return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.WARM, context).biome);
            }
        } else {
            return value;
        }

    }

    protected net.minecraftforge.common.BiomeManager.BiomeEntry getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType type, INoiseRandom context) {
        java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> biomeList = TofuBiomes.entryTofuBiome;
        int totalWeight = net.minecraft.util.WeightedRandom.getTotalWeight(biomeList);
        int weight = context.random(totalWeight);
        return (net.minecraftforge.common.BiomeManager.BiomeEntry) net.minecraft.util.WeightedRandom.getRandomItem(biomeList, weight);
    }
}