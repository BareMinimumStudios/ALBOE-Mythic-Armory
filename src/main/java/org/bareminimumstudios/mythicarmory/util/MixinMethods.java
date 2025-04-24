package org.bareminimumstudios.mythicarmory.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;
import org.bareminimumstudios.mythicarmory.registry.EffectRegistry;
import org.bareminimumstudios.mythicarmory.registry.ItemRegistry;

import java.util.List;

public class MixinMethods {
    public static float modifyDamage(LivingEntity entity, float original, DamageSource source) {
        if(HelperMethods.isHolding(entity, ItemRegistry.SOLARIS_EDGE, false) && entity.getWorld().isDay()) {
            float multiplier = 1 - MythicArmoryMain.WEAPONS_CONFIG.horizonShift.nightTakenDamageReduction();
            return original * multiplier;
        }

        if(entity.hasStatusEffect(EffectRegistry.LUNAR_SHIELD) && source.getAttacker() != null) {
            Vec3d dir = source.getAttacker().getPos()
                    .subtract(entity.getPos()).normalize();

            if(isFromFront(entity, dir)) {
                original *= 0.5f;

                entity.getWorld().playSound(
                        null, entity.getBlockPos(),
                        SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE,
                        SoundCategory.PLAYERS,
                        1, 1
                );

                Box aoe = new Box(
                        entity.getX() - 5,
                        entity.getY() - 5,
                        entity.getZ() - 5,
                        entity.getX() + 5,
                        entity.getY() + 5,
                        entity.getZ() + 5
                );

                List<LivingEntity> enemies = entity.getWorld().getNonSpectatingEntities(LivingEntity.class, aoe);

                for(LivingEntity enemy : enemies) {
                    if (enemy.isTeammate(entity) || enemy == entity) continue;

                    ((ServerWorld) enemy.getWorld()).spawnParticles(
                            ParticleTypes.ENCHANTED_HIT,
                            enemy.getX(), enemy.getEyeY(), enemy.getZ(),
                            20, 0, 0, 0, 0.4
                    );

                    enemy.addStatusEffect(
                            new StatusEffectInstance(
                                    StatusEffects.BLINDNESS,
                                    50
                            )
                    );

                    enemy.damage(enemy.getDamageSources().magic(), original * 0.15f);
                }
            }
        }

        return original;
    }

    private static boolean isFromFront(LivingEntity entity, Vec3d dir) {
        float lowerBound = normaliseAngle(entity.getYaw() - 90);
        float attackAngle = (float) Math.toDegrees(Math.atan2(-dir.getX(), dir.getZ()));
        float upperBound = normaliseAngle(entity.getYaw() + 90);

        boolean isFromFront;
        if (lowerBound <= upperBound) {
            isFromFront = attackAngle >= lowerBound && attackAngle <= upperBound;
        } else {
            isFromFront = attackAngle >= lowerBound || attackAngle <= upperBound;
        }
        return isFromFront;
    }

    public static float normaliseAngle(float angle) {
        while (angle <= -180) angle += 360;
        while (angle > 180) angle -= 360;
        return angle;
    }
}
