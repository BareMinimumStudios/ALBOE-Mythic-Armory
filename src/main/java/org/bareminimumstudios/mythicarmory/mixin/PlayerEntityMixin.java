package org.bareminimumstudios.mythicarmory.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.bareminimumstudios.mythicarmory.util.MixinMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @ModifyVariable(at = @At("HEAD"), method = "applyDamage", index = 2, argsOnly = true)
    private float mythicarmory$modifyDamage(float amount, DamageSource source) {
        return MixinMethods.modifyDamage((PlayerEntity) (Object) this, amount, source);
    }
}
