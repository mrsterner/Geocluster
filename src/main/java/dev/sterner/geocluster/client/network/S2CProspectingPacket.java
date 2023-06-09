package dev.sterner.geocluster.client.network;

import dev.sterner.geocluster.Geocluster;
import dev.sterner.geocluster.client.toast.IOreToastManager;
import dev.sterner.geocluster.client.toast.OreToast;
import dev.sterner.geocluster.common.utils.PacketUtils;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.HashSet;

public class S2CProspectingPacket {
    public static final Identifier ID = Geocluster.id("prospecting");

    public static void send(PlayerEntity player, HashSet<BlockState> foundBlocks, String opposite) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeNbt(PacketUtils.fromBlockSet(foundBlocks));
        buf.writeString(opposite);
        ServerPlayNetworking.send((ServerPlayerEntity) player, ID, buf);
    }

    public static void handle(MinecraftClient client, ClientPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
        NbtCompound nbtCompound = buf.readNbt();
        if (nbtCompound != null) {
            HashSet<BlockState> blocks = PacketUtils.toBlockSet(nbtCompound);
            String direction = buf.readString();
            client.execute(() -> {
                ClientPlayerEntity clientPlayerEntity = client.player;
                if (clientPlayerEntity != null) {
                    if (!direction.equals("")) {
                        for (BlockState blockState : blocks) {
                            ((IOreToastManager) client).getManager().add(new OreToast(blockState, Direction.byName(direction)));
                        }
                    } else {
                        for (BlockState blockState : blocks) {
                            ((IOreToastManager) client).getManager().add(new OreToast(blockState, null));
                        }
                    }
                }
            });
        }
    }
}
