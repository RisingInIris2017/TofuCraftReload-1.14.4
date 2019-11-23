package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public abstract class TofuAnimalEntity extends AnimalEntity {
    protected TofuAnimalEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static boolean spawnHandle(EntityType<? extends AnimalEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason reason, BlockPos p_223316_3_, Random p_223316_4_) {
        return p_223316_1_.getBlockState(p_223316_3_.down()).getBlock() == TofuBlocks.TOFUTERRAIN && p_223316_1_.getLightSubtracted(p_223316_3_, 0) > 8;
    }
}
