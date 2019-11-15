package baguchan.mcmod.tofucraft.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SeasoningItem extends Item {
    public SeasoningItem(Properties group) {
        super(group);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        int dmg = itemStack.getDamage();
        if (dmg < this.getMaxDamage(itemStack)) {
            ItemStack stack = itemStack.copy();
            stack.setDamage(dmg + 1);
            return stack;
        } else
            return super.getContainerItem(itemStack);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        int dmg = stack.getDamage();
        if (dmg < this.getMaxDamage(stack)) {
            return true;
        } else {
            return super.hasContainerItem(stack);
        }
    }
}
