package baguchan.mcmod.tofucraft.item;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TofuStickItem extends Item {
    public TofuStickItem(Properties group) {
        super(group);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {

        if (context.getWorld().getBlockState(context.getPos()).getBlock() == TofuBlocks.GRILLEDTOFU) {
            //Consume when operation succeeds
            if (TofuBlocks.TOFUPORTAL.trySpawnPortal(context.getWorld(), context.getPos().offset(context.getFace()))) {
                if (!context.getPlayer().isCreative()) {
                    context.getItem().shrink(1);
                }
            }
        }
        return super.onItemUse(context);
    }

    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean hasEffect(ItemStack stack) {

        return true;

    }
}
