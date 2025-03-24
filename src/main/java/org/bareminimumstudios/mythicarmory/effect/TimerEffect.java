package org.bareminimumstudios.mythicarmory.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.bareminimumstudios.mythicarmory.registry.EffectRegistry;

import java.util.function.BiPredicate;

public class TimerEffect extends StatusEffect {
    private final BiPredicate<LivingEntity, World> shouldTickUpwards;
    private final int max;

    public TimerEffect(StatusEffectCategory category, int color, BiPredicate<LivingEntity, World> shouldTickUpwards, int max) {
        super(category, color);
        this.shouldTickUpwards = shouldTickUpwards;
        this.max = max;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);

        if(shouldTickUpwards(entity, entity.getWorld())) {
            entity.addStatusEffect(
                    new StatusEffectInstance(
                            this,
                            Math.min(entity.getStatusEffect(this).getDuration() + 2, max + 1),
                            amplifier
                    )
            );
        }
    }

    public boolean shouldTickUpwards(LivingEntity entity, World world) {
        if(world.isClient()) return false;

        return shouldTickUpwards.test(entity, world);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
