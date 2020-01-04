package baguchan.mcmod.tofucraft.entity.projectile;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class BeamEntity extends AbstractFireballEntity {
    public boolean isCanBreak = false;

    public BeamEntity(EntityType<? extends BeamEntity> p_i50160_1_, World p_i50160_2_) {
        super(p_i50160_1_, p_i50160_2_);
    }

    public BeamEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(TofuEntitys.BEAM, shooter, accelX, accelY, accelZ, worldIn);
    }

    public BeamEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(TofuEntitys.BEAM, x, y, z, accelX, accelY, accelZ, worldIn);
    }

    public BeamEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(TofuEntitys.BEAM, world);
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.getType() == RayTraceResult.Type.ENTITY) {
                Entity entity = ((EntityRayTraceResult) result).getEntity();

                boolean flag = entity.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 4.0F);
                if (flag) {
                    this.applyEnchantments(this.shootingEntity, entity);
                }

                this.world.createExplosion(this.shootingEntity, this.posX, this.posY, this.posZ, 0.4F, this.isCanBreak ? Explosion.Mode.BREAK : Explosion.Mode.NONE);

            } else if (this.shootingEntity == null || !(this.shootingEntity instanceof MobEntity) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.shootingEntity)) {
                BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) result;
                BlockPos blockpos = blockraytraceresult.getPos().offset(blockraytraceresult.getFace());

                this.world.createExplosion(this.shootingEntity, blockpos.getX(), blockpos.getY(), blockpos.getZ(), 0.4F, this.isCanBreak ? Explosion.Mode.BREAK : Explosion.Mode.NONE);

            }

            this.remove();
        }

    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("CanBreak", this.isCanBreak);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("CanBreak")) {
            this.isCanBreak = compound.getBoolean("CanBreak");
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return false;
    }


    @Override
    protected boolean isFireballFiery() {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
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