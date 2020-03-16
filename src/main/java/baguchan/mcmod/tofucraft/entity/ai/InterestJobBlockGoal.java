package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import net.minecraft.block.*;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class InterestJobBlockGoal extends MoveToBlockGoal {
    private final TofunianEntity creature;
    private int sleepTick;

    public InterestJobBlockGoal(TofunianEntity creature, double speed) {
        super(creature, speed, 8);
        this.creature = creature;
    }

    public boolean shouldExecute() {
        //return !this.creature.isBeingRidden() && !this.creature.isChild() && this.creature.isNitwit() && this.creature.world.isDaytime() && this.creature.getAttackTarget() == null && this.creature.world.rand.nextInt(20) == 0 && super.shouldExecute();
        //DayTime is useless...
        return !this.creature.isBeingRidden() && !this.creature.isChild() && this.creature.isNitwit() && this.creature.getAttackTarget() == null && this.creature.world.rand.nextInt(20) == 0 && super.shouldExecute();
    }

    protected int getRunDelay(CreatureEntity creatureIn) {
        return 40 + creatureIn.getRNG().nextInt(60);
    }

    @Override
    public boolean shouldContinueExecuting() {
        BlockPos blockpos = new BlockPos(destinationBlock.getX(), destinationBlock.getY(), destinationBlock.getZ());

        return this.creature.world.isDaytime() && this.creature.isNitwit() & super.shouldContinueExecuting();
    }


    public void startExecuting() {
        super.startExecuting();
    }

    public void resetTask() {
        super.resetTask();
        this.creature.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.8F, 0.7F);
    }

    /*
     *  when moved WorkBlock he's Job changed
     */
    @Override
    public void tick() {
        super.tick();

        BlockState blockstate = this.creature.world.getBlockState(new BlockPos(destinationBlock.getX(), destinationBlock.getY(), destinationBlock.getZ()));
        Block block = blockstate.getBlock();
        if (this.getIsAboveDestination()) {
            if (block == Blocks.CRAFTING_TABLE) {
                this.creature.setRole(TofunianEntity.Roles.TOFUCOCK);
            } else if (block instanceof AbstractFurnaceBlock) {
                this.creature.setRole(TofunianEntity.Roles.TOFUSMITH);
            } else if (block instanceof CauldronBlock) {
                this.creature.setRole(TofunianEntity.Roles.SOYWORKER);
            }

            this.creature.updateTofunianState();
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

            return block instanceof CauldronBlock || block instanceof AbstractFurnaceBlock || block == Blocks.CRAFTING_TABLE;

        }
    }
}