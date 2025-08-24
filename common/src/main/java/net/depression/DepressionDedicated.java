package net.depression;

import dev.architectury.networking.NetworkManager;
import net.depression.network.*;

public class DepressionDedicated {
    public static void onInitializeServer() {
        NetworkManager.registerS2CPayloadType(MentalStatusPacket.EmotionPayload.TYPE, MentalStatusPacket.EmotionPayload.CODEC);
        NetworkManager.registerS2CPayloadType(MentalStatusPacket.MentalHealthPayload.TYPE, MentalStatusPacket.MentalHealthPayload.CODEC);
        NetworkManager.registerS2CPayloadType(ActionbarHintPacket.OverdosePayload.TYPE, ActionbarHintPacket.OverdosePayload.CODEC);
        NetworkManager.registerS2CPayloadType(ActionbarHintPacket.BipolarPayload.TYPE, ActionbarHintPacket.BipolarPayload.CODEC);
        NetworkManager.registerS2CPayloadType(ActionbarHintPacket.ComponentPayload.NEARBY_BLOCK_TYPE, ActionbarHintPacket.ComponentPayload.CODEC);
        NetworkManager.registerS2CPayloadType(ActionbarHintPacket.ComponentPayload.BREAK_BLOCK_TYPE, ActionbarHintPacket.ComponentPayload.CODEC);
        NetworkManager.registerS2CPayloadType(ActionbarHintPacket.ComponentPayload.KILL_ENTITY_TYPE, ActionbarHintPacket.ComponentPayload.CODEC);
        NetworkManager.registerS2CPayloadType(ActionbarHintPacket.ComponentPayload.FISH_TYPE, ActionbarHintPacket.ComponentPayload.CODEC);
        NetworkManager.registerS2CPayloadType(ActionbarHintPacket.ComponentPayload.FEED_ANIMAL_TYPE, ActionbarHintPacket.ComponentPayload.CODEC);
        NetworkManager.registerS2CPayloadType(ActionbarHintPacket.ComponentPayload.PET_TYPE, ActionbarHintPacket.ComponentPayload.CODEC);
        NetworkManager.registerS2CPayloadType(ActionbarHintPacket.EmptyPayload.LOOT_TYPE, ActionbarHintPacket.EmptyPayload.CODEC);
        NetworkManager.registerS2CPayloadType(ActionbarHintPacket.ComponentPayload.PTSD_FORM_TYPE, ActionbarHintPacket.ComponentPayload.CODEC);
        NetworkManager.registerS2CPayloadType(ActionbarHintPacket.ComponentPayload.PTSD_DISPERSE_TYPE, ActionbarHintPacket.ComponentPayload.CODEC);
        NetworkManager.registerS2CPayloadType(ActionbarHintPacket.ComponentPayload.PTSD_REMISSION_TYPE, ActionbarHintPacket.ComponentPayload.CODEC);
        NetworkManager.registerS2CPayloadType(ActionbarHintPacket.EmptyPayload.INSOMNIA_TYPE, ActionbarHintPacket.EmptyPayload.CODEC);
        NetworkManager.registerS2CPayloadType(ActionbarHintPacket.EmptyPayload.MENTAL_FATIGUE_TYPE, ActionbarHintPacket.EmptyPayload.CODEC);
        NetworkManager.registerS2CPayloadType(CloseEyePacket.TYPE, CloseEyePacket.CODEC);
        NetworkManager.registerS2CPayloadType(PTSDOnsetPacket.PTSDOnsetPayload.TYPE, PTSDOnsetPacket.PTSDOnsetPayload.CODEC);
        NetworkManager.registerS2CPayloadType(PTSDOnsetPacket.PhotosmPayload.TYPE, PTSDOnsetPacket.PhotosmPayload.CODEC);
        NetworkManager.registerS2CPayloadType(DiaryUpdatePacket.DiaryUpdatePayload.TYPE, DiaryUpdatePacket.DiaryUpdatePayload.CODEC);
        NetworkManager.registerS2CPayloadType(MentalTraitPacket.MentalTraitPayload.TYPE, MentalTraitPacket.MentalTraitPayload.CODEC);
        NetworkManager.registerS2CPayloadType(RhythmCraftPacket.ProfileUpdatePayload.TYPE, RhythmCraftPacket.ProfileUpdatePayload.CODEC);
        NetworkManager.registerS2CPayloadType(RhythmCraftPacket.PlaySongPayload.TYPE, RhythmCraftPacket.PlaySongPayload.CODEC);
        NetworkManager.registerS2CPayloadType(RhythmCraftPacket.AcceptEditPayload.TYPE, RhythmCraftPacket.AcceptEditPayload.CODEC);
        NetworkManager.registerS2CPayloadType(RhythmCraftPacket.NoteChangedPayload.TYPE, RhythmCraftPacket.NoteChangedPayload.CODEC);
        NetworkManager.registerS2CPayloadType(RhythmCraftPacket.GameplayChangePayload.TYPE, RhythmCraftPacket.GameplayChangePayload.CODEC);
        NetworkManager.registerS2CPayloadType(RhythmCraftPacket.TimeChangedPayload.TYPE, RhythmCraftPacket.TimeChangedPayload.CODEC);
        NetworkManager.registerS2CPayloadType(RhythmCraftPacket.GameEndPayload.TYPE, RhythmCraftPacket.GameEndPayload.CODEC);
        NetworkManager.registerS2CPayloadType(RhythmCraftPacket.SpaceChangePayload.TYPE, RhythmCraftPacket.SpaceChangePayload.CODEC);
    }
}
