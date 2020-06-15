package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class TofunianLoveGoal extends Goal {
    private static final EntityPredicate field_220689_d = (new EntityPredicate()).setDistance(8.0D).allowInvulnerable().allowFriendlyFire().setLineOfSiteRequired();

    protected final TofunianEntity tofunianEntity;
    protected final World world;
    private final double moveSpeed;
    private int spawnBabyDelay;
    protected TofunianEntity field_75391_e;

    public TofunianLoveGoal(TofunianEntity tofunianEntity, double speed) {
        this.tofunianEntity = tofunianEntity;
        this.world = tofunianEntity.world;
        this.moveSpeed = speed;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean shouldExecute() {
        if (!this.tofunianEntity.canAbondonItems() || this.tofunianEntity.isChild()) {
            return false;
        } else {
            this.field_75391_e = this.getNearbyMate();
            return this.field_75391_e != null;
        }
    }

    public boolean shouldContinueExecuting() {
        return this.field_75391_e.isAlive() && this.field_75391_e.canAbondonItems() && this.spawnBabyDelay < 120;
    }

    @Override
    public void startExecuting() {
        this.tofunianEntity.setInLove(160);
        this.world.setEntityState(this.tofunianEntity, (byte) 18);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.field_75391_e = null;
        this.spawnBabyDelay = 0;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.tofunianEntity.getLookController().setLookPositionWithEntity(this.field_75391_e, 10.0F, (float) this.tofunianEntity.getVerticalFaceSpeed());
        this.tofunianEntity.getNavigator().tryMoveToEntityLiving(this.field_75391_e, this.moveSpeed);
        ++this.spawnBabyDelay;
        if (this.spawnBabyDelay >= 160 && this.tofunianEntity.getDistanceSq(this.field_75391_e) < 9.0D) {
            this.spawnBaby();
        }

        if (this.spawnBabyDelay % 20 == 0) {
            this.world.setEntityState(this.tofunianEntity, (byte) 18);
        }

    }

    /**
     * Loops through nearby tofunianEntitys and finds another tofunianEntity of the same type that can be mated with. Returns the first
     * valid mate found.
     */
    @Nullable
    private TofunianEntity getNearbyMate() {
        List<TofunianEntity> list = this.world.getTargettableEntitiesWithinAABB(TofunianEntity.class, field_220689_d, this.tofunianEntity, this.tofunianEntity.getBoundingBox().grow(8.0D));
        double d0 = Double.MAX_VALUE;
        TofunianEntity tofunianEntityentity = null;

        for (TofunianEntity tofunianEntityentity1 : list) {
            if (this.tofunianEntity.canMateWith(tofunianEntityentity1) && this.tofunianEntity.getDistanceSq(tofunianEntityentity1) < d0) {
                tofunianEntityentity = tofunianEntityentity1;
                d0 = this.tofunianEntity.getDistanceSq(tofunianEntityentity1);
            }
        }

        return tofunianEntityentity;
    }

    /**
     * Spawns a baby tofunianEntity of the same type.
     */
    protected void spawnBaby() {
        AgeableEntity ageableentity = this.tofunianEntity.createChild(this.field_75391_e);
        final net.minecraftforge.event.entity.living.BabyEntitySpawnEvent event = new net.minecraftforge.event.entity.living.BabyEntitySpawnEvent(tofunianEntity, field_75391_e, ageableentity);
        final boolean cancelled = net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
        ageableentity = event.getChild();
        if (cancelled) {
            this.tofunianEntity.setGrowingAge(6000);
            this.field_75391_e.setGrowingAge(6000);
            this.world.setEntityState(this.tofunianEntity, (byte) 13);
            return;
        }
        if (ageableentity != null) {

            this.tofunianEntity.setGrowingAge(6000);
            this.field_75391_e.setGrowingAge(6000);
            ageableentity.setGrowingAge(-24000);
            ageableentity.setLocationAndAngles(this.tofunianEntity.getPosX(), this.tofunianEntity.getPosY(), this.tofunianEntity.getPosZ(), 0.0F, 0.0F);
            this.world.addEntity(ageableentity);
            this.world.setEntityState(this.tofunianEntity, (byte) 18);
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                this.world.addEntity(new ExperienceOrbEntity(this.world, this.tofunianEntity.getPosX(), this.tofunianEntity.getPosY(), this.tofunianEntity.getPosZ(), this.tofunianEntity.getRNG().nextInt(7) + 1));
            }

            this.tofunianEntity.consumeFoods();
        }
    }
}
