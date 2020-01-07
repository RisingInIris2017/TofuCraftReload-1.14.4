package baguchan.mcmod.tofucraft.entity.projectile;

import baguchan.mcmod.tofucraft.init.TofuDamageSource;
import baguchan.mcmod.tofucraft.init.TofuEntitys;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.UUID;

public class TofuHomingForceEntity extends Entity {
    private LivingEntity owner;
    private Entity target;
    private int steps = 1200;
    @Nullable
    private UUID ownerUniqueId;
    private BlockPos ownerBlockPos;
    @Nullable
    private UUID targetUniqueId;
    private BlockPos targetBlockPos;

    public TofuHomingForceEntity(EntityType<? extends TofuHomingForceEntity> p_i50161_1_, World p_i50161_2_) {
        super(p_i50161_1_, p_i50161_2_);
        this.noClip = true;
    }

    @OnlyIn(Dist.CLIENT)
    public TofuHomingForceEntity(World worldIn, double x, double y, double z, double motionXIn, double motionYIn, double motionZIn) {
        this(TofuEntitys.TOFU_HOMING_FORCE, worldIn);
        this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
        this.setMotion(motionXIn, motionYIn, motionZIn);
    }

    public TofuHomingForceEntity(World worldIn, LivingEntity ownerIn, Entity targetIn) {
        this(TofuEntitys.TOFU_HOMING_FORCE, worldIn);
        this.owner = ownerIn;
        BlockPos blockpos = new BlockPos(ownerIn);
        double d0 = (double) blockpos.getX() + 0.5D;
        double d1 = (double) blockpos.getY() + 0.5D;
        double d2 = (double) blockpos.getZ() + 0.5D;
        this.setLocationAndAngles(d0, d1, d2, this.rotationYaw, this.rotationPitch);
        this.target = targetIn;
    }

