package org.bareminimumstudios.mythicarmory.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.SectionHeader;
import io.wispforest.owo.config.annotation.Sync;

@SuppressWarnings("unused")
@Sync(Option.SyncMode.NONE)
@Config(name = "alboe_mythicarmory/client", wrapperName = "ClientConfig")
public class ClientConfigModel {
    @SectionHeader("abilities")
    public DashControlTypes dashControl = DashControlTypes.CAMERA;

    public enum DashControlTypes {
        MOVEMENT, CAMERA
    }
}
