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
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class SaltFurnaceContainer extends Container {
    private final IInventory inventory;
    private final IIntArray furnaceData;
    protected final World world;

    public SaltFurnaceContainer(int id, PlayerInventory playerInventoryIn) {
        this(id, playerInventoryIn, new Inventory(1), new IntArray(2));
    }

    public SaltFurnaceContainer(int id, PlayerInventory playerInventoryIn, IInventory furnaceInventoryIn, IIntArray furnaceDataIn) {
        super(TofuContainers.SALT_FURNACE, id);
        this.inventory = furnaceInventoryIn;
        this.furnaceData = furnaceDataIn;
        this.world = playerInventoryIn.player.world;

        this.addSlot(new Slot(this.inventory, 0, 23, 53));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventoryIn, k, 8 + k * 18, 142));
        }
        this.trackIntArray(furnaceDataIn);
    }


    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
    }

    public void clear() {
        this.inventory.clear();
    }

    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.inventory.isUsableByPlayer(playerIn);
    }


    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 0) {
                if (!this.mergeItemStack(itemstack1, 0, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (this.inventorySlots.get(0).getHasStack() || !this.inventorySlots.get(0).isItemValid(itemstack1)) {
                    return ItemStack.EMPTY;
                }

                if (itemstack1.hasTag()) { // Forge: Fix MC-17431
                    ((Slot) this.inventorySlots.get(0)).putStack(itemstack1.split(1));
                } else if (!itemstack1.isEmpty()) {
                    this.inventorySlots.get(0).putStack(new ItemStack(itemstack1.getItem()));
                    itemstack1.shrink(1);
                }
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled() {
        int i = this.furnaceData.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.furnaceData.get(0) * 13 / i;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isBurning() {
        return this.furnaceData.get(0) > 0;
    }
}