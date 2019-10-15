package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.entity.ai.GoToBedGoal;
import baguchan.mcmod.tofucraft.entity.ai.InterestJobBlockGoal;
import baguchan.mcmod.tofucraft.entity.ai.RestockTradeGoal;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuItems;
import baguchan.mcmod.tofucraft.init.TofuSounds;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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
    private int xp;
    @Nullable
    private PlayerEntity field_213778_bG;

    @Nullable
    private BlockPos tofunainHome;


    public static Predicate<Entity> ENEMY_PREDICATE =
            input -> (input instanceof ZombieEntity || input instanceof AbstractIllagerEntity || input instanceof VexEntity);
    private boolean isAISetupFinished;


    public Int2ObjectMap<VillagerTrades.ITrade[]> getOfferMap() {
        Int2ObjectMap<VillagerTrades.ITrade[]> offers = null;
        if (getRole() == Roles.TOFUCOCK) {
            offers = func_221238_a(ImmutableMap.of(1,
                    new VillagerTrades.ITrade[]{
                            new TradeForZundaRuby(TofuItems.TOFUKINU, 20, 8, 2),
                            new TradeForZundaRuby(TofuItems.TOFUMOMEN, 26, 6, 2)
                    }, 2,
                    new VillagerTrades.ITrade[]{
                            new TradeForZundaRuby(TofuBlocks.ISHITOFU_BRICK, 12, 6, 3),
                            new TradeForItem(TofuItems.TOFUCOOKIE, 9, 6, 2)
                    }));
        } else if (getRole() == Roles.TOFUSMITH) {
            offers = func_221238_a(ImmutableMap.of(1,
                    new VillagerTrades.ITrade[]{
                            new TradeForZundaRuby(TofuItems.TOFUISHI, 26, 7, 2),
                            new TradeForItem(TofuItems.ARMOR_SOLIDHELMET, 1, 3, 3)
                    }, 2,
                    new VillagerTrades.ITrade[]{
                            new TradeForZundaRuby(TofuItems.TOFUMETAL, 4, 7, 2),
                            new TradeForHicostItem(TofuItems.ARMOR_METALBOOTS, 4, 3, 3)
                    }, 3,
                    new VillagerTrades.ITrade[]{
                            new TradeForHicostItem(TofuItems.METALSWORD, 5, 3, 3),
                            new TradeForHicostItem(TofuItems.METALSHOVEL, 4, 3, 3)
                    }, 4,
                    new VillagerTrades.ITrade[]{
                            new TradeForHicostItem(TofuItems.TOFUSTICK, 4, 2, 2)
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
        this.xp += p_213713_1_.func_222210_n();
        this.field_213778_bG = this.getCustomer();

        if (this.func_213741_eu()) {
            this.timeUntilReset = 40;
            this.customer = true;
            i += 5;
        }


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

    static class TradeForHicostItem implements VillagerTrades.ITrade {
        private final Item item;
        private final int count;
        private final int maxUses;
        private final int givenXP;
        private final float priceMultiplier;

        public TradeForHicostItem(IItemProvider item, int count, int maxUses, int givenXP) {
            this.item = item.asItem();
            this.count = count;
            this.maxUses = maxUses;
            this.givenXP = givenXP;
            this.priceMultiplier = 0.05F;
        }

        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            ItemStack itemstack = new ItemStack(this.item);
            return new MerchantOffer(new ItemStack(TofuItems.ZUNDARUBY, this.count), itemstack, this.maxUses, this.givenXP, this.priceMultiplier);
        }
    }

    private void populateBuyingList() {
        this.setLevel(this.tofunianCareerLevel + 1);
        this.populateTradeData();
    }

    public void restock() {
        for (MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.func_222203_h();
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putInt("Level", getLevel());
        compound.putInt("Xp", this.xp);
        compound.putInt("Role", getRole().ordinal());

        if (this.tofunainHome != null) {
            compound.put("TofunianHome", NBTUtil.writeBlockPos(this.tofunainHome));
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        this.setLevel(compound.getInt("Level"));
        if (compound.contains("Xp", 3)) {
            this.xp = compound.getInt("Xp");
        }
        if (compound.contains("Role")) {
            this.setRole(Roles.get(compound.getInt("Role")));
        }

        if (compound.contains("TofunianHome")) {
            this.tofunainHome = NBTUtil.readBlockPos(compound.getCompound("TofunianHome"));
        }

        updateUniqueEntityAI();
    }

    public void setTofunainHome(@Nullable BlockPos pos) {
        this.tofunainHome = pos;
    }

    @Nullable
    public BlockPos getTofunainHome() {
        return this.tofunainHome;
    }

    private boolean func_213741_eu() {
        int i = this.getLevel();
        return VillagerData.func_221128_d(i) && this.xp >= VillagerData.func_221127_c(i);
    }

    public int getXp() {
        return this.xp;
    }

    public void setXp(int p_213761_1_) {
        this.xp = p_213761_1_;
    }

    @Override
    protected void registerData() {
        super.registerData();

        this.getDataManager().register(ROLE, Roles.TOFUNIAN.ordinal());
    }

    public void setLevel(int level) {
        this.tofunianCareerLevel = level;
    }

    public int getLevel() {
        return this.tofunianCareerLevel;
    }

    public void setRole(Roles role) {
        this.getDataManager().set(ROLE, role.ordinal());
        if (canGuard()) {
            this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5.0D);
        }
    }

    public Roles getRole() {
        return Roles.get(this.getDataManager().get(ROLE));
    }

    public boolean canGuard() {
        return this.getRole() == Roles.GUARD;
    }

    public boolean canFarm() {
        return this.getRole() == Roles.TOFUCOCK;
    }

    public boolean canSmith() {
        return this.getRole() == Roles.TOFUSMITH;
    }

    public boolean isNitwit() {
        return this.getRole() == Roles.TOFUNIAN;
    }

    protected void func_213750_eg() {
        super.func_213750_eg();
        for (MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.func_222220_k();
        }
    }


    public boolean isStockOut() {
        for (MerchantOffer merchantoffer : this.getOffers()) {
            if (merchantoffer.func_222217_o()) {
                return true;
            }
        }

        return false;
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

        if (this.isNitwit() && this.func_213716_dX()) {
            this.func_213750_eg();
        }

        super.updateAITasks();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(2, new LookAtCustomerGoal(this));
        this.goalSelector.addGoal(4, new GoToBedGoal(this, 1.15D));
        this.goalSelector.addGoal(5, new MoveToHomeGoal(this, 120D, 1.15D));
        this.goalSelector.addGoal(6, new InterestJobBlockGoal(this, 1.15D));
        this.goalSelector.addGoal(6, new RestockTradeGoal(this, 1.15D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, ZombieEntity.class, 8.0F, 1.2D, 1.25D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, AbstractIllagerEntity.class, 8.0F, 1.25D, 1.25D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, VexEntity.class, 8.0F, 1.2D, 1.2D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.3F);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);

        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    private void updateUniqueEntityAI() {
        if (this.isAISetupFinished) {
            if (canGuard()) {
                this.targetSelector.addGoal(1, new MeleeAttackGoal(this, 1.0F, true));
                this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, MonsterEntity.class, 10, true, false, ENEMY_PREDICATE));
                this.isAISetupFinished = false;
            }
        }
    }

    private void updateEntityEquipment() {
        if (canGuard()) {
            this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(TofuItems.ARMOR_METALHELMET));
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(TofuItems.METALSWORD));
        }
    }

    public void updateTofunianState() {
        updateUniqueEntityAI();
        updateEntityEquipment();
    }

    @Override
    protected void onGrowingAdult() {
        super.onGrowingAdult();

    }


    @Override
    public void livingTick() {
        this.updateArmSwingProgress();
        super.livingTick();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        float f = (float) this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
        int i = 0;

        if (entityIn instanceof LivingEntity) {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((LivingEntity) entityIn).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag) {
            if (i > 0 && entityIn instanceof LivingEntity) {
                ((LivingEntity) entityIn).knockBack(this, (float) i * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.setMotion(this.getMotion().x * 0.6D, this.getMotion().y, this.getMotion().z * 0.6D);
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0) {
                entityIn.setFire(j * 4);
            }

            if (entityIn instanceof PlayerEntity) {
                PlayerEntity entityplayer = (PlayerEntity) entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem() instanceof AxeItem && itemstack1.getItem() == Items.SHIELD) {
                    float f1 = 0.25F + (float) EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1) {
                        entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
                        this.world.setEntityState(entityplayer, (byte) 30);
                    }
                }
            }

            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = itemstack.getItem() == Items.NAME_TAG;
        if (flag) {
            itemstack.interactWithEntity(player, this, hand);
            return true;
        } else if (itemstack.getItem() != TofuItems.TOFUNIAN_SPAWNEGG && this.isAlive() && !this.func_213716_dX() && !this.isChild()) {
            if (hand == Hand.MAIN_HAND) {
                player.addStat(Stats.TALKED_TO_VILLAGER);
            }

            if (this.getOffers().isEmpty()) {
                if (!this.isNitwit()) {
                    //remaking trade
                    this.populateTradeData();

                    if (!this.world.isRemote) {
                        this.setCustomer(player);
                        this.func_213707_a(player, this.getDisplayName(), getLevel());
                    }
                    return true;
                }
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

    public void setCustomer(@Nullable PlayerEntity player) {
        boolean flag = this.getCustomer() != null && player == null;
        super.setCustomer(player);
        if (flag) {
            this.func_213750_eg();
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
        updateTofunianState();

        ILivingEntityData data = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);

        return data;
    }

    public void rollDiceChild() {
        int childChance = 20;
        if (childChance >= this.world.rand.nextInt(100)) {
            this.setGrowingAge(-24000);
        }
    }

    public void rollDiceRole() {
        int randRole = this.world.rand.nextInt(Roles.values().length - 1);

        this.setRole(Roles.get(randRole));

        updateTofunianState();
    }

    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public TofunianEntity createChild(AgeableEntity ageable) {
        TofunianEntity entityvillager = TofuEntitys.TOFUNIAN.create(world);

        entityvillager.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entityvillager)), SpawnReason.BREEDING, null, null);

        updateTofunianState();

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
        TOFUCOCK,
        TOFUSMITH,
        TOFUNIAN;

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

    public class MoveToHomeGoal extends Goal {
        public final TofunianEntity tofunian;
        public final double distance;
        public final double speed;

        public MoveToHomeGoal(TofunianEntity houseTofunianEntity, double distance, double speed) {
            this.tofunian = houseTofunianEntity;
            this.distance = distance;
            this.speed = speed;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            TofunianEntity.this.navigator.clearPath();
        }


        public boolean shouldExecute() {
            BlockPos blockpos = this.tofunian.getTofunainHome();
            return blockpos != null && this.func_220846_a(blockpos, this.distance);
        }

        @Override
        public boolean shouldContinueExecuting() {
            BlockPos blockpos = this.tofunian.getTofunainHome();
            return blockpos != null && this.func_220846_a(blockpos, this.distance * 0.85F);
        }


        public void tick() {
            BlockPos blockpos = this.tofunian.getTofunainHome();
            if (blockpos != null && TofunianEntity.this.navigator.noPath()) {
                if (this.func_220846_a(blockpos, 6.0D)) {
                    Vec3d vec3d = (new Vec3d((double) blockpos.getX() - this.tofunian.posX, (double) blockpos.getY() - this.tofunian.posY, (double) blockpos.getZ() - this.tofunian.posZ)).normalize();
                    Vec3d vec3d1 = vec3d.scale(10.0D).add(this.tofunian.posX, this.tofunian.posY, this.tofunian.posZ);
                    TofunianEntity.this.navigator.tryMoveToXYZ(vec3d1.x, vec3d1.y, vec3d1.z, this.speed);
                } else {
                    TofunianEntity.this.navigator.tryMoveToXYZ((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), this.speed);
                }
            }

        }

        private boolean func_220846_a(BlockPos p_220846_1_, double p_220846_2_) {
            return !p_220846_1_.withinDistance(this.tofunian.getPositionVec(), p_220846_2_);
        }
    }
}
