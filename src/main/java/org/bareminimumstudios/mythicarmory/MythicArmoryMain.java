package org.bareminimumstudios.mythicarmory;

import net.fabricmc.api.ModInitializer;

import org.bareminimumstudios.mythicarmory.config.WeaponsConfig;
import org.bareminimumstudios.mythicarmory.registry.ItemRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MythicArmoryMain implements ModInitializer {
	public static final String MOD_ID = "alboe_mythicarmory";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	public static final WeaponsConfig WEAPONS_CONFIG = WeaponsConfig.createAndLoad();

	@Override
	public void onInitialize() {
		ItemRegistry.registerItems();

		LOGGER.info("ALBOE! Mythic Armory initialised successfully.");
	}
}