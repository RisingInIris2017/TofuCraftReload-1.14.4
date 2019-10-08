package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuItems;
import baguchan.mcmod.tofucraft.init.TofuSounds;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class TofunianEntity extends AbstractVillagerEntity {
    private static final DataParameter<Integer> ROLE = EntityDataManager.createKey(TofunianEntity.class, DataSerializers.VARINT);
    private boolean customer;
    private int tofunianCareerLevel = 1;
    @Nullable
    private PlayerEntity field_213778_bG;

    public static int MAX_HOME_DISTANCE = 128;

    public static Predicate<Entity> ENEMY_PREDICATE =
            input -> (input instanceof MonsterEntity);


    public Int2ObjectMap<VillagerTrades.ITrade[]> getOfferMap() {
        Int2ObjectMap<VillagerTrades.ITrade[]> offers = null;
        if (getRole() == Roles.TOFUCOCK) {
            offers = func_221238_a(ImmutableMap.of(1,
                    new VillagerTrades.ITrade[]{
                            new TradeForZundaRuby(TofuItems.TOFUKINU, 20, 8, 2),
                            new TradeForZundaRuby(TofuItems.TOFUMOMEN, 26, 6, 2)
                    }, 2,
                    new VillagerTrades.ITrade[]{
                            new TradeForItem(TofuItems.TOFUCOOKIE, 9, 6, 2)
                    }));
        }

        return offers;
    }

    private int timeUntilReset;

    public TofunianEntity(EntityType<? extends TofunianEntity> p_i50182_1_, World p_i50182_2_) {
        super(p_i50182_1_, p_i50182_2_);
    }


    private static Int2ObjectMap<VillagerTrades.ITrade[]> func_221238_a(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }


    @Override
    protected void populateTradeData() {
        Int2ObjectMap<VillagerTrades.ITrade[]> int2objectmap = getOfferMap();

        if (int2objectmap != null && !int2objectmap.isEmpty()) {

            VillagerTrades.ITrade[] avillagertrades$itrade = int2objectmap.get(this.getLevel());

            if (avillagertrades$itrade != null) {
                MerchantOffers merchantoffers = this.getOffers();

                this.addTrades(merchantoffers, avillagertrades$itrade, 2);
            }

        }

    }

    protected void func_213713_b(MerchantOffer p_213713_1_) {
        int i = 3 + this.rand.nextInt(4);
        this.field_213778_bG = this.getCustomer();

        this.timeUntilReset = 40;
        this.customer = true;
        i += 5;


        if (p_213713_1_.func_222221_q()) {
            this.world.addEntity(new ExperienceOrbEntity(this.world, this.posX, this.posY + 0.5D, this.posZ, i));
        }
    }

    static class TradeForZundaRuby implements VillagerTrades.ITrade {
        private final Item item;
        private final int count;
        private final int maxUses;
        private final int givenXP;
        private final float priceMultiplier;

        public TradeForZundaRuby(IItemProvider item, int count, int maxUses, int givenXP) {
            this.item = item.asItem();
            this.count = count;
            this.maxUses = maxUses;
            this.givenXP = givenXP;
            this.priceMultiplier = 0.05F;
        }

        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            ItemStack itemstack = new ItemStack(this.item, this.count);
            return new MerchantOffer(itemstack, new ItemStack(TofuItems.ZUNDARUBY), this.maxUses, this.givenXP, this.priceMultiplier);
        }
    }

    static class TradeForItem implements VillagerTrades.ITrade {
        private final Item item;
        private final int count;
        private final int maxUses;
        private final int givenXP;
        private final float priceMultiplier;

        public TradeForItem(IItemProvider item, int count, int maxUses, int givenXP) {
            this.item = item.asItem();
            this.count = count;
            this.maxUses = maxUses;
            this.givenXP = givenXP;
            this.priceMultiplier = 0.05F;
        }

        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            ItemStack itemstack = new ItemStack(this.item, this.count);
            return new MerchantOffer(new ItemStack(TofuItems.ZUNDARUBY), itemstack, this.maxUses, this.givenXP, this.priceMultiplier);
        }
    }

    private void populateBuyingList() {
        this.setLevel(this.tofunianCareerLevel + 1);
        this.populateTradeData();
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putInt("Level", getLevel());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        this.setLevel(compound.getInt("Level"));

        updateUniqueEntityAI();
    }

    @Override
    protected void registerData() {
        super.registerData();

        this.getDataManager().register(ROLE, Integer.valueOf(0));
    }

    public void setLevel(int level) {
        this.tofunianCareerLevel = level;
    }

    public int getLevel() {
        return this.tofunianCareerLevel;
    }

    public void setRole(Roles role) {
        this.getDataManager().set(ROLE, role.ordinal());
    }

    public Roles getRole() {
        return Roles.get(this.getDataManager().get(ROLE));
    }

    private boolean canGuard() {
        return this.getRole() == Roles.GUARD;
    }

    @Override
    protected void updateAITasks() {
        if (!this.func_213716_dX() && this.timeUntilReset > 0) {
            --this.timeUntilReset;
            if (this.timeUntilReset <= 0) {
                if (this.customer) {
                    this.populateBuyingList();
                    this.customer = false;
                }

                this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 200, 0));
            }
        }
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(2, new LookAtCustomerGoal(this));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.3F);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);

        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    private void updateUniqueEntityAI() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        //i dont think this one works, change to predicate
        if (canGuard()) {
            this.targetSelector.addGoal(1, new MeleeAttackGoal(this, 1.0F, true));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, LivingEntity.class, 10, true, false, ENEMY_PREDICATE));
        } else {
            this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, AbstractIllagerEntity.class, 8.0F, 0.5D, 0.5D));
            this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, VexEntity.class, 8.0F, 0.5D, 0.5D));
            this.goalSelector.addGoal(1, new PanicGoal(this, 0.5D));
        }

    }

    @Override
    protected void onGrowingAdult() {
        super.onGrowingAdult();
        updateUniqueEntityAI();
    }

    @Override
    public void livingTick() {
        this.updateArmSwingProgress();
        super.livingTick();
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = itemstack.getItem() == Items.NAME_TAG;
        if (flag) {
            itemstack.interactWithEntity(player, this, hand);
            return true;
        } else if (itemstack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.func_213716_dX() && !this.isChild()) {
            if (hand == Hand.MAIN_HAND) {
                player.addStat(Stats.TALKED_TO_VILLAGER);
            }

            if (this.getOffers().isEmpty()) {
                return super.processInteract(player, hand);
            } else {
                if (!this.world.isRemote) {
                    this.setCustomer(player);
                    this.func_213707_a(player, this.getDisplayName(), getLevel());
                }

                return true;
            }
        } else {
            return super.processInteract(player, hand);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return TofuSounds.TOFUNIAN_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return null;
    }

    protected SoundEvent func_213721_r(boolean p_213721_1_) {
        return p_213721_1_ ? TofuSounds.TOFUNIAN_YES : null;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.85F;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        rollDiceChild();

        rollDiceRole();

        updateUniqueEntityAI();

        ILivingEntityData data = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);

        return data;
    }

    public void setWanderDistance() {
        this.setHomePosAndDistance(this.getPosition(), MAX_HOME_DISTANCE);
    }

    public void rollDiceChild() {
        int childChance = 20;
        if (childChance >= this.world.rand.nextInt(100)) {
            this.setGrowingAge(-24000);
        }
    }

    public void rollDiceRole() {
        int randRole = this.world.rand.nextInt(Roles.values().length);

        this.setRole(Roles.get(randRole));

        if (canGuard()) {
            this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5.0D);
        }
    }

    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public TofunianEntity createChild(AgeableEntity ageable) {
        TofunianEntity entityvillager = TofuEntitys.TOFUNIAN.create(world);

        entityvillager.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entityvillager)), SpawnReason.BREEDING, null, null);

        return entityvillager;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("entity.tofucraft.tofunian." +
                getRole().toString().toLowerCase(Locale.ROOT) + ".name"
        );
    }

    public enum Roles {
        GUARD,
        TOFUCOCK;

        private static final Map<Integer, Roles> lookup = new HashMap<>();

        static {
            for (Roles e : EnumSet.allOf(Roles.class)) {
                lookup.put(e.ordinal(), e);
            }
        }

        public static Roles get(int intValue) {
            return lookup.get(intValue);
        }
    }
}
