package net.depression.network;

import dev.architectury.networking.NetworkManager;
import net.depression.Depression;
import net.depression.mental.MentalStatus;
import net.depression.server.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class MentalStatusPacket {
    public record EmotionPayload(double emotionValue, boolean inCombat) implements CustomPacketPayload {
        public static final Type<EmotionPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "emotion_packet"));

        public static final StreamCodec<RegistryFriendlyByteBuf, EmotionPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.DOUBLE, EmotionPayload::emotionValue,
                ByteBufCodecs.BOOL, EmotionPayload::inCombat,
                EmotionPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record MentalHealthPayload(double mentalHealthValue, int mentalHealthId) implements CustomPacketPayload {
        public static final Type<MentalHealthPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "mental_health_packet"));

        public static final StreamCodec<RegistryFriendlyByteBuf, MentalHealthPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.DOUBLE, MentalHealthPayload::mentalHealthValue,
                ByteBufCodecs.INT, MentalHealthPayload::mentalHealthId,
                MentalHealthPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static void sendToPlayer(ServerPlayer player, MentalStatus mentalStatus) {
        boolean inCombat = mentalStatus.combatCountdown > 0 || Registry.playerEventMap.containsKey(player.getUUID());

        NetworkManager.sendToPlayer(player, new EmotionPayload(mentalStatus.emotionValue, inCombat));
        NetworkManager.sendToPlayer(player, new MentalHealthPayload(mentalStatus.mentalHealthValue, mentalStatus.getMentalHealthId()));
    }
}