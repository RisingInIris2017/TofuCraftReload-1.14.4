package baguchan.mcmod.tofucraft.event;


import baguchan.mcmod.tofucraft.TofuCraftCore;
import com.google.common.collect.Sets;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID)
public class LootEvents {
    private static final Set<ResourceLocation> RUIN_LOOT = Sets.newHashSet(LootTables.CHESTS_UNDERWATER_RUIN_BIG);
    private static final Set<ResourceLocation> SHIP_LOOT = Sets.newHashSet(LootTables.CHESTS_SHIPWRECK_SUPPLY);
    private static final Set<ResourceLocation> TEMPLE_LOOT = Sets.newHashSet(LootTables.CHESTS_JUNGLE_TEMPLE);
    private static final Set<ResourceLocation> NETHER_BRIDGE_LOOT = Sets.newHashSet(LootTables.CHESTS_NETHER_BRIDGE);

    private static ResourceLocation tall_grass_drops = new ResourceLocation("minecraft", "blocks/tall_grass");

    @SubscribeEvent
    public static void onInjectLoot(LootTableLoadEvent event) {

        if (RUIN_LOOT.contains(event.getName())) {
            LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(TofuCraftCore.MODID, "injections/tofustick_ruin"))).name("tofustick_ruin").build();
            event.getTable().addPool(pool);
        }

        if (SHIP_LOOT.contains(event.getName())) {
            LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(TofuCraftCore.MODID, "injections/tofustick_ship"))).name("tofustick_ship").build();
            event.getTable().addPool(pool);
        }

        if (TEMPLE_LOOT.contains(event.getName())) {
            LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(TofuCraftCore.MODID, "injections/tofustick_temple"))).name("tofustick_temple").build();
            event.getTable().addPool(pool);
        }

        if (NETHER_BRIDGE_LOOT.contains(event.getName())) {
            LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(TofuCraftCore.MODID, "injections/soybean_fortress"))).name("soybean_fortress").build();
            event.getTable().addPool(pool);
        }

        /*if (event.getName().equals(tall_grass_drops)) {

        }*/
    }
}
