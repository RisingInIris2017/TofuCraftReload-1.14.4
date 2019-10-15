package baguchan.mcmod.tofucraft.world.biome;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.world.biome.gen.DefaultTofuBiomeFeature;
import net.minecraft.world.biome.Biome;

public abstract class BiomeTofuBase extends Biome {
    public BiomeTofuBase(Biome.Builder builder) {
        super(builder);

        DefaultTofuBiomeFeature.addCarvers(this);
        DefaultTofuBiomeFeature.addStructure(this);
        this.addSpawn(TofuEntitys.TOFU_MONSTER, new SpawnListEntry(TofuEntitys.TOFUSLIME, 10, 1, 2));
    }


}