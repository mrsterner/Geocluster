package dev.sterner.geocluster.client.event;

import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.util.Identifier;

public record ClusterInfo(ClientPlayerEntity player, BlockState state, MutableText name, MutableText msg, Identifier toastTexture, boolean isCancelled){

}