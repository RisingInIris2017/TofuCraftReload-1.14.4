package baguchan.mcmod.tofucraft.init;


import baguchan.mcmod.tofucraft.TofuCraftCore;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID)
public class TofuTrades {
    @SubscribeEvent
    public static void onWandererTradesEvent(WandererTradesEvent event) {
        List<VillagerTrades.ITrade> trades = event.getGenericTrades();
        trades.add(new ItemsForEmeraldsTrade(TofuItems.TOFUSTICK, 8, 1, 4, 1));
    }


//	ItemsForEmeralds -> GET Items, GIVE Emeralds.
//	(Output item, Emerald cost, Amount of Item, Trade Limit, Experience)
//
//	EmeraldsForItems -> GET Emeralds, GIVE Items
//	(Input item, Amount of Item, Emerald return, Trade Limit, Experience)

    @SubscribeEvent
    public static void onVillagerTradesEvent(VillagerTradesEvent event) {
        List<VillagerTrades.ITrade> novice = event.getTrades().get(1);
        List<VillagerTrades.ITrade> apprentice = event.getTrades().get(2);
        List<VillagerTrades.ITrade> journeyman = event.getTrades().get(3);
        List<VillagerTrades.ITrade> expert = event.getTrades().get(4);
        List<VillagerTrades.ITrade> master = event.getTrades().get(5);

        if (event.getType() == TofuVillagers.TOFU_CRAFTSMAN) {
            novice.add(new EmeraldsForItemsTrade(Items.GLASS_BOTTLE, 4, 1, 8, 3));
            novice.add(new EmeraldsForItemsTrade(TofuItems.SEEDS_SOYBEAN, 18, 1, 8, 2));

            apprentice.add(new ItemsForEmeraldsTrade(TofuItems.TOFUGRILD, 1, 12, 8, 18));
            apprentice.add(new EmeraldsForItemsTrade(TofuItems.TOFUMOMEN, 26, 1, 8, 16));

            journeyman.add(new ItemsForEmeraldsTrade(TofuItems.TOFUMETAL_SHIELD, 1, 1, 4, 22));
            journeyman.add(new ItemsForEmeraldsTrade(TofuItems.ARMOR_METALBOOTS, 3, 1, 6, 23));
            journeyman.add(new ItemsForEmeraldsTrade(TofuItems.ARMOR_METALCHESTPLATE, 6, 1, 6, 24));
            journeyman.add(new ItemsForEmeraldsTrade(TofuItems.ARMOR_METALLEGGINS, 5, 1, 6, 23));

            expert.add(new ItemsForEmeraldsTrade(TofuItems.BREWED_SOYMILK_ANNIN_BOTTLE, 3, 1, 4, 30));
            expert.add(new ItemsForEmeraldsTrade(TofuItems.BREWED_SOYMILK_APPLE_BOTTLE, 2, 1, 4, 30));

            master.add(new ItemsForEmeraldsTrade(TofuItems.MORIJIO, 8, 1, 3, 20));
        }
    }

    public static class ItemsForEmeraldsTrade implements ITrade {

        private final ItemStack itemstack;
        private final int stackSize;
        private final int recievedSize;
        private final int maxUses;
        private final int givenExp;
        private final float priceMultiplier;

        public ItemsForEmeraldsTrade(Block block, int stackSize, int recievedSize, int maxUses, int givenExp) {

            this(new ItemStack(block), stackSize, recievedSize, maxUses, givenExp);

        }


        public ItemsForEmeraldsTrade(Item item, int stackSize, int recievedSize, int givenExp) {

            this(new ItemStack(item), stackSize, recievedSize, 12, givenExp);

        }


        public ItemsForEmeraldsTrade(Item item, int stackSize, int recievedSize, int maxUses, int givenExp) {

            this(new ItemStack(item), stackSize, recievedSize, maxUses, givenExp);

        }


        public ItemsForEmeraldsTrade(ItemStack stack, int stackSize, int recievedSize, int maxUses, int givenExp) {

            this(stack, stackSize, recievedSize, maxUses, givenExp, 0.05F);

        }


        public ItemsForEmeraldsTrade(ItemStack stack, int stackSize, int recievedSize, int maxUses, int givenExp, float priceMultiplier) {

            this.itemstack = stack;

            this.stackSize = stackSize;

            this.recievedSize = recievedSize;

            this.maxUses = maxUses;

            this.givenExp = givenExp;

            this.priceMultiplier = priceMultiplier;

        }


        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {

            return new MerchantOffer(new ItemStack(Items.EMERALD, this.stackSize), new ItemStack(this.itemstack.getItem(), this.recievedSize), this.maxUses, this.givenExp, this.priceMultiplier);

        }

    }


    public static class EmeraldsForItemsTrade implements ITrade {

        private final ItemStack itemstack;

        private final int stackSize;

        private final int recievedSize;

        private final int maxUses;

        private final int givenExp;

        private final float priceMultiplier;


        public EmeraldsForItemsTrade(Block block, int stackSize, int recievedSize, int maxUses, int givenExp) {

            this(new ItemStack(block), stackSize, recievedSize, maxUses, givenExp);

        }


        public EmeraldsForItemsTrade(Item item, int stackSize, int recievedSize, int givenExp) {

            this(new ItemStack(item), stackSize, recievedSize, 12, givenExp);

        }


        public EmeraldsForItemsTrade(Item item, int stackSize, int recievedSize, int maxUses, int givenExp) {

            this(new ItemStack(item), stackSize, recievedSize, maxUses, givenExp);

        }


        public EmeraldsForItemsTrade(ItemStack stack, int stackSize, int recievedSize, int maxUses, int givenExp) {

            this(stack, stackSize, recievedSize, maxUses, givenExp, 0.05F);

        }


        public EmeraldsForItemsTrade(ItemStack stack, int stackSize, int recievedSize, int maxUses, int givenExp, float priceMultiplier) {

            this.itemstack = stack;

            this.stackSize = stackSize;

            this.recievedSize = recievedSize;

            this.maxUses = maxUses;

            this.givenExp = givenExp;

            this.priceMultiplier = priceMultiplier;

        }


        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {

            return new MerchantOffer(new ItemStack(this.itemstack.getItem(), this.stackSize), new ItemStack(Items.EMERALD, this.recievedSize), this.maxUses, this.givenExp, this.priceMultiplier);

        }

    }


    public static class ItemsForEmeraldsAndItemsTrade implements ITrade {

        private final ItemStack buyingItem;

        private final int buyingItemCount;

        private final int emeraldCount;

        private final ItemStack sellingItem;

        private final int sellingItemCount;

        private final int maxUses;

        private final int givenExp;

        private final float priceMultiplier;


        public ItemsForEmeraldsAndItemsTrade(Item item, int stackSize, Item item2, int recievedSize, int maxUses, int givenExp) {

            this(new ItemStack(item), stackSize, 1, new ItemStack(item2), recievedSize, maxUses, givenExp, 0.05F);

        }


        public ItemsForEmeraldsAndItemsTrade(ItemStack stack, int stackSize, int emeraldCount, ItemStack recieved, int recievedSize, int maxUses, int givenExp, float priceMultiplier) {

            this.buyingItem = stack;

            this.buyingItemCount = stackSize;

            this.emeraldCount = emeraldCount;

            this.sellingItem = recieved;

            this.sellingItemCount = recievedSize;

            this.maxUses = maxUses;

            this.givenExp = givenExp;

            this.priceMultiplier = 0.05F;

        }


        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {

            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount), new ItemStack(this.buyingItem.getItem(), this.buyingItemCount), new ItemStack(this.sellingItem.getItem(), this.sellingItemCount), this.maxUses, this.givenExp, this.priceMultiplier);

        }

    }
}
