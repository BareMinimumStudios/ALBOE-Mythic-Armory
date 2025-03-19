package org.bareminimumstudios.mythicarmory.util;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;

public class ParticleHelper {

    /**
     * Creates a burst of particles that move outward from the user, constrained to the XZ plane.
     *
     * @param world The world in which to spawn the particles, typically matching the player's.
     * @param particle The particle type to spawn.
     * @param centerX The X coordinate of the burst's center.
     * @param centerY The Y coordinate of the burst's center.
     * @param centerZ The Z coordinate of the burst's center.
     * @param angle The central angle (in degrees) at which the particle burst originates.
     * @param arc The total width of the arc (in degrees) for the particle burst. The arc extends half of this value on either side of the {@code angle}.
     * @param speed The speed at which the particles are emitted outward.
     * @param updrift The vertical momentum applied to each particle. A value of 0 keeps particles straight.
     * @param spacing The angular spacing (in degrees) between each particle. The particles will be spread evenly within the arc, with one particle centered and others placed at equal intervals on either side.
     * @param dy A random offset applied to the Y position of each particle, with the range {@code -dy} to {@code +dy}.
     */
    public static void spawnHorizontalBurst(World world, ParticleEffect particle, double centerX, double centerY, double centerZ, float angle, float arc, float speed, float updrift, float spacing, double dy) {
        // Find number of particles
        // Equal to 1 (center particle) + 2 * the half-arc length worth of spaced particle
        int particlesEitherSide = (int) Math.floor(arc/(2*spacing));
        int totalParticles = 1 + (particlesEitherSide*2);

        double offSet = particlesEitherSide * spacing;

        // Spawn particles
        double particleAngle = angle + 90 - offSet; // Minecraft's direction system is offset by -90, hence we add 90
        for(int i = 0; i < totalParticles; i++) {
            // Calculate velocity
            double radianAngle = Math.toRadians(particleAngle);
            double xVelocity = Math.cos(radianAngle) * speed;
            double zVelocity = Math.sin(radianAngle) * speed;

            // Calculate y position
            double yPosition = centerY;
            yPosition += (world.getRandom().nextFloat() * 2 * dy) - dy;

            // Spawn
            world.addParticle(particle, centerX, yPosition, centerZ, xVelocity, updrift, zVelocity);
            particleAngle += spacing;
        }
    }
}
