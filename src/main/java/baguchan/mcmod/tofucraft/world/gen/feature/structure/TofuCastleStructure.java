package baguchan.mcmod.tofucraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class TofuCastleStructure extends Structure<NoFeatureConfig> {
    public TofuCastleStructure(Codec<NoFeatureConfig> p_i51440_1_) {
        super(p_i51440_1_);
    }

    public String getStructureName() {
        return "tofucraft:tofucastle";
    }

    public int getSize() {
        return 3;
    }

    @Override
    public IStartFactory getStartFactory() {
        return Start::new;
    }

    public GenerationStage.Decoration func_236396_f_() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> p_i225806_1_, int p_i225806_2_, int p_i225806_3_, MutableBoundingBox p_i225806_4_, int p_i225806_5_, long p_i225806_6_) {
            super(p_i225806_1_, p_i225806_2_, p_i225806_3_, p_i225806_4_, p_i225806_5_, p_i225806_6_);
        }

        public void func_230364_a_(ChunkGenerator generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig p_230364_6_) {
            BlockPos blockpos = new BlockPos(chunkX * 16, 90, chunkZ * 16);

            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
            TofuCastlePieces.addStructure(templateManagerIn, blockpos, rotation, this.components, this.rand, biomeIn);
            this.recalculateStructureSize();
        }

    }
}