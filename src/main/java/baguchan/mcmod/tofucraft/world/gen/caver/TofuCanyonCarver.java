package baguchan.mcmod.tofucraft.world.gen.caver;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuFluids;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CanyonWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class TofuCanyonCarver extends CanyonWorldCarver {
    public TofuCanyonCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> p_i49930_1_) {
        super(p_i49930_1_);
        this.carvableBlocks = ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.GRASS_BLOCK, TofuBlocks.TOFUTERRAIN, TofuBlocks.MINCEDTOFU, TofuBlocks.TOFUISHI_TERRAIN);
        this.carvableFluids = ImmutableSet.of(TofuFluids.SOYMILK);
    }

    @Override
    protected boolean carveBlock(IChunk chunk, BitSet carvingMask, Random random, BlockPos.MutableBlockPos posHere, BlockPos.MutableBlockPos posAbove, BlockPos.MutableBlockPos posBelow, int p_222703_7_, int p_222703_8_, int p_222703_9_, int globalX, int globalZ, int x, int y, int z, AtomicBoolean foundSurface) {
        int index = x | z << 4 | y << 8;
        if (carvingMask.get(index)) {
            return false;
        }

        carvingMask.set(index);
        posHere.setPos(globalX, y, globalZ);

        BlockState state = chunk.getBlockState(posHere);
        BlockState stateAbove = chunk.getBlockState(posAbove.setPos(posHere).move(Direction.UP));
        if (!this.canCarveBlock(state, stateAbove)) {
            return false;
        } else {

            if (y > 12) {
                chunk.setBlockState(posHere, CAVE_AIR, false);
            } else {
                chunk.setBlockState(posHere, TofuBlocks.SOYMILK.getDefaultState(), false);
            }
        }

        return true;
    }
}
