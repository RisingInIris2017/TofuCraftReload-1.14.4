package baguchan.mcmod.tofucraft.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

public class TofuDamageSource {
    public static DamageSource causeTofuForceDamage(Entity source, LivingEntity indirectEntityIn) {
        return new IndirectEntityDamageSource("tofucraft.tofuforce", source, indirectEntityIn).setProjectile();
    }
}
