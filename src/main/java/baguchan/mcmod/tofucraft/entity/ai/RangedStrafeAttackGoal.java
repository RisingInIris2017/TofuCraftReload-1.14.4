package baguchan.mcmod.tofucraft.entity.ai;

import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;

public class RangedStrafeAttackGoal<T extends MonsterEntity & IRangedAttackMob> extends Goal {
    private final MobEntity entity;
    private final T rangedAttackEntityHost;
    private LivingEntity targetEntity;
    private int rangedAttackTime = -1;
    private final double entityMoveSpeed;
    private int seeTime;
    private final int attackIntervalMin;
    private final int maxRangedAttackTime;
    private final float attackRadius;
    private final float maxAttackDistance;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;

    public RangedStrafeAttackGoal(T attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
        this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn);
    }

    public RangedStrafeAttackGoal(T attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn) {
        if (!(attacker instanceof LivingEntity)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        } else {
            this.rangedAttackEntityHost = attacker;
            this.entity = attacker;
            this.entityMoveSpeed = movespeed;
            this.attackIntervalMin = p_i1650_4_;
            this.maxRangedAttackTime = maxAttackTime;
            this.attackRadius = maxAttackDistanceIn;
            this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        LivingEntity livingentity = this.entity.getAttackTarget();
        if (livingentity != null && livingentity.isAlive()) {
            this.targetEntity = livingentity;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.shouldExecute() || !this.entity.getNavigator().noPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.targetEntity = null;
        this.seeTime = 0;
        this.rangedAttackTime = -1;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        double d0 = this.entity.getDistanceSq(this.targetEntity.posX, this.targetEntity.getBoundingBox().minY, this.targetEntity.posZ);
        boolean flag = this.entity.getEntitySenses().canSee(this.targetEntity);
        if (flag) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        if (!(d0 > (double) this.maxAttackDistance) && this.seeTime >= 5) {
            this.entity.getNavigator().clearPath();
            ++this.strafingTime;
        } else {
            this.entity.getNavigator().tryMoveToEntityLiving(this.targetEntity, this.entityMoveSpeed);
            this.strafingTime = -1;
        }

        if (this.strafingTime >= 20) {
            if ((double) this.rangedAttackEntityHost.getRNG().nextFloat() < 0.3D) {
                this.strafingClockwise = !this.strafingClockwise;
            }

            if ((double) this.entity.getRNG().nextFloat() < 0.3D) {
                this.strafingBackwards = !this.strafingBackwards;
            }

            this.strafingTime = 0;
        }

        if (this.strafingTime > -1) {
            if (d0 > (double) (this.maxAttackDistance * 0.75F)) {
                this.strafingBackwards = false;
            } else if (d0 < (double) (this.maxAttackDistance * 0.25F)) {
                this.strafingBackwards = true;
            }

            this.entity.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
            this.entity.faceEntity(targetEntity, 30.0F, 30.0F);
        } else {
            this.entity.getLookController().setLookPositionWithEntity(targetEntity, 30.0F, 30.0F);
        }

        if (--this.rangedAttackTime == 0) {
            if (!flag) {
                return;
            }

            float f = MathHelper.sqrt(d0) / this.attackRadius;
            float lvt_5_1_ = MathHelper.clamp(f, 0.1F, 1.0F);
            this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.targetEntity, lvt_5_1_);
            this.rangedAttackTime = MathHelper.floor(f * (float) (this.maxRangedAttackTime - this.attackIntervalMin) + (float) this.attackIntervalMin);
        } else if (this.rangedAttackTime < 0) {
            float f2 = MathHelper.sqrt(d0) / this.attackRadius;
            this.rangedAttackTime = MathHelper.floor(f2 * (float) (this.maxRangedAttackTime - this.attackIntervalMin) + (float) this.attackIntervalMin);
        }

    }
}