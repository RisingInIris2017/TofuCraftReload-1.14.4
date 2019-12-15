package baguchan.mcmod.tofucraft.item.base;

import baguchan.mcmod.tofucraft.api.tfenergy.ITofuEnergy;
import baguchan.mcmod.tofucraft.api.tfenergy.TofuNetwork;
import baguchan.mcmod.tofucraft.tileentity.base.TileEntitySenderBase;
import baguchan.mcmod.tofucraft.utils.NBTUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class ItemTofuEnergyContained extends Item implements IEnergyExtractable, IEnergyInsertable, IEnergyContained {

    /*
     * Comment:
     * This is the base class of a TF Energy Item which can insert energy to, extract energy from and store energy.
     * There are some exceptions, so I divided the code into three interfaces.
     * Codes here are highly overridable, but can have function without any overrides.
     * */

    public static final String TAG_TF = "tf_energy";
    public static final String TAG_TFMAX = "tf_energymax";

    public ItemTofuEnergyContained(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.tofucraft.energy", getEnergy(stack), getEnergyMax(stack)));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    private boolean getShowState(ItemStack stack) {
        return !Minecraft.getInstance().player.isSneaking() && getEnergy(stack) != 0;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return getShowState(stack) || super.showDurabilityBar(stack);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return getShowState(stack) ?
                1.0 - (double) getEnergy(stack) / (double) getEnergyMax(stack) : super.getDurabilityForDisplay(stack);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return getShowState(stack) ? Color.white.getRGB() : super.getRGBDurabilityForDisplay(stack);
    }

    @Override
    public int drain(ItemStack inst, int amount, boolean simulate) {
        int calculated = Math.min(amount, getEnergy(inst));
        if (!simulate) setEnergy(inst, getEnergy(inst) - calculated);
        return calculated;
    }

    @Override
    public int fill(ItemStack inst, int amount, boolean simulate) {
        int calculated = Math.min(amount, getEnergyMax(inst) - getEnergy(inst));
        if (!simulate) setEnergy(inst, getEnergy(inst) + calculated);
        return calculated;
    }

    @Override
    public int getEnergy(ItemStack inst) {
        return NBTUtil.getInteger(inst.getTag(), TAG_TF, 0);
    }

    //This acts as a way to modify the max energy an item can hold.
    //You can override it to strictly declare how much an item should hold.
    @Override
    public int getEnergyMax(ItemStack inst) {
        return NBTUtil.getInteger(inst.getTag(), TAG_TFMAX, 0);
    }

    @Override
    public void setEnergy(ItemStack inst, int amount) {
        inst.setTag(NBTUtil.setInteger(inst.getTag(), TAG_TF, amount));
    }

    @Override
    public void setEnergyMax(ItemStack inst, int amount) {
        inst.setTag(NBTUtil.setInteger(inst.getTag(), TAG_TFMAX, amount));
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem) {
        this.inventoryTick(entityItem.getItem(), entityItem.getEntityWorld(), entityItem, 0, false);
        return super.onEntityItemUpdate(stack, entityItem);
    }

    //Items will recharge overtime if there are TileEntitySender nearby.
    //It's tedious to put your item inside the battery box each time it exhausted.
    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote && getEnergy(stack) < getEnergyMax(stack)) {
            BlockPos entityPos = new BlockPos(entityIn.posX, entityIn.posY, entityIn.posZ);
            List<TileEntity> list = TofuNetwork.toTiles(TofuNetwork.Instance.getExtractableWithinRadius(
                    worldIn, entityPos, 64));
            if (!list.isEmpty()) {
                int toDrain = getEnergyMax(stack) - getEnergy(stack);
                for (TileEntity te : list) {
                    if (te instanceof TileEntitySenderBase &&
                            ((TileEntitySenderBase) te).isValid() &&
                            te.getDistanceSq(entityPos.getX(), entityPos.getY(), entityPos.getZ()) <= ((TileEntitySenderBase) te).getRadius()) {
                        toDrain -= ((ITofuEnergy) te).drain(Math.min(toDrain, ((TileEntitySenderBase) te).getTransferPower()), false);
                        if (toDrain == 0) break;
                    }
                }
                fill(stack, getEnergyMax(stack) - getEnergy(stack) - toDrain, false);
            }
        }
    }
}