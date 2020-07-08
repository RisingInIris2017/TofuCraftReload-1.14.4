package baguchan.mcmod.tofucraft.item;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;

public class SoybeansItem extends BlockItem {
    public SoybeansItem(Properties group) {
        super(TofuBlocks.SOYBEAN, group);
    }

    public String getTranslationKey() {
        return this.getDefaultTranslationKey();
    }

    protected boolean canPlace(BlockItemUseContext p_195944_1_, BlockState p_195944_2_) {
        PlayerEntity playerentity = p_195944_1_.getPlayer();
        ISelectionContext iselectioncontext = playerentity == null ? ISelectionContext.dummy() : ISelectionContext.forEntity(playerentity);
        return (!this.checkPosition() || TofuBlocks.SPROUTS.getDefaultState().isValidPosition(p_195944_1_.getWorld(), p_195944_1_.getPos()) || p_195944_2_.isValidPosition(p_195944_1_.getWorld(), p_195944_1_.getPos())) && p_195944_1_.getWorld().func_226663_a_(p_195944_2_, p_195944_1_.getPos(), iselectioncontext);
    }

    @Override
    protected boolean placeBlock(BlockItemUseContext context, BlockState state) {
        if (context.getWorld().getBlockState(context.getPos().down()).isIn(BlockTags.WOOL)) {
            return context.getWorld().setBlockState(context.getPos(), TofuBlocks.SPROUTS.getDefaultState(), 11);
        } else {
            return super.placeBlock(context, state);
        }
    }

    public ActionResultType tryPlace(BlockItemUseContext context) {
        if (!context.canPlace()) {
            return ActionResultType.FAIL;
        } else {
            BlockItemUseContext blockitemusecontext = this.getBlockItemUseContext(context);
            if (blockitemusecontext == null) {
                return ActionResultType.FAIL;
            } else {
                BlockState blockstate = this.getStateForPlacement(blockitemusecontext);
                if (blockstate == null) {
                    return ActionResultType.FAIL;
                } else if (!this.placeBlock(blockitemusecontext, blockstate)) {
                    return ActionResultType.FAIL;
                } else {
                    BlockPos blockpos = blockitemusecontext.getPos();
                    World world = blockitemusecontext.getWorld();
                    PlayerEntity playerentity = blockitemusecontext.getPlayer();
                    ItemStack itemstack = blockitemusecontext.getItem();
                    BlockState blockstate1 = world.getBlockState(blockpos);
                    Block block = blockstate1.getBlock();
                    this.onBlockPlaced(blockpos, world, playerentity, itemstack, blockstate1);
                    block.onBlockPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
                    if (playerentity instanceof ServerPlayerEntity) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerentity, blockpos, itemstack);
                    }


                    SoundType soundtype = blockstate1.getSoundType(world, blockpos, context.getPlayer());
                    world.playSound(playerentity, blockpos, this.getPlaceSound(blockstate1, world, blockpos, context.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    itemstack.shrink(1);
                    return ActionResultType.SUCCESS;
                }
            }
        }
    }
}
