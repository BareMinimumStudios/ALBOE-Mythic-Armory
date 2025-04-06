package org.bareminimumstudios.mythicarmory.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.option.ControlsListWidget;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;
import org.bareminimumstudios.mythicarmory.client.InputHandler;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Environment(EnvType.CLIENT)
@Mixin(ControlsListWidget.KeyBindingEntry.class)
public class KeyBindingEntryMixin {

    @Final
    @Shadow
    private KeyBinding binding;

    @Final
    @Shadow
    private ButtonWidget editButton;

    @Unique
    private boolean isOnlyIssuelessDuplicates = true;
    private boolean hasIssuelessDuplicates = false;

    @Inject(method = "update", at = @At("HEAD"))
    private void mythicarmory$resetIssuelessVariables(CallbackInfo ci) {
        isOnlyIssuelessDuplicates = true;
        hasIssuelessDuplicates = false;
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/MutableText;append(Lnet/minecraft/text/Text;)Lnet/minecraft/text/MutableText;", ordinal = 0))
    private Text mythicarmory$addIssuelessBinding(Text value, @Local(name = "keyBinding") KeyBinding other) {
        if (InputHandler.overrideKeybindings.contains(binding) || InputHandler.overrideKeybindings.contains(other)) {
            hasIssuelessDuplicates = true;
            return Text.literal("* ").append(Text.literal(value.getString())).formatted(Formatting.GRAY).formatted(Formatting.ITALIC);
        }

        isOnlyIssuelessDuplicates = false;
        return value;
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;setMessage(Lnet/minecraft/text/Text;)V", ordinal = 1))
    private Text mythicarmory$changeIssuelessOnlyFormatting(Text value) {
        if(isOnlyIssuelessDuplicates) {
            return Text.literal("[ ")
                    .append(this.editButton.getMessage().copy().formatted(Formatting.WHITE))
                    .append(" ]")
                    .formatted(Formatting.GRAY);
        }
        return value;
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/tooltip/Tooltip;of(Lnet/minecraft/text/Text;)Lnet/minecraft/client/gui/tooltip/Tooltip;", ordinal = 0))
    private Text mythicarmory$addIssuelessDescription(Text value) {
        if(hasIssuelessDuplicates) {
            return value.copy()
                    .append(Text.literal("\n\n"))
                    .append(Text.translatable("tooltip.alboe_mythicarmory.allowed_overbinding")
                            .formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
        }

        return value;
    }

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal = 0))
    private void mythicarmory$changeColor(Args args) {
        if(isOnlyIssuelessDuplicates) {
            args.set(4, HelperMethods.toDecimalColor(170, 170, 170) | -16777216);
        }
    }
}
