package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.entity.ai.WaterAvoidingFlyGoal;
import baguchan.mcmod.tofucraft.entity.pathnavigator.FlyingSkyPathNavigator;
import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

import javax.annotation.Nullable;
import java.util.UUID;

public class TofuEagleEntity extends TameableEntity {
    private static final DataParameter<Boolean> FLYING = EntityDataManager.createKey(TofuEagleEntity.class, DataSerializers.BOOLEAN);

    protected final FlyingSkyPathNavigator flyNavigator;
    protected final GroundPathNavigator groundNavigator;

    public TofuEagleEntity(EntityType<? extends TofuEagleEntity> p_i48553_1_, World worldIn) {
        super(p_i48553_1_, worldIn);
        this.moveController = new MoveHelperController(this);
        this.experienceValue = 4;

        this.flyNavigator = new FlyingSkyPathNavigator(this, worldIn);
        this.groundNavigator = new GroundPathNavigator(this, worldIn);
    }

    protected void registerGoals() {
        this.sitGoal = new SitGoal(this) {
            @Override
            public boolean shouldExecute() {
                return !isFlying() && super.shouldExecute();
            }
        };
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(3, this.sitGoal);
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowOwnerFlyingGoal(this, 1.0D, 5.0F, 1.0F));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D) {
            @Override
            public boolean shouldExecute() {
                return !isFlying() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(6, new WaterAvoidingFlyGoal(this, 1.0D, 22.0F) {
            @Override
            public boolean shouldExecute() {
                return isFlying() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(7, new LookAtGoal(this, MobEntity.class, 6.0F));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, TofuSlimeEntity.class, true, EntityPredicates.IS_LIVING_ALIVE));
    }


    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(22.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Nullable
    @Override
    public TofuEagleEntity createChild(AgeableEntity ageable) {
        TofuEagleEntity entity = TofuEntitys.TOFUEAGLE.create(this.world);
        UUID uuid = this.getOwnerId();
        if (uuid != null) {
            entity.setOwnerId(uuid);
            entity.setTamed(true);
        }

        return entity;
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(FLYING, false);
    }

    public void livingTick() {
        super.livingTick();
        this.updateFlying();
    }

    protected boolean isNearGround() {
        Path path = this.getNavigator().getPath();
        if (path != null) {

            BlockPos blockpos = this.world.getHeight(Heightmap.Type.WORLD_SURFACE, this.getPosition());
            if (blockpos != null) {
                double d0 = this.getDistanceSq((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ());
                if (d0 < 3.5D) {
                    return true;
                }
            }
        }

        return false;
    }

    public void updateFlying() {
        if (!this.world.isRemote) {
            if (!isSitting() && this.isServerWorld() && !this.isFlying() && this.rand.nextInt(300) == 0) {
                this.navigator = this.flyNavigator;
                this.setFlying(true);
            } else if (this.isFlying() && isNearGround() && this.rand.nextInt(120) == 0 && this.getAttackTarget() == null) {
                this.navigator = this.groundNavigator;
                this.setFlying(false);
            } else if (this.isFlying() && isSitting()) {
                this.navigator = this.groundNavigator;
                this.setFlying(false);
            }
        }
    }

    @Override
    public float getEyeHeight(Pose p_213307_1_) {
        return this.getHeight() * 0.5F;
    }

    @Override
    public void travel(Vec3d p_213352_1_) {
        if (this.isServerWorld() && this.isFlying()) {
            this.moveRelative(0.1F, p_213352_1_);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
        } else {
            super.travel(p_213352_1_);
        }
    }

    public boolean isFlying() {
        return this.dataManager.get(FLYING);
    }

    public void setFlying(boolean flying) {
        this.dataManager.set(FLYING, flying);
    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if (this.isTamed()) {
            if (!itemstack.isEmpty()) {
                if (item.isFood()) {
                    if ((item.getFood().isMeat() || item == TofuItems.TOFUHAMBURG_RAW) && this.getHealth() < this.getMaxHealth()) {
                        if (!player.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }

                        this.heal((float) item.getFood().getHealing());
                        return true;
                    }
                }
            }

            if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(itemstack)) {
                this.sitGoal.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget((LivingEntity) null);
            }
        } else if (item == TofuItems.TOFUHAMBURG_RAW) {
            if (!player.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }

            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity) null);
                    this.sitGoal.setSitting(true);
                    this.setHealth(this.getMaxHealth());
                    this.playTameEffect(true);
                    this.world.setEntityState(this, (byte) 7);
                } else {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte) 6);
                }
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    public boolean isBreedingItem(ItemStack stack) {
        Item item = stack.getItem();
        return item.isFood() && (item.getFood().isMeat() || item == TofuItems.TOFUHAMBURG_RAW);
    }

    public int getMaxSpawnedInChunk() {
        return 3;
    }

    public boolean canMateWith(AnimalEntity otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (!this.isTamed()) {
            return false;
        } else if (!(otherAnimal instanceof TofuEagleEntity)) {
            return false;
        } else {
            TofuEagleEntity wolfentity = (TofuEagleEntity) otherAnimal;
            if (!wolfentity.isTamed()) {
                return false;
            } else if (wolfentity.isSitting()) {
                return false;
            } else {
                return this.isInLove() && wolfentity.isInLove();
            }
        }
    }

    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity)) {
            if (target instanceof TofuEagleEntity) {
                TofuEagleEntity wolfentity = (TofuEagleEntity) target;
                if (wolfentity.isTamed() && wolfentity.getOwner() == owner) {
                    return false;
                }
            }

            if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity) owner).canAttackPlayer((PlayerEntity) target)) {
                return false;
            } else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity) target).isTame()) {
                return false;
            } else if (target instanceof TameableEntity && ((TameableEntity) target).isTamed()) {
                return false;
            } else {
                return !(target instanceof CatEntity) || !((CatEntity) target).isTamed();
            }
        } else {
            return false;
        }
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    static class MoveHelperController extends MovementController {
        private final TofuEagleEntity tofueagle;

        MoveHelperController(TofuEagleEntity tofueagleIn) {
            super(tofueagleIn);
            this.tofueagle = tofueagleIn;
        }

        public void tick() {
            if (this.tofueagle.isFlying() && this.action == MovementController.Action.MOVE_TO) {
                double d0 = this.posX - this.tofueagle.posX;
                double d1 = this.posY - this.tofueagle.posY;
                double d2 = this.posZ - this.tofueagle.posZ;
                double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.tofueagle.rotationYaw = this.limitAngle(this.tofueagle.rotationYaw, f, 90.0F);
                this.tofueagle.renderYawOffset = this.tofueagle.rotationYaw;
                float f1 = (float) (this.speed * this.tofueagle.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
                this.tofueagle.setAIMoveSpeed(MathHelper.lerp(0.125F, this.tofueagle.getAIMoveSpeed(), f1));
                this.tofueagle.setMotion(this.tofueagle.getMotion().add(0.0D, (double) this.tofueagle.getAIMoveSpeed() * d1 * 0.145D, 0.0D));

            } else {
                super.tick();
            }
        }
    }
}