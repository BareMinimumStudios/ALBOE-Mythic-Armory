package org.bareminimumstudios.mythicarmory.config;


import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;

@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@Config(name = "alboe_mythicarmory/weapons", wrapperName = "WeaponsConfig")
public class WeaponConfigModel {
    @SectionHeader("solarisEdge")
    @RestartRequired
    public int solarisEdgeDamage = 25;
    @RestartRequired
    public float solarisEdgeSwingSpeed = 1.1f;
    @Nest
    public SolarisEdgeConfig.HorizonShift horizonShift = new SolarisEdgeConfig.HorizonShift();
    @Nest
    public SolarisEdgeConfig.SolarOverload solarOverload = new SolarisEdgeConfig.SolarOverload();
}
