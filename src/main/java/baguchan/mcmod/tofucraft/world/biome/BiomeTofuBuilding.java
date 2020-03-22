package baguchan.mcmod.tofucraft.world.biome;

import baguchan.mcmod.tofucraft.init.TofuSurfaceBuilder;
import baguchan.mcmod.tofucraft.world.gen.DefaultTofuBiomeFeature;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class BiomeTofuBuilding extends BiomeTofuBase {
    public BiomeTofuBuilding() {
        super((new Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, TofuSurfaceBuilder.TOFUDEFAULT_CONFIG).precipitation(RainType.RAIN).category(Category.PLAINS).depth(0.125F).scale(0.05F).temperature(0.8F).downfall(0.4F).waterColor(0xc7d8e8).waterFogColor(0xa7c2ff).parent(null));

        DefaultTofuBiomeFeature.addTofuBuilding(this);
        DefaultTofuBiomeFeature.addLeeks(this);
        DefaultTofuBiomeFeature.addTofuBerry(this);
    }
}