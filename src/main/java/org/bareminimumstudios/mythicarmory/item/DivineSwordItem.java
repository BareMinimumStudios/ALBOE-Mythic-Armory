package org.bareminimumstudios.mythicarmory.item;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import org.bareminimumstudios.mythicarmory.util.ToolMaterials;

public class DivineSwordItem extends SwordItem {
    public DivineSwordItem(int attackDamage, float attackSpeed, Settings settings) {
        super(ToolMaterials.DIVINE, attackDamage, attackSpeed, settings);
    }
}
