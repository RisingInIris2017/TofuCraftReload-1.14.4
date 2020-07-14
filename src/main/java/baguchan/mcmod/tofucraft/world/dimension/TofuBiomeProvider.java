package baguchan.mcmod.tofucraft.world.dimension;

import baguchan.mcmod.tofucraft.init.TofuBiomes;
import baguchan.mcmod.tofucraft.world.biome.layer.TofuLayerUtil;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;

import java.util.List;

public class TofuBiomeProvider extends BiomeProvider {
    public static final Codec<TofuBiomeProvider> tofuBiomeProviderCodec = RecordCodecBuilder.create((instance) ->
            instance.group(Codec.LONG.fieldOf("seed").stable().forGetter((obj) -> obj.seed))
                    .apply(instance, instance.stable(TofuBiomeProvider::new)));

    private final Layer genBiomes;
    private final long seed;

    protected static final List<Biome> biomes = Lists.newArrayList(TofuBiomes.TOFU_FOREST, TofuBiomes.TOFUZUNDA_SWAMP, TofuBiomes.TOFU_PLAIN, TofuBiomes.ZUNDATOFU_PLAIN, TofuBiomes.ZUNDATOFU_FUNGIFOREST, TofuBiomes.TOFU_BUILDING, TofuBiomes.TOFU_MOUNTAIN, TofuBiomes.TOFU_RIVER);

    public TofuBiomeProvider(long seed) {
        super(biomes);
        this.seed = seed;
        getBiomesToSpawnIn().clear();
        getBiomesToSpawnIn().add(TofuBiomes.TOFU_FOREST);
        getBiomesToSpawnIn().add(TofuBiomes.TOFUZUNDA_SWAMP);
        getBiomesToSpawnIn().add(TofuBiomes.TOFU_PLAIN);

        genBiomes = TofuLayerUtil.buildTofuProcedure(seed);
    }


    @Override
    protected Codec<? extends BiomeProvider> func_230319_a_() {
        return tofuBiomeProviderCodec;
    }

    @Override
    public BiomeProvider func_230320_a_(long l) {
        return new TofuBiomeProvider(l);
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return this.genBiomes.func_215738_a(x, z);
    }
}
