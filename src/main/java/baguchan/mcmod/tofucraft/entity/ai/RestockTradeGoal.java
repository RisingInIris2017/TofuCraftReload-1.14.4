package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import baguchan.mcmod.tofucraft.utils.WorldUtils;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class RestockTradeGoal extends MoveToBlockGoal {
    private final TofunianEntity creature;
    private int sleepTick;

    public RestockTradeGoal(TofunianEntity creature, double speed) {
        super(creature, speed, 12);
        this.creature = creature;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        //return !this.creature.isBeingRidden() && this.creature.isStockOut() && this.creature.world.isDaytime() && this.creature.getAttackTarget() == null && super.shouldExecute();
        //DayTime is useless...
        return !this.creature.isBeingRidden() && this.creature.isStockOut() && WorldUtils.isDaytime(this.creature.world) && this.creature.getAttackTarget() == null && super.shouldExecute();
    }

    protected int getRunDelay(CreatureEntity creatureIn) {
        return 40 + creatureIn.getRNG().nextInt(60);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.creature.isStockOut() & super.shouldContinueExecuting();
    }


    public void startExecuting() {
        super.startExecuting();
    }

    public void resetTask() {
        super.resetTask();
        this.creature.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.8F, 0.7F);
        this.creature.swingArm(Hand.MAIN_HAND);
    }

    /*
     *  when moved WorkBlock trade update
     */
    @Override
    public void tick() {
        super.tick();

        BlockState blockstate = this.creature.world.getBlockState(new BlockPos(destinationBlock.getX(), destinationBlock.getY(), destinationBlock.getZ()));
        Block block = blockstate.getBlock();
        if (this.getIsAboveDestination()) {
            this.creature.restock();
        }
    }

    public double getTargetDistanceSq() {
        return 1.2D;
    }

    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        if (!worldIn.isAirBlock(pos.up())) {
            return false;
        } else {
            BlockState blockstate = worldIn.getBlockState(pos);
            Block block = blockstate.getBlock();

            if (this.creature.canFarm()) {
                return block == Blocks.CRAFTING_TABLE;
            } else if (this.creature.canSmith()) {
                return block instanceof AbstractFurnaceBlock;
            }
            return block == Blocks.CRAFTING_TABLE;
        }
    }
}