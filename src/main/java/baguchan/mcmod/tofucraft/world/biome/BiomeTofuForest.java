package baguchan.mcmod.tofucraft.world.biome;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuFeatures;
import baguchan.mcmod.tofucraft.init.TofuSurfaceBuilder;
import baguchan.mcmod.tofucraft.world.gen.DefaultTofuBiomeFeature;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class BiomeTofuForest extends BiomeTofuBase {
    public BiomeTofuForest() {
        super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, TofuSurfaceBuilder.TOFUDEFAULT_CONFIG).precipitation(RainType.RAIN).category(Category.FOREST).depth(0.15F).scale(0.25F).temperature(0.7F).downfall(0.8F).waterColor(0xc7d8e8).waterFogColor(0xa7c2ff).parent(null));
        this.addStructure(TofuFeatures.TOFUCASTLE.withConfiguration(new NoFeatureConfig()));
        DefaultTofuBiomeFeature.addTofuFlowers(this);
        DefaultTofuBiomeFeature.addTofuForestTrees(this);
        /*DefaultTofuBiomeFeature.addLeeks(this);*/
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(TofuEntitys.TOFUCOW, 10, 4, 4));

        this.addSpawn(TofuEntitys.TOFU_MONSTER, new SpawnListEntry(TofuEntitys.TOFUCHINGER, 40, 2, 3));
    }

}