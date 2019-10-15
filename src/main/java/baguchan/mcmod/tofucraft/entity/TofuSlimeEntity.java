package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.init.TofuDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTables;

import java.util.Random;

public class TofuSlimeEntity extends SlimeEntity {
    public TofuSlimeEntity(EntityType<? extends TofuSlimeEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected ResourceLocation getLootTable() {
        return this.getSlimeSize() == 1 ? this.getType().getLootTable() : LootTables.EMPTY;
    }

    public static boolean spawnHandle(EntityType<TofuSlimeEntity> p_223366_0_, IWorld p_223366_1_, SpawnReason reason, BlockPos p_223366_3_, Random randomIn) {
        if (p_223366_1_.getWorldInfo().getGenerator().handleSlimeSpawnReduction(randomIn, p_223366_1_) && randomIn.nextInt(4) != 1) {
            return false;
        } else {
            if (p_223366_1_.getDifficulty() != Difficulty.PEACEFUL) {
                Biome biome = p_223366_1_.getBiome(p_223366_3_);
                if (p_223366_1_.getWorld().getDimension().getType().getModType() == TofuDimensions.TOFUWORLD && randomIn.nextFloat() < 0.05F && p_223366_1_.getLight(p_223366_3_) <= randomIn.nextInt(10)) {
                    return func_223315_a(p_223366_0_, p_223366_1_, reason, p_223366_3_, randomIn);
                }

                ChunkPos chunkpos = new ChunkPos(p_223366_3_);
                boolean flag = SharedSeedRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, p_223366_1_.getSeed(), 987234911L).nextInt(10) == 0;
                if (randomIn.nextInt(10) == 0 && flag && p_223366_3_.getY() < 40) {
                    return func_223315_a(p_223366_0_, p_223366_1_, reason, p_223366_3_, randomIn);
                }
            }

            return false;
        }
    }
}
