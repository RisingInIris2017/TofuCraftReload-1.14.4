package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class SoySplashEntity extends Entity {

    private static final DataParameter<Boolean> FLOATING = EntityDataManager.createKey(SoySplashEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Float> FLOAT_HEIGHT = EntityDataManager.createKey(SoySplashEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> SPLASH_POWER = EntityDataManager.createKey(SoySplashEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> MAX_HEIGHT = EntityDataManager.createKey(SoySplashEntity.class, DataSerializers.VARINT);



    public SoySplashEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.setFloating(true);
    }

    public SoySplashEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(TofuEntitys.SOY_SPLASH, world);
    }


    @Override
    protected void registerData() {
        this.dataManager.register(FLOATING, false);
        this.dataManager.register(FLOAT_HEIGHT, 0F);
        this.dataManager.register(SPLASH_POWER, 0F);
        this.dataManager.register(MAX_HEIGHT, 6);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.setFloating(compound.getBoolean("Floating"));
        this.setFloatHeight(compound.getFloat("FloatHeight"));

        if (compound.contains("MaxHeight")) {
            this.setMaxHeight(compound.getInt("MaxHeight"));
        } else {
            this.setMaxHeight(6);
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putBoolean("Floating", this.isFloating());
        compound.putFloat("FloatHeight", this.getFloatHeight());
        compound.putFloat("MaxHeight", this.getMaxHeight());
    }

    public boolean isFloating() {
        return this.dataManager.get(FLOATING);
    }

    public void setFloating(boolean floating) {
        this.dataManager.set(FLOATING, floating);
    }

    public float getFloatHeight() {
        return this.dataManager.get(FLOAT_HEIGHT);
    }

    public void setFloatHeight(float floatHeight) {
        this.dataManager.set(FLOAT_HEIGHT, floatHeight);
    }

    public float getSplashPower() {
        return this.dataManager.get(SPLASH_POWER);
    }

    public void setSplashPower(float power) {
        this.dataManager.set(SPLASH_POWER, power);
    }

    public int getMaxHeight() {
        return this.dataManager.get(MAX_HEIGHT);
    }

    public void setMaxHeight(int height) {
        this.dataManager.set(MAX_HEIGHT, height);
    }


    public void recalculateSize() {
        double d0 = this.getPosX();
        double d1 = this.getPosY();
        double d2 = this.getPosZ();
        super.recalculateSize();
        this.setPosition(d0, d1, d2);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (FLOAT_HEIGHT.equals(key)) {
            this.recalculateSize();
        }

        super.notifyDataManagerChange(key);
    }

    @Override
    public void tick() {
        super.tick();

        this.setFloatHeight(MathHelper.clamp(this.getFloatHeight() + getSplashPower(), 0.0F, this.getMaxHeight()));
        if (this.isFloating()) {
            this.setSplashPower(MathHelper.clamp(this.getSplashPower() + 0.01F, -0.75F, 0.8F));
        } else {
            this.setSplashPower(MathHelper.clamp(this.getSplashPower() - 0.01F, -0.75F, 0.8F));
        }

        if (this.ticksExisted % 4 == 0) {
            this.playSound(SoundEvents.WEATHER_RAIN, 1.0F, 1.0F);
            this.doSoySplashEffect();
        }

        if (this.ticksExisted % 80 == 0) {
            if (this.isFloating() && this.getFloatHeight() >= this.getMaxHeight()) {
                setFloating(false);
            } else if (!isFloating() && this.getFloatHeight() <= 0.0F) {
                this.remove();
            }
        }

        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox());
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (!(entity instanceof SoySplashEntity) && !entity.noClip) {
                    entity.onGround = true;
                    entity.fallDistance = 0.0F;
                    if (this.getSplashPower() >= 0.3F) {
                        entity.setMotion(entity.getMotion().x, ((double) this.getSplashPower() - entity.getMotion().y) * (double) this.getSplashPower(), entity.getMotion().z);
                    } else {
                        entity.setMotion(entity.getMotion().x, ((double) 0.3F - entity.getMotion().y) * (double) 0.3F, entity.getMotion().z);
                    }
                }
            }
        }
    }

    protected void doSoySplashEffect() {
        float f = 0.9F;
        Vec3d vec3d = this.getMotion();
        float f2 = (float) MathHelper.floor(this.getPosY());


        for (int j = 0; (float) j < 1.0F + this.getWidth() * 20.0F; ++j) {
            float f5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.getWidth();
            float f6 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.getWidth();

            float f7 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.getHeight();
            this.world.addParticle(ParticleTypes.SPLASH, this.getPosX() + (double) f5, (double) (f2 + f7), this.getPosZ() + (double) f6, vec3d.x, vec3d.y, vec3d.z);
        }

    }

    public PushReaction getPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        return EntitySize.flexible(1.0F, 1.0F * this.getFloatHeight());
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
