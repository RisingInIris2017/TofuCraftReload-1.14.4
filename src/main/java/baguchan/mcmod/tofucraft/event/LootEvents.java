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
    private static final Set<ResourceLocation> SHIPWRECK_LOOT = Sets.newHashSet(LootTables.CHESTS_SHIPWRECK_TREASURE);
    private static final Set<ResourceLocation> TEMPLE_LOOT = Sets.newHashSet(LootTables.CHESTS_JUNGLE_TEMPLE);


    @SubscribeEvent
    public static void onInjectLoot(LootTableLoadEvent event) {
        if (SHIPWRECK_LOOT.contains(event.getName())) {
            LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(TofuCraftCore.MODID, "injections/tofustick_structures")).weight(1).quality(1)).name("tofustick_structure").build();
            event.getTable().addPool(pool);
        }

        if (RUIN_LOOT.contains(event.getName())) {
            LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(TofuCraftCore.MODID, "injections/tofustick_structures")).weight(1).quality(1)).name("tofustick_structure").build();
            event.getTable().addPool(pool);
        }

        if (TEMPLE_LOOT.contains(event.getName())) {
            LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(TofuCraftCore.MODID, "injections/tofustick_structures")).weight(1).quality(1)).name("tofustick_structure").build();
            event.getTable().addPool(pool);
        }
    }
}
