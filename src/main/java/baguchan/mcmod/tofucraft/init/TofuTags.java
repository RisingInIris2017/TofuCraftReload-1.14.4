package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class TofuTags {
    public static class Fluids {

        public static final Tag<Fluid> SOYMILK = tag("soymilk");

        private static Tag<Fluid> tag(String name) {
            return new FluidTags.Wrapper(new ResourceLocation(TofuCraftCore.MODID, name));
        }
    }

    public static class Items {

        public static final Tag<Item> TOFU = tag("tofu");


        private static Tag<Item> tag(String name) {
            return new ItemTags.Wrapper(new ResourceLocation(TofuCraftCore.MODID, name));
        }
    }

    public static class Blocks {

        public static final Tag<Block> TOFULOG = tag("tofulogs");
        public static final Tag<Block> TOFUTERRAIN = tag("tofu_terrain");

        private static Tag<Block> tag(String name) {
            return new BlockTags.Wrapper(new ResourceLocation(TofuCraftCore.MODID, name));
        }
    }
}
