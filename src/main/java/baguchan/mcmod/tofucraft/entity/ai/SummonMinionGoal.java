package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.entity.TofuGandlemEntity;
import baguchan.mcmod.tofucraft.entity.TofuTurretEntity;
import baguchan.mcmod.tofucraft.init.TofuEntitys;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class SummonMinionGoal extends CastingGoal {
    private final EntityPredicate field_220843_e = (new EntityPredicate()).setDistance(20.0D).setLineOfSiteRequired().setUseInvisibilityCheck().allowInvulnerable().allowFriendlyFire();

    public SummonMinionGoal(TofuGandlemEntity tofuGandlemEntity) {
        super(tofuGandlemEntity);
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity livingentity = this.tofuGandlemEntity.getAttackTarget();
        if (!super.shouldExecute()) {
            return false;
        } else {
            int i = this.tofuGandlemEntity.world.getTargettableEntitiesWithinAABB(MonsterEntity.class, this.field_220843_e, this.tofuGandlemEntity, this.tofuGandlemEntity.getBoundingBox().grow(20.0D)).size();
            return this.tofuGandlemEntity.world.rand.nextInt(8) + 1 > i;
        }
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.spellCooldown = this.tofuGandlemEntity.ticksExisted + 1200;
        this.spellWarmup = 100;
        this.tofuGandlemEntity.playSound(SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON, 2.0F, 1.0F);
    }

    @Override
    protected void castSpell() {
        super.castSpell();

        for (int i = 0; i < 3; ++i) {
            BlockPos blockpos = (new BlockPos(this.tofuGandlemEntity)).add(-2 + this.tofuGandlemEntity.world.rand.nextInt(5), 1, -2 + this.tofuGandlemEntity.world.rand.nextInt(5));
            TofuTurretEntity tofuTurretEntity = TofuEntitys.TOFUTURRET.create(this.tofuGandlemEntity.world);
            tofuTurretEntity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
            tofuTurretEntity.onInitialSpawn(this.tofuGandlemEntity.world, this.tofuGandlemEntity.world.getDifficultyForLocation(blockpos), SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
            tofuTurretEntity.setAttackTarget(this.tofuGandlemEntity.getAttackTarget());
            this.tofuGandlemEntity.world.addEntity(tofuTurretEntity);
        }
    }
}
