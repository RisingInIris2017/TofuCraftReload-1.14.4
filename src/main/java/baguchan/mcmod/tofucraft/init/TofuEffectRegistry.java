package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.effect.TofuResistanceEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.*;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuEffectRegistry {
    public static final Effect TOFU_RESISTANCE = new TofuResistanceEffect(EffectType.BENEFICIAL, 0xc7d8e2);
    public static final Effect UNSTABLE_RESISTANCE = new TofuResistanceEffect(EffectType.HARMFUL, 0x00494a);


    public static final Potion TOFU_RESISTANCE_POTION = new Potion(new EffectInstance(TofuEffectRegistry.TOFU_RESISTANCE, 1200));
    public static final Potion UNSTABLE_RESISTANCE_POTION = new Potion(new EffectInstance(TofuEffectRegistry.UNSTABLE_RESISTANCE, 1200));

    @SubscribeEvent
    public static void onRegisterPotions(RegistryEvent.Register<Potion> event) {
        event.getRegistry().register(TOFU_RESISTANCE_POTION.setRegistryName("tofu_resistance"));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.fromItems(TofuBlocks.TOFUBERRY), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), TOFU_RESISTANCE_POTION));
        event.getRegistry().register(UNSTABLE_RESISTANCE_POTION.setRegistryName("unstable_resistance"));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), TOFU_RESISTANCE_POTION)), Ingredient.fromItems(Items.FERMENTED_SPIDER_EYE), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), UNSTABLE_RESISTANCE_POTION));
    }

    @SubscribeEvent
    public static void registerEffect(RegistryEvent.Register<Effect> event) {
        event.getRegistry().register(TOFU_RESISTANCE.setRegistryName("tofu_resistance"));
        event.getRegistry().register(UNSTABLE_RESISTANCE.setRegistryName("unstable_resistance"));
    }

}
