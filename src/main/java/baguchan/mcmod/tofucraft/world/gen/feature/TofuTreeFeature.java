package baguchan.mcmod.tofucraft.world.gen.feature;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class TofuTreeFeature extends AbstractTreeFeature<TreeFeatureConfig> {
    private static final BlockState DEFAULT_TRUNK = TofuBlocks.ISHITOFU.getDefaultState();
    private static final BlockState DEFAULT_LEAF = TofuBlocks.TOFULEAVES.getDefaultState();
    protected final int minTreeHeight;
    private final boolean vinesGrow;
    private final BlockState trunk;
    private final BlockState leaf;

    public TofuTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> configFactoryIn, boolean doBlockNotifyOnPlace) {
        this(configFactoryIn, doBlockNotifyOnPlace, 4, DEFAULT_TRUNK, DEFAULT_LEAF, false);
    }

    public TofuTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> configFactoryIn, boolean doBlockNotifyOnPlace, int minTreeHeightIn, BlockState trunkState, BlockState leafState, boolean vinesGrowIn) {
        super(configFactoryIn);
        this.minTreeHeight = minTreeHeightIn;
        this.trunk = trunkState;
        this.leaf = leafState;
        this.vinesGrow = vinesGrowIn;
        //this.setSapling((net.minecraftforge.common.IPlantable) TofuBlocks.TOFUSAPLING);
    }


    protected int getHeight(Random random) {
        return this.minTreeHeight + random.nextInt(3);
    }

    @Override
    protected boolean func_225557_a_(IWorldGenerationReader p_225557_1_, Random p_225557_2_, BlockPos p_225557_3_, Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_, MutableBoundingBox p_225557_6_, TreeFeatureConfig p_225557_7_) {
        return false;
    }
}