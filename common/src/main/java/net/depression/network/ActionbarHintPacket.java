package net.depression.network;

    import dev.architectury.networking.NetworkManager;
    import net.depression.Depression;
    import net.minecraft.network.RegistryFriendlyByteBuf;
    import net.minecraft.network.chat.Component;
    import net.minecraft.network.chat.ComponentSerialization;
    import net.minecraft.network.codec.ByteBufCodecs;
    import net.minecraft.network.codec.StreamCodec;
    import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
    import net.minecraft.resources.ResourceLocation;
    import net.minecraft.server.level.ServerPlayer;

    public class ActionbarHintPacket {

        public record OverdosePayload(int count) implements CustomPacketPayload {
            public static final Type<OverdosePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "overdose_packet"));
            public static final StreamCodec<RegistryFriendlyByteBuf, OverdosePayload> CODEC = StreamCodec.composite(
                    ByteBufCodecs.INT, OverdosePayload::count,
                    OverdosePayload::new
            );
            @Override
            public Type<? extends CustomPacketPayload> type() { return TYPE; }
        }

        public record BipolarPayload(boolean isMania) implements CustomPacketPayload {
            public static final Type<BipolarPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "bipolar_packet"));
            public static final StreamCodec<RegistryFriendlyByteBuf, BipolarPayload> CODEC = StreamCodec.composite(
                    ByteBufCodecs.BOOL, BipolarPayload::isMania,
                    BipolarPayload::new
            );
            @Override
            public Type<? extends CustomPacketPayload> type() { return TYPE; }
        }

        public record ComponentPayload(Component component, String packetType) implements CustomPacketPayload {
            public static final Type<ComponentPayload> FISH_TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "fish_heal_packet"));
            public static final Type<ComponentPayload> FEED_ANIMAL_TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "feed_animal_heal_packet"));
            public static final Type<ComponentPayload> PET_TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "pet_heal_packet"));
            public static final Type<ComponentPayload> NEARBY_BLOCK_TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "nearby_block_heal_packet"));
            public static final Type<ComponentPayload> KILL_ENTITY_TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "kill_entity_heal_packet"));
            public static final Type<ComponentPayload> BREAK_BLOCK_TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "break_block_heal_packet"));
            public static final Type<ComponentPayload> PTSD_FORM_TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "ptsd_form_packet"));
            public static final Type<ComponentPayload> PTSD_DISPERSE_TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "ptsd_disperse_packet"));
            public static final Type<ComponentPayload> PTSD_REMISSION_TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "ptsd_remission_packet"));

            public static final StreamCodec<RegistryFriendlyByteBuf, ComponentPayload> CODEC = StreamCodec.composite(
                    ComponentSerialization.TRUSTED_STREAM_CODEC, ComponentPayload::component,
                    ByteBufCodecs.STRING_UTF8, ComponentPayload::packetType,
                    ComponentPayload::new
            );

            @Override
            public Type<? extends CustomPacketPayload> type() {
                return switch (packetType) {
                    case "fish" -> FISH_TYPE;
                    case "feed_animal" -> FEED_ANIMAL_TYPE;
                    case "pet" -> PET_TYPE;
                    case "nearby_block" -> NEARBY_BLOCK_TYPE;
                    case "kill_entity" -> KILL_ENTITY_TYPE;
                    case "break_block" -> BREAK_BLOCK_TYPE;
                    case "ptsd_form" -> PTSD_FORM_TYPE;
                    case "ptsd_disperse" -> PTSD_DISPERSE_TYPE;
                    case "ptsd_remission" -> PTSD_REMISSION_TYPE;
                    default -> throw new IllegalArgumentException("Unknown packet type: " + packetType);
                };
            }
        }

        public record EmptyPayload(String packetType) implements CustomPacketPayload {
            public static final Type<EmptyPayload> LOOT_TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "loot_heal_packet"));
            public static final Type<EmptyPayload> INSOMNIA_TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "insomnia_packet"));
            public static final Type<EmptyPayload> MENTAL_FATIGUE_TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "mental_fatigue_packet"));

            public static final StreamCodec<RegistryFriendlyByteBuf, EmptyPayload> CODEC = StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8, EmptyPayload::packetType,
                    EmptyPayload::new
            );

            @Override
            public Type<? extends CustomPacketPayload> type() {
                return switch (packetType) {
                    case "loot" -> LOOT_TYPE;
                    case "insomnia" -> INSOMNIA_TYPE;
                    case "mental_fatigue" -> MENTAL_FATIGUE_TYPE;
                    default -> throw new IllegalArgumentException("Unknown packet type: " + packetType);
                };
            }
        }

        // Send methods
        public static void sendOverdosePacket(ServerPlayer player, int count) {
            NetworkManager.sendToPlayer(player, new OverdosePayload(count));
        }

        public static void sendBipolarPacket(ServerPlayer player, boolean isMania) {
            NetworkManager.sendToPlayer(player, new BipolarPayload(isMania));
        }

        public static void sendFishHealPacket(ServerPlayer player, Component id) {
            NetworkManager.sendToPlayer(player, new ComponentPayload(id, "fish"));
        }

        public static void sendFeedAnimalHealPacket(ServerPlayer player, Component id) {
            NetworkManager.sendToPlayer(player, new ComponentPayload(id, "feed_animal"));
        }

        public static void sendPetHealPacket(ServerPlayer player, Component id) {
            NetworkManager.sendToPlayer(player, new ComponentPayload(id, "pet"));
        }

        public static void sendLootHealPacket(ServerPlayer player) {
            NetworkManager.sendToPlayer(player, new EmptyPayload("loot"));
        }

        public static void sendNearbyBlockHealPacket(ServerPlayer player, Component id) {
            NetworkManager.sendToPlayer(player, new ComponentPayload(id, "nearby_block"));
        }

        public static void sendKillEntityHealPacket(ServerPlayer player, Component id) {
            NetworkManager.sendToPlayer(player, new ComponentPayload(id, "kill_entity"));
        }

        public static void sendBreakBlockHealPacket(ServerPlayer player, Component id) {
            NetworkManager.sendToPlayer(player, new ComponentPayload(id, "break_block"));
        }

        public static void sendPTSDFormPacket(ServerPlayer player, Component id) {
            NetworkManager.sendToPlayer(player, new ComponentPayload(id, "ptsd_form"));
        }

        public static void sendPTSDDispersePacket(ServerPlayer player, Component id) {
            NetworkManager.sendToPlayer(player, new ComponentPayload(id, "ptsd_disperse"));
        }

        public static void sendPTSDRemissionPacket(ServerPlayer player, Component id) {
            NetworkManager.sendToPlayer(player, new ComponentPayload(id, "ptsd_remission"));
        }

        public static void sendInsomniaPacket(ServerPlayer player) {
            NetworkManager.sendToPlayer(player, new EmptyPayload("insomnia"));
        }

        public static void sendMentalFatiguePacket(ServerPlayer player) {
            NetworkManager.sendToPlayer(player, new EmptyPayload("mental_fatigue"));
        }
    }