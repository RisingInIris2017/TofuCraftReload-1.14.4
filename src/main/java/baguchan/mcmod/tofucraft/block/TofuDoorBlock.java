package baguchan.mcmod.tofucraft.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class TofuDoorBlock extends DoorBlock {
    public TofuDoorBlock(Properties from) {
        super(from);
    }

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {

        if (state.getMaterial() != Material.IRON) {
            if (!state.get(OPEN)) {
                return PathNodeType.DOOR_WOOD_CLOSED;
            } else {
                return PathNodeType.DOOR_OPEN;
            }
        } else {
            if (!state.get(OPEN)) {
                return PathNodeType.DOOR_IRON_CLOSED;
            } else {
                return PathNodeType.DOOR_OPEN;
            }
        }
    }
}
