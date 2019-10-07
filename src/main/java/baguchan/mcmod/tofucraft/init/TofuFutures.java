package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuFutures {
    public static final Feature<NoFeatureConfig> TOFUFLOWER = new FlowersFeature(NoFeatureConfig::deserialize) {
        @Override
        public BlockState getRandomFlower(Random random, BlockPos pos) {
            return TofuBlocks.TOFUFLOWER.getDefaultState();
        }
    };

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Feature<?>> registry) {
        registry.getRegistry().register(TOFUFLOWER.setRegistryName("tofuflower"));
    }
}
