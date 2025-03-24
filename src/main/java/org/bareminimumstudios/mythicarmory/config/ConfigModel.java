package org.bareminimumstudios.mythicarmory.config;


import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;

@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@Modmenu(modId = MythicArmoryMain.MOD_ID)
@Config(name = "alboe_mythicarmory/weapons", wrapperName = "WeaponsConfig")
public class ConfigModel {
    @SectionHeader("Solaris Edge")
    @RestartRequired
    public boolean solarisEdgeEnabled = true;
    @RestartRequired
    public int solarisEdgeDamage = 25;
    @RestartRequired
    public float solarisEdgeSwingSpeed = 1.1f;
    @Nest
    public SolarisEdgeConfig.HorizonShift horizonShift = new SolarisEdgeConfig.HorizonShift();
    @Nest
    public SolarisEdgeConfig.SolarOverload solarOverload = new SolarisEdgeConfig.SolarOverload();
}
