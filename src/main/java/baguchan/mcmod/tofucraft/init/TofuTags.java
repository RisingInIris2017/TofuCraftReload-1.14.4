package baguchan.mcmod.tofucraft.init;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class TofuTags {
    public static class Fluids {

        public static final Tag<Fluid> SOYMILK = tag("soymilk");

        private static Tag<Fluid> tag(String name) {
            return new FluidTags.Wrapper(new ResourceLocation(TofuCraftCore.MODID, name));
        }
    }
}
