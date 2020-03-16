package baguchan.mcmod.tofucraft.client;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import baguchan.mcmod.tofucraft.client.render.*;
import baguchan.mcmod.tofucraft.client.render.tileentity.TofuBedBlockRenderer;
import baguchan.mcmod.tofucraft.client.render.tileentity.TofuChestBlockRenderer;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuEntitys;
import baguchan.mcmod.tofucraft.init.TofuTileEntitys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TofuCraftCore.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
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

        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.ZUNSY, ZunsyRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.ZUNDAMA, ZundamaRender::new);


        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.MORIJIO, MorijioRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.FUKUMAME, FukumameRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.ZUNDAARROW, ZundaArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.BEAM, BeamRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.TOFU_HOMING_FORCE, EmptyRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEntitys.TOFU_FALLING_BLOCK, FallingBlockRenderer::new);
    }

    public static void renderTileEntity() {
        ClientRegistry.bindTileEntityRenderer(TofuTileEntitys.TOFUCHEST, TofuChestBlockRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TofuTileEntitys.TOFUBED, TofuBedBlockRenderer::new);
    }

    public static void renderBlock() {
        Minecraft.getInstance().getBlockColors().register((p_210226_0_, p_210226_1_, p_210226_2_, p_210226_3_) -> {
            return p_210226_1_ != null && p_210226_2_ != null ? BiomeColors.getWaterColor(p_210226_1_, p_210226_2_) : -1;
        }, TofuBlocks.SALTPAN);
        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFULEAVES, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFUZUNDALEAVES, RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFUTORCH_KINU, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFUTORCH_MOMEN, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFUTORCH_ISHI, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFUTORCH_METAL, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.WALLTOFUTORCH_KINU, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.WALLTOFUTORCH_MOMEN, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.WALLTOFUTORCH_ISHI, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.WALLTOFUTORCH_METAL, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(TofuBlocks.SOYBEAN, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.RICE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.RICE_ROOT, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.LEEK, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.LEEKCROP, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFUSAPLING, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.ZUNDATOFUSAPLING, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFUFLOWER, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(TofuBlocks.POTTED_TOFUFLOWER, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.POTTED_TOFUSAPLING, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.POTTED_ZUNDATOFUSAPLING, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFUBERRY, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFUBERRYSTEM, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFUDOOR_KINU, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFUDOOR_MOMEN, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFUDOOR_ISHI, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFUDOOR_METAL, RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFULADDER_KINU, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFULADDER_MOMEN, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFULADDER_ISHI, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFULADDER_ISHIBRICK, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFULADDER_METAL, RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(TofuBlocks.TOFUPORTAL, RenderType.translucent());
    }

    public static void setup(final FMLCommonSetupEvent event) {
        ClientRegistrar.renderEntity();
        ClientRegistrar.renderTileEntity();
        ClientRegistrar.renderBlock();
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (event.getMap().getBasePath().equals(Atlases.CHEST_ATLAS)) {
            event.addSprite(new ResourceLocation(TofuCraftCore.MODID, "entity/chest/tofuchest"));
            event.addSprite(new ResourceLocation(TofuCraftCore.MODID, "entity/chest/tofuchest_left"));
            event.addSprite(new ResourceLocation(TofuCraftCore.MODID, "entity/chest/tofuchest_right"));
        }

        if (event.getMap().getBasePath().equals(Atlases.BED_ATLAS)) {
            event.addSprite(TofuBedBlockRenderer.TEXTURES);
        }
    }
}