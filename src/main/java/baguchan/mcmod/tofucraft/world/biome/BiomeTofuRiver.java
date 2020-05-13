package baguchan.mcmod.tofucraft.world.biome;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuSurfaceBuilder;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class BiomeTofuRiver extends BiomeTofuBase {
    public BiomeTofuRiver() {
        super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, TofuSurfaceBuilder.TOFUDEFAULT_CONFIG).precipitation(RainType.RAIN).category(Category.NONE).depth(-0.35F).scale(0.00F).temperature(0.8F).downfall(0.4F).waterColor(0xc7d8e8).waterFogColor(0xa7c2ff).parent(null));

        this.addSpawn(TofuEntitys.TOFU_MONSTER, new SpawnListEntry(TofuEntitys.TOFUSLIME, 60, 1, 1));
        this.addSpawn(TofuEntitys.TOFU_MONSTER, new SpawnListEntry(TofuEntitys.TOFUSPIDER, 30, 1, 2));
        this.addSpawn(TofuEntitys.TOFU_MONSTER, new SpawnListEntry(TofuEntitys.TOFUCREEPER, 5, 1, 2));
        this.addSpawn(EntityClassification.WATER_CREATURE, new SpawnListEntry(TofuEntitys.TOFUFISH, 10, 2, 3));
    }

}