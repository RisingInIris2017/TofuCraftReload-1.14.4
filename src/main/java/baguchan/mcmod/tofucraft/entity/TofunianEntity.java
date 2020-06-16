package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.entity.ai.*;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuItems;
import baguchan.mcmod.tofucraft.init.TofuSounds;
import baguchan.mcmod.tofucraft.utils.WorldUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.IReputationTracking;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.village.GossipManager;
import net.minecraft.village.GossipType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TofunianEntity extends AbstractVillagerEntity implements IReputationTracking {
    private static final DataParameter<String> ROLE = EntityDataManager.createKey(TofunianEntity.class, DataSerializers.STRING);
    private static final Set<Item> SEED = ImmutableSet.of(TofuItems.SEEDS_SOYBEAN);
    private static final Set<Item> FOOD = ImmutableSet.of(TofuItems.TOFUGRILD, TofuItems.TOFUCOOKIE, TofuItems.TOFUMOMEN, TofuItems.HIYAYAKKO);
    private static final Map<Item, Integer> field_213788_bA = ImmutableMap.of(TofuItems.HIYAYAKKO, 2, TofuItems.TOFUGRILD, 2, TofuItems.TOFUCOOKIE, 3, TofuItems.TOFUMOMEN, 1);
    private static final Map<Item, Integer> canCookItems = ImmutableMap.of(TofuItems.SEEDS_SOYBEAN, 2, TofuItems.TOFUMOMEN, 1);
    public static final List<Item> cookedItem = Lists.newArrayList(TofuItems.TOFUGRILD, TofuItems.HIYAYAKKO);


    private int inLove;

    private boolean customer;
    private int tofunianCareerLevel = 1;
    private int xp;
    @Nullable
    private PlayerEntity previousCustomer;

    @Nullable
    private BlockPos tofunainHome;
    private final GossipManager gossip = new GossipManager();

    public static int MAX_HOME_DISTANCE_SQ = 60 * 60;
    private int homeDimID = 0;


    public static Predicate<Entity> ENEMY_PREDICATE =
            input -> (input instanceof ZombieEntity || input instanceof AbstractIllagerEntity || input instanceof VexEntity || input instanceof TofuChingerEntity);

    public Int2ObjectMap<VillagerTrades.ITrade[]> getOfferMap() {
        Int2ObjectMap<VillagerTrades.ITrade[]> offers = null;
        if (getRole() == Roles.TOFUCOCK) {
            offers = func_221238_a(ImmutableMap.of(1,
                    new VillagerTrades.ITrade[]{
                            new TradeForZundaRuby(TofuItems.TOFUKINU, 20, 8, 2),
                            new TradeForZundaRuby(TofuItems.TOFUMOMEN, 30, 6, 2)
                    }, 2,
                    new VillagerTrades.ITrade[]{
                            new TradeForItem(TofuBlocks.ISHITOFU_BRICK, 12, 4, 12),
                            new TradeForItem(TofuItems.TOFUCOOKIE, 9, 6, 10),
                    }, 3,
                    new VillagerTrades.ITrade[]{
                            new TradeForZundaRuby(TofuItems.EDAMAME, 26, 6, 14),
                            new TradeForItem(TofuItems.TTTBURGER, 6, 6, 16)
                    }, 4,
                    new VillagerTrades.ITrade[]{
                            new TradeForZundaRuby(TofuItems.SOYSAUCE, 1, 4, 20),
                            new TradeForItem(TofuItems.TOFUZUNDA, 7, 6, 22)
                    }, 5,
                    new VillagerTrades.ITrade[]{
                            new TradeForHicostItem(TofuItems.TOFUHOE, 4, 4, 30)
                    }));
        } else if (getRole() == Roles.TOFUSMITH) {
            offers = func_221238_a(ImmutableMap.of(1,
                    new VillagerTrades.ITrade[]{
                            new TradeForZundaRuby(TofuItems.TOFUISHI, 26, 7, 2),
                            new TradeForItem(TofuItems.ARMOR_SOLIDHELMET, 1, 3, 3)
                    }, 2,
                    new VillagerTrades.ITrade[]{
                            new TradeForZundaRuby(TofuItems.TOFUMETAL, 4, 7, 8),
                            new TradeForHicostItem(TofuItems.ARMOR_METALBOOTS, 4, 3, 14)
                    }, 3,
                    new VillagerTrades.ITrade[]{
                            new TradeForHicostItem(TofuItems.METALSWORD, 5, 3, 16),
                            new TradeForHicostItem(TofuItems.METALSHOVEL, 4, 3, 16),
                            new TradeForHicostItem(TofuItems.METALPICKAXE, 6, 3, 16)
                    }, 4,
                    new VillagerTrades.ITrade[]{
                            new TradeForHicostItem(TofuItems.TOFUSTICK, 4, 2, 30)
                    }, 5,
                    new VillagerTrades.ITrade[]{
                            new TradeForHicostItem(TofuItems.TOFUDIAMONDAXE, 18, 1, 30),
                            new TradeForHicostItem(TofuItems.TOFUDIAMONDPICKAXE, 17, 1, 30),
                            new TradeForHicostItem(TofuItems.TOFUDIAMONDSHOVEL, 15, 1, 30),
                            new TradeForHicostItem(TofuItems.ARMOR_DIAMONDBOOTS, 15, 1, 30)
                    }));
        } else if (getRole() == Roles.SOYWORKER) {
            offers = func_221238_a(ImmutableMap.of(1,
                    new VillagerTrades.ITrade[]{
                            new TradeForZundaRuby(Items.GLASS_BOTTLE, 6, 8, 2),
                            new TradeForItem(TofuItems.SOYMILK_BOTTLE, 1, 3, 4)
                    }, 2,
                    new VillagerTrades.ITrade[]{
                            new TradeForZundaRuby(TofuItems.SEEDS_SOYBEAN, 28, 6, 8),
                            new TradeForHicostItem(TofuItems.SOYMILK_COCOA_BOTTLE, 2, 3, 14),
                            new TradeForHicostItem(TofuItems.SOYMILK_KINAKO_BOTTLE, 2, 3, 14)
                    }, 3,
                    new VillagerTrades.ITrade[]{
                            new TradeForHicostItem(TofuItems.SOYMILK_RAMUNE_BOTTLE, 2, 3, 16),
                            new TradeForHicostItem(TofuItems.SOYMILK_STRAWBERRY_BOTTLE, 2, 3, 16),
                            new TradeForHicostItem(TofuItems.SOYMILK_ZUNDA_BOTTLE, 2, 3, 16)
                    }, 4,
                    new VillagerTrades.ITrade[]{
                            new TradeForZundaRuby(TofuBlocks.TOFUBERRY, 12, 8, 26),
                            new TradeForHicostItem(TofuItems.SOYMILK_PUDDING_BOTTLE, 4, 2, 28)
                    }, 5,
                    new VillagerTrades.ITrade[]{
                            new TradeForHicostItem(TofuItems.BREWED_SOYMILK_ANNIN_BOTTLE, 4, 3, 30),
                            new TradeForHicostItem(TofuItems.BREWED_SOYMILK_APPLE_BOTTLE, 4, 3, 30)
                    }));
        }

        return offers;
    }

    private int timeUntilReset;

    public TofunianEntity(EntityType<? extends TofunianEntity> p_i50182_1_, World p_i50182_2_) {
        super(p_i50182_1_, p_i50182_2_);
        this.setCanPickUpLoot(true);
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

    @Override
    protected void onVillagerTrade(MerchantOffer offer) {
        int i = 3 + this.rand.nextInt(4);
        this.xp += offer.getGivenExp();
        this.previousCustomer = this.getCustomer();

        if (this.func_213741_eu()) {
            this.timeUntilReset = 40;
            this.customer = true;
            i += 5;
        }

        if (offer.getDoesRewardExp()) {
            this.world.addEntity(new ExperienceOrbEntity(this.world, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), i));
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
            merchantoffer.resetUses();
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putInt("InLove", this.inLove);

        compound.putInt("Level", getLevel());
        compound.putInt("Xp", this.xp);
        compound.putString("Role", getRole().name());

        compound.put("Gossips", this.gossip.serialize(NBTDynamicOps.INSTANCE).getValue());

        if (this.tofunainHome != null) {
            compound.put("TofunianHome", NBTUtil.writeBlockPos(this.tofunainHome));
        }

        compound.putInt("HomeDimId", homeDimID);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        this.inLove = compound.getInt("InLove");

        this.setLevel(compound.getInt("Level"));
        if (compound.contains("Xp", 3)) {
            this.xp = compound.getInt("Xp");
        }
        if (compound.contains("Role")) {
            this.setRole(Roles.get(compound.getString("Role")));
        }

        ListNBT listnbt = compound.getList("Gossips", 10);
        this.gossip.deserialize(new Dynamic<>(NBTDynamicOps.INSTANCE, listnbt));

        if (compound.contains("TofunianHome")) {
            this.tofunainHome = NBTUtil.readBlockPos(compound.getCompound("TofunianHome"));
        }

        if (compound.contains("HomeDimId")) {
            this.homeDimID = compound.getInt("HomeDimId");
        }

        updateUniqueEntityAI();
        this.setCanPickUpLoot(true);
    }

    public void setTofunainHome(@Nullable BlockPos pos) {
        this.tofunainHome = pos;
        this.homeDimID = this.dimension.getId();
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

        this.getDataManager().register(ROLE, Roles.TOFUNIAN.name());
    }

    public void setLevel(int level) {
        this.tofunianCareerLevel = level;
    }

    public int getLevel() {
        return this.tofunianCareerLevel;
    }

    public void setRole(Roles role) {
        this.getDataManager().set(ROLE, role.name());
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

    protected void resetCustomer() {
        super.resetCustomer();
        for (MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetSpecialPrice();
        }
    }


    //when uses no left
    public boolean isStockOut() {
        for (MerchantOffer merchantoffer : this.getOffers()) {
            if (merchantoffer.hasNoUsesLeft()) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void updateAITasks() {
        if (!this.hasCustomer() && this.timeUntilReset > 0) {
            --this.timeUntilReset;
            if (this.timeUntilReset <= 0) {
                if (this.customer) {
                    this.populateBuyingList();
                    this.customer = false;
                }

                this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 200, 0));
            }
        }

        if (this.isNitwit() && this.hasCustomer()) {
            this.resetCustomer();
        }

        if (this.previousCustomer != null && this.world instanceof ServerWorld) {
            ((ServerWorld) this.world).updateReputation(IReputationType.TRADE, this.previousCustomer, this);
            this.world.setEntityState(this, (byte) 14);
            this.previousCustomer = null;
        }


        if (this.getGrowingAge() != 0) {
            this.inLove = 0;
        }

        findHome(false);

        super.updateAITasks();
    }

    private void findHome(boolean force) {
        if (!force && (world.getGameTime() + this.getEntityId()) % (20 * 30) != 0) return;

        boolean tryFind = false;

        if (tofunainHome == null) {

            tryFind = true;
        } else if (tofunainHome != null) {

            BlockState state = world.getBlockState(tofunainHome);

            if (this.homeDimID != this.dimension.getId()) {
                tofunainHome = null;

                tryFind = true;
            } else {
                if (this.getDistanceSq(tofunainHome.getX(), tofunainHome.getY(), tofunainHome.getZ()) > MAX_HOME_DISTANCE_SQ) {
                    tofunainHome = null;

                    tryFind = true;
                } else if (!state.getBlock().isIn(BlockTags.BEDS)) {
                    tofunainHome = null;

                    tryFind = true;
                }
            }
        }

        if (tryFind) {
            int range = 15;

            for (int x = -range; x <= range; x++) {
                for (int y = -range / 2; y <= range / 2; y++) {
                    for (int z = -range; z <= range; z++) {
                        BlockPos pos = this.getPosition().add(x, y, z);
                        BlockState state = world.getBlockState(pos);

                        if (state.getBlock().isIn(BlockTags.BEDS)) {
                            setTofunainHome(pos);
                            return;
                        }
                    }
                }
            }
        }
    }

    protected void registerGoals() {

        //Sleep AI is still proglem because tofuworld's time line is breaking
        this.goalSelector.addGoal(0, new WakeUpGoal(this));
        this.goalSelector.addGoal(0, new DoSleepingGoal());
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D) {
            @Override
            public boolean shouldExecute() {
                return !canGuard() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0F, true) {
            @Override
            public boolean shouldExecute() {
                return canGuard() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(2, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(2, new LookAtCustomerGoal(this));
        this.goalSelector.addGoal(2, new EatOffhandFoodGoal<>(this));
        this.goalSelector.addGoal(3, new SleepOnBedGoal(this, 1.1D));
        this.goalSelector.addGoal(4, new TofunianLoveGoal(this, 0.85D));
        this.goalSelector.addGoal(5, new MoveToHomeGoal(this, 40D, 1.10D));
        this.goalSelector.addGoal(6, new InterestJobBlockGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new RestockTradeGoal(this, 1.05D));
        this.goalSelector.addGoal(7, new CookingTofuGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new CropHarvestGoal(this, 0.95D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 0.9D));
        this.goalSelector.addGoal(9, new LookAndPassGoal(this, LivingEntity.class, 4.0F));
        this.goalSelector.addGoal(10, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.goalSelector.addGoal(1, new AvoidEntityGoal(this, ZombieEntity.class, 8.0F, 1.2D, 1.25D) {
            @Override
            public boolean shouldExecute() {
                return !canGuard() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(1, new AvoidEntityGoal(this, AbstractIllagerEntity.class, 8.0F, 1.25D, 1.25D) {
            @Override
            public boolean shouldExecute() {
                return !canGuard() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(1, new AvoidEntityGoal(this, VexEntity.class, 8.0F, 1.2D, 1.2D) {
            @Override
            public boolean shouldExecute() {
                return !canGuard() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(1, new AvoidEntityGoal(this, TofuChingerEntity.class, 8.0F, 1.2D, 1.2D) {
            @Override
            public boolean shouldExecute() {
                return !canGuard() && super.shouldExecute();
            }
        });
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, MonsterEntity.class, 10, true, false, ENEMY_PREDICATE) {
            @Override
            public boolean shouldExecute() {
                return canGuard() && super.shouldExecute();
            }
        });

    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.3F);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);

        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    private void updateUniqueEntityAI() {
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
        updateTofunianState();
    }

    @Override
    public void livingTick() {
        this.updateArmSwingProgress();
        super.livingTick();

        if (this.inLove > 0) {
            --this.inLove;
            if (this.inLove % 10 == 0) {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                this.world.addParticle(ParticleTypes.HEART, this.getPosX() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), this.getPosY() + 0.5D + (double) (this.rand.nextFloat() * this.getHeight()), this.getPosZ() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), d0, d1, d2);
            }
        }
    }

    public boolean isInLove() {
        return this.inLove > 0;
    }

    public void tick() {
        super.tick();
        if (this.getShakeHeadTicks() > 0) {
            this.setShakeHeadTicks(this.getShakeHeadTicks() - 1);
        }
    }

    private void shakeHead() {
        this.setShakeHeadTicks(40);
        if (!this.world.isRemote()) {
            this.playSound(TofuSounds.TOFUNIAN_NO, this.getSoundVolume(), this.getSoundPitch());
        }

    }

    public void setInLove(int inLove) {
        this.inLove = inLove;
    }

    @Override
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();
        Item item = itemstack.getItem();
        if (this.isTofuniansFood(item) || this.isSeed(item)) {
            Inventory inventory = this.getVillagerInventory();
            boolean flag = false;

            for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                ItemStack itemstack1 = inventory.getStackInSlot(i);
                if (itemstack1.isEmpty() || itemstack1.getItem() == item && itemstack1.getCount() < itemstack1.getMaxStackSize()) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                return;
            }

            int j = inventory.count(item);
            if (j == 256) {
                return;
            }

            if (j > 256) {
                inventory.func_223374_a(item, j - 256);
                return;
            }

            this.onItemPickup(itemEntity, itemstack.getCount());
            ItemStack itemstack2 = inventory.addItem(itemstack);
            if (itemstack2.isEmpty()) {
                itemEntity.remove();
            } else {
                itemstack.setCount(itemstack2.getCount());
            }
        }
    }

    public boolean isSeed(Item p_223717_1_) {
        return SEED.contains(p_223717_1_);
    }

    public boolean isTofuniansFood(Item p_223717_1_) {
        return FOOD.contains(p_223717_1_);
    }

    //when tofunian have enough foods
    public boolean canAbondonItems() {
        return this.func_213751_ew() >= 42;
    }

    public boolean wantsMoreFood() {
        return this.func_213751_ew() < 42;
    }

    private int func_213751_ew() {
        Inventory inventory = this.getVillagerInventory();
        return field_213788_bA.entrySet().stream().mapToInt((p_213764_1_) -> {
            return inventory.count(p_213764_1_.getKey()) * p_213764_1_.getValue();
        }).sum();
    }

    public boolean canCookItem() {
        return this.canCookItemCount() > 40 && this.func_213751_ew() < 128;
    }

    private int canCookItemCount() {
        Inventory inventory = this.getVillagerInventory();
        return canCookItems.entrySet().stream().mapToInt((p_213764_1_) -> {
            return inventory.count(p_213764_1_.getKey()) * p_213764_1_.getValue();
        }).sum();
    }

    public boolean canKeepCookItem() {
        return this.canCookItemCount() > 30 && this.func_213751_ew() < 128;
    }

    //When tofunian make child,consume foods
    public void consumeFoods() {
        if (this.canAbondonItems() && this.func_213751_ew() != 0) {
            for (int i = 0; i < this.getVillagerInventory().getSizeInventory(); ++i) {
                ItemStack itemstack = this.getVillagerInventory().getStackInSlot(i);
                if (!itemstack.isEmpty()) {
                    Integer integer = field_213788_bA.get(itemstack.getItem());
                    if (integer != null) {
                        int j = itemstack.getCount();

                        for (int k = j; k > 0; --k) {
                            this.getVillagerInventory().decrStackSize(i, 1);
                            if (!this.canAbondonItems()) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public ItemStack findFoods() {

        for (int i = 0; i < this.getVillagerInventory().getSizeInventory(); ++i) {
            ItemStack itemstack = this.getVillagerInventory().getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                if (isTofuniansFood(itemstack.getItem())) {
                    return itemstack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public void cookingFood() {
        if (this.canCookItemCount() != 0) {
            for (int i = 0; i < this.getVillagerInventory().getSizeInventory(); ++i) {
                ItemStack itemstack = this.getVillagerInventory().getStackInSlot(i);
                if (!itemstack.isEmpty()) {
                    Integer integer = canCookItems.get(itemstack.getItem());
                    if (integer != null) {
                        int j = itemstack.getCount();

                        this.getVillagerInventory().decrStackSize(i, 1);
                        this.cookResult();

                    }
                }
            }
        }
    }

    private void cookResult() {
        this.getVillagerInventory().addItem(new ItemStack(cookedItem.get(this.getRNG().nextInt(cookedItem.size()))));
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 18) {
            for (int i = 0; i < 7; ++i) {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                this.world.addParticle(ParticleTypes.HEART, this.getPosX() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), this.getPosY() + 0.5D + (double) (this.rand.nextFloat() * this.getHeight()), this.getPosZ() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), d0, d1, d2);
            }
        } else if (id == 13) {
            for (int i = 0; i < 7; ++i) {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                this.world.addParticle(ParticleTypes.ANGRY_VILLAGER, this.getPosX() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), this.getPosY() + 0.5D + (double) (this.rand.nextFloat() * this.getHeight()), this.getPosZ() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), d0, d1, d2);
            }
        } else {
            super.handleStatusUpdate(id);
        }

    }


    public boolean canMateWith(TofunianEntity otherTofunian) {
        if (otherTofunian == this) {
            return false;
        } else if (otherTofunian.getClass() != this.getClass()) {
            return false;
        } else {
            return this.canAbondonItems() && otherTofunian.canAbondonItems();
        }
    }

    public void setRevengeTarget(@Nullable LivingEntity livingBase) {
        if (livingBase != null && this.world instanceof ServerWorld) {
            ((ServerWorld) this.world).updateReputation(IReputationType.VILLAGER_HURT, livingBase, this);
            if (this.isAlive() && livingBase instanceof PlayerEntity) {
                this.world.setEntityState(this, (byte) 13);
            }
        }

        super.setRevengeTarget(livingBase);
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

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.inLove = 0;
            this.world.setEntityState(this, (byte) 13);
            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = itemstack.getItem() == Items.NAME_TAG;
        if (flag) {
            itemstack.interactWithEntity(player, this, hand);
            return true;
        } else if (itemstack.getItem() != TofuItems.TOFUNIAN_SPAWNEGG && this.isAlive() && !this.hasCustomer() && !this.isChild() && !this.isSleeping() && !player.func_226563_dT_()) {

            if (this.getOffers().isEmpty()) {
                if (!this.isNitwit() && !this.canGuard()) {
                    //remaking trade
                    this.populateTradeData();

                    if (!this.world.isRemote) {
                        this.setCustomer(player);
                        this.displayMerchantGui(player);
                    }
                    return true;
                } else {
                    if (!this.world.isRemote) {
                        if (hand == Hand.MAIN_HAND) {
                            this.shakeHead();
                        }
                    }
                    return super.processInteract(player, hand);
                }
            } else {
                if (!this.world.isRemote) {
                    this.setCustomer(player);
                    this.displayMerchantGui(player);
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
            this.resetCustomer();
        }
    }


    private void displayMerchantGui(PlayerEntity player) {
        this.recalculateSpecialPricesFor(player);
        this.setCustomer(player);
        this.openMerchantContainer(player, this.getDisplayName(), this.getLevel());
    }

    private void recalculateSpecialPricesFor(PlayerEntity playerIn) {
        int i = this.getPlayerReputation(playerIn);
        if (i != 0) {
            for (MerchantOffer merchantoffer : this.getOffers()) {
                merchantoffer.increaseSpecialPrice(-MathHelper.floor((float) i * merchantoffer.getPriceMultiplier()));
            }
        }

        if (playerIn.isPotionActive(Effects.HERO_OF_THE_VILLAGE)) {
            EffectInstance effectinstance = playerIn.getActivePotionEffect(Effects.HERO_OF_THE_VILLAGE);
            int k = effectinstance.getAmplifier();

            for (MerchantOffer merchantoffer1 : this.getOffers()) {
                double d0 = 0.3D + 0.0625D * (double) k;
                int j = (int) Math.floor(d0 * (double) merchantoffer1.getBuyingStackFirst().getCount());
                merchantoffer1.increaseSpecialPrice(-Math.max(j, 1));
            }
        }

    }

    public int getPlayerReputation(PlayerEntity player) {
        return this.gossip.func_220921_a(player.getUniqueID(), (p_223103_0_) -> {
            return true;
        });
    }

    public void updateReputation(IReputationType type, Entity target) {
        if (type == IReputationType.TRADE) {
            this.gossip.func_220916_a(target.getUniqueID(), GossipType.TRADING, 2);
        } else if (type == IReputationType.VILLAGER_HURT) {
            this.gossip.func_220916_a(target.getUniqueID(), GossipType.MINOR_NEGATIVE, 25);
        } else if (type == IReputationType.VILLAGER_KILLED) {
            this.gossip.func_220916_a(target.getUniqueID(), GossipType.MAJOR_NEGATIVE, 25);
        }

    }

    public GossipManager getGossip() {
        return gossip;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return TofuSounds.TOFUNIAN_AMBIENT;
    }

    @Override
    protected SoundEvent getVillagerYesNoSound(boolean getYesSound) {
        return getYesSound ? TofuSounds.TOFUNIAN_YES : TofuSounds.TOFUNIAN_NO;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.85F;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {

        if (reason == SpawnReason.BREEDING) {
            int chance = 10;
            if (chance >= this.world.rand.nextInt(100)) {
                setRole(Roles.GUARD);
            }
        } else {
            rollDiceChild();

            int chance = 10;
            if (chance >= this.world.rand.nextInt(100)) {
                setRole(Roles.GUARD);
            }
        }

        updateTofunianState();
        findHome(true);

        ILivingEntityData data = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);

        return data;
    }

    public void rollDiceChild() {
        int childChance = 20;
        if (childChance >= this.world.rand.nextInt(100)) {
            this.setGrowingAge(-24000);
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

    public enum Roles implements net.minecraftforge.common.IExtensibleEnum {
        GUARD,
        TOFUCOCK,
        TOFUSMITH,
        SOYWORKER,
        TOFUNIAN;

        private static final Map<String, Roles> lookup = Arrays.stream(values()).collect(Collectors.toMap(Roles::name, (p_220362_0_) -> {
            return p_220362_0_;
        }));


        private Roles() {
        }

        public static Roles create(String name) {
            throw new IllegalStateException("Enum not extended");
        }

        public static Roles get(String stringValue) {
            return lookup.get(stringValue);
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
            if (!WorldUtils.isDaytime(this.tofunian.world) && this.tofunian.world.getDimension().isSurfaceWorld()) {
                return blockpos != null && this.func_220846_a(blockpos, this.distance);
            } else {
                return blockpos != null && this.func_220846_a(blockpos, this.distance);
            }
        }

        @Override
        public boolean shouldContinueExecuting() {
            BlockPos blockpos = this.tofunian.getTofunainHome();
            return blockpos != null && this.moveHome();
        }

        private boolean moveHome() {
            BlockPos blockpos = this.tofunian.getTofunainHome();

            if (!WorldUtils.isDaytime(this.tofunian.world) && this.tofunian.world.getDimension().isSurfaceWorld()) {
                return this.func_220846_a(blockpos, this.distance * 0.2F);
            } else {
                return this.func_220846_a(blockpos, this.distance * 0.75F);
            }
        }


        public void tick() {
            BlockPos blockpos = this.tofunian.getTofunainHome();
            if (blockpos != null && TofunianEntity.this.navigator.noPath()) {
                if (this.func_220846_a(blockpos, 6.0D)) {
                    Vec3d vec3d = (new Vec3d((double) blockpos.getX() - this.tofunian.getPosX(), (double) blockpos.getY() - this.tofunian.getPosY(), (double) blockpos.getZ() - this.tofunian.getPosZ())).normalize();
                    Vec3d vec3d1 = vec3d.scale(10.0D).add(this.tofunian.getPosX(), this.tofunian.getPosY(), this.tofunian.getPosZ());
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

    class DoSleepingGoal extends Goal {
        public DoSleepingGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return TofunianEntity.this.isSleeping();
        }
    }
}
