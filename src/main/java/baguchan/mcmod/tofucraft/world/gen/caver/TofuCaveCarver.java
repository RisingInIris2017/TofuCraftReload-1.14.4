package baguchan.mcmod.tofucraft.world.gen.caver;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.world.dimension.TofuWorldChunkGenerator;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class TofuCaveCarver extends CaveWorldCarver {
    private final float radiusScale;
    private final boolean largeCave;

    public TofuCaveCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> deserialize, float radiusScale, boolean largeCave) {
        super(deserialize, 256);
        this.radiusScale = radiusScale;
        this.largeCave = largeCave;
    }

    @Override
    protected int generateCaveStartY(Random random) {
        if (this.largeCave) {
            return random.nextInt(240) + 8;
        } else {
            return random.nextInt(random.nextInt(TofuWorldChunkGenerator.SURFACE_LEVEL) + 8);
        }
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

    @Override
    protected boolean canCarveBlock(BlockState state, BlockState aboveState) {
        Material material = state.getMaterial();
        Material aboveMaterial = aboveState.getMaterial();
        return (material == Material.ROCK || material == Material.EARTH || material == Material.ORGANIC || state.getBlock() == TofuBlocks.TOFUTERRAIN)
                && aboveMaterial != Material.WATER && aboveMaterial != Material.LAVA;
    }

    @Override
    protected float generateCaveRadius(Random random) {
        return super.generateCaveRadius(random) * this.radiusScale;
    }
}