package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.entity.pathnavigator.FlyingCreaturePathNavigator;
import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class GrillderEntity extends AnimalEntity implements IFlyingAnimal {
    private static final Ingredient BREEDING_ITEMS = Ingredient.fromItems(TofuItems.SEEDS_SOYBEAN);

    private static final DataParameter<Boolean> IS_FLYING = EntityDataManager.createKey(GrillderEntity.class, DataSerializers.BOOLEAN);


    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    private boolean isLandNavigator = true;
    protected final FlyingCreaturePathNavigator flyNavigator;
    protected final GroundPathNavigator groundNavigator;

    public GrillderEntity(EntityType<? extends GrillderEntity> type, World worldIn) {
        super(type, worldIn);
        this.flyNavigator = new FlyingCreaturePathNavigator(this, worldIn);
        this.groundNavigator = new GroundPathNavigator(this, worldIn);
    }

    protected void registerGoals() {

        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new StopFlyGoal(this, 120));
        this.goalSelector.addGoal(2, new RandomFlyGoal(this, 1.3F, 20));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1.0F) {
            @Override
            public boolean shouldExecute() {
                return !isFlying() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(4, new LookAtGoal(this, LivingEntity.class, 8.0F));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue((double) 4.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.24F);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(IS_FLYING, false);
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveController = new MovementController(this);

            this.navigator = new GroundPathNavigator(this, world);
            this.setNoGravity(false);

            this.isLandNavigator = true;
        } else {
            this.moveController = new FlyingMovementController(this);

            this.navigator = new FlyingCreaturePathNavigator(this, world);
            this.isLandNavigator = false;
        }
    }

    public boolean isFlying() {
        return this.dataManager.get(IS_FLYING);
    }

    public void setFlying(boolean standing) {
        this.dataManager.set(IS_FLYING, standing);
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setFlying(compound.getBoolean("Flying"));
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Flying", this.isFlying());
    }

    public void livingTick() {
        super.livingTick();
        this.calculateFlapping();

        if (!isFlying() && this.rand.nextInt(320) == 0) {
            this.setFlying(true);
        }

        if (isFlying() && this.isLandNavigator) {

            switchNavigator(false);

        }

        if (!isFlying() && !this.isLandNavigator) {

            switchNavigator(true);

        }
    }

    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = (float) ((double) this.flapSpeed + (double) (!this.onGround && !this.isPassenger() ? 4 : -1) * 0.3D);
        this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }
        Vec3d vec3d = this.getMotion();
        if (!this.isFlying() && !this.onGround && vec3d.y < 0.0D) {
            this.setMotion(vec3d.mul(1.0D, 0.6D, 1.0D));
        }

        this.flapping = (float) ((double) this.flapping * 0.9D);

        this.flap += this.flapping * 2.0F;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return TofuEntitys.GRILLDER.create(this.world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_ITEMS.test(stack);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }


    static class RandomFlyGoal extends Goal {
        private final GrillderEntity parentEntity;
        protected final double speed;
        protected int executionChance;
        protected double x;
        protected double y;
        protected double z;
        protected boolean usingPathfind;

        protected int tick;

        public RandomFlyGoal(GrillderEntity grillderEntity, double speedIn, int chance) {
            this.parentEntity = grillderEntity;
            this.speed = speedIn;
            this.executionChance = chance;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean shouldExecute() {
            if (this.parentEntity.isFlying()) {
                Vec3d randomPosition = getPosition();

                if (randomPosition == null) {
                    return false;
                } else {
                    BlockPos groundHeight = getGroundHeight(this.parentEntity.world, new BlockPos(randomPosition), 10, new BlockPos(0, 0, 0));

                    if (randomPosition.y - groundHeight.getY() >= 10) {
                        randomPosition = randomPosition.add(0, -6, 0);
                    }

                    this.x = randomPosition.x;
                    this.y = randomPosition.y;
                    this.z = randomPosition.z;
                    return true;
                }
            }
            return false;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */

        @Override
        public boolean shouldContinueExecuting() {
            return !this.parentEntity.getNavigator().noPath();
        }

        @Override
        public void resetTask() {
            super.resetTask();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.tick = 0;
            this.parentEntity.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
        }

        protected static BlockPos getGroundHeight(World world, BlockPos pos, int maxIter, BlockPos fallback) {
            if (world.canBlockSeeSky(pos)) {
                return world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);
            }
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
            int i = 0;
            for (; i < maxIter; i++) {
                mutablePos.setPos(pos.getX(), pos.getY() - i, pos.getZ());
                if (!world.isAirBlock(mutablePos))
                    break;
            }
            if (i < maxIter) {
                return new BlockPos(pos.getX(), pos.getY() - i, pos.getZ());
            }
            return fallback;
        }

        @Nullable
        protected Vec3d getPosition() {
            return RandomPositionGenerator.findRandomTarget(this.parentEntity, 15, 6);
        }
    }

    private class StopFlyGoal extends Goal {
        private final GrillderEntity parentEntity;
        protected int executionChance;

        public StopFlyGoal(GrillderEntity grillderEntity, int chance) {
            this.parentEntity = grillderEntity;
            this.executionChance = chance;
        }

        public boolean shouldExecute() {
            if (this.parentEntity.isFlying()) {
                Vec3d randomPosition = getPosition();

                if (randomPosition == null || this.parentEntity.rand.nextInt(this.executionChance) == 0) {
                    return false;
                } else {
                    BlockPos groundHeight = getGroundHeight(this.parentEntity.world, new BlockPos(randomPosition), 8, new BlockPos(0, 0, 0));

                    if (randomPosition.y - groundHeight.getY() <= 8) {
                        return true;
                    }

                }
            }
            return false;
        }

        @Override
        public void startExecuting() {
            this.parentEntity.setFlying(false);
        }

        @Override
        public boolean shouldContinueExecuting() {
            return false;
        }

        protected BlockPos getGroundHeight(World world, BlockPos pos, int maxIter, BlockPos fallback) {
            if (world.canBlockSeeSky(pos)) {
                return world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);
            }
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
            int i = 0;
            for (; i < maxIter; i++) {
                mutablePos.setPos(pos.getX(), pos.getY() - i, pos.getZ());
                if (!world.isAirBlock(mutablePos))
                    break;
            }
            if (i < maxIter) {
                return new BlockPos(pos.getX(), pos.getY() - i, pos.getZ());
            }
            return fallback;
        }

        @Nullable
        protected Vec3d getPosition() {
            return RandomPositionGenerator.findRandomTarget(this.parentEntity, 1, 8);
        }

    }
}
