package baguchan.mcmod.tofucraft.item;

import baguchan.mcmod.tofucraft.init.TofuItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TofuShieldItem extends Item {
    public TofuShieldItem(Properties group) {
        super(group);
        this.addPropertyOverride(new ResourceLocation("blocking"), (p_210314_0_, p_210314_1_, p_210314_2_) -> {
            return p_210314_2_ != null && p_210314_2_.isHandActive() && p_210314_2_.getActiveItemStack() == p_210314_0_ ? 1.0F : 0.0F;
        });
        DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == TofuItems.TOFUMETAL || super.getIsRepairable(toRepair, repair);
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
        return true;
    }
}

