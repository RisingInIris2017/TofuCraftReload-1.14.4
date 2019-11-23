package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.entity.TofuGandlemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

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
            if (this.tofuGandlemEntity.isCasting() || this.tofuGandlemEntity.isSoyshot()) {
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
