package org.bareminimumstudios.mythicarmory.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;
import org.bareminimumstudios.mythicarmory.registry.ItemRegistry;

import java.util.List;

public class LootTableModifier {
    private static final List<String> bossList = MythicArmoryMain.LOOT_CONFIG.bosses();


    public static void register() {
        // Divinity Shrapnel
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.toString().contains("chests")) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(ItemRegistry.DIVINITY_SHRAPNEL)
                                .conditionally(RandomChanceLootCondition.builder(MythicArmoryMain.LOOT_CONFIG.divinityShrapnelChestChance())));

                tableBuilder.pool(pool);
            }
        });

        // Divine Weapons - chests
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.toString().contains("chests") && !bossList.contains(id.toString())) {
                LootPool.Builder pool = LootPool.builder();
                poolDivineItems(pool);

                pool.conditionally(RandomChanceLootCondition.builder(MythicArmoryMain.LOOT_CONFIG.divineWeaponsChestChance()));

                tableBuilder.pool(pool);
            }
        });

        // Divine Weapons - override
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (bossList.contains(id.toString())) {
                LootPool.Builder pool = LootPool.builder();
                poolDivineItems(pool);

                pool.conditionally(RandomChanceLootCondition.builder(MythicArmoryMain.LOOT_CONFIG.divineWeaponsBossChance()));

                tableBuilder.pool(pool);
            }
        });
    }

    public static void poolDivineItems(LootPool.Builder pool) {
        if(MythicArmoryMain.LOOT_CONFIG.solarisEdgeEnabled()) {
            pool.with(ItemEntry.builder(ItemRegistry.SOLARIS_EDGE));
        }

        if(MythicArmoryMain.LOOT_CONFIG.skyThresherEnabled()) {
            pool.with(ItemEntry.builder(ItemRegistry.SKY_THRESHER));
        }
    }
}
