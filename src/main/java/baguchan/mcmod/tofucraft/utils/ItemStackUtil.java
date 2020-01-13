package baguchan.mcmod.tofucraft.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ItemStackUtil {
    public static CompoundNBT getItemTagCompound(ItemStack stack) {
        return stack.getOrCreateTag();
    }
}
