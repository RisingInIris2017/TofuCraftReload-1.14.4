package baguchan.mcmod.tofucraft.item;

import baguchan.mcmod.tofucraft.entity.TofuSlimeEntity;
import baguchan.mcmod.tofucraft.init.TofuDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class TofuSlimeRadarItem extends Item {
    public TofuSlimeRadarItem(Properties group) {
        super(group);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        boolean flag = playerIn.isCreative();

        if (flag || playerIn.getHeldItem(handIn).getDamage() <= playerIn.getHeldItem(handIn).getMaxDamage()) {
            if (!worldIn.isRemote) {
                boolean isSpawnChunk = playerIn.dimension.getModType() == TofuDimensions.TOFUWORLD || TofuSlimeEntity.isSpawnChunk(playerIn.world, playerIn.getPosX(), playerIn.getPosZ());

                if (isSpawnChunk)
                    playerIn.sendMessage(new TranslationTextComponent("tofucraft.radar.result.success", new Object()));
                else playerIn.sendMessage(new TranslationTextComponent("tofucraft.radar.result.failed", new Object()));
            }

            if (!playerIn.isCreative() && playerIn.getHeldItem(handIn).isDamageable()) {
                playerIn.getHeldItem(handIn).damageItem(1, playerIn, (p_220036_0_) -> {
                    p_220036_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            }
            playerIn.playSound(SoundEvents.UI_BUTTON_CLICK, 0.5F, 1.0F);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
