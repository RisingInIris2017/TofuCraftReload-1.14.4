package baguchan.mcmod.tofucraft.client.screen;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.container.SaltFurnaceContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SaltFurnaceScreen extends ContainerScreen<SaltFurnaceContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TofuCraftCore.MODID, "textures/gui/salt_furnace.png");

    public SaltFurnaceScreen(SaltFurnaceContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 12.0F, 5.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderHelper.setupGuiFlatDiffuseLighting();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        int i2 = (this.width - this.xSize) / 2;
        int j2 = (this.height - this.ySize) / 2;
        this.blit(i2, j2, 0, 0, this.xSize, this.ySize);

        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);
        if (((SaltFurnaceContainer) this.container).isBurning()) {
            int k = ((SaltFurnaceContainer) this.container).getBurnLeftScaled();
            this.blit(i + 23, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }
    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        p_render_3_ = this.minecraft.getRenderPartialTicks();
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);

    }

}