package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.world.biome.gen.feature.TofuTreeFeature;
import baguchan.mcmod.tofucraft.world.biome.gen.feature.structure.TofuVillageStructure;
import net.minecraft.block.BlockState;
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

    public static final Structure<NoFeatureConfig> TOFUVILLAGE = new TofuVillageStructure(NoFeatureConfig::deserialize);


    @SubscribeEvent
    public static void register(RegistryEvent.Register<Feature<?>> registry) {
        registry.getRegistry().register(TOFUFLOWER.setRegistryName("tofuflower"));
        registry.getRegistry().register(TOFUTREE.setRegistryName("tofutree"));
        registry.getRegistry().register(ZUNDATOFUTREE.setRegistryName("zunda_tofutree"));
        registry.getRegistry().register(TOFUVILLAGE.setRegistryName("tofuvillage"));
    }
}
