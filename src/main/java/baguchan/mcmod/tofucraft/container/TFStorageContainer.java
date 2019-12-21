package baguchan.mcmod.tofucraft.container;

import baguchan.mcmod.tofucraft.init.TofuContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TFStorageContainer extends Container {

    protected final IInventory storageInv;
    private final IIntArray field_217064_e;

    public TFStorageContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new Inventory(1), new IntArray(3));
    }

    public TFStorageContainer(int id, PlayerInventory playerInventory, IInventory storage, IIntArray p_i50104_6_) {
        super(TofuContainers.TFSTORAGE, id);
        this.storageInv = storage;
        this.field_217064_e = p_i50104_6_;

        this.addSlot(new Slot(this.storageInv, 0, 28, 29));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.storageInv.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            if (index < storageInv.getSizeInventory()) {
                if (!this.mergeItemStack(itemstack1, storageInv.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, storageInv.getSizeInventory(), false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookProgressionScaled() {
        int i = this.field_217064_e.get(1);
        int j = this.field_217064_e.get(2);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

}