package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.entity.ai.RangedStrafeAttackGoal;
import baguchan.mcmod.tofucraft.entity.projectile.FukumameEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TofuSpiderEntity extends SpiderEntity implements IRangedAttackMob {
    public TofuSpiderEntity(EntityType<? extends TofuSpiderEntity> type, World p_i48550_2_) {
        super(type, p_i48550_2_);
        this.experienceValue = 4;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(4, new RangedStrafeAttackGoal<>(this, 0.95D, 65, 20F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new TargetGoal<>(this, PlayerEntity.class));
        this.targetSelector.addGoal(3, new TargetGoal<>(this, IronGolemEntity.class));
    }


    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset() {
        return (double) (this.getHeight() * 0.65F);
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        for (int i = 0; i < 4; i++) {
            FukumameEntity snowballentity = new FukumameEntity(this.world, this);
            Vec3d vec3d = this.getLook(1.0F);
            double d0 = target.getPosY() + (double) target.getEyeHeight();
            double d1 = target.getPosX() - (this.getPosX() + vec3d.x * 1.2D);
            double d2 = d0 - snowballentity.getPosY();
            double d3 = target.getPosZ() - (this.getPosZ() + vec3d.z * 1.2D);
            float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.15F;
            snowballentity.shoot(d1, d2 + (double) f, d3, 0.8F, 12.0F);
            this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            this.world.addEntity(snowballentity);
        }
    }

    static class TargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public TargetGoal(SpiderEntity spider, Class<T> classTarget) {
            super(spider, classTarget, true);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            float f = this.goalOwner.getBrightness();
            return f >= 0.5F ? false : super.shouldExecute();
        }
    }
}
