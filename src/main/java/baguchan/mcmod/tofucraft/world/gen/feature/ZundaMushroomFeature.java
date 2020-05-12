package baguchan.mcmod.tofucraft.world.gen.feature;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuTags;
import baguchan.mcmod.tofucraft.world.AbstractWrappedWorld;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Set;
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


    protected boolean place(IWorld world, Random random, BlockPos origin, MutableBoundingBox bounds) {
        Rotation[] arotation = Rotation.values();
        Rotation rotation = arotation[random.nextInt(arotation.length)];

        TemplateManager templatemanager = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureTemplateManager();

        Template template = templatemanager.getTemplateDefaulted(templates[random.nextInt(templates.length)]);

        PlacementSettings placementsettings = (new PlacementSettings()).setRotation(rotation).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);

        BlockPos blockpos = template.transformedBlockPos(placementsettings, new BlockPos(-offsetX / 2, 0, -offsetZ / 2));

        if (!isSoil(world, origin.down()) || !world.isAirBlock(origin)) {
            return false;
        }

        template.addBlocksToWorld(world, origin.add(blockpos), placementsettings, 2 | 16);

        return true;
    }

    protected final boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader world, Random random, BlockPos origin, MutableBoundingBox bounds) {

        WorldWrapper wrapper = new WorldWrapper((IWorld) world, bounds);

        boolean result = this.place(wrapper, random, origin, bounds);

        if (bounds.minX > bounds.maxX) {

            bounds.minX = bounds.minY = bounds.minZ = bounds.maxX = bounds.maxY = bounds.maxZ = 0;

        }

        return result;
    }

    public final boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        Set<BlockPos> set = Sets.newHashSet();
        MutableBoundingBox mutableboundingbox = MutableBoundingBox.getNewBoundingBox();
        boolean flag = this.place(set, worldIn, rand, pos, mutableboundingbox);
        if (mutableboundingbox.minX <= mutableboundingbox.maxX) {
            List<Set<BlockPos>> list = Lists.newArrayList();
            int i = 6;

            for (int j = 0; j < 6; ++j) {
                list.add(Sets.newHashSet());
            }

            VoxelShapePart voxelshapepart = new BitSetVoxelShapePart(mutableboundingbox.getXSize(), mutableboundingbox.getYSize(), mutableboundingbox.getZSize());

            try (BlockPos.PooledMutable blockpos$pooledmutableblockpos = BlockPos.PooledMutable.retain()) {
                if (flag && !set.isEmpty()) {
                    for (BlockPos blockpos : Lists.newArrayList(set)) {
                        if (mutableboundingbox.isVecInside(blockpos)) {
                            voxelshapepart.setFilled(blockpos.getX() - mutableboundingbox.minX, blockpos.getY() - mutableboundingbox.minY, blockpos.getZ() - mutableboundingbox.minZ, true, true);
                        }

                        for (Direction direction : Direction.values()) {
                            blockpos$pooledmutableblockpos.setPos(blockpos).move(direction);
                            if (!set.contains(blockpos$pooledmutableblockpos)) {
                                BlockState blockstate = worldIn.getBlockState(blockpos$pooledmutableblockpos);
                                if (blockstate.has(BlockStateProperties.DISTANCE_1_7)) {
                                    list.get(0).add(blockpos$pooledmutableblockpos.toImmutable());
                                    this.func_208521_b(worldIn, blockpos$pooledmutableblockpos, blockstate.with(BlockStateProperties.DISTANCE_1_7, Integer.valueOf(1)));
                                    if (mutableboundingbox.isVecInside(blockpos$pooledmutableblockpos)) {
                                        voxelshapepart.setFilled(blockpos$pooledmutableblockpos.getX() - mutableboundingbox.minX, blockpos$pooledmutableblockpos.getY() - mutableboundingbox.minY, blockpos$pooledmutableblockpos.getZ() - mutableboundingbox.minZ, true, true);
                                    }
                                }
                            }
                        }
                    }
                }

                for (int l = 1; l < 6; ++l) {
                    Set<BlockPos> set1 = list.get(l - 1);
                    Set<BlockPos> set2 = list.get(l);

                    for (BlockPos blockpos1 : set1) {
                        if (mutableboundingbox.isVecInside(blockpos1)) {
                            voxelshapepart.setFilled(blockpos1.getX() - mutableboundingbox.minX, blockpos1.getY() - mutableboundingbox.minY, blockpos1.getZ() - mutableboundingbox.minZ, true, true);
                        }

                        for (Direction direction1 : Direction.values()) {
                            blockpos$pooledmutableblockpos.setPos(blockpos1).move(direction1);
                            if (!set1.contains(blockpos$pooledmutableblockpos) && !set2.contains(blockpos$pooledmutableblockpos)) {
                                BlockState blockstate1 = worldIn.getBlockState(blockpos$pooledmutableblockpos);
                                if (blockstate1.has(BlockStateProperties.DISTANCE_1_7)) {
                                    int k = blockstate1.get(BlockStateProperties.DISTANCE_1_7);
                                    if (k > l + 1) {
                                        BlockState blockstate2 = blockstate1.with(BlockStateProperties.DISTANCE_1_7, Integer.valueOf(l + 1));
                                        this.func_208521_b(worldIn, blockpos$pooledmutableblockpos, blockstate2);
                                        if (mutableboundingbox.isVecInside(blockpos$pooledmutableblockpos)) {
                                            voxelshapepart.setFilled(blockpos$pooledmutableblockpos.getX() - mutableboundingbox.minX, blockpos$pooledmutableblockpos.getY() - mutableboundingbox.minY, blockpos$pooledmutableblockpos.getZ() - mutableboundingbox.minZ, true, true);
                                        }

                                        set2.add(blockpos$pooledmutableblockpos.toImmutable());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Template.func_222857_a(worldIn, 3, voxelshapepart, mutableboundingbox.minX, mutableboundingbox.minY, mutableboundingbox.minZ);
            return true;
        } else {
            return false;
        }
    }

    protected boolean canGrow(IWorld world, BlockPos minCorner, BlockPos maxCorner) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();

        for (BlockPos pos : BlockPos.getAllInBoxMutable(minCorner, maxCorner)) {
            mutablePos.setPos(pos);
            mutablePos.move(Direction.DOWN);

            int depth = 0;
            while (isAirOrLeaves(world, mutablePos)) {
                mutablePos.move(Direction.DOWN);
                if (depth++ >= 3) {
                    return false;
                }
            }

            if (!isSoil(world, mutablePos)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAirOrLeaves(IWorldGenerationBaseReader worldIn, BlockPos pos) {
        if (worldIn instanceof net.minecraft.world.IWorldReader) // FORGE: Redirect to state method when possible
            return worldIn.hasBlockState(pos, state -> state.canBeReplacedByLeaves((net.minecraft.world.IWorldReader) worldIn, pos));
        return worldIn.hasBlockState(pos, (p_227223_0_) -> {
            return p_227223_0_.isAir() || p_227223_0_.isIn(BlockTags.LEAVES);
        });
    }

    protected boolean canFit(IWorld world, BlockPos trunkTop, BlockPos minCorner, BlockPos maxCorner) {
        BlockPos maxFit = new BlockPos(maxCorner.getX(), trunkTop.getY(), maxCorner.getZ());

        for (BlockPos pos : BlockPos.getAllInBoxMutable(minCorner, maxFit)) {
            if (!canGrowInto(world, pos)) {
                return false;
            }
        }

        return true;
    }

    protected static boolean canGrowInto(IWorld world, BlockPos pos) {
        return world.hasBlockState(pos, state -> {
            return state.isAir(world, pos)
                    || state.isIn(TofuTags.Blocks.TOFUTERRAIN)
                    || !state.isOpaqueCube(world, pos);
        });
    }

    protected void setBlockState(IWorldWriter worldIn, BlockPos pos, BlockState state) {
        this.func_208521_b(worldIn, pos, state);
    }

    protected final void func_227217_a_(IWorldWriter p_227217_1_, BlockPos p_227217_2_, BlockState p_227217_3_, MutableBoundingBox p_227217_4_) {
        this.func_208521_b(p_227217_1_, p_227217_2_, p_227217_3_);
        p_227217_4_.expandTo(new MutableBoundingBox(p_227217_2_, p_227217_2_));
    }

    private void func_208521_b(IWorldWriter p_208521_1_, BlockPos p_208521_2_, BlockState p_208521_3_) {
        p_208521_1_.setBlockState(p_208521_2_, p_208521_3_, 19);
    }

    private boolean isSoil(IWorld worldIn, BlockPos down) {
        return worldIn.getBlockState(down).getBlock() == TofuBlocks.TOFUTERRAIN || worldIn.getBlockState(down).getBlock() == TofuBlocks.ZUNDATOFUTERRAIN;
    }

    private static class WorldWrapper extends AbstractWrappedWorld {
        private final MutableBoundingBox bounds;

        private WorldWrapper(IWorld world, MutableBoundingBox bounds) {
            super(world);
            this.bounds = bounds;
        }

        @Override
        public boolean setBlockState(BlockPos pos, BlockState state, int flags) {
            boolean set = super.setBlockState(pos, state, flags);
            if (set) {
                this.bounds.expandTo(new MutableBoundingBox(pos, pos));
            }
            return set;
        }

        @Override
        public boolean removeBlock(BlockPos pos, boolean b) {
            if (super.removeBlock(pos, b)) {
                return true;
            }
            return false;
        }

        @Override
        public boolean destroyBlock(BlockPos pos, boolean b) {
            if (super.destroyBlock(pos, b)) {
                return true;
            }
            return false;
        }

        @Override
        public boolean destroyBlock(BlockPos blockPos, boolean b, @Nullable Entity entity) {
            if (super.destroyBlock(blockPos, b)) {
                return true;
            }
            return false;
        }

        @Override
        public BiomeManager getBiomeManager() {
            return null;
        }

        @Override
        public Biome getNoiseBiomeRaw(int x, int y, int z) {
            return null;
        }

        @Override
        public WorldLightManager getLightManager() {
            return null;
        }
    }
}
