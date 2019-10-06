package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.world.dimension.TofuWorldDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.BiFunction;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuDimensions {

    public static ModDimension TOFUWORLD = new ModDimension() {

        @Override
        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return TofuWorldDimension::new;
        }

    }.setRegistryName(new ResourceLocation(TofuCraftCore.MODID, "tofuworld"));

    @SubscribeEvent
    public static void register(RegistryEvent.Register<ModDimension> registry) {
        registry.getRegistry().register(TOFUWORLD);
        DimensionManager.registerDimension(new ResourceLocation(TofuCraftCore.MODID, "tofuworld"), TOFUWORLD, null, true);
    }

}