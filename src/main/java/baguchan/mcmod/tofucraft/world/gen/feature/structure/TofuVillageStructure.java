package baguchan.mcmod.tofucraft.world.gen.feature.structure;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class TofuVillageStructure extends Structure<NoFeatureConfig> {

    public TofuVillageStructure(Codec<NoFeatureConfig> p_i51419_1_) {
        super(p_i51419_1_);
    }

    @Override
    public IStartFactory getStartFactory() {
        return Start::new;
    }

    @Override
    public String getStructureName() {
        return "tofucraft:tofuvillage";
    }

    public int getSize() {
        return 8;
    }

    public GenerationStage.Decoration func_236396_f_() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    public static class Start extends MarginedStructureStart<NoFeatureConfig> {

        public Start(Structure<NoFeatureConfig> p_i50460_1_, int p_i50460_2_, int p_i50460_3_, MutableBoundingBox p_i50460_5_, int p_i50460_6_, long p_i50460_7_) {
            super(p_i50460_1_, p_i50460_2_, p_i50460_3_, p_i50460_5_, p_i50460_6_, p_i50460_7_);
        }

        public void init(ChunkGenerator generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
            TofuVillagePools.init();
            JigsawManager.func_236823_a_(new ResourceLocation(TofuCraftCore.MODID, "tofu_village/town_centers"), 6, TofuVillage::new, generator, templateManagerIn, pos, this.components, this.rand, true, true);
            this.recalculateStructureSize();
        }

        @Override
        public void func_230364_a_(ChunkGenerator p_230364_1_, TemplateManager p_230364_2_, int p_230364_3_, int p_230364_4_, Biome p_230364_5_, NoFeatureConfig p_230364_6_) {

        }
    }

    public static class TofuVillage extends AbstractVillagePiece {

        public TofuVillage(TemplateManager p_i50890_1_, JigsawPiece p_i50890_2_, BlockPos p_i50890_3_, int p_i50890_4_, Rotation p_i50890_5_, MutableBoundingBox p_i50890_6_) {
            super(TofuStructurePieceType.TOFUVI_PIECE, p_i50890_1_, p_i50890_2_, p_i50890_3_, p_i50890_4_, p_i50890_5_, p_i50890_6_);
        }

        public TofuVillage(TemplateManager p_i50891_1_, CompoundNBT p_i50891_2_) {
            super(p_i50891_1_, p_i50891_2_, TofuStructurePieceType.TOFUVI_PIECE);
        }
    }
}