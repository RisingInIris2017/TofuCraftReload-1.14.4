package baguchan.mcmod.tofucraft.block;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuDimensions;
import baguchan.mcmod.tofucraft.world.dimension.TofuWorldTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
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

       /* for (int i = 0; i < 4; ++i) {
            double d0 = (double) ((float) pos.getX() + rand.nextFloat());
            double d1 = (double) ((float) pos.getY() + rand.nextFloat()) + 0.8D;
            double d2 = (double) ((float) pos.getZ() + rand.nextFloat());
            double d3 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double) rand.nextFloat()) * 0.5D;
            double d5 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
            int j = rand.nextInt(2) * 2 - 1;

            worldIn.addParticle(TofuParticles.TOFUPORTAL, false, d0, d1, d2, d3, d4, d5);
        }*/

    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityCollision(state, worldIn, pos, entityIn);
        if (!worldIn.isRemote && !entityIn.isPassenger() && !entityIn.isBeingRidden() && entityIn.isNonBoss()) {


            MinecraftServer server = worldIn.getServer();

            if (entityIn.dimension.getModType() == TofuDimensions.TOFUWORLD) {
                if (server != null && entityIn.timeUntilPortal <= 0) {


                    PlayerList playerList = server.getPlayerList();


                    DimensionType warptype = DimensionType.OVERWORLD;


                    TofuWorldTeleporter teleporter = new TofuWorldTeleporter(server.getWorld(warptype));


                    entityIn.timeUntilPortal = entityIn.getPortalCooldown();


                    if (entityIn.timeUntilPortal > 0) {


                        entityIn.timeUntilPortal = entityIn.getPortalCooldown();


                    }


                    if (entityIn instanceof ServerPlayerEntity) {

                        this.changePlayerDimension(warptype, (ServerPlayerEntity) entityIn);

                        entityIn.timeUntilPortal = entityIn.getPortalCooldown();
                    } else {
                        DimensionType origin = entityIn.dimension;

                        this.changeDimension(warptype, entityIn);
                        entityIn.timeUntilPortal = entityIn.getPortalCooldown();
                    }

                } else {
                    entityIn.timeUntilPortal = Math.max(entityIn.getPortalCooldown(), 100);
                }
            } else {
                if (server != null && entityIn.timeUntilPortal <= 0) {


                    PlayerList playerList = server.getPlayerList();


                    DimensionType warptype = DimensionType.byName(TofuDimensions.TOFUWORLD.getRegistryName());

                    TofuWorldTeleporter teleporter = new TofuWorldTeleporter(server.getWorld(warptype));


                    entityIn.timeUntilPortal = entityIn.getPortalCooldown();


                    if (entityIn.timeUntilPortal > 0) {


                        entityIn.timeUntilPortal = entityIn.getPortalCooldown();


                    }

                    if (entityIn instanceof ServerPlayerEntity) {

                        this.changePlayerDimension(warptype, (ServerPlayerEntity) entityIn);

                        entityIn.timeUntilPortal = entityIn.getPortalCooldown();
                    } else {
                        DimensionType origin = entityIn.dimension;

                        this.changeDimension(warptype, entityIn);
                        entityIn.timeUntilPortal = entityIn.getPortalCooldown();
                    }


                } else {
                    entityIn.timeUntilPortal = Math.max(entityIn.getPortalCooldown(), 100);
                }
            }
        }
    }

    public Entity changePlayerDimension(DimensionType destination, ServerPlayerEntity serverPlayerEntity) {
        if (!net.minecraftforge.common.ForgeHooks.onTravelToDimension(serverPlayerEntity, destination)) return null;
        serverPlayerEntity.invulnerableDimensionChange = true;
        DimensionType dimensiontype = serverPlayerEntity.dimension;

        ServerWorld serverworld = serverPlayerEntity.server.getWorld(dimensiontype);
        serverPlayerEntity.dimension = destination;
        ServerWorld serverworld1 = serverPlayerEntity.server.getWorld(destination);
        WorldInfo worldinfo = serverworld1.getWorldInfo();
        net.minecraftforge.fml.network.NetworkHooks.sendDimensionDataPacket(serverPlayerEntity.connection.netManager, serverPlayerEntity);
        serverPlayerEntity.connection.sendPacket(new SRespawnPacket(destination, WorldInfo.byHashing(worldinfo.getSeed()), worldinfo.getGenerator(), serverPlayerEntity.interactionManager.getGameType()));
        serverPlayerEntity.connection.sendPacket(new SServerDifficultyPacket(worldinfo.getDifficulty(), worldinfo.isDifficultyLocked()));
        PlayerList playerlist = serverPlayerEntity.server.getPlayerList();
        playerlist.updatePermissionLevel(serverPlayerEntity);
        serverworld.removeEntity(serverPlayerEntity, true); //Forge: the player entity is moved to the new world, NOT cloned. So keep the data alive with no matching invalidate call.
        serverPlayerEntity.revive();
        TofuWorldTeleporter tofuteleporter = new TofuWorldTeleporter(serverworld1);

        Entity e = tofuteleporter.placeEntity(serverPlayerEntity, serverworld, serverworld1, serverPlayerEntity.rotationYaw, spawnPortal -> {//Forge: Start vanilla logic
            double d0 = serverPlayerEntity.getPosX();
            double d1 = serverPlayerEntity.getPosY();
            double d2 = serverPlayerEntity.getPosZ();
            float f = serverPlayerEntity.rotationPitch;
            float f1 = serverPlayerEntity.rotationYaw;
            double d3 = 8.0D;
            float f2 = f1;
            serverworld.getProfiler().startSection("moving");
            double moveFactor = serverworld.getDimension().getMovementFactor() / serverworld1.getDimension().getMovementFactor();
            d0 *= moveFactor;
            d2 *= moveFactor;

            serverPlayerEntity.setLocationAndAngles(d0, d1, d2, f1, f);
            serverworld.getProfiler().endSection();
            serverworld.getProfiler().startSection("placing");
            double d7 = Math.min(-2.9999872E7D, serverworld1.getWorldBorder().minX() + 16.0D);
            double d4 = Math.min(-2.9999872E7D, serverworld1.getWorldBorder().minZ() + 16.0D);
            double d5 = Math.min(2.9999872E7D, serverworld1.getWorldBorder().maxX() - 16.0D);
            double d6 = Math.min(2.9999872E7D, serverworld1.getWorldBorder().maxZ() - 16.0D);
            d0 = MathHelper.clamp(d0, d7, d5);
            d2 = MathHelper.clamp(d2, d4, d6);
            serverPlayerEntity.setLocationAndAngles(d0, d1, d2, f1, f);
            if (spawnPortal && !tofuteleporter.placeInPortal(serverPlayerEntity, f2)) {
                tofuteleporter.makePortal(serverPlayerEntity);
                tofuteleporter.placeInPortal(serverPlayerEntity, f2);
            }

            serverworld.getProfiler().endSection();
            serverPlayerEntity.setWorld(serverworld1);
            serverworld1.addDuringPortalTeleport(serverPlayerEntity);
            serverPlayerEntity.connection.setPlayerLocation(serverPlayerEntity.getPosX(), serverPlayerEntity.getPosY(), serverPlayerEntity.getPosZ(), f1, f);
            return serverPlayerEntity;//forge: this is part of the ITeleporter patch
        });//Forge: End vanilla logic
        if (e != serverPlayerEntity)
            throw new java.lang.IllegalArgumentException(String.format("Teleporter %s returned not the player entity but instead %s, expected PlayerEntity %s", tofuteleporter, e, this));
        serverPlayerEntity.interactionManager.setWorld(serverworld1);
        serverPlayerEntity.connection.sendPacket(new SPlayerAbilitiesPacket(serverPlayerEntity.abilities));
        playerlist.sendWorldInfo(serverPlayerEntity, serverworld1);
        playerlist.sendInventory(serverPlayerEntity);

        for (EffectInstance effectinstance : serverPlayerEntity.getActivePotionEffects()) {
            serverPlayerEntity.connection.sendPacket(new SPlayEntityEffectPacket(serverPlayerEntity.getEntityId(), effectinstance));
        }

        serverPlayerEntity.connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
        serverPlayerEntity.lastExperience = -1;
        serverPlayerEntity.lastHealth = -1.0F;
        serverPlayerEntity.lastFoodLevel = -1;
        net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerChangedDimensionEvent(serverPlayerEntity, dimensiontype, destination);
        return serverPlayerEntity;

    }

    @Nullable
    public Entity changeDimension(DimensionType destination, Entity entity) {
        if (!entity.world.isRemote && !entity.removed) {
            entity.world.getProfiler().startSection("changeDimension");
            MinecraftServer minecraftserver = entity.getServer();
            DimensionType dimensiontype = entity.dimension;
            ServerWorld serverworld = minecraftserver.getWorld(dimensiontype);
            ServerWorld serverworld1 = minecraftserver.getWorld(destination);

            TofuWorldTeleporter tofuteleporter = new TofuWorldTeleporter(serverworld1);
            entity.dimension = destination;
            entity.detach();
            entity.world.getProfiler().startSection("reposition");
            Vec3d vec3d = entity.getMotion();
            float f = 0.0F;

            BlockPos blockpos;

            double movementFactor = serverworld.getDimension().getMovementFactor() / serverworld1.getDimension().getMovementFactor();
            double d0 = entity.getPosX() * movementFactor;
            double d1 = entity.getPosZ() * movementFactor;

            double d3 = Math.min(-2.9999872E7D, serverworld1.getWorldBorder().minX() + 16.0D);
            double d4 = Math.min(-2.9999872E7D, serverworld1.getWorldBorder().minZ() + 16.0D);
            double d5 = Math.min(2.9999872E7D, serverworld1.getWorldBorder().maxX() - 16.0D);
            double d6 = Math.min(2.9999872E7D, serverworld1.getWorldBorder().maxZ() - 16.0D);
            d0 = MathHelper.clamp(d0, d3, d5);
            d1 = MathHelper.clamp(d1, d4, d6);
            Vec3d vec3d1 = entity.getLastPortalVec();
            blockpos = new BlockPos(d0, entity.getPosY(), d1);


            entity.world.getProfiler().endStartSection("reloading");
            Entity entity2 = entity.getType().create(serverworld1);
            if (entity2 != null) {
                entity2.copyDataFromOld(entity);
                tofuteleporter.placeInPortal(entity2, entity2.rotationYaw);
                entity2.setMotion(vec3d);
                serverworld1.addFromAnotherDimension(entity2);
                entity.remove();
            }

            entity2.world.getProfiler().endSection();
            serverworld.resetUpdateEntityTick();
            serverworld1.resetUpdateEntityTick();
            entity2.world.getProfiler().endSection();
            return entity2;
        } else {
            return null;
        }
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
                            if (!isSnowBlock(world.getBlockState(nwCorner.down().add(x, y, z)))) {
                                return;
                            }
                        }
                    }
                }
            }

            /*for (int y = 0; y < 2; y++) {
                if (!isSnowBlock(world.getBlockState(eastPos.add(0, y + 1, 0)))) {

                    return;
                }
                if (!isSnowBlock(world.getBlockState(westPos.add(0, y + 1, 0)))) {

                    return;
                }
                if (!isSnowBlock(world.getBlockState(southPos.add(0, y + 1, 0)))) {

                    return;
                }
                if (!isSnowBlock(world.getBlockState(northPos.add(0, y + 1, 0)))) {

                    return;
                }
            }

            if (!isSnowBlock(world.getBlockState(eastPos2.add(0, 3, 0)))) {

                return;
            }
            if (!isSnowBlock(world.getBlockState(westPos2.add(0, 3, 0)))) {

                return;
            }
            if (!isSnowBlock(world.getBlockState(southPos2.add(0, 3, 0)))) {

                return;
            }
            if (!isSnowBlock(world.getBlockState(northPos2.add(0, 3, 0)))) {

                return;
            }*/

            this.valid = true;
        }

        int getDistanceUntilEdge(BlockPos pos, Direction facing) {
            int i;

            for (i = 0; i < 9; ++i) {
                BlockPos blockpos = pos.offset(facing, i);

                if (!this.isEmptyBlock(this.world.getBlockState(blockpos)) || !isSnowBlock(this.world.getBlockState(blockpos.down()))) {
                    break;
                }
            }

            BlockState state = this.world.getBlockState(pos.offset(facing, i));
            return isSnowBlock(state) ? i : 0;
        }

        boolean isEmptyBlock(BlockState state) {
            return state.getBlock() == TofuBlocks.SOYMILK;
        }

        boolean isSnowBlock(BlockState state) {
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