package org.bareminimumstudios.mythicarmory.config;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WeaponsConfig extends ConfigWrapper<org.bareminimumstudios.mythicarmory.config.WeaponConfigModel> {

    public final Keys keys = new Keys();

    private final Option<java.lang.Integer> solarisEdgeDamage = this.optionForKey(this.keys.solarisEdgeDamage);
    private final Option<java.lang.Float> solarisEdgeSwingSpeed = this.optionForKey(this.keys.solarisEdgeSwingSpeed);
    private final Option<java.lang.Float> horizonShift_dayExtraDamage = this.optionForKey(this.keys.horizonShift_dayExtraDamage);
    private final Option<java.lang.Float> horizonShift_nightDealtDamageReduction = this.optionForKey(this.keys.horizonShift_nightDealtDamageReduction);
    private final Option<java.lang.Float> horizonShift_nightTakenDamageReduction = this.optionForKey(this.keys.horizonShift_nightTakenDamageReduction);
    private final Option<java.lang.Integer> horizonShift_regenInterval = this.optionForKey(this.keys.horizonShift_regenInterval);
    private final Option<java.lang.Float> horizonShift_dayRegenAmount = this.optionForKey(this.keys.horizonShift_dayRegenAmount);
    private final Option<java.lang.Float> horizonShift_nightRegenAmount = this.optionForKey(this.keys.horizonShift_nightRegenAmount);
    private final Option<java.lang.Integer> solarOverload_ticksToCharge = this.optionForKey(this.keys.solarOverload_ticksToCharge);
    private final Option<java.lang.Integer> solarOverload_ticksCanStore = this.optionForKey(this.keys.solarOverload_ticksCanStore);
    private final Option<java.lang.Integer> solarOverload_cooldown = this.optionForKey(this.keys.solarOverload_cooldown);
    private final Option<java.lang.Integer> skyThresherDamage = this.optionForKey(this.keys.skyThresherDamage);
    private final Option<java.lang.Float> skyThresherSwingSpeed = this.optionForKey(this.keys.skyThresherSwingSpeed);
    private final Option<java.lang.Float> zephyr_jumpHeightIncrease = this.optionForKey(this.keys.zephyr_jumpHeightIncrease);
    private final Option<java.lang.Float> zephyr_speedIncrease = this.optionForKey(this.keys.zephyr_speedIncrease);
    private final Option<java.lang.Integer> zephyr_slowFallTime = this.optionForKey(this.keys.zephyr_slowFallTime);
    private final Option<java.lang.Integer> zephyr_dashCooldown = this.optionForKey(this.keys.zephyr_dashCooldown);
    private final Option<java.lang.Float> zephyr_dashStrength = this.optionForKey(this.keys.zephyr_dashStrength);
    private final Option<java.lang.Float> mistral_chance = this.optionForKey(this.keys.mistral_chance);
    private final Option<java.lang.Integer> mistral_nauseaDuration = this.optionForKey(this.keys.mistral_nauseaDuration);
    private final Option<java.lang.Float> mistral_pullStrength = this.optionForKey(this.keys.mistral_pullStrength);

    private WeaponsConfig() {
        super(org.bareminimumstudios.mythicarmory.config.WeaponConfigModel.class);
    }

    private WeaponsConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(org.bareminimumstudios.mythicarmory.config.WeaponConfigModel.class, janksonBuilder);
    }

    public static WeaponsConfig createAndLoad() {
        var wrapper = new WeaponsConfig();
        wrapper.load();
        return wrapper;
    }

    public static WeaponsConfig createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new WeaponsConfig(janksonBuilder);
        wrapper.load();
        return wrapper;
    }

    public int solarisEdgeDamage() {
        return solarisEdgeDamage.value();
    }

    public void solarisEdgeDamage(int value) {
        solarisEdgeDamage.set(value);
    }

    public float solarisEdgeSwingSpeed() {
        return solarisEdgeSwingSpeed.value();
    }

    public void solarisEdgeSwingSpeed(float value) {
        solarisEdgeSwingSpeed.set(value);
    }

    public final HorizonShift_ horizonShift = new HorizonShift_();
    public class HorizonShift_ implements HorizonShift {
        public float dayExtraDamage() {
            return horizonShift_dayExtraDamage.value();
        }

        public void dayExtraDamage(float value) {
            horizonShift_dayExtraDamage.set(value);
        }

        public float nightDealtDamageReduction() {
            return horizonShift_nightDealtDamageReduction.value();
        }

        public void nightDealtDamageReduction(float value) {
            horizonShift_nightDealtDamageReduction.set(value);
        }

        public float nightTakenDamageReduction() {
            return horizonShift_nightTakenDamageReduction.value();
        }

        public void nightTakenDamageReduction(float value) {
            horizonShift_nightTakenDamageReduction.set(value);
        }

        public int regenInterval() {
            return horizonShift_regenInterval.value();
        }

        public void regenInterval(int value) {
            horizonShift_regenInterval.set(value);
        }

        public float dayRegenAmount() {
            return horizonShift_dayRegenAmount.value();
        }

        public void dayRegenAmount(float value) {
            horizonShift_dayRegenAmount.set(value);
        }

        public float nightRegenAmount() {
            return horizonShift_nightRegenAmount.value();
        }

        public void nightRegenAmount(float value) {
            horizonShift_nightRegenAmount.set(value);
        }

    }
    public final SolarOverload_ solarOverload = new SolarOverload_();
    public class SolarOverload_ implements SolarOverload {
        public int ticksToCharge() {
            return solarOverload_ticksToCharge.value();
        }

        public void ticksToCharge(int value) {
            solarOverload_ticksToCharge.set(value);
        }

        public int ticksCanStore() {
            return solarOverload_ticksCanStore.value();
        }

        public void ticksCanStore(int value) {
            solarOverload_ticksCanStore.set(value);
        }

        public int cooldown() {
            return solarOverload_cooldown.value();
        }

        public void cooldown(int value) {
            solarOverload_cooldown.set(value);
        }

    }
    public int skyThresherDamage() {
        return skyThresherDamage.value();
    }

    public void skyThresherDamage(int value) {
        skyThresherDamage.set(value);
    }

    public float skyThresherSwingSpeed() {
        return skyThresherSwingSpeed.value();
    }

    public void skyThresherSwingSpeed(float value) {
        skyThresherSwingSpeed.set(value);
    }

    public final Zephyr_ zephyr = new Zephyr_();
    public class Zephyr_ implements Zephyr {
        public float jumpHeightIncrease() {
            return zephyr_jumpHeightIncrease.value();
        }

        public void jumpHeightIncrease(float value) {
            zephyr_jumpHeightIncrease.set(value);
        }

        public float speedIncrease() {
            return zephyr_speedIncrease.value();
        }

        public void speedIncrease(float value) {
            zephyr_speedIncrease.set(value);
        }

        public int slowFallTime() {
            return zephyr_slowFallTime.value();
        }

        public void slowFallTime(int value) {
            zephyr_slowFallTime.set(value);
        }

        public int dashCooldown() {
            return zephyr_dashCooldown.value();
        }

        public void dashCooldown(int value) {
            zephyr_dashCooldown.set(value);
        }

        public float dashStrength() {
            return zephyr_dashStrength.value();
        }

        public void dashStrength(float value) {
            zephyr_dashStrength.set(value);
        }

    }
    public final Mistral_ mistral = new Mistral_();
    public class Mistral_ implements Mistral {
        public float chance() {
            return mistral_chance.value();
        }

        public void chance(float value) {
            mistral_chance.set(value);
        }

        public int nauseaDuration() {
            return mistral_nauseaDuration.value();
        }

        public void nauseaDuration(int value) {
            mistral_nauseaDuration.set(value);
        }

        public float pullStrength() {
            return mistral_pullStrength.value();
        }

        public void pullStrength(float value) {
            mistral_pullStrength.set(value);
        }

    }
    public interface HorizonShift {
        float dayExtraDamage();
        void dayExtraDamage(float value);
        float nightDealtDamageReduction();
        void nightDealtDamageReduction(float value);
        float nightTakenDamageReduction();
        void nightTakenDamageReduction(float value);
        int regenInterval();
        void regenInterval(int value);
        float dayRegenAmount();
        void dayRegenAmount(float value);
        float nightRegenAmount();
        void nightRegenAmount(float value);
    }
    public interface SolarOverload {
        int ticksToCharge();
        void ticksToCharge(int value);
        int ticksCanStore();
        void ticksCanStore(int value);
        int cooldown();
        void cooldown(int value);
    }
    public interface Zephyr {
        float jumpHeightIncrease();
        void jumpHeightIncrease(float value);
        float speedIncrease();
        void speedIncrease(float value);
        int slowFallTime();
        void slowFallTime(int value);
        int dashCooldown();
        void dashCooldown(int value);
        float dashStrength();
        void dashStrength(float value);
    }
    public interface Mistral {
        float chance();
        void chance(float value);
        int nauseaDuration();
        void nauseaDuration(int value);
        float pullStrength();
        void pullStrength(float value);
    }
    public static class Keys {
        public final Option.Key solarisEdgeDamage = new Option.Key("solarisEdgeDamage");
        public final Option.Key solarisEdgeSwingSpeed = new Option.Key("solarisEdgeSwingSpeed");
        public final Option.Key horizonShift_dayExtraDamage = new Option.Key("horizonShift.dayExtraDamage");
        public final Option.Key horizonShift_nightDealtDamageReduction = new Option.Key("horizonShift.nightDealtDamageReduction");
        public final Option.Key horizonShift_nightTakenDamageReduction = new Option.Key("horizonShift.nightTakenDamageReduction");
        public final Option.Key horizonShift_regenInterval = new Option.Key("horizonShift.regenInterval");
        public final Option.Key horizonShift_dayRegenAmount = new Option.Key("horizonShift.dayRegenAmount");
        public final Option.Key horizonShift_nightRegenAmount = new Option.Key("horizonShift.nightRegenAmount");
        public final Option.Key solarOverload_ticksToCharge = new Option.Key("solarOverload.ticksToCharge");
        public final Option.Key solarOverload_ticksCanStore = new Option.Key("solarOverload.ticksCanStore");
        public final Option.Key solarOverload_cooldown = new Option.Key("solarOverload.cooldown");
        public final Option.Key skyThresherDamage = new Option.Key("skyThresherDamage");
        public final Option.Key skyThresherSwingSpeed = new Option.Key("skyThresherSwingSpeed");
        public final Option.Key zephyr_jumpHeightIncrease = new Option.Key("zephyr.jumpHeightIncrease");
        public final Option.Key zephyr_speedIncrease = new Option.Key("zephyr.speedIncrease");
        public final Option.Key zephyr_slowFallTime = new Option.Key("zephyr.slowFallTime");
        public final Option.Key zephyr_dashCooldown = new Option.Key("zephyr.dashCooldown");
        public final Option.Key zephyr_dashStrength = new Option.Key("zephyr.dashStrength");
        public final Option.Key mistral_chance = new Option.Key("mistral.chance");
        public final Option.Key mistral_nauseaDuration = new Option.Key("mistral.nauseaDuration");
        public final Option.Key mistral_pullStrength = new Option.Key("mistral.pullStrength");
    }
}

