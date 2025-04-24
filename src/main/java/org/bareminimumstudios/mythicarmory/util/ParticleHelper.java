package org.bareminimumstudios.mythicarmory.util;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.bareminimumstudios.mythicarmory.networking.S2C.S2CBurstParticles;
import org.bareminimumstudios.mythicarmory.networking.S2C.S2CSquareParticles;

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
    public static void spawnHorizontalBurst(World world, DefaultParticleType particle, double centerX, double centerY, double centerZ, float angle, float arc, double speed, float updrift, float spacing, double dy) {
        if (!world.isClient()) {
            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, new BlockPos((int) Math.round(centerX), (int) Math.round(centerY), (int) Math.round(centerZ)))) {
                ServerPlayNetworking.send(player, new S2CBurstParticles(Registries.PARTICLE_TYPE.getId(particle),
                        centerX, centerY, centerZ, angle, arc, speed, updrift, spacing, dy));
            }
        } else {
            // Find number of particles
            // Equal to 1 (center particle) + 2 * the half-arc length worth of spaced particle
            int particlesEitherSide = (int) Math.floor(arc / (2 * spacing));
            int totalParticles = 1 + (particlesEitherSide * 2);

            double offSet = particlesEitherSide * spacing;

            // Spawn particles
            double particleAngle = angle + 90 - offSet; // Minecraft's direction system is offset by -90, hence we add 90
            for (int i = 0; i < totalParticles; i++) {
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

    /**
     * Creates a flat square or circle of particles.
     *
     * @param world The world in which to spawn the particles, typically matching the player's.
     * @param particle The particle type to spawn.
     * @param x The X coordinate of the square's center.
     * @param y The Y coordinate of the square's center.
     * @param z The Z coordinate of the square's center.
     * @param yaw The yaw (horizontal) rotation of the square, pivoted from the centre.
     * @param pitch The pitch (vertical) rotation of the square, pivoted from the centre.
     * @param roll The roll (tilt) rotation of the square, pivoted from the centre.
     * @param size The half side-length of the square - or radius of the circle.
     * @param speedMin The minimum speed of the particles. A negative value allows the particles to move backwards.
     * @param speedMax The maximum speed of the particles. A negative value allows the particles to move backwards.
     * @param count The number of particles that will spawn.
     * @param cropToCircle If true, will spawn as a circle instead of a square.
     */
    public static void spawn2DSquare(World world, DefaultParticleType particle, double x, double y, double z, double size, float yaw, float pitch, float roll, double speedMin, double speedMax, int count, boolean cropToCircle) {
        if (!world.isClient()) {
            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, new BlockPos((int) Math.round(x), (int) Math.round(y), (int) Math.round(z)))) {
                ServerPlayNetworking.send(player, new S2CSquareParticles(Registries.PARTICLE_TYPE.getId(particle),
                        x, y, z, size, yaw, pitch, roll, speedMin, speedMax, count, cropToCircle));
            }
        } else {
            for (int i = 0; i < count; i++) {
                // Position within 2d bounds
                double dx;
                double dy;
                if (cropToCircle) {
                    // Randomise position within the circle
                    float angle = (float) Math.toRadians(world.getRandom().nextBetween(0, 3600) / 10f);
                    float amplitude = world.getRandom().nextFloat();

                    dx = Math.cos(angle) * amplitude * size;
                    dy = Math.sin(angle) * amplitude * size;
                } else {
                    // Randomise position within the square
                    dx = (world.getRandom().nextBetween(-100, 100) / 100f) * size;
                    dy = (world.getRandom().nextBetween(-100, 100) / 100f) * size;
                }

                // Rotate position to 3d space
                double posX = dx;
                double posY = dy;
                double posZ = 0;

                double yawR = Math.toRadians(yaw);
                double pitchR = -Math.toRadians(pitch);
                double rollR = Math.toRadians(roll);

                double x0 = posX, y0 = posY; // Roll
                posX = (x0 * Math.cos(rollR)) - (y0 * Math.sin(rollR));
                posY = (x0 * Math.sin(rollR)) + (y0 * Math.cos(rollR));

                y0 = posY;
                double z0 = posZ; // Pitch
                posY = (y0 * Math.cos(pitchR)) - (z0 * Math.sin(pitchR));
                posZ = (y0 * Math.sin(pitchR)) + (z0 * Math.cos(pitchR));

                x0 = posX;
                z0 = posZ; // Yaw
                posX = (x0 * Math.cos(yawR)) + (z0 * Math.sin(yawR));
                posZ = (x0 * Math.sin(yawR)) - (z0 * Math.cos(yawR));

                // Create velocity
                double vx = -Math.sin(yawR) * Math.cos(pitchR);
                double vy = -Math.sin(pitchR);
                double vz = Math.cos(yawR) * Math.cos(pitchR);
                Vec3d velocity = new Vec3d(vx, vy, vz).multiply(-world.getRandom().nextBetween(
                    (int) Math.round(speedMin * 100f),
                    (int) Math.round(speedMax * 100f))
                / 100f);

                // Spawn
                world.addParticle(particle, x + posX, y + posY, z + posZ, velocity.getX(), velocity.getY(), velocity.getZ());
            }
        }
    }
}
