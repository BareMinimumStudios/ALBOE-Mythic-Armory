package org.bareminimumstudios.mythicarmory.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;
import org.bareminimumstudios.mythicarmory.registry.EffectRegistry;
import org.bareminimumstudios.mythicarmory.registry.ItemRegistry;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;
import org.bareminimumstudios.mythicarmory.util.MixinMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyVariable(at = @At("HEAD"), method = "applyDamage", index = 2, argsOnly = true)
    private float mythicarmory$modifyDamage(float amount, DamageSource source) {
        return MixinMethods.modifyDamage((LivingEntity) (Object) this, amount, source);
    }

    @ModifyConstant(method = "travel", constant = @Constant(doubleValue = 0.08d))
    private double mythicarmory$modifyInitialFallSpeed(double initialValue) {
        return modifyFallSpeed((LivingEntity) (Object) this, initialValue, 0.03125f);
    }

    @ModifyConstant(method = "travel", constant = @Constant(doubleValue = 0.01d))
    private double mythicarmory$modifySlowFallFallSpeed(double initialValue) {
        return modifyFallSpeed((LivingEntity) (Object) this, initialValue, 0.25f);
    }

    @Unique
    private static double modifyFallSpeed(LivingEntity entity, double initialValue, double modifier) {
        boolean bl = entity.getVelocity().y < -0.1d; // -0.1d used to prevent jittering when walking on the floor.

        if(bl && entity.hasStatusEffect(EffectRegistry.GLIDE)) {
            initialValue *= modifier;
            entity.fallDistance = 0.0f;
        }
        return initialValue;
    }

    @ModifyReturnValue(method = "getJumpVelocity", at = @At("RETURN"))
    private float mythicarmory$modifyJumpVelocity(float original) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if(HelperMethods.isHolding(entity, ItemRegistry.SKY_THRESHER, true)) {
            original *= 1 + MythicArmoryMain.WEAPONS_CONFIG.zephyr.jumpHeightIncrease();
        }

        return original;
    }

    @ModifyReturnValue(method = "computeFallDamage", at = @At("RETURN"))
    public int mythicarmory$removeFallDamage(int original) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if(entity.hasStatusEffect(EffectRegistry.ZEPHYR_ENERGY)) return 0;

        return original;
    }
}
