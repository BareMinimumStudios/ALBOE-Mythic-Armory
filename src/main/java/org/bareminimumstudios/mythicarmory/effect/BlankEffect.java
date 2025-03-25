package org.bareminimumstudios.mythicarmory.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;

import java.util.function.BiPredicate;

public class BlankEffect extends StatusEffect {
    public BlankEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
}
