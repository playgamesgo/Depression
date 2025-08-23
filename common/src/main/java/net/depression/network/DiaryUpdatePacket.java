package net.depression.network;

import dev.architectury.networking.NetworkManager;
import net.depression.Depression;
import net.depression.item.diary.ConditionComponents;
import net.depression.mental.MentalStatus;
import net.depression.server.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class DiaryUpdatePacket {
    public record DiaryUpdatePayload(String content) implements CustomPacketPayload {
        public static final Type<DiaryUpdatePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Depression.MOD_ID, "diary_update_packet"));

        public static final StreamCodec<RegistryFriendlyByteBuf, DiaryUpdatePayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, DiaryUpdatePayload::content,
                DiaryUpdatePayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static void sendToServer(String content) {
        NetworkManager.sendToServer(new DiaryUpdatePayload(content));
    }

    public static void sendToPlayer(ServerPlayer player) {
        MentalStatus mentalStatus = Registry.mentalStatus.get(player.getUUID());
        double mentalHealthValue;
        if (mentalStatus == null) {
            mentalHealthValue = 100;
        }
        else {
            mentalHealthValue = mentalStatus.mentalHealthValue;
        }
        String content = "";
        if (85 <= mentalHealthValue && mentalHealthValue <= 100) { //健康1
            content = "    'diary.depression.healthy_1.1'\n    "
                    + ConditionComponents.HEALTHY_1_WEATHER.get(player)
                    + ConditionComponents.HEALTHY_1_BREED.get(player)
                    + ConditionComponents.HEALTHY_1_EAT.get(player)
                    + "\n    'diary.depression.healthy_1.2'"
                    + ConditionComponents.HEALTHY_1_KILL_MOBS.get(player);
        }
        else if (70 <= mentalHealthValue && mentalHealthValue < 85) { //健康2
            content = "    'diary.depression.healthy_2.1'\n    "
                    + ConditionComponents.HEALTHY_2_BREED.get(player)
                    + ConditionComponents.HEALTHY_2_WEATHER.get(player)
                    + ConditionComponents.HEALTHY_2_MOVE_IN_WEATHER.get(player)
                    + "\n    'diary.depression.healthy_2.2'";
        }
        else if (55 <= mentalHealthValue && mentalHealthValue < 70) { //轻度抑郁1
            content = "    'diary.depression.mild_depression_1.1'\n    "
                    + ConditionComponents.MILD_DEPRESSION_1_WEATHER.get(player)
                    + ConditionComponents.MILD_DEPRESSION_1_MOVE_IN_WEATHER.get(player)
                    + "\n    'diary.depression.mild_depression_1.2'"
                    + ConditionComponents.MILD_DEPRESSION_1_MOVE.get(player);
        }
        else if (55 <= mentalHealthValue && mentalHealthValue < 70) { //轻度抑郁1
            content = "    'diary.depression.mild_depression_1.1'\n    "
                    + ConditionComponents.MILD_DEPRESSION_1_WEATHER.get(player)
                    + ConditionComponents.MILD_DEPRESSION_1_MOVE_IN_WEATHER.get(player)
                    + "\n    'diary.depression.mild_depression_1.2'"
                    + ConditionComponents.MILD_DEPRESSION_1_MOVE.get(player);
        }
        else if (40 <= mentalHealthValue && mentalHealthValue < 55) { //轻度抑郁2
            content = "    'diary.depression.mild_depression_2.1'\n    "
                    + ConditionComponents.MILD_DEPRESSION_2_WEATHER.get(player)
                    + ConditionComponents.MILD_DEPRESSION_2_EAT.get(player)
                    + "\n    'diary.depression.mild_depression_2.2'"
                    + ConditionComponents.MILD_DEPRESSION_2_HURT.get(player);
        }
        else if (30 <= mentalHealthValue && mentalHealthValue < 40) { //中度抑郁1
            content = "    'diary.depression.moderate_depression_1.1'\n    "
                    + ConditionComponents.MODERATE_DEPRESSION_1_KILL_ENTITIES.get(player)
                    + ConditionComponents.MODERATE_DEPRESSION_1_WEATHER.get(player)
                    + ConditionComponents.MODERATE_DEPRESSION_1_EAT.get(player)
                    + ConditionComponents.MODERATE_DEPRESSION_1_HURT.get(player)
                    + "\n    'diary.depression.moderate_depression_1.2'"
                    + ConditionComponents.MODERATE_DEPRESSION_1_MOB_NEARBY.get(player);
        }
        else if (20 <= mentalHealthValue && mentalHealthValue < 30) { //中度抑郁2
            content = "    'diary.depression.moderate_depression_2.1'"
                    + "\n    'diary.depression.moderate_depression_2.2'"
                    + ConditionComponents.MODERATE_DEPRESSION_2_EAT.get(player)
                    + "\n    'diary.depression.moderate_depression_2.3'"
                    + ConditionComponents.MODERATE_DEPRESSION_2_HURT.get(player);
        }
        else if (10 <= mentalHealthValue && mentalHealthValue < 20) { //重度抑郁1
            if (mentalStatus.isMania()) {
                content = "    'diary.depression.mania_1.1'";
            }
            else {
                content = "    'diary.depression.major_depressive_disorder_1.1'";
            }
        }
        else if (0 <= mentalHealthValue && mentalHealthValue < 10) { //重度抑郁2
            if (mentalStatus.isMania()) {
                content = "    'diary.depression.mania_2.1'";
            }
            else {
                content = "    'diary.depression.major_depressive_disorder_2.1'"
                        + ConditionComponents.MAJOR_DEPRESSIVE_DISORDER_2_EAT.get(player)
                        + "\n    'diary.depression.major_depressive_disorder_2.2'";
            }
        }

        NetworkManager.sendToPlayer(player, new DiaryUpdatePayload(content));
    }
}
