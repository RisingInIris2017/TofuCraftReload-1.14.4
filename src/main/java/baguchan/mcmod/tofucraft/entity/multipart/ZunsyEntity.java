package baguchan.mcmod.tofucraft.entity.multipart;

import baguchan.mcmod.tofucraft.entity.TofuChingerEntity;
import baguchan.mcmod.tofucraft.entity.movement.FlyingStrafeMovementController;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class ZunsyEntity extends CreatureEntity {
    private float heightOffset = 0.5f;
    private int heightOffsetUpdateTime;
    public List<ZundamaEntity> listZundama = Lists.newArrayList();

    public ZunsyEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new FlyingStrafeMovementController(this, 15, false);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        //this.goalSelector.addGoal(2, new RangedStrafeAttackGoal<>(this, 1.0D, 65, 20F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 0.95D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, TofuChingerEntity.class, true));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(26.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue((double) 0.65D);
        //this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {

        for (int l = 0; l < 4; ++l) {
            listZundama.add(new ZundamaEntity(this.world));
        }

        for (int l = 0; l < this.listZundama.size(); ++l) {
            listZundama.get(l).setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
            listZundama.get(l).setTrack(true);
            worldIn.addEntity(listZundama.get(l));
        }

        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        if (compound.contains("Zundamas", 9)) {
            ListNBT listnbt = compound.getList("Zundamas", 10);
            if (this.world != null) {
                this.listZundama.clear();

                for (int i = 0; i < listnbt.size(); ++i) {
                    Optional<EntityType<?>> optional = EntityType.readEntityType(listnbt.getCompound(i));
                    if (optional != null) {
                        Entity entity = optional.get().create(world);
                        entity.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());

                        if (entity instanceof ZundamaEntity) {
                            this.listZundama.add((ZundamaEntity) entity);
                            ((ZundamaEntity) entity).setTrack(true);
                        }
                        world.addEntity(entity);
                    }
                }
            }
        }
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        if (!this.listZundama.isEmpty()) {
            ListNBT listnbt = new ListNBT();

            for (Entity entity : this.listZundama) {
                listnbt.add(entity.writeWithoutTypeId(new CompoundNBT()));
            }

            compound.put("Zundamas", listnbt);
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {
            if (!this.listZundama.isEmpty()) {
                Vec3d[] avec3d = new Vec3d[this.listZundama.size()];

                for (int j = 0; j < this.listZundama.size(); ++j) {
                    avec3d[j] = new Vec3d(this.listZundama.get(j).getPosX(), this.listZundama.get(j).getPosY(), this.listZundama.get(j).getPosZ());
                }

                this.movePart();

                for (int l = 0; l < this.listZundama.size(); ++l) {
                    this.listZundama.get(l).prevPosX = avec3d[l].x;
                    this.listZundama.get(l).prevPosY = avec3d[l].y;
                    this.listZundama.get(l).prevPosZ = avec3d[l].z;
                    this.listZundama.get(l).lastTickPosX = avec3d[l].x;
                    this.listZundama.get(l).lastTickPosY = avec3d[l].y;
                    this.listZundama.get(l).lastTickPosZ = avec3d[l].z;
                }
            }
        }
    }

    private void movePart() {
        for (int i = 0; i < this.listZundama.size(); i++) {
            if (this.listZundama.get(i).isAlive()) {
                LivingEntity leader = i == 0 ? this : this.listZundama.get(i - 1);
                double followX = leader.getPosX();
                double followY = leader.getPosY();
                double followZ = leader.getPosZ();

                float angle = (((leader.rotationYaw + 180) * 3.141593F) / 180F);


                double straightenForce = 0.05D + (1.0 / (float) (i + 1)) * 0.5D;

                double idealX = -MathHelper.sin(angle) * straightenForce;
                double idealZ = MathHelper.cos(angle) * straightenForce;


                Vec3d diff = new Vec3d(listZundama.get(i).getPosX() - followX, listZundama.get(i).getPosY() - followY, listZundama.get(i).getPosZ() - followZ);
                diff = diff.normalize();

                // weight so segments drift towards their ideal position
                diff = diff.add(idealX, 0, idealZ).normalize();

                double f = 0.8D;

                double destX = followX + f * diff.x;
                double destY = followY + f * diff.y;
                double destZ = followZ + f * diff.z;

                listZundama.get(i).setPosition(destX, destY, destZ);

                double distance = (double) MathHelper.sqrt(diff.x * diff.x + diff.z * diff.z);

                listZundama.get(i).setHeadRotation((float) (Math.atan2(diff.z, diff.x) * 180.0D / Math.PI) + 90.0F, (int) -(Math.atan2(diff.y, distance) * 180.0D / Math.PI));
            }
        }
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
    public void onDeath(DamageSource cause) {
        if (!this.listZundama.isEmpty()) {
            for (int l = 0; l < this.listZundama.size(); ++l) {
                listZundama.get(l).setTrack(false);
            }
        }
        super.onDeath(cause);
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
}
