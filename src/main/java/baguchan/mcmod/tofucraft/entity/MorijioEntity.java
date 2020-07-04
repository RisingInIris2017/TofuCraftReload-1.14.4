package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class MorijioEntity extends Entity {

    public MorijioEntity(EntityType<? extends MorijioEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public MorijioEntity(World worldIn, double posX, double posY, double posZ) {
        this(TofuEntitys.MORIJIO, worldIn);
        this.setPosition(posX, posY, posZ);
    }

    public MorijioEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(TofuEntitys.MORIJIO, world);
    }


    @Override
    protected void registerData() {

    }

    @Override
    public void tick() {
        this.setMotion(this.getMotion().mul((double) 0.99F, (double) 0.98F, (double) 0.99F));
        if (!this.onGround) {
            this.setMotion(this.getMotion().add(0.0F, -0.01F, 0.0F));
        }

        this.move(MoverType.SELF, this.getMotion());

        super.tick();
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!source.isExplosion()) {
            if (!this.world.isRemote) {
                this.dropItem(source);
                this.remove();
            }

            return true;
        } else {
            return super.attackEntityFrom(source, amount);
        }
    }

    private void dropItem(DamageSource p_213815_1_) {
        Block.spawnAsEntity(this.world, new BlockPos(this.getPositionVec()), new ItemStack(TofuItems.MORIJIO));
        this.playSound(SoundEvents.ENTITY_ARMOR_STAND_BREAK, 1.0F, 1.0F);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

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
