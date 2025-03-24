package org.bareminimumstudios.mythicarmory.config;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WeaponsConfig extends ConfigWrapper<org.bareminimumstudios.mythicarmory.config.ConfigModel> {

    public final Keys keys = new Keys();

    private final Option<java.lang.Boolean> solarisEdgeEnabled = this.optionForKey(this.keys.solarisEdgeEnabled);
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

    private WeaponsConfig() {
        super(org.bareminimumstudios.mythicarmory.config.ConfigModel.class);
    }

    private WeaponsConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(org.bareminimumstudios.mythicarmory.config.ConfigModel.class, janksonBuilder);
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

    public boolean solarisEdgeEnabled() {
        return solarisEdgeEnabled.value();
    }

    public void solarisEdgeEnabled(boolean value) {
        solarisEdgeEnabled.set(value);
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
    public static class Keys {
        public final Option.Key solarisEdgeEnabled = new Option.Key("solarisEdgeEnabled");
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
    }
}

