package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.world.gen.feature.*;
import baguchan.mcmod.tofucraft.world.gen.feature.config.TofuOreConfig;
import baguchan.mcmod.tofucraft.world.gen.feature.structure.TofuCastleStructure;
import baguchan.mcmod.tofucraft.world.gen.feature.structure.TofuVillageStructure;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuFeatures {
    public static final Feature<NoFeatureConfig> TOFUFLOWER = new FlowersFeature(NoFeatureConfig::deserialize) {
        @Override
        public BlockState getRandomFlower(Random random, BlockPos pos) {
            return TofuBlocks.TOFUFLOWER.getDefaultState();
        }
    };

    public static final Feature<NoFeatureConfig> TOFUTREE = new TofuTreeFeature(NoFeatureConfig::deserialize, false);
    public static final Feature<NoFeatureConfig> ZUNDATOFUTREE = new TofuTreeFeature(NoFeatureConfig::deserialize, false, 4, TofuBlocks.ISHITOFU.getDefaultState(), TofuBlocks.TOFUZUNDALEAVES.getDefaultState(), false);

    public static final Feature<NoFeatureConfig> ZUNDAMUSHROOM_SMALL = new ZundaMushroomFeature(NoFeatureConfig::deserialize, 7, 7, new ResourceLocation[]{
            new ResourceLocation(TofuCraftCore.MODID, "mushroom/mushroom_zunda_small")
    });

    public static final Feature<NoFeatureConfig> ZUNDAMUSHROOM_BIG = new ZundaMushroomFeature(NoFeatureConfig::deserialize, 9, 9, new ResourceLocation[]{
            new ResourceLocation(TofuCraftCore.MODID, "mushroom/mushroom_zunda_big")
    });


    public static final Feature<NoFeatureConfig> TOFUBERRY = new TofuBerryFeature(NoFeatureConfig::deserialize);

    public static final Feature<TofuOreConfig> TOFUORE = new TofuOreFeature(TofuOreConfig::deserialize);

    public static final Feature<NoFeatureConfig> TOFUSTONECAVE = new TofuStoneCaveFeature(NoFeatureConfig::deserialize);

    public static final Structure<NoFeatureConfig> TOFUVILLAGE = new TofuVillageStructure(NoFeatureConfig::deserialize);
    public static final Structure<NoFeatureConfig> TOFUCASTLE = new TofuCastleStructure(NoFeatureConfig::deserialize);


    @SubscribeEvent
    public static void register(RegistryEvent.Register<Feature<?>> registry) {
        registry.getRegistry().register(TOFUFLOWER.setRegistryName("tofuflower"));
        registry.getRegistry().register(TOFUTREE.setRegistryName("tofutree"));
        registry.getRegistry().register(ZUNDATOFUTREE.setRegistryName("zunda_tofutree"));
        registry.getRegistry().register(ZUNDAMUSHROOM_SMALL.setRegistryName("zundamushroom_small"));
        registry.getRegistry().register(ZUNDAMUSHROOM_BIG.setRegistryName("zundamushroom_big"));
        registry.getRegistry().register(TOFUBERRY.setRegistryName("tofuberry"));
        registry.getRegistry().register(TOFUORE.setRegistryName("tofuore"));
        registry.getRegistry().register(TOFUSTONECAVE.setRegistryName("tofustone_cave"));
        registry.getRegistry().register(TOFUVILLAGE.setRegistryName("tofuvillage"));
        registry.getRegistry().register(TOFUCASTLE.setRegistryName("tofucastle"));
    }
}
