package org.bareminimumstudios.mythicarmory.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.bareminimumstudios.mythicarmory.util.MixinMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyVariable(at = @At("HEAD"), method = "applyDamage", index = 2, argsOnly = true)
    private float mythicarmory$modifyDamage(float amount) {
        return MixinMethods.modifyDamage((LivingEntity) (Object) this, amount);
    }
}
