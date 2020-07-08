package baguchan.mcmod.tofucraft.world.dimension;

import baguchan.mcmod.tofucraft.block.TofuPortalBlock;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class TofuWorldTeleporter implements ITeleporter {
    private static final TofuPortalBlock BLOCK_TOFUPORTAL = (TofuPortalBlock) TofuBlocks.TOFUPORTAL;

    protected final ServerWorld world;
    protected final Random random;

    public TofuWorldTeleporter(ServerWorld worldIn) {
        this.world = worldIn;
        this.random = new Random(worldIn.getSeed());
    }

    public boolean placeInPortal(Entity p_222268_1_, float p_222268_2_) {
        Vector3d vector3d = p_222268_1_.getLastPortalVec();
        Direction direction = p_222268_1_.getTeleportDirection();
        BlockPos pos = p_222268_1_.func_233580_cy_();

        boolean blockpattern$portalinfo = this.placeInExistingPortal(pos, p_222268_1_.getMotion(), direction, vector3d.x, vector3d.y, p_222268_1_ instanceof PlayerEntity);
        if (!blockpattern$portalinfo) {
            return false;
        } else {
            Vector3d vector3d1 = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
            p_222268_1_.setMotion(0, 0, 0);
            p_222268_1_.rotationYaw = p_222268_2_;
            p_222268_1_.moveForced(vector3d1.x, vector3d1.y, vector3d1.z);
            return true;
        }
    }

    public boolean placeInExistingPortal(BlockPos p_222272_1_, Vector3d p_222272_2_, Direction directionIn, double p_222272_4_, double p_222272_6_, boolean p_222272_8_) {
        PointOfInterestManager pointofinterestmanager = this.world.getPointOfInterestManager();
        pointofinterestmanager.ensureLoadedAndValid(this.world, p_222272_1_, 128);
        List<PointOfInterest> list = pointofinterestmanager.getInSquare((p_226705_0_) -> {
            return p_226705_0_ == PointOfInterestType.NETHER_PORTAL;
        }, p_222272_1_, 128, PointOfInterestManager.Status.ANY).collect(Collectors.toList());
        Optional<PointOfInterest> optional = list.stream().min(Comparator.<PointOfInterest>comparingDouble((p_226706_1_) -> {
            return p_226706_1_.getPos().distanceSq(p_222272_1_);
        }).thenComparingInt((p_226704_0_) -> {
            return p_226704_0_.getPos().getY();
        }));
        return optional.map((p_226707_7_) -> {
            BlockPos blockpos = p_226707_7_.getPos();
            this.world.getChunkProvider().registerTicket(TicketType.PORTAL, new ChunkPos(blockpos), 3, blockpos);
            TofuPortalBlock portal = TofuBlocks.TOFUPORTAL;
            return portal.trySpawnPortal(this.world, blockpos);
        }).orElse(false);
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
        for (BlockPos basePos : BlockPos.Mutable.getAllInBoxMutable(pos.add(-2, 0, -2), pos.add(2, 1, 2))) {
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
        for (BlockPos airPos : BlockPos.Mutable.getAllInBoxMutable(pos.add(-2, 2, -1), pos.add(2, 3, 1))) {
            world.setBlockState(airPos, Blocks.AIR.getDefaultState(), 2);
        }

        //Portal blocks
        for (BlockPos portalPos : BlockPos.Mutable.getAllInBoxMutable(pos.add(-1, 1, -1), pos.add(1, 1, 1))) {
            world.setBlockState(portalPos, portalState, 2);
        }

        return true;
    }
}