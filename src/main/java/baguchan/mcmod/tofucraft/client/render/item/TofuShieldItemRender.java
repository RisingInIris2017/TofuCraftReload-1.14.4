package baguchan.mcmod.tofucraft.client.render.item;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.init.TofuItems;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuShieldItemRender extends ItemStackTileEntityRenderer {
    private final static ResourceLocation ISHI_TEXTURE = new ResourceLocation(TofuCraftCore.MODID, "textures/entity/tofuishi_shield.png");
    private final static ResourceLocation METAL_TEXTURE = new ResourceLocation(TofuCraftCore.MODID, "textures/entity/tofumetal_shield.png");

    private final ShieldModel shieldModel = new ShieldModel(); // TODO model rockshroom

    @Override
    public void renderByItem(ItemStack stack) {
        if (stack.getItem() == TofuItems.TOFUISHI_SHIELD) {
            Minecraft.getInstance().getTextureManager().bindTexture(ISHI_TEXTURE);
            GlStateManager.pushMatrix();
            GlStateManager.scalef(1f, -1f, -1f);
            this.shieldModel.render();
            if (stack.hasEffect()) {
                this.renderEffect(this.shieldModel::render);
            }
            GlStateManager.popMatrix();
        } else if (stack.getItem() == TofuItems.TOFUMETAL_SHIELD) {
            Minecraft.getInstance().getTextureManager().bindTexture(METAL_TEXTURE);
            GlStateManager.pushMatrix();
            GlStateManager.scalef(1f, -1f, -1f);
            this.shieldModel.render();
            if (stack.hasEffect()) {
                this.renderEffect(this.shieldModel::render);
            }
            GlStateManager.popMatrix();
        }
    }

    private void renderEffect(Runnable renderModelFunction) {
        GlStateManager.color3f(0.5019608F, 0.2509804F, 0.8F);
        Minecraft.getInstance().getTextureManager().bindTexture(ItemRenderer.RES_ITEM_GLINT);
        ItemRenderer.renderEffect(Minecraft.getInstance().getTextureManager(), renderModelFunction, 1);
    }
}