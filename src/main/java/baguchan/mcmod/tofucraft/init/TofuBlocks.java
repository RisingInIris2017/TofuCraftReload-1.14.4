package baguchan.mcmod.tofucraft.init;


import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.block.SoyBeanCropsBlock;
import baguchan.mcmod.tofucraft.block.TofuBlock;
import baguchan.mcmod.tofucraft.block.TofuFluidBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuBlocks {

    public static final Block SOYMILK = new TofuFluidBlock(TofuFluids.SOYMILK, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
    public static final Block SOYBEAN = new SoyBeanCropsBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP));
    public static final Block KINUTOFU = new TofuBlock(Block.Properties.create(TofuMaterial.TOFU).tickRandomly().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.05F, 0.2F).sound(SoundType.SNOW));
    public static final Block MOMENTOFU = new TofuBlock(Block.Properties.create(TofuMaterial.TOFU).tickRandomly().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.25F, 0.5F).sound(SoundType.SNOW));
    public static final Block ISHITOFU = new TofuBlock(Block.Properties.create(Material.ROCK).tickRandomly().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE));
    public static final Block METALTOFU = new Block(Block.Properties.create(Material.IRON).tickRandomly().harvestTool(ToolType.PICKAXE).hardnessAndResistance(4.0F, 6.0F).sound(SoundType.METAL));
    public static final Block GRILLEDTOFU = new Block(Block.Properties.create(TofuMaterial.TOFU).tickRandomly().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.25F, 0.5F).sound(SoundType.SNOW));


    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> registry) {
        //Terrain
        registry.getRegistry().register(SOYMILK.setRegistryName("soymilk"));
        registry.getRegistry().register(SOYBEAN.setRegistryName("soybean"));
        registry.getRegistry().register(KINUTOFU.setRegistryName("blocktofukinu"));
        registry.getRegistry().register(MOMENTOFU.setRegistryName("blocktofumomen"));
        registry.getRegistry().register(ISHITOFU.setRegistryName("blocktofuishi"));
        registry.getRegistry().register(METALTOFU.setRegistryName("blocktofumetal"));
        registry.getRegistry().register(GRILLEDTOFU.setRegistryName("blocktofugrilled"));
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> registry) {

        TofuItems.register(registry, new BlockItem(KINUTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(MOMENTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(ISHITOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(METALTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(GRILLEDTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
    }

}