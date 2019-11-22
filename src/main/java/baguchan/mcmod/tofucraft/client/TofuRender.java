package baguchan.mcmod.tofucraft.client;

import baguchan.mcmod.tofucraft.client.render.*;
import baguchan.mcmod.tofucraft.client.render.tileentity.TofuChestBlockRenderer;
import baguchan.mcmod.tofucraft.entity.*;
import baguchan.mcmod.tofucraft.entity.projectile.BeamEntity;
import baguchan.mcmod.tofucraft.entity.projectile.FukumameEntity;
import baguchan.mcmod.tofucraft.entity.projectile.ZundaArrowEntity;
import baguchan.mcmod.tofucraft.tileentity.TofuChestTileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class TofuRender {

    public static void renderEntity() {
        RenderingRegistry.registerEntityRenderingHandler(TofunianEntity.class, TofunianRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuSlimeEntity.class, TofuSlimeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuCowEntity.class, TofuCowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuFishEntity.class, TofuFishRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuChingerEntity.class, TofuChingerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(GrillderEntity.class, GrillderRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuSpiderEntity.class, TofuSpiderRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuTurretEntity.class, TofuTurretRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuMindEntity.class, TofuMindRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SesamenianEntity.class, SesamenianRender::new);


        RenderingRegistry.registerEntityRenderingHandler(FukumameEntity.class, FukumameRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ZundaArrowEntity.class, ZundaArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BeamEntity.class, BeamRender::new);
    }

    public static void renderTileEntity() {
        ClientRegistry.bindTileEntitySpecialRenderer(TofuChestTileEntity.class, new TofuChestBlockRenderer());
    }
}
