package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.world.gen.feature.SurfaceTofuTemplateFeature;
import baguchan.mcmod.tofucraft.world.gen.feature.TofuBerryFeature;
import baguchan.mcmod.tofucraft.world.gen.feature.TofuTreeFeature;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuFeatures {

    public static final Feature<NoFeatureConfig> ZUNDAMUSHROOM_SMALL = new SurfaceTofuTemplateFeature(NoFeatureConfig.field_236558_a_, 7, 7, new ResourceLocation[]{
            new ResourceLocation(TofuCraftCore.MODID, "mushroom/mushroom_zunda_small"),
            new ResourceLocation(TofuCraftCore.MODID, "mushroom/mushroom_zunda_cache_small")
    });

    public static final Feature<NoFeatureConfig> ZUNDAMUSHROOM_BIG = new SurfaceTofuTemplateFeature(NoFeatureConfig.field_236558_a_, 9, 9, new ResourceLocation[]{
            new ResourceLocation(TofuCraftCore.MODID, "mushroom/mushroom_zunda_big"),
            new ResourceLocation(TofuCraftCore.MODID, "mushroom/mushroom_zunda_big2")
    });

    public static final Feature<NoFeatureConfig> TOFU_BUILDING = new SurfaceTofuTemplateFeature(NoFeatureConfig.field_236558_a_, 4, 4, new ResourceLocation[]{
            new ResourceLocation(TofuCraftCore.MODID, "tofu/tofu_1"),
            new ResourceLocation(TofuCraftCore.MODID, "tofu/tofu_2"),
            new ResourceLocation(TofuCraftCore.MODID, "tofu/tofu_3"),
            new ResourceLocation(TofuCraftCore.MODID, "tofu/tofu_4"),
            new ResourceLocation(TofuCraftCore.MODID, "tofu/tofu_5")
    });

    public static final Feature<NoFeatureConfig> TOFUBERRY = new TofuBerryFeature(NoFeatureConfig.field_236558_a_);

    public static final Feature<BaseTreeFeatureConfig> TOFUTREE = new TofuTreeFeature(BaseTreeFeatureConfig.field_236676_a_);

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Feature<?>> registry) {
        ;
        registry.getRegistry().register(TOFU_BUILDING.setRegistryName("tofu_building"));
        registry.getRegistry().register(ZUNDAMUSHROOM_SMALL.setRegistryName("zundamushroom_small"));
        registry.getRegistry().register(ZUNDAMUSHROOM_BIG.setRegistryName("zundamushroom_big"));
        registry.getRegistry().register(TOFUBERRY.setRegistryName("tofuberry"));
        registry.getRegistry().register(TOFUTREE.setRegistryName("tofutree"));
    }
}
