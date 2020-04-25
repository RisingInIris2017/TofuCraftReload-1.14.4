package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.container.SaltFurnaceContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuContainers {
    public static ContainerType<SaltFurnaceContainer> SALT_FURNACE = new ContainerType<>(SaltFurnaceContainer::new);

    @SubscribeEvent
    public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(
                SALT_FURNACE.setRegistryName("salt_furnace")
        );

    }
}