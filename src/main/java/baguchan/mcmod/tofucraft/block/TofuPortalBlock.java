package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuParticles;
import baguchan.mcmod.tofucraft.world.dimension.TofuWorldTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class TofuPortalBlock extends Block {
    private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    private static final VoxelShape NULL = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

    public TofuPortalBlock(Properties properties) {
        super(properties);
    }

    public boolean trySpawnPortal(World worldIn, BlockPos pos) {

        TofuPortalBlock.Size size = new TofuPortalBlock.Size(worldIn, pos);

        if (size.isValid()) {
            size.placePortalBlocks();
            worldIn.playSound(null, pos, SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.BLOCKS, 0.7F, 1.0F);
            return true;
        } else {
            TofuPortalBlock.Size size1 = new TofuPortalBlock.Size(worldIn, pos);

            if (size1.isValid()) {
                size1.placePortalBlocks();
                worldIn.playSound(null, pos, SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            } else {
                return false;
            }
        }

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(100) == 0) {
            worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i) {
            double d0 = (double) ((float) pos.getX() + rand.nextFloat());
            double d1 = (double) ((float) pos.getY() + rand.nextFloat()) + 0.8D;
            double d2 = (double) ((float) pos.getZ() + rand.nextFloat());
            double d3 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double) rand.nextFloat()) * 0.5D;
            double d5 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            int j = rand.nextInt(2) * 2 - 1;

            worldIn.addParticle(TofuParticles.TOFUPORTAL, false, d0, d1, d2, d3, d4, d5);
        }

    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entity) {
        if (!worldIn.isRemote) {
            MinecraftServer server = worldIn.getServer();

            //TODO Make Go to TofuWorld
            boolean inOverworld = entity.world.func_234923_W_() != World.field_234918_g_;
            if ((!entity.isBeingRidden()) && (entity.getPassengers().isEmpty())) {
                if (entity.timeUntilPortal < 0) {
                    entity.timeUntilPortal = entity.getPortalCooldown();


                    if (inOverworld) {
                        ServerWorld dimWorld = server.getWorld(World.field_234918_g_);
                        TofuWorldTeleporter tofuTeleporter = new TofuWorldTeleporter(dimWorld);
                        if (dimWorld != null) {
                            tofuTeleporter.placeInPortal(entity, entity.rotationYaw);
                            entity.changeDimension(dimWorld, tofuTeleporter);
                        }
                    } else {
                        ServerWorld dimWorld = server.getWorld(World.field_234918_g_);
                        TofuWorldTeleporter tofuTeleporter = new TofuWorldTeleporter(dimWorld);
                        if (dimWorld != null) {
                            tofuTeleporter.placeInPortal(entity, entity.rotationYaw);
                            entity.changeDimension(dimWorld, tofuTeleporter);
                        }
                    }
                } else {
                    entity.timeUntilPortal = entity.getPortalCooldown();
                }
            }
        }
    }


    private Entity teleportEntity(Entity entity, ServerWorld endpointWorld, BlockPos endpoint, boolean tofuworld) {
        if (tofuworld) {
            BlockPos height = entity.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(entity.getPositionVec()));
            endpoint = height;
            entity.setLocationAndAngles(height.getX() + 0.5D, height.getY() + 0.5D, height.getZ() + 0.5D, entity.rotationYaw, 0.0F);

            placeInPortal(entity, endpoint, endpointWorld);
        } else {
            BlockPos height = entity.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(entity.getPositionVec()));
            endpoint = height;
            entity.setLocationAndAngles(height.getX() + 0.5D, height.getY() + 0.5D, height.getZ() + 0.5D, entity.rotationYaw, 0.0F);

        }
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            player.teleport(endpointWorld, endpoint.getX() + 0.5D, endpoint.getY() + 0.5D, endpoint.getZ() + 0.5D, entity.rotationYaw, entity.rotationPitch);
            return player;
        }

        entity.detach();
        entity.func_241206_a_(endpointWorld);
        Entity teleportedEntity = entity.getType().create(endpointWorld);
        if (teleportedEntity == null) {
            return entity;
        }
        teleportedEntity.copyDataFromOld(entity);
        teleportedEntity.setLocationAndAngles(endpoint.getX() + 0.5D, endpoint.getY() + 0.5D, endpoint.getZ() + 0.5D, entity.rotationYaw, entity.rotationPitch);
        teleportedEntity.setRotationYawHead(entity.rotationYaw);
        endpointWorld.addFromAnotherDimension(teleportedEntity);
        return teleportedEntity;
    }

    public void placeInPortal(Entity entity, BlockPos endPoint, ServerWorld serverWorld) {
        entity.setMotion(0, 0, 0);
        TofuWorldTeleporter.createPortal(serverWorld, endPoint, entity);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return NULL;
    }

    public static class Size {
        private static final int MAX_SIZE = 9;
        private static final int MIN_SIZE = 3;

        private final IWorld world;
        private boolean valid = false;
        private BlockPos nw;
        private BlockPos se;

        public Size(IWorld world, BlockPos pos) {
            this.world = world;

            int east = getDistanceUntilEdge(pos, Direction.EAST);
            int west = getDistanceUntilEdge(pos, Direction.WEST);
            int north = getDistanceUntilEdge(pos, Direction.NORTH);
            int south = getDistanceUntilEdge(pos, Direction.SOUTH);

            int width = east + west - 1;
            int length = north + south - 1;

            if (width > Size.MAX_SIZE || length > Size.MAX_SIZE) {
                return;
            }
            if (width < Size.MIN_SIZE || length < Size.MIN_SIZE) {
                return;
            }

            BlockPos neCorner = pos.east(east).north(north);
            BlockPos nwCorner = pos.west(west).north(north);
            BlockPos seCorner = pos.east(east).south(south);
            BlockPos swCorner = pos.west(west).south(south);

            BlockPos eastPos = pos.east(east);
            BlockPos westPos = pos.west(west);
            BlockPos southPos = pos.south(south);
            BlockPos northPos = pos.north(north);

            BlockPos eastPos2 = pos.east();
            BlockPos westPos2 = pos.west();
            BlockPos southPos2 = pos.south();
            BlockPos northPos2 = pos.north();

            this.nw = nwCorner.add(1, 0, 1);
            this.se = seCorner.add(-1, 0, -1);
            int wallWidth = width + 2;
            int wallLength = length + 2;

            for (int y = 0; y <= 1; y++) {
                for (int x = 0; x < wallWidth; x++) {
                    for (int z = 0; z < wallLength; z++) {
                        if (y == 0 || x == 0 || z == 0 || x == wallWidth - 1 || z == wallLength - 1) {
                            if (!isTofuBlock(world.getBlockState(nwCorner.down().add(x, y, z)))) {
                                return;
                            }
                        }
                    }
                }
            }
            this.valid = true;
        }

        int getDistanceUntilEdge(BlockPos pos, Direction facing) {
            int i;

            for (i = 0; i < 9; ++i) {
                BlockPos blockpos = pos.offset(facing, i);

                if (!this.isEmptyBlock(this.world.getBlockState(blockpos)) || !isTofuBlock(this.world.getBlockState(blockpos.down()))) {
                    break;
                }
            }

            BlockState state = this.world.getBlockState(pos.offset(facing, i));
            return isTofuBlock(state) ? i : 0;
        }

        boolean isEmptyBlock(BlockState state) {
            return state.getBlock() == TofuBlocks.SOYMILK;
        }

        boolean isTofuBlock(BlockState state) {
            return state.getBlock() == TofuBlocks.GRILLEDTOFU;
        }

        boolean isValid() {
            return this.valid;
        }

        void placePortalBlocks() {
            for (BlockPos portalPos : BlockPos.Mutable.getAllInBoxMutable(nw, se)) {
                this.world.setBlockState(portalPos, TofuBlocks.TOFUPORTAL.getDefaultState(), 2);
            }
        }
    }
}