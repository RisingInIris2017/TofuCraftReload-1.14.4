package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.utils.WorldUtils;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;

public class WakeUpGoal extends Goal {
    private final CreatureEntity creature;

    public WakeUpGoal(CreatureEntity housecreature) {
        this.creature = housecreature;
    }

    @Override
    public boolean shouldExecute() {
        return !this.creature.world.getDimension().isSurfaceWorld() || WorldUtils.isDaytime(this.creature.world) && this.creature.isSleeping() || !this.creature.getBedPosition().isPresent() && this.creature.isSleeping()
                || this.creature.getBedPosition().isPresent() && this.creature.isSleeping() && this.creature.getDistanceSq(this.creature.getBedPosition().get().getX(), this.creature.getBedPosition().get().getY(), this.creature.getBedPosition().get().getZ()) > 3.0F;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.creature.wakeUp();
    }
}
