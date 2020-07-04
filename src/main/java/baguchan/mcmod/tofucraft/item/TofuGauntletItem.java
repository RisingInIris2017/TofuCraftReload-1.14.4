package baguchan.mcmod.tofucraft.item;

import baguchan.mcmod.tofucraft.entity.projectile.FukumameEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;

public class TofuGauntletItem extends Item {
    public TofuGauntletItem(Properties group) {
        super(group);
    }

    public static boolean isUsable(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);

        int i = this.getUseDuration(stack) - timeLeft;

        /*if (entityLiving.getLastAttackedEntity() != null) {
            if (entityLiving instanceof PlayerEntity && !((PlayerEntity) entityLiving).isCreative()) {

                stack.damageItem(1, entityLiving, (p_220009_1_) -> {
                    p_220009_1_.sendBreakAnimation(entityLiving.getActiveHand());
                });

                ((PlayerEntity) entityLiving).getCooldownTracker().setCooldown(stack.getItem(), 60);

            }

            entityLiving.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 3.0F, 1.0F / (entityLiving.getRNG().nextFloat() * 0.4F + 0.8F));

            TofuHomingForceEntity forceEntity = new TofuHomingForceEntity(entityLiving.world, entityLiving, entityLiving.getLastAttackedEntity());

            forceEntity.damage = 2.0F + getCharge(i, stack) * 4.0F;

            entityLiving.world.addEntity(forceEntity);
        } else {

            List<LivingEntity> list = worldIn.getEntitiesWithinAABB(LivingEntity.class, (new AxisAlignedBB((double) entityLiving.getPosX(), (double) entityLiving.getPosY(), (double) entityLiving.getPosZ(), (double) entityLiving.getPosX(), (double) entityLiving.getPosY(), (double) entityLiving.getPosZ())).grow(24.0D, 8.0F, 24.0D), (p_205033_0_) -> {
                return p_205033_0_ != entityLiving && p_205033_0_ instanceof IMob;
            });

            if (!list.isEmpty()) {
                if (entityLiving instanceof PlayerEntity && !((PlayerEntity) entityLiving).isCreative()) {

                    stack.damageItem(1, entityLiving, (p_220009_1_) -> {
                        p_220009_1_.sendBreakAnimation(entityLiving.getActiveHand());
                    });

                    ((PlayerEntity) entityLiving).getCooldownTracker().setCooldown(stack.getItem(), 60);

                }

                entityLiving.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 3.0F, 1.0F / (entityLiving.getRNG().nextFloat() * 0.4F + 0.8F));

                TofuHomingForceEntity forceEntity = new TofuHomingForceEntity(entityLiving.world, entityLiving, list.get(worldIn.rand.nextInt(list.size())));

                forceEntity.damage = 2.0F + getCharge(i, stack) * 4.0F;

                entityLiving.world.addEntity(forceEntity);
            }

        }*/
    }

    private static float getCharge(int useTime, ItemStack stack) {
        float f = (float) useTime / 20F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        Mode mode = Mode.byItemStack(itemstack);

        if (!playerIn.isSneaking()) {
            if (!isUsable(itemstack)) {
                return ActionResult.resultFail(itemstack);
            } else {
                if (mode == Mode.SOYSHOT) {
                    if (!playerIn.abilities.isCreativeMode) {

                        itemstack.damageItem(1, playerIn, (p_220009_1_) -> {
                            p_220009_1_.sendBreakAnimation(playerIn.getActiveHand());
                        });

                    }

                    worldIn.playSound((PlayerEntity) null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                    playerIn.getCooldownTracker().setCooldown(this, 10);
                    if (!worldIn.isRemote) {
                        for (int i = 0; i < 8; i++) {
                            FukumameEntity fukumamelentity = new FukumameEntity(worldIn, playerIn);
                            float d0 = (worldIn.rand.nextFloat() * 18.0F) - 9.0F;

                            fukumamelentity.func_234612_a_(playerIn, playerIn.rotationPitch + d0 * 0.25F, playerIn.rotationYaw + d0, 0.0F, 1.5F, 0.8F);
                            worldIn.addEntity(fukumamelentity);
                        }
                    }

                    playerIn.addStat(Stats.ITEM_USED.get(this));
                    return ActionResult.resultSuccess(itemstack);
                } else {
                    playerIn.setActiveHand(handIn);
                    return ActionResult.resultSuccess(itemstack);
                }
            }
        } else {
            toggleBowMode(itemstack);

            playerIn.playSound(SoundEvents.UI_BUTTON_CLICK, 0.5F, 1.75F);

            if (playerIn.world.isRemote) {
                ((PlayerEntity) playerIn).sendStatusMessage(getModeMessage(itemstack), true);
            }
            return ActionResult.resultSuccess(itemstack);
        }
    }

    public Mode toggleBowMode(ItemStack stack) {
        Mode current = Mode.byItemStack(stack);
        CompoundNBT nbt = stack.getOrCreateTag();

        Mode next = Mode.byType(current.getType() + 1);

        nbt.putInt("Mode", next.getType());

        return next;
    }

    public ITextComponent getModeMessage(ItemStack stack) {
        Mode mode = Mode.byItemStack(stack);
        ITextComponent name = new TranslationTextComponent(mode.getUnlocalizedName(stack));
        ITextComponent title = new TranslationTextComponent(stack.getTranslationKey() + ".mode");

        return name;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(getModeMessage(stack));
    }

    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }

    public enum Mode {
        SOYSHOT(0, "soyshot", 0.0F),
        HOMING(1, "homing_shot", 20.0F);

        private static final Mode[] TYPE_LOOKUP = new Mode[values().length];

        private int modeType;
        private String modeName;
        private float pullingSpeed;

        private Mode(int type, String name, float pulling) {
            this.modeType = type;
            this.modeName = name;
            this.pullingSpeed = pulling;
        }

        public int getType() {
            return modeType;
        }

        public String getModeName() {
            return modeName;
        }

        public String getUnlocalizedName(ItemStack stack) {
            return stack.getTranslationKey() + "." + modeName;
        }

        public float getPullingSpeed() {
            return pullingSpeed;
        }

        public static Mode byItemStack(ItemStack stack) {
            if (stack.isEmpty()) {
                return SOYSHOT;
            }

            CompoundNBT nbt = stack.getOrCreateTag();

            if (nbt == null || !nbt.contains("Mode", Constants.NBT.TAG_ANY_NUMERIC)) {
                return SOYSHOT;
            }

            return byType(nbt.getInt("Mode"));
        }

        public static Mode byType(int type) {
            if (type < 0 || type >= TYPE_LOOKUP.length) {
                type = 0;
            }

            return TYPE_LOOKUP[type];
        }

        static {
            for (Mode mode : values()) {
                TYPE_LOOKUP[mode.getType()] = mode;
            }
        }
    }
}
