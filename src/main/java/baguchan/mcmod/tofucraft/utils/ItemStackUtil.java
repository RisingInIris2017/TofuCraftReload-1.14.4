package baguchan.mcmod.tofucraft.utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d;

public class ItemStackUtil {
    public static CompoundNBT getItemTagCompound(ItemStack stack) {
        return stack.getOrCreateTag();
    }

    public static void throwItemAt(LivingEntity from, ItemStack p_233865_1_, Vector3d p_233865_2_) {
        double d0 = from.getPosYEye() - (double) 0.3F;
        ItemEntity itementity = new ItemEntity(from.world, from.getPosX(), d0, from.getPosZ(), p_233865_1_);
        float f = 0.3F;
        Vector3d vector3d = p_233865_2_.subtract(from.getPositionVec());
        vector3d = vector3d.normalize().scale((double) 0.3F);
        itementity.setMotion(vector3d);
        itementity.setDefaultPickupDelay();
        from.world.addEntity(itementity);
    }
}
