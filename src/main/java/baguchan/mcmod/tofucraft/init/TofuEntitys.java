package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static baguchan.mcmod.tofucraft.TofuCraftCore.MODID;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuEntitys {

    public static final EntityType<TofunianEntity> TOFUNIAN = EntityType.Builder.create(TofunianEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.2F).build(prefix("tofunian"));

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(TOFUNIAN.setRegistryName("tofunian"));

    }

    private static String prefix(String path) {

        return MODID + "." + path;

    }

    public static void spawnEntity() {


    }
}