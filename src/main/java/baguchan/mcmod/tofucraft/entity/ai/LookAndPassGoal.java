package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import baguchan.mcmod.tofucraft.utils.ItemStackUtil;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;

import java.util.EnumSet;

public class LookAndPassGoal extends Goal {
    protected final TofunianEntity entity;
    protected LivingEntity closestEntity;
    protected final float maxDistance;
    private int lookTime;
    protected final float chance;
    protected final Class<? extends LivingEntity> watchedClass;
    protected final EntityPredicate field_220716_e;

    public LookAndPassGoal(TofunianEntity entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
        this(entityIn, watchTargetClass, maxDistance, 0.001F);
    }

    public LookAndPassGoal(TofunianEntity entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance, float chanceIn) {
        this.entity = entityIn;
        this.watchedClass = watchTargetClass;
        this.maxDistance = maxDistance;
        this.chance = chanceIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
        if (watchTargetClass == PlayerEntity.class) {
            this.field_220716_e = (new EntityPredicate()).setDistance((double) maxDistance).allowFriendlyFire().allowInvulnerable().setSkipAttackChecks().setCustomPredicate((p_220715_1_) -> {
                return EntityPredicates.notRiding(entityIn).test(p_220715_1_);
            });
        } else {
            this.field_220716_e = (new EntityPredicate()).setDistance((double) maxDistance).allowFriendlyFire().allowInvulnerable().setSkipAttackChecks();
        }

    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        if (this.entity.getRNG().nextFloat() >= this.chance) {
            return false;
        } else {

            if (this.entity.canAbondonItems()) {
                if (this.watchedClass == PlayerEntity.class) {
                    this.closestEntity = this.entity.world.getClosestPlayer(this.field_220716_e, this.entity, this.entity.getPosX(), this.entity.getPosYEye(), this.entity.getPosZ());
                    if (this.closestEntity instanceof PlayerEntity && ((PlayerEntity) this.closestEntity).getFoodStats().getFoodLevel() < 12) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    this.closestEntity = this.entity.world.func_225318_b(this.watchedClass, this.field_220716_e, this.entity, this.entity.getPosX(), this.entity.getPosYEye(), this.entity.getPosZ(), this.entity.getBoundingBox().grow((double) this.maxDistance, 3.0D, (double) this.maxDistance));
                    if (this.closestEntity instanceof TofunianEntity && ((TofunianEntity) this.closestEntity).wantsMoreFood()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }

        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        if (!this.closestEntity.isAlive()) {
            return false;
        } else if (this.entity.getDistanceSq(this.closestEntity) > (double) (this.maxDistance * this.maxDistance)) {
            return false;
        } else {
            return this.lookTime > 0;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.lookTime = 60 + this.entity.getRNG().nextInt(20);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.closestEntity = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.entity.getLookController().setLookPosition(this.closestEntity.getPosX(), this.closestEntity.getPosYEye(), this.closestEntity.getPosZ());

        if (this.lookTime == 50) {
            ItemStack stack = this.entity.findFoods();
            if (!stack.isEmpty()) {
                ItemStackUtil.throwItemAt(this.entity, stack.split(8), this.closestEntity.getPositionVec());
            }
        }

        --this.lookTime;
    }
}