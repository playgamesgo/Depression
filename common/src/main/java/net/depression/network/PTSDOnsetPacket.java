package net.depression.network;

import dev.architectury.networking.NetworkManager;
import net.depression.Depression;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class PTSDOnsetPacket {

    public record PTSDOnsetPayload(int onsetLevel, double distance) implements CustomPacketPayload {
        public static final Type<PTSDOnsetPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "ptsd_onset_packet"));

        public static final StreamCodec<RegistryFriendlyByteBuf, PTSDOnsetPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, PTSDOnsetPayload::onsetLevel,
                ByteBufCodecs.DOUBLE, PTSDOnsetPayload::distance,
                PTSDOnsetPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record PhotosmPayload(String id) implements CustomPacketPayload {
        public static final Type<PhotosmPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "photism_packet"));

        public static final StreamCodec<RegistryFriendlyByteBuf, PhotosmPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, PhotosmPayload::id,
                PhotosmPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static void sendToPlayer(ServerPlayer player, int onsetLevel, double distance) {
        NetworkManager.sendToPlayer(player, new PTSDOnsetPayload(onsetLevel, distance));
    }

    public static void sendPhotismPacket(ServerPlayer player, String id) {
        NetworkManager.sendToPlayer(player, new PhotosmPayload(id));
    }
}