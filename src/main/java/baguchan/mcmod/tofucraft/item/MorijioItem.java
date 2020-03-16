package baguchan.mcmod.tofucraft.item;

import baguchan.mcmod.tofucraft.entity.MorijioEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class MorijioItem extends Item {
    public MorijioItem(Properties group) {
        super(group);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        Direction direction = context.getFace();
        if (direction == Direction.DOWN) {
            return ActionResultType.FAIL;
        } else {
            World world = context.getWorld();
            BlockItemUseContext blockitemusecontext = new BlockItemUseContext(context);
            BlockPos blockpos = blockitemusecontext.getPos();
            BlockPos blockpos1 = blockpos.up();
            if (blockitemusecontext.canPlace()) {
                double d0 = (double) blockpos.getX();
                double d1 = (double) blockpos.getY();
                double d2 = (double) blockpos.getZ();
                List<Entity> list = world.getEntitiesWithinAABBExcludingEntity((Entity) null, new AxisAlignedBB(d0, d1, d2, d0 + 1.0D, d1 + 1.0D, d2 + 1.0D));
                if (!list.isEmpty()) {
                    return ActionResultType.FAIL;
                } else {
                    ItemStack itemstack = context.getItem();
                    if (!world.isRemote) {
                        MorijioEntity armorstandentity = new MorijioEntity(world, d0 + 0.5D, d1, d2 + 0.5D);
                        float f = (float) MathHelper.floor((MathHelper.wrapDegrees(context.getPlacementYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                        armorstandentity.setLocationAndAngles(d0 + 0.5D, d1, d2 + 0.5D, f, 0.0F);
                        EntityType.applyItemNBT(world, context.getPlayer(), armorstandentity, itemstack.getTag());
                        world.addEntity(armorstandentity);
                        world.playSound((PlayerEntity) null, armorstandentity.getPosX(), armorstandentity.getPosY(), armorstandentity.getPosZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);
                    }

                    itemstack.shrink(1);
                    return ActionResultType.SUCCESS;
                }
            } else {
                return ActionResultType.FAIL;
            }
        }
    }
}
