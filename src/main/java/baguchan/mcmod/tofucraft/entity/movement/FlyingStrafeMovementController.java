package baguchan.mcmod.tofucraft.entity.movement;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.MathHelper;

public class FlyingStrafeMovementController extends FlyingMovementController {
    public FlyingStrafeMovementController(MobEntity p_i47418_1_, int p_i225710_2_, boolean p_i225710_3_) {
        super(p_i47418_1_, p_i225710_2_, p_i225710_3_);
    }

    public void tick() {
        if (this.action == MovementController.Action.STRAFE) {
            this.mob.setNoGravity(false);
            float f = (float) this.mob.getAttribute(Attributes.field_233821_d_).getValue();
            float f1 = (float) this.speed * f;
            float f2 = this.moveForward;
            float f3 = this.moveStrafe;
            float f4 = MathHelper.sqrt(f2 * f2 + f3 * f3);
            if (f4 < 1.0F) {
                f4 = 1.0F;
            }

            f4 = f1 / f4;
            f2 = f2 * f4;
            f3 = f3 * f4;
            float f5 = MathHelper.sin(this.mob.rotationYaw * ((float) Math.PI / 180F));
            float f6 = MathHelper.cos(this.mob.rotationYaw * ((float) Math.PI / 180F));
            float f7 = f2 * f6 - f3 * f5;
            float f8 = f3 * f6 + f2 * f5;
            PathNavigator pathnavigator = this.mob.getNavigator();
            if (pathnavigator != null) {
                NodeProcessor nodeprocessor = pathnavigator.getNodeProcessor();
                if (nodeprocessor != null && nodeprocessor.getPathNodeType(this.mob.world, MathHelper.floor(this.mob.getPosX() + (double) f7), MathHelper.floor(this.mob.getPosY()), MathHelper.floor(this.mob.getPosZ() + (double) f8)) != PathNodeType.WALKABLE) {
                    this.moveForward = 1.0F;
                    this.moveStrafe = 0.0F;
                    f1 = f;
                }
            }

            this.mob.setAIMoveSpeed(f1);
            this.mob.setMoveForward(this.moveForward);
            this.mob.setMoveStrafing(this.moveStrafe);
            this.action = MovementController.Action.WAIT;
        } else {
            super.tick();
        }

    }
}