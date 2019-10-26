package baguchan.mcmod.tofucraft.world.biome.gen.feature;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class TofuBerryFeature extends Feature<NoFeatureConfig> {
    private static final BlockState BERRYBASE = TofuBlocks.TOFUBERRYSTEM.getDefaultState();
    private static final BlockState BERRY = TofuBlocks.TOFUBERRY.getDefaultState();

    public TofuBerryFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49919_1_) {
        super(p_i49919_1_);
    }

    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int i = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(pos);
        BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos(pos);

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