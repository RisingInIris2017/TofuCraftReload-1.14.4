package baguchan.mcmod.tofucraft.world.gen.feature.structure;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.world.gen.feature.structure.processor.StructureVoidProcessor;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.template.StructureProcessor;

@SuppressWarnings("deprecation")
public class TofuVillagePools {
    public static void init() {
    }

    static {
        ImmutableList<StructureProcessor> townCenterProcessors = ImmutableList.of(new StructureVoidProcessor());
        ImmutableList<StructureProcessor> buildingProcessors = ImmutableList.of(new StructureVoidProcessor());

        JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation(TofuCraftCore.MODID, "tofu_village/town_centers"), new ResourceLocation("empty"), ImmutableList.of(new com.mojang.datafixers.util.Pair<>(new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/town_centers/fountain", townCenterProcessors), 1)), JigsawPattern.PlacementBehaviour.RIGID));
        JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation(TofuCraftCore.MODID, "tofu_village/house"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(
                                new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/house/tofu_smith", buildingProcessors),
                                2),
                        new Pair<>(
                                new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/house/soyworker_house", buildingProcessors),
                                2),
                        new Pair<>(
                                new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/house/tofunian_towerhouse", buildingProcessors),
                                3),
                        new Pair<>(
                                new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/house/hut_1", buildingProcessors),
                                2),
                        new Pair<>(
                                new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/house/hut_2", buildingProcessors),
                                3),
                        new Pair<>(
                                new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/house/small_farm_1", buildingProcessors),
                                2)),
                JigsawPattern.PlacementBehaviour.RIGID));

    /*    JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation(TofuCraftCore.MODID + ":tofu_village/decor"), new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(
                                new FeatureJigsawPiece(new ConfiguredFeature<>(TofuFeatures.TOFUTREE, IFeatureConfig.NO_FEATURE_CONFIG)), 1),
                        new Pair<>(new FeatureJigsawPiece(new ConfiguredFeature<>(TofuFeatures.TOFUFLOWER, IFeatureConfig.NO_FEATURE_CONFIG)), 1),
                        Pair.of(EmptyJigsawPiece.INSTANCE, 2)),
                JigsawPattern.PlacementBehaviour.RIGID));*/

        JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation(TofuCraftCore.MODID, "tofu_village/streets"),
                new ResourceLocation(TofuCraftCore.MODID, "tofu_village/terminators"),
                ImmutableList.of(
                        new Pair<>(
                                new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/streets/corner_1"),
                                2),
                        new Pair<>(
                                new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/streets/corner_2"),
                                4),
                        new Pair<>(
                                new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/streets/corner_3"),
                                6),
                        new Pair<>(
                                new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/streets/crossroad_1"),
                                5),
                        new Pair<>(
                                new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/streets/crossroad_2"),
                                2),
                        new Pair<>(
                                new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/streets/crossroad_3"),
                                3)
                ),
                JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));

        JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation(TofuCraftCore.MODID, "tofu_village/terminators"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(
                                new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/terminators/terminator_01"), 1)), JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));

        JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation(TofuCraftCore.MODID, "tofu_village/tofunian"),
                new ResourceLocation("empty"),
                ImmutableList.of(new Pair<>(
                        new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/tofunian/tofunian_child"),
                        1), new Pair<>(
                        new SingleJigsawPiece(TofuCraftCore.MODID + ":tofu_village/tofunian/tofunian"),
                        4)), JigsawPattern.PlacementBehaviour.RIGID));

    }
}