package baguchan.mcmod.tofucraft.world.biome;

import baguchan.mcmod.tofucraft.world.gen.DefaultTofuBiomeFeature;
import net.minecraft.world.biome.Biome;

public abstract class BiomeTofuBase extends Biome {
    public BiomeTofuBase(Biome.Builder builder) {
        super(builder);

        DefaultTofuBiomeFeature.addCarvers(this);
        DefaultTofuBiomeFeature.addStructure(this);
        DefaultTofuBiomeFeature.addTofuOre(this);
        DefaultTofuBiomeFeature.addTofuVariants(this);
        DefaultTofuBiomeFeature.addTofuSedimentDisks(this);
    }


}