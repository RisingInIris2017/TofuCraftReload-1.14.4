package baguchan.mcmod.tofucraft.world.gen.feature;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class TofuBuildingFeature extends Feature<NoFeatureConfig> {
    protected final int minHeight;
    protected final BlockState blockTofuState;

    public TofuBuildingFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        this(configFactoryIn, 3, TofuBlocks.TOFUTERRAIN.getDefaultState());
    }

    public TofuBuildingFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn, int height, BlockState tofustate) {
        super(configFactoryIn);
        this.minHeight = height;
        this.blockTofuState = tofustate;
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int var6 = rand.nextInt(3) + this.minHeight;
        boolean var7 = true;
        int var3 = pos.getX();
        int var4 = pos.getY();
        int var5 = pos.getZ();

        if (var4 >= 1 && var4 + var6 + 1 <= 256) {
            byte var9;
            int var11;
            BlockPos.Mutable mutablepos = new BlockPos.Mutable();

            for (int var8 = var4; var8 <= var4 + 1 + var6; ++var8) {
                var9 = 1;

                if (var8 == var4) {
                    var9 = 0;
                }

                if (var8 >= var4 + 1 + var6 - 2) {
                    var9 = 2;
                }

                for (int var10 = var3 - var9; var10 <= var3 + var9 && var7; ++var10) {
                    for (var11 = var5 - var9; var11 <= var5 + var9 && var7; ++var11) {
                        if (var8 >= 0 && var8 < 256) {
                            mutablepos.setPos(var10, var8, var11);

                            if (!worldIn.isAirBlock(mutablepos)) {
                                var7 = false;
                            }
                        } else {
                            var7 = false;
                        }
                    }
                }
            }

            if (!var7) {
                return false;
            } else {
                Block var8 = worldIn.getBlockState(pos.down()).getBlock();

                if ((var8 == TofuBlocks.TOFUTERRAIN)
                        && var4 < 256 - var6 - 1) {
                    this.buildTofu(pos, var6, worldIn.getWorld(), rand);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    protected void buildTofu(BlockPos pos, int height, World worldIn, Random rand) {
        int ox = pos.getX();
        int oy = pos.getY();
        int oz = pos.getZ();

        int radius = 1 + height / 2;
        BlockPos.Mutable mutablepos = new BlockPos.Mutable();

        for (int blockY = oy; blockY <= oy + height; ++blockY) {
            for (int blockX = ox - radius; blockX <= ox + radius; ++blockX) {

                for (int blockZ = oz - radius; blockZ <= oz + radius; ++blockZ) {
                    if (blockY == oy) {
                        for (int y = oy - 1; y > 0; y--) {
                            mutablepos.setPos(blockX, y, blockZ);

                            Block blockId = worldIn.getBlockState(mutablepos).getBlock();
                            if (blockId == Blocks.AIR || blockId == TofuBlocks.LEEK) {
                                this.setBlockState(worldIn, mutablepos, this.blockTofuState);
                            } else {
                                break;
                            }
                        }
                    }
                    mutablepos.setPos(blockX, blockY, blockZ);

                    this.setBlockState(worldIn, mutablepos, this.blockTofuState);
                }
            }
        }
        mutablepos.setPos(ox, oy + height + 1, oz);
        //this.plantLeeks(mutablepos, radius, worldIn, rand);
    }
}
