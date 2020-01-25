package baguchan.mcmod.tofucraft.world.gen;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;

public class DefaultTofuBiomeFeature {
    public static final TreeFeatureConfig tofutree = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.ISHITOFU.getDefaultState()), new SimpleBlockStateProvider(TofuBlocks.TOFULEAVES.getDefaultState()), new SpruceFoliagePlacer(2, 1))).baseHeight(6).func_227354_b_(3).func_227356_e_(1).func_227357_f_(1).func_227359_h_(2).func_227352_a_().setSapling((net.minecraftforge.common.IPlantable) TofuBlocks.TOFUSAPLING).build();
    public static final TreeFeatureConfig tofuZundaTree = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(TofuBlocks.ISHITOFU.getDefaultState()), new SimpleBlockStateProvider(TofuBlocks.TOFUZUNDALEAVES.getDefaultState()), new SpruceFoliagePlacer(2, 1))).baseHeight(6).func_227354_b_(3).func_227356_e_(1).func_227357_f_(1).func_227359_h_(2).func_227352_a_().setSapling((net.minecraftforge.common.IPlantable) TofuBlocks.ZUNDATOFUSAPLING).build();



    public static final OreFeatureConfig.FillerBlockType TOFU_FILLER = OreFeatureConfig.FillerBlockType.create("tofu", "natural_tofu", new BlockMatcher(TofuBlocks.TOFUTERRAIN));

    /*public static void addStructure(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(TofuFeatures.TOFUVILLAGE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
        biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(TofuFeatures.TOFUCASTLE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));

    }*/

    public static void addCarvers(Biome biomeIn) {
     /*   biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(TofuCaver.TOFU_CAVE, new ProbabilityConfig(0.14285715F)));
        biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(TofuCaver.TOFU_CANYON, new ProbabilityConfig(0.02F)));
    */
    }

    public static void addTofuVariants(Biome biomeIn) {
        //biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(TOFU_FILLER, TofuBlocks.MINCEDTOFU.getDefaultState(), 33), Placement.COUNT_RANGE, new CountRangeConfig(8, 0, 0, 256)));
    }

    public static void addTofuSedimentDisks(Biome biomeIn) {
        //biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.DISK, new SphereReplaceConfig(TofuBlocks.MINCEDTOFU.getDefaultState(), 6, 2, Lists.newArrayList(TofuBlocks.TOFUTERRAIN.getDefaultState())), Placement.COUNT_TOP_SOLID, new FrequencyConfig(1)));
    }

    public static void addTofuNormalTrees(Biome biomeIn) {
        //biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(TofuFeatures.TOFUTREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(0, 0.02F, 1)));
    }

    public static void addBigZundaMushroom(Biome biomeIn) {
     /*   biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(TofuFeatures.ZUNDAMUSHROOM_SMALL, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP, new FrequencyConfig(6)));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(TofuFeatures.ZUNDAMUSHROOM_BIG, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP, new FrequencyConfig(6)));
    */
    }

    public static void addTofuOre(Biome biomeIn) {
        /*biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(
                TofuFeatures.TOFUORE, new TofuOreConfig(TofuBlocks.ORE_TOFUDIAMOND.getDefaultState(), 9),
                Placement.COUNT_RANGE, new CountRangeConfig(5, 0, 0, 30)
        ));*/
    }

    /*public static void addTofuForestTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(TofuFeatures.TOFUTREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(10, 0.1F, 1)));
    }

    public static void addTofuSwampZundaTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(TofuFeatures.ZUNDATOFUTREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(4, 0.1F, 1)));
    }

    public static void addTofuBerry(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(TofuFeatures.TOFUBERRY, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_RANGE, new CountRangeConfig(20, 10, 20, 128)));
    }

    public static void addTofuFlowers(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(TofuFeatures.TOFUFLOWER, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_32, new FrequencyConfig(1)));
    }

    public static void addLeeks(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.BUSH, new BushConfig(TofuBlocks.LEEK.getDefaultState()), Placement.COUNT_CHANCE_HEIGHTMAP, new HeightWithChanceConfig(3, 0.15F)));
    }

    public static void addManyTofuFlowers(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(TofuFeatures.TOFUFLOWER, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_32, new FrequencyConfig(3)));
    }*/
}
