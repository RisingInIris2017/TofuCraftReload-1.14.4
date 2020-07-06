package baguchan.mcmod.tofucraft.client.render.tileentity;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuChestBlockRenderer<T extends TileEntity & IChestLid> extends TileEntityRenderer<T> {
    public static final RenderMaterial TEXTURE_NORMALTOFU = getChestRenderMaterial(new ResourceLocation(TofuCraftCore.MODID, "entity/chest/tofuchest"));
    public static final RenderMaterial TEXTURE_TOFU_LEFT = getChestRenderMaterial(new ResourceLocation(TofuCraftCore.MODID, "entity/chest/tofuchest_left"));
    public static final RenderMaterial TEXTURE_TOFU_RIGHT = getChestRenderMaterial(new ResourceLocation(TofuCraftCore.MODID, "entity/chest/tofuchest_right"));

    private final ModelRenderer singleLid;
    private final ModelRenderer singleBottom;
    private final ModelRenderer singleLatch;
    private final ModelRenderer rightLid;
    private final ModelRenderer rightBottom;
    private final ModelRenderer rightLatch;
    private final ModelRenderer leftLid;
    private final ModelRenderer leftBottom;
    private final ModelRenderer leftLatch;

    public TofuChestBlockRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);


        this.singleBottom = new ModelRenderer(64, 64, 0, 19);
        this.singleBottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
        this.singleLid = new ModelRenderer(64, 64, 0, 0);
        this.singleLid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
        this.singleLid.rotationPointY = 9.0F;
        this.singleLid.rotationPointZ = 1.0F;
        this.singleLatch = new ModelRenderer(64, 64, 0, 0);
        this.singleLatch.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
        this.singleLatch.rotationPointY = 8.0F;
        this.rightBottom = new ModelRenderer(64, 64, 0, 19);
        this.rightBottom.addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.rightLid = new ModelRenderer(64, 64, 0, 0);
        this.rightLid.addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.rightLid.rotationPointY = 9.0F;
        this.rightLid.rotationPointZ = 1.0F;
        this.rightLatch = new ModelRenderer(64, 64, 0, 0);
        this.rightLatch.addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.rightLatch.rotationPointY = 8.0F;
        this.leftBottom = new ModelRenderer(64, 64, 0, 19);
        this.leftBottom.addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.leftLid = new ModelRenderer(64, 64, 0, 0);
        this.leftLid.addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.leftLid.rotationPointY = 9.0F;
        this.leftLid.rotationPointZ = 1.0F;
        this.leftLatch = new ModelRenderer(64, 64, 0, 0);
        this.leftLatch.addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.leftLatch.rotationPointY = 8.0F;
    }

    public void render(T tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        World world = tileEntityIn.getWorld();
        boolean flag = world != null;
        BlockState blockstate = flag ? tileEntityIn.getBlockState() : TofuBlocks.TOFUCHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType chesttype = blockstate.func_235901_b_(ChestBlock.TYPE) ? blockstate.get(ChestBlock.TYPE) : ChestType.SINGLE;
        Block block = blockstate.getBlock();
        if (block instanceof AbstractChestBlock) {
            AbstractChestBlock<?> abstractchestblock = (AbstractChestBlock) block;
            boolean flag1 = chesttype != ChestType.SINGLE;
            matrixStackIn.push();
            float f = blockstate.get(ChestBlock.FACING).getHorizontalAngle();
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-f));
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> icallbackwrapper;
            if (flag) {
                icallbackwrapper = abstractchestblock.func_225536_a_(blockstate, world, tileEntityIn.getPos(), true);
            } else {
                icallbackwrapper = TileEntityMerger.ICallback::func_225537_b_;
            }

            float f1 = icallbackwrapper.<Float2FloatFunction>apply(ChestBlock.func_226917_a_(tileEntityIn)).get(partialTicks);
            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            int i = icallbackwrapper.<Int2IntFunction>apply(new DualBrightnessCallback<>()).applyAsInt(combinedLightIn);
            RenderMaterial rendermaterial = getChestRenderMaterial(tileEntityIn, chesttype);
            IVertexBuilder ivertexbuilder = rendermaterial.getBuffer(bufferIn, RenderType::getEntityCutout);
            if (flag1) {
                if (chesttype == ChestType.LEFT) {
                    this.renderModels(matrixStackIn, ivertexbuilder, this.leftLid, this.leftLatch, this.leftBottom, f1, i, combinedOverlayIn);
                } else {
                    this.renderModels(matrixStackIn, ivertexbuilder, this.rightLid, this.rightLatch, this.rightBottom, f1, i, combinedOverlayIn);
                }
            } else {
                this.renderModels(matrixStackIn, ivertexbuilder, this.singleLid, this.singleLatch, this.singleBottom, f1, i, combinedOverlayIn);
            }

            matrixStackIn.pop();
        }
    }

    private void renderModels(MatrixStack p_228871_1_, IVertexBuilder p_228871_2_, ModelRenderer p_228871_3_, ModelRenderer p_228871_4_, ModelRenderer p_228871_5_, float p_228871_6_, int p_228871_7_, int p_228871_8_) {
        p_228871_3_.rotateAngleX = -(p_228871_6_ * ((float) Math.PI / 2F));
        p_228871_4_.rotateAngleX = p_228871_3_.rotateAngleX;
        p_228871_3_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
        p_228871_4_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
        p_228871_5_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
    }

    public static RenderMaterial getChestRenderMaterial(TileEntity p_228771_0_, ChestType p_228771_1_) {
        return getChestRenderMaterial(p_228771_1_, TEXTURE_NORMALTOFU, TEXTURE_TOFU_LEFT, TEXTURE_TOFU_RIGHT);
    }

    private static RenderMaterial getChestRenderMaterial(ResourceLocation p_228774_0_) {
        return new RenderMaterial(Atlases.CHEST_ATLAS, p_228774_0_);
    }

    private static RenderMaterial getChestRenderMaterial(ChestType p_228772_0_, RenderMaterial p_228772_1_, RenderMaterial p_228772_2_, RenderMaterial p_228772_3_) {
        switch (p_228772_0_) {
            case LEFT:
                return p_228772_2_;
            case RIGHT:
                return p_228772_3_;
            case SINGLE:
            default:
                return p_228772_1_;
        }
    }
}