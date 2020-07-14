package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.world.gen.feature.structure.TofuCastleStructure;
import baguchan.mcmod.tofucraft.world.gen.feature.structure.TofuVillageStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Locale;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuStructures {
    public static final Structure<NoFeatureConfig> TOFUVILLAGE = new TofuVillageStructure(NoFeatureConfig.field_236558_a_);
    public static final Structure<NoFeatureConfig> TOFUCASTLE = new TofuCastleStructure(NoFeatureConfig.field_236558_a_);

    public static <C extends IFeatureConfig> IStructurePieceType registerStructurePiece(IStructurePieceType pieceType, ResourceLocation key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key, pieceType);
    }

    @SubscribeEvent
    public static void registerStructure(RegistryEvent.Register<Structure<?>> registry) {
        registry.getRegistry().register(TOFUVILLAGE.setRegistryName("tofucraft:tofu_village"));
        registry.getRegistry().register(TOFUCASTLE.setRegistryName("tofucraft:tofu_castle"));

        putStructureOnAList("tofucraft:tofu_village", TOFUVILLAGE);
        putStructureOnAList("tofucraft:tofu_castle", TOFUCASTLE);
    }

    public static <F extends Structure<?>> void putStructureOnAList(String nameForList, F structure) {
        Structure.field_236365_a_.put(nameForList.toLowerCase(Locale.ROOT), structure);
    }

    private static String prefix(String path) {
        return TofuCraftCore.MODID + ":" + path;
    }
}
