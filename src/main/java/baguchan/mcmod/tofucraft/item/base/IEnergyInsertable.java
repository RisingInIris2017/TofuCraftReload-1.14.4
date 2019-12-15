package baguchan.mcmod.tofucraft.item.base;

import net.minecraft.item.ItemStack;

public interface IEnergyInsertable {
    int fill(ItemStack inst, int energy, boolean simulate);
}
