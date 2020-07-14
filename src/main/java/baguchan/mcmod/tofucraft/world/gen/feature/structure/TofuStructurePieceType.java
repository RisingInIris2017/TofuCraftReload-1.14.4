package baguchan.mcmod.tofucraft.world.gen.feature.structure;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.init.TofuStructures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class TofuStructurePieceType {
    public static IStructurePieceType TOFUVI_PIECE;
    public static IStructurePieceType TOFUCASTLE_PIECE;

    public static void init() {
        TOFUVI_PIECE = TofuStructures.registerStructurePiece(TofuVillageStructure.TofuVillage::new, new ResourceLocation(TofuCraftCore.MODID, "tofu_village"));
        TOFUCASTLE_PIECE = TofuStructures.registerStructurePiece(TofuCastlePieces.Piece::new, new ResourceLocation(TofuCraftCore.MODID, "tofu_castle"));
    }
}
