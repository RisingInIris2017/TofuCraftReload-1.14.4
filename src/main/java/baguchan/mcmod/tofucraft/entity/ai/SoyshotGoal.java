package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.entity.TofuGandlemEntity;
import baguchan.mcmod.tofucraft.entity.projectile.FukumameEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;

import java.util.EnumSet;

public class SoyshotGoal extends Goal {
    protected int shotCooldown;
    protected int shotTime;
    protected TofuGandlemEntity tofuGandlemEntity;

    public SoyshotGoal(TofuGandlemEntity tofuGandlemEntity) {
        this.tofuGandlemEntity = tofuGandlemEntity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity livingentity = this.tofuGandlemEntity.getAttackTarget();
        if (livingentity != null && livingentity.isAlive()) {
            if (this.tofuGandlemEntity.isCasting() || this.tofuGandlemEntity.isShooting()) {
                return false;
            } else {
                return this.tofuGandlemEntity.ticksExisted >= this.shotCooldown && this.tofuGandlemEntity.getHealth() < this.tofuGandlemEntity.getMaxHealth() / 2;
            }
        } else {
            return false;
        }
    }

    public boolean shouldContinueExecuting() {
        LivingEntity livingentity = this.tofuGandlemEntity.getAttackTarget();
        return livingentity != null && livingentity.isAlive() && this.shotTime > 0;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.tofuGandlemEntity.setShooting(true);
        this.shotCooldown = this.tofuGandlemEntity.ticksExisted + 1200;
        this.shotTime = 120;
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.tofuGandlemEntity.setShooting(false);
    }

    public void tick() {
        --this.shotTime;

        if (this.shotTime == 60 || this.shotTime == 80) {
            this.tofuGandlemEntity.playSound(SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, 2.0F, 1.4F);

            ((ServerWorld) this.tofuGandlemEntity.world).spawnParticle(ParticleTypes.CRIT, this.tofuGandlemEntity.getPosX(), this.tofuGandlemEntity.getPosY(), this.tofuGandlemEntity.getPosZ(), 15, 0.2D, 0.2D, 0.2D, 0.0D);
        }

        if (this.shotTime <= 20) {
            if (this.tofuGandlemEntity.ticksExisted % 6 == 0) {
                for (int i = 0; i < 3; i++) {
                    FukumameEntity projectile = new FukumameEntity(this.tofuGandlemEntity.world, this.tofuGandlemEntity);

                    Vec3d vec3d = this.tofuGandlemEntity.getLook(1.0F);

                    this.tofuGandlemEntity.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 3.0F, 1.0F / (this.tofuGandlemEntity.getRNG().nextFloat() * 0.4F + 0.8F));

                    projectile.setLocationAndAngles(this.tofuGandlemEntity.getPosX() + vec3d.x * 1.3D, this.tofuGandlemEntity.getPosY() + (this.tofuGandlemEntity.getEyeHeight() / 2), this.tofuGandlemEntity.getPosZ() + vec3d.z * 1.2D, this.tofuGandlemEntity.rotationYaw, this.tofuGandlemEntity.rotationPitch);

                    float d0 = (this.tofuGandlemEntity.world.rand.nextFloat() * 16.0F) - 8.0F;

                    projectile.shoot(this.tofuGandlemEntity, this.tofuGandlemEntity.rotationPitch, this.tofuGandlemEntity.rotationYaw + d0, 0.0F, 1.5f, 0.8F);
                    this.tofuGandlemEntity.world.addEntity(projectile);
                }
            }
        }

        if (this.tofuGandlemEntity.getAttackTarget() != null) {
            this.tofuGandlemEntity.getLookController().setLookPositionWithEntity(this.tofuGandlemEntity.getAttackTarget(), 10.0F, 10.0F);
        }
    }
}
