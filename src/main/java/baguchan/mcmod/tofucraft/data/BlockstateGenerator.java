package baguchan.mcmod.tofucraft.data;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

import static baguchan.mcmod.tofucraft.TofuCraftCore.prefix;

public class BlockstateGenerator extends BlockStateProvider {
    public BlockstateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, TofuCraftCore.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(TofuBlocks.TOFUBED).partialState()
                .setModels(ConfiguredModel.builder().modelFile(models().getExistingFile(prefix("block/tofubed"))).build());
        getVariantBuilder(TofuBlocks.TOFUCHEST).partialState()
                .setModels(ConfiguredModel.builder().modelFile(models().getExistingFile(prefix("block/tofuchest"))).build());
        getVariantBuilder(TofuBlocks.WHEAT_BOWL).partialState()
                .setModels(ConfiguredModel.builder().modelFile(models().getExistingFile(prefix("block/wheat_bowl"))).build());

        registerTofuBlocks();
        registerPlantBlocks();
        registerWoodBlocks();
    }

    private void registerTofuBlocks() {
        simpleBlock(TofuBlocks.KINUTOFU);
        simpleBlock(TofuBlocks.MOMENTOFU);
        simpleBlock(TofuBlocks.ISHITOFU);
        simpleBlock(TofuBlocks.ISHITOFU_BRICK);
        simpleBlock(TofuBlocks.METALTOFU);
        simpleBlock(TofuBlocks.DIAMONDTOFU);
        simpleBlock(TofuBlocks.GRILLEDTOFU);
        simpleBlock(TofuBlocks.HELLTOFU);
        simpleBlock(TofuBlocks.ZUNDATOFU);
        simpleBlock(TofuBlocks.ZUNDAISHITOFU);
        simpleBlock(TofuBlocks.ZUNDATOFU_BRICK);
        simpleBlock(TofuBlocks.ZUNDATOFU_SMOOTH_BRICK);

        simpleBlock(TofuBlocks.CONCRETE_TOFU);
        simpleBlock(TofuBlocks.CONCRETE_POWDER_TOFU);
    }

    private void registerPlantBlocks() {
        plantBlocks(TofuBlocks.LEEK);
        plantBlocks(TofuBlocks.TOFUFLOWER);
        plantBlocks(TofuBlocks.TOFUSAPLING);
        plantBlocks(TofuBlocks.ZUNDATOFUSAPLING);
        plantBlocks(TofuBlocks.ZUNDATOFU_MUSHROOM);
    }

    private void registerWoodBlocks() {
        singleBlockBoilerPlate(TofuBlocks.TOFULEAVES, "block/leaves", m -> m.texture("all", "block/leaves_tofu"));
        singleBlockBoilerPlate(TofuBlocks.TOFUZUNDALEAVES, "block/leaves", m -> m.texture("all", "block/leaves_zundatofu"));

        tofuSlabBlock(TofuBlocks.TOFUSLAB_KINU, TofuBlocks.KINUTOFU, new ResourceLocation("tofucraft:block/tofuslab_kinu"));
        tofuSlabBlock(TofuBlocks.TOFUSLAB_MOMEN, TofuBlocks.MOMENTOFU, new ResourceLocation("tofucraft:block/tofuslab_momen"));
        tofuSlabBlock(TofuBlocks.TOFUSLAB_ISHI, TofuBlocks.ISHITOFU, new ResourceLocation("tofucraft:block/tofuslab_ishi"));
        tofuSlabBlock(TofuBlocks.TOFUSLAB_ISHIBRICK, TofuBlocks.ISHITOFU_BRICK, new ResourceLocation("tofucraft:block/tofuslab_ishibrick"));
        tofuSlabBlock(TofuBlocks.TOFUSLAB_METAL, TofuBlocks.METALTOFU, new ResourceLocation("tofucraft:block/tofuslab_metal"));
        tofuSlabBlock(TofuBlocks.TOFUSLAB_ZUNDA, TofuBlocks.ZUNDATOFU, new ResourceLocation("tofucraft:block/tofuslab_zunda"));
        tofuSlabBlock(TofuBlocks.TOFUSLAB_ZUNDABRICK, TofuBlocks.ZUNDATOFU_BRICK, new ResourceLocation("tofucraft:block/tofuslab_zundabrick"));

        stairsBlock(TofuBlocks.TOFUSTAIR_KINU, new ResourceLocation("tofucraft:block/tofustair_kinu"));
        stairsBlock(TofuBlocks.TOFUSTAIR_MOMEN, new ResourceLocation("tofucraft:block/tofustair_momen"));
        stairsBlock(TofuBlocks.TOFUSTAIR_ISHI, new ResourceLocation("tofucraft:block/tofustair_ishi"));
        stairsBlock(TofuBlocks.TOFUSTAIR_ISHIBRICK, new ResourceLocation("tofucraft:block/tofustair_ishibrick"));
        stairsBlock(TofuBlocks.TOFUSTAIR_METAL, new ResourceLocation("tofucraft:block/tofustair_metal"));
        stairsBlock(TofuBlocks.TOFUSTAIR_ZUNDA, new ResourceLocation("tofucraft:block/tofustair_zunda"));
        stairsBlock(TofuBlocks.TOFUSTAIR_ZUNDABRICK, new ResourceLocation("tofucraft:block/tofustair_zundabrick"));
    }

    public void tofuSlabBlock(SlabBlock slab, Block block, ResourceLocation texture) {

        slabBlock(slab, blockTexture(block), texture, texture, texture);
    }

    /**
     * Generates a blockstate json pointing to one single generated model with the same name as the block
     * The single generated model has the given parent and can be customized.
     */
    private void singleBlockBoilerPlate(Block b, String parent, Consumer<BlockModelBuilder> modelCustomizer) {
        BlockModelBuilder bModel = models().withExistingParent(b.getRegistryName().getPath(), parent);
        modelCustomizer.accept(bModel);
        simpleBlock(b, bModel);
    }

    private void logWoodSapling(LogBlock log, Block wood, Block sapling) {
        logBlock(log);
        ResourceLocation sideTex = blockTexture(log);
        getVariantBuilder(wood).partialState()
                .setModels(ConfiguredModel.builder().modelFile(models().cubeAll(wood.getRegistryName().getPath(), sideTex)).build());

        ResourceLocation saplingTex = prefix("block/" + sapling.getRegistryName().getPath());
        getVariantBuilder(sapling).partialState()
                .setModels(ConfiguredModel.builder().modelFile(models().cross(sapling.getRegistryName().getPath(), saplingTex)).build());
    }

    private void plantBlocks(Block plant) {
        ResourceLocation saplingTex = prefix("block/" + plant.getRegistryName().getPath());
        getVariantBuilder(plant).partialState()
                .setModels(ConfiguredModel.builder().modelFile(models().cross(plant.getRegistryName().getPath(), saplingTex)).build());
    }


    @Nonnull
    @Override
    public String getName() {
        return "TofuCraft blockstates and block models";
    }
}