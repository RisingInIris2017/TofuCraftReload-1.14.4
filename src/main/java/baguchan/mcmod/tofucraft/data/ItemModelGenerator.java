package baguchan.mcmod.tofucraft.data;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;

import static baguchan.mcmod.tofucraft.TofuCraftCore.prefix;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TofuCraftCore.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        toBlock(TofuBlocks.KINUTOFU);
        toBlock(TofuBlocks.MOMENTOFU);
        toBlock(TofuBlocks.ISHITOFU);
        toBlock(TofuBlocks.METALTOFU);
        toBlock(TofuBlocks.DIAMONDTOFU);
    }

    private void generated(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
    }

    private void woodenButton(Block button, String variant) {
        getBuilder(button.getRegistryName().getPath())
                .parent(getExistingFile(mcLoc("block/button_inventory")))
                .texture("texture", "block/wood/planks_" + variant + "_0");
    }

    private void woodenFence(Block fence, String variant) {
        getBuilder(fence.getRegistryName().getPath())
                .parent(getExistingFile(mcLoc("block/fence_inventory")))
                .texture("texture", "block/wood/planks_" + variant + "_0");
    }

    private void toBlock(Block b) {
        toBlockModel(b, b.getRegistryName().getPath());
    }

    private void toBlockModel(Block b, String model) {
        withExistingParent(b.getRegistryName().getPath(), prefix("block/" + model));
    }

    @Override
    public String getName() {
        return "TofuCraft item and itemblock models";
    }


}