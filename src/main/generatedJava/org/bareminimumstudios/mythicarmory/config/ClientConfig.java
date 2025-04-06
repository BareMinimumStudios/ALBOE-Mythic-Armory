package org.bareminimumstudios.mythicarmory.config;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ClientConfig extends ConfigWrapper<org.bareminimumstudios.mythicarmory.config.ClientConfigModel> {

    public final Keys keys = new Keys();

    private final Option<org.bareminimumstudios.mythicarmory.config.ClientConfigModel.DashControlTypes> dashControl = this.optionForKey(this.keys.dashControl);

    private ClientConfig() {
        super(org.bareminimumstudios.mythicarmory.config.ClientConfigModel.class);
    }

    private ClientConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(org.bareminimumstudios.mythicarmory.config.ClientConfigModel.class, janksonBuilder);
    }

    public static ClientConfig createAndLoad() {
        var wrapper = new ClientConfig();
        wrapper.load();
        return wrapper;
    }

    public static ClientConfig createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new ClientConfig(janksonBuilder);
        wrapper.load();
        return wrapper;
    }

    public org.bareminimumstudios.mythicarmory.config.ClientConfigModel.DashControlTypes dashControl() {
        return dashControl.value();
    }

    public void dashControl(org.bareminimumstudios.mythicarmory.config.ClientConfigModel.DashControlTypes value) {
        dashControl.set(value);
    }


    public static class Keys {
        public final Option.Key dashControl = new Option.Key("dashControl");
    }
}

