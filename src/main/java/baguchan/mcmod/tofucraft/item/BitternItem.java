package baguchan.mcmod.tofucraft.item;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuFluids;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BitternItem extends Item {
    public BitternItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        } else {
            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
                BlockPos blockpos = blockraytraceresult.getPos();
                Direction direction = blockraytraceresult.getFace();
                if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(blockpos.offset(direction), direction, itemstack)) {
                    return new ActionResult<>(ActionResultType.FAIL, itemstack);
                }

                BlockState blockstate = worldIn.getBlockState(blockpos);
                Material material = blockstate.getMaterial();
                FluidState ifluidstate = worldIn.getFluidState(blockpos);
                if ((ifluidstate.getFluid() == TofuFluids.SOYMILK)) {

                    // special case for handling block placement with water lilies
                    net.minecraftforge.common.util.BlockSnapshot blocksnapshot = net.minecraftforge.common.util.BlockSnapshot.create(worldIn, blockpos);
                    worldIn.setBlockState(blockpos, TofuBlocks.KINUTOFU.getDefaultState(), 11);
                    if (net.minecraftforge.event.ForgeEventFactory.onBlockPlace(playerIn, blocksnapshot, net.minecraft.util.Direction.UP)) {
                        blocksnapshot.restore(true, false);
                        return new ActionResult<ItemStack>(ActionResultType.FAIL, itemstack);
                    }

                    if (playerIn instanceof ServerPlayerEntity) {
                        CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) playerIn, itemstack);
                    }

                    ItemStack itemHeld = playerIn.getHeldItem(handIn);
                    ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);

                    if (!playerIn.abilities.isCreativeMode) {
                        if (itemHeld.getCount() == 1) {
                            playerIn.setHeldItem(handIn, bottle);
                        } else {
                            if (!playerIn.inventory.addItemStackToInventory(bottle)) {
                                worldIn.addEntity(new ItemEntity(worldIn, (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 1.5D, (double) blockpos.getZ() + 0.5D, bottle));
                            }

                            itemHeld.shrink(1);
                        }
                    }

                    playerIn.addStat(Stats.ITEM_USED.get(this));
                    worldIn.playSound(playerIn, blockpos, SoundEvents.BLOCK_SNOW_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
                } else if ((ifluidstate.getFluid() == TofuFluids.SOYMILK_HELL)) {

                    // special case for handling block placement with water lilies
                    net.minecraftforge.common.util.BlockSnapshot blocksnapshot = net.minecraftforge.common.util.BlockSnapshot.create(worldIn, blockpos);
                    worldIn.setBlockState(blockpos, TofuBlocks.HELLTOFU.getDefaultState(), 11);
                    if (net.minecraftforge.event.ForgeEventFactory.onBlockPlace(playerIn, blocksnapshot, net.minecraft.util.Direction.UP)) {
                        blocksnapshot.restore(true, false);
                        return new ActionResult<ItemStack>(ActionResultType.FAIL, itemstack);
                    }

                    if (playerIn instanceof ServerPlayerEntity) {
                        CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) playerIn, itemstack);
                    }

                    ItemStack itemHeld = playerIn.getHeldItem(handIn);
                    ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);

                    if (!playerIn.abilities.isCreativeMode) {
                        if (itemHeld.getCount() == 1) {
                            playerIn.setHeldItem(handIn, bottle);
                        } else {
                            if (!playerIn.inventory.addItemStackToInventory(bottle)) {
                                worldIn.addEntity(new ItemEntity(worldIn, (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 1.5D, (double) blockpos.getZ() + 0.5D, bottle));
                            }

                            itemHeld.shrink(1);
                        }
                    }

                    playerIn.addStat(Stats.ITEM_USED.get(this));
                    worldIn.playSound(playerIn, blockpos, SoundEvents.BLOCK_SNOW_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
                }
            }

            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
    }
}
