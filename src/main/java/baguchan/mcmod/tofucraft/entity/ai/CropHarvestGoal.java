package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class CropHarvestGoal extends MoveToBlockGoal {
    private final TofunianEntity tofunian;
    private boolean wantsToHarvest;
    private boolean canHarvest;
    private boolean canPlant;

    public CropHarvestGoal(TofunianEntity tofunianIn, double speed) {
        super(tofunianIn, speed, 8);
        this.tofunian = tofunianIn;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.runDelay <= 0) {
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.tofunian.world, this.tofunian)) {
                return false;
            }

            this.canHarvest = false;
            this.canPlant = false;
            this.wantsToHarvest = true;
        }

        return this.creature.world.isDaytime() && this.tofunian.getRole() == TofunianEntity.Roles.TOFUCOCK && super.shouldExecute();
    }

    public boolean shouldContinueExecuting() {
        return this.creature.world.isDaytime() && (this.canHarvest || this.canPlant) && super.shouldContinueExecuting();
    }

    public void tick() {
        super.tick();
        this.tofunian.getLookController().setLookPosition((double) this.destinationBlock.getX() + 0.5D, (double) (this.destinationBlock.getY() + 1), (double) this.destinationBlock.getZ() + 0.5D, 10.0F, (float) this.tofunian.getVerticalFaceSpeed());
        if (this.getIsAboveDestination()) {
            World world = this.tofunian.world;
            BlockPos blockpos = this.destinationBlock.up();
            BlockState blockstate = world.getBlockState(blockpos);
            Block block = blockstate.getBlock();
            if (this.canHarvest && block instanceof CropsBlock) {
                Integer integer = blockstate.get(CropsBlock.AGE);
                if (integer == 7) {
                    world.destroyBlock(blockpos, true);
                }

            }

            BlockState blockstate2 = world.getBlockState(blockpos.down());

            ItemStack stack = this.findSeeds(this.tofunian);

            if (this.canPlant && (blockstate2.getBlock() == Blocks.FARMLAND || blockstate2.getBlock() == TofuBlocks.TOFUFARMLAND) && !stack.isEmpty()) {
                world.setBlockState(blockpos, TofuBlocks.SOYBEAN.getDefaultState(), 2);
                stack.shrink(1);
            }

            this.canPlant = false;
            this.canHarvest = false;
            this.runDelay = 10;
        }

    }

    public double getTargetDistanceSq() {
        return 2.0D;
    }


    /*
     *  Find a not planted farmland or crop
     */
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos).getBlock();
        if ((block == Blocks.FARMLAND || block == TofuBlocks.TOFUFARMLAND) && this.wantsToHarvest && (!this.canHarvest || !this.canPlant)) {
            pos = pos.up();
            BlockState blockstate = worldIn.getBlockState(pos);
            block = blockstate.getBlock();

            ItemStack stack = this.findSeeds(this.tofunian);
            if (block instanceof CropsBlock && ((CropsBlock) block).isMaxAge(blockstate)) {
                this.canHarvest = true;
                return true;
            } else if (blockstate.isAir(worldIn, pos) && !stack.isEmpty()) {
                this.canPlant = true;
                return true;
            }
        }

        return false;
    }

    private ItemStack findSeeds(TofunianEntity tofunian) {
        Inventory inventory = tofunian.getVillagerInventory();
        int i = inventory.getSizeInventory();

        for (int j = 0; j < i; ++j) {
            ItemStack itemstack = inventory.getStackInSlot(j);
            if (itemstack.isEmpty()) {
                return ItemStack.EMPTY;
            }

            if (itemstack.getItem() == TofuItems.SEEDS_SOYBEAN) {
                return itemstack;
            }
        }
        return ItemStack.EMPTY;
    }
}