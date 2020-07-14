package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.world.biome.*;
import baguchan.mcmod.tofucraft.world.gen.feature.structure.TofuStructurePieceType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.common.BiomeDictionary.Type.HILLS;
import static net.minecraftforge.common.BiomeDictionary.Type.MOUNTAIN;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuBiomes {
    public static List<BiomeManager.BiomeEntry> entryTofuBiome = new ArrayList<BiomeManager.BiomeEntry>();

    public static final Biome TOFU_FOREST = new BiomeTofuForest();
    public static final Biome TOFUZUNDA_SWAMP = new BiomeZundaTofuSwamp();
    public static final Biome TOFU_PLAIN = new BiomeTofuPlain();
    public static final Biome ZUNDATOFU_PLAIN = new BiomeZundaTofuPlain();
    public static final Biome ZUNDATOFU_FUNGIFOREST = new BiomeZundaTofuFungiForest();
    public static final Biome TOFU_BUILDING = new BiomeTofuBuilding();
    public static final Biome TOFU_MOUNTAIN = new BiomeTofuMountain();
    public static final Biome TOFU_RIVER = new BiomeTofuRiver();

    @SubscribeEvent
    public static void onRegisterBiomes(RegistryEvent.Register<Biome> event) {
        TofuStructurePieceType.init();

        event.getRegistry().register(TOFU_FOREST.setRegistryName("tofu_forest"));
        event.getRegistry().register(TOFUZUNDA_SWAMP.setRegistryName("tofuzunda_forest"));
        event.getRegistry().register(TOFU_PLAIN.setRegistryName("tofu_plain"));
        event.getRegistry().register(ZUNDATOFU_PLAIN.setRegistryName("zundatofu_plain"));
        event.getRegistry().register(ZUNDATOFU_FUNGIFOREST.setRegistryName("zundatofu_fungiforest"));
        event.getRegistry().register(TOFU_BUILDING.setRegistryName("tofu_building"));
        event.getRegistry().register(TOFU_MOUNTAIN.setRegistryName("tofu_mountain"));
        event.getRegistry().register(TOFU_RIVER.setRegistryName("tofu_river"));

        entryTofuBiome.add(new BiomeManager.BiomeEntry(TOFU_FOREST, 10));
        entryTofuBiome.add(new BiomeManager.BiomeEntry(TOFUZUNDA_SWAMP, 6));
        entryTofuBiome.add(new BiomeManager.BiomeEntry(TOFU_PLAIN, 10));
        entryTofuBiome.add(new BiomeManager.BiomeEntry(ZUNDATOFU_PLAIN, 8));
        entryTofuBiome.add(new BiomeManager.BiomeEntry(ZUNDATOFU_FUNGIFOREST, 8));
        entryTofuBiome.add(new BiomeManager.BiomeEntry(TOFU_BUILDING, 8));
        //entryTofuBiome.add(new BiomeManager.BiomeEntry(TOFU_BUILDING, 12));
        entryTofuBiome.add(new BiomeManager.BiomeEntry(TOFU_MOUNTAIN, 6));


        registerBiomeTypes();
    }

    public static void registerBiomeTypes() {
        BiomeDictionary.addTypes(TOFU_MOUNTAIN, MOUNTAIN, HILLS);
        BiomeDictionary.addTypes(TOFU_PLAIN, BiomeDictionary.Type.PLAINS);
        BiomeDictionary.addTypes(ZUNDATOFU_PLAIN, BiomeDictionary.Type.PLAINS);
        BiomeDictionary.addTypes(TOFU_FOREST, BiomeDictionary.Type.FOREST);
        BiomeDictionary.addTypes(ZUNDATOFU_FUNGIFOREST, BiomeDictionary.Type.FOREST);
        BiomeDictionary.addTypes(TOFUZUNDA_SWAMP, BiomeDictionary.Type.SWAMP);
        BiomeDictionary.addTypes(TOFU_BUILDING, BiomeDictionary.Type.PLAINS);
        BiomeDictionary.addTypes(TOFU_RIVER, BiomeDictionary.Type.RIVER);
    }
}