package net.depression.mixin.emotion;

import net.depression.util.TempValues;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RandomizableContainer.class)
public interface RandomizableContainerBlockEntityMixin extends Container {
    @Inject(method = "unpackLootTable", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/advancements/critereon/LootTableTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/resources/ResourceKey;)V"))
    private void onUnpackLootTable(Player player, CallbackInfo ci) {
        TempValues.lootPlayer = (ServerPlayer) player;
    }
}
