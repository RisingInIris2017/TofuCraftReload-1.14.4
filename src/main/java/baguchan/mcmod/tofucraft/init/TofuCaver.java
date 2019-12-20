package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.world.gen.caver.TofuCanyonCarver;
import baguchan.mcmod.tofucraft.world.gen.caver.TofuCaveCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuCaver {

    public static final WorldCarver<ProbabilityConfig> TOFU_CAVE = new TofuCaveCarver(ProbabilityConfig::deserialize, 2.2F, false);
    public static final WorldCarver<ProbabilityConfig> TOFU_LARGECAVE = new TofuCaveCarver(ProbabilityConfig::deserialize, 2.6F, true);
    public static final WorldCarver<ProbabilityConfig> TOFU_CANYON = new TofuCanyonCarver(ProbabilityConfig::deserialize);

    @SubscribeEvent
    public static void registerCarvers(RegistryEvent.Register<WorldCarver<?>> registry) {
        registry.getRegistry().register(TOFU_CAVE.setRegistryName(TofuCraftCore.MODID, "tofu_cave"));
        registry.getRegistry().register(TOFU_LARGECAVE.setRegistryName(TofuCraftCore.MODID, "tofu_largecave"));
        registry.getRegistry().register(TOFU_CANYON.setRegistryName(TofuCraftCore.MODID, "tofu_canyon"));
    }

}