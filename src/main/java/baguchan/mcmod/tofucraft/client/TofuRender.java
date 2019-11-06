package baguchan.mcmod.tofucraft.client;

import baguchan.mcmod.tofucraft.client.render.*;
import baguchan.mcmod.tofucraft.entity.*;
import baguchan.mcmod.tofucraft.entity.projectile.FukumameEntity;
import baguchan.mcmod.tofucraft.entity.projectile.ZundaArrowEntity;
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


        RenderingRegistry.registerEntityRenderingHandler(FukumameEntity.class, FukumameRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ZundaArrowEntity.class, ZundaArrowRender::new);
    }
}
