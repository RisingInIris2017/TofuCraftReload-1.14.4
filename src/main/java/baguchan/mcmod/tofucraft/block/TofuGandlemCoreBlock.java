package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.entity.TofuGandlemEntity;
import baguchan.mcmod.tofucraft.init.TofuEntitys;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class TofuGandlemCoreBlock extends Block {
    public static VoxelShape AABB = Block.makeCuboidShape(4.0f, 0.0f, 4.0f, 12.0f, 8.0F, 12.0f);

    public TofuGandlemCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return true;
        } else {
            worldIn.destroyBlock(pos, false);

            worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.BLOCKS, 3.0F, 1.0F);

            TofuGandlemEntity tofuGandlemEntity = TofuEntitys.TOFUGANDLEM.create(worldIn);
            tofuGandlemEntity.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
            tofuGandlemEntity.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(new BlockPos(tofuGandlemEntity)), SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
            worldIn.addEntity(tofuGandlemEntity);

            return true;
        }
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return AABB;
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isSolid(BlockState state) {
        return false;
    }
}
