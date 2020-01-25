package baguchan.mcmod.tofucraft.world.gen.feature.structure;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.init.TofuFeatures;
import com.mojang.datafixers.Dynamic;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;
import java.util.function.Function;

public class TofuVillageStructure extends Structure<NoFeatureConfig> {

    public TofuVillageStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51419_1_) {
        super(p_i51419_1_);
    }

    @Override
    protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
        int i = chunkGenerator.getSettings().getVillageDistance();
        int j = chunkGenerator.getSettings().getVillageSeparation();
        int k = x + i * spacingOffsetsX;
        int l = z + i * spacingOffsetsZ;
        int i1 = k < 0 ? k - i + 1 : k;
        int j1 = l < 0 ? l - i + 1 : l;
        int k1 = i1 / i;
        int l1 = j1 / i;
        ((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 10387312);
        k1 = k1 * i;
        l1 = l1 * i;
        k1 = k1 + random.nextInt(i - j);
        l1 = l1 + random.nextInt(i - j);
        return new ChunkPos(k1, l1);
    }

    @Override
    public boolean func_225558_a_(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome p_225558_6_) {
        ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
        if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
            BlockPos pos = new BlockPos((chunkPosX << 4) + 8, 0, (chunkPosZ << 4) + 8);
            return isValid(chunkGen, pos.add(-4, 0, -4), p_225558_1_) &&
                    isValid(chunkGen, pos.add(-4, 0, 4), p_225558_1_) &&
                    isValid(chunkGen, pos.add(4, 0, 4), p_225558_1_) &&
                    isValid(chunkGen, pos.add(4, 0, -4), p_225558_1_);
        } else {
            return false;
        }
    }

    private boolean isValid(ChunkGenerator<?> chunkGen, BlockPos pos, BiomeManager p_225558_1_) {
        return chunkGen.hasStructure(p_225558_1_.func_226836_a_(pos), TofuFeatures.TOFUVILLAGE);
    }

    @Override
    public BlockPos findNearest(World worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, BlockPos pos, int radius, boolean p_211405_5_) {
        BlockPos ret = super.findNearest(worldIn, chunkGenerator, pos, radius, p_211405_5_);

        return ret; // Fallback ?
    }

    @Override
    public IStartFactory getStartFactory() {
        return Start::new;
    }

    @Override
    public String getStructureName() {
        return "tofucraft:tofuvillage";
    }

    @Override
    public int getSize() {
        return 8;
    }

    public static class Start extends MarginedStructureStart {

        public Start(Structure<?> p_i50460_1_, int p_i50460_2_, int p_i50460_3_, MutableBoundingBox p_i50460_5_, int p_i50460_6_, long p_i50460_7_) {
            super(p_i50460_1_, p_i50460_2_, p_i50460_3_, p_i50460_5_, p_i50460_6_, p_i50460_7_);
        }

        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
            TofuVillagePools.init();
            JigsawManager.func_214889_a(new ResourceLocation(TofuCraftCore.MODID, "tofu_village/town_centers"), 6, TofuVillage::new, generator, templateManagerIn, pos, this.components, this.rand);
            this.recalculateStructureSize();
        }
    }

    public static class TofuVillage extends AbstractVillagePiece {

        public TofuVillage(TemplateManager p_i50890_1_, JigsawPiece p_i50890_2_, BlockPos p_i50890_3_, int p_i50890_4_, Rotation p_i50890_5_, MutableBoundingBox p_i50890_6_) {
            super(ITofuStructurePieceType.TOFUVI_PIECE, p_i50890_1_, p_i50890_2_, p_i50890_3_, p_i50890_4_, p_i50890_5_, p_i50890_6_);
        }

        public TofuVillage(TemplateManager p_i50891_1_, CompoundNBT p_i50891_2_) {
            super(p_i50891_1_, p_i50891_2_, ITofuStructurePieceType.TOFUVI_PIECE);
        }
    }
}