package baguchan.mcmod.tofucraft.world.gen.feature.structure;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.entity.TofuMindEntity;
import baguchan.mcmod.tofucraft.entity.TofuTurretEntity;
import baguchan.mcmod.tofucraft.init.TofuEntitys;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class TofuCastlePieces {
    private static final ResourceLocation tofucastle_main = new ResourceLocation(TofuCraftCore.MODID, "tofucastle/tofucastle_main");
    private static final ResourceLocation tofucastle_under = new ResourceLocation(TofuCraftCore.MODID, "tofucastle/tofucastle_undermain");

    private static final Map<ResourceLocation, BlockPos> structurePos = ImmutableMap.of(tofucastle_main, BlockPos.ZERO, tofucastle_under, new BlockPos(0, -19, 0));

    public static void addStructure(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> list, Random p_207617_4_, Biome biome) {
        addGenerate(list, new TofuCastlePieces.Piece(templateManager, tofucastle_main, pos, rotation, 0));

        addGenerate(list, new TofuCastlePieces.Piece(templateManager, tofucastle_under, pos, rotation, 0));
    }

    private static TofuCastlePieces.Piece addGenerate(List<StructurePiece> p_189935_0_, TofuCastlePieces.Piece p_189935_1_) {
        p_189935_0_.add(p_189935_1_);
        return p_189935_1_;
    }


    public static class Piece extends TemplateStructurePiece {
        private final ResourceLocation field_207615_d;
        private final Rotation field_207616_e;

        public Piece(TemplateManager p_i49313_1_, ResourceLocation p_i49313_2_, BlockPos p_i49313_3_, Rotation p_i49313_4_, int p_i49313_5_) {
            super(ITofuStructurePieceType.TOFUCASTLE_PIECE, 0);
            this.field_207615_d = p_i49313_2_;
            BlockPos blockpos = TofuCastlePieces.structurePos.get(p_i49313_2_);
            this.templatePosition = p_i49313_3_.add(blockpos.getX(), blockpos.getY() - p_i49313_5_, blockpos.getZ());
            this.field_207616_e = p_i49313_4_;
            this.func_207614_a(p_i49313_1_);
        }

        public Piece(TemplateManager p_i50566_1_, CompoundNBT p_i50566_2_) {
            super(ITofuStructurePieceType.TOFUCASTLE_PIECE, p_i50566_2_);
            this.field_207615_d = new ResourceLocation(p_i50566_2_.getString("Template"));
            this.field_207616_e = Rotation.valueOf(p_i50566_2_.getString("Rot"));
            this.func_207614_a(p_i50566_1_);
        }

        private void func_207614_a(TemplateManager p_207614_1_) {
            Template template = p_207614_1_.getTemplateDefaulted(this.field_207615_d);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.field_207616_e).setMirror(Mirror.NONE).setCenterOffset(TofuCastlePieces.structurePos.get(this.field_207615_d)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementsettings);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putString("Template", this.field_207615_d.toString());
            tagCompound.putString("Rot", this.field_207616_e.name());
        }

        protected void handleDataMarker(String function, BlockPos pos, IWorld world, Random rand, MutableBoundingBox box) {
            if (function.equals("Turret")) {
                TofuTurretEntity entityturret = TofuEntitys.TOFUTURRET.create(world.getWorld());
                entityturret.enablePersistence();
                entityturret.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                world.addEntity(entityturret);
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            } else if (function.equals("MindCore")) {
                TofuMindEntity entitymind = TofuEntitys.TOFUMIND.create(world.getWorld());
                entitymind.enablePersistence();
                entitymind.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                world.addEntity(entitymind);
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            }
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
         * the end, it adds Fences...
         */
        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.field_207616_e).setMirror(Mirror.NONE).setCenterOffset(TofuCastlePieces.structurePos.get(this.field_207615_d)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            BlockPos blockpos = TofuCastlePieces.structurePos.get(this.field_207615_d);
            BlockPos blockpos1 = this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(3 - blockpos.getX(), 0, 0 - blockpos.getZ())));
            int i = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
            BlockPos blockpos2 = this.templatePosition;
            this.templatePosition = this.templatePosition.add(0, i - 90 - 1, 0);
            boolean flag = super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn, p_74875_4_);


            this.templatePosition = blockpos2;
            return flag;
        }
    }
}