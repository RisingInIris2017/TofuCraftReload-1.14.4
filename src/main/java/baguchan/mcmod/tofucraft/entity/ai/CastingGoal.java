package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.entity.TofuGandlemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

public abstract class CastingGoal extends Goal {
    protected int spellCooldown;
    protected int spellWarmup;
    protected TofuGandlemEntity tofuGandlemEntity;

    public CastingGoal(TofuGandlemEntity tofuGandlemEntity) {
        this.tofuGandlemEntity = tofuGandlemEntity;
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity livingentity = this.tofuGandlemEntity.getAttackTarget();
        if (livingentity != null && livingentity.isAlive()) {
            if (this.tofuGandlemEntity.isCasting() || this.tofuGandlemEntity.isShooting()) {
                return false;
            } else {
                return this.tofuGandlemEntity.ticksExisted >= this.spellCooldown;
            }
        } else {
            return false;
        }
    }

    public boolean shouldContinueExecuting() {
        LivingEntity livingentity = this.tofuGandlemEntity.getAttackTarget();
        return livingentity != null && livingentity.isAlive() && this.spellWarmup > 0;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.tofuGandlemEntity.setCasting(true);

        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                if (i > -2 && i < 2 && j == -1) {
                    j = 2;
                }

                if (this.tofuGandlemEntity.getRNG().nextInt(16) == 0) {
                    for (int k = 0; k <= 2; ++k) {
                        BlockPos blockpos = new BlockPos(this.tofuGandlemEntity.getPositionVec()).add(i, k, j);

                        //this.tofuGandlemEntity.world.addParticle(TofuParticles.TOFUPORTAL, (double) this.tofuGandlemEntity.getPosition().getX() + 0.5D, (double) this.tofuGandlemEntity.getPosition().getY() + 0.25D, (double) this.tofuGandlemEntity.getPosition().getZ() + 0.5D, (double) ((float) i + this.tofuGandlemEntity.getRNG().nextFloat()) - 0.5D, (double) ((float) k - this.tofuGandlemEntity.getRNG().nextFloat() - 1.0F), (double) ((float) j + this.tofuGandlemEntity.getRNG().nextFloat()) - 0.5D);

                    }
                }
            }
        }
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.tofuGandlemEntity.setCasting(false);
    }

    public void tick() {
        --this.spellWarmup;
        if (this.spellWarmup == 0) {
            this.castSpell();
        }
    }

    protected void castSpell() {

    }
}
