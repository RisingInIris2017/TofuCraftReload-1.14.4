package baguchan.mcmod.tofucraft.tileentity.tofuenergy.sender;

import baguchan.mcmod.tofucraft.api.recipe.TofuEnergyMap;
import baguchan.mcmod.tofucraft.block.mecha.TFStorageBlock;
import baguchan.mcmod.tofucraft.container.TFStorageContainer;
import baguchan.mcmod.tofucraft.init.TofuTileEntitys;
import baguchan.mcmod.tofucraft.item.base.IEnergyExtractable;
import baguchan.mcmod.tofucraft.tileentity.base.TileEntitySenderBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.IIntArray;
import net.minecraft.util.INameable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TFStorageTileEntity extends TileEntitySenderBase implements IInventory, INamedContainerProvider, INameable {

    /*
     * Comment:
     * This is a rewritten version of Tofu Force Storage, to fix some bugs and make it fit to the bigger skeleton.
     * Most part of working mechanism is rewritten, but there are codes that I can't even understand...
     *
     * Program logic:
     * 1. Check if the machine is idle.
     * 2. If true, then check if there's proper material to consume, then add to workload.
     * 3. If false, work and give out energy
     *
     * */

    private static final int POWER = 10;
    //public FluidTank tank = new TFStorageTank(2000);
    protected NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
    private int workload = 0;
    private int current_workload = 0;

    protected final IIntArray data = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return TFStorageTileEntity.this.energy;
                case 1:
                    return TFStorageTileEntity.this.workload;
                case 2:
                    return TFStorageTileEntity.this.current_workload;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    TFStorageTileEntity.this.energy = value;
                    break;
                case 1:
                    TFStorageTileEntity.this.workload = value;
                    break;
                case 2:
                    TFStorageTileEntity.this.current_workload = value;
            }

        }

        public int size() {
            return 3;
        }
    };

    public TFStorageTileEntity() {
        super(TofuTileEntitys.TFSTORAGE, 5000);
    }

    @Override
    public void tick() {
        boolean wasWorking = workload > 0 && getEnergyStored() < getMaxEnergyStored();
        boolean flag1 = false;

        //Update energy sender logic
        super.tick();

        if (this.world.isRemote) return;

        //Consume beans inside machine
        ItemStack from = this.inventory.get(0);
        if (workload == 0) {
            if (from.getItem() instanceof IEnergyExtractable) {
                IEnergyExtractable symbol = (IEnergyExtractable) from.getItem();
                workload += symbol.drain(from, POWER * 20, false);
                refresh();
            } else if (TofuEnergyMap.getFuel(from) != -1) {
                workload += TofuEnergyMap.getFuel(from);
                from.shrink(1);
                refresh();
            }

           /* if (milk != null) {
                Map.Entry<FluidStack, Int> recipe = TofuEnergyMap.getLiquidFuel(milk);
                if (recipe != null) {
                    tank.drain(recipe.getKey().amount, true);
                    workload += recipe.getValue();
                }
            }*/
            current_workload = workload;
        }

        //Transform workload to power
        if (workload > 0 && getEnergyStored() < getMaxEnergyStored()) {
            workload -= receive(Math.min(workload, POWER), false);
        }

        if (wasWorking != (workload > 0 && getEnergyStored() < getMaxEnergyStored())) {
            flag1 = true;
            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(TFStorageBlock.LIT, Boolean.valueOf(!wasWorking)), 3);
        }

        if (flag1) {
            this.markDirty();
        }
    }

   /* public FluidTank getTank() {
        return this.tank;
    }*/

    protected void refresh() {
        if (hasWorld() && !world.isRemote) {
            BlockState state = world.getBlockState(pos);
            world.markAndNotifyBlock(pos, world.getChunkAt(pos), state, state, 11);
        }
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.inventory) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemstack = ItemStackHelper.getAndSplit(inventory, index, count);

        if (!itemstack.isEmpty()) {
            this.markDirty();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(inventory, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {

        inventory.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {

            return false;

        } else {

            return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;

        }
    }

    @Override
    public void openInventory(PlayerEntity player) {
        this.markDirty();
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        this.markDirty();
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    public NonNullList<ItemStack> getInventory() {
        return inventory;
    }

    @Nullable
    @Override
    public ITextComponent getCustomName() {
        return null;
    }

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent("container.tofucraft.tfstorage.name", new Object());
    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    /*@Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        }

        return super.getCapability(capability, facing);
    }*/


    /*@Override
    public boolean shouldRefresh(World world, BlockPos pos, @Nonnull BlockState oldState, @Nonnull BlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }*/

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT par1nbtTagCompound) {
        CompoundNBT ret = super.write(par1nbtTagCompound);
        writePacketNBT(ret);
        return ret;
    }

    @Nonnull
    @Override
    public final CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Override
    public void read(CompoundNBT par1nbtTagCompound) {
        super.read(par1nbtTagCompound);
        readPacketNBT(par1nbtTagCompound);
    }

    public void writePacketNBT(CompoundNBT cmp) {
        ItemStackHelper.saveAllItems(cmp, this.inventory);
        cmp.putInt("workload", this.workload);
        cmp.putInt("current", this.current_workload);
    }

    public void readPacketNBT(CompoundNBT cmp) {
        this.inventory =
                NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(cmp, this.inventory);

        this.workload = cmp.getInt("workload");
        this.current_workload = cmp.getInt("current");

        //this.tank.readFromNBT(cmp.get("Tank"));
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = new CompoundNBT();
        writePacketNBT(tag);
        return new SUpdateTileEntityPacket(pos, 99, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        super.onDataPacket(net, packet);
        readPacketNBT(packet.getNbtCompound());
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new TFStorageContainer(id, playerInventory, this, data);
    }

    /*private class TFStorageTank extends FluidTank {
        TFStorageTank(int capacity) {
            super(capacity);
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return super.canFillFluidType(fluid) && TofuEnergyMap.getLiquidFuel(fluid) != null;
        }

        @Override
        protected void onContentsChanged() {
            TFStorageTileEntity.this.refresh();
        }
    }*/

}