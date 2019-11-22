package baguchan.mcmod.tofucraft.world.biome;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.world.gen.DefaultTofuBiomeFeature;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;

public abstract class BiomeTofuBase extends Biome {
    public BiomeTofuBase(Biome.Builder builder) {
        super(builder);

        DefaultTofuBiomeFeature.addCarvers(this);
        DefaultTofuBiomeFeature.addStructure(this);
        DefaultTofuBiomeFeature.addTofuBerry(this);
        DefaultTofuBiomeFeature.addTofuOre(this);
        this.addSpawn(TofuEntitys.TOFU_MONSTER, new SpawnListEntry(TofuEntitys.TOFUSLIME, 25, 1, 1));
        this.addSpawn(TofuEntitys.TOFU_MONSTER, new SpawnListEntry(TofuEntitys.TOFUSPIDER, 1, 1, 2));


        this.addSpawn(EntityClassification.WATER_CREATURE, new SpawnListEntry(TofuEntitys.TOFUFISH, 10, 2, 3));

    }


}