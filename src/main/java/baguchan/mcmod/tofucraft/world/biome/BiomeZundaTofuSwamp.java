package baguchan.mcmod.tofucraft.world.biome;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuSurfaceBuilder;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class BiomeZundaTofuSwamp extends BiomeTofuBase {
    public BiomeZundaTofuSwamp() {
        super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.SWAMP, TofuSurfaceBuilder.TOFUZUNDA_CONFIG).precipitation(RainType.RAIN).category(Category.SWAMP).depth(-0.2F).scale(0.1F).temperature(0.8F).downfall(0.9F).waterColor(0x9fe17a).waterFogColor(0xcaffa4).parent(null));
        //DefaultTofuBiomeFeature.addTofuSwampZundaTrees(this);
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(TofuEntitys.TOFUCOW, 10, 4, 4));
        this.addSpawn(TofuEntitys.TOFU_MONSTER, new SpawnListEntry(TofuEntitys.TOFUCHINGER, 80, 2, 3));

    }

}