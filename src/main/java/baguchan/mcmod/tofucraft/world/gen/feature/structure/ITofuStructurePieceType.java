package baguchan.mcmod.tofucraft.world.gen.feature.structure;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.init.TofuStructures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public interface ITofuStructurePieceType {
    IStructurePieceType TOFUVI_PIECE = TofuStructures.registerStructurePiece(TofuVillageStructure.TofuVillage::new, new ResourceLocation(TofuCraftCore.MODID, "tofuvillage"));
    IStructurePieceType TOFUCASTLE_PIECE = TofuStructures.registerStructurePiece(TofuCastlePieces.Piece::new, new ResourceLocation(TofuCraftCore.MODID, "tofucastle"));

}
