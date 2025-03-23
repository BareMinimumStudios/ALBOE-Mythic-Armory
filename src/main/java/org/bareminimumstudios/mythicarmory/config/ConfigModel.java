package org.bareminimumstudios.mythicarmory.config;


import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.Nest;
import io.wispforest.owo.config.annotation.SectionHeader;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;

@Modmenu(modId = MythicArmoryMain.MOD_ID)
@Config(name = "alboe_mythicarmory/weapons", wrapperName = "WeaponsConfig")
public class ConfigModel {
    @SectionHeader("Solaris Edge")
    public boolean solarisEdgeEnabled = true;
    @Nest
    public SolarisEdgeConfig.HorizonShift horizonShift = new SolarisEdgeConfig.HorizonShift();
    @Nest
    public SolarisEdgeConfig.SolarOverload solarOverload = new SolarisEdgeConfig.SolarOverload();
}
