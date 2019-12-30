package baguchan.mcmod.tofucraft.item;

import net.minecraft.item.ItemStack;

public class FoildDrinkItem extends DrinkItem {
    public FoildDrinkItem(Properties group) {
        super(group);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
