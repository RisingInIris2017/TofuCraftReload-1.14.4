package baguchan.mcmod.tofucraft.item.base;

import net.minecraft.item.ItemStack;

public interface IEnergyExtractable {
    int drain(ItemStack inst, int amount, boolean simulate);
}
