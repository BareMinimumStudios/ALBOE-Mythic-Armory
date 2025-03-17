package org.bareminimumstudios.mythicarmory.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;

public class ItemRegistry {

    // Item Group
    public static final RegistryKey<ItemGroup> KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), HelperMethods.identifierOf("main"));
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.DIAMOND_SWORD))
            .displayName(Text.translatable("itemGroup.rulers_of_ruin.name"))
            .build();

    // Divine Weapons
    public static final Item SOLARIS_EDGE = null;

    public static void registerItems() {
        Registry.register(Registries.ITEM_GROUP, KEY, ITEM_GROUP);
    }

    public static Item register(String id, Item item) {
        Registry.register(Registries.ITEM, HelperMethods.identifierOf(id), item);
        return item;
    }
}
