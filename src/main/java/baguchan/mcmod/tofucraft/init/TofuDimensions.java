package baguchan.mcmod.tofucraft.init;

import net.minecraft.world.biome.provider.NetherBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;

public class TofuDimensions {
    //public static final RegistryKey<Dimension> TOFU_WORLD = RegistryKey.func_240903_a_(Registry.DIMENSION_KEY, new ResourceLocation(TofuCraftCore.MODID, "tofu_world"));

    private static ChunkGenerator tofuWorldGenerator(long p_236041_0_) {
        return new NoiseChunkGenerator(NetherBiomeProvider.Preset.field_235288_b_.func_235292_a_(p_236041_0_), p_236041_0_, DimensionSettings.Preset.field_236122_b_.func_236137_b_());
    }
}
