package net.depression.network;

import dev.architectury.networking.NetworkManager;
import net.depression.Depression;
import net.depression.rhythmcraft.ProfileDataType;
import net.depression.rhythmcraft.RhythmCraftProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;

public class RhythmCraftPacket {
    public record ReadChartPayload(String songId, int difficulty, boolean isEditMode) implements CustomPacketPayload {
        public static final Type<ReadChartPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "rc_read_chart_packet"));
        public static final StreamCodec<RegistryFriendlyByteBuf, ReadChartPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, ReadChartPayload::songId,
                ByteBufCodecs.INT, ReadChartPayload::difficulty,
                ByteBufCodecs.BOOL, ReadChartPayload::isEditMode,
                ReadChartPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record ReadyPayload() implements CustomPacketPayload {
        public static final Type<ReadyPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "rc_ready_packet"));
        public static final StreamCodec<RegistryFriendlyByteBuf, ReadyPayload> CODEC = StreamCodec.unit(new ReadyPayload());

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record PlaySongPayload(String id) implements CustomPacketPayload {
        public static final Type<PlaySongPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "rc_play_song_packet"));
        public static final StreamCodec<RegistryFriendlyByteBuf, PlaySongPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, PlaySongPayload::id,
                PlaySongPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record ProfileRequestPayload() implements CustomPacketPayload {
        public static final Type<ProfileRequestPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "rc_profile_request_packet"));
        public static final StreamCodec<RegistryFriendlyByteBuf, ProfileRequestPayload> CODEC = StreamCodec.unit(new ProfileRequestPayload());

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record ProfileUpdatePayload(String dataType, String sortType, int difficulty, int index, String chartKey, ArrayList<Integer> scores) implements CustomPacketPayload {
        public static final Type<ProfileUpdatePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "profile_update_packet"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ProfileUpdatePayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, ProfileUpdatePayload::dataType,
                ByteBufCodecs.STRING_UTF8, ProfileUpdatePayload::sortType,
                ByteBufCodecs.INT, ProfileUpdatePayload::difficulty,
                ByteBufCodecs.INT, ProfileUpdatePayload::index,
                ByteBufCodecs.STRING_UTF8, ProfileUpdatePayload::chartKey,
                ByteBufCodecs.collection(ArrayList::new, ByteBufCodecs.INT), ProfileUpdatePayload::scores,
                ProfileUpdatePayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record AcceptEditPayload() implements CustomPacketPayload {
        public static final Type<AcceptEditPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "rc_accept_edit_packet"));
        public static final StreamCodec<RegistryFriendlyByteBuf, AcceptEditPayload> CODEC = StreamCodec.unit(new AcceptEditPayload());

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record NoteChangedPayload(BlockPos pos, boolean isAdd) implements CustomPacketPayload {
        public static final Type<NoteChangedPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "rc_note_change_packet"));
        public static final StreamCodec<RegistryFriendlyByteBuf, NoteChangedPayload> CODEC = StreamCodec.composite(
                BlockPos.STREAM_CODEC, NoteChangedPayload::pos,
                ByteBufCodecs.BOOL, NoteChangedPayload::isAdd,
                NoteChangedPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record PauseChangedPayload(long tick) implements CustomPacketPayload {
        public static final Type<PauseChangedPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "rc_pause_packet"));
        public static final StreamCodec<RegistryFriendlyByteBuf, PauseChangedPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.VAR_LONG, PauseChangedPayload::tick,
                PauseChangedPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record ProgressChangedPayload(long tick) implements CustomPacketPayload {
        public static final Type<ProgressChangedPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "rc_progress_change_packet"));
        public static final StreamCodec<RegistryFriendlyByteBuf, ProgressChangedPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.VAR_LONG, ProgressChangedPayload::tick,
                ProgressChangedPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record TimeChangedPayload(long tick) implements CustomPacketPayload {
        public static final Type<TimeChangedPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "rc_time_change_packet"));
        public static final StreamCodec<RegistryFriendlyByteBuf, TimeChangedPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.VAR_LONG, TimeChangedPayload::tick,
                TimeChangedPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record GameplayChangePayload(int score, int combo) implements CustomPacketPayload {
        public static final Type<GameplayChangePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "rc_gameplay_change_packet"));
        public static final StreamCodec<RegistryFriendlyByteBuf, GameplayChangePayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, GameplayChangePayload::score,
                ByteBufCodecs.INT, GameplayChangePayload::combo,
                GameplayChangePayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record GameEndPayload(int score, int hits, int prevBest) implements CustomPacketPayload {
        public static final Type<GameEndPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "rc_game_end_packet"));
        public static final StreamCodec<RegistryFriendlyByteBuf, GameEndPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, GameEndPayload::score,
                ByteBufCodecs.INT, GameEndPayload::hits,
                ByteBufCodecs.INT, GameEndPayload::prevBest,
                GameEndPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record LoadBackPayload() implements CustomPacketPayload {
        public static final Type<LoadBackPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "rc_load_back_packet"));
        public static final StreamCodec<RegistryFriendlyByteBuf, LoadBackPayload> CODEC = StreamCodec.unit(new LoadBackPayload());

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record SpaceChangePayload(int space) implements CustomPacketPayload {
        public static final Type<SpaceChangePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "rc_space_change_packet"));
        public static final StreamCodec<RegistryFriendlyByteBuf, SpaceChangePayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, SpaceChangePayload::space,
                SpaceChangePayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static void sendReadChart(String songId, int difficulty, boolean isEditMode) {
        NetworkManager.sendToServer(new ReadChartPayload(songId, difficulty, isEditMode));
    }

    public static void sendReady() {
        NetworkManager.sendToServer(new ReadyPayload());
    }

    public static void sendPlaySong(ServerPlayer player, String id) {
        NetworkManager.sendToPlayer(player, new PlaySongPayload(id));
    }

    public static void sendProfileRequest() {
        NetworkManager.sendToServer(new ProfileRequestPayload());
    }

    public static void sendProfileS2C(RhythmCraftProfile profile, ServerPlayer player) {
        String charKey = "";
        ArrayList<Integer> scores = new ArrayList<>();
        for (String key : profile.chartScores.keySet()) {
            charKey = key;
            scores.addAll(profile.chartScores.get(key));
        }

        NetworkManager.sendToPlayer(player, new ProfileUpdatePayload(
                "",
                profile.sortType.name(),
                profile.difficulty,
                profile.index,
                charKey,
                scores
        ));
    }

    public static void sendProfileUpdateC2S(RhythmCraftProfile profile, ProfileDataType type) {
        String sortType = "";
        int difficulty = 0;
        int index = 0;

        switch (type) {
            case SORT_TYPE:
                sortType = profile.sortType.name();
                break;
            case DIFFICULTY:
                difficulty = profile.difficulty;
                break;
            case INDEX:
                index = profile.index;
                break;
        }

        NetworkManager.sendToServer(new ProfileUpdatePayload(
                type.name(),
                sortType,
                difficulty,
                index,
                "",
                new ArrayList<>()
        ));
    }

    public static void sendAcceptEdit(ServerPlayer player) {
        NetworkManager.sendToPlayer(player, new AcceptEditPayload());
    }

    public static void sendNoteChange(ServerPlayer player, BlockPos pos, boolean isAdd) {
        NetworkManager.sendToPlayer(player, new NoteChangedPayload(pos, isAdd));
    }

    public static void sendPauseChange(long tick) {
        NetworkManager.sendToServer(new PauseChangedPayload(tick));
    }

    public static void sendProgressChange(long tick) {
        NetworkManager.sendToServer(new ProgressChangedPayload(tick));
    }

    public static void sendTimeChange(ServerPlayer player, long tick) {
        NetworkManager.sendToPlayer(player, new TimeChangedPayload(tick));
    }

    public static void sendGameplayChange(ServerPlayer player, int score, int combo) {
        NetworkManager.sendToPlayer(player, new GameplayChangePayload(score, combo));
    }

    public static void sendGameEnd(ServerPlayer player, int score, int hits, int prevBest) {
        NetworkManager.sendToPlayer(player, new GameEndPayload(score, hits, prevBest));
    }

    public static void sendLoadBack() {
        NetworkManager.sendToServer(new LoadBackPayload());
    }

    public static void sendSpaceChange(ServerPlayer player, int space) {
        NetworkManager.sendToPlayer(player, new SpaceChangePayload(space));
    }
}