package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import net.minecraft.entity.item.PaintingType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuPaintings {
    public static final DeferredRegister<PaintingType> PAINTINGS = new DeferredRegister<>(ForgeRegistries.PAINTING_TYPES, TofuCraftCore.MODID);

    public static final RegistryObject<PaintingType> TOFUCHINGER = PAINTINGS.register("tofu_chinger", () -> new PaintingType(32, 16));
    public static final RegistryObject<PaintingType> TOFUNIAN = PAINTINGS.register("tofunian", () -> new PaintingType(64, 64));

}
