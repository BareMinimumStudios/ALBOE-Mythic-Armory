package org.bareminimumstudios.mythicarmory.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;
import org.bareminimumstudios.mythicarmory.util.ToolMaterials;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DivineSwordItem extends SwordItem {
    protected static final Style DIVINE_STYLE = Style.EMPTY.withColor(HelperMethods.toDecimalColor(253, 220, 92));

    public DivineSwordItem(int attackDamage, float attackSpeed, Settings settings) {
        super(ToolMaterials.DIVINE, attackDamage, attackSpeed, settings.fireproof().rarity(Rarity.EPIC));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey(stack))
                .setStyle(DIVINE_STYLE);
    }
}
