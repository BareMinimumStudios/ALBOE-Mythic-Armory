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
    private final BiPredicate<LivingEntity, World> shouldSustain;
    private final int max;

    public TimerEffect(StatusEffectCategory category, int color, int max) {
        this(category, color, (e, w) -> false, (e, w) -> false, max);
    }

    public TimerEffect(StatusEffectCategory category, int color, BiPredicate<LivingEntity, World> shouldTickUpwards, int max) {
        this(category, color, shouldTickUpwards, (e, w) -> false, max);
    }

    public TimerEffect(StatusEffectCategory category, int color, BiPredicate<LivingEntity, World> shouldTickUpwards,  BiPredicate<LivingEntity, World> shouldSustain, int max) {
        super(category, color);
        this.shouldTickUpwards = shouldTickUpwards;
        this.shouldSustain = shouldSustain;
        this.max = max;
    }

    public int getTime(LivingEntity entity) {
        if(entity.hasStatusEffect(this)) {
            return entity.getStatusEffect(this).getDuration();
        }

        return 0;
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
        } else if (shouldSustain(entity, entity.getWorld())) {
            entity.addStatusEffect(
                    new StatusEffectInstance(
                            this,
                            Math.min(entity.getStatusEffect(this).getDuration() + 1, max + 1),
                            amplifier
                    )
            );
        }
    }

    public boolean shouldTickUpwards(LivingEntity entity, World world) {
        if(world.isClient()) return false;

        return shouldTickUpwards.test(entity, world);
    }

    public boolean shouldSustain(LivingEntity entity, World world) {
        if(world.isClient()) return false;

        return shouldSustain.test(entity, world);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
