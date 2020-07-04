package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.entity.ai.HealSpellGoal;
import baguchan.mcmod.tofucraft.entity.ai.RangedStrafeAttackGoal;
import baguchan.mcmod.tofucraft.entity.ai.SoyshotGoal;
import baguchan.mcmod.tofucraft.entity.ai.SummonMinionGoal;
import baguchan.mcmod.tofucraft.entity.movement.FlyingStrafeMovementController;
import baguchan.mcmod.tofucraft.entity.projectile.BeamEntity;
import baguchan.mcmod.tofucraft.entity.projectile.FukumameEntity;
import baguchan.mcmod.tofucraft.init.TofuCreatureAttribute;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class TofuGandlemEntity extends MonsterEntity implements IRangedAttackMob {
    private static final DataParameter<Boolean> SLEEP = EntityDataManager.createKey(TofuGandlemEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CASTING = EntityDataManager.createKey(TofuGandlemEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SHOOTING = EntityDataManager.createKey(TofuGandlemEntity.class, DataSerializers.BOOLEAN);


    private float heightOffset = 0.5f;
    private int heightOffsetUpdateTime;

    private float prevClientSideSpellCastingAnimation;
    private float clientSideSpellCastingAnimation;
    private float prevClientSideAttackingAnimation;
    private float clientSideAttackingAnimation;
    private float prevClientSideShootingAnimation;
    private float clientSideShootingAnimation;

    private final ServerBossInfo bossInfo = (ServerBossInfo) (new ServerBossInfo(this.getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS));


    public TofuGandlemEntity(EntityType<? extends TofuGandlemEntity> type, World p_i48553_2_) {
        super(type, p_i48553_2_);
        this.moveController = new FlyingStrafeMovementController(this, 15, false);
        this.experienceValue = 100;
        this.bossInfo.setVisible(false);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SLEEP, false);
        this.dataManager.register(CASTING, false);
        this.dataManager.register(SHOOTING, false);
    }

    public boolean isSleep() {
        return this.dataManager.get(SLEEP);
    }

    public void setSleep(boolean isSleep) {
        this.dataManager.set(SLEEP, isSleep);
    }

    public boolean isCasting() {
        return this.dataManager.get(CASTING);
    }

    public void setCasting(boolean isCasting) {
        this.dataManager.set(CASTING, isCasting);
    }

    public void setShooting(boolean shooting) {
        this.dataManager.set(SHOOTING, shooting);
    }

    public boolean isShooting() {
        return this.dataManager.get(SHOOTING);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new DoNothingGoal());
        this.goalSelector.addGoal(1, new SwimGoal(this));
        //this.goalSelector.addGoal(2, new TofuHomingShotGoal(this));
        this.goalSelector.addGoal(3, new SoyshotGoal(this));
        this.goalSelector.addGoal(4, new SummonMinionGoal(this));
        this.goalSelector.addGoal(5, new HealSpellGoal(this));
        this.goalSelector.addGoal(6, new RangedStrafeAttackGoal<>(this, 0.95D, 65, 20F));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomFlyingGoal(this, 0.95D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, false));
    }

    public static AttributeModifierMap.MutableAttribute getAttributeMap() {
        return MonsterEntity.func_233666_p_().func_233815_a_(Attributes.field_233821_d_, (double) 0.26F).func_233815_a_(Attributes.field_233822_e_, 0.6D).func_233815_a_(Attributes.field_233818_a_, 400.0D).func_233815_a_(Attributes.field_233819_b_, 40.0D).func_233815_a_(Attributes.field_233826_i_, 8.0D);
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
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putBoolean("Sleep", this.isSleep());
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        this.setSleep(compound.getBoolean("Sleep"));

        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }
    }

    public void setCustomName(@Nullable ITextComponent name) {
        super.setCustomName(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    protected void updateAITasks() {

        if (!this.isSleep()) {
            --this.heightOffsetUpdateTime;

            if (this.heightOffsetUpdateTime <= 0) {
                this.heightOffsetUpdateTime = 100;
                this.heightOffset = 0.5f + (float) this.rand.nextGaussian() * 3f;
            }

            LivingEntity target = getAttackTarget();
            Vector3d vec3d = this.getMotion();
            if (target != null && target.isAlive() && target.getPosY() + (double) target.getEyeHeight() > this.getPosY() + (double) getEyeHeight() + (double) this.heightOffset && this.isAlive()) {
                this.setMotion(this.getMotion().add(0.0D, ((double) 0.3F - vec3d.y) * (double) 0.3F, 0.0D));
                this.isAirBorne = true;
            }

            if (!this.onGround && vec3d.y < 0.0D) {
                this.setMotion(vec3d.mul(1.0D, 0.6D, 1.0D));
            }

            if (!this.bossInfo.isVisible()) {
                this.bossInfo.setVisible(true);
            }

            super.updateAITasks();
        } else {
            if (this.bossInfo.isVisible()) {
                this.bossInfo.setVisible(false);
            }

            if (this.world.getDifficulty() == Difficulty.PEACEFUL && !this.isSleep()) {
                this.setSleep(true);
            }
        }

        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void tick() {
        super.tick();

        if (this.world.isRemote) {
            this.prevClientSideSpellCastingAnimation = this.clientSideSpellCastingAnimation;
            if (this.isCasting()) {
                this.clientSideSpellCastingAnimation = MathHelper.clamp(this.clientSideSpellCastingAnimation + 0.5F, 0.0F, 6.0F);
            } else {
                this.clientSideSpellCastingAnimation = MathHelper.clamp(this.clientSideSpellCastingAnimation - 1.0F, 0.0F, 6.0F);
            }

            this.prevClientSideAttackingAnimation = this.clientSideAttackingAnimation;
            if (this.isAggressive()) {
                this.clientSideAttackingAnimation = MathHelper.clamp(this.clientSideAttackingAnimation + 0.5F, 0.0F, 6.0F);
            } else {
                this.clientSideAttackingAnimation = MathHelper.clamp(this.clientSideAttackingAnimation - 1.0F, 0.0F, 6.0F);
            }

            this.prevClientSideShootingAnimation = this.clientSideShootingAnimation;
            if (this.isShooting()) {
                this.clientSideShootingAnimation = MathHelper.clamp(this.clientSideShootingAnimation + 1.0F, 0.0F, 6.0F);
            } else {
                this.clientSideShootingAnimation = MathHelper.clamp(this.clientSideShootingAnimation - 1.0F, 0.0F, 6.0F);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getSpellCastingAnimationScale(float p_189795_1_) {
        return (this.prevClientSideSpellCastingAnimation + (this.clientSideSpellCastingAnimation - this.prevClientSideSpellCastingAnimation) * p_189795_1_) / 6.0F;
    }

    @OnlyIn(Dist.CLIENT)
    public float getAttackingAnimationScale(float p_189795_1_) {
        return (this.prevClientSideAttackingAnimation + (this.clientSideAttackingAnimation - this.prevClientSideAttackingAnimation) * p_189795_1_) / 6.0F;
    }

    @OnlyIn(Dist.CLIENT)
    public float getShootingAnimationScale(float p_189795_1_) {
        return (this.prevClientSideShootingAnimation + (this.clientSideShootingAnimation - this.prevClientSideShootingAnimation) * p_189795_1_) / 6.0F;
    }

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
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isSleep() && source.getTrueSource() instanceof LivingEntity) {
            if (this.world.getDifficulty() != Difficulty.PEACEFUL) {
                this.playSound(SoundEvents.ENTITY_WITHER_SPAWN, 2.0F, 1.0F);
                this.setSleep(false);
            }
            return false;
        } else if (isSleep()) {
            return false;
        }

        if (source.getImmediateSource() instanceof FukumameEntity) {
            return false;
        }


        if (source.isExplosion() || source.isFireDamage()) {
            return super.attackEntityFrom(source, amount * 0.75F);
        } else {
            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean isPotionApplicable(EffectInstance potioneffectIn) {
        if (potioneffectIn.getPotion() == Effects.POISON || potioneffectIn.getPotion() == Effects.WITHER) {
            net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, potioneffectIn);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
        }
        return super.isPotionApplicable(potioneffectIn);
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity livingEntity, float v) {
        this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.2F, 1.0F);
        double d1 = livingEntity.getPosX() - this.getPosX();
        double d2 = livingEntity.getBoundingBox().minY + (double) (livingEntity.getHeight() / 2.0F) - (this.getPosY() + (double) (this.getEyeHeight()));
        double d3 = livingEntity.getPosZ() - this.getPosZ();
        float f = 0.075F;
        BeamEntity smallfireballentity = new BeamEntity(this.world, this, d1 + this.getRNG().nextGaussian() * (double) f - this.getRNG().nextGaussian() * (double) f, d2, d3 + this.getRNG().nextGaussian() * (double) f - this.getRNG().nextGaussian() * (double) f);
        smallfireballentity.setPosition(smallfireballentity.getPosX(), this.getPosY() + (double) (this.getEyeHeight()), smallfireballentity.getPosZ());
        smallfireballentity.explosionPower = 1.0F;

        this.world.addEntity(smallfireballentity);
    }

    public void addTrackingPlayer(ServerPlayerEntity player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See {@link Entity#addTrackingPlayer} for
     * more information on tracking.
     */
    public void removeTrackingPlayer(ServerPlayerEntity player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
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

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean isNonBoss() {
        return false;
    }

    class DoNothingGoal extends Goal {
        public DoNothingGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return TofuGandlemEntity.this.isSleep();
        }
    }
}
