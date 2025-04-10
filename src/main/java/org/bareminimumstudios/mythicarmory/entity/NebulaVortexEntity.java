package org.bareminimumstudios.mythicarmory.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;
import org.bareminimumstudios.mythicarmory.registry.ParticleRegistry;
import org.bareminimumstudios.mythicarmory.util.ParticleHelper;

public class NebulaVortexEntity extends AbilityPointEntity {

    public NebulaVortexEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        Random random = this.getRandom();

        // Particles
        ParticleHelper.spawnHorizontalBurst(this.getWorld(), ParticleRegistry.RISING_NEBULA,
                this.getX(), this.getY(), this.getZ(), random.nextBetween(0, 360), 360,
                random.nextBetween(15, 30) / 10f, random.nextBetween(5, 40) / 10f, 120, 0.1);

        ParticleHelper.spawnHorizontalBurst(this.getWorld(), ParticleRegistry.RISING_NEBULA,
                this.getX(), this.getY(), this.getZ(), 0, 360,
                random.nextBetween(10, 20) / 10f, 3, 60, 0.1);

        // Pull
        if(this.age % MythicArmoryMain.WEAPONS_CONFIG.mistral.pullInterval() == 0) {
            double range = MythicArmoryMain.WEAPONS_CONFIG.mistral.pullRange();
            Box box = new Box(
                    this.getX() + range,
                    this.getY() + 10,
                    this.getZ() + range,
                    this.getX() - range,
                    this.getY() - 5,
                    this.getZ() - range);

            this.getWorld().getNonSpectatingEntities(LivingEntity.class, box).forEach(
                    (entity -> {
                        if(entity == this || entity == this.getOwner() || (this.getOwner() != null && entity.isTeammate(this.getOwner()))) return;

                        entity.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.NAUSEA,
                                MythicArmoryMain.WEAPONS_CONFIG.mistral.nauseaDuration(),
                                3
                        ));

                        Vec3d movement = this.getPos().subtract(entity.getPos());
                        entity.addVelocity(movement.normalize().multiply(MythicArmoryMain.WEAPONS_CONFIG.mistral.pullStrength()));
                    })
            );
        }

        // Die
        if(this.age >= MythicArmoryMain.WEAPONS_CONFIG.mistral.vortexDuration()) {
            this.discard();
        }
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0);
    }
}
