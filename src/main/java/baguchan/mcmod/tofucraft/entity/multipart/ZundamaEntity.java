package baguchan.mcmod.tofucraft.entity.multipart;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.HandSide;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class ZundamaEntity extends LivingEntity {
    private static final DataParameter<Boolean> TRACK = EntityDataManager.createKey(ZundamaEntity.class, DataSerializers.BOOLEAN);

    public ZundamaEntity(World world) {
        super(TofuEntitys.ZUNDAMA, world);
    }

    public ZundamaEntity(EntityType<? extends ZundamaEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(TRACK, false);
    }

    //This entity Part has Health
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SLIME_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
    }

    @Override
    public void onDeath(DamageSource cause) {
        for (int j = 0; j < 8; ++j) {
            float f = this.rand.nextFloat() * ((float) Math.PI * 2F);
            float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
            float f2 = MathHelper.sin(f) * (float) 0.5F * f1;
            float f3 = MathHelper.cos(f) * (float) 0.5F * f1;
            this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(TofuItems.ZUNDAMA)), this.getPosX() + (double) f2, this.getPosY(), this.getPosZ() + (double) f3, 0.0D, 0.0D, 0.0D);
        }
        super.onDeath(cause);
    }

    public void travel(Vec3d p_213352_1_) {
        if (this.isServerWorld() && this.isTrack()) {
            this.moveRelative(0.01F, p_213352_1_);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
        } else {
            super.travel(p_213352_1_);
        }

    }

    public boolean isTrack() {
        return this.dataManager.get(TRACK);
    }

    public void setTrack(boolean track) {
        this.dataManager.set(TRACK, track);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvisible()) {
            return false;
        } else {
            if (source == DamageSource.DROWN || isTrack() && source == DamageSource.IN_WALL) {
                return false;
            }

            if (source.isProjectile() && amount < 4.0F) {
                return false;
            } else if (source.isProjectile()) {
                return super.attackEntityFrom(source, amount * 0.25F);
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return ImmutableList.of();
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {

    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public HandSide getPrimaryHand() {
        return HandSide.RIGHT;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.remove();
        } else {
            Entity entity = this.world.getClosestPlayer(this, -1.0D);
            if (entity != null) {
                double d0 = entity.getDistanceSq(this);
                if (d0 > 16384.0D) {
                    this.remove();
                }

                if (this.idleTime > 600 && this.rand.nextInt(800) == 0 && d0 > 1024.0D) {
                    this.remove();
                } else if (d0 < 1024.0D) {
                    this.idleTime = 0;
                }
            }

        }
    }

    /**
     * Returns true if Entity argument is equal to this Entity
     */
    public boolean isEntityEqual(Entity entityIn) {
        return this == entityIn;
    }
}