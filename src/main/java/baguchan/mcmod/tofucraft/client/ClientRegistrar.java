package baguchan.mcmod.tofucraft.client;

import baguchan.mcmod.tofucraft.client.render.*;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuEntitys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ClientRegistrar {

    public static void renderEntity() {
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.TOFUNIAN, TofunianRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.TOFUSLIME, TofuSlimeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.TOFUCOW, TofuCowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.TOFUFISH, TofuFishRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.TOFUCHINGER, TofuChingerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.TOFUSPIDER, TofuSpiderRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.TOFUCREEPER, TofuCreeperRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.TOFUTURRET, TofuTurretRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.TOFUMIND, TofuMindRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.TOFUGANDLEM, TofuGandlemRender::new);


        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.FUKUMAME, FukumameRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.ZUNDAARROW, ZundaArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.BEAM, BeamRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.TOFU_HOMING_FORCE, EmptyRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.TOFU_FALLING_BLOCK, FallingBlockRenderer::new);
    }

    public static void renderTileEntity() {
        //ClientRegistry.bindTileEntityRenderer(TofuTileEntitys.TOFUCHEST, TofuChestBlockRenderer::new);
        //ClientRegistry.bindTileEntityRenderer(TofuTileEntitys.TOFUBED, TofuBedBlockRenderer::new);
    }

    public static void renderBlockColor() {
        Minecraft.getInstance().getBlockColors().register((p_210226_0_, p_210226_1_, p_210226_2_, p_210226_3_) -> {
            return p_210226_1_ != null && p_210226_2_ != null ? BiomeColors.getWaterColor(p_210226_1_, p_210226_2_) : -1;
        }, TofuBlocks.SALTPAN);
    }

    public static void setup(final FMLCommonSetupEvent event) {
        ClientRegistrar.renderEntity();
        ClientRegistrar.renderTileEntity();
        ClientRegistrar.renderBlockColor();
    }
}