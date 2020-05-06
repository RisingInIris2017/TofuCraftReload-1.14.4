package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class EatOffhandFoodGoal<T extends TofunianEntity> extends Goal {
    private final T entity;

    public EatOffhandFoodGoal(T p_i50319_1_) {
        this.entity = p_i50319_1_;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        return this.entity.isAlive() && this.entity.getHealth() < this.entity.getMaxHealth() && this.entity.getHeldItem(Hand.OFF_HAND).isEmpty() && this.entity.getRNG().nextInt(120) == 0 && !this.entity.findFoods().isEmpty();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.entity.isHandActive();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        ItemStack itemstack = this.entity.findFoods().split(1);
        this.entity.setItemStackToSlot(EquipmentSlotType.OFFHAND, itemstack);
        this.entity.setActiveHand(Hand.OFF_HAND);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.entity.setItemStackToSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
    }
}