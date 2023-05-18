package dev.sterner.geocluster.client.network;

import dev.sterner.geocluster.Geocluster;
import dev.sterner.geocluster.common.utils.GeoclusterUtils;
import dev.sterner.geocluster.common.utils.PacketUtils;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.util.Identifier;

import java.util.HashSet;

public class S2CProspectingPacket {
    public static final Identifier ID = Geocluster.id("prospecting");

    public static void send(PlayerEntity player, HashSet<BlockState> foundBlocks, String opposite) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeNbt(PacketUtils.encodeBlocks(foundBlocks));
        buf.writeString(opposite);
        ServerPlayNetworking.send((ServerPlayerEntity) player, ID, buf);
    }

    public static void handle(MinecraftClient client, ClientPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
        NbtCompound nbtCompound = buf.readNbt();
        if(nbtCompound != null){
            HashSet<BlockState> blocks = PacketUtils.decodeBlocks(nbtCompound);
            String direction = buf.readString();
            client.execute(() -> {
                ClientPlayerEntity clientPlayerEntity = client.player;
                if (clientPlayerEntity != null) {
                    if (!direction.equals("")) {
                        sendProspectingMessage(clientPlayerEntity, "geocluster.pro_pick.tooltip.found", PacketUtils.messagify(blocks), direction);
                    } else {
                        sendProspectingMessage(clientPlayerEntity, "geocluster.pro_pick.tooltip.found_surface", PacketUtils.messagify(blocks));
                    }
                }
            });
        }
    }

    private static void sendProspectingMessage(ClientPlayerEntity player, String key, Object... messageDecorators) {
        MutableText msg = GeoclusterUtils.tryTranslate(key, messageDecorators);
        player.sendMessage(msg, true);
    }
}
