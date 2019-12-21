package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.container.TFStorageContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(TofuCraftCore.MODID)
@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuContainers {
    public static final ContainerType<TFStorageContainer> TFSTORAGE = new ContainerType<>(TFStorageContainer::new);

    private static <T extends Container> ContainerType<T> register(String name, ContainerType.IFactory<T> container) {
        ContainerType<T> type = new ContainerType<>(container);
        type.setRegistryName(name);

        return type;
    }

    @SubscribeEvent
    public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(TFSTORAGE.setRegistryName(TofuCraftCore.MODID, "tfstorage"));
    }
}
