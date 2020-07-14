package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.world.dimension.ChunkGeneratorTofuWorld;
import baguchan.mcmod.tofucraft.world.dimension.TofuBiomeProvider;
import net.minecraft.util.registry.Registry;

public class TofuDimensions {

    public static void init() {
        Registry.register(Registry.field_239689_aA_, TofuCraftCore.prefix("tofu_world"), TofuBiomeProvider.tofuBiomeProviderCodec);
        Registry.register(Registry.field_239690_aB_, TofuCraftCore.prefix("tofu_world"), ChunkGeneratorTofuWorld.codecTofuChunk);
    }
}
