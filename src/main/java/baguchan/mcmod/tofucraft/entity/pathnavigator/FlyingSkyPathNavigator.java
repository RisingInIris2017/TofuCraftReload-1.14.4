package baguchan.mcmod.tofucraft.entity.pathnavigator;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlyingSkyPathNavigator extends FlyingPathNavigator {
    public FlyingSkyPathNavigator(MobEntity entityIn, World worldIn) {
        super(entityIn, worldIn);
    }

    public boolean canEntityStandOnPos(BlockPos pos) {
        return this.world.isAirBlock(pos.down());
    }
}
