package baguchan.mcmod.tofucraft.item;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.item.base.ItemTofuEnergyContained;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TofuForceCoreItem extends ItemTofuEnergyContained {
    public TofuForceCoreItem(Properties group) {
        super(group);
        this.addPropertyOverride(new ResourceLocation("broken"), (p_210312_0_, p_210312_1_, p_210312_2_) -> {
            return isUsable(p_210312_0_) ? 0.0F : 1.0F;
        });
    }

    public static boolean isUsable(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);

        if (entityIn instanceof LivingEntity) {
            LivingEntity entityLivingBase = (LivingEntity) entityIn;

            if (entityLivingBase.ticksExisted % 400 == 0 && isUsable(stack)) {
                if (entityLivingBase.getHealth() < entityLivingBase.getMaxHealth()) {
                    if (getEnergy(stack) >= 5) {
                        drain(stack, 5, false);

                    } else {
                        stack.damageItem(1, (LivingEntity) entityIn, (p_220036_0_) -> {
                            p_220036_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                        });

                        entityLivingBase.heal(1);
                    }
                }
            }
        }
    }

    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Item.getItemFromBlock(TofuBlocks.METALTOFU) || super.getIsRepairable(toRepair, repair);
    }

}
