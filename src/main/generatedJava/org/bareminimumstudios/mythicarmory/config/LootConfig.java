package org.bareminimumstudios.mythicarmory.config;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class LootConfig extends ConfigWrapper<org.bareminimumstudios.mythicarmory.config.LootConfigModel> {

    public final Keys keys = new Keys();

    private final Option<java.lang.Boolean> solarisEdgeEnabled = this.optionForKey(this.keys.solarisEdgeEnabled);
    private final Option<java.lang.Float> divinityShrapnelChestChance = this.optionForKey(this.keys.divinityShrapnelChestChance);
    private final Option<java.lang.Float> divineWeaponsChestChance = this.optionForKey(this.keys.divineWeaponsChestChance);
    private final Option<java.lang.Float> divineWeaponsBossChance = this.optionForKey(this.keys.divineWeaponsBossChance);
    private final Option<java.util.List<java.lang.String>> bosses = this.optionForKey(this.keys.bosses);

    private LootConfig() {
        super(org.bareminimumstudios.mythicarmory.config.LootConfigModel.class);
    }

    private LootConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(org.bareminimumstudios.mythicarmory.config.LootConfigModel.class, janksonBuilder);
    }

    public static LootConfig createAndLoad() {
        var wrapper = new LootConfig();
        wrapper.load();
        return wrapper;
    }

    public static LootConfig createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new LootConfig(janksonBuilder);
        wrapper.load();
        return wrapper;
    }

    public boolean solarisEdgeEnabled() {
        return solarisEdgeEnabled.value();
    }

    public void solarisEdgeEnabled(boolean value) {
        solarisEdgeEnabled.set(value);
    }

    public float divinityShrapnelChestChance() {
        return divinityShrapnelChestChance.value();
    }

    public void divinityShrapnelChestChance(float value) {
        divinityShrapnelChestChance.set(value);
    }

    public float divineWeaponsChestChance() {
        return divineWeaponsChestChance.value();
    }

    public void divineWeaponsChestChance(float value) {
        divineWeaponsChestChance.set(value);
    }

    public float divineWeaponsBossChance() {
        return divineWeaponsBossChance.value();
    }

    public void divineWeaponsBossChance(float value) {
        divineWeaponsBossChance.set(value);
    }

    public java.util.List<java.lang.String> bosses() {
        return bosses.value();
    }

    public void bosses(java.util.List<java.lang.String> value) {
        bosses.set(value);
    }


    public static class Keys {
        public final Option.Key solarisEdgeEnabled = new Option.Key("solarisEdgeEnabled");
        public final Option.Key divinityShrapnelChestChance = new Option.Key("divinityShrapnelChestChance");
        public final Option.Key divineWeaponsChestChance = new Option.Key("divineWeaponsChestChance");
        public final Option.Key divineWeaponsBossChance = new Option.Key("divineWeaponsBossChance");
        public final Option.Key bosses = new Option.Key("bosses");
    }
}

