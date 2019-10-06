package baguchan.mcmod.tofucraft.world.biome;

import baguchan.mcmod.tofucraft.init.TofuSurfaceBuilder;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class BiomeTofuForest extends BiomeTofuBase {
    public BiomeTofuForest() {
        super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, TofuSurfaceBuilder.TOFUDEFAULT_CONFIG).precipitation(RainType.RAIN).category(Category.FOREST).depth(0.15F).scale(0.25F).temperature(0.7F).downfall(0.8F).waterColor(0xc7d8e8).waterFogColor(0xa7c2ff).parent(null));

    }

}