package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.fluid.FluidSoyMilk;
import baguchan.mcmod.tofucraft.fluid.FluidSoyMilkHell;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuFluids {

    public static final FlowingFluid SOYMILK = registerFluid("soymilk", new FluidSoyMilk.Source());
    public static final FlowingFluid SOYMILK_FLOW = registerFluid("soymilk_flow", new FluidSoyMilk.Flowing());
    public static final FlowingFluid SOYMILK_HELL = registerFluid("soymilk_hell", new FluidSoyMilkHell.Source());
    public static final FlowingFluid SOYMILK_HELL_FLOW = registerFluid("soymilk_hell_flow", new FluidSoyMilkHell.Flowing());


    private static <T extends Fluid> T registerFluid(String key, T p_215710_1_) {

        ResourceLocation location = new ResourceLocation(TofuCraftCore.MODID + ":" + key);

        p_215710_1_.setRegistryName(location);

        return p_215710_1_;
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Fluid> registry) {
        registry.getRegistry().register(SOYMILK);
        registry.getRegistry().register(SOYMILK_FLOW);
        registry.getRegistry().register(SOYMILK_HELL);
        registry.getRegistry().register(SOYMILK_HELL_FLOW);
    }
}