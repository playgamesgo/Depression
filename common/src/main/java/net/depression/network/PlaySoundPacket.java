package net.depression.network;

import dev.architectury.networking.NetworkManager;
import net.depression.Depression;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class PlaySoundPacket {
    public record PlaySoundPayload(String soundId) implements CustomPacketPayload {
        public static final Type<PlaySoundPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "play_sound_packet"));

        public static final StreamCodec<RegistryFriendlyByteBuf, PlaySoundPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, PlaySoundPayload::soundId,
                PlaySoundPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static void sendToServer(String soundId) {
        NetworkManager.sendToServer(new PlaySoundPayload(soundId));
    }

    public static void sendToPlayer(ServerPlayer player, String soundId) {
        NetworkManager.sendToPlayer(player, new PlaySoundPayload(soundId));
    }
}