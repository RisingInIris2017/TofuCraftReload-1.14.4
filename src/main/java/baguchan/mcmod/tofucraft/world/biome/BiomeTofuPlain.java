package baguchan.mcmod.tofucraft.world.biome;

import baguchan.mcmod.tofucraft.init.TofuFeatures;
import baguchan.mcmod.tofucraft.init.TofuSurfaceBuilder;
import baguchan.mcmod.tofucraft.world.gen.DefaultTofuBiomeFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class BiomeTofuPlain extends BiomeTofuBase {
    public BiomeTofuPlain() {
        super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, TofuSurfaceBuilder.TOFUDEFAULT_CONFIG).precipitation(RainType.RAIN).category(Category.PLAINS).depth(0.125F).scale(0.05F).temperature(0.8F).downfall(0.4F).waterColor(0xc7d8e8).waterFogColor(0xa7c2ff).parent(null));
        DefaultTofuBiomeFeature.addTofuFlowers(this);
        this.addStructure(TofuFeatures.TOFUVILLAGE, new NoFeatureConfig());
        DefaultTofuBiomeFeature.addTofuNormalTrees(this);
        DefaultTofuBiomeFeature.addLeeks(this);
    }
}