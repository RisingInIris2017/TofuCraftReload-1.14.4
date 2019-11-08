package baguchan.mcmod.tofucraft.client.render.tileentity;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.block.TofuChestBlock;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.tileentity.TofuChestTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.model.ChestModel;
import net.minecraft.client.renderer.tileentity.model.LargeChestModel;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuChestBlockRenderer extends TileEntityRenderer<TofuChestTileEntity> {
    private static final ResourceLocation TEXTURE_NORMALTOFU_DOUBLE = new ResourceLocation(TofuCraftCore.MODID, "textures/entity/chest/tofuchest_double.png");
    private static final ResourceLocation TEXTURE_NORMALTOFU_NORMAL = new ResourceLocation(TofuCraftCore.MODID, "textures/entity/chest/tofuchest.png");
    private final ChestModel simpleChest = new ChestModel();
    private final LargeChestModel largeChest = new LargeChestModel();

    public TofuChestBlockRenderer() {
    }

    private void bindTexture(Block chestModel, int destroyStage, boolean isDouble) {
        ResourceLocation rl = TEXTURE_NORMALTOFU_NORMAL;
        if (destroyStage >= 0) {
            rl = DESTROY_STAGES[destroyStage];
        } else if (chestModel == TofuBlocks.TOFUCHEST) {
            rl = isDouble ? TEXTURE_NORMALTOFU_DOUBLE : TEXTURE_NORMALTOFU_NORMAL;
        }
        bindTexture(rl);
    }

    @Override
    public void render(TofuChestTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.enableDepthTest();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);

        final Block blockModel;
        final BlockState state;
        if (te.hasWorld() && te.getBlockState().getBlock() instanceof TofuChestBlock) {
            state = te.getBlockState();
            blockModel = state.getBlock();
        } else {
            blockModel = te.getChestModel();
            state = blockModel.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        }
        ChestType chestType = state.has(ChestBlock.TYPE) ? state.get(ChestBlock.TYPE) : ChestType.SINGLE;

        if (chestType != ChestType.LEFT) {
            boolean flag = chestType != ChestType.SINGLE;
            ChestModel chestmodel = flag ? this.largeChest : this.simpleChest;
            bindTexture(blockModel, destroyStage, flag);
            if (destroyStage >= 0) {
                GlStateManager.matrixMode(5890);
                GlStateManager.pushMatrix();
                GlStateManager.scalef(flag ? 8.0F : 4.0F, 4.0F, 1.0F);
                GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
                GlStateManager.matrixMode(5888);
            } else {
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            }

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.translatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
            GlStateManager.scalef(1.0F, -1.0F, -1.0F);
            float f = state.get(ChestBlock.FACING).getHorizontalAngle();
            if ((double) Math.abs(f) > 1.0E-5D) {
                GlStateManager.translatef(0.5F, 0.5F, 0.5F);
                GlStateManager.rotatef(f, 0.0F, 1.0F, 0.0F);
                GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
            }

            this.applyLidRotation(te, partialTicks, chestmodel);
            chestmodel.renderAll();
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            if (destroyStage >= 0) {
                GlStateManager.matrixMode(5890);
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5888);
            }
        }
    }

    private void applyLidRotation(TofuChestTileEntity p_199346_1_, float p_199346_2_, ChestModel p_199346_3_) {
        float f = ((IChestLid) p_199346_1_).getLidAngle(p_199346_2_);
        f = 1.0F - f;
        f = 1.0F - f * f * f;
        p_199346_3_.getLid().rotateAngleX = -(f * ((float) Math.PI / 2F));
    }
}