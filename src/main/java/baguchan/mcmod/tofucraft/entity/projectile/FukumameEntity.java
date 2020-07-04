package baguchan.mcmod.tofucraft.entity.projectile;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class FukumameEntity extends ThrowableEntity {
    public FukumameEntity(EntityType<? extends FukumameEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public FukumameEntity(World worldIn, LivingEntity throwerIn) {
        super(TofuEntitys.FUKUMAME, throwerIn, worldIn);
    }

    public FukumameEntity(World worldIn, double x, double y, double z) {
        super(TofuEntitys.FUKUMAME, x, y, z, worldIn);
    }

    public FukumameEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(TofuEntitys.FUKUMAME, world);
    }

    @Override
    protected void registerData() {

    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            double d0 = 0.08D;

            for (int i = 0; i < 6; ++i) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(TofuItems.SEEDS_SOYBEAN)), this.getPosX(), this.getPosY(), this.getPosZ(), ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }


    @Override
    protected void onImpact(RayTraceResult result) {

        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();

            if (this.func_234616_v_() == entity && this.ticksExisted < 4) {
                return;
            }

            entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), (float) 2);

            entity.hurtResistantTime = 5;
        }
        this.world.setEntityState(this, (byte) 3);
        this.remove();
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
