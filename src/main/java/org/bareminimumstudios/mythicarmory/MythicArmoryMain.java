package org.bareminimumstudios.mythicarmory;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.spell_engine.api.spell.SpellEvents;
import org.bareminimumstudios.mythicarmory.config.LootConfig;
import org.bareminimumstudios.mythicarmory.config.WeaponsConfig;
import org.bareminimumstudios.mythicarmory.networking.PacketHandler;
import org.bareminimumstudios.mythicarmory.registry.*;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;
import org.bareminimumstudios.mythicarmory.util.LootTableModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MythicArmoryMain implements ModInitializer {
	public static final String MOD_ID = "alboe_mythicarmory";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	public static final WeaponsConfig WEAPONS_CONFIG = WeaponsConfig.createAndLoad();
	public static final LootConfig LOOT_CONFIG = LootConfig.createAndLoad();

	@Override
	public void onInitialize() {
		ItemRegistry.register();
		EffectRegistry.register();
		LootTableModifier.register();
		PacketHandler.registerServerReceivers();
		ParticleRegistry.register();
		EntityRegistry.register();
		SoundRegistry.register();
		initializeEventListeners();

		LOGGER.info("ALBOE! Mythic Armory initialised successfully.");
	}

	public void initializeEventListeners() {
		// Solaris Edge - Empowered Meter Usage
		// This is hacky, but it's also the only way to do this in Spell Engine
		SpellEvents.PROJECTILE_SHOOT.register((projectileLaunchEvent) -> {
			if(projectileLaunchEvent.spellInfo().id().equals(HelperMethods.identifierOf("eclipse_invocation_empowered"))) {
				LivingEntity player = projectileLaunchEvent.caster();
				if(!player.hasStatusEffect(EffectRegistry.SOLAR_DRAIN)) {
					player.removeStatusEffect(EffectRegistry.SOLAR_CHARGE);
					player.addStatusEffect(
							new StatusEffectInstance(EffectRegistry.SOLAR_DRAIN, WEAPONS_CONFIG.solarOverload.cooldown())
					);
				}
			}
		});
	}
}