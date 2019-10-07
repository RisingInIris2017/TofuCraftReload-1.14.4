package baguchan.mcmod.tofucraft.world.biome;

import baguchan.mcmod.tofucraft.world.biome.gen.DefaultTofuBiomeFeature;
import net.minecraft.world.biome.Biome;

public abstract class BiomeTofuBase extends Biome {
    protected int crystalRarity;

    public BiomeTofuBase(Biome.Builder builder) {
        super(builder);
        this.crystalRarity = 8;

        DefaultTofuBiomeFeature.addCarvers(this);
    }


}