package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.init.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class TofuCowEntity extends CowEntity {
    private static final DataParameter<String> TOFUCOW_TYPE = EntityDataManager.createKey(TofuCowEntity.class, DataSerializers.STRING);

    private static final Ingredient BREEDING_ITEMS = Ingredient.fromItems(Items.WHEAT, TofuItems.LEEK);

    public TofuCowEntity(EntityType<? extends TofuCowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, BREEDING_ITEMS, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.2F);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(TOFUCOW_TYPE, TofuCowEntity.Type.NONE.name);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("Type", this.getTofuCowType().name);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setTofuCowType(TofuCowEntity.Type.getTypeByName(compound.getString("Type")));
    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        IFluidHandlerItem handler = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(itemstack, 1)).orElse(null);
        if (handler instanceof FluidBucketWrapper && !player.isCreative() && !this.isChild()) {
            FluidBucketWrapper fluidBucketWrapper = ((FluidBucketWrapper) handler);

            if (this.getTofuCowType() == Type.ZUNDA) {
                FluidStack fluidStack = new FluidStack(TofuFluids.SOYMILK, 1000);

                player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                itemstack.shrink(1);

                fluidBucketWrapper.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                if (itemstack.isEmpty()) {

                    player.setHeldItem(hand, fluidBucketWrapper.getContainer());
                } else if (!player.inventory.addItemStackToInventory(fluidBucketWrapper.getContainer())) {
                    player.dropItem(fluidBucketWrapper.getContainer(), false);
                }
                return true;
            } else {
                FluidStack fluidStack = new FluidStack(TofuFluids.SOYMILK, 1000);

                player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                itemstack.shrink(1);

                fluidBucketWrapper.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                if (itemstack.isEmpty()) {
                    player.setHeldItem(hand, fluidBucketWrapper.getContainer());
                } else if (!player.inventory.addItemStackToInventory(fluidBucketWrapper.getContainer())) {
                    player.dropItem(fluidBucketWrapper.getContainer(), false);
                }
                return true;
            }

        } else if (itemstack.getItem() == Items.GLASS_BOTTLE && !player.abilities.isCreativeMode && !this.isChild()) {
            player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            itemstack.shrink(1);
            if (itemstack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(TofuItems.SOYMILK_BOTTLE));
            } else if (!player.inventory.addItemStackToInventory(new ItemStack(TofuItems.SOYMILK_BOTTLE))) {
                player.dropItem(new ItemStack(TofuItems.SOYMILK_BOTTLE), false);
            }

            return true;
        } else {
            return super.processInteract(player, hand);
        }
    }

    private void setTofuCowType(TofuCowEntity.Type typeIn) {
        this.dataManager.set(TOFUCOW_TYPE, typeIn.name);
    }

    public TofuCowEntity.Type getTofuCowType() {
        return TofuCowEntity.Type.getTypeByName(this.dataManager.get(TOFUCOW_TYPE));
    }

    public TofuCowEntity createChild(AgeableEntity ageable) {
        TofuCowEntity mooshroomentity = TofuEntitys.TOFUCOW.create(this.world);
        mooshroomentity.setTofuCowType(this.func_213445_a((TofuCowEntity) ageable));
        return mooshroomentity;
    }

    private TofuCowEntity.Type func_213445_a(TofuCowEntity p_213445_1_) {
        TofuCowEntity.Type mooshroomentity$type = this.getTofuCowType();
        TofuCowEntity.Type mooshroomentity$type1 = p_213445_1_.getTofuCowType();
        TofuCowEntity.Type mooshroomentity$type2;

        mooshroomentity$type2 = this.rand.nextBoolean() ? mooshroomentity$type : mooshroomentity$type1;


        return mooshroomentity$type2;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        Biome biome = worldIn.getBiome(new BlockPos(this));

        if (biome == TofuBiomes.TOFU_PLAIN) {
            if (worldIn.getRandom().nextInt(8) == 0) {
                this.setTofuCowType(Type.LEEK);
            }
        } else {
            if (biome == TofuBiomes.ZUNDATOFU_PLAIN || biome == TofuBiomes.TOFUZUNDA_SWAMP) {
                if (worldIn.getRandom().nextInt(8) == 0) {
                    this.setTofuCowType(Type.NONE);
                } else {
                    this.setTofuCowType(Type.ZUNDA);
                }
            }
        }


        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_ITEMS.test(stack);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isChild() ? sizeIn.height * 0.95F : 1.3F;
    }

    public static enum Type {
        NONE("none", Blocks.AIR.getDefaultState()),
        LEEK("leek", TofuBlocks.LEEK.getDefaultState()),
        ZUNDA("zunda", Blocks.AIR.getDefaultState());

        private final String name;
        private final BlockState renderState;

        private Type(String nameIn, BlockState renderStateIn) {
            this.name = nameIn;
            this.renderState = renderStateIn;
        }

        /**
         * A block state that is rendered on the back of the mooshroom.
         */
        @OnlyIn(Dist.CLIENT)
        public BlockState getRenderState() {
            return this.renderState;
        }

        private static TofuCowEntity.Type getTypeByName(String nameIn) {
            for (TofuCowEntity.Type mooshroomentity$type : values()) {
                if (mooshroomentity$type.name.equals(nameIn)) {
                    return mooshroomentity$type;
                }
            }

            return LEEK;
        }
    }
}