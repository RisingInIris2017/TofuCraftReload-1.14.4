package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.item.BitternItem;
import baguchan.mcmod.tofucraft.item.TofuStickItem;
import net.minecraft.inventory.EquipmentSlotType;
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
    public static final Item TOFUZUNDA = new Item(new Item.Properties().food(TofuFoods.COOKEDTOFU).group(TofuItemGroup.TOFUCRAFT));

    public static final Item TOFUCOOKIE = new Item(new Item.Properties().food(TofuFoods.TOFUCOOKIE).group(TofuItemGroup.TOFUCRAFT));

    public static final Item SEEDS_SOYBEAN = new BlockNamedItem(TofuBlocks.SOYBEAN,new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item EDAMAME = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item EDAMAME_BOILD = new Item(new Item.Properties().food(TofuFoods.EDAMAME).group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOYMILK_BUCKET = new BucketItem(TofuFluids.SOYMILK, (new Item.Properties()).containerItem(Items.BUCKET).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));

    public static final Item SALT = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item BITTERN = new BitternItem(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ZUNDA = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ZUNDAMA = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUSTICK = new TofuStickItem(new Item.Properties().rarity(Rarity.RARE).group(TofuItemGroup.TOFUCRAFT));
    public static final Item ZUNDARUBY = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUNIAN_SPAWNEGG = new SpawnEggItem(TofuEntitys.TOFUNIAN, 0xEBE8E8, 0xCACFA1, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUSLIME_SPAWNEGG = new SpawnEggItem(TofuEntitys.TOFUSLIME, 0xEBE8E8, 0x2E2E2E, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));


    public static final Item ARMOR_KINUHELMET = new ArmorItem(TofuArmorMaterial.KINU, EquipmentSlotType.HEAD, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_KINUCHESTPLATE = new ArmorItem(TofuArmorMaterial.KINU, EquipmentSlotType.CHEST, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_KINULEGGINS = new ArmorItem(TofuArmorMaterial.KINU, EquipmentSlotType.LEGS, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_KINUBOOTS = new ArmorItem(TofuArmorMaterial.KINU, EquipmentSlotType.FEET, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

    public static final Item ARMOR_MOMENHELMET = new ArmorItem(TofuArmorMaterial.MOMEN, EquipmentSlotType.HEAD, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_MOMENCHESTPLATE = new ArmorItem(TofuArmorMaterial.MOMEN, EquipmentSlotType.CHEST, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_MOMENLEGGINS = new ArmorItem(TofuArmorMaterial.MOMEN, EquipmentSlotType.LEGS, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_MOMENBOOTS = new ArmorItem(TofuArmorMaterial.MOMEN, EquipmentSlotType.FEET, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

    public static final Item ARMOR_SOLIDHELMET = new ArmorItem(TofuArmorMaterial.SOLID, EquipmentSlotType.HEAD, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_SOLIDCHESTPLATE = new ArmorItem(TofuArmorMaterial.SOLID, EquipmentSlotType.CHEST, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_SOLIDLEGGINS = new ArmorItem(TofuArmorMaterial.SOLID, EquipmentSlotType.LEGS, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_SOLIDBOOTS = new ArmorItem(TofuArmorMaterial.SOLID, EquipmentSlotType.FEET, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

    public static final Item ARMOR_METALHELMET = new ArmorItem(TofuArmorMaterial.METAL, EquipmentSlotType.HEAD, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_METALCHESTPLATE = new ArmorItem(TofuArmorMaterial.METAL, EquipmentSlotType.CHEST, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_METALLEGGINS = new ArmorItem(TofuArmorMaterial.METAL, EquipmentSlotType.LEGS, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_METALBOOTS = new ArmorItem(TofuArmorMaterial.METAL, EquipmentSlotType.FEET, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));


    public static final Item KINUSWORD = new SwordItem(TofuItemTier.KINU, 0, -2.2F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item KINUAXE = new AxeItem(TofuItemTier.KINU, 0.0F, -2.25F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item KINUPICKAXE = new PickaxeItem(TofuItemTier.KINU, 0, -2.2F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item KINUSHOVEL = new ShovelItem(TofuItemTier.KINU, 0, -2.2F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

    public static final Item MOMENSWORD = new SwordItem(TofuItemTier.MOMEN, 0, -2.2F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item MOMENAXE = new AxeItem(TofuItemTier.MOMEN, 1.0F, -2.5F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item MOMENPICKAXE = new PickaxeItem(TofuItemTier.MOMEN, 0, -2.25F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item MOMENSHOVEL = new ShovelItem(TofuItemTier.MOMEN, 0, -2.25F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

    public static final Item SOLIDSWORD = new SwordItem(TofuItemTier.SOLID, 3, -2.3F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOLIDAXE = new AxeItem(TofuItemTier.SOLID, 7.0F, -2.9F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOLIDPICKAXE = new PickaxeItem(TofuItemTier.SOLID, 1, -2.9F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOLIDSHOVEL = new ShovelItem(TofuItemTier.SOLID, 1.5F, -2.9F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

    public static final Item METALSWORD = new SwordItem(TofuItemTier.METAL, 3, -2.3F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item METALAXE = new AxeItem(TofuItemTier.METAL, 6.0F, -3.0F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item METALPICKAXE = new PickaxeItem(TofuItemTier.METAL, 1, -2.9F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item METALSHOVEL = new ShovelItem(TofuItemTier.METAL, 1.5F, -2.9F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));



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
        register(registry, TOFUZUNDA, "tofuzunda");
        register(registry, TOFUCOOKIE, "tofucookie");
        register(registry, SEEDS_SOYBEAN, "seeds_soybeans");

        register(registry, EDAMAME, "edamame");
        register(registry, EDAMAME_BOILD, "edamame_boild");

        register(registry, SOYMILK_BUCKET, "bucketsoymilk");
        register(registry, SALT, "salt");
        register(registry, BITTERN, "bittern_bottle");
        register(registry, ZUNDA, "zunda");
        register(registry, ZUNDAMA, "zundama");
        register(registry, TOFUSTICK, "tofustick");
        register(registry, ZUNDARUBY, "zundaruby");
        register(registry, TOFUNIAN_SPAWNEGG, "tofunian_spawnegg");
        register(registry, TOFUSLIME_SPAWNEGG, "tofuslime_spawnegg");

        register(registry, ARMOR_KINUHELMET, "armorkinuhelmet");
        register(registry, ARMOR_KINUCHESTPLATE, "armorkinuchestplate");
        register(registry, ARMOR_KINULEGGINS, "armorkinuleggins");
        register(registry, ARMOR_KINUBOOTS, "armorkinuboots");
        register(registry, ARMOR_MOMENHELMET, "armormomenhelmet");
        register(registry, ARMOR_MOMENCHESTPLATE, "armormomenchestplate");
        register(registry, ARMOR_MOMENLEGGINS, "armormomenleggins");
        register(registry, ARMOR_MOMENBOOTS, "armormomenboots");
        register(registry, ARMOR_SOLIDHELMET, "armorsolidhelmet");
        register(registry, ARMOR_SOLIDCHESTPLATE, "armorsolidchestplate");
        register(registry, ARMOR_SOLIDLEGGINS, "armorsolidleggins");
        register(registry, ARMOR_SOLIDBOOTS, "armorsolidboots");
        register(registry, ARMOR_METALHELMET, "armormetalhelmet");
        register(registry, ARMOR_METALCHESTPLATE, "armormetalchestplate");
        register(registry, ARMOR_METALLEGGINS, "armormetalleggins");
        register(registry, ARMOR_METALBOOTS, "armormetalboots");

        register(registry, KINUSWORD, "swordkinu");
        register(registry, KINUAXE, "toolkinuaxe");
        register(registry, KINUPICKAXE, "toolkinupickaxe");
        register(registry, KINUSHOVEL, "toolkinushovel");
        register(registry, MOMENSWORD, "swordmomen");
        register(registry, MOMENAXE, "toolmomenaxe");
        register(registry, MOMENPICKAXE, "toolmomenpickaxe");
        register(registry, MOMENSHOVEL, "toolmomenshovel");
        register(registry, SOLIDSWORD, "swordsolid");
        register(registry, SOLIDAXE, "toolsolidaxe");
        register(registry, SOLIDPICKAXE, "toolsolidpickaxe");
        register(registry, SOLIDSHOVEL, "toolsolidshovel");
        register(registry, METALSWORD, "swordmetal");
        register(registry, METALAXE, "toolmetalaxe");
        register(registry, METALPICKAXE, "toolmetalpickaxe");
        register(registry, METALSHOVEL, "toolmetalshovel");
    }
}
