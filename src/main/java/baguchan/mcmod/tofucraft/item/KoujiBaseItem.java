package baguchan.mcmod.tofucraft.item;

import baguchan.mcmod.tofucraft.init.TofuItems;
import baguchan.mcmod.tofucraft.utils.ItemStackUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class KoujiBaseItem extends Item {
    public KoujiBaseItem(Properties group) {
        super(group);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            CompoundNBT nbt = ItemStackUtil.getItemTagCompound(stack);
            if (!nbt.contains("timer")) {
                nbt.putInt("timer", 0);
            } else if (nbt.contains("timer") && nbt.getInt("timer") >= 18000) {
                ItemStack newstack = new ItemStack(TofuItems.KOUJI, 1);
                stack.shrink(1);
                player.inventory.addItemStackToInventory(newstack);
            } else if (nbt.contains("timer")) {
                nbt.putInt("timer", nbt.getInt("timer") + 1);
            }
        }
    }
}
