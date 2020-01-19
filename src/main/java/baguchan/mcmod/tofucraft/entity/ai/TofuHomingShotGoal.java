package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.entity.TofuGandlemEntity;
import baguchan.mcmod.tofucraft.entity.projectile.TofuHomingForceEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;

import java.util.EnumSet;

public class TofuHomingShotGoal extends Goal {
    protected int shotCooldown;
    protected int shotTime;
    protected TofuGandlemEntity tofuGandlemEntity;

    public TofuHomingShotGoal(TofuGandlemEntity tofuGandlemEntity) {
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
                return this.tofuGandlemEntity.ticksExisted >= this.shotCooldown && this.tofuGandlemEntity.getHealth() < this.tofuGandlemEntity.getMaxHealth() / 1.5;
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
        this.shotTime = 100;
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

            ((ServerWorld) this.tofuGandlemEntity.world).spawnParticle(ParticleTypes.CRIT, this.tofuGandlemEntity.posX, this.tofuGandlemEntity.posY, this.tofuGandlemEntity.posZ, 15, 0.2D, 0.2D, 0.2D, 0.0D);
        }

        if (this.shotTime <= 20) {
            if (this.tofuGandlemEntity.ticksExisted % 10 == 0) {
                this.tofuGandlemEntity.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 3.0F, 1.0F / (this.tofuGandlemEntity.getRNG().nextFloat() * 0.4F + 0.8F));

                for (int i = 0; i < 1; i++) {
                    this.tofuGandlemEntity.world.addEntity(new TofuHomingForceEntity(this.tofuGandlemEntity.world, this.tofuGandlemEntity, this.tofuGandlemEntity.getAttackTarget()));
                }
            }
        }

        if (this.tofuGandlemEntity.getAttackTarget() != null) {
            this.tofuGandlemEntity.getLookController().setLookPositionWithEntity(this.tofuGandlemEntity.getAttackTarget(), 10.0F, 10.0F);
        }
    }
}
