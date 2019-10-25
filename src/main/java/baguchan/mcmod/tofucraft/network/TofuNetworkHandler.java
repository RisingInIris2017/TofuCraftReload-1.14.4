package baguchan.mcmod.tofucraft.network;

import baguchan.mcmod.tofucraft.TofuCraftCore;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class TofuNetworkHandler {
    public static final String NETWORK_PROTOCOL = "2";

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(TofuCraftCore.MODID, "net"))
            .networkProtocolVersion(() -> NETWORK_PROTOCOL)
            .clientAcceptedVersions(NETWORK_PROTOCOL::equals)
            .serverAcceptedVersions(NETWORK_PROTOCOL::equals)
            .simpleChannel();

    public static void register() {
        /*CHANNEL.messageBuilder(TofuAnimationMessage.class, 0)
                .encoder(TofuAnimationMessage::writePacketData).decoder(TofuAnimationMessage::readPacketData)
                .consumer(TofuAnimationMessage.Handler::handle)
                .add();*/

    }
}