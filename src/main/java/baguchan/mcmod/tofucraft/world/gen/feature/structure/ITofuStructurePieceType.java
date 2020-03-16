package baguchan.mcmod.tofucraft.world.gen.feature.structure;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public interface ITofuStructurePieceType {
    IStructurePieceType TOFUVI_PIECE = IStructurePieceType.register(TofuVillageStructure.TofuVillage::new, TofuCraftCore.MODID + ".tofuvillage");
    IStructurePieceType TOFUCASTLE_PIECE = IStructurePieceType.register(TofuCastlePieces.Piece::new, TofuCraftCore.MODID + ".tofucastle");

}
