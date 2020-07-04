package baguchan.mcmod.tofucraft.item;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class TofuForceCoreItem extends Item {
    public TofuForceCoreItem(Properties group) {
        super(group);
       /* this.(new ResourceLocation("broken"), (p_210312_0_, p_210312_1_, p_210312_2_) -> {
            return isUsable(p_210312_0_) ? 0.0F : 1.0F;
        });*/
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

                    stack.damageItem(1, (LivingEntity) entityIn, (p_220036_0_) -> {
                        p_220036_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                    });

                    entityLivingBase.heal(1);
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

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (!isUsable(stack)) {
            tooltip.add(new TranslationTextComponent("tooltip.tofucraft.tofuforce.broken").func_240701_a_(TextFormatting.ITALIC).func_240701_a_(TextFormatting.GRAY));
        }
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Item.getItemFromBlock(TofuBlocks.METALTOFU) || super.getIsRepairable(toRepair, repair);
    }
}
