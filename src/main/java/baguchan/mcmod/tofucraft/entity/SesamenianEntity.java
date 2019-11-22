package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.entity.ai.GoToBedGoal;
import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuItems;
import baguchan.mcmod.tofucraft.init.TofuSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SesamenianEntity extends AbstractSesamenian {

    public SesamenianEntity(EntityType<? extends SesamenianEntity> type, World p_i48553_2_) {
        super(type, p_i48553_2_);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0F, true));
        this.goalSelector.addGoal(4, new GoToBedGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new MoveToHomeGoal(this, 20D, 1.15D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.9D));
        this.goalSelector.addGoal(7, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.3F);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);

        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return TofuSounds.TOFUNIAN_AMBIENT;
    }


    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setEnchantmentBasedOnDifficulty(difficultyIn);
        if (this.getType() == TofuEntitys.SESAMENIAN) {
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(TofuItems.METALSWORD));

            if (worldIn.getWorld().rand.nextInt(3) == 0) {
                this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(TofuItems.ARMOR_METALHELMET));
            }
        }

        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
}
