package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.tileentity.SaltFurnaceTileEntity;
import baguchan.mcmod.tofucraft.tileentity.TofuBedTileEntity;
import baguchan.mcmod.tofucraft.tileentity.TofuChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuTileEntitys {
    public static final TileEntityType<TofuChestTileEntity> TOFUCHEST = TileEntityType.Builder.<TofuChestTileEntity>create(TofuChestTileEntity::new, TofuBlocks.TOFUCHEST).build(null);
    public static final TileEntityType<TofuBedTileEntity> TOFUBED = TileEntityType.Builder.create(TofuBedTileEntity::new, TofuBlocks.TOFUBED).build(null);
    public static final TileEntityType<SaltFurnaceTileEntity> SALT_FURNACE = TileEntityType.Builder.create(SaltFurnaceTileEntity::new, TofuBlocks.SALT_FURNACE).build(null);


    @SubscribeEvent
    public static void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(TOFUCHEST.setRegistryName("tofuchest"));
        event.getRegistry().register(TOFUBED.setRegistryName("tofubed"));
        event.getRegistry().register(SALT_FURNACE.setRegistryName("salt_furnace"));
    }
}
