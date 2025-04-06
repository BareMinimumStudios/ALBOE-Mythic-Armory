package org.bareminimumstudios.mythicarmory.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.LivingEntity;
import org.bareminimumstudios.mythicarmory.client.InputHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(KeyBinding.class)
public class KeyBindingMixin {

    @Inject(method = "onKeyPressed", at = @At("TAIL"))
    private static void mythicarmory$overrideKeyBinds(InputUtil.Key key, CallbackInfo ci, @Local KeyBinding keyBinding) {
        for (KeyBinding override : InputHandler.overrideKeybindings) {
            if(keyBinding != null && override != keyBinding && override.equals(keyBinding)) {
                override.timesPressed++;
            }
        }
    }

    @Inject(method = "setKeyPressed", at = @At("TAIL"))
    private static void mythicarmory$overrideKeyBinds(InputUtil.Key key, boolean pressed, CallbackInfo ci, @Local KeyBinding keyBinding) {
        for (KeyBinding override : InputHandler.overrideKeybindings) {
            if(keyBinding != null && override != keyBinding && override.equals(keyBinding)) {
                override.setPressed(pressed);
            }
        }
    }
}
