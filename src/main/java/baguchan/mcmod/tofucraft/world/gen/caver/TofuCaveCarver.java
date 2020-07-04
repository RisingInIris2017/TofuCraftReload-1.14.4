package baguchan.mcmod.tofucraft.world.gen.caver;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuFluids;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class TofuCaveCarver extends CaveWorldCarver {
    private final float radiusScale;
    private final boolean largeCave;

    public TofuCaveCarver(Codec<ProbabilityConfig> deserialize, float radiusScale, boolean largeCave) {
        super(deserialize, 256);
        this.radiusScale = radiusScale;
        this.largeCave = largeCave;
        this.carvableBlocks = ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.GRASS_BLOCK, TofuBlocks.TOFUTERRAIN, TofuBlocks.MINCEDTOFU, TofuBlocks.ZUNDATOFUTERRAIN);
        this.carvableFluids = ImmutableSet.of(TofuFluids.SOYMILK);
    }


    protected boolean func_230358_a_(IChunk p_230358_1_, Function<BlockPos, Biome> p_230358_2_, BitSet p_230358_3_, Random p_230358_4_, BlockPos.Mutable p_230358_5_, BlockPos.Mutable p_230358_6_, BlockPos.Mutable p_230358_7_, int p_230358_8_, int p_230358_9_, int p_230358_10_, int p_230358_11_, int p_230358_12_, int p_230358_13_, int p_230358_14_, int p_230358_15_, MutableBoolean p_230358_16_) {
        int i = p_230358_13_ | p_230358_15_ << 4 | p_230358_14_ << 8;
        if (p_230358_3_.get(i)) {
            return false;
        } else {
            p_230358_3_.set(i);
            p_230358_5_.setPos(p_230358_11_, p_230358_14_, p_230358_12_);
            BlockState blockstate = p_230358_1_.getBlockState(p_230358_5_);
            BlockState blockstate1 = p_230358_1_.getBlockState(p_230358_6_.func_239622_a_(p_230358_5_, Direction.UP));
            if (blockstate.isIn(Blocks.GRASS_BLOCK) || blockstate.isIn(Blocks.MYCELIUM)) {
                p_230358_16_.setTrue();
            }

            if (!this.canCarveBlock(blockstate, blockstate1)) {
                return false;
            } else {
                if (p_230358_14_ < 11) {
                    p_230358_1_.setBlockState(p_230358_5_, TofuBlocks.SOYMILK.getDefaultState(), false);
                } else {
                    p_230358_1_.setBlockState(p_230358_5_, CAVE_AIR, false);
                    if (p_230358_16_.isTrue()) {
                        p_230358_7_.func_239622_a_(p_230358_5_, Direction.DOWN);
                        if (p_230358_1_.getBlockState(p_230358_7_).isIn(Blocks.DIRT)) {
                            p_230358_1_.setBlockState(p_230358_7_, p_230358_2_.apply(p_230358_5_).getSurfaceBuilderConfig().getTop(), false);
                        }
                    }
                }

                return true;
            }
        }
    }
}