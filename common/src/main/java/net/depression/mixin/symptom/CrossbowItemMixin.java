package net.depression.mixin.symptom;

import net.depression.mental.MentalStatus;
import net.depression.server.Registry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
    @Inject(method = "performShooting", at = @At("TAIL"))
    private void onCrossbowShot(Level level, LivingEntity livingEntity, InteractionHand interactionHand, ItemStack itemStack, float f, float g, LivingEntity livingEntity2, CallbackInfo ci) {
        if (livingEntity instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) livingEntity;
            if (player.isCreative()) {
                return;
            }
            MentalStatus mentalStatus = MentalStatus.getMentalStatusByServerPlayer(player);
            mentalStatus.mentalIllness.trigMentalFatigue();
        }
    }
}
