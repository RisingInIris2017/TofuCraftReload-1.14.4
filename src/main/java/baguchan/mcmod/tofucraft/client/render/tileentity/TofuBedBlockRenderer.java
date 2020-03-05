package baguchan.mcmod.tofucraft.client.render.tileentity;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.block.TofuBedBlock;
import baguchan.mcmod.tofucraft.tileentity.TofuBedTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TofuBedBlockRenderer extends TileEntityRenderer<TofuBedTileEntity> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(TofuCraftCore.MODID, "entity/tofubed");
    private static final Material DEFAULT_TEXTURE = new Material(Atlases.BED_ATLAS, new ResourceLocation(TofuCraftCore.MODID, "textures/entity/tofubed"));

    private final ModelRenderer field_228843_a_;
    private final ModelRenderer field_228844_c_;
    private final ModelRenderer[] field_228845_d_ = new ModelRenderer[4];

    public TofuBedBlockRenderer(TileEntityRendererDispatcher p_i226004_1_) {
        super(p_i226004_1_);
        this.field_228843_a_ = new ModelRenderer(64, 64, 0, 0);
        this.field_228843_a_.addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F, 0.0F);
        this.field_228844_c_ = new ModelRenderer(64, 64, 0, 22);
        this.field_228844_c_.addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F, 0.0F);
        this.field_228845_d_[0] = new ModelRenderer(64, 64, 50, 0);
        this.field_228845_d_[1] = new ModelRenderer(64, 64, 50, 6);
        this.field_228845_d_[2] = new ModelRenderer(64, 64, 50, 12);
        this.field_228845_d_[3] = new ModelRenderer(64, 64, 50, 18);
        this.field_228845_d_[0].addBox(0.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F);
        this.field_228845_d_[1].addBox(0.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F);
        this.field_228845_d_[2].addBox(-16.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F);
        this.field_228845_d_[3].addBox(-16.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F);
        this.field_228845_d_[0].rotateAngleX = ((float) Math.PI / 2F);
        this.field_228845_d_[1].rotateAngleX = ((float) Math.PI / 2F);
        this.field_228845_d_[2].rotateAngleX = ((float) Math.PI / 2F);
        this.field_228845_d_[3].rotateAngleX = ((float) Math.PI / 2F);
        this.field_228845_d_[0].rotateAngleZ = 0.0F;
        this.field_228845_d_[1].rotateAngleZ = ((float) Math.PI / 2F);
        this.field_228845_d_[2].rotateAngleZ = ((float) Math.PI * 1.5F);
        this.field_228845_d_[3].rotateAngleZ = (float) Math.PI;
    }

    public void render(TofuBedTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Material material = DEFAULT_TEXTURE;
        World world = tileEntityIn.getWorld();
        if (world != null) {
            BlockState blockstate = tileEntityIn.getBlockState();
            int i = combinedLightIn;
            this.func_228847_a_(matrixStackIn, bufferIn, blockstate.get(TofuBedBlock.PART) == BedPart.HEAD, blockstate.get(TofuBedBlock.HORIZONTAL_FACING), material, i, combinedOverlayIn, false);
        } else {
            this.func_228847_a_(matrixStackIn, bufferIn, true, Direction.SOUTH, material, combinedLightIn, combinedOverlayIn, false);
            this.func_228847_a_(matrixStackIn, bufferIn, false, Direction.SOUTH, material, combinedLightIn, combinedOverlayIn, true);
        }

    }

    private void func_228847_a_(MatrixStack p_228847_1_, IRenderTypeBuffer p_228847_2_, boolean p_228847_3_, Direction p_228847_4_, Material p_228847_5_, int p_228847_6_, int p_228847_7_, boolean p_228847_8_) {
        this.field_228843_a_.showModel = p_228847_3_;
        this.field_228844_c_.showModel = !p_228847_3_;
        this.field_228845_d_[0].showModel = !p_228847_3_;
        this.field_228845_d_[1].showModel = p_228847_3_;
        this.field_228845_d_[2].showModel = !p_228847_3_;
        this.field_228845_d_[3].showModel = p_228847_3_;
        p_228847_1_.push();
        p_228847_1_.translate(0.0D, 0.5625D, p_228847_8_ ? -1.0D : 0.0D);
        p_228847_1_.rotate(Vector3f.XP.rotationDegrees(90.0F));
        p_228847_1_.translate(0.5D, 0.5D, 0.5D);
        p_228847_1_.rotate(Vector3f.ZP.rotationDegrees(180.0F + p_228847_4_.getHorizontalAngle()));
        p_228847_1_.translate(-0.5D, -0.5D, -0.5D);
        IVertexBuilder ivertexbuilder = p_228847_5_.getBuffer(p_228847_2_, RenderType::entitySolid);
        this.field_228843_a_.render(p_228847_1_, ivertexbuilder, p_228847_6_, p_228847_7_);
        this.field_228844_c_.render(p_228847_1_, ivertexbuilder, p_228847_6_, p_228847_7_);
        this.field_228845_d_[0].render(p_228847_1_, ivertexbuilder, p_228847_6_, p_228847_7_);
        this.field_228845_d_[1].render(p_228847_1_, ivertexbuilder, p_228847_6_, p_228847_7_);
        this.field_228845_d_[2].render(p_228847_1_, ivertexbuilder, p_228847_6_, p_228847_7_);
        this.field_228845_d_[3].render(p_228847_1_, ivertexbuilder, p_228847_6_, p_228847_7_);
        p_228847_1_.pop();
    }
}