package org.bareminimumstudios.mythicarmory.client.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SimpleNebulaParticle extends SpriteBillboardParticle {


    public SimpleNebulaParticle(ClientWorld clientWorld, double x, double y, double z, double vX, double vY, double vZ, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z);
        this.setSprite(spriteProvider.getSprite(clientWorld.getRandom()));
        this.velocityX = vX;
        this.velocityY = vY;
        this.velocityZ = vZ;

        this.blue = (random.nextFloat() / 2f) + 0.5f;
        this.green = 0;
        this.red = random.nextFloat() / 2f;
        this.maxAge = 80;
        this.alpha = 0.5f;
        this.scale(2.5f);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();

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
            return new SimpleNebulaParticle(clientWorld, d, e, f, g, h, i, spriteProvider);
        }
    }
}
