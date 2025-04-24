package org.bareminimumstudios.mythicarmory.client.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;

public class SphericalNebulaParticle extends RisingNebulaParticle {


    public SphericalNebulaParticle(ClientWorld clientWorld, double x, double y, double z, double angle, double speed, double spacing, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, angle, speed, spacing, spriteProvider);
        this.maxAge = 40;
    }

    public void tick() {
        super.tick();

        // Pivot around point
        double horizontalAngleOffset = this.age % 20;
        horizontalAngleOffset *= (360/20f);

        double heightOffset = this.age / 20f;
        heightOffset *= heightGain;

        this.moveAroundPoint(initialAngle + horizontalAngleOffset, heightOffset, spacing);

        // Fade
        if (this.age > this.maxAge * 0.5) {
            this.setAlpha(0.75f - (((float)this.age - (float)(this.maxAge * 0.5)) / ((float)this.maxAge)));
        } else {
            this.setAlpha(0.75f);
        }

//        this.world.addParticle(ParticleTypes.ENCHANTED_HIT, this.x, this.y, this.z, 0, 0, 0);
    }

    @Override
    public void moveAroundPoint(double yaw, double height, double spacing) {
        double normalisedHeight = height / 4;
        double yawR = Math.toRadians(yaw);
        double pitchR = normalisedHeight * Math.PI;

        Vec3d offset = new Vec3d(
                Math.cos(pitchR) * Math.sin(yawR),
                Math.sin(pitchR),
                Math.cos(pitchR) * Math.cos(yawR));

        offset = offset.multiply(spacing);

        this.setPos(
                offset.getX() + pivot.getX(),
                offset.getY() + pivot.getY(),
                offset.getZ() + pivot.getZ());
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new SphericalNebulaParticle(clientWorld, d, e, f, Math.toDegrees(Math.atan2(g, i)), h >= 0 ? h == 0 ? 0 : 1 : -1, Math.hypot(g, i), spriteProvider);
        }
    }
}
