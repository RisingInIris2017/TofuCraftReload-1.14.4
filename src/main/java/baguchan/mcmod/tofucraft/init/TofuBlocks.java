package baguchan.mcmod.tofucraft.init;


import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.block.*;
import baguchan.mcmod.tofucraft.client.render.tileentity.TofuChestItemRender;
import baguchan.mcmod.tofucraft.world.tree.TofuTree;
import baguchan.mcmod.tofucraft.world.tree.ZundaTofuTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;
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
    public static final Block RICE = new RiceCropsBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP));
    public static final Block RICE_ROOT = new RiceRootBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP));

    public static final Block KINUTOFU = new TofuBlock(Block.Properties.create(TofuMaterial.TOFU).tickRandomly().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.1F, 0.2F).sound(SoundType.SNOW));
    public static final Block MOMENTOFU = new TofuBlock(Block.Properties.create(TofuMaterial.TOFU).tickRandomly().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.35F, 0.5F).sound(SoundType.SNOW));
    public static final Block ISHITOFU = new TofuBlock(Block.Properties.create(Material.ROCK).tickRandomly().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE));
    public static final Block METALTOFU = new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(4.0F, 7.5F).sound(SoundType.METAL));
    public static final Block DIAMONDTOFU = new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 8.0F).sound(SoundType.METAL));
    public static final Block GRILLEDTOFU = new Block(Block.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.25F, 0.5F).sound(SoundType.SNOW));
    public static final Block ZUNDATOFU = new Block(Block.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.25F, 0.5F).sound(SoundType.SNOW));

    public static final Block ISHITOFU_BRICK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.6F, 6.5F).sound(SoundType.STONE));
    public static final Block ZUNDATOFU_BRICK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.6F, 6.5F).sound(SoundType.STONE));
    public static final Block ZUNDATOFU_SMOOTHBRICK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.6F, 6.5F).sound(SoundType.STONE));

    //Stair
    public static final Block TOFUSTAIR_KINU = new StairsBlock(KINUTOFU::getDefaultState, Block.Properties.from(KINUTOFU));
    public static final Block TOFUSTAIR_MOMEN = new StairsBlock(MOMENTOFU::getDefaultState, Block.Properties.from(MOMENTOFU));
    public static final Block TOFUSTAIR_ISHI = new StairsBlock(ISHITOFU::getDefaultState, Block.Properties.from(ISHITOFU));
    public static final Block TOFUSTAIR_METAL = new StairsBlock(METALTOFU::getDefaultState, Block.Properties.from(METALTOFU));
    public static final Block TOFUSTAIR_ZUNDA = new StairsBlock(ZUNDATOFU::getDefaultState, Block.Properties.from(ZUNDATOFU));
    public static final Block TOFUSTAIR_ZUNDABRICK = new StairsBlock(ZUNDATOFU_BRICK::getDefaultState, Block.Properties.from(ZUNDATOFU_BRICK));
    public static final Block TOFUSTAIR_ISHIBRICK = new StairsBlock(ISHITOFU_BRICK::getDefaultState, Block.Properties.from(ISHITOFU_BRICK));
    //Slab
    public static final Block TOFUSLAB_KINU = new SlabBlock(Block.Properties.from(KINUTOFU));
    public static final Block TOFUSLAB_MOMEN = new SlabBlock(Block.Properties.from(MOMENTOFU));
    public static final Block TOFUSLAB_ISHI = new SlabBlock(Block.Properties.from(ISHITOFU));
    public static final Block TOFUSLAB_METAL = new SlabBlock(Block.Properties.from(METALTOFU));
    public static final Block TOFUSLAB_ZUNDA = new SlabBlock(Block.Properties.from(ZUNDATOFU));
    public static final Block TOFUSLAB_ZUNDABRICK = new SlabBlock(Block.Properties.from(ZUNDATOFU_BRICK));
    public static final Block TOFUSLAB_ISHIBRICK = new SlabBlock(Block.Properties.from(ISHITOFU_BRICK));
    //torch
    public static final Block TOFUTORCH_KINU = new TofuTorchBlock(Block.Properties.create(TofuMaterial.TOFU).hardnessAndResistance(0.0F, 0.5F).lightValue(14).doesNotBlockMovement().sound(SoundType.SNOW));
    public static final Block TOFUTORCH_MOMEN = new TofuTorchBlock(Block.Properties.create(TofuMaterial.TOFU).hardnessAndResistance(0.0F, 0.5F).lightValue(14).doesNotBlockMovement().sound(SoundType.SNOW));
    public static final Block TOFUTORCH_ISHI = new TofuTorchBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.0F, 6.0F).lightValue(14).doesNotBlockMovement().sound(SoundType.STONE));
    public static final Block TOFUTORCH_METAL = new TofuTorchBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.0F, 7.5F).lightValue(14).doesNotBlockMovement().sound(SoundType.METAL));
    public static final Block WALLTOFUTORCH_KINU = new WallTofuTorchBlock(Block.Properties.create(TofuMaterial.TOFU).hardnessAndResistance(0.0F, 0.5F).lightValue(14).doesNotBlockMovement().sound(SoundType.SNOW).lootFrom(TOFUTORCH_KINU));
    public static final Block WALLTOFUTORCH_MOMEN = new WallTofuTorchBlock(Block.Properties.create(TofuMaterial.TOFU).hardnessAndResistance(0.0F, 0.5F).lightValue(14).doesNotBlockMovement().sound(SoundType.SNOW).lootFrom(TOFUTORCH_MOMEN));
    public static final Block WALLTOFUTORCH_ISHI = new WallTofuTorchBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.0F, 6.0F).lightValue(14).doesNotBlockMovement().sound(SoundType.STONE).lootFrom(TOFUTORCH_ISHI));
    public static final Block WALLTOFUTORCH_METAL = new WallTofuTorchBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(0.0F, 7.5F).lightValue(14).doesNotBlockMovement().sound(SoundType.METAL).lootFrom(TOFUTORCH_METAL));
    //Ladder
    public static final Block TOFULADDER_KINU = new TofuLadderBlock(Block.Properties.from(KINUTOFU));
    public static final Block TOFULADDER_MOMEN = new TofuLadderBlock(Block.Properties.from(MOMENTOFU));
    public static final Block TOFULADDER_ISHI = new TofuLadderBlock(Block.Properties.from(ISHITOFU));
    public static final Block TOFULADDER_METAL = new TofuLadderBlock(Block.Properties.from(METALTOFU));
    //public static final Block TOFULADDER_ZUNDABRICK = new TofuLadderBlock(Block.Properties.from(ZUNDATOFU_BRICK));
    public static final Block TOFULADDER_ISHIBRICK = new TofuLadderBlock(Block.Properties.from(ISHITOFU_BRICK));

    //DOOR
    public static final Block TOFUDOOR_KINU = new TofuDoorBlock(Block.Properties.create(TofuMaterial.TOFU).tickRandomly().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.1F, 0.2F).sound(SoundType.SNOW));
    public static final Block TOFUDOOR_MOMEN = new TofuDoorBlock(Block.Properties.create(TofuMaterial.TOFU).tickRandomly().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.35F, 0.5F).sound(SoundType.SNOW));
    public static final Block TOFUDOOR_ISHI = new TofuDoorBlock(Block.Properties.create(Material.ROCK).tickRandomly().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE));
    public static final Block TOFUDOOR_METAL = new TofuDoorBlock(Block.Properties.from(METALTOFU));

    //TERRAIN
    public static final Block TOFUSTEM = new RotatedPillarBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.0F, 5.0F).sound(SoundType.WOOD));
    public static final Block TOFUTERRAIN = new Block(Block.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.45F, 0.85F).sound(SoundType.SNOW));
    public static final Block ORE_TOFUDIAMOND = new Block(Block.Properties.create(TofuMaterial.TOFUORE).harvestTool(ToolType.SHOVEL).harvestLevel(2).hardnessAndResistance(1.0F, 2.0F).sound(SoundType.SNOW));
    public static final Block TOFUBEDROCK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(-1.0F, 2000000.0F).sound(SoundType.STONE));
    public static final Block TOFUFLOWER = new TofuFlowerBlock(Effects.ABSORPTION, 20, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
    public static final Block TOFULEAVES = new TofuLeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.5F).tickRandomly().sound(SoundType.PLANT));
    public static final Block TOFUZUNDALEAVES = new TofuLeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.5F).tickRandomly().sound(SoundType.PLANT));
    public static final Block TOFUSAPLING = new TofuSaplingBlock(new TofuTree(), Block.Properties.create(Material.PLANTS).hardnessAndResistance(0.0F).tickRandomly().doesNotBlockMovement().sound(SoundType.PLANT));
    public static final Block ZUNDATOFUSAPLING = new TofuSaplingBlock(new ZundaTofuTree(), Block.Properties.create(Material.PLANTS).hardnessAndResistance(0.0F).tickRandomly().doesNotBlockMovement().sound(SoundType.PLANT));

    public static final Block TOFUBERRYSTEM = new TofuBerryStemBlock(Block.Properties.create(Material.PLANTS).hardnessAndResistance(1.0F).tickRandomly().sound(SoundType.CLOTH));
    public static final Block TOFUBERRY = new TofuBerryBlock(Block.Properties.create(Material.PLANTS).hardnessAndResistance(0.65F).tickRandomly().lightValue(9).sound(SoundType.CLOTH));

    //Util block
    public static final TofuPortalBlock TOFUPORTAL = new TofuPortalBlock(Block.Properties.create(Material.PORTAL).doesNotBlockMovement().harvestTool(ToolType.PICKAXE).hardnessAndResistance(-1.0F, 3000000.0F).lightValue(10).sound(SoundType.GLASS));
    public static final Block SALTPAN = new SaltPanBlock(Block.Properties.create(Material.WOOD).tickRandomly().harvestTool(ToolType.AXE).hardnessAndResistance(1.0F).sound(SoundType.WOOD));
    public static final Block POTTED_TOFUFLOWER = new FlowerPotBlock(TOFUFLOWER, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F));
    public static final Block TOFUCHEST = new TofuChestBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE));
    public static final Block WHEAT_BOWL = new WheatBowlBlock(Block.Properties.create(Material.ORGANIC).hardnessAndResistance(0.5F, 1.0F).tickRandomly().sound(SoundType.PLANT));
    public static final Block TOFUFARMLAND = new TofuFarmlandBlock(Block.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.45F, 0.85F).tickRandomly().sound(SoundType.CLOTH));
    //Barrel
    public static final Block BARREL_MISO = new MisoBarrelBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(0.5F, 2.0F).tickRandomly().sound(SoundType.WOOD));
    //Misc
    public static final Block TOFUGANDLEM_CORE = new TofuGandlemCoreBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(10000.0F).tickRandomly().sound(SoundType.STONE));


    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> registry) {
        //Terrain
        registry.getRegistry().register(SOYMILK.setRegistryName("soymilk"));
        registry.getRegistry().register(SOYBEAN.setRegistryName("soybean"));
        registry.getRegistry().register(RICE.setRegistryName("rice"));
        registry.getRegistry().register(RICE_ROOT.setRegistryName("rice_root"));
        registry.getRegistry().register(KINUTOFU.setRegistryName("blocktofukinu"));
        registry.getRegistry().register(MOMENTOFU.setRegistryName("blocktofumomen"));
        registry.getRegistry().register(ISHITOFU.setRegistryName("blocktofuishi"));
        registry.getRegistry().register(METALTOFU.setRegistryName("blocktofumetal"));
        registry.getRegistry().register(DIAMONDTOFU.setRegistryName("blocktofudiamond"));
        registry.getRegistry().register(GRILLEDTOFU.setRegistryName("blocktofugrilled"));
        registry.getRegistry().register(ZUNDATOFU.setRegistryName("blocktofuzunda"));

        registry.getRegistry().register(ISHITOFU_BRICK.setRegistryName("tofuishi_brick"));
        registry.getRegistry().register(ZUNDATOFU_BRICK.setRegistryName("tofuzunda_brick"));
        registry.getRegistry().register(ZUNDATOFU_SMOOTHBRICK.setRegistryName("tofuzunda_smoothbrick"));
        //Stair
        registry.getRegistry().register(TOFUSTAIR_KINU.setRegistryName("tofustair_kinu"));
        registry.getRegistry().register(TOFUSTAIR_MOMEN.setRegistryName("tofustair_momen"));
        registry.getRegistry().register(TOFUSTAIR_ISHI.setRegistryName("tofustair_ishi"));
        registry.getRegistry().register(TOFUSTAIR_METAL.setRegistryName("tofustair_metal"));
        registry.getRegistry().register(TOFUSTAIR_ZUNDA.setRegistryName("tofustair_zunda"));
        registry.getRegistry().register(TOFUSTAIR_ZUNDABRICK.setRegistryName("tofustair_zundabrick"));
        registry.getRegistry().register(TOFUSTAIR_ISHIBRICK.setRegistryName("tofustair_ishibrick"));
        //Slab
        registry.getRegistry().register(TOFUSLAB_KINU.setRegistryName("tofuslab_kinu"));
        registry.getRegistry().register(TOFUSLAB_MOMEN.setRegistryName("tofuslab_momen"));
        registry.getRegistry().register(TOFUSLAB_ISHI.setRegistryName("tofuslab_ishi"));
        registry.getRegistry().register(TOFUSLAB_ISHIBRICK.setRegistryName("tofuslab_ishibrick"));
        registry.getRegistry().register(TOFUSLAB_ZUNDA.setRegistryName("tofuslab_zunda"));
        registry.getRegistry().register(TOFUSLAB_ZUNDABRICK.setRegistryName("tofuslab_zundabrick"));
        registry.getRegistry().register(TOFUSLAB_METAL.setRegistryName("tofuslab_metal"));
        //Torch
        registry.getRegistry().register(TOFUTORCH_KINU.setRegistryName("tofutorch_kinu"));
        registry.getRegistry().register(TOFUTORCH_MOMEN.setRegistryName("tofutorch_momen"));
        registry.getRegistry().register(TOFUTORCH_ISHI.setRegistryName("tofutorch_ishi"));
        registry.getRegistry().register(TOFUTORCH_METAL.setRegistryName("tofutorch_metal"));
        registry.getRegistry().register(WALLTOFUTORCH_KINU.setRegistryName("walltofutorch_kinu"));
        registry.getRegistry().register(WALLTOFUTORCH_MOMEN.setRegistryName("walltofutorch_momen"));
        registry.getRegistry().register(WALLTOFUTORCH_ISHI.setRegistryName("walltofutorch_ishi"));
        registry.getRegistry().register(WALLTOFUTORCH_METAL.setRegistryName("walltofutorch_metal"));
        //Ladder
        registry.getRegistry().register(TOFULADDER_KINU.setRegistryName("tofuladder_kinu"));
        registry.getRegistry().register(TOFULADDER_MOMEN.setRegistryName("tofuladder_momen"));
        registry.getRegistry().register(TOFULADDER_ISHI.setRegistryName("tofuladder_ishi"));
        registry.getRegistry().register(TOFULADDER_ISHIBRICK.setRegistryName("tofuladder_ishibrick"));
        registry.getRegistry().register(TOFULADDER_METAL.setRegistryName("tofuladder_metal"));
        //Door
        registry.getRegistry().register(TOFUDOOR_KINU.setRegistryName("tofudoor_kinu"));
        registry.getRegistry().register(TOFUDOOR_MOMEN.setRegistryName("tofudoor_momen"));
        registry.getRegistry().register(TOFUDOOR_ISHI.setRegistryName("tofudoor_ishi"));
        registry.getRegistry().register(TOFUDOOR_METAL.setRegistryName("tofudoor_metal"));
        //terrain
        registry.getRegistry().register(TOFUSTEM.setRegistryName("tofustem"));
        registry.getRegistry().register(TOFUTERRAIN.setRegistryName("tofu_terrain"));
        registry.getRegistry().register(ORE_TOFUDIAMOND.setRegistryName("ore_tofudiamond"));
        registry.getRegistry().register(TOFUBEDROCK.setRegistryName("tofubedrock"));
        registry.getRegistry().register(TOFUFLOWER.setRegistryName("tofuflower"));
        registry.getRegistry().register(TOFULEAVES.setRegistryName("leaves_tofu"));
        registry.getRegistry().register(TOFUZUNDALEAVES.setRegistryName("leaves_zundatofu"));
        registry.getRegistry().register(TOFUSAPLING.setRegistryName("sapling_tofu"));
        registry.getRegistry().register(ZUNDATOFUSAPLING.setRegistryName("zundasapling_tofu"));
        registry.getRegistry().register(TOFUBERRYSTEM.setRegistryName("tofuberry_stem"));
        registry.getRegistry().register(TOFUBERRY.setRegistryName("tofuberry"));
        //Util block
        registry.getRegistry().register(TOFUPORTAL.setRegistryName("tofuportal"));
        registry.getRegistry().register(SALTPAN.setRegistryName("blocksaltpan"));
        registry.getRegistry().register(POTTED_TOFUFLOWER.setRegistryName("potted_tofuflower"));
        registry.getRegistry().register(TOFUCHEST.setRegistryName("tofuchest"));
        registry.getRegistry().register(WHEAT_BOWL.setRegistryName("wheat_bowl"));
        registry.getRegistry().register(TOFUFARMLAND.setRegistryName("tofu_farmland"));
        //Barrel
        registry.getRegistry().register(BARREL_MISO.setRegistryName("barrelmiso"));

        registry.getRegistry().register(TOFUGANDLEM_CORE.setRegistryName("tofugandlem_core"));
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> registry) {

        TofuItems.register(registry, new BlockItem(KINUTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(MOMENTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(ISHITOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(METALTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(DIAMONDTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(GRILLEDTOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(ZUNDATOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new BlockItem(ISHITOFU_BRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(ZUNDATOFU_BRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(ZUNDATOFU_SMOOTHBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new BlockItem(TOFUSTAIR_KINU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSTAIR_MOMEN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSTAIR_ISHI, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSTAIR_METAL, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSTAIR_ZUNDA, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSTAIR_ZUNDABRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSTAIR_ISHIBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new BlockItem(TOFUSLAB_KINU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSLAB_MOMEN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSLAB_ISHI, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSLAB_METAL, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSLAB_ZUNDA, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSLAB_ZUNDABRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSLAB_ISHIBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new WallOrFloorItem(TOFUTORCH_KINU, WALLTOFUTORCH_KINU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new WallOrFloorItem(TOFUTORCH_MOMEN, WALLTOFUTORCH_MOMEN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new WallOrFloorItem(TOFUTORCH_ISHI, WALLTOFUTORCH_ISHI, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new WallOrFloorItem(TOFUTORCH_METAL, WALLTOFUTORCH_METAL, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new BlockItem(TOFULADDER_KINU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFULADDER_MOMEN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFULADDER_ISHI, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFULADDER_METAL, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFULADDER_ISHIBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new TallBlockItem(TOFUDOOR_KINU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new TallBlockItem(TOFUDOOR_MOMEN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new TallBlockItem(TOFUDOOR_ISHI, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new TallBlockItem(TOFUDOOR_METAL, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new BlockItem(TOFUSTEM, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUTERRAIN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(ORE_TOFUDIAMOND, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUBEDROCK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUFLOWER, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFULEAVES, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUZUNDALEAVES, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUSAPLING, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(ZUNDATOFUSAPLING, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUBERRY, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new BlockItem(SALTPAN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUCHEST, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT).setTEISR(() -> TofuChestItemRender::new)));
        TofuItems.register(registry, new BlockItem(WHEAT_BOWL, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
        TofuItems.register(registry, new BlockItem(TOFUFARMLAND, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

        TofuItems.register(registry, new BlockItem(BARREL_MISO, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
    }

}