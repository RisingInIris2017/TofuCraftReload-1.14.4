package baguchan.mcmod.tofucraft.world.gen.feature;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Function;

public class ZundaMushroomFeature extends Feature<NoFeatureConfig> {
    protected final int offsetX;
    protected final int offsetZ;
    protected final ResourceLocation[] templates;

    public ZundaMushroomFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn, int offsetX, int offsetZ, ResourceLocation[] resourceLocations) {
        super(configFactoryIn);
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
        this.templates = resourceLocations;
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        Random random = worldIn.getRandom();
        Rotation[] arotation = Rotation.values();
        Rotation rotation = arotation[random.nextInt(arotation.length)];

        if (!isSoil(worldIn, pos.down())) {
            return false;
        }

        TemplateManager templatemanager = ((ServerWorld) worldIn.getWorld()).getSaveHandler().getStructureTemplateManager();

        Template template = templatemanager.getTemplateDefaulted(templates[random.nextInt(templates.length)]);
        ChunkPos chunkpos = new ChunkPos(pos);
        MutableBoundingBox mutableboundingbox = new MutableBoundingBox(chunkpos.getXStart(), 0, chunkpos.getZStart(), chunkpos.getXEnd(), 256, chunkpos.getZEnd());

        PlacementSettings placementsettings = (new PlacementSettings()).setRotation(rotation).setBoundingBox(mutableboundingbox).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);

        BlockPos blockpos = template.transformedBlockPos(placementsettings, new BlockPos(offsetX, 0, offsetZ));

        template.addBlocksToWorld(worldIn, pos.add(blockpos), placementsettings, 4);

        return true;
    }

    private boolean isSoil(IWorld worldIn, BlockPos down) {
        return worldIn.getBlockState(down).getBlock() == TofuBlocks.TOFUTERRAIN;
    }
}
