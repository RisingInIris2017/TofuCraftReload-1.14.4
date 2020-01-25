package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.world.dimension.TofuWorldChunkGenerator;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.IChunkGeneratorFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuChunkGeneratorTypes {

    public static final ChunkGeneratorType<TofuWorldChunkGenerator.Config, TofuWorldChunkGenerator> TOFU_SURFACE = register(TofuWorldChunkGenerator::new, TofuWorldChunkGenerator.Config::new, true);

    private static <C extends GenerationSettings, T extends ChunkGenerator<C>> ChunkGeneratorType<C, T> register(IChunkGeneratorFactory<C, T> factoryIn, Supplier<C> settingsIn, boolean canUseForBuffet) {
        return new ChunkGeneratorType<>(factoryIn, canUseForBuffet, settingsIn);
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<ChunkGeneratorType<?, ?>> registry) {
        registry.getRegistry().register(TOFU_SURFACE.setRegistryName("tofu_surface"));
    }
}