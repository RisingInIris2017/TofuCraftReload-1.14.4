package baguchan.mcmod.tofucraft.world.biome.gen.feature.structure;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

import java.util.Locale;

public interface ITofuStructurePieceType {
    IStructurePieceType TOFUVI_PIECE = register(TofuVillageStructure.TofuVillage::new, "TofuVi");

    static IStructurePieceType register(IStructurePieceType type, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), type);
    }
}
