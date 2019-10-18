package baguchan.mcmod.tofucraft.item;

import baguchan.mcmod.tofucraft.entity.projectile.ZundaArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ZundaArrowItem extends ArrowItem {
    public ZundaArrowItem(Properties group) {
        super(group);
    }

    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        ZundaArrowEntity arrowentity = new ZundaArrowEntity(worldIn, shooter);
        return arrowentity;
    }
}
