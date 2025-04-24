package org.bareminimumstudios.mythicarmory.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.bareminimumstudios.mythicarmory.registry.ParticleRegistry;
import org.bareminimumstudios.mythicarmory.util.ParticleHelper;

public class GlideEffect extends StatusEffect {
    public GlideEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);

        ParticleHelper.spawn2DSquare(entity.getWorld(), ParticleRegistry.SHORT_NEBULA,
                entity.getX(), entity.getY() - 0.25, entity.getZ(),
                0.5, 0, 90, 0,
                0.05, 0.2, 3, true);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
