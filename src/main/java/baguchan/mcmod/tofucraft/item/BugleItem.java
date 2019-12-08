package baguchan.mcmod.tofucraft.item;

import baguchan.mcmod.tofucraft.init.TofuSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BugleItem extends Item {
    public BugleItem(Properties group) {
        super(group);
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
        entityLiving.playSound(TofuSounds.TOFUBUGLE, 40F, 1.0F);

        if (entityLiving instanceof PlayerEntity) {
            ((PlayerEntity) entityLiving).getCooldownTracker().setCooldown(stack.getItem(), 80);
        }
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }

}
