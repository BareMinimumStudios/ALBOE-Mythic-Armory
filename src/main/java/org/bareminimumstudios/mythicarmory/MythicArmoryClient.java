package org.bareminimumstudios.mythicarmory;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.predicate.item.ItemPredicate;
import net.spell_engine.api.effect.CustomModelStatusEffect;
import net.spell_engine.api.render.CustomModels;
import org.bareminimumstudios.mythicarmory.client.renderers.LunarShieldRenderer;
import org.bareminimumstudios.mythicarmory.item.SolarisEdgeItem;
import org.bareminimumstudios.mythicarmory.registry.EffectRegistry;
import org.bareminimumstudios.mythicarmory.registry.ItemRegistry;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Environment(EnvType.CLIENT)
public class MythicArmoryClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		registerModelPredicates();
		registerSpellEngineRenderers();
	}

	public static void registerSpellEngineRenderers() {
		CustomModels.registerModelIds(List.of(
				LunarShieldRenderer.modelId_base
		));

		CustomModelStatusEffect.register(EffectRegistry.LUNAR_SHIELD, new LunarShieldRenderer());
	}

	public static void registerModelPredicates() {
		// Solaris Edge
		ModelPredicateProviderRegistry.register(ItemRegistry.SOLARIS_EDGE, HelperMethods.identifierOf("form"), (itemStack, clientWorld, livingEntity, seed) -> {
			SolarisEdgeItem.Form form = SolarisEdgeItem.getForm(itemStack);

			if(form == null) {
				return 0.5f; // Day
			}

            return switch (form) {
				case NIGHT -> 0f;
				case DAY -> 0.5f;
                case EMPOWERED -> 1f;
            };
        });
	}
}