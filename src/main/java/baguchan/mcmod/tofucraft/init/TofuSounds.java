package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID)
public final class TofuSounds {
    public static final SoundEvent TOFUNIAN_YES = createEvent("mob.tofunian.yes");
    public static final SoundEvent TOFUNIAN_AMBIENT = createEvent("mob.tofunian.ambient");
    public static final SoundEvent TOFUBUGLE = createEvent("tofubugle");

    private static SoundEvent createEvent(String name) {

        SoundEvent sound = new SoundEvent(new ResourceLocation(TofuCraftCore.MODID, name));

        sound.setRegistryName(new ResourceLocation(TofuCraftCore.MODID, name));

        return sound;
    }


    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
        evt.getRegistry().register(TOFUNIAN_YES);
        evt.getRegistry().register(TOFUNIAN_AMBIENT);
        evt.getRegistry().register(TOFUBUGLE);
    }

    private TofuSounds() {

    }
}