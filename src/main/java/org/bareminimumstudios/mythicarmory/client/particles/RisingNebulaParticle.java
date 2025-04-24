package org.bareminimumstudios.mythicarmory.client.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;

public class RisingNebulaParticle extends SpriteBillboardParticle {

    protected final Vec3d pivot;
    protected final double initialAngle;
    protected final double heightGain;
    protected final double spacing;

    public RisingNebulaParticle(ClientWorld clientWorld, double x, double y, double z, double angle, double speed, double spacing, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z);
        this.setSprite(spriteProvider.getSprite(clientWorld.getRandom()));
        this.initialAngle = angle;
        this.heightGain = speed;
        this.spacing = spacing;
        this.pivot = new Vec3d(x, y, z);

        this.blue = (random.nextFloat() / 2f) + 0.5f;
        this.green = 0;
        this.red = random.nextFloat() / 2f;
        this.maxAge = 80;
        this.alpha = 0;
        this.scale(2.5f);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void moveAroundPoint(double angle, double height, double spacing) {
        double radians = Math.toRadians(angle);
        Vec3d offset = new Vec3d(Math.cos(radians) * spacing, height, Math.sin(radians) * spacing);

        this.setPos(
                offset.getX() + pivot.getX(),
                offset.getY() + pivot.getY(),
                offset.getZ() + pivot.getZ());
    }

    @Override
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
            this.setAlpha(0.5f - (((float)this.age - (float)(this.maxAge * 0.5)) / ((float)this.maxAge)));
        } else {
            this.setAlpha(0.5f);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new RisingNebulaParticle(clientWorld, d, e, f, Math.toDegrees(Math.atan2(g, i)), h, Math.hypot(g, i), spriteProvider);
        }
    }
}
