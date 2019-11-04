package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class TofuArmorMaterial {
    private static final int[] DURABILITY = new int[]{13, 15, 16, 11};
    public static final IArmorMaterial KINU = new Builder()
            .withName(TofuCraftCore.MODID + ":armor_kinu")
            .withDurabilityFactor(1)
            .withDamageReductionAmounts(new int[]{0, 0, 0, 0})
            .withEnchantability(8)
            .withSound(SoundEvents.BLOCK_WOOL_PLACE)
            .withRepairMaterial(Ingredient.fromItems(TofuItems.TOFUKINU))
            .build();
    public static final IArmorMaterial MOMEN = new Builder()
            .withName(TofuCraftCore.MODID + ":armor_momen")
            .withDurabilityFactor(1)
            .withDamageReductionAmounts(new int[]{0, 1, 1, 0})
            .withEnchantability(10)
            .withSound(SoundEvents.BLOCK_WOOL_PLACE)
            .withRepairMaterial(Ingredient.fromItems(TofuItems.TOFUMOMEN))
            .build();
    public static final IArmorMaterial SOLID = new Builder()
            .withName(TofuCraftCore.MODID + ":armor_solid")
            .withDurabilityFactor(10)
            .withDamageReductionAmounts(new int[]{2, 3, 4, 2})
            .withEnchantability(16)
            .withRepairMaterial(Ingredient.fromItems(TofuItems.TOFUISHI))
            .build();
    public static final IArmorMaterial METAL = new Builder()
            .withName(TofuCraftCore.MODID + ":armor_metal")
            .withDurabilityFactor(15)
            .withDamageReductionAmounts(new int[]{2, 5, 6, 2})
            .withEnchantability(12)
            .withSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
            .withRepairMaterial(Ingredient.fromItems(TofuItems.TOFUMETAL))
            .build();
    public static final IArmorMaterial DIAMOND = new Builder()
            .withName(TofuCraftCore.MODID + ":armor_diamond")
            .withDurabilityFactor(35)
            .withDamageReductionAmounts(new int[]{3, 6, 8, 3})
            .withEnchantability(10)
            .withToughness(2.5F)
            .withSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
            .withRepairMaterial(Ingredient.fromItems(TofuItems.TOFUDIAMOND))
            .build();

    private static class Builder {
        private String name;
        private int durabilityFactor;
        private int[] damageReductionAmounts;
        private int enchantability;
        private SoundEvent sound = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
        private Ingredient repairMaterial;
        private float toughness;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDurabilityFactor(int durabilityFactor) {
            this.durabilityFactor = durabilityFactor;
            return this;
        }

        public Builder withDamageReductionAmounts(int[] damageReductionAmount) {
            this.damageReductionAmounts = damageReductionAmount;
            return this;
        }

        public Builder withEnchantability(int enchantability) {
            this.enchantability = enchantability;
            return this;
        }

        public Builder withSound(SoundEvent sound) {
            this.sound = sound;
            return this;
        }

        public Builder withRepairMaterial(Ingredient repairMaterial) {
            this.repairMaterial = repairMaterial;
            return this;
        }

        public Builder withToughness(float toughness) {
            this.toughness = toughness;
            return this;
        }

        public IArmorMaterial build() {
            return new IArmorMaterial() {
                @Override
                public int getDurability(EquipmentSlotType slot) {
                    return DURABILITY[slot.getIndex()] * Builder.this.durabilityFactor;
                }

                @Override
                public int getDamageReductionAmount(EquipmentSlotType slot) {
                    return Builder.this.damageReductionAmounts[slot.getIndex()];
                }

                @Override
                public int getEnchantability() {
                    return Builder.this.enchantability;
                }

                @Override
                public SoundEvent getSoundEvent() {
                    return Builder.this.sound;
                }

                @Override
                public Ingredient getRepairMaterial() {
                    return Builder.this.repairMaterial;
                }

                @Override
                public String getName() {
                    return Builder.this.name;
                }

                @Override
                public float getToughness() {
                    return Builder.this.toughness;
                }
            };
        }
    }
}