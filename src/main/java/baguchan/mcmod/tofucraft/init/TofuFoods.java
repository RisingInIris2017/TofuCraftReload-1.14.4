package baguchan.mcmod.tofucraft.init;

import net.minecraft.item.Food;

public class TofuFoods {
    public static final Food TOFUKINU = new Food.Builder().hunger(2).saturation(0.15f).fastToEat().build();
    public static final Food TOFUMOMEN = new Food.Builder().hunger(2).saturation(0.15f).fastToEat().build();
    public static final Food TOFUISHI = new Food.Builder().hunger(2).saturation(0.2f).fastToEat().build();
    public static final Food TOFUGRILD = new Food.Builder().hunger(3).saturation(0.25f).fastToEat().build();
    public static final Food COOKEDTOFU = new Food.Builder().hunger(4).saturation(0.3f).fastToEat().build();
    public static final Food TOFUFISH = new Food.Builder().hunger(2).saturation(0.15f).build();
    public static final Food COOKEDTOFUFISH = new Food.Builder().hunger(4).saturation(0.35f).build();
    public static final Food TOFUZUNDA = new Food.Builder().hunger(4).saturation(0.32f).fastToEat().build();

    public static final Food TOFUCOOKIE = new Food.Builder().hunger(3).saturation(0.32f).build();
    public static final Food SOYSTICK = new Food.Builder().hunger(4).saturation(0.36f).fastToEat().build();
    public static final Food TTTBURGER = new Food.Builder().hunger(7).saturation(0.5f).build();
    public static final Food RICEBURGER = new Food.Builder().hunger(9).saturation(0.54f).build();

    public static final Food HIYAYAKKO = new Food.Builder().hunger(3).saturation(0.22f).build();

    public static final Food SALTYMELON = new Food.Builder().hunger(3).saturation(0.16f).build();
    public static final Food EDAMAME = new Food.Builder().hunger(1).saturation(0.12f).fastToEat().build();
    public static final Food RICEBALL = new Food.Builder().hunger(3).saturation(0.32f).fastToEat().build();
    public static final Food RICEBALL_SALT = new Food.Builder().hunger(3).saturation(0.34f).fastToEat().build();

    public static final Food ZUNDAMOCHI = new Food.Builder().hunger(5).saturation(0.365f).fastToEat().build();
    public static final Food GOHEIMOCHI = new Food.Builder().hunger(4).saturation(0.36f).build();
    public static final Food YAKIONIGIRI_MISO = new Food.Builder().hunger(4).saturation(0.36f).build();
    public static final Food YAKIONIGIRI_SHOYU = new Food.Builder().hunger(4).saturation(0.36f).build();
}
