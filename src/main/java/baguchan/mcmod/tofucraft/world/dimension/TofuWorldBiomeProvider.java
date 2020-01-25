package baguchan.mcmod.tofucraft.world.dimension;

import baguchan.mcmod.tofucraft.init.TofuBiomes;
import baguchan.mcmod.tofucraft.world.biome.layer.TofuLayerUtil;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.layer.Layer;

import java.util.Set;

public class TofuWorldBiomeProvider extends BiomeProvider {
    private final World world;
    private final Layer genBiomes;
    public static final Set<Biome> biomes = ImmutableSet.of(TofuBiomes.TOFU_FOREST, TofuBiomes.TOFUZUNDA_SWAMP, TofuBiomes.TOFU_PLAIN, TofuBiomes.ZUNDATOFU_PLAIN, TofuBiomes.TOFU_MOUNTAIN);

    public TofuWorldBiomeProvider(World world, OverworldBiomeProviderSettings settingsProvider) {
        super(biomes);
        this.world = world;
        this.genBiomes = TofuLayerUtil.buildTofuProcedure(settingsProvider.func_226850_a_(), settingsProvider.func_226851_b_(), settingsProvider.getGeneratorSettings());
    }

    public Biome getNoiseBiome(int x, int y, int z) {
        return this.genBiomes.func_215738_a(x, z);
    }

    public boolean hasStructure(Structure<?> structureIn) {
        return this.hasStructureCache.computeIfAbsent(structureIn, (p_205006_1_) -> {
            for (Biome biome : biomes) {
                if (biome.hasStructure(p_205006_1_)) {
                    return true;
                }
            }

            return false;
        });
    }

    public Set<BlockState> getSurfaceBlocks() {
        if (this.topBlocksCache.isEmpty()) {
            for (Biome biome : biomes) {
                this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
            }
        }

        return this.topBlocksCache;
    }
}