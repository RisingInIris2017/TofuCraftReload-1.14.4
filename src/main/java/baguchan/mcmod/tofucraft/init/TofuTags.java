package baguchan.mcmod.tofucraft.init;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class TofuTags {
    public static class Fluids {

        public static final ITag.INamedTag<Fluid> SOYMILK = FluidTags.makeWrapperTag("soymilk");

    }

    public static class Items {

        public static final ITag.INamedTag<Item> TOFU = ItemTags.makeWrapperTag("tofu");

    }

    public static class Blocks {

        public static final ITag.INamedTag<Block> TOFULOG = BlockTags.makeWrapperTag("tofulogs");
        public static final ITag.INamedTag<Block> TOFUTERRAIN = BlockTags.makeWrapperTag("tofu_terrain");

    }
}
