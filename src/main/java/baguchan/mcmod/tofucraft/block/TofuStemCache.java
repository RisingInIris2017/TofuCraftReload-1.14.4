package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.init.TofuItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class TofuStemCache extends HorizontalBlock {
    public static final BooleanProperty ZUNDAMA = BooleanProperty.create("zundama");

    public TofuStemCache(Properties builder) {
        super(builder);
        this.setDefaultState(this.stateContainer.getBaseState().with(ZUNDAMA, Boolean.valueOf(true)));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        boolean hasZunda = state.get(ZUNDAMA);
        if (hasZunda) {
            ItemStack salt = new ItemStack(TofuItems.ZUNDAMA, 2 + worldIn.rand.nextInt(4));

            float f = 0.7F;
            double d0 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.2D + 0.6D;
            double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            ItemEntity itemEntity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, salt);
            itemEntity.setPickupDelay(10);
            worldIn.addEntity(itemEntity);

            worldIn.setBlockState(pos, state.with(ZUNDAMA, false), 3);


            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, ZUNDAMA);
    }
}
