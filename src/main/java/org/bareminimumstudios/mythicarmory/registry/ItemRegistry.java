package org.bareminimumstudios.mythicarmory.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import org.bareminimumstudios.mythicarmory.item.DivinityShrapnelItem;
import org.bareminimumstudios.mythicarmory.item.SolarisEdgeItem;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;

public class ItemRegistry {

    // Crafting Materials
    public static final Item DIVINITY_SHRAPNEL = register("divinity_shrapnel", new DivinityShrapnelItem(
            new Item.Settings()
    ));

    // Divine Weapons
    public static final Item SOLARIS_EDGE = register("solaris_edge", new SolarisEdgeItem(
            100,
            -2f,
            new Item.Settings()
                    .rarity(Rarity.EPIC)
                    .fireproof()
    ));

    // Item Group
    public static final RegistryKey<ItemGroup> KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), HelperMethods.identifierOf("main"));
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(SOLARIS_EDGE))
            .displayName(Text.translatable("itemGroup.alboe_mythicarmory.name"))
            .build();

    public static void registerItems() {
        Registry.register(Registries.ITEM_GROUP, KEY, ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(KEY).register(
                itemGroup -> {
                    itemGroup.add(DIVINITY_SHRAPNEL);
                    itemGroup.add(SOLARIS_EDGE);
                }
        );
    }

    public static Item register(String id, Item item) {
        Registry.register(Registries.ITEM, HelperMethods.identifierOf(id), item);
        return item;
    }
}
