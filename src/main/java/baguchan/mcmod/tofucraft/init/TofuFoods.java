package baguchan.mcmod.tofucraft.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class TofuFoods {
    public static final Food TOFUKINU = new Food.Builder().hunger(2).saturation(0.15f).fastToEat().build();
    public static final Food TOFUMOMEN = new Food.Builder().hunger(2).saturation(0.15f).fastToEat().build();
    public static final Food TOFUISHI = new Food.Builder().hunger(2).saturation(0.2f).fastToEat().build();
    public static final Food TOFUGRILD = new Food.Builder().hunger(3).saturation(0.24f).fastToEat().build();
    public static final Food COOKEDTOFU = new Food.Builder().hunger(4).saturation(0.3f).fastToEat().build();
    public static final Food COOKEDTOFUFISH = new Food.Builder().hunger(4).saturation(0.34f).fastToEat().build();
    public static final Food TOFUZUNDA = new Food.Builder().hunger(4).saturation(0.3f).fastToEat().effect(new EffectInstance(Effects.REGENERATION, 100), 0.4F).build();

    public static final Food TOFUCOOKIE = new Food.Builder().hunger(3).saturation(0.32f).fastToEat().build();
    public static final Food EDAMAME = new Food.Builder().hunger(1).saturation(0.12f).fastToEat().build();
}
