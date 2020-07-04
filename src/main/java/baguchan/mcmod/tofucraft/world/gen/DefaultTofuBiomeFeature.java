package baguchan.mcmod.tofucraft.world.gen;

import baguchan.mcmod.tofucraft.block.crop.SoyBeanNetherCropsBlock;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuCaver;
import baguchan.mcmod.tofucraft.init.TofuFeatures;
import com.google.common.collect.Lists;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

public class DefaultTofuBiomeFeature {
    public static final BaseTreeFeatureConfig TOFU_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.ISHITOFU.getDefaultState()), new SimpleBlockStateProvider(TofuBlocks.TOFULEAVES.getDefaultState()), new BlobFoliagePlacer(2, 0, 0, 0, 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().build();
    public static final BaseTreeFeatureConfig ZUNDATOFU_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.ISHITOFU.getDefaultState()), new SimpleBlockStateProvider(TofuBlocks.TOFUZUNDALEAVES.getDefaultState()), new BlobFoliagePlacer(2, 0, 0, 0, 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().build();

    public static final OreFeatureConfig.FillerBlockType TOFU_FILLER = OreFeatureConfig.FillerBlockType.create("tofu", "natural_tofu", new BlockMatcher(TofuBlocks.TOFUTERRAIN));

    public static final BlockClusterFeatureConfig TOFUFLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.TOFUFLOWER.getDefaultState()), new SimpleBlockPlacer())).tries(64).build();
    public static final BlockClusterFeatureConfig LEEK = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.LEEK.getDefaultState()), new SimpleBlockPlacer())).tries(64).build();
    public static final BlockClusterFeatureConfig ZUNDATOFU_MUSHROOM = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.ZUNDATOFU_MUSHROOM.getDefaultState()), new SimpleBlockPlacer())).tries(64).build();
    public static final BlockClusterFeatureConfig NETHER_SOYBEAN = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.SOYBEAN_NETHER.getDefaultState().with(SoyBeanNetherCropsBlock.AGE, 7)), new SimpleBlockPlacer())).tries(64).func_227317_b_().build();



    public static void addCarvers(Biome biomeIn) {
        biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(TofuCaver.TOFU_CAVE, new ProbabilityConfig(0.14285715F)));
        biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(TofuCaver.TOFU_CANYON, new ProbabilityConfig(0.02F)));
    }

    public static void addTofuVariants(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(TOFU_FILLER, TofuBlocks.MINCEDTOFU.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 256))));
    }

    public static void addTofuSedimentDisks(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(new SphereReplaceConfig(TofuBlocks.MINCEDTOFU.getDefaultState(), 6, 2, Lists.newArrayList(TofuBlocks.TOFUTERRAIN.getDefaultState()))).withPlacement(Placement.COUNT_TOP_SOLID.configure(new FrequencyConfig(1))));
    }

    public static void addTofuNormalTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(DefaultTofuBiomeFeature.TOFU_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.02F, 1))));
    }


    public static void addTofuOre(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(TOFU_FILLER, TofuBlocks.ORE_TOFUDIAMOND.getDefaultState(), 9)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 0, 0, 32))));
    }

    public static void addTofuForestTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(DefaultTofuBiomeFeature.TOFU_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));

    }

    public static void addTofuSwampZundaTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(DefaultTofuBiomeFeature.ZUNDATOFU_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(4, 0.1F, 1))));
    }

    public static void addZundaTofuFungi(Biome biomeIn) {
        //biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(TofuFeatures.ZUNDAMUSHROOM_BIG.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withChance(0.33333334F)), TofuFeatures.ZUNDAMUSHROOM_SMALL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
    }

    public static void addTofuBuilding(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(DefaultTofuBiomeFeature.ZUNDATOFU_TREE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(1))));
    }

    public static void addTofuBerry(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, TofuFeatures.TOFUBERRY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 10, 20, 128))));
    }

    public static void addTofuFlowers(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(TOFUFLOWER_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(1))));
    }

    public static void addLeeks(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(LEEK).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(1))));
    }

    public static void addSmallZundaMushroom(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ZUNDATOFU_MUSHROOM).withPlacement(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(2))));
    }

    public static void addNetherSoybean() {
        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {

                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(NETHER_SOYBEAN).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(0.35F, 0, 0, 60))));
            }
        }
    }

}
