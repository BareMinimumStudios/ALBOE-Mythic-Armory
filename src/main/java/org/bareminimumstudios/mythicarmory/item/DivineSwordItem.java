package org.bareminimumstudios.mythicarmory.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;
import org.bareminimumstudios.mythicarmory.util.ToolMaterials;

public class DivineSwordItem extends SwordItem {
    protected static final Style DIVINE_STYLE = Style.EMPTY.withColor(HelperMethods.toDecimalColor(253, 220, 92));

    public DivineSwordItem(int attackDamage, float attackSpeed, Settings settings) {
        super(ToolMaterials.DIVINE, attackDamage, attackSpeed, settings.fireproof());
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey(stack))
                .setStyle(DIVINE_STYLE);
    }
}
