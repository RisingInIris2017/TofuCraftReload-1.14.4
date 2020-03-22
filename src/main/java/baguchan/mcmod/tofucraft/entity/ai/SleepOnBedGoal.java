package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.utils.WorldUtils;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class SleepOnBedGoal extends MoveToBlockGoal {
    private final CreatureEntity creature;
    private int sleepTick;

    public SleepOnBedGoal(CreatureEntity housecreature, double speed) {
        super(housecreature, speed, 14);
        this.creature = housecreature;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        return !this.creature.isBeingRidden() && !this.creature.isSleeping() && this.creature.world.getDimension().isSurfaceWorld() && !WorldUtils.isDaytime(this.creature.world) && this.creature.getAttackTarget() == null && super.shouldExecute();
    }

    protected int getRunDelay(CreatureEntity creatureIn) {
        return 100 + creatureIn.getRNG().nextInt(60);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.creature.isSleeping() && this.creature.getAttackTarget() == null;
    }



    public void resetTask() {
        super.resetTask();

    }

    /*
     *  when moved Finded bed, he going to sleep
     */
    @Override
    public void tick() {
        super.tick();

        if (!this.creature.isSleeping()) {
            BlockPos pos = this.creature.getPosition();
            BlockState blockstate = this.creature.world.getBlockState(pos);

            if (this.getIsAboveDestination()) {
                this.creature.startSleeping(this.destinationBlock);
            }
        }
    }

    public double getTargetDistanceSq() {
        return 1.5D;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */

    /**
     * Return true to set given position as destination
     */
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        if (!worldIn.isAirBlock(pos.up())) {
            return false;
        } else {
            BlockState blockstate = worldIn.getBlockState(pos);
            Block block = blockstate.getBlock();

            return block.isIn(BlockTags.BEDS) && blockstate.get(BedBlock.PART) == BedPart.FOOT && !blockstate.get(BedBlock.OCCUPIED);

        }
    }
}