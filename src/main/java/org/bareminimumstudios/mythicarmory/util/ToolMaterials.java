package org.bareminimumstudios.mythicarmory.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.bareminimumstudios.mythicarmory.registry.ItemRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public enum ToolMaterials implements ToolMaterial {
    DIVINE(4, 3270, 15f, 5f, 30, HelperMethods.identifierOf("divinity_shrapnel"));


    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Identifier[] repairIngredient;

    ToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, @NotNull Identifier... repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    /**
     * Turns the list of identifiers into ingredients.<br>
     * It is done in this way due to how FabricMC modloading order causing crashes with dependency mods
     *
     * @return The ingredient list
     */
    @Override
    public Ingredient getRepairIngredient() {
        List<Item> items = new ArrayList<>();
        for (Identifier item : this.repairIngredient) {
            items.add(
                    Registries.ITEM.getOrEmpty(item)
                            .orElse(Items.AIR)
            );
        }

        return Ingredient.ofItems(items.toArray(ItemConvertible[]::new));
    }
}
