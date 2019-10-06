package baguchan.mcmod.tofucraft.world.biome;

import net.minecraft.world.biome.Biome;

public abstract class BiomeTofuBase extends Biome {
    protected int crystalRarity;

    public BiomeTofuBase(Biome.Builder builder) {
        super(builder);
        this.crystalRarity = 8;


    }


}