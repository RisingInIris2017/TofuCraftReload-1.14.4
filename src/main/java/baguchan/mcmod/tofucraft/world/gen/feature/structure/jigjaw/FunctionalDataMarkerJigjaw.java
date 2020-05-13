package baguchan.mcmod.tofucraft.world.gen.feature.structure.jigjaw;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

public abstract class FunctionalDataMarkerJigjaw extends SingleJigsawPiece {
    public FunctionalDataMarkerJigjaw(String p_i51400_1_, List<StructureProcessor> p_i51400_2_) {
        super(p_i51400_1_, p_i51400_2_);
    }

    public FunctionalDataMarkerJigjaw(String location) {
        this(location, ImmutableList.of());
    }

    @Override
    public boolean func_225575_a_(TemplateManager p_225575_1_, IWorld p_225575_2_, ChunkGenerator<?> p_225575_3_, BlockPos p_225575_4_, Rotation p_225575_5_, MutableBoundingBox p_225575_6_, Random p_225575_7_) {
        Template template = p_225575_1_.getTemplateDefaulted(this.location);
        PlacementSettings placementsettings = this.createPlacementSettings(p_225575_5_, p_225575_6_);
        if (template.addBlocksToWorld(p_225575_2_, p_225575_4_, placementsettings, 2)) {
            for (Template.BlockInfo template$blockinfo : Template.processBlockInfos(template, p_225575_2_, p_225575_4_, placementsettings, this.func_214857_a(p_225575_1_, p_225575_4_, p_225575_5_, false))) {
                this.func_214846_a(p_225575_2_, template$blockinfo, p_225575_4_, p_225575_5_, p_225575_7_, p_225575_6_);
            }

            for (Template.BlockInfo template$blockinfo : template.func_215381_a(p_225575_4_, placementsettings, Blocks.STRUCTURE_BLOCK)) {
                if (template$blockinfo.nbt != null) {
                    StructureMode structuremode = StructureMode.valueOf(template$blockinfo.nbt.getString("mode"));
                    if (structuremode == StructureMode.DATA) {
                        this.handleDataMarker(template$blockinfo.nbt.getString("metadata"), template$blockinfo.pos, p_225575_2_, p_225575_2_.getRandom(), p_225575_6_);
                    }
                }
            }
        }
        return true;
    }

    protected abstract void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb);
}
