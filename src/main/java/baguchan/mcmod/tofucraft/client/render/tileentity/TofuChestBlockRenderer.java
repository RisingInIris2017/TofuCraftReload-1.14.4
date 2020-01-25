package baguchan.mcmod.tofucraft.client.render.tileentity;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.tileentity.TofuChestTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.*;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Calendar;

@OnlyIn(Dist.CLIENT)
public class TofuChestBlockRenderer extends TileEntityRenderer<TofuChestTileEntity> {
    private static final Material TEXTURE_NORMALTOFU_DOUBLE_CHRISTMAS = getChestMaterial(new ResourceLocation(TofuCraftCore.MODID, "textures/entity/chest/tofuchest_double_christmas.png"));
    private static final Material TEXTURE_NORMALTOFU_CHRISTMAS = getChestMaterial(new ResourceLocation(TofuCraftCore.MODID, "textures/entity/chest/tofuchest_christmas.png"));
    private static final Material TEXTURE_NORMALTOFU_DOUBLE = getChestMaterial(new ResourceLocation(TofuCraftCore.MODID, "textures/entity/chest/tofuchest_double.png"));
    private static final Material TEXTURE_NORMALTOFU = getChestMaterial(new ResourceLocation(TofuCraftCore.MODID, "textures/entity/chest/tofuchest.png"));

    public static final ResourceLocation CHEST_ATLAS = new ResourceLocation(TofuCraftCore.MODID, "textures/atlas/tofuchest.png");
    private final ModelRenderer field_228862_a_;
    private final ModelRenderer field_228863_c_;
    private final ModelRenderer field_228864_d_;
    private final ModelRenderer field_228865_e_;
    private final ModelRenderer field_228866_f_;
    private final ModelRenderer field_228867_g_;
    private final ModelRenderer field_228868_h_;
    private final ModelRenderer field_228869_i_;
    private final ModelRenderer field_228870_j_;
    private boolean isChristmas;

    public TofuChestBlockRenderer(TileEntityRendererDispatcher p_i226008_1_) {
        super(p_i226008_1_);
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH) >= 9 && calendar.get(Calendar.DAY_OF_MONTH) <= 26) {
            this.isChristmas = true;
        }
        this.field_228863_c_ = new ModelRenderer(64, 64, 0, 19);
        this.field_228863_c_.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
        this.field_228862_a_ = new ModelRenderer(64, 64, 0, 0);
        this.field_228862_a_.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
        this.field_228862_a_.rotationPointY = 9.0F;
        this.field_228862_a_.rotationPointZ = 1.0F;
        this.field_228864_d_ = new ModelRenderer(64, 64, 0, 0);
        this.field_228864_d_.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
        this.field_228864_d_.rotationPointY = 8.0F;
        this.field_228866_f_ = new ModelRenderer(64, 64, 0, 19);
        this.field_228866_f_.addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.field_228865_e_ = new ModelRenderer(64, 64, 0, 0);
        this.field_228865_e_.addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.field_228865_e_.rotationPointY = 9.0F;
        this.field_228865_e_.rotationPointZ = 1.0F;
        this.field_228867_g_ = new ModelRenderer(64, 64, 0, 0);
        this.field_228867_g_.addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.field_228867_g_.rotationPointY = 8.0F;
        this.field_228869_i_ = new ModelRenderer(64, 64, 0, 19);
        this.field_228869_i_.addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.field_228868_h_ = new ModelRenderer(64, 64, 0, 0);
        this.field_228868_h_.addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.field_228868_h_.rotationPointY = 9.0F;
        this.field_228868_h_.rotationPointZ = 1.0F;
        this.field_228870_j_ = new ModelRenderer(64, 64, 0, 0);
        this.field_228870_j_.addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.field_228870_j_.rotationPointY = 8.0F;
    }

    public void render(TofuChestTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        World world = tileEntityIn.getWorld();
        boolean flag = world != null;
        BlockState blockstate = flag ? tileEntityIn.getBlockState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType chesttype = blockstate.has(ChestBlock.TYPE) ? blockstate.get(ChestBlock.TYPE) : ChestType.SINGLE;
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

            float f1 = icallbackwrapper.apply(ChestBlock.func_226917_a_((IChestLid) tileEntityIn)).get(partialTicks);
            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            int i = icallbackwrapper.apply(new DualBrightnessCallback<>()).applyAsInt(combinedLightIn);
            Material material = null;
            IVertexBuilder ivertexbuilder = material.getBuffer(bufferIn, RenderType::entityCutout);
            if (flag1) {
                if (chesttype == ChestType.LEFT) {
                    this.func_228871_a_(matrixStackIn, ivertexbuilder, this.field_228868_h_, this.field_228870_j_, this.field_228869_i_, f1, i, combinedOverlayIn);
                } else {
                    this.func_228871_a_(matrixStackIn, ivertexbuilder, this.field_228865_e_, this.field_228867_g_, this.field_228866_f_, f1, i, combinedOverlayIn);
                }
            } else {
                this.func_228871_a_(matrixStackIn, ivertexbuilder, this.field_228862_a_, this.field_228864_d_, this.field_228863_c_, f1, i, combinedOverlayIn);
            }

            matrixStackIn.pop();
        }
    }

    private void func_228871_a_(MatrixStack p_228871_1_, IVertexBuilder p_228871_2_, ModelRenderer p_228871_3_, ModelRenderer p_228871_4_, ModelRenderer p_228871_5_, float p_228871_6_, int p_228871_7_, int p_228871_8_) {
        p_228871_3_.rotateAngleX = -(p_228871_6_ * ((float) Math.PI / 2F));
        p_228871_4_.rotateAngleX = p_228871_3_.rotateAngleX;
        p_228871_3_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
        p_228871_4_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
        p_228871_5_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
    }

    /*public static Material getChestMaterial(TileEntity p_228771_0_, ChestType p_228771_1_, boolean p_228771_2_) {
        if (p_228771_2_) {
            return getChestMaterial(p_228771_1_, TEXTURE_NORMALTOFU_CHRISTMAS, CHEST_XMAS_LEFT_MATERIAL, CHEST_XMAS_RIGHT_MATERIAL);
        } else if (p_228771_0_ instanceof TrappedChestTileEntity) {
            return getChestMaterial(p_228771_1_, CHEST_TRAPPED_MATERIAL, CHEST_TRAPPED_LEFT_MATERIAL, CHEST_TRAPPED_RIGHT_MATERIAL);
        } else {
            return p_228771_0_ instanceof EnderChestTileEntity ? ENDER_CHEST_MATERIAL : getChestMaterial(p_228771_1_, CHEST_MATERIAL, CHEST_LEFT_MATERIAL, CHEST_RIGHT_MATERIAL);
        }
    }*/

    private static Material getChestMaterial(ResourceLocation p_228774_0_) {
        return new Material(CHEST_ATLAS, p_228774_0_);
    }

    private static Material getChestMaterial(ChestType p_228772_0_, Material p_228772_1_, Material p_228772_2_, Material p_228772_3_) {
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