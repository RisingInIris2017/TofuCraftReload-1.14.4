package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.world.biome.*;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuBiomes {
    public static List<Biome> tofubiome = new ArrayList<Biome>();

    public static final Biome TOFU_FOREST = new BiomeTofuForest();
    public static final Biome TOFUZUNDA_SWAMP = new BiomeZundaTofuSwamp();
    public static final Biome TOFU_PLAIN = new BiomeTofuPlain();
    public static final Biome ZUNDATOFU_PLAIN = new BiomeZundaTofuPlain();
    public static final Biome TOFU_MOUNTAIN = new BiomeTofuMountain();
    public static final Biome TOFU_RIVER = new BiomeTofuRiver();

    @SubscribeEvent
    public static void onRegisterBiomes(RegistryEvent.Register<Biome> event) {

        event.getRegistry().register(TOFU_FOREST.setRegistryName("tofu_forest"));
        event.getRegistry().register(TOFUZUNDA_SWAMP.setRegistryName("tofuzunda_forest"));
        event.getRegistry().register(TOFU_PLAIN.setRegistryName("tofu_plain"));
        event.getRegistry().register(ZUNDATOFU_PLAIN.setRegistryName("zundatofu_plain"));
        event.getRegistry().register(TOFU_MOUNTAIN.setRegistryName("tofu_mountain"));
        event.getRegistry().register(TOFU_RIVER.setRegistryName("tofu_river"));

        tofubiome.add(TOFU_FOREST);
        tofubiome.add(TOFUZUNDA_SWAMP);
        tofubiome.add(TOFU_PLAIN);
        tofubiome.add(ZUNDATOFU_PLAIN);
        tofubiome.add(TOFU_MOUNTAIN);
        tofubiome.add(TOFU_RIVER);

        registerBiomeTypes();
    }

    public static void registerBiomeTypes() {
        BiomeDictionary.addTypes(TOFU_MOUNTAIN, BiomeDictionary.Type.MOUNTAIN);
        BiomeDictionary.addTypes(TOFU_PLAIN, BiomeDictionary.Type.PLAINS);
        BiomeDictionary.addTypes(ZUNDATOFU_PLAIN, BiomeDictionary.Type.PLAINS);
        BiomeDictionary.addTypes(TOFU_FOREST, BiomeDictionary.Type.FOREST);
        BiomeDictionary.addTypes(TOFUZUNDA_SWAMP, BiomeDictionary.Type.SWAMP);
        BiomeDictionary.addTypes(TOFU_RIVER, BiomeDictionary.Type.RIVER);
    }
}