    public TofuHomingForceEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(TofuEntitys.TOFU_HOMING_FORCE, world);
    }

    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    protected void writeAdditional(CompoundNBT compound) {
        if (this.owner != null) {
            BlockPos blockpos = new BlockPos(this.owner);
            CompoundNBT compoundnbt = NBTUtil.writeUniqueId(this.owner.getUniqueID());
            compoundnbt.putInt("X", blockpos.getX());
            compoundnbt.putInt("Y", blockpos.getY());
            compoundnbt.putInt("Z", blockpos.getZ());
            compound.put("Owner", compoundnbt);
        }

        if (this.target != null) {
            BlockPos blockpos1 = new BlockPos(this.target);
            CompoundNBT compoundnbt1 = NBTUtil.writeUniqueId(this.target.getUniqueID());
            compoundnbt1.putInt("X", blockpos1.getX());
            compoundnbt1.putInt("Y", blockpos1.getY());
            compoundnbt1.putInt("Z", blockpos1.getZ());
            compound.put("Target", compoundnbt1);
        }


        compound.putInt("Steps", this.steps);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditional(CompoundNBT compound) {
        this.steps = compound.getInt("Steps");

        if (compound.contains("Owner", 10)) {
            CompoundNBT compoundnbt = compound.getCompound("Owner");
            this.ownerUniqueId = NBTUtil.readUniqueId(compoundnbt);
            this.ownerBlockPos = new BlockPos(compoundnbt.getInt("X"), compoundnbt.getInt("Y"), compoundnbt.getInt("Z"));
        }

        if (compound.contains("Target", 10)) {
            CompoundNBT compoundnbt1 = compound.getCompound("Target");
            this.targetUniqueId = NBTUtil.readUniqueId(compoundnbt1);
            this.targetBlockPos = new BlockPos(compoundnbt1.getInt("X"), compoundnbt1.getInt("Y"), compoundnbt1.getInt("Z"));
        }

    }

    protected void registerData() {
    }


    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        if (!this.world.isRemote && this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.world.addParticle(ParticleTypes.EXPLOSION, this.posX, this.posY, this.posZ, 0.0D, 0.2D, 0.0D);
            this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.0F);
            this.remove();
        } else {
            super.tick();
            if (!this.world.isRemote) {
                if (this.target == null && this.targetUniqueId != null) {
                    for (LivingEntity livingentity : this.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(this.targetBlockPos.add(-2, -2, -2), this.targetBlockPos.add(2, 2, 2)))) {
                        if (livingentity.getUniqueID().equals(this.targetUniqueId)) {
                            this.target = livingentity;
                            break;
                        }
                    }

                    this.targetUniqueId = null;
                }

                if (this.owner == null && this.ownerUniqueId != null) {
                    for (LivingEntity livingentity1 : this.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(this.ownerBlockPos.add(-2, -2, -2), this.ownerBlockPos.add(2, 2, 2)))) {
                        if (livingentity1.getUniqueID().equals(this.ownerUniqueId)) {
                            this.owner = livingentity1;
                            break;
                        }
                    }

                    this.ownerUniqueId = null;
                }

                if (this.target == null || !this.target.isAlive() || this.target instanceof PlayerEntity && ((PlayerEntity) this.target).isSpectator()) {
                    if (!this.hasNoGravity()) {
                        this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
                    }
                } else {

                    Vec3d vec3d = this.getMotion();
                    Vec3d vec3d2 = new Vec3d(target.posX - this.posX, target.posY + (double) target.getEyeHeight() - this.posY, target.posZ - this.posZ);
                    Vec3d vec3d3 = vec3d2.normalize().scale(0.5F);

                    this.setMotion(MathHelper.clamp((vec3d3.x - vec3d.x) * 0.5D, -0.3F, 0.3F), MathHelper.clamp((vec3d3.y - vec3d.y) * 0.5D, -0.3F, 0.3F), MathHelper.clamp((vec3d3.z - vec3d.z) * 0.5D, -0.3F, 0.3F));

                }

                RayTraceResult raytraceresult = ProjectileHelper.rayTrace(this, true, false, this.owner, RayTraceContext.BlockMode.COLLIDER);
                if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                    this.bulletHit(raytraceresult);
                }
            }

            Vec3d vec3d1 = this.getMotion();
            this.setPosition(this.posX + vec3d1.x, this.posY + vec3d1.y, this.posZ + vec3d1.z);
            ProjectileHelper.rotateTowardsMovement(this, 0.5F);
            if (this.world.isRemote) {
                this.world.addParticle(ParticleTypes.CLOUD, this.posX - vec3d1.x, this.posY - vec3d1.y + 0.15D, this.posZ - vec3d1.z, 0.0D, 0.0D, 0.0D);
            }

            if (this.steps > 0) {
                --this.steps;
            } else {
                this.world.addParticle(ParticleTypes.EXPLOSION, this.posX, this.posY, this.posZ, 0.0D, 0.2D, 0.0D);
                this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.0F);
                this.remove();
            }

        }
    }

    /**
     * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
     */
    public boolean isBurning() {
        return false;
    }

    /**
     * Checks if the entity is in range to render.
     */
    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        return distance < 16384.0D;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness() {
        return 1.0F;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }

    protected void bulletHit(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            boolean flag = entity.attackEntityFrom(TofuDamageSource.causeTofuForceDamage(this, this.owner).setProjectile(), 5.0F);
            if (flag) {
                this.applyEnchantments(this.owner, entity);
                if (entity instanceof LivingEntity) {
                    ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 400));
                    ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.WEAKNESS, 200));
                }
            }
        } else {
            ((ServerWorld) this.world).spawnParticle(ParticleTypes.EXPLOSION, this.posX, this.posY, this.posZ, 2, 0.2D, 0.2D, 0.2D, 0.0D);
            this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.0F);
        }

        this.remove();
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!this.world.isRemote) {
            this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HURT, 1.0F, 1.0F);
            ((ServerWorld) this.world).spawnParticle(ParticleTypes.CRIT, this.posX, this.posY, this.posZ, 15, 0.2D, 0.2D, 0.2D, 0.0D);
            this.remove();
        }

        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}