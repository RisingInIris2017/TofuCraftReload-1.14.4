package baguchan.mcmod.tofucraft.tileentity;

import baguchan.mcmod.tofucraft.block.SaltFurnaceBlock;
import baguchan.mcmod.tofucraft.container.SaltFurnaceContainer;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuTileEntitys;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class SaltFurnaceTileEntity extends TileEntity implements INamedContainerProvider, IInventory, ITickableTileEntity {
    private int burnTime;
    private int recipesUsed;
    private int ticksExisted;

    protected NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    protected final IIntArray furnaceData = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return SaltFurnaceTileEntity.this.burnTime;
                case 1:
                    return SaltFurnaceTileEntity.this.recipesUsed;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    SaltFurnaceTileEntity.this.burnTime = value;
                    break;
                case 1:
                    SaltFurnaceTileEntity.this.recipesUsed = value;
                    break;
            }

        }

        public int size() {
            return 2;
        }
    };

    public SaltFurnaceTileEntity() {
        super(TofuTileEntitys.SALT_FURNACE);
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    public void read(BlockState p_230337_1_, CompoundNBT compound) {
        super.read(p_230337_1_, compound);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.items);
        this.ticksExisted = compound.getInt("TicksExisted");
        this.burnTime = compound.getInt("BurnTime");
        this.recipesUsed = this.getBurnTime(this.items.get(0));
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("TicksExisted", this.ticksExisted);
        compound.putInt("BurnTime", this.burnTime);
        ItemStackHelper.saveAllItems(compound, this.items);

        return compound;
    }

    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.burnTime;
        }

        if (!this.world.isRemote) {
            ItemStack itemstack = this.items.get(0);
            if (!this.isBurning()) {
                this.burnTime = this.getBurnTime(itemstack);
                this.recipesUsed = this.burnTime;
                itemstack.shrink(1);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(SaltFurnaceBlock.LIT, Boolean.valueOf(this.isBurning())), 3);
            }

            if (this.isBurning()) {
                if (world.getBlockState(pos.up()).getBlock() == Blocks.CAULDRON) {
                    ticksExisted++;
                    if (ticksExisted >= 300) {
                        if (world.getBlockState(pos.up()).func_235901_b_(CauldronBlock.LEVEL)) {
                            int level = world.getBlockState(pos.up()).get(CauldronBlock.LEVEL);
                            if (level > 0) {
                                world.setBlockState(pos.up(), TofuBlocks.SALT_CAULDRON.getDefaultState().with(CauldronBlock.LEVEL, level), 2);
                            }
                        }
                    }
                } else {
                    ticksExisted = 0;
                }
            } else {
                ticksExisted = 0;
            }
        }

        if (flag1) {
            this.markDirty();
        }
    }

    protected int getBurnTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            Item item = fuel.getItem();
            return net.minecraftforge.common.ForgeHooks.getBurnTime(fuel);
        }
    }

    public static boolean isFuel(ItemStack stack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(stack) > 0;
    }

    public int getSizeInventory() {
        return this.items.size();
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getStackInSlot(int index) {
        return this.items.get(index);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.items, index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.items, index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag) {
            this.markDirty();
        }

    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     */
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return isFuel(stack) || stack.getItem() == Items.BUCKET;
    }

    public void clear() {
        this.items.clear();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.tofucraft.salt_furnace");
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new SaltFurnaceContainer(p_createMenu_1_, p_createMenu_2_, this, this.furnaceData);
    }
}
