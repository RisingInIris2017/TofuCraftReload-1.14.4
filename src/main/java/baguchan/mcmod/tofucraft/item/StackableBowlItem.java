package baguchan.mcmod.tofucraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class StackableBowlItem extends Item {
    public StackableBowlItem(Item.Properties p_i50054_1_) {
        super(p_i50054_1_);
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        ItemStack bowl = new ItemStack(Items.BOWL);


        if (stack.getCount() == 1) {
            return new ItemStack(Items.BOWL);
        } else {
            if (entityLiving instanceof PlayerEntity) {
                if (!((PlayerEntity) entityLiving).inventory.addItemStackToInventory(bowl)) {
                    worldIn.addEntity(new ItemEntity(worldIn, (double) entityLiving.posX, (double) entityLiving.posY + 0.5D, (double) entityLiving.posZ, bowl));
                }
            }

            return super.onItemUseFinish(stack, worldIn, entityLiving);
        }
    }
}
