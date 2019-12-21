package baguchan.mcmod.tofucraft.block.mecha;

import baguchan.mcmod.tofucraft.tileentity.tofuenergy.sender.TFStorageTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class TFStorageBlock extends ContainerBlock {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public TFStorageBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(LIT, Boolean.valueOf(false)));
    }


    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TFStorageTileEntity) {
                player.openContainer((INamedContainerProvider) tileentity);
            }
        }
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.animateTick(stateIn, worldIn, pos, rand);

        boolean lit = (Boolean) stateIn.get(LIT);
        if (lit) {
            if (rand.nextInt(2) == 0) {
                double d4 = rand.nextBoolean() ? 0.05 : -0.05;
                double d0 = ((float) pos.getX() + 0.5 + (rand.nextFloat() * d4));
                double d1 = (double) ((float) pos.getY() + 0.8);
                double d2 = ((float) pos.getZ() + 0.5 + rand.nextFloat() * d4);

                worldIn.addParticle(ParticleTypes.CLOUD, d0, d1, d2, 0.0D, 0.35D, 0.0D);
            }
        }
    }

    public int getLightValue(BlockState state) {
        return state.get(LIT) ? super.getLightValue(state) : 0;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TFStorageTileEntity) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TFStorageTileEntity) tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TFStorageTileEntity();
    }
}
