package net.depression.listener;

import dev.architectury.event.EventResult;
import net.depression.block.ComputerBlock;
import net.depression.client.DepressionClient;
import net.depression.listener.client.ClientTickEventListener;
import net.depression.network.RhythmCraftPacket;
import net.depression.rhythmcraft.PlayingChart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;


public class InteractionEventListener {
    public static EventResult onRightClickBlock(Player player, InteractionHand hand, BlockPos blockPos, Direction direction) {
        Level level = player.level();
        Block block = level.getBlockState(blockPos).getBlock();
        if (level.isClientSide() && DepressionClient.ENABLE_COMPUTER && block instanceof ComputerBlock) {
            ClientTickEventListener.isSetComputerScreen = true;
        }
        if (player instanceof ServerPlayer serverPlayer
                && serverPlayer.serverLevel() instanceof PlayingChart playingChart
                    && playingChart.isEditMode
                        && player.getMainHandItem().is(Items.STICK)) {
            if (!playingChart.chart.notes.contains(blockPos)) {
                playingChart.chart.notes.add(blockPos);
                playingChart.chart.isEdited = true;
                RhythmCraftPacket.sendNoteChange(serverPlayer, blockPos, true);
            }
        }
        return EventResult.pass();
    }
}
