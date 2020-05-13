package baguchan.mcmod.tofucraft.world.gen.feature.structure.jigjaw;

import baguchan.mcmod.tofucraft.block.TofuChestBlock;
import baguchan.mcmod.tofucraft.entity.TofuMindEntity;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuLootTables;
import baguchan.mcmod.tofucraft.tileentity.TofuChestTileEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.StructureProcessor;

import java.util.List;
import java.util.Random;

public class ZundaTemplateJigjaw extends FunctionalDataMarkerJigjaw {
    public ZundaTemplateJigjaw(String p_i51400_1_, List<StructureProcessor> p_i51400_2_) {
        super(p_i51400_1_, p_i51400_2_);
    }

    public ZundaTemplateJigjaw(String location) {
        this(location, ImmutableList.of());
    }

    public void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
        if (function.equals("Chest")) {
            worldIn.setBlockState(pos, TofuBlocks.TOFUCHEST.getDefaultState().with(TofuChestBlock.FACING, Direction.WEST), 2);

            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TofuChestTileEntity) {
                ((TofuChestTileEntity) tileEntity).setLootTable(TofuLootTables.tofucastle_normal, rand.nextLong());
            }

        } else if (function.equals("Mind")) {
            TofuMindEntity entitymind = TofuEntitys.TOFUMIND.create(worldIn.getWorld());
            entitymind.enablePersistence();
            entitymind.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
            entitymind.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(new BlockPos(entitymind)), SpawnReason.STRUCTURE, (ILivingEntityData) null, (CompoundNBT) null);
            worldIn.addEntity(entitymind);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
        }
    }
}
