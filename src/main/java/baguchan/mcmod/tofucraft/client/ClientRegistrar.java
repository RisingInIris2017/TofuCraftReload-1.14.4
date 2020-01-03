package baguchan.mcmod.tofucraft.client;

import baguchan.mcmod.tofucraft.client.render.*;
import baguchan.mcmod.tofucraft.client.render.tileentity.TofuBedBlockRenderer;
import baguchan.mcmod.tofucraft.client.render.tileentity.TofuChestBlockRenderer;
import baguchan.mcmod.tofucraft.entity.*;
import baguchan.mcmod.tofucraft.entity.projectile.BeamEntity;
import baguchan.mcmod.tofucraft.entity.projectile.FukumameEntity;
import baguchan.mcmod.tofucraft.entity.projectile.ZundaArrowEntity;
import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.tileentity.TofuBedTileEntity;
import baguchan.mcmod.tofucraft.tileentity.TofuChestTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ClientRegistrar {

    public static void renderEntity() {
        RenderingRegistry.registerEntityRenderingHandler(TofunianEntity.class, TofunianRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuSlimeEntity.class, TofuSlimeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuCowEntity.class, TofuCowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuFishEntity.class, TofuFishRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuChingerEntity.class, TofuChingerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(GrillderEntity.class, GrillderRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuSpiderEntity.class, TofuSpiderRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuEagleEntity.class, TofuEagleRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuLizardEntity.class, TofuLizardRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuTurretEntity.class, TofuTurretRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuMindEntity.class, TofuMindRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuGandlemEntity.class, TofuGandlemRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SesamenianEntity.class, SesamenianRender::new);


        RenderingRegistry.registerEntityRenderingHandler(FukumameEntity.class, FukumameRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ZundaArrowEntity.class, ZundaArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BeamEntity.class, BeamRender::new);
    }

    public static void renderTileEntity() {
        ClientRegistry.bindTileEntitySpecialRenderer(TofuChestTileEntity.class, new TofuChestBlockRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TofuBedTileEntity.class, new TofuBedBlockRenderer());
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