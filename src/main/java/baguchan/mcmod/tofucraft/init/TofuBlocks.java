package baguchan.mcmod.tofucraft.init;


import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.block.*;
import baguchan.mcmod.tofucraft.world.tree.TofuTree;
import baguchan.mcmod.tofucraft.world.tree.ZundaTofuTree;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.WallOrFloorItem;
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
    //Stair
    public static final Block TOFUSTAIR_KINU = new StairsBlock(KINUTOFU::getDefaultState, Block.Properties.from(KINUTOFU));
    public static final Block TOFUSTAIR_MOMEN = new StairsBlock(MOMENTOFU::getDefaultState, Block.Properties.from(MOMENTOFU));
    public static final Block TOFUSTAIR_ISHI = new StairsBlock(ISHITOFU::getDefaultState, Block.Properties.from(ISHITOFU));
    public static final Block TOFUSTAIR_METAL = new StairsBlock(METALTOFU::getDefaultState, Block.Properties.from(METALTOFU));
    public static final Block TOFUSTAIR_ISHIBRICK = new StairsBlock(ISHITOFU_BRICK::getDefaultState, Block.Properties.from(ISHITOFU_BRICK));
    //torch
    public static final Block TOFUTORCH_KINU = new TofuTorchBlock(Block.Properties.create(TofuMaterial.TOFU).hardnessAndResistance(0.0F, 0.5F).lightValue(14).sound(SoundType.SNOW));
    public static final Block TOFUTORCH_MOMEN = new TofuTorchBlock(Block.Properties.create(TofuMaterial.TOFU).hardnessAndResistance(0.0F, 0.5F).lightValue(14).sound(SoundType.SNOW));
    public static final Block TOFUTORCH_ISHI = new TofuTorchBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.0F, 6.0F).lightValue(14).sound(SoundType.STONE));
    public static final Block TOFUTORCH_METAL = new TofuTorchBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.0F, 7.5F).lightValue(14).sound(SoundType.METAL));
    public static final Block WALLTOFUTORCH_KINU = new WallTofuTorchBlock(Block.Properties.create(TofuMaterial.TOFU).hardnessAndResistance(0.0F, 0.5F).lightValue(14).sound(SoundType.SNOW).lootFrom(TOFUTORCH_KINU));
    public static final Block WALLTOFUTORCH_MOMEN = new WallTofuTorchBlock(Block.Properties.create(TofuMaterial.TOFU).hardnessAndResistance(0.0F, 0.5F).lightValue(14).sound(SoundType.SNOW).lootFrom(TOFUTORCH_MOMEN));
    public static final Block WALLTOFUTORCH_ISHI = new WallTofuTorchBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.0F, 6.0F).lightValue(14).sound(SoundType.STONE).lootFrom(TOFUTORCH_ISHI));
    public static final Block WALLTOFUTORCH_METAL = new WallTofuTorchBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.0F, 7.5F).lightValue(14).sound(SoundType.METAL).lootFrom(TOFUTORCH_METAL));


    //TERRAIN
    public static final Block TOFUTERRAIN = new Block(Block.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.45F, 0.85F).sound(SoundType.SNOW));
    public static final Block TOFUBEDROCK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(-1.0F, 2000000.0F).sound(SoundType.STONE));
    public static final Block TOFUFLOWER = new TofuFlowerBlock(Effects.ABSORPTION, 20, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
    public static final Block TOFULEAVES = new TofuLeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.5F).tickRandomly().sound(SoundType.PLANT));
    public static final Block TOFUZUNDALEAVES = new TofuLeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.5F).tickRandomly().sound(SoundType.PLANT));
    public static final Block TOFUSAPLING = new TofuSaplingBlock(new TofuTree(), Block.Properties.create(Material.PLANTS).hardnessAndResistance(0.0F).tickRandomly().doesNotBlockMovement().sound(SoundType.PLANT));
    public static final Block ZUNDATOFUSAPLING = new TofuSaplingBlock(new ZundaTofuTree(), Block.Properties.create(Material.PLANTS).hardnessAndResistance(0.0F).tickRandomly().doesNotBlockMovement().sound(SoundType.PLANT));
    //Util block
    public static final Block TOFUPORTAL = new TofuPortalBlock(Block.Properties.create(Material.PORTAL).harvestTool(ToolType.PICKAXE).hardnessAndResistance(-1.0F, 3000000.0F).sound(SoundType.GLASS));
    public static final Block SALTPAN = new SaltPanBlock(Block.Properties.create(Material.WOOD).tickRandomly().harvestTool(ToolType.AXE).hardnessAndResistance(1.0F).sound(SoundType.WOOD));
    public static final Block POTTED_TOFUFLOWER = new FlowerPotBlock(TOFUFLOWER, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F));


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
        //Stair
        registry.getRegistry().register(TOFUSTAIR_KINU.setRegistryName("tofustair_kinu"));
        registry.getRegistry().register(TOFUSTAIR_MOMEN.setRegistryName("tofustair_momen"));
        registry.getRegistry().register(TOFUSTAIR_ISHI.setRegistryName("tofustair_ishi"));
        registry.getRegistry().register(TOFUSTAIR_METAL.setRegistryName("tofustair_metal"));
        registry.getRegistry().register(TOFUSTAIR_ISHIBRICK.setRegistryName("tofustair_ishibrick"));
        //Torch
        registry.getRegistry().register(TOFUTORCH_KINU.setRegistryName("tofutorch_kinu"));
        registry.getRegistry().register(TOFUTORCH_MOMEN.setRegistryName("tofutorch_momen"));
        registry.getRegistry().register(TOFUTORCH_ISHI.setRegistryName("tofutorch_ishi"));
        registry.getRegistry().register(TOFUTORCH_METAL.setRegistryName("tofutorch_metal"));
        registry.getRegistry().register(WALLTOFUTORCH_KINU.setRegistryName("walltofutorch_kinu"));
        registry.getRegistry().register(WALLTOFUTORCH_MOMEN.setRegistryName("walltofutorch_momen"));
        registry.getRegistry().register(WALLTOFUTORCH_ISHI.setRegistryName("walltofutorch_ishi"));
        registry.getRegistry().register(WALLTOFUTORCH_METAL.setRegistryName("walltofutorch_metal"));
        //terrain
        registry.getRegistry().register(TOFUTERRAIN.setRegistryName("tofu_terrain"));
        registry.getRegistry().register(TOFUBEDROCK.setRegistryName("tofubedrock"));
        registry.getRegistry().register(TOFUFLOWER.setRegistryName("tofuflower"));
        registry.getRegistry().register(TOFULEAVES.setRegistryName("leaves_tofu"));
        registry.getRegistry().register(TOFUZUNDALEAVES.setRegistryName("leaves_zundatofu"));
        registry.getRegistry().register(TOFUSAPLING.setRegistryName("sapling_tofu"));
        registry.getRegistry().register(ZUNDATOFUSAPLING.setRegistryName("zundasapling_tofu"));
        //Util block
        registry.getRegistry().register(TOFUPORTAL.setRegistryName("tofuportal"));
        registry.getRegistry().register(SALTPAN.setRegistryName("blocksaltpan"));
        registry.getRegistry().register(POTTED_TOFUFLOWER.setRegistryName("potted_tofuflower"));
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> registry) {

        TofuItems.register(registry, new BlockItem(KINUTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(MOMENTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(ISHITOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(METALTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(GRILLEDTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new BlockItem(ISHITOFU_BRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new BlockItem(TOFUSTAIR_KINU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSTAIR_MOMEN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSTAIR_ISHI, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSTAIR_METAL, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSTAIR_ISHIBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new WallOrFloorItem(TOFUTORCH_KINU, WALLTOFUTORCH_KINU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new WallOrFloorItem(TOFUTORCH_MOMEN, WALLTOFUTORCH_MOMEN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new WallOrFloorItem(TOFUTORCH_ISHI, WALLTOFUTORCH_ISHI, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new WallOrFloorItem(TOFUTORCH_METAL, WALLTOFUTORCH_METAL, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new BlockItem(TOFUTERRAIN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUBEDROCK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUFLOWER, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFULEAVES, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUZUNDALEAVES, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSAPLING, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(ZUNDATOFUSAPLING, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new BlockItem(SALTPAN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
    }

}