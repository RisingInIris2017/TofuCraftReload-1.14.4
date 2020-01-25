package baguchan.mcmod.tofucraft.entity.projectile;

import baguchan.mcmod.tofucraft.entity.TofuSlimeEntity;
import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuItems;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Arrays;
import java.util.List;

public class ZundaArrowEntity extends AbstractArrowEntity {
    private IntOpenHashSet field_213878_az;
    private List<Entity> field_213875_aA;
    private int duration = 200;

    public ZundaArrowEntity(EntityType<? extends ZundaArrowEntity> p_i50158_1_, World p_i50158_2_) {
        super(p_i50158_1_, p_i50158_2_);
    }

    public ZundaArrowEntity(World worldIn, LivingEntity shooter) {
        super(TofuEntitys.ZUNDAARROW, shooter, worldIn);
    }

    public ZundaArrowEntity(World worldIn, double x, double y, double z) {
        super(TofuEntitys.ZUNDAARROW, x, y, z, worldIn);
    }

    public ZundaArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {

        this(TofuEntitys.ZUNDAARROW, world);

    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        if (this.world.isRemote && !this.inGround) {
            this.world.addParticle(ParticleTypes.INSTANT_EFFECT, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        }

    }

    protected ItemStack getArrowStack() {
        return new ItemStack(TofuItems.ZUNDAARROW);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        float f = (float) this.getMotion().length();
        int i = MathHelper.ceil(Math.max((double) f * this.getDamage(), 0.0D));
        if (this.getPierceLevel() > 0) {
            if (this.field_213878_az == null) {
                this.field_213878_az = new IntOpenHashSet(5);
            }

            if (this.field_213875_aA == null) {
                this.field_213875_aA = Lists.newArrayListWithCapacity(5);
            }

            if (this.field_213878_az.size() >= this.getPierceLevel() + 1) {
                this.remove();
                return;
            }

            this.field_213878_az.add(entity.getEntityId());
        }

        if (this.getIsCritical()) {
            i += this.rand.nextInt(i / 2 + 2);
        }

        Entity entity1 = this.getShooter();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.causeIndirectMagicDamage(this, this);
        } else {
            damagesource = DamageSource.causeIndirectMagicDamage(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity) entity1).setLastAttackedEntity(entity);
            }
        }

        int j = entity.getFireTimer();
        if (this.isBurning() && !(entity instanceof EndermanEntity)) {
            entity.setFire(5);
        }

        if (entity instanceof LivingEntity && ((LivingEntity) entity).getCreatureAttribute() == CreatureAttribute.UNDEAD) {
            if (entity.attackEntityFrom(damagesource, (float) i * 1.15F)) {

                LivingEntity livingentity = (LivingEntity) entity;
                if (!this.world.isRemote && this.getPierceLevel() <= 0) {
                    livingentity.setArrowCountInEntity(livingentity.getArrowCountInEntity() + 1);
                }

                if (!this.world.isRemote) {
                    livingentity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, this.duration / 2, 0));
                }

                Vec3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double) 1 * 0.6D);
                if (vec3d.lengthSquared() > 0.0D) {
                    livingentity.addVelocity(vec3d.x, 0.1D, vec3d.z);
                }


                if (!this.world.isRemote && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.applyThornEnchantments(livingentity, entity1);
                    EnchantmentHelper.applyArthropodEnchantments((LivingEntity) entity1, livingentity);
                }

                if (entity1 != null && livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity) entity1).connection.sendPacket(new SChangeGameStatePacket(6, 0.0F));
                }

                if (!entity.isAlive() && this.field_213875_aA != null) {
                    this.field_213875_aA.add(livingentity);
                }

                if (!this.world.isRemote && entity1 instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) entity1;
                    if (this.field_213875_aA != null && this.getShotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayerentity, this.field_213875_aA, this.field_213875_aA.size());
                    } else if (!entity.isAlive() && this.getShotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayerentity, Arrays.asList(entity), 0);
                    }
                }


                this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                if (this.getPierceLevel() <= 0 && !(entity instanceof EndermanEntity)) {
                    this.remove();
                }
            } else {
                entity.setFireTimer(j);
                this.setMotion(this.getMotion().scale(-0.1D));
                this.rotationYaw += 180.0F;
                this.prevRotationYaw += 180.0F;
                if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {
                    if (this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
                        this.entityDropItem(this.getArrowStack(), 0.1F);
                    }

                    this.remove();
                }
            }
        } else {
            if (entity instanceof LivingEntity) {
                this.arrowHit((LivingEntity) entity);
                this.remove();
            } else {
                entity.setFireTimer(j);
                this.setMotion(this.getMotion().scale(-0.1D));
                this.rotationYaw += 180.0F;
                this.prevRotationYaw += 180.0F;
                if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {
                    if (this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
                        this.entityDropItem(this.getArrowStack(), 0.1F);
                    }

                    this.remove();
                }
            }
        }
    }


    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);

        if (living instanceof TofuSlimeEntity) {
            if (!this.world.isRemote) {
                living.entityDropItem(new ItemStack(TofuItems.TOFUZUNDA, ((TofuSlimeEntity) living).getSlimeSize() * 2 + 1));
                living.remove();
            }
        }
        EffectInstance effectinstance = new EffectInstance(Effects.REGENERATION, this.duration, 0);
        living.addPotionEffect(effectinstance);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("Duration")) {
            this.duration = compound.getInt("Duration");
        }

    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Duration", this.duration);
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