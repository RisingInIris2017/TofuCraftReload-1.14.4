package baguchan.mcmod.tofucraft.client.screen;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.container.SaltFurnaceContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SaltFurnaceScreen extends ContainerScreen<SaltFurnaceContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TofuCraftCore.MODID, "textures/gui/salt_furnace.png");

    public SaltFurnaceScreen(SaltFurnaceContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(p_230450_1_, i, j, 0, 0, this.xSize, this.ySize);
        if (this.container.isBurning()) {
            int k = this.container.getBurnLeftScaled();
            this.blit(p_230450_1_, i + 23, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }
    }

}