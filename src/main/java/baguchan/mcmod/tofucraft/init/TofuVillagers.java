package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuVillagers {
    private static Method blockStatesInjector = ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_221052_a", PointOfInterestType.class);

    public static PointOfInterestType SALTPAN = new PointOfInterestType("tofu_craftsman", PointOfInterestType.getAllStates(TofuBlocks.SALTPAN.getBlock()), 1, 1);
    public static VillagerProfession TOFU_CRAFTSMAN = new VillagerProfession("tofu_craftsman", SALTPAN, ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_LEATHERWORKER);

    @SubscribeEvent
    public static void registerPointOfInterest(RegistryEvent.Register<PointOfInterestType> register) {
        register.getRegistry().register(SALTPAN.setRegistryName("saltpan"));
    }

    @SubscribeEvent
    public static void registerVillagerProfession(RegistryEvent.Register<VillagerProfession> register) {
        register.getRegistry().register(TOFU_CRAFTSMAN.setRegistryName("tofu_craftsman"));
    }

    @SuppressWarnings("deprecation")
    public static void init() {
        PlainsVillagePools.init();
        SnowyVillagePools.init();
        SavannaVillagePools.init();
        DesertVillagePools.init();
        TaigaVillagePools.init();
        for (String biome : new String[]{"plains", "snowy", "savanna", "desert", "taiga"})
            addToPool(new ResourceLocation("village/" + biome + "/houses"), new ResourceLocation(TofuCraftCore.MODID, "village/tofu_craftsman_house_" + biome + "_1"), 3);
        try {
            blockStatesInjector.invoke(null, SALTPAN);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("deprecation")
    private static void addToPool(ResourceLocation pool, ResourceLocation toAdd, int weight) {
        JigsawPattern old = JigsawManager.REGISTRY.get(pool);
        List<JigsawPiece> shuffled = old.getShuffledPieces(new Random());
        List<Pair<JigsawPiece, Integer>> newPieces = new ArrayList<>();
        for (JigsawPiece p : shuffled) {
            newPieces.add(new Pair<>(p, 1));
        }
        newPieces.add(new Pair<>(new SingleJigsawPiece(toAdd.toString()), weight));
        ResourceLocation something = old.func_214948_a();
        JigsawManager.REGISTRY.register(new JigsawPattern(pool, something, newPieces, JigsawPattern.PlacementBehaviour.RIGID));
    }
}
