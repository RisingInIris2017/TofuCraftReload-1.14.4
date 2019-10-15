package baguchan.mcmod.tofucraft.world.biome.gen.feature;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class TofuTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
    private static final BlockState DEFAULT_TRUNK = TofuBlocks.ISHITOFU.getDefaultState();
    private static final BlockState DEFAULT_LEAF = TofuBlocks.TOFULEAVES.getDefaultState();
    protected final int minTreeHeight;
    private final boolean vinesGrow;
    private final BlockState trunk;
    private final BlockState leaf;

    public TofuTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn, boolean doBlockNotifyOnPlace) {
        this(configFactoryIn, doBlockNotifyOnPlace, 4, DEFAULT_TRUNK, DEFAULT_LEAF, false);
    }

    public TofuTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn, boolean doBlockNotifyOnPlace, int minTreeHeightIn, BlockState trunkState, BlockState leafState, boolean vinesGrowIn) {
        super(configFactoryIn, doBlockNotifyOnPlace);
        this.minTreeHeight = minTreeHeightIn;
        this.trunk = trunkState;
        this.leaf = leafState;
        this.vinesGrow = vinesGrowIn;
        this.setSapling((net.minecraftforge.common.IPlantable) TofuBlocks.TOFUSAPLING);
    }

    public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox p_208519_5_) {
        int i = this.getHeight(rand);
        boolean flag = true;
        if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getMaxHeight()) {
            for (int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
                int k = 1;
                if (j == position.getY()) {
                    k = 0;
                }

                if (j >= position.getY() + 1 + i - 2) {
                    k = 2;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
                    for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
                        if (j >= 0 && j < worldIn.getMaxHeight()) {
                            if (!func_214587_a(worldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else if (isSoil(worldIn, position.down(), getSapling()) && position.getY() < worldIn.getMaxHeight() - i - 1) {
                this.setDirtAt(worldIn, position.down(), position);
                int j2 = 3;
                int k2 = 0;

                for (int i3 = 0; i3 < i; ++i3) {
                    if (isAirOrLeaves(worldIn, position.up(i3)) || isTallPlants(worldIn, position.up(i3))) {
                        this.setLogState(changedBlocks, worldIn, position.up(i3), this.trunk, p_208519_5_);
                    }
                }

                for (int l2 = position.getY() - 3 + i; l2 <= position.getY() + i; ++l2) {
                    int l3 = l2 - (position.getY() + i);
                    int j4 = 1 - l3 / 2;

                    for (int j1 = position.getX() - j4; j1 <= position.getX() + j4; ++j1) {
                        int k1 = j1 - position.getX();

                        for (int l1 = position.getZ() - j4; l1 <= position.getZ() + j4; ++l1) {

                            BlockPos blockpos = new BlockPos(j1, l2, l1);
                            if (isAirOrLeaves(worldIn, blockpos) || isTallPlants(worldIn, blockpos)) {
                                this.setLogState(changedBlocks, worldIn, blockpos, this.leaf, p_208519_5_);
                            }

                        }
                    }
                }

                if (this.vinesGrow) {
                    for (int j3 = position.getY() - 3 + i; j3 <= position.getY() + i; ++j3) {
                        int i4 = j3 - (position.getY() + i);
                        int k4 = 2 - i4 / 2;
                        BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

                    }

                }

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    protected int getHeight(Random random) {
        return this.minTreeHeight + random.nextInt(3);
    }

}