package baguchan.mcmod.tofucraft.entity.projectile;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class NetherFukumameEntity extends ThrowableEntity {
    public NetherFukumameEntity(EntityType<? extends NetherFukumameEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public NetherFukumameEntity(World worldIn, LivingEntity throwerIn) {
        super(TofuEntitys.NETHER_FUKUMAME, throwerIn, worldIn);
    }

    public NetherFukumameEntity(World worldIn, double x, double y, double z) {
        super(TofuEntitys.NETHER_FUKUMAME, x, y, z, worldIn);
    }

    public NetherFukumameEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(TofuEntitys.NETHER_FUKUMAME, world);
    }

    @Override
    protected void registerData() {

    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            double d0 = 0.08D;

            for (int i = 0; i < 6; ++i) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(TofuItems.SEEDS_SOYBEAN_NETHER)), this.getPosX(), this.getPosY(), this.getPosZ(), ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }


    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
        Entity entity = result.getEntity();

        if (this.func_234616_v_() == entity && this.ticksExisted < 4) {
            return;
        }

        entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), (float) 2);

        entity.hurtResistantTime = 5;

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult rayTraceResult) {
        super.func_230299_a_(rayTraceResult);

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        RayTraceResult result2 = ProjectileHelper.func_234618_a_(this, this::func_230298_a_, RayTraceContext.BlockMode.COLLIDER);

        RayTraceResult.Type raytraceresult$type = result2.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
            this.onEntityHit((EntityRayTraceResult) result2);
        } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
            this.func_230299_a_((BlockRayTraceResult) result2);
        }

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