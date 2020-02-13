package baguchan.mcmod.tofucraft.world.tree;

import baguchan.mcmod.tofucraft.world.gen.DefaultTofuBiomeFeature;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class ZundaTofuTree extends Tree {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random p_225546_1_, boolean p_225546_2_) {
        return Feature.NORMAL_TREE.withConfiguration(DefaultTofuBiomeFeature.tofuZundaTree);
    }
}