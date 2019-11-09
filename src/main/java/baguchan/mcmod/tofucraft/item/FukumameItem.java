package baguchan.mcmod.tofucraft.item;

import baguchan.mcmod.tofucraft.entity.projectile.FukumameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class FukumameItem extends Item {
    public FukumameItem(Properties group) {
        super(group);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!playerIn.abilities.isCreativeMode) {
            itemstack.damageItem(1, playerIn, (p_220009_1_) -> {
                p_220009_1_.sendBreakAnimation(playerIn.getActiveHand());
            });
        }

        worldIn.playSound((PlayerEntity) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldownTracker().setCooldown(this, 8);
        if (!worldIn.isRemote) {
            for (int i = 0; i < 6; i++) {
                FukumameEntity fukumamelentity = new FukumameEntity(worldIn, playerIn);
                float d0 = (worldIn.rand.nextFloat() * 20.0F) - 10.0F;

                fukumamelentity.shoot(playerIn, playerIn.rotationPitch + d0 * 0.25F, playerIn.rotationYaw + d0, 0.0F, 1.5F, 0.8F);
                worldIn.addEntity(fukumamelentity);
            }
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}
