package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuItems {
    public static final Item TOFUKINU = new Item(new Item.Properties().food(TofuFoods.TOFUKINU).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUMOMEN = new Item(new Item.Properties().food(TofuFoods.TOFUMOMEN).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUISHI = new Item(new Item.Properties().food(TofuFoods.TOFUISHI).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUMETAL = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUGRILD = new Item(new Item.Properties().food(TofuFoods.TOFUGRILD).group(TofuItemGroup.TOFUCRAFT));

    public static final Item TOFUCOOKIE = new Item(new Item.Properties().food(TofuFoods.TOFUCOOKIE).group(TofuItemGroup.TOFUCRAFT));

    public static final Item SEEDS_SOYBEAN = new BlockNamedItem(TofuBlocks.SOYBEAN,new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

    public static final Item SOYMILK_BUCKET = new BucketItem(TofuFluids.SOYMILK, (new Item.Properties()).containerItem(Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC));

    public static void register(RegistryEvent.Register<Item> registry, Item item, String id) {
        if (item instanceof BlockItem){
            Item.BLOCK_TO_ITEM.put(((BlockItem) item).getBlock(), item);
        }

        item.setRegistryName(new ResourceLocation(TofuCraftCore.MODID, id));

        registry.getRegistry().register(item);
    }

    public static void register(RegistryEvent.Register<Item> registry, Item item) {

        if (item instanceof BlockItem && item.getRegistryName() == null) {
            item.setRegistryName(((BlockItem) item).getBlock().getRegistryName());

            Item.BLOCK_TO_ITEM.put(((BlockItem) item).getBlock(), item);
        }

        registry.getRegistry().register(item);
    }


    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> registry) {
        register(registry, TOFUKINU, "tofukinu");
        register(registry, TOFUMOMEN, "tofumomen");
        register(registry, TOFUISHI, "tofuishi");
        register(registry, TOFUMETAL, "tofumetal");
        register(registry, TOFUGRILD, "tofugrilled");
        register(registry, TOFUCOOKIE, "tofucookie");
        register(registry, SEEDS_SOYBEAN, "seeds_soybeans");
        register(registry, SOYMILK_BUCKET, "bucketsoymilk");
    }
}
