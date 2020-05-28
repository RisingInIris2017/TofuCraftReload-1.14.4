package baguchan.mcmod.tofucraft.data;


import baguchan.mcmod.tofucraft.TofuCraftCore;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt) {
        evt.getGenerator().addProvider(new BlockstateGenerator(evt.getGenerator(), evt.getExistingFileHelper()));
        evt.getGenerator().addProvider(new ItemModelGenerator(evt.getGenerator(), evt.getExistingFileHelper()));
    }
}
