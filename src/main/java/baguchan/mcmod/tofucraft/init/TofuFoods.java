package baguchan.mcmod.tofucraft.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class TofuFoods {
    public static final Food TOFUKINU = new Food.Builder().hunger(2).saturation(0.15f).fastToEat().build();
    public static final Food TOFUMOMEN = new Food.Builder().hunger(2).saturation(0.15f).fastToEat().build();
    public static final Food TOFUISHI = new Food.Builder().hunger(2).saturation(0.2f).fastToEat().build();
    public static final Food TOFUGRILD = new Food.Builder().hunger(3).saturation(0.25f).fastToEat().build();
    public static final Food TOFUHELL = new Food.Builder().hunger(2).saturation(0.16f).fastToEat().effect(new EffectInstance(Effects.FIRE_RESISTANCE, 600), 1.0F).build();
    public static final Food COOKEDTOFU = new Food.Builder().hunger(4).saturation(0.3f).fastToEat().build();
    public static final Food TOFUFISH = new Food.Builder().hunger(2).saturation(0.15f).build();
    public static final Food COOKEDTOFUFISH = new Food.Builder().hunger(4).saturation(0.35f).build();
    public static final Food TOFUHAMBURG = new Food.Builder().hunger(6).saturation(0.42f).build();
    public static final Food TOFUHAMBURG_RAW = new Food.Builder().hunger(2).saturation(0.12f).build();
    public static final Food TOFUZUNDA = new Food.Builder().hunger(4).saturation(0.32f).fastToEat().build();

    public static final Food TOFUCOOKIE = new Food.Builder().hunger(3).saturation(0.32f).build();
    public static final Food SOYSTICK = new Food.Builder().hunger(4).saturation(0.36f).fastToEat().build();
    public static final Food TTTBURGER = new Food.Builder().hunger(7).saturation(0.5f).build();
    public static final Food RICEBURGER = new Food.Builder().hunger(9).saturation(0.54f).build();

    public static final Food SOYMILK = new Food.Builder().hunger(0).saturation(0.1f).build();
    public static final Food SOYMILK_ANNIN = new Food.Builder().hunger(0).saturation(0.12f).effect(new EffectInstance(Effects.ABSORPTION, 100), 1.0F).build();
    public static final Food SOYMILK_APPLE = new Food.Builder().hunger(0).saturation(0.12f).effect(new EffectInstance(Effects.RESISTANCE, 60), 1.0F).build();
    public static final Food BREWED_SOYMILK_ANNIN = new Food.Builder().hunger(0).saturation(0.12f).effect(new EffectInstance(Effects.ABSORPTION, 1200), 1.0F).build();
    public static final Food BREWED_SOYMILK_APPLE = new Food.Builder().hunger(0).saturation(0.12f).effect(new EffectInstance(Effects.RESISTANCE, 900), 1.0F).build();
    public static final Food SOYMILK_COCOA = new Food.Builder().hunger(0).saturation(0.12f).effect(new EffectInstance(Effects.JUMP_BOOST, 100), 1.0F).build();
    public static final Food SOYMILK_KINAKO = new Food.Builder().hunger(0).saturation(0.12f).effect(new EffectInstance(Effects.SPEED, 400), 1.0F).build();
    public static final Food SOYMILK_PUDDING = new Food.Builder().hunger(0).saturation(0.14f).effect(new EffectInstance(Effects.REGENERATION, 200), 1.0F).build();
    public static final Food SOYMILK_PUMPKIN = new Food.Builder().hunger(0).saturation(0.12f).effect(new EffectInstance(Effects.STRENGTH, 200), 1.0F).build();
    public static final Food SOYMILK_RAMUNE = new Food.Builder().hunger(1).saturation(0.13f).effect(new EffectInstance(Effects.DOLPHINS_GRACE, 400), 1.0F).build();
    public static final Food SOYMILK_STRAWBERRY = new Food.Builder().hunger(0).saturation(0.13f).effect(new EffectInstance(Effects.MINING_FATIGUE, 400), 1.0F).build();
    public static final Food SOYMILK_ZUNDA = new Food.Builder().hunger(0).saturation(0.13f).effect(new EffectInstance(Effects.NIGHT_VISION, 600), 1.0F).build();


    public static final Food HIYAYAKKO = new Food.Builder().hunger(3).saturation(0.22f).build();

    public static final Food SALTYMELON = new Food.Builder().hunger(3).saturation(0.16f).build();
    public static final Food EDAMAME = new Food.Builder().hunger(1).saturation(0.12f).fastToEat().build();
    public static final Food RICEBALL = new Food.Builder().hunger(3).saturation(0.32f).fastToEat().build();
    public static final Food RICEBALL_SALT = new Food.Builder().hunger(3).saturation(0.34f).fastToEat().build();

    public static final Food ZUNDAMOCHI = new Food.Builder().hunger(4).saturation(0.365f).fastToEat().build();
    public static final Food GOHEIMOCHI = new Food.Builder().hunger(4).saturation(0.36f).build();
    public static final Food YAKIONIGIRI_MISO = new Food.Builder().hunger(4).saturation(0.36f).build();
    public static final Food YAKIONIGIRI_SHOYU = new Food.Builder().hunger(4).saturation(0.36f).build();
    public static final Food KINAKOMANJU = new Food.Builder().hunger(3).saturation(0.20f).fastToEat().build();
    public static final Food ZUNDAMANJU = new Food.Builder().hunger(3).saturation(0.24f).fastToEat().build();
    public static final Food NETHERMANJU = new Food.Builder().hunger(4).saturation(0.25f).fastToEat().effect(new EffectInstance(Effects.FIRE_RESISTANCE, 600), 1.0F).build();

}
