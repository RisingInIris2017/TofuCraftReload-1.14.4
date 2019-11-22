package baguchan.mcmod.tofucraft.world.tree;

import baguchan.mcmod.tofucraft.world.gen.feature.TofuTreeFeature;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class TofuTree extends Tree {
    @Nullable
    protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
        return new TofuTreeFeature(NoFeatureConfig::deserialize, false);
    }
}