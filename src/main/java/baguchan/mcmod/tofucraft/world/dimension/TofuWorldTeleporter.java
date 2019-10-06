package baguchan.mcmod.tofucraft.world.dimension;

import baguchan.mcmod.tofucraft.block.TofuPortalBlock;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ColumnPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

public class TofuWorldTeleporter extends Teleporter {
    private static final TofuPortalBlock BLOCK_TOFUPORTAL = (TofuPortalBlock) TofuBlocks.TOFUPORTAL;
    private final Object2LongMap<ColumnPos> field_222275_f = new Object2LongOpenHashMap<>();

    protected final Map<ColumnPos, TofuWorldTeleporter.PortalPosition> destinationCoordinateCache = Maps.newHashMapWithExpectedSize(4096);

    public TofuWorldTeleporter(ServerWorld worldServer) {
        super(worldServer);
    }


    public boolean func_222268_a(Entity entity, float rotationYaw) {
        ColumnPos columnpos = new ColumnPos(entity.getPosition());

        double distance = -1.0D;
        boolean doesPortalExist = true;
        BlockPos location = BlockPos.ZERO;

        if (this.destinationCoordinateCache.containsKey(columnpos)) {
            final PortalPosition portalPosition = this.destinationCoordinateCache.get(columnpos);
            distance = 0.0D;
            location = portalPosition.field_222267_a;
            portalPosition.lastUpdateTime = this.world.getGameTime();
            doesPortalExist = false;
        } else {
            final BlockPos entityPos = new BlockPos(entity);
            for (int offsetX = -128; offsetX <= 128; ++offsetX) {
                BlockPos positionCache;

                for (int offsetZ = -128; offsetZ <= 128; ++offsetZ) {

                    for (BlockPos currentPos = entityPos.add(offsetX, this.world.getActualHeight() - 1 - entityPos.getY(), offsetZ); currentPos.getY() >= 0; currentPos = positionCache) {
                        positionCache = currentPos.down();
                        if (this.world.getBlockState(currentPos).getBlock() == TofuBlocks.TOFUPORTAL) {
                            while (this.world.getBlockState(positionCache = currentPos.down()).getBlock() == TofuBlocks.TOFUPORTAL) {
                                currentPos = positionCache;
                            }
                            final double distanceToEntity = currentPos.distanceSq(entityPos);

                            if (distance < 0.0D || distanceToEntity < distance) {
                                distance = distanceToEntity;
                                location = currentPos;
                            }
                        }
                    }
                }
            }
        }

        if (distance >= 0.0D) {
            if (doesPortalExist) {
                this.destinationCoordinateCache.put(columnpos, new PortalPosition(location, this.world.getWorld().getGameTime()));
            }

            double tpX = location.getX() + 0.5D;
            double tpY = location.getY() + 0.5D;
            double tpZ = location.getZ() + 0.5D;
            Direction direction = null;

            if (this.world.getBlockState(location.west()).getBlock() == TofuBlocks.TOFUPORTAL) {
                direction = Direction.NORTH;
            }

            if (this.world.getBlockState(location.east()).getBlock() == TofuBlocks.TOFUPORTAL) {
                direction = Direction.SOUTH;
            }

            if (this.world.getBlockState(location.north()).getBlock() == TofuBlocks.TOFUPORTAL) {
                direction = Direction.EAST;
            }

            if (this.world.getBlockState(location.south()).getBlock() == TofuBlocks.TOFUPORTAL) {
                direction = Direction.WEST;
            }

            final Direction enumfacing1 = Direction.byHorizontalIndex(MathHelper.floor(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3);

            if (direction != null) {
                Direction enumfacing2 = direction.rotateYCCW();
                final BlockPos blockpos2 = location.offset(direction);
                boolean flag2 = this.isInsideBlock(blockpos2);
                boolean flag3 = this.isInsideBlock(blockpos2.offset(enumfacing2));

                if (flag3 && flag2) {
                    location = location.offset(enumfacing2);
                    direction = direction.getOpposite();
                    enumfacing2 = enumfacing2.getOpposite();
                    final BlockPos blockpos3 = location.offset(direction);
                    flag2 = this.isInsideBlock(blockpos3);
                    flag3 = this.isInsideBlock(blockpos3.offset(enumfacing2));
                }

                float f6 = 0.5F;
                float f1 = 0.5F;

                if (!flag3 && flag2) {
                    f6 = 1.0F;
                } else if (flag3 && !flag2) {
                    f6 = 0.0F;
                } else if (flag3) {
                    f1 = 0.0F;
                }

                tpX = location.getX() + 0.5D;
                tpY = location.getY() + 0.5D;
                tpZ = location.getZ() + 0.5D;
                tpX += enumfacing2.getXOffset() * f6 + direction.getXOffset() * f1;
                tpZ += enumfacing2.getYOffset() * f6 + direction.getYOffset() * f1;
                float f2 = 0.0F;
                float f3 = 0.0F;
                float f4 = 0.0F;
                float f5 = 0.0F;

                if (direction == enumfacing1) {
                    f2 = 1.0F;
                    f3 = 1.0F;
                } else if (direction == enumfacing1.getOpposite()) {
                    f2 = -1.0F;
                    f3 = -1.0F;
                } else if (direction == enumfacing1.rotateY()) {
                    f4 = 1.0F;
                    f5 = -1.0F;
                } else {
                    f4 = -1.0F;
                    f5 = 1.0F;
                }

                final double d2 = entity.getMotion().getX();
                final double d3 = entity.getMotion().getZ();
                entity.setMotion(d2 * f2 + d3 * f5, 0.0F, d2 * f4 + d3 * f3);
                entity.rotationYaw = rotationYaw - enumfacing1.getHorizontalIndex() * 90 + direction.getHorizontalIndex() * 90;
            } else {
                entity.setMotion(0.0F, 0.0F, 0.0F);
            }

            if (entity instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) entity).connection.setPlayerLocation(tpX, tpY, tpZ, entity.rotationYaw, entity.rotationPitch);
                ((ServerPlayerEntity) entity).connection.captureCurrentPosition();
            } else {
                entity.setLocationAndAngles(tpX, tpY, tpZ, entity.rotationYaw, entity.rotationPitch);
            }
            return true;
        } else {
            return false;
        }
    }

    /*@Nullable
    public BlockPattern.PortalInfo func_222272_a(BlockPos p_222272_1_, Vec3d p_222272_2_, Direction p_222272_3_, double p_222272_4_, double p_222272_6_, boolean p_222272_8_) {
        int i = 128;
        boolean flag = true;
        BlockPos blockpos = null;
        ColumnPos columnpos = new ColumnPos(p_222272_1_);
        if (!p_222272_8_ && this.field_222275_f.containsKey(columnpos)) {
            return null;
        } else {
            TofuWorldTeleporter.PortalPosition teleporter$portalposition = this.destinationCoordinateCache.get(columnpos);
            if (teleporter$portalposition != null) {
                blockpos = teleporter$portalposition.field_222267_a;
                teleporter$portalposition.lastUpdateTime = this.world.getGameTime();
                flag = false;
            } else {
                double d0 = Double.MAX_VALUE;

                for(int j = -128; j <= 128; ++j) {
                    BlockPos blockpos2;
                    for(int k = -128; k <= 128; ++k) {
                        for(BlockPos blockpos1 = p_222272_1_.add(j, this.world.getActualHeight() - 1 - p_222272_1_.getY(), k); blockpos1.getY() >= 0; blockpos1 = blockpos2) {
                            blockpos2 = blockpos1.down();
                            if (this.world.getBlockState(blockpos1).getBlock() == BLOCK_TOFUPORTAL) {
                                for(blockpos2 = blockpos1.down(); this.world.getBlockState(blockpos2).getBlock() == BLOCK_TOFUPORTAL; blockpos2 = blockpos2.down()) {
                                    blockpos1 = blockpos2;
                                }

                                double d1 = blockpos1.distanceSq(p_222272_1_);
                                if (d0 < 0.0D || d1 < d0) {
                                    d0 = d1;
                                    blockpos = blockpos1;
                                }
                            }
                        }
                    }
                }
            }

            if (blockpos == null) {
                long l = this.world.getGameTime() + 300L;
                this.field_222275_f.put(columnpos, l);
                return null;
            } else {
                if (flag) {
                    this.destinationCoordinateCache.put(columnpos, new TofuWorldTeleporter.PortalPosition(blockpos, this.world.getGameTime()));
                    org.apache.logging.log4j.util.Supplier[] asupplier = new org.apache.logging.log4j.util.Supplier[2];
                    Dimension dimension = this.world.getDimension();
                    asupplier[0] = dimension::getType;
                    asupplier[1] = () -> {
                        return columnpos;
                    };
                    this.world.getChunkProvider().func_217228_a(TicketType.PORTAL, new ChunkPos(blockpos), 3, columnpos);
                }

                BlockPattern.PatternHelper blockpattern$patternhelper = BLOCK_TOFUPORTAL.createPatternHelper(this.world, blockpos);
                return blockpattern$patternhelper.func_222504_a(p_222272_3_, blockpos, p_222272_6_, p_222272_2_, p_222272_4_);
            }
        }
    }*/

    private boolean isInsideBlock(BlockPos position) {
        return !this.world.isAirBlock(position) || !this.world.isAirBlock(position.up());
    }

    @Override
    public boolean makePortal(Entity entity) {
        return createPortal(this.world, new BlockPos(MathHelper.floor(entity.posX), MathHelper.floor(entity.posY), MathHelper.floor(entity.posZ)), entity);
    }

    public static boolean createPortal(World world, BlockPos pos, @Nullable Entity entity) {
        BlockState portalState = TofuBlocks.TOFUPORTAL.getDefaultState();
        BlockState snowstate;

        while (pos.getY() > 1 && world.isAirBlock(pos)) {
            pos = pos.down();
        }

        while (!world.isAirBlock(pos.up()) && (world.getBlockState(pos).getBlock() != TofuBlocks.TOFUTERRAIN || world.getBlockState(pos).getBlock() != Blocks.GRASS)) {
            pos = pos.up();
        }

        snowstate = TofuBlocks.GRILLEDTOFU.getDefaultState();


        //Bottom layers
        for (BlockPos basePos : BlockPos.MutableBlockPos.getAllInBoxMutable(pos.add(-2, 0, -2), pos.add(2, 1, 2))) {
            world.setBlockState(basePos, snowstate, 2);
        }



       /* //Pillars
        for (int y = 2; y < 4; y++) {
            world.setBlockState(pos.add(0, y, -2), snowstate, 2);
            world.setBlockState(pos.add(0, y, 2), snowstate, 2);
            world.setBlockState(pos.add(2, y, 0), snowstate, 2);
            world.setBlockState(pos.add(-2, y, 0), snowstate, 2);
        }

        world.setBlockState(pos.add(1, 4, 0), snowstate, 2);
        world.setBlockState(pos.add(-1, 4, 0), snowstate, 2);
        world.setBlockState(pos.add(0, 4, 1), snowstate, 2);
        world.setBlockState(pos.add(0, 4, -1), snowstate, 2);
        */

        //air
        for (BlockPos airPos : BlockPos.MutableBlockPos.getAllInBoxMutable(pos.add(-2, 2, -1), pos.add(2, 3, 1))) {
            world.setBlockState(airPos, Blocks.AIR.getDefaultState(), 2);
        }

        //Portal blocks
        for (BlockPos portalPos : BlockPos.MutableBlockPos.getAllInBoxMutable(pos.add(-1, 1, -1), pos.add(1, 1, 1))) {
            world.setBlockState(portalPos, portalState, 2);
        }

        return true;
    }

    public void tick(long worldTime) {
        if (worldTime % 100L == 0L) {
            this.func_222270_b(worldTime);
            this.func_222269_c(worldTime);
        }

    }

    private void func_222270_b(long p_222270_1_) {
        LongIterator longiterator = this.field_222275_f.values().iterator();

        while (longiterator.hasNext()) {
            long i = longiterator.nextLong();
            if (i <= p_222270_1_) {
                longiterator.remove();
            }
        }

    }

    private void func_222269_c(long p_222269_1_) {
        long i = p_222269_1_ - 300L;
        Iterator<Map.Entry<ColumnPos, PortalPosition>> iterator = this.destinationCoordinateCache.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<ColumnPos, PortalPosition> entry = iterator.next();
            TofuWorldTeleporter.PortalPosition teleporter$portalposition = entry.getValue();
            if (teleporter$portalposition.lastUpdateTime < i) {
                ColumnPos columnpos = entry.getKey();
                Supplier[] asupplier = new Supplier[2];
                Dimension dimension = this.world.getDimension();
                asupplier[0] = dimension::getType;
                asupplier[1] = () -> {
                    return columnpos;
                };
                this.world.getChunkProvider().func_217222_b(TicketType.PORTAL, new ChunkPos(teleporter$portalposition.field_222267_a), 3, columnpos);
                iterator.remove();
            }
        }

    }

    static class PortalPosition {
        public final BlockPos field_222267_a;
        public long lastUpdateTime;

        public PortalPosition(BlockPos p_i50813_1_, long p_i50813_2_) {
            this.field_222267_a = p_i50813_1_;
            this.lastUpdateTime = p_i50813_2_;
        }
    }
}