package org.bareminimumstudios.mythicarmory.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;
import org.bareminimumstudios.mythicarmory.effect.TimerEffect;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;

public class EffectRegistry {

    public static final StatusEffect SOLAR_CHARGE = register("solar_charge", new TimerEffect(
            StatusEffectCategory.BENEFICIAL, 16773856,
            (entity, world) -> world.isDay()
                    && world.isSkyVisible(entity.getBlockPos())
                    && HelperMethods.isHolding(entity, ItemRegistry.SOLARIS_EDGE, false)
                    && !entity.hasStatusEffect(EffectRegistry.SOLAR_DRAIN),
            MythicArmoryMain.WEAPONS_CONFIG.solarOverload.ticksCanStore()));

    public static final StatusEffect SOLAR_DRAIN = register("solar_drain", new TimerEffect(
            StatusEffectCategory.HARMFUL, 16773856,
            (entity, world) -> false,
            MythicArmoryMain.WEAPONS_CONFIG.solarOverload.cooldown()));


    public static StatusEffect register(String id, StatusEffect effect) {
        Registry.register(Registries.STATUS_EFFECT, HelperMethods.identifierOf(id), effect);
        return effect;
    }

    public static void register() {}
}
