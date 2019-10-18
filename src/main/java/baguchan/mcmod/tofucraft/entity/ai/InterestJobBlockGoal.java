package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
        return !this.creature.isBeingRidden() && this.creature.isNitwit() && this.creature.world.isDaytime() && this.creature.getAttackTarget() == null && this.creature.world.rand.nextInt(40) == 0 && super.shouldExecute();
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
            }

            this.creature.updateTofunianState();

            if (this.creature.getTofunainHome() == null) {
                this.creature.setTofunainHome(new BlockPos(destinationBlock.getX(), destinationBlock.getY(), destinationBlock.getZ()));
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

            return block instanceof AbstractFurnaceBlock || block == Blocks.CRAFTING_TABLE;

        }
    }
}