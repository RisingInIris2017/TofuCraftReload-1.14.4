package baguchan.mcmod.tofucraft.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Predicate;

public class TofuChingerEntity extends MonsterEntity {
    private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(TofuChingerEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IS_EATING = EntityDataManager.createKey(TofuChingerEntity.class, DataSerializers.BOOLEAN);
    private float clientSideEatAnimation0;
    private float clientSideEatAnimation;
    private int warningSoundTicks;

    public TofuChingerEntity(EntityType<? extends TofuChingerEntity> type, World world) {
        super(type, world);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal());
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, TofunianEntity.class, 10, true, false, (Predicate<LivingEntity>) null));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, TofuSlimeEntity.class, 10, true, false, (Predicate<LivingEntity>) null));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, TofuFishEntity.class, 10, true, true, (Predicate<LivingEntity>) null));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    protected void playWarningSound() {
        if (this.warningSoundTicks <= 0) {
            this.playSound(SoundEvents.ENTITY_EVOKER_FANGS_ATTACK, 1.0F, this.getSoundPitch() * 1.2F);
            this.warningSoundTicks = 40;
        }

    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(IS_EATING, false);
        this.dataManager.register(SIZE, 0);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Size", this.getChingerSize());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        int i = compound.getInt("Size");
        if (i < 0) {
            i = 0;
        } else if (i > 50) {
            i = 50;
        }

        this.setChingerSize(i, false);
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (SIZE.equals(key)) {
            this.recalculateSize();
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;
           /* if (this.isInWater() && this.rand.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }*/
        }

        super.notifyDataManagerChange(key);
    }

    protected void setChingerSize(int size, boolean resetHealth) {
        this.dataManager.set(SIZE, size);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.recalculateSize();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double) (12.0D + 2.0F * size));
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) (0.25F + 0.005F * (float) size));
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double) (4.0D + 0.5F * (float) size));

        if (resetHealth) {
            this.setHealth(this.getMaxHealth());
        }

        this.experienceValue = 3 + size * 2;
    }

    public int getChingerSize() {
        return this.dataManager.get(SIZE);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        if (this.world.isRemote) {
            if (this.clientSideEatAnimation != this.clientSideEatAnimation0) {
                this.recalculateSize();
            }

            this.clientSideEatAnimation0 = this.clientSideEatAnimation;
            if (this.isEating()) {
                this.clientSideEatAnimation = MathHelper.clamp(this.clientSideEatAnimation + 1.0F, 0.0F, 6.0F);
            } else {
                this.clientSideEatAnimation = MathHelper.clamp(this.clientSideEatAnimation - 1.0F, 0.0F, 6.0F);
            }
        }

        if (this.warningSoundTicks > 0) {
            --this.warningSoundTicks;
        }

    }

    public EntitySize getSize(Pose poseIn) {
        if (this.clientSideEatAnimation > 0.0F) {
            float f = this.clientSideEatAnimation / 6.0F;
            float f1 = 1.0F + 0.2F * getChingerSize() + f;
            return super.getSize(poseIn).scale(1.0F + 0.2F * getChingerSize(), f1);
        } else {
            return super.getSize(poseIn).scale(1.0F + 0.2F * getChingerSize());
        }
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.75F * sizeIn.height;
    }


    public boolean isEating() {
        return this.dataManager.get(IS_EATING);
    }

    public void setEating(boolean standing) {
        this.dataManager.set(IS_EATING, standing);
    }

    @OnlyIn(Dist.CLIENT)
    public float getEatingAnimationScale(float p_189795_1_) {
        return MathHelper.lerp(p_189795_1_, this.clientSideEatAnimation0, this.clientSideEatAnimation) / 6.0F;
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        int i = this.rand.nextInt(4);
        if (i < 6 && this.rand.nextFloat() < 0.5F * difficultyIn.getClampedAdditionalDifficulty()) {
            ++i;
        }

        this.setChingerSize(i, true);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public static boolean spawnHandle(EntityType<TofuChingerEntity> p_223366_0_, IWorld p_223366_1_, SpawnReason reason, BlockPos p_223366_3_, Random randomIn) {
        return p_223366_1_.getWorld().canBlockSeeSky(p_223366_3_) && func_223315_a(p_223366_0_, p_223366_1_, reason, p_223366_3_, randomIn);
    }

    class MeleeAttackGoal extends net.minecraft.entity.ai.goal.MeleeAttackGoal {
        public MeleeAttackGoal() {
            super(TofuChingerEntity.this, 1.25D, true);
        }

        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.attackTick <= 0) {
                this.attackTick = 20;
                this.attacker.attackEntityAsMob(enemy);
                TofuChingerEntity.this.setEating(false);
            } else if (distToEnemySqr <= d0 * 2.0D) {
                if (this.attackTick <= 0) {
                    TofuChingerEntity.this.setEating(false);
                    this.attackTick = 20;
                }

                if (this.attackTick <= 10) {
                    TofuChingerEntity.this.setEating(true);
                }

                if (this.attackTick <= 5) {
                    TofuChingerEntity.this.playWarningSound();
                }
            } else {
                this.attackTick = 20;
                TofuChingerEntity.this.setEating(false);
            }

        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            TofuChingerEntity.this.setEating(false);
            super.resetTask();
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return (double) (4.0F + attackTarget.getWidth());
        }
    }
}
