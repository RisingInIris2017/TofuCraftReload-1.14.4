package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.init.TofuItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class MisoBarrelBlock extends SimpleBarrelBlock {
    public static final BooleanProperty SOYSAUCE = BooleanProperty.create("soysauce");

    public MisoBarrelBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FERM, 0).with(SOYSAUCE, true));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.PASS;
        } else {
            ItemStack itemHeld = player.getHeldItem(handIn);
            if (state.get(FERM) >= 7 && state.get(SOYSAUCE)) {
                if (itemHeld.getItem() == Items.GLASS_BOTTLE && !player.abilities.isCreativeMode) {
                    ItemStack bottle = new ItemStack(TofuItems.SOYSAUCE);

                    worldIn.playSound(player, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    itemHeld.shrink(1);

                    if (itemHeld.isEmpty()) {
                        player.setHeldItem(handIn, bottle);
                    } else {
                        if (!player.inventory.addItemStackToInventory(bottle)) {
                            player.dropItem(bottle, false);
                        }
                    }
                    worldIn.setBlockState(pos, state.with(SOYSAUCE, false), 3);
                }
            }

            return ActionResultType.SUCCESS;
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FERM, SOYSAUCE);
    }
}
