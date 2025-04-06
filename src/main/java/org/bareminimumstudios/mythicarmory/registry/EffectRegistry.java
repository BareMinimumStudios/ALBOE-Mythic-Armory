package org.bareminimumstudios.mythicarmory.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.spell_engine.api.effect.Synchronized;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;
import org.bareminimumstudios.mythicarmory.effect.BlankEffect;
import org.bareminimumstudios.mythicarmory.effect.TimerEffect;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;

public class EffectRegistry {

    public static final TimerEffect SOLAR_CHARGE = register("solar_charge", new TimerEffect(
            StatusEffectCategory.BENEFICIAL, 16773856,
            (entity, world) -> world.isDay()
                    && world.isSkyVisible(entity.getBlockPos())
                    && HelperMethods.isHolding(entity, ItemRegistry.SOLARIS_EDGE, false)
                    && !entity.hasStatusEffect(EffectRegistry.SOLAR_DRAIN),
            MythicArmoryMain.WEAPONS_CONFIG.solarOverload.ticksCanStore()));

    public static final TimerEffect SOLAR_DRAIN = register("solar_drain", new TimerEffect(
            StatusEffectCategory.HARMFUL, 16773856,
            MythicArmoryMain.WEAPONS_CONFIG.solarOverload.cooldown()));

    public static final StatusEffect LUNAR_SHIELD = register("lunar_shield", new BlankEffect(
            StatusEffectCategory.BENEFICIAL, 14935011
    ));

    public static final StatusEffect GLIDE = register("glide", new BlankEffect(
            StatusEffectCategory.NEUTRAL, 0
    ));

    public static final TimerEffect ZEPHYR_DEFICIT = register("zephyr_deficit", new TimerEffect(
            StatusEffectCategory.HARMFUL, HelperMethods.toDecimalColor(82, 2, 89),
            MythicArmoryMain.WEAPONS_CONFIG.zephyr.dashCooldown()));

    public static final TimerEffect ZEPHYR_ENERGY = register("zephyr_energy", new TimerEffect(
            StatusEffectCategory.BENEFICIAL, HelperMethods.toDecimalColor(60, 60, 180),
            (entity, world) -> entity.isOnGround()
                    && world.getTime() % 3 < 2
                    && HelperMethods.isHolding(entity, ItemRegistry.SKY_THRESHER, true),
            (entity, world) -> (!entity.hasStatusEffect(GLIDE) || entity.isOnGround())
                    && HelperMethods.isHolding(entity, ItemRegistry.SKY_THRESHER, true),
            MythicArmoryMain.WEAPONS_CONFIG.zephyr.slowFallTime()));


    public static <T extends StatusEffect> T register(String id, T effect) {
        Registry.register(Registries.STATUS_EFFECT, HelperMethods.identifierOf(id), effect);
        return effect;
    }

    public static void register() {
        Synchronized.configure(LUNAR_SHIELD, true);
    }
}
