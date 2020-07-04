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

        public static final ITag.INamedTag<Fluid> SOYMILK = makeWrapperTag("soymilk");

        public static ITag.INamedTag<Fluid> makeWrapperTag(String id) {
            return FluidTags.makeWrapperTag("tofucraft:" + id);
        }
    }

    public static class Items {

        public static final ITag.INamedTag<Item> TOFU = makeWrapperTag("tofu");

        public static ITag.INamedTag<Item> makeWrapperTag(String id) {
            return ItemTags.makeWrapperTag("tofucraft:" + id);
        }
    }

    public static class Blocks {

        public static final ITag.INamedTag<Block> TOFULOG = makeWrapperTag("tofulogs");
        public static final ITag.INamedTag<Block> TOFUTERRAIN = makeWrapperTag("tofu_terrain");


        public static ITag.INamedTag<Block> makeWrapperTag(String id) {
            return BlockTags.makeWrapperTag("tofucraft:" + id);
        }
    }
}
