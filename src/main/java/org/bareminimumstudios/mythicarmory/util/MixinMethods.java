package org.bareminimumstudios.mythicarmory.util;

import net.minecraft.entity.LivingEntity;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;
import org.bareminimumstudios.mythicarmory.registry.ItemRegistry;

public class MixinMethods {
    public static float modifyDamage(LivingEntity entity, float original) {
        if(HelperMethods.isHolding(entity, ItemRegistry.SOLARIS_EDGE, false) && entity.getWorld().isDay()) {
            float multiplier = 1 - MythicArmoryMain.WEAPONS_CONFIG.horizonShift.nightTakenDamageReduction();
            return original * multiplier;
        }

        return original;
    }
}
