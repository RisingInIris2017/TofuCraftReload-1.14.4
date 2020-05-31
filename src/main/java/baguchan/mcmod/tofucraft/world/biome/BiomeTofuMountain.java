package baguchan.mcmod.tofucraft.world.biome;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuSurfaceBuilder;
import baguchan.mcmod.tofucraft.world.gen.DefaultTofuBiomeFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class BiomeTofuMountain extends BiomeTofuBase {
    public BiomeTofuMountain() {
        super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, TofuSurfaceBuilder.TOFUDEFAULT_CONFIG).precipitation(RainType.RAIN).category(Category.EXTREME_HILLS).depth(0.2F).scale(1.55F).temperature(0.2F).downfall(0.4F).waterColor(0xc7d8e8).waterFogColor(0xa7c2ff).parent(null));
        DefaultTofuBiomeFeature.addTofuNormalTrees(this);
        DefaultTofuBiomeFeature.addTofuBerry(this);

        this.addSpawn(TofuEntitys.TOFU_MONSTER, new SpawnListEntry(TofuEntitys.TOFUSLIME, 60, 1, 1));
        this.addSpawn(TofuEntitys.TOFU_MONSTER, new SpawnListEntry(TofuEntitys.TOFUSPIDER, 30, 1, 2));
        this.addSpawn(TofuEntitys.TOFU_MONSTER, new SpawnListEntry(TofuEntitys.TOFUCREEPER, 5, 1, 2));
    }
}