package net.bareminimumstudios.mythicarmory;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MythicArmoryMain implements ModInitializer {
	public static final String MOD_ID = "alboe_mythicarmory";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("ALBOE! Mythic Armory initialised successfully.");
	}
}