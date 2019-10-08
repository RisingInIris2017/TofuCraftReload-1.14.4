package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.Set;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VillagerProfessions {
    public static final PointOfInterestType TOFUCRAFTSMAN = registerInterest("tofu_craftsman", getAllStates(TofuBlocks.SALTPAN), 1, SoundEvents.BLOCK_WATER_AMBIENT, 1);

    public static final VillagerProfession TOFUCRAFTSMAN_PROFESSION = registerProfession("tofu_craftsman", TOFUCRAFTSMAN);

    @SubscribeEvent
    public static void registerInterestType(RegistryEvent.Register<PointOfInterestType> event) {
        event.getRegistry().register(TOFUCRAFTSMAN.setRegistryName("tofu_craftsman"));
    }

    @SubscribeEvent
    public static void registerProfession(RegistryEvent.Register<VillagerProfession> event) {
        VillagerTrades.field_221239_a.put(TOFUCRAFTSMAN_PROFESSION, func_221238_a(ImmutableMap.of(1, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(TofuItems.SEEDS_SOYBEAN, 14, 20, 2), new EmeraldForItemsTrade(TofuItems.SALT, 14, 22, 2), new ItemsForEmeraldsTrade(TofuItems.TOFUMOMEN, 1, 12, 2)}, 2, new VillagerTrades.ITrade[]{new EmeraldForItemsTrade(Items.GLASS_BOTTLE, 4, 8, 2)})));
        event.getRegistry().register(TOFUCRAFTSMAN_PROFESSION.setRegistryName("tofu_craftsman"));
    }

    private static Int2ObjectMap<VillagerTrades.ITrade[]> func_221238_a(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    private static Set<BlockState> getAllStates(Block p_221042_0_) {
        return ImmutableSet.copyOf(p_221042_0_.getStateContainer().getValidStates());
    }

    private static PointOfInterestType registerInterest(String key, Set<BlockState> p_221051_1_, int p_221051_2_, @Nullable SoundEvent p_221051_3_, int p_221051_4_) {
        return new PointOfInterestType(key, p_221051_1_, p_221051_2_, p_221051_3_, p_221051_4_);
    }

    static VillagerProfession registerProfession(String key, PointOfInterestType p_221147_1_) {
        return registerProfession(key, p_221147_1_, ImmutableSet.of(), ImmutableSet.of());
    }

    static VillagerProfession registerProfession(String key, PointOfInterestType p_221148_1_, ImmutableSet<Item> p_221148_2_, ImmutableSet<Block> p_221148_3_) {
        return new VillagerProfession(key, p_221148_1_, p_221148_2_, p_221148_3_);
    }

    static class EmeraldForItemsTrade implements VillagerTrades.ITrade {
        private final Item field_221183_a;
        private final int field_221184_b;
        private final int field_221185_c;
        private final int field_221186_d;
        private final float field_221187_e;

        public EmeraldForItemsTrade(IItemProvider p_i50539_1_, int p_i50539_2_, int p_i50539_3_, int p_i50539_4_) {
            this.field_221183_a = p_i50539_1_.asItem();
            this.field_221184_b = p_i50539_2_;
            this.field_221185_c = p_i50539_3_;
            this.field_221186_d = p_i50539_4_;
            this.field_221187_e = 0.05F;
        }

        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
            ItemStack itemstack = new ItemStack(this.field_221183_a, this.field_221184_b);
            return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.field_221185_c, this.field_221186_d, this.field_221187_e);
        }
    }

    static class ItemsForEmeraldsTrade implements VillagerTrades.ITrade {
        private final ItemStack field_221208_a;
        private final int field_221209_b;
        private final int field_221210_c;
        private final int field_221211_d;
        private final int field_221212_e;
        private final float field_221213_f;

        public ItemsForEmeraldsTrade(Block p_i50528_1_, int p_i50528_2_, int p_i50528_3_, int p_i50528_4_, int p_i50528_5_) {
            this(new ItemStack(p_i50528_1_), p_i50528_2_, p_i50528_3_, p_i50528_4_, p_i50528_5_);
        }

        public ItemsForEmeraldsTrade(Item p_i50529_1_, int p_i50529_2_, int p_i50529_3_, int p_i50529_4_) {
            this(new ItemStack(p_i50529_1_), p_i50529_2_, p_i50529_3_, 12, p_i50529_4_);
        }

        public ItemsForEmeraldsTrade(Item p_i50530_1_, int p_i50530_2_, int p_i50530_3_, int p_i50530_4_, int p_i50530_5_) {
            this(new ItemStack(p_i50530_1_), p_i50530_2_, p_i50530_3_, p_i50530_4_, p_i50530_5_);
        }

        public ItemsForEmeraldsTrade(ItemStack p_i50531_1_, int p_i50531_2_, int p_i50531_3_, int p_i50531_4_, int p_i50531_5_) {
            this(p_i50531_1_, p_i50531_2_, p_i50531_3_, p_i50531_4_, p_i50531_5_, 0.05F);
        }

        public ItemsForEmeraldsTrade(ItemStack p_i50532_1_, int p_i50532_2_, int p_i50532_3_, int p_i50532_4_, int p_i50532_5_, float p_i50532_6_) {
            this.field_221208_a = p_i50532_1_;
            this.field_221209_b = p_i50532_2_;
            this.field_221210_c = p_i50532_3_;
            this.field_221211_d = p_i50532_4_;
            this.field_221212_e = p_i50532_5_;
            this.field_221213_f = p_i50532_6_;
        }

        public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.field_221209_b), new ItemStack(this.field_221208_a.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
        }
    }
}
