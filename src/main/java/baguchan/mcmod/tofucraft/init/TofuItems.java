package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.render.item.TofuShieldItemRender;
import baguchan.mcmod.tofucraft.entity.projectile.ZundaArrowEntity;
import baguchan.mcmod.tofucraft.item.*;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.*;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuItems {
    public static final Item TOFUKINU = new Item(new Item.Properties().food(TofuFoods.TOFUKINU).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUMOMEN = new Item(new Item.Properties().food(TofuFoods.TOFUMOMEN).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUISHI = new Item(new Item.Properties().food(TofuFoods.TOFUISHI).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUMETAL = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUDIAMOND = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUDIAMOND_NUGGET = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUGRILD = new Item(new Item.Properties().food(TofuFoods.TOFUGRILD).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUHELL = new Item(new Item.Properties().food(TofuFoods.TOFUHELL).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUZUNDA = new Item(new Item.Properties().food(TofuFoods.TOFUZUNDA).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUMINCED = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

    public static final Item TOFUFISH = new Item(new Item.Properties().food(TofuFoods.TOFUFISH).group(TofuItemGroup.TOFUCRAFT));
    public static final Item COOKED_TOFUFISH = new Item(new Item.Properties().food(TofuFoods.COOKEDTOFUFISH).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUHAMBURG = new Item(new Item.Properties().food(TofuFoods.TOFUHAMBURG).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUHAMBURG_RAW = new Item(new Item.Properties().food(TofuFoods.TOFUHAMBURG_RAW).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUCOOKIE = new Item(new Item.Properties().food(TofuFoods.TOFUCOOKIE).group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOYSTICK = new Item(new Item.Properties().food(TofuFoods.SOYSTICK).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TTTBURGER = new Item(new Item.Properties().food(TofuFoods.TTTBURGER).group(TofuItemGroup.TOFUCRAFT));
    public static final Item RICEBURGER = new Item(new Item.Properties().food(TofuFoods.RICEBURGER).group(TofuItemGroup.TOFUCRAFT));
    //Drink
    public static final Item SOYMILK_BOTTLE = new DrinkItem(new Item.Properties().food(TofuFoods.SOYMILK).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOYMILK_ANNIN_BOTTLE = new DrinkItem(new Item.Properties().food(TofuFoods.SOYMILK_ANNIN).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOYMILK_APPLE_BOTTLE = new DrinkItem(new Item.Properties().food(TofuFoods.SOYMILK_APPLE).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
    public static final Item BREWED_SOYMILK_ANNIN_BOTTLE = new FoildDrinkItem(new Item.Properties().food(TofuFoods.BREWED_SOYMILK_ANNIN).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
    public static final Item BREWED_SOYMILK_APPLE_BOTTLE = new FoildDrinkItem(new Item.Properties().food(TofuFoods.BREWED_SOYMILK_APPLE).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOYMILK_COCOA_BOTTLE = new DrinkItem(new Item.Properties().food(TofuFoods.SOYMILK_COCOA).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOYMILK_KINAKO_BOTTLE = new DrinkItem(new Item.Properties().food(TofuFoods.SOYMILK_KINAKO).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOYMILK_PUDDING_BOTTLE = new DrinkItem(new Item.Properties().food(TofuFoods.SOYMILK_PUDDING).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOYMILK_PUMPKIN_BOTTLE = new DrinkItem(new Item.Properties().food(TofuFoods.SOYMILK_PUMPKIN).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOYMILK_RAMUNE_BOTTLE = new DrinkItem(new Item.Properties().food(TofuFoods.SOYMILK_RAMUNE).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOYMILK_STRAWBERRY_BOTTLE = new DrinkItem(new Item.Properties().food(TofuFoods.SOYMILK_STRAWBERRY).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOYMILK_ZUNDA_BOTTLE = new DrinkItem(new Item.Properties().food(TofuFoods.SOYMILK_ZUNDA).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));

    public static final Item HIYAYAKKO = new StackableBowlItem(new Item.Properties().food(TofuFoods.HIYAYAKKO).containerItem(Items.BOWL).maxStackSize(6).group(TofuItemGroup.TOFUCRAFT));

    public static final Item SALTYMELON = new Item(new Item.Properties().food(TofuFoods.SALTYMELON).group(TofuItemGroup.TOFUCRAFT));

    public static final Item SEEDS_SOYBEAN = new BlockNamedItem(TofuBlocks.SOYBEAN,new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item SEEDS_SOYBEAN_NETHER = new BlockNamedItem(TofuBlocks.SOYBEAN_NETHER, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item KINAKO = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOYBEAN_PARCHED = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item FUKUMAME = new FukumameItem(new Item.Properties().maxDamage(64).group(TofuItemGroup.TOFUCRAFT));
    public static final Item EDAMAME = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item EDAMAME_BOILD = new Item(new Item.Properties().food(TofuFoods.EDAMAME).group(TofuItemGroup.TOFUCRAFT));
    public static final Item RICE = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item SEEDS_RICE = new BlockRiceItem(TofuBlocks.RICE, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ONIGIRI = new Item(new Item.Properties().food(TofuFoods.RICEBALL).group(TofuItemGroup.TOFUCRAFT));
    public static final Item ONIGIRI_SALT = new Item(new Item.Properties().food(TofuFoods.RICEBALL_SALT).group(TofuItemGroup.TOFUCRAFT));
    public static final Item ZUNDAMOCHI = new Item(new Item.Properties().food(TofuFoods.ZUNDAMOCHI).group(TofuItemGroup.TOFUCRAFT));
    public static final Item GOHEIMOCHI = new Item(new Item.Properties().food(TofuFoods.GOHEIMOCHI).group(TofuItemGroup.TOFUCRAFT));
    public static final Item YAKIONIGIRI_MISO = new Item(new Item.Properties().food(TofuFoods.YAKIONIGIRI_MISO).group(TofuItemGroup.TOFUCRAFT));
    public static final Item YAKIONIGIRI_SHOYU = new Item(new Item.Properties().food(TofuFoods.YAKIONIGIRI_SHOYU).group(TofuItemGroup.TOFUCRAFT));
    public static final Item KINAKOMANJU = new Item(new Item.Properties().food(TofuFoods.KINAKOMANJU).group(TofuItemGroup.TOFUCRAFT));
    public static final Item ZUNDAMANJU = new Item(new Item.Properties().food(TofuFoods.ZUNDAMANJU).group(TofuItemGroup.TOFUCRAFT));

    public static final Item SOYMILK_BUCKET = new BucketItem(TofuFluids.SOYMILK, (new Item.Properties()).containerItem(Items.BUCKET).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOYMILK_HELL_BUCKET = new BucketItem(TofuFluids.SOYMILK_HELL, (new Item.Properties()).containerItem(Items.BUCKET).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));

    public static final Item SALT = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item MORIJIO = new MorijioItem(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item BITTERN = new BitternItem(new Item.Properties().containerItem(Items.GLASS_BOTTLE).group(TofuItemGroup.TOFUCRAFT));
    public static final Item LEEK = new BlockNamedItem(TofuBlocks.LEEKCROP, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

    public static final Item KOUJIBASE = new KoujiBaseItem(new Item.Properties().maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
    public static final Item KOUJI = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item MISO = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item SOYSAUCE = new SeasoningItem(new Item.Properties().maxStackSize(1).maxDamage(32).containerItem(Items.GLASS_BOTTLE).group(TofuItemGroup.TOFUCRAFT));
    public static final Item DASHI = new SeasoningItem(new Item.Properties().maxStackSize(1).maxDamage(32).containerItem(Items.GLASS_BOTTLE).group(TofuItemGroup.TOFUCRAFT));


    public static final Item ZUNDA = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ZUNDAMA = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUSTICK = new TofuStickItem(new Item.Properties().rarity(Rarity.RARE).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFU_RADER = new TofuSlimeRadarItem(new Item.Properties().maxStackSize(1).maxDamage(120).group(TofuItemGroup.TOFUCRAFT));
    public static final Item ZUNDARUBY = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUNIAN_SPAWNEGG = new SpawnEggItem(TofuEntitys.TOFUNIAN, 0xEBE8E8, 0xCACFA1, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUSLIME_SPAWNEGG = new SpawnEggItem(TofuEntitys.TOFUSLIME, 0xEBE8E8, 0x2E2E2E, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUCOW_SPAWNEGG = new SpawnEggItem(TofuEntitys.TOFUCOW, 0xEBE8E8, 0xA3A3A3, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUFISH_SPAWNEGG = new SpawnEggItem(TofuEntitys.TOFUFISH, 0xEBE8E8, 0x3a3e3f, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUCHINGER_SPAWNEGG = new SpawnEggItem(TofuEntitys.TOFUCHINGER, 0xEBE8E8, 0xB3B3B3, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUSPIDER_SPAWNEGG = new SpawnEggItem(TofuEntitys.TOFUSPIDER, 0xEBE8E8, 0x2E2E2E, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUCREEPER_SPAWNEGG = new SpawnEggItem(TofuEntitys.TOFUCREEPER, 0xefeedf, 0x82817b, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUTURRET_SPAWNEGG = new SpawnEggItem(TofuEntitys.TOFUTURRET, 0xe5e0bd, 0x9cd6f5, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUMIND_SPAWNEGG = new SpawnEggItem(TofuEntitys.TOFUMIND, 0xe5e0bd, 0x9cd6f5, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

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

    public static final Item ARMOR_DIAMONDHELMET = new ArmorItem(TofuArmorMaterial.DIAMOND, EquipmentSlotType.HEAD, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_DIAMONDCHESTPLATE = new ArmorItem(TofuArmorMaterial.DIAMOND, EquipmentSlotType.CHEST, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_DIAMONDLEGGINS = new ArmorItem(TofuArmorMaterial.DIAMOND, EquipmentSlotType.LEGS, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item ARMOR_DIAMONDBOOTS = new ArmorItem(TofuArmorMaterial.DIAMOND, EquipmentSlotType.FEET, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));


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

    public static final Item TOFUDIAMONDSWORD = new SwordItem(TofuItemTier.TOFUDIAMOND, 3, -2.3F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUDIAMONDAXE = new AxeItem(TofuItemTier.TOFUDIAMOND, 6.0F, -3.0F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUDIAMONDPICKAXE = new PickaxeItem(TofuItemTier.TOFUDIAMOND, 1, -2.9F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUDIAMONDSHOVEL = new ShovelItem(TofuItemTier.TOFUDIAMOND, 1.5F, -2.9F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

    public static final Item TOFUHOE = new TofuHoeItem(new Item.Properties().maxDamage(280).group(TofuItemGroup.TOFUCRAFT));
    public static final Item ZUNDABOW = new ZundaBowItem(new Item.Properties().maxDamage(420).group(TofuItemGroup.TOFUCRAFT));
    public static final Item ZUNDAARROW = new ZundaArrowItem(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item BUGLE = new BugleItem(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUWOOD_STICK = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

    public static final Item TOFUISHI_SHIELD = new TofuShieldItem(new Item.Properties().maxDamage(320).setISTER(() -> TofuShieldItemRender::new).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUMETAL_SHIELD = new TofuShieldItem(new Item.Properties().maxDamage(450).setISTER(() -> TofuShieldItemRender::new).group(TofuItemGroup.TOFUCRAFT));

    public static final Item TOFUFORCE_CORE = new TofuForceCoreItem(new Item.Properties().maxDamage(340).group(TofuItemGroup.TOFUCRAFT));
    public static final Item TOFUGAUNTLET = new TofuGauntletItem(new Item.Properties().maxDamage(320).group(TofuItemGroup.TOFUCRAFT));


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
        register(registry, TOFUDIAMOND, "tofudiamond");
        register(registry, TOFUDIAMOND_NUGGET, "tofudiamondnugget");
        register(registry, TOFUGRILD, "tofugrilled");
        register(registry, TOFUHELL, "tofuhell");
        register(registry, TOFUZUNDA, "tofuzunda");
        register(registry, TOFUMINCED, "tofuminced");

        register(registry, TOFUFISH, "raw_tofufish");
        register(registry, COOKED_TOFUFISH, "cooked_tofufish");
        register(registry, TOFUHAMBURG, "tofuhamburg");
        register(registry, TOFUHAMBURG_RAW, "tofuhamburg_raw");
        register(registry, TOFUCOOKIE, "tofucookie");
        register(registry, SOYSTICK, "soystick");
        register(registry, TTTBURGER, "tttburger");
        register(registry, RICEBURGER, "riceburger");

        register(registry, SOYMILK_BOTTLE, "soymilk");
        register(registry, SOYMILK_ANNIN_BOTTLE, "soymilk_annin");
        register(registry, SOYMILK_APPLE_BOTTLE, "soymilk_apple");
        register(registry, BREWED_SOYMILK_ANNIN_BOTTLE, "brewed_soymilk_annin");
        register(registry, BREWED_SOYMILK_APPLE_BOTTLE, "brewed_soymilk_apple");
        register(registry, SOYMILK_COCOA_BOTTLE, "soymilk_cocoa");
        register(registry, SOYMILK_KINAKO_BOTTLE, "soymilk_kinako");
        register(registry, SOYMILK_PUDDING_BOTTLE, "soymilk_pudding");
        register(registry, SOYMILK_PUMPKIN_BOTTLE, "soymilk_pumpkin");
        register(registry, SOYMILK_RAMUNE_BOTTLE, "soymilk_ramune");
        register(registry, SOYMILK_STRAWBERRY_BOTTLE, "soymilk_strawberry");
        register(registry, SOYMILK_ZUNDA_BOTTLE, "soymilk_zunda");

        register(registry, HIYAYAKKO, "hiyayakko");
        register(registry, SALTYMELON, "saltymelon");
        register(registry, SEEDS_SOYBEAN, "seeds_soybeans");
        register(registry, SEEDS_SOYBEAN_NETHER, "seeds_soybeans_nether");
        register(registry, SOYBEAN_PARCHED, "soybeans_parched");
        register(registry, KINAKO, "kinako");

        register(registry, EDAMAME, "edamame");
        register(registry, EDAMAME_BOILD, "edamame_boild");
        register(registry, FUKUMAME, "fukumame");

        register(registry, RICE, "rice");
        register(registry, SEEDS_RICE, "seeds_rice");
        register(registry, ONIGIRI, "onigiri");
        register(registry, ONIGIRI_SALT, "onigirisalt");
        register(registry, ZUNDAMOCHI, "zundamochi");
        register(registry, GOHEIMOCHI, "goheimochi");
        register(registry, YAKIONIGIRI_MISO, "yakionigiri_miso");
        register(registry, YAKIONIGIRI_SHOYU, "yakionigiri_shoyu");
        register(registry, KINAKOMANJU, "kinakomanju");
        register(registry, ZUNDAMANJU, "zundamanju");

        register(registry, SOYMILK_BUCKET, "bucketsoymilk");
        register(registry, SOYMILK_HELL_BUCKET, "bucketsoymilk_hell");
        register(registry, SALT, "salt");
        register(registry, MORIJIO, "morijio");
        register(registry, BITTERN, "bittern_bottle");
        register(registry, LEEK, "leek");
        register(registry, KOUJIBASE, "koujibase");
        register(registry, KOUJI, "kouji");
        register(registry, MISO, "miso");
        register(registry, SOYSAUCE, "bottlesoysause");
        register(registry, DASHI, "bottle_dashi");
        register(registry, ZUNDA, "zunda");
        register(registry, ZUNDAMA, "zundama");
        register(registry, TOFUSTICK, "tofustick");
        register(registry, TOFU_RADER, "tofuradar");
        register(registry, ZUNDARUBY, "zundaruby");
        register(registry, TOFUNIAN_SPAWNEGG, "tofunian_spawnegg");
        register(registry, TOFUSLIME_SPAWNEGG, "tofuslime_spawnegg");
        register(registry, TOFUCOW_SPAWNEGG, "tofucow_spawnegg");
        register(registry, TOFUFISH_SPAWNEGG, "tofufish_spawnegg");
        register(registry, TOFUCHINGER_SPAWNEGG, "tofuchinger_spawnegg");
        register(registry, TOFUSPIDER_SPAWNEGG, "tofuspider_spawnegg");
        register(registry, TOFUCREEPER_SPAWNEGG, "tofucreeper_spawnegg");
        register(registry, TOFUTURRET_SPAWNEGG, "tofuturret_spawnegg");
        register(registry, TOFUMIND_SPAWNEGG, "tofumind_spawnegg");

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
        register(registry, ARMOR_DIAMONDHELMET, "armordiamondhelmet");
        register(registry, ARMOR_DIAMONDCHESTPLATE, "armordiamondchestplate");
        register(registry, ARMOR_DIAMONDLEGGINS, "armordiamondleggins");
        register(registry, ARMOR_DIAMONDBOOTS, "armordiamondboots");

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
        register(registry, TOFUDIAMONDSWORD, "sworddiamond");
        register(registry, TOFUDIAMONDAXE, "tooldiamondaxe");
        register(registry, TOFUDIAMONDPICKAXE, "tooldiamondpickaxe");
        register(registry, TOFUDIAMONDSHOVEL, "tooldiamondshovel");

        register(registry, TOFUHOE, "tofuhoe");
        register(registry, ZUNDABOW, "zundabow");
        register(registry, ZUNDAARROW, "zunda_arrow");
        register(registry, BUGLE, "bugle");
        register(registry, TOFUWOOD_STICK, "tofuwood_stick");

        register(registry, TOFUISHI_SHIELD, "tofuishi_shield");
        register(registry, TOFUMETAL_SHIELD, "tofumetal_shield");
        register(registry, TOFUFORCE_CORE, "tofuforce_core");
        register(registry, TOFUGAUNTLET, "tofu_gauntlet");

        ComposterBlock.CHANCES.put(SEEDS_SOYBEAN, 0.3F);
        ComposterBlock.CHANCES.put(EDAMAME, 0.35F);

        DispenserBlock.registerDispenseBehavior(ZUNDAARROW, new ProjectileDispenseBehavior() {
            @Override
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                AbstractArrowEntity abstractarrowentity = new ZundaArrowEntity(worldIn, position.getX(), position.getY(), position.getZ());
                abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
                return abstractarrowentity;
            }
        });

        IDispenseItemBehavior idispenseitembehavior = new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior field_218405_b = new DefaultDispenseItemBehavior();

            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                BucketItem bucketitem = (BucketItem) stack.getItem();
                BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                World world = source.getWorld();
                if (bucketitem.tryPlaceContainedLiquid((PlayerEntity) null, world, blockpos, (BlockRayTraceResult) null)) {
                    bucketitem.onLiquidPlaced(world, stack, blockpos);
                    return new ItemStack(Items.BUCKET);
                } else {
                    return this.field_218405_b.dispense(source, stack);
                }
            }
        };
        DispenserBlock.registerDispenseBehavior(SOYMILK_BUCKET, idispenseitembehavior);
        DispenserBlock.registerDispenseBehavior(SOYMILK_HELL_BUCKET, idispenseitembehavior);


        IDispenseItemBehavior bitternItemBehavior = new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior field_218405_b = new DefaultDispenseItemBehavior();

            @Override
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                World world = source.getWorld();
                IFluidState ifluidstate = world.getFluidState(blockpos);
                if ((ifluidstate.getFluid() == TofuFluids.SOYMILK)) {
                    world.setBlockState(blockpos, TofuBlocks.KINUTOFU.getDefaultState(), 11);

                    Direction direction = source.getBlockState().get(DispenserBlock.FACING);
                    IPosition iposition = DispenserBlock.getDispensePosition(source);
                    doDispense(source.getWorld(), new ItemStack(Items.GLASS_BOTTLE), 6, direction, iposition);

                    stack.shrink(1);
                    return stack;
                } else {
                    return this.field_218405_b.dispense(source, stack);
                }
            }
        };
        DispenserBlock.registerDispenseBehavior(BITTERN, bitternItemBehavior);

        IDispenseItemBehavior bugleItemBehavior = new DefaultDispenseItemBehavior() {
            @Override
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                source.getWorld().playSound((PlayerEntity) null, source.getBlockPos(), TofuSounds.TOFUBUGLE, SoundCategory.BLOCKS, 40F, 1.0F);
                return stack;
            }
        };
        DispenserBlock.registerDispenseBehavior(BUGLE, bugleItemBehavior);

        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(new ItemStack(SOYMILK_ANNIN_BOTTLE)), Ingredient.fromItems(TofuBlocks.TOFUBERRY), new ItemStack(BREWED_SOYMILK_ANNIN_BOTTLE));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(new ItemStack(SOYMILK_APPLE_BOTTLE)), Ingredient.fromItems(TofuBlocks.TOFUBERRY), new ItemStack(BREWED_SOYMILK_APPLE_BOTTLE));

    }
}
