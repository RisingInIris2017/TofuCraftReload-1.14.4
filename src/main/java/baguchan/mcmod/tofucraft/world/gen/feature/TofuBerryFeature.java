package baguchan.mcmod.tofucraft.world.gen.feature;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

public class TofuBerryFeature extends Feature<NoFeatureConfig> {
    private static final BlockState BERRYBASE = TofuBlocks.TOFUBERRYSTEM.getDefaultState();
    private static final BlockState BERRY = TofuBlocks.TOFUBERRY.getDefaultState();

    public TofuBerryFeature(Codec<NoFeatureConfig> p_i49919_1_) {
        super(p_i49919_1_);
    }

    @Override
    public boolean func_230362_a_(ISeedReader worldIn, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random rand, BlockPos pos, NoFeatureConfig p_230362_6_) {
        int i = 0;
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable(pos.getX(), pos.getY(), pos.getZ());
        BlockPos.Mutable blockpos$mutableblockpos1 = new BlockPos.Mutable(pos.getX(), pos.getY(), pos.getZ());

        if (worldIn.isAirBlock(blockpos$mutableblockpos)) {
            if (TofuBlocks.TOFUBERRYSTEM.getDefaultState().isValidPosition(worldIn, blockpos$mutableblockpos)) {
                int j = rand.nextInt(4) + 2;

                for (int l1 = 0; l1 < j && worldIn.isAirBlock(blockpos$mutableblockpos); ++l1) {
                    worldIn.setBlockState(blockpos$mutableblockpos, BERRYBASE, 2);
                    blockpos$mutableblockpos.move(Direction.DOWN, 1);
                }

                if (blockpos$mutableblockpos.getY() - pos.getY() <= -2) {
                    worldIn.setBlockState(blockpos$mutableblockpos, BERRY, 2);
                }
            }

            ++i;
        }

        return i > 0;
    }
}