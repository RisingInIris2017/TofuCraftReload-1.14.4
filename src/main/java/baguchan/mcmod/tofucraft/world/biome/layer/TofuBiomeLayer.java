package baguchan.mcmod.tofucraft.world.biome.layer;

import baguchan.mcmod.tofucraft.init.TofuBiomes;
import baguchan.mcmod.tofucraft.world.dimension.TofuWorldBiomeProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public class TofuBiomeLayer implements IC0Transformer {
    private static final int BIRCH_FOREST = Registry.BIOME.getId(Biomes.BIRCH_FOREST);
    private static final int DESERT = Registry.BIOME.getId(Biomes.DESERT);
    private static final int MOUNTAINS = Registry.BIOME.getId(Biomes.MOUNTAINS);
    private static final int FOREST = Registry.BIOME.getId(Biomes.FOREST);
    private static final int SNOWY_TUNDRA = Registry.BIOME.getId(Biomes.SNOWY_TUNDRA);
    private static final int JUNGLE = Registry.BIOME.getId(Biomes.JUNGLE);
    private static final int BADLANDS_PLATEAU = Registry.BIOME.getId(Biomes.BADLANDS_PLATEAU);
    private static final int WOODED_BADLANDS_PLATEAU = Registry.BIOME.getId(Biomes.WOODED_BADLANDS_PLATEAU);
    private static final int MUSHROOM_FIELDS = Registry.BIOME.getId(Biomes.MUSHROOM_FIELDS);
    private static final int PLAINS = Registry.BIOME.getId(Biomes.PLAINS);
    private static final int GIANT_TREE_TAIGA = Registry.BIOME.getId(Biomes.GIANT_TREE_TAIGA);
    private static final int DARK_FOREST = Registry.BIOME.getId(Biomes.DARK_FOREST);
    private static final int SAVANNA = Registry.BIOME.getId(Biomes.SAVANNA);
    private static final int SWAMP = Registry.BIOME.getId(Biomes.SWAMP);
    private static final int TAIGA = Registry.BIOME.getId(Biomes.TAIGA);
    private static final int SNOWY_TAIGA = Registry.BIOME.getId(Biomes.SNOWY_TAIGA);
    private final int field_227472_v_;
    @SuppressWarnings("unchecked")
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry>[] biomes = new java.util.ArrayList[net.minecraftforge.common.BiomeManager.BiomeType.values().length];

    public TofuBiomeLayer(WorldType p_i225882_1_, int p_i225882_2_) {
        for (net.minecraftforge.common.BiomeManager.BiomeType type : net.minecraftforge.common.BiomeManager.BiomeType.values()) {
            com.google.common.collect.ImmutableList<net.minecraftforge.common.BiomeManager.BiomeEntry> biomesToAdd = net.minecraftforge.common.BiomeManager.getBiomes(type);
            int idx = type.ordinal();

            if (biomes[idx] == null)
                biomes[idx] = new java.util.ArrayList<net.minecraftforge.common.BiomeManager.BiomeEntry>();
            if (biomesToAdd != null) biomes[idx].addAll(biomesToAdd);
        }

        int desertIdx = net.minecraftforge.common.BiomeManager.BiomeType.DESERT.ordinal();

        TofuWorldBiomeProvider.biomes.forEach((biome -> {
            biomes[desertIdx].add(new net.minecraftforge.common.BiomeManager.BiomeEntry(biome, 10));
        }));

        this.field_227472_v_ = p_i225882_2_;
    }

    public int apply(INoiseRandom context, int value) {
        if (this.field_227472_v_ >= 0) {
            return this.field_227472_v_;
        } else {
            int i = (value & 3840) >> 8;
            value = value & -3841;
            if (!TofuLayerUtil.isRiver(value) && value != MUSHROOM_FIELDS) {
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
                        return Registry.BIOME.getId(TofuBiomes.TOFU_RIVER);
                }
            } else {
                return value;
            }
        }
    }

    protected net.minecraftforge.common.BiomeManager.BiomeEntry getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType type, INoiseRandom context) {
        java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> biomeList = biomes[type.ordinal()];

        return (net.minecraftforge.common.BiomeManager.BiomeEntry) net.minecraft.util.WeightedRandom.getRandomItem(biomeList, context.random(12 / 10) * 10);
    }
}