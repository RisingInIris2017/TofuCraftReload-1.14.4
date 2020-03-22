package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import baguchan.mcmod.tofucraft.utils.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class CookingTofuGoal extends MoveToBlockGoal {
    private final TofunianEntity creature;
    private int sleepTick;

    public CookingTofuGoal(TofunianEntity creature, double speed) {
        super(creature, speed, 8);
        this.creature = creature;
    }

    public boolean shouldExecute() {
        return !this.creature.isBeingRidden() && !this.creature.isChild() && this.creature.getRole() == TofunianEntity.Roles.TOFUCOCK && WorldUtils.isDaytime(this.creature.world) && this.creature.getAttackTarget() == null && this.creature.world.rand.nextInt(160) == 0 && this.creature.canCookItem() && super.shouldExecute();
    }

    protected int getRunDelay(CreatureEntity creatureIn) {
        return 40 + creatureIn.getRNG().nextInt(60);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return WorldUtils.isDaytime(this.creature.world) && this.creature.getRole() == TofunianEntity.Roles.TOFUCOCK && this.creature.canKeepCookItem();
    }


    public void startExecuting() {
        super.startExecuting();
    }

    public void resetTask() {
        super.resetTask();
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
            if (this.creature.ticksExisted % 20 == 0) {
                this.creature.swingArm(Hand.MAIN_HAND);
                this.creature.cookingFood();
                this.creature.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.8F, 0.7F);
            }
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

            return block == Blocks.CRAFTING_TABLE;

        }
    }
}