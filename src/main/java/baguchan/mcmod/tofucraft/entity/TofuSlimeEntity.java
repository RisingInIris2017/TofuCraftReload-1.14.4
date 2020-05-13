package baguchan.mcmod.tofucraft.entity;

import baguchan.mcmod.tofucraft.init.TofuBiomes;
import baguchan.mcmod.tofucraft.init.TofuDimensions;
import baguchan.mcmod.tofucraft.init.TofuItems;
import baguchan.mcmod.tofucraft.init.TofuLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.loot.LootTables;

import java.util.Random;

public class TofuSlimeEntity extends SlimeEntity {
    private static final DataParameter<Boolean> WEAK = EntityDataManager.createKey(TofuSlimeEntity.class, DataSerializers.BOOLEAN);

    public TofuSlimeEntity(EntityType<? extends TofuSlimeEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerData() {
        super.registerData();
        this.getDataManager().register(WEAK, false);
    }

    public void tick() {
        if (!this.world.isRemote && this.isAlive()) {
            if (!isWeak() && this.world.getBiome(this.getPosition()) == TofuBiomes.ZUNDATOFU_FUNGIFOREST) {
                setWeak(true);
            } else if (isWeak() && this.world.getBiome(this.getPosition()) != TofuBiomes.ZUNDATOFU_FUNGIFOREST) {
                setWeak(false);
            }
        }

        super.tick();
    }

    public void livingTick() {
        if (this.isAlive()) {
            boolean flag = this.isWeak();
            if (flag) {
                if (this.ticksExisted % 100 == 0) {
                    attackEntityFrom(DamageSource.STARVE, 2.0F);
                }

                if (getActivePotionEffect(Effects.WEAKNESS) == null) {
                    addPotionEffect(new EffectInstance(Effects.WEAKNESS, 100));
                }
            }
        }

        super.livingTick();
    }


    public boolean isWeak() {
        return this.getDataManager().get(WEAK);
    }

    public void setWeak(boolean weak) {
        this.getDataManager().set(WEAK, weak);
    }

    protected ResourceLocation getLootTable() {
        if (isWeak()) {
            return this.getSlimeSize() == 1 ? TofuLootTables.weak_tofuslime : LootTables.EMPTY;
        } else {
            return this.getSlimeSize() == 1 ? this.getType().getLootTable() : LootTables.EMPTY;
        }
    }

    public static boolean spawnHandle(EntityType<TofuSlimeEntity> p_223366_0_, IWorld p_223366_1_, SpawnReason reason, BlockPos p_223366_3_, Random randomIn) {
        if (p_223366_1_.getWorldInfo().getGenerator().handleSlimeSpawnReduction(randomIn, p_223366_1_) && randomIn.nextInt(4) != 1) {
            return false;
        } else {
            if (p_223366_1_.getDifficulty() != Difficulty.PEACEFUL) {
                Biome biome = p_223366_1_.getBiome(p_223366_3_);
                if (p_223366_1_.getWorld().getDimension().getType().getModType() == TofuDimensions.TOFUWORLD && p_223366_1_.getLightFor(LightType.BLOCK, p_223366_3_) <= randomIn.nextInt(7)) {
                    return canSpawnOn(p_223366_0_, p_223366_1_, reason, p_223366_3_, randomIn);
                }

                ChunkPos chunkpos = new ChunkPos(p_223366_3_);
                boolean flag = SharedSeedRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, p_223366_1_.getSeed(), 987234911L).nextInt(10) == 0;
                if (p_223366_1_.getWorld().getDimension().getType() == DimensionType.OVERWORLD && randomIn.nextInt(10) == 0 && flag && p_223366_3_.getY() < 50) {
                    return canSpawnOn(p_223366_0_, p_223366_1_, reason, p_223366_3_, randomIn);
                }
            }

            return false;
        }
    }

    public static boolean isSpawnChunk(World world, double x, double z) {
        BlockPos blockpos = new BlockPos(MathHelper.floor(x), 0, MathHelper.floor(z));
        ChunkPos chunkpos = new ChunkPos(blockpos);

        return SharedSeedRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, world.getSeed(), 987234911L).nextInt(10) == 0;
    }

    protected IParticleData getSquishParticle() {
        return new ItemParticleData(ParticleTypes.ITEM, new ItemStack(TofuItems.TOFUKINU));
    }
}
