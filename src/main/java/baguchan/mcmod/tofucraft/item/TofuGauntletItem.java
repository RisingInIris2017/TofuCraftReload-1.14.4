package baguchan.mcmod.tofucraft.item;

import baguchan.mcmod.tofucraft.entity.projectile.FukumameEntity;
import baguchan.mcmod.tofucraft.entity.projectile.TofuHomingForceEntity;
import baguchan.mcmod.tofucraft.item.base.ItemTofuEnergyContained;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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

public class TofuGauntletItem extends ItemTofuEnergyContained {
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

        if (entityLiving.getLastAttackedEntity() != null) {
            if (entityLiving instanceof PlayerEntity && !((PlayerEntity) entityLiving).isCreative()) {
                if (getEnergy(stack) >= 10) {
                    drain(stack, 10, false);
                    ((PlayerEntity) entityLiving).getCooldownTracker().setCooldown(stack.getItem(), 50);
                } else {
                    stack.damageItem(1, entityLiving, (p_220009_1_) -> {
                        p_220009_1_.sendBreakAnimation(entityLiving.getActiveHand());
                    });

                    ((PlayerEntity) entityLiving).getCooldownTracker().setCooldown(stack.getItem(), 80);
                }
            }

            entityLiving.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 3.0F, 1.0F / (entityLiving.getRNG().nextFloat() * 0.4F + 0.8F));

            for (int i = 0; i < 1; i++) {
                entityLiving.world.addEntity(new TofuHomingForceEntity(entityLiving.world, entityLiving, entityLiving.getLastAttackedEntity()));
            }
        }
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        Mode mode = Mode.byItemStack(itemstack);

        if (!isUsable(itemstack)) {
            return ActionResult.func_226251_d_(itemstack);
        } else {
            if (mode == Mode.SOYSHOT) {
                if (!playerIn.abilities.isCreativeMode) {

                    if (getEnergy(itemstack) >= 5) {
                        drain(itemstack, 5, false);
                    } else {
                        itemstack.damageItem(1, playerIn, (p_220009_1_) -> {
                            p_220009_1_.sendBreakAnimation(playerIn.getActiveHand());
                        });
                    }
                }

                worldIn.playSound((PlayerEntity) null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                playerIn.getCooldownTracker().setCooldown(this, 10);
                if (!worldIn.isRemote) {
                    for (int i = 0; i < 8; i++) {
                        FukumameEntity fukumamelentity = new FukumameEntity(worldIn, playerIn);
                        float d0 = (worldIn.rand.nextFloat() * 18.0F) - 9.0F;

                        fukumamelentity.shoot(playerIn, playerIn.rotationPitch + d0 * 0.25F, playerIn.rotationYaw + d0, 0.0F, 1.5F, 0.8F);
                        worldIn.addEntity(fukumamelentity);
                    }
                }

                playerIn.addStat(Stats.ITEM_USED.get(this));
                return ActionResult.func_226249_b_(itemstack);
            } else {
                playerIn.setActiveHand(handIn);
                return ActionResult.func_226249_b_(itemstack);
            }
        }
    }

    public Mode toggleBowMode(ItemStack stack) {
        Mode current = Mode.byItemStack(stack);
        CompoundNBT nbt = stack.getOrCreateTag();

        Mode next = Mode.byType(current.getType() + 1);

        nbt.putInt("Mode", next.getType());

        return next;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        toggleBowMode(stack);

        entity.playSound(SoundEvents.UI_BUTTON_CLICK, 0.5F, 1.75F);

        if (entity.world.isRemote && entity instanceof PlayerEntity) {
            ((PlayerEntity) entity).sendStatusMessage(getModeMessage(stack), true);
        }

        return false;
    }

    public ITextComponent getModeMessage(ItemStack stack) {
        Mode mode = Mode.byItemStack(stack);
        ITextComponent name = new TranslationTextComponent(mode.getUnlocalizedName(stack));
        ITextComponent title = new TranslationTextComponent(stack.getTranslationKey() + ".mode");

        return title.appendText(": ").appendSibling(name);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(getModeMessage(stack));
    }

    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    public int getEnergyMax(ItemStack inst) {
        return 6000;
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
