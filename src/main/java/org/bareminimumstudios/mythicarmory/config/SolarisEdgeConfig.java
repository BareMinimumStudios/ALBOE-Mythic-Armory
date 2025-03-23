package org.bareminimumstudios.mythicarmory.config;

public class SolarisEdgeConfig {
    public static class HorizonShift {
        public float dayExtraDamage = 0.25f;
        public float nightDealtDamageReduction = 0.1f;
        public float nightTakenDamageReduction = 0.2f;
        public int regenInterval = 100;
        public float dayRegenAmount = 3f;
        public float nightRegenAmount = 5f;
    }

    public static class SolarOverload {
        public int ticksToCharge = 300;
        public int ticksCanStore = 400;
    }
}
