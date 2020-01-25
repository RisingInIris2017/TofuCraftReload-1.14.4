package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.init.TofuCreatureAttribute;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;

public abstract class AbstractSesamenian extends MonsterEntity {
    @Nullable
    private BlockPos sesameHome;

    public AbstractSesamenian(EntityType<? extends AbstractSesamenian> type, World p_i48553_2_) {
        super(type, p_i48553_2_);
    }

    @Override
    public void livingTick() {
        this.updateArmSwingProgress();
        super.livingTick();
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        if (this.sesameHome != null) {
            compound.put("SesameHome", NBTUtil.writeBlockPos(this.sesameHome));
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        if (compound.contains("SesameHome")) {
            this.sesameHome = NBTUtil.readBlockPos(compound.getCompound("SesameHome"));
        }
    }

    public void setSesameHome(@Nullable BlockPos pos) {
        this.sesameHome = pos;
    }

    @Nullable
    public BlockPos getSesameHome() {
        return this.sesameHome;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.85F;
    }


    public class MoveToHomeGoal extends Goal {
        public final AbstractSesamenian tofunian;
        public final double distance;
        public final double speed;

        public MoveToHomeGoal(AbstractSesamenian sesamenianEntity, double distance, double speed) {
            this.tofunian = sesamenianEntity;
            this.distance = distance;
            this.speed = speed;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            AbstractSesamenian.this.navigator.clearPath();
        }


        public boolean shouldExecute() {
            BlockPos blockpos = this.tofunian.getSesameHome();
            return blockpos != null && this.func_220846_a(blockpos, this.distance);
        }

        @Override
        public boolean shouldContinueExecuting() {
            BlockPos blockpos = this.tofunian.getSesameHome();
            return blockpos != null && this.func_220846_a(blockpos, this.distance * 0.75F);
        }


        public void tick() {
            BlockPos blockpos = this.tofunian.getSesameHome();
            if (blockpos != null && AbstractSesamenian.this.navigator.noPath()) {
                if (this.func_220846_a(blockpos, 6.0D)) {
                    Vec3d vec3d = (new Vec3d((double) blockpos.getX() - this.tofunian.getPosX(), (double) blockpos.getY() - this.tofunian.getPosY(), (double) blockpos.getZ() - this.tofunian.getPosZ())).normalize();
                    Vec3d vec3d1 = vec3d.scale(10.0D).add(this.tofunian.getPosX(), this.tofunian.getPosY(), this.tofunian.getPosZ());
                    AbstractSesamenian.this.navigator.tryMoveToXYZ(vec3d1.x, vec3d1.y, vec3d1.z, this.speed);
                } else {
                    AbstractSesamenian.this.navigator.tryMoveToXYZ((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), this.speed);
                }
            }

        }

        private boolean func_220846_a(BlockPos p_220846_1_, double p_220846_2_) {
            return !p_220846_1_.withinDistance(this.tofunian.getPositionVec(), p_220846_2_);
        }
    }

    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof LivingEntity && ((LivingEntity) entityIn).getCreatureAttribute() == TofuCreatureAttribute.SESAMENIAN) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    public CreatureAttribute getCreatureAttribute() {
        return TofuCreatureAttribute.SESAMENIAN;
    }
}
