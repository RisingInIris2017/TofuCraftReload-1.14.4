package baguchan.mcmod.tofucraft.world.biome;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuSurfaceBuilder;
import baguchan.mcmod.tofucraft.world.gen.DefaultTofuBiomeFeature;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class BiomeTofuMountain extends BiomeTofuBase {
    public BiomeTofuMountain() {
        super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, TofuSurfaceBuilder.TOFUDEFAULT_CONFIG).precipitation(RainType.RAIN).category(Category.PLAINS).depth(0.2F).scale(1.65F).temperature(0.8F).downfall(0.4F).waterColor(0xc7d8e8).waterFogColor(0xa7c2ff).parent(null));
        DefaultTofuBiomeFeature.addTofuNormalTrees(this);
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(TofuEntitys.TOFUEAGLE, 8, 2, 3));

    }
}