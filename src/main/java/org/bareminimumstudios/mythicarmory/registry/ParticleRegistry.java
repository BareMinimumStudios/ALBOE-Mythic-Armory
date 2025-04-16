package org.bareminimumstudios.mythicarmory.registry;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.bareminimumstudios.mythicarmory.client.particles.RisingNebulaParticle;
import org.bareminimumstudios.mythicarmory.client.particles.ShortNebulaParticle;
import org.bareminimumstudios.mythicarmory.client.particles.SimpleNebulaParticle;
import org.bareminimumstudios.mythicarmory.client.particles.SphericalNebulaParticle;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;

public class ParticleRegistry {

    public static final DefaultParticleType RISING_NEBULA = FabricParticleTypes.simple(true);
    public static final DefaultParticleType NEBULA = FabricParticleTypes.simple(true);
    public static final DefaultParticleType SHORT_NEBULA = FabricParticleTypes.simple(true);
    public static final DefaultParticleType SPHERICAL_NEBULA = FabricParticleTypes.simple(true);


    public static void register() {
        Registry.register(Registries.PARTICLE_TYPE, HelperMethods.identifierOf("rising_nebula"), RISING_NEBULA);
        Registry.register(Registries.PARTICLE_TYPE, HelperMethods.identifierOf("nebula"), NEBULA);
        Registry.register(Registries.PARTICLE_TYPE, HelperMethods.identifierOf("short_nebula"), SHORT_NEBULA);
        Registry.register(Registries.PARTICLE_TYPE, HelperMethods.identifierOf("spherical_nebula"), SPHERICAL_NEBULA);
    }

    public static void registerVisuals() {
        registerVisuals(RISING_NEBULA, RisingNebulaParticle.Factory::new);
        registerVisuals(NEBULA, SimpleNebulaParticle.Factory::new);
        registerVisuals(SHORT_NEBULA, ShortNebulaParticle.Factory::new);
        registerVisuals(SPHERICAL_NEBULA, SphericalNebulaParticle.Factory::new);
    }

    public static <T extends ParticleEffect> void registerVisuals(ParticleType<T> particle, ParticleFactoryRegistry.PendingParticleFactory<T> factory) {
        ParticleFactoryRegistry.getInstance().register(particle, factory);
    }
}
