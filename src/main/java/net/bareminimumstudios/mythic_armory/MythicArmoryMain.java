package net.bareminimumstudios.mythic_armory;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MythicArmoryMain implements ModInitializer {
	public static final String MOD_ID = "alboe_mythic_armory";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("ALBOE! Mythic Armory initialised successfully.");
	}
}