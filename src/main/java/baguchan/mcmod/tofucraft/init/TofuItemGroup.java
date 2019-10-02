package baguchan.mcmod.tofucraft.init;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TofuItemGroup {
    public static final ItemGroup TOFUCRAFT = new ItemGroup("tofucraft") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(TofuItems.TOFUMOMEN);
        }
    };
}
