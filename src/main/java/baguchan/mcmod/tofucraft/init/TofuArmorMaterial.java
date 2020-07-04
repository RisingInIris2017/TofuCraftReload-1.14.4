package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

public class TofuArmorMaterial implements IArmorMaterial {
    private String name;
    private int durability;
    private int[] damageReduction;
    private int encantability;
    private SoundEvent sound;
    private float toughness;
    private float knockback_resistance;
    private Ingredient ingredient = null;

    public TofuArmorMaterial(String name, int durability, int[] damageReduction, int encantability, SoundEvent sound, float toughness, float knockback_resistance) {
        this.name = TofuCraftCore.MODID + ":" + name;
        this.durability = durability;
        this.damageReduction = damageReduction;
        this.encantability = encantability;
        this.sound = sound;
        this.toughness = toughness;
        this.knockback_resistance = knockback_resistance;
    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return durability;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return damageReduction[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return encantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return sound;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return ingredient == null ? Ingredient.EMPTY : ingredient;
    }

    public TofuArmorMaterial setRepairMaterial(Ingredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float func_230304_f_() {
        return knockback_resistance;
    }
}