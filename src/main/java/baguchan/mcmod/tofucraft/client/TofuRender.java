package baguchan.mcmod.tofucraft.client;

import baguchan.mcmod.tofucraft.client.render.TofuCowRender;
import baguchan.mcmod.tofucraft.client.render.TofuSlimeRender;
import baguchan.mcmod.tofucraft.client.render.TofunianRender;
import baguchan.mcmod.tofucraft.entity.TofuCowEntity;
import baguchan.mcmod.tofucraft.entity.TofuSlimeEntity;
import baguchan.mcmod.tofucraft.entity.TofunianEntity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class TofuRender {

    public static void renderEntity() {
        RenderingRegistry.registerEntityRenderingHandler(TofunianEntity.class, TofunianRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuSlimeEntity.class, TofuSlimeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(TofuCowEntity.class, TofuCowRender::new);
    }
}
