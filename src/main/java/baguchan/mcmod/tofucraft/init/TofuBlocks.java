package baguchan.mcmod.tofucraft.init;


import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuBlocks {

    public static final Block SOYMILK = new TofuFluidBlock(TofuFluids.SOYMILK, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
    public static final Block SOYBEAN = new SoyBeanCropsBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP));
    public static final Block KINUTOFU = new TofuBlock(Block.Properties.create(TofuMaterial.TOFU).tickRandomly().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.1F, 0.2F).sound(SoundType.SNOW));
    public static final Block MOMENTOFU = new TofuBlock(Block.Properties.create(TofuMaterial.TOFU).tickRandomly().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.35F, 0.5F).sound(SoundType.SNOW));
    public static final Block ISHITOFU = new TofuBlock(Block.Properties.create(Material.ROCK).tickRandomly().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE));
    public static final Block METALTOFU = new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(4.0F, 7.5F).sound(SoundType.METAL));
    public static final Block GRILLEDTOFU = new Block(Block.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.25F, 0.5F).sound(SoundType.SNOW));

    public static final Block ISHITOFU_BRICK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.6F, 6.5F).sound(SoundType.STONE));
    //TERRAIN
    public static final Block TOFUTERRAIN = new Block(Block.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.45F, 0.85F).sound(SoundType.SNOW));
    public static final Block TOFUBEDROCK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(-1.0F, 2000000.0F).sound(SoundType.STONE));
    public static final Block TOFUFLOWER = new FlowerBlock(Effects.ABSORPTION, 20, Block.Properties.create(Material.PLANTS).hardnessAndResistance(0.0F).sound(SoundType.PLANT));


    public static final Block TOFUPORTAL = new TofuPortalBlock(Block.Properties.create(Material.PORTAL).harvestTool(ToolType.PICKAXE).hardnessAndResistance(-1.0F, 3000000.0F).sound(SoundType.GLASS));

    //Util block
    public static final Block SALTPAN = new SaltPanBlock(Block.Properties.create(Material.WOOD).tickRandomly().harvestTool(ToolType.AXE).hardnessAndResistance(1.0F).sound(SoundType.WOOD));


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

        registry.getRegistry().register(ISHITOFU_BRICK.setRegistryName("tofuishi_brick"));
        //terrain
        registry.getRegistry().register(TOFUTERRAIN.setRegistryName("tofu_terrain"));
        registry.getRegistry().register(TOFUBEDROCK.setRegistryName("tofubedrock"));
        registry.getRegistry().register(TOFUFLOWER.setRegistryName("tofuflower"));
        registry.getRegistry().register(TOFUPORTAL.setRegistryName("tofuportal"));

        registry.getRegistry().register(SALTPAN.setRegistryName("blocksaltpan"));
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> registry) {

        TofuItems.register(registry, new BlockItem(KINUTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(MOMENTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(ISHITOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(METALTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(GRILLEDTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new BlockItem(ISHITOFU_BRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new BlockItem(TOFUTERRAIN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUBEDROCK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUFLOWER, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new BlockItem(SALTPAN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
    }

}