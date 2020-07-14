package baguchan.mcmod.tofucraft.world.gen.feature;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class SurfaceTofuTemplateFeature extends Feature<NoFeatureConfig> {
    protected final int offsetX;
    protected final int offsetZ;
    private final ResourceLocation[] TEMPLATE;


    public SurfaceTofuTemplateFeature(Codec<NoFeatureConfig> p_i231955_1_, int offsetX, int offsetZ, ResourceLocation[] resourceLocations) {
        super(p_i231955_1_);
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
        this.TEMPLATE = resourceLocations;
    }

    public boolean func_230362_a_(ISeedReader world, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos pos, NoFeatureConfig p_230362_6_) {
        Rotation rotation = Rotation.randomRotation(p_230362_4_);
        int i = p_230362_4_.nextInt(TEMPLATE.length);
        TemplateManager templatemanager = ((ServerWorld) world.getWorld()).getServer().func_240792_aT_();
        Template template = templatemanager.getTemplateDefaulted(TEMPLATE[i]);
        ChunkPos chunkpos = new ChunkPos(pos);
        MutableBoundingBox mutableboundingbox = new MutableBoundingBox(chunkpos.getXStart(), 0, chunkpos.getZStart(), chunkpos.getXEnd(), 256, chunkpos.getZEnd());
        PlacementSettings placementsettings = (new PlacementSettings()).setRotation(rotation).setBoundingBox(mutableboundingbox).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
        BlockPos blockpos = Template.transformedBlockPos(placementsettings, new BlockPos(offsetX / 2, 0, offsetZ / 2));

        BlockPos blockpos1 = pos.add(blockpos.getX(), 0, blockpos.getZ());

        if (!isSoil(world, blockpos1.down()) || !world.isAirBlock(blockpos1)) {
            return false;
        }

        BlockPos blockpos2 = Template.transformedBlockPos(placementsettings, blockpos1.add(-offsetX / 2, 0, -offsetZ / 2));
        BlockPos blockpos3 = Template.transformedBlockPos(placementsettings, blockpos1.add(offsetX / 2, 0, offsetZ / 2));


        template.func_237146_a_(world, blockpos2, blockpos3, placementsettings, p_230362_4_, 4);
        return true;
    }

    private boolean isSoil(IWorld worldIn, BlockPos down) {
        return worldIn.getBlockState(down).getBlock() == TofuBlocks.TOFUTERRAIN || worldIn.getBlockState(down).getBlock() == TofuBlocks.ZUNDATOFUTERRAIN;
    }
}