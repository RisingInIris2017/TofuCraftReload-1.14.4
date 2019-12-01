package baguchan.mcmod.tofucraft.world.biome;

import baguchan.mcmod.tofucraft.init.TofuSurfaceBuilder;
import baguchan.mcmod.tofucraft.world.gen.DefaultTofuBiomeFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class BiomeZundaMushroomForest extends BiomeTofuBase {
    public BiomeZundaMushroomForest() {
        super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, TofuSurfaceBuilder.TOFUDEFAULT_CONFIG).precipitation(RainType.RAIN).category(Category.PLAINS).depth(0.125F).scale(0.05F).temperature(0.8F).downfall(0.4F).waterColor(0x9fe17a).waterFogColor(0xcaffa4).parent(null));
        DefaultTofuBiomeFeature.addTofuFlowers(this);
        DefaultTofuBiomeFeature.addBigZundaMushroom(this);
    }
}