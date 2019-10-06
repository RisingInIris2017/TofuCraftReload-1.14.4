package baguchan.mcmod.tofucraft.world.biome.layer;

import baguchan.mcmod.tofucraft.init.TofuBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public class TofuBiomeLayer implements IC0Transformer {
    private static final int TOFURIVER = Registry.BIOME.getId(TofuBiomes.TOFU_RIVER);
    @SuppressWarnings("unchecked")
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry>[] biomes = new java.util.ArrayList[net.minecraftforge.common.BiomeManager.BiomeType.values().length];
    private final OverworldGenSettings settings;

    public TofuBiomeLayer(WorldType p_i48641_1_, OverworldGenSettings p_i48641_2_) {

        for (net.minecraftforge.common.BiomeManager.BiomeType type : net.minecraftforge.common.BiomeManager.BiomeType.values()) {
            com.google.common.collect.ImmutableList<net.minecraftforge.common.BiomeManager.BiomeEntry> biomesToAdd = net.minecraftforge.common.BiomeManager.getBiomes(type);
            int idx = type.ordinal();

            if (biomes[idx] == null)
                biomes[idx] = new java.util.ArrayList<net.minecraftforge.common.BiomeManager.BiomeEntry>();
        }

        if (p_i48641_1_ == WorldType.DEFAULT_1_1) {
            this.settings = null;
        } else {
            this.settings = p_i48641_2_;
        }


    }

    public int apply(INoiseRandom context, int value) {
        if (this.settings != null && this.settings.getBiomeId() >= 0) {
            return this.settings.getBiomeId();
        } else {
            int i = (value & 3840) >> 8;
            value = value & -3841;
         /*   if (!isRiver(value) && value != MUSHROOM_FIELDS) {
                switch(value) {
                    case 1:
                        if (i > 0) {
                            return context.random(3) == 0 ? BADLANDS_PLATEAU : WOODED_BADLANDS_PLATEAU;
                        }

                        return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.DESERT, context).biome);
                    case 2:
                        if (i > 0) {
                            return JUNGLE;
                        }

                        return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.WARM, context).biome);
                    case 3:
                        if (i > 0) {
                            return GIANT_TREE_TAIGA;
                        }

                        return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.COOL, context).biome);
                    case 4:
                        return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.ICY, context).biome);
                    default:
                        return MUSHROOM_FIELDS;
                }
            } else {*/
            return value;

        }
    }


    protected static boolean isRiver(int biomeIn) {
        return biomeIn == TOFURIVER;
    }

}