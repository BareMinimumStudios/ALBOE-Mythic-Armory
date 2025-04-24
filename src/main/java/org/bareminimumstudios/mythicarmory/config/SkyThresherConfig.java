package org.bareminimumstudios.mythicarmory.config;

import io.wispforest.owo.config.annotation.RestartRequired;

@SuppressWarnings("unused")
public class SkyThresherConfig {
    public static class Zephyr {
        public float jumpHeightIncrease = 0.5f;
        public float speedIncrease = 0.3f;
        @RestartRequired
        public int slowFallTime = 200;
        @RestartRequired
        public int dashCooldown = 40;
        public float dashStrength = 1.5f;
    }

    public static class Mistral {
        public float chance = 0.1f;
        public int nauseaDuration = 100;
        public float pullStrength = 0.1f;
        public int pullInterval = 10;
        public int vortexDuration = 120;
        public double pullRange = 6;
    }

    public static class NebulaStorm {
        public float explosionDamage = 8f;
        public float trappedDamage = 4f;
        public double speed = 0.3f;
        public int duration = 180;
    }
}
