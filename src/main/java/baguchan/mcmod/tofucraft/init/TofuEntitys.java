package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.entity.*;
import baguchan.mcmod.tofucraft.entity.projectile.ZundaArrowEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import static baguchan.mcmod.tofucraft.TofuCraftCore.MODID;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuEntitys {
    public static final EntityClassification TOFU_MONSTER = EntityClassification.create("tofu_monster", "tofu_monster", 25, false, false);


    public static final EntityType<TofunianEntity> TOFUNIAN = EntityType.Builder.create(TofunianEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.2F).build(prefix("tofunian"));

    public static final EntityType<TofuSlimeEntity> TOFUSLIME = EntityType.Builder.create(TofuSlimeEntity::new, TOFU_MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(2.04F, 2.04F).build(prefix("tofuslime"));
    public static final EntityType<TofuCowEntity> TOFUCOW = EntityType.Builder.create(TofuCowEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.9F, 1.4F).build(prefix("tofucow"));
    public static final EntityType<TofuFishEntity> TOFUFISH = EntityType.Builder.create(TofuFishEntity::new, EntityClassification.WATER_CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.5F, 0.3F).build(prefix("tofufish"));
    public static final EntityType<TofuChingerEntity> TOFUCHINGER = EntityType.Builder.create(TofuChingerEntity::new, TOFU_MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.5F, 0.65F).build(prefix("tofuchinger"));

    public static final EntityType<ZundaArrowEntity> ZUNDAARROW = EntityType.Builder.<ZundaArrowEntity>create(ZundaArrowEntity::new, EntityClassification.MISC).setTrackingRange(100).setCustomClientFactory(ZundaArrowEntity::new).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).size(0.5F, 0.5F).build(prefix("zunda_arrow"));


    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(TOFUNIAN.setRegistryName("tofunian"));
        event.getRegistry().register(TOFUSLIME.setRegistryName("tofuslime"));
        event.getRegistry().register(TOFUCOW.setRegistryName("tofucow"));
        event.getRegistry().register(TOFUFISH.setRegistryName("tofufish"));
        event.getRegistry().register(TOFUCHINGER.setRegistryName("tofu_chinger"));

        event.getRegistry().register(ZUNDAARROW.setRegistryName("zunda_arrow"));

        EntitySpawnPlacementRegistry.register(TOFUSLIME, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TofuSlimeEntity::spawnHandle);
        EntitySpawnPlacementRegistry.register(TOFUCHINGER, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TofuChingerEntity::spawnHandle);
        EntitySpawnPlacementRegistry.register(TOFUFISH, EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TofuFishEntity::spawnHandler);
    }

    private static String prefix(String path) {

        return MODID + "." + path;

    }

    public static void spawnEntity() {
        for (Biome biome : ForgeRegistries.BIOMES) {

            if (!TofuBiomes.tofubiome.contains(biome)) {
                biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(TOFUSLIME, 120, 2, 3));
            }
        }

    }
}