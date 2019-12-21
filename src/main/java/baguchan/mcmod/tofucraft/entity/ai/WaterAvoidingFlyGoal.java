package baguchan.mcmod.tofucraft.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.Heightmap;

import javax.annotation.Nullable;

public class WaterAvoidingFlyGoal extends RandomWalkingGoal {
    protected final float falldistance;

    public WaterAvoidingFlyGoal(CreatureEntity creature, double speedIn, float falldistance) {
        this(creature, speedIn, 40, falldistance);
    }

    public WaterAvoidingFlyGoal(CreatureEntity creature, double speedIn, int chance, float falldistance) {
        super(creature, speedIn, chance);
        this.falldistance = falldistance;
    }

    @Nullable
    protected Vec3d getPosition() {
        Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.creature, 15, 7);
        if (!isNearGround(this.falldistance)) {
            return vec3d == null ? super.getPosition() : vec3d.subtract(0, 5F, 0);
        } else {
            return vec3d == null ? super.getPosition() : vec3d;
        }
    }

    protected boolean isNearGround(float falldistance) {
        Path path = this.creature.getNavigator().getPath();
        if (path != null) {

            BlockPos blockpos = this.creature.world.getHeight(Heightmap.Type.WORLD_SURFACE, this.creature.getPosition());
            if (blockpos != null) {
                double d0 = this.creature.getDistanceSq((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ());
                if (d0 < falldistance) {
                    return true;
                }
            }
        }

        return false;
    }
}