package baguchan.mcmod.tofucraft.item.fukumame;

import baguchan.mcmod.tofucraft.entity.projectile.NetherFukumameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class NetherFukumameItem extends Item {
    public NetherFukumameItem(Properties group) {
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

        worldIn.playSound((PlayerEntity) null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldownTracker().setCooldown(this, 12);
        if (!worldIn.isRemote) {
            for (int i = 0; i < 6; i++) {
                NetherFukumameEntity fukumamelentity = new NetherFukumameEntity(worldIn, playerIn);
                float d0 = (worldIn.rand.nextFloat() * 22.0F) - 11.0F;

                fukumamelentity.func_234612_a_(playerIn, playerIn.rotationPitch + d0 * 0.25F, playerIn.rotationYaw + d0, 0.0F, 1.2F, 0.1F);
                worldIn.addEntity(fukumamelentity);
            }
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}