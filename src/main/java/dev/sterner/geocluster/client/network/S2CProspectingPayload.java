package dev.sterner.geocluster.client.network;

import dev.sterner.geocluster.Geocluster;
import dev.sterner.geocluster.client.toast.IOreToastManager;
import dev.sterner.geocluster.client.toast.OreToast;
import dev.sterner.geocluster.common.utils.PacketUtils;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.HashSet;

public record S2CProspectingPayload(String string, NbtCompound nbt) implements CustomPayload {

    public static final CustomPayload.Id<S2CProspectingPayload> ID = new CustomPayload.Id<>(Geocluster.id("prospecting_payload"));
    public static final PacketCodec<RegistryByteBuf, S2CProspectingPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING,
            S2CProspectingPayload::string,
            PacketCodecs.UNLIMITED_NBT_COMPOUND,
            S2CProspectingPayload::nbt,
            S2CProspectingPayload::new
    );

    public static void handle(MinecraftClient client, NbtCompound nbt, String string) {
        NbtCompound nbtCompound = nbt;
        if (nbtCompound != null) {
            HashSet<BlockState> blocks = PacketUtils.toBlockSet(nbtCompound);
            String direction = string;
            client.execute(() -> {
                ClientPlayerEntity clientPlayerEntity = client.player;
                if (clientPlayerEntity != null) {
                    if (!direction.equals("")) {
                        for (BlockState blockState : blocks) {
                            ((IOreToastManager) client).getManager().add(new OreToast(clientPlayerEntity, blockState, Direction.byName(direction)));
                        }
                    } else {
                        for (BlockState blockState : blocks) {
                            ((IOreToastManager) client).getManager().add(new OreToast(clientPlayerEntity, blockState, null));
                        }
                    }
                }
            });
        }
    }

    public static void send(PlayerEntity player, HashSet<BlockState> foundBlocks, String opposite) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeNbt(PacketUtils.fromBlockSet(foundBlocks));
        buf.writeString(opposite);
        ServerPlayNetworking.send((ServerPlayerEntity) player, new S2CProspectingPayload(opposite, PacketUtils.fromBlockSet(foundBlocks)));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
