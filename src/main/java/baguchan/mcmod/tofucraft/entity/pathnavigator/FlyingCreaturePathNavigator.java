package baguchan.mcmod.tofucraft.entity.pathnavigator;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlyingCreaturePathNavigator extends FlyingPathNavigator {
    public FlyingCreaturePathNavigator(CreatureEntity entity, World worldIn) {
        super(entity, worldIn);
    }

    public boolean canEntityStandOnPos(BlockPos pos) {
        return this.world.isAirBlock(pos.down());
    }
}
