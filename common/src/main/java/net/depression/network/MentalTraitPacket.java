package net.depression.network;

import dev.architectury.networking.NetworkManager;
import net.depression.Depression;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class MentalTraitPacket {

    public record MentalTraitPayload(String id) implements CustomPacketPayload {
        public static final Type<MentalTraitPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "mental_trait_packet"));

        public static final StreamCodec<RegistryFriendlyByteBuf, MentalTraitPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, MentalTraitPayload::id,
                MentalTraitPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static void sendToPlayer(ServerPlayer player) {
        NetworkManager.sendToPlayer(player, new MentalTraitPayload(""));
    }

    public static void sendToServer(String id) {
        NetworkManager.sendToServer(new MentalTraitPayload(id));
    }
}