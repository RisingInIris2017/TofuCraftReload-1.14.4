package baguchan.mcmod.tofucraft.world.gen.feature;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuTags;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class TofuStoneCaveFeature extends Feature<NoFeatureConfig> {

    public TofuStoneCaveFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int i = rand.nextInt(10);

                for (int y = 1; y < 10 + i; y++) {
                    this.tryPlace(worldIn, pos.add(x, y, z));
                }

            }

        }

        return true;
    }

    private void tryPlace(IWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        if (state.isIn(TofuTags.Blocks.TOFUTERRAIN)) {
            this.setBlockState(world, pos, TofuBlocks.TOFUISHI_TERRAIN.getDefaultState());
        }

        /*if (state.getBlock() == TofuBlocks.ORE_TOFUDIAMOND) {
            this.setBlockState(world, pos, TofuBlocks.TOFUISHI_TOFUDIAMOND.getDefaultState());
        }*/
    }

}