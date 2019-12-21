package baguchan.mcmod.tofucraft.client.screen;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.container.TFStorageContainer;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class TFStorageScreen extends ContainerScreen<TFStorageContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(TofuCraftCore.MODID, "textures/gui/storage_machine.png");
    private final TFStorageContainer tileStorage;

    public TFStorageScreen(TFStorageContainer containerIn, PlayerInventory playerInv, ITextComponent title) {
        super(containerIn, playerInv, title);
        this.tileStorage = containerIn;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTick) {
        super.render(mouseX, mouseY, partialTick);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 8f, 4f, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8f, (float) (this.ySize - 94), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.renderBackground();
        GlStateManager.color4f(1f, 1f, 1f, 1f);
        getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.blit(var5, var6, 0, 0, this.xSize, this.ySize);
        int var7;

        // Progress arrow
        var7 = this.tileStorage.getCookProgressionScaled();
        this.blit(var5 + 46, var6 + 34, 176, 14, var7 + 1, 16);

    }
}