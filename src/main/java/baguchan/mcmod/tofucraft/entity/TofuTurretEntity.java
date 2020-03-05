package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.entity.ai.RangedStrafeAttackGoal;
import baguchan.mcmod.tofucraft.entity.movement.FlyingStrafeMovementController;
import baguchan.mcmod.tofucraft.entity.projectile.BeamEntity;
import baguchan.mcmod.tofucraft.init.TofuCreatureAttribute;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TofuTurretEntity extends MonsterEntity implements IRangedAttackMob {
    private float heightOffset = 0.5f;
    private int heightOffsetUpdateTime;


    public TofuTurretEntity(EntityType<? extends TofuTurretEntity> p_i48553_1_, World p_i48553_2_) {
        super(p_i48553_1_, p_i48553_2_);
        this.moveController = new FlyingStrafeMovementController(this, 20, false);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new RangedStrafeAttackGoal<>(this, 1.0D, 65, 20F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 0.95D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, false));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue((double) 0.5D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.265F);
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanSwim(true);
        flyingpathnavigator.setCanEnterDoors(true);
        return flyingpathnavigator;
    }

    @Override
    protected void updateAITasks() {
        --this.heightOffsetUpdateTime;

        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5f + (float) this.rand.nextGaussian() * 3f;
        }

        LivingEntity target = getAttackTarget();
        Vec3d vec3d = this.getMotion();
        if (target != null && target.isAlive() && target.getPosY() + (double) target.getEyeHeight() > this.getPosY() + (double) getEyeHeight() + (double) this.heightOffset && this.isAlive()) {
            this.setMotion(this.getMotion().add(0.0D, ((double) 0.3F - vec3d.y) * (double) 0.3F, 0.0D));
            this.isAirBorne = true;
        }

        if (!this.onGround && vec3d.y < 0.0D) {
            this.setMotion(vec3d.mul(1.0D, 0.6D, 1.0D));
        }


        super.updateAITasks();
    }

    @Override
    public boolean onLivingFall(float p_225503_1_, float p_225503_2_) {
        return false;
    }


    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public float getEyeHeight(Pose p_213307_1_) {
        return this.getHeight() * 0.55F;
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity livingEntity, float v) {
        double d1 = livingEntity.getPosX() - this.getPosX();
        double d2 = livingEntity.getBoundingBox().minY + (double) (livingEntity.getHeight() / 2.0F) - (this.getPosY() + (double) (this.getEyeHeight()));
        double d3 = livingEntity.getPosZ() - this.getPosZ();
        float f = 0.075F;
        BeamEntity smallfireballentity = new BeamEntity(this.world, this, d1 + this.getRNG().nextGaussian() * (double) f - this.getRNG().nextGaussian() * (double) f, d2, d3 + this.getRNG().nextGaussian() * (double) f - this.getRNG().nextGaussian() * (double) f);
        smallfireballentity.setPosition(smallfireballentity.getPosX(), this.getPosY() + (double) (this.getEyeHeight()), smallfireballentity.getPosZ());
        smallfireballentity.explosionPower = 1.2F;

        this.world.addEntity(smallfireballentity);
    }

    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof LivingEntity && ((LivingEntity) entityIn).getCreatureAttribute() == TofuCreatureAttribute.TOFUGUARIAN) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    public CreatureAttribute getCreatureAttribute() {
        return TofuCreatureAttribute.TOFUGUARIAN;
    }
}
