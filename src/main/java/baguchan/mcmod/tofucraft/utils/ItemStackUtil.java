package baguchan.mcmod.tofucraft.utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ItemStackUtil {
    public static CompoundNBT getItemTagCompound(ItemStack stack) {
        return stack.getOrCreateTag();
    }

    public static void throwItemAt(LivingEntity from, ItemStack stack, LivingEntity to) {
        double d0 = from.getPosYEye() - (double) 0.3F;
        ItemEntity itementity = new ItemEntity(from.world, from.getPosX(), d0, from.getPosZ(), stack);
        BlockPos blockpos = new BlockPos(to);
        BlockPos blockpos1 = new BlockPos(from);
        float f = 0.3F;
        Vec3d vec3d = new Vec3d(blockpos.subtract(blockpos1));
        vec3d = vec3d.normalize().scale((double) 0.3F);
        itementity.setMotion(vec3d);
        itementity.setDefaultPickupDelay();
        from.world.addEntity(itementity);
    }
}
