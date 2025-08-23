package net.depression.network;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.depression.Depression;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record CloseEyePacket() implements CustomPacketPayload {
    public static final Type<CloseEyePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "close_eye_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CloseEyePacket> CODEC = StreamCodec.unit(new CloseEyePacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void sendToPlayer(ServerPlayer player) {
        NetworkManager.sendToPlayer(player, new CloseEyePacket());
    }
}