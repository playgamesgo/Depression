package net.depression.client;

import dev.architectury.event.events.client.*;
import dev.architectury.networking.NetworkManager;
import net.depression.client.rhythmcraft.ClientPlayingChart;
import net.depression.config.ClientConfig;
import net.depression.key.KeyMappings;
import net.depression.listener.client.ClientLifecycleEventListener;
import net.depression.listener.client.ClientRawInputEventListener;
import net.depression.listener.client.ClientTickEventListener;
import net.depression.network.*;
import net.depression.rhythmcraft.RhythmCraftProfile;
import net.depression.rhythmcraft.SongSortType;
import net.depression.screen.rhythmcraft.GameGuiRenderer;
import net.depression.screen.rhythmcraft.RCSelectionScreen;
import net.depression.util.OggStreamPlayer;

import java.util.ArrayList;

public class DepressionClient {
    public static boolean ENABLE_COMPUTER;
    public static final ClientMentalStatus clientMentalStatus = new ClientMentalStatus();
    public static final ClientActionbarHint clientActionbarHint = new ClientActionbarHint();
    public static RhythmCraftProfile rcProfile = new RhythmCraftProfile();
    public static OggStreamPlayer oggStreamPlayer = new OggStreamPlayer();
    public static ClientPlayingChart playingChart;

    public static void onInitializeClient() {
        KeyMappings.init();

        NetworkManager.registerReceiver(NetworkManager.Side.S2C, MentalStatusPacket.EmotionPayload.TYPE, MentalStatusPacket.EmotionPayload.CODEC,
                clientMentalStatus::receiveEmotionPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, MentalStatusPacket.MentalHealthPayload.TYPE, MentalStatusPacket.MentalHealthPayload.CODEC,
                clientMentalStatus::receiveMentalHealthPacket);

        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ActionbarHintPacket.OverdosePayload.TYPE, ActionbarHintPacket.OverdosePayload.CODEC,
                clientActionbarHint::receiveOverdosePacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ActionbarHintPacket.BipolarPayload.TYPE, ActionbarHintPacket.BipolarPayload.CODEC,
                clientActionbarHint::receiveBipolarPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ActionbarHintPacket.ComponentPayload.NEARBY_BLOCK_TYPE, ActionbarHintPacket.ComponentPayload.CODEC,
                clientActionbarHint::receiveNearbyBlockHealPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ActionbarHintPacket.ComponentPayload.BREAK_BLOCK_TYPE, ActionbarHintPacket.ComponentPayload.CODEC,
                clientActionbarHint::receiveBreakBlockHealPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ActionbarHintPacket.ComponentPayload.KILL_ENTITY_TYPE, ActionbarHintPacket.ComponentPayload.CODEC,
                clientActionbarHint::receiveKillEntityHealPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ActionbarHintPacket.ComponentPayload.FISH_TYPE, ActionbarHintPacket.ComponentPayload.CODEC,
                clientActionbarHint::receiveFishHealPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ActionbarHintPacket.ComponentPayload.FEED_ANIMAL_TYPE, ActionbarHintPacket.ComponentPayload.CODEC,
                clientActionbarHint::receiveFeedAnimalHealPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ActionbarHintPacket.ComponentPayload.PET_TYPE, ActionbarHintPacket.ComponentPayload.CODEC,
                clientActionbarHint::receivePetHealPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ActionbarHintPacket.EmptyPayload.LOOT_TYPE, ActionbarHintPacket.EmptyPayload.CODEC,
                clientActionbarHint::receiveLootHealPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ActionbarHintPacket.ComponentPayload.PTSD_FORM_TYPE, ActionbarHintPacket.ComponentPayload.CODEC,
                clientActionbarHint::receivePTSDFormPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ActionbarHintPacket.ComponentPayload.PTSD_DISPERSE_TYPE, ActionbarHintPacket.ComponentPayload.CODEC,
                clientActionbarHint::receivePTSDDispersePacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ActionbarHintPacket.ComponentPayload.PTSD_REMISSION_TYPE, ActionbarHintPacket.ComponentPayload.CODEC,
                clientActionbarHint::receivePTSDRemissionPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ActionbarHintPacket.EmptyPayload.INSOMNIA_TYPE, ActionbarHintPacket.EmptyPayload.CODEC,
                clientActionbarHint::receiveInsomniaPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ActionbarHintPacket.EmptyPayload.MENTAL_FATIGUE_TYPE, ActionbarHintPacket.EmptyPayload.CODEC,
                clientActionbarHint::receiveMentalFatiguePacket);

        NetworkManager.registerReceiver(NetworkManager.Side.S2C, CloseEyePacket.TYPE, CloseEyePacket.CODEC,
                (buf, context) -> {
                    clientMentalStatus.mentalIllness.receiveCloseEyePacket(buf, context);
                });

        NetworkManager.registerReceiver(NetworkManager.Side.S2C, PTSDOnsetPacket.PTSDOnsetPayload.TYPE, PTSDOnsetPacket.PTSDOnsetPayload.CODEC,
                (payload, context) -> clientMentalStatus.ptsdManager.receivePTSDOnsetPacket(payload, context));
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, PTSDOnsetPacket.PhotosmPayload.TYPE, PTSDOnsetPacket.PhotosmPayload.CODEC,
                ClientPTSDManager::receivePhotismPacket);

        NetworkManager.registerReceiver(NetworkManager.Side.S2C, DiaryUpdatePacket.DiaryUpdatePayload.TYPE, DiaryUpdatePacket.DiaryUpdatePayload.CODEC,
                ClientDiaryUpdater::receiveDiaryUpdatePacket);

        NetworkManager.registerReceiver(NetworkManager.Side.S2C, MentalTraitPacket.MentalTraitPayload.TYPE, MentalTraitPacket.MentalTraitPayload.CODEC,
                ClientMentalStatus::receiveMentalTraitPacket);

        NetworkManager.registerReceiver(NetworkManager.Side.S2C, RhythmCraftPacket.ProfileUpdatePayload.TYPE, RhythmCraftPacket.ProfileUpdatePayload.CODEC,
                DepressionClient::receiveRCProfilePacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, RhythmCraftPacket.PlaySongPayload.TYPE, RhythmCraftPacket.PlaySongPayload.CODEC,
                ClientPlayingChart::receivePlaySongPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, RhythmCraftPacket.AcceptEditPayload.TYPE, RhythmCraftPacket.AcceptEditPayload.CODEC,
                RCSelectionScreen::receiveAcceptEditPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, RhythmCraftPacket.NoteChangedPayload.TYPE, RhythmCraftPacket.NoteChangedPayload.CODEC,
                ClientPlayingChart::receiveNoteChangePacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, RhythmCraftPacket.GameplayChangePayload.TYPE, RhythmCraftPacket.GameplayChangePayload.CODEC,
                ClientPlayingChart::receiveGameplayChangePacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, RhythmCraftPacket.TimeChangedPayload.TYPE, RhythmCraftPacket.TimeChangedPayload.CODEC,
                ClientPlayingChart::receiveTimeChangePacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, RhythmCraftPacket.GameEndPayload.TYPE, RhythmCraftPacket.GameEndPayload.CODEC,
                ClientPlayingChart::receiveGameEndPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, RhythmCraftPacket.SpaceChangePayload.TYPE, RhythmCraftPacket.SpaceChangePayload.CODEC,
                ClientPlayingChart::receiveSpaceChangePacket);

        ClientGuiEvent.RENDER_HUD.register(clientMentalStatus::renderHud);
        ClientGuiEvent.RENDER_HUD.register(GameGuiRenderer::renderHud);
        ClientLifecycleEvent.CLIENT_LEVEL_LOAD.register(ClientLifecycleEventListener::onClientLevelLoad);
        ClientLifecycleEvent.CLIENT_STOPPING.register(ClientLifecycleEventListener::onClientStopping);
        ClientRawInputEvent.MOUSE_SCROLLED.register(ClientRawInputEventListener::onMouseScrolled);
        ClientRawInputEvent.MOUSE_CLICKED_PRE.register(ClientRawInputEventListener::onMouseClicked);
        ClientTickEvent.CLIENT_LEVEL_PRE.register(ClientTickEventListener::onClientLevelTick);
        ClientTickEvent.CLIENT_PRE.register(ClientTickEventListener::onClientTick);
        ClientConfig.load();
    }

    public static void receiveRCProfilePacket(RhythmCraftPacket.ProfileUpdatePayload buf, NetworkManager.PacketContext packetContext) {
        rcProfile.sortType = SongSortType.valueOf(buf.sortType());
        rcProfile.difficulty = buf.difficulty();
        rcProfile.index = buf.index();
        String key = buf.chartKey();
        ArrayList<Integer> scores = buf.scores();
        rcProfile.chartScores.put(key, scores);
    }
}
