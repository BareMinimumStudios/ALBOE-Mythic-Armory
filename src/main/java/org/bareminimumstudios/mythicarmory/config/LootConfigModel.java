package org.bareminimumstudios.mythicarmory.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Sync(Option.SyncMode.NONE)
@Config(name = "alboe_mythicarmory/loot", wrapperName = "LootConfig")
public class LootConfigModel {
    @SectionHeader("enabledWeapon")
    @RestartRequired
    public boolean solarisEdgeEnabled = true;

    @SectionHeader("generic")
    @RestartRequired
    public float divinityShrapnelChestChance = 0.0033f;
    @RestartRequired
    public float divineWeaponsChestChance = 0.002f;

    @SectionHeader("bosses")
    @RestartRequired
    public float divineWeaponsBossChance = 0.01f;
    @RestartRequired
    public List<String> bosses = List.of(
            "minecraft:entities/ender_dragon",
            "minecraft:entities/wither",
            "bosses_of_mass_destruction:entities/void_blossom",
            "bosses_of_mass_destruction:entities/lich",
            "bosses_of_mass_destruction:chests/obsidilith",
            "bosses_of_mass_destruction:chests/nether_gauntlet"
    );
}
