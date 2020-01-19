package baguchan.mcmod.tofucraft.entity.ai;

import baguchan.mcmod.tofucraft.entity.TofuGandlemEntity;
import baguchan.mcmod.tofucraft.init.TofuEffectRegistry;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;

import java.util.EnumSet;

public class HealSpellGoal extends CastingGoal {
    public HealSpellGoal(TofuGandlemEntity tofuGandlemEntity) {
        super(tofuGandlemEntity);
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if (!super.shouldExecute()) {
            return false;
        } else {
            return this.tofuGandlemEntity.getHealth() < this.tofuGandlemEntity.getMaxHealth() / 2;
        }
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.spellCooldown = this.tofuGandlemEntity.ticksExisted + 1200;
        this.spellWarmup = 80;
        this.tofuGandlemEntity.playSound(SoundEvents.ENTITY_ILLUSIONER_PREPARE_MIRROR, 2.0F, 1.0F);
    }

    @Override
    protected void castSpell() {
        super.castSpell();

        this.tofuGandlemEntity.heal(10);
        this.tofuGandlemEntity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 300));
        this.tofuGandlemEntity.addPotionEffect(new EffectInstance(TofuEffectRegistry.TOFU_RESISTANCE, 1200));
    }
}
