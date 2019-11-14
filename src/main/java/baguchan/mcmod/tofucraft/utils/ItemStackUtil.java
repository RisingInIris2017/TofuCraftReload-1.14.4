package baguchan.mcmod.tofucraft.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ItemStackUtil {
    public static CompoundNBT getItemTagCompound(ItemStack stack) {
        CompoundNBT tag;
        if (stack.hasTag()) {
            tag = stack.getTag();
        } else {
            tag = new CompoundNBT();
            stack.setTag(tag);
        }
        return tag;
    }
}
