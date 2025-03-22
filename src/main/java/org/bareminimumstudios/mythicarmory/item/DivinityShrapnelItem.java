package org.bareminimumstudios.mythicarmory.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.bareminimumstudios.mythicarmory.util.Styles;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DivinityShrapnelItem extends Item {
    public DivinityShrapnelItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey(stack))
                .setStyle(Styles.DIVINE.get());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.alboe_mythicarmory.divinity_shrapnel.tooltip1").setStyle(Styles.TOOLTIP.get()));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.divinity_shrapnel.tooltip2").setStyle(Styles.TOOLTIP.get()));
        tooltip.add(Text.literal(""));

        tooltip.add(Text.translatable("item.alboe_mythicarmory.divinity_shrapnel.tooltip3")
                .setStyle(Styles.TOOLTIP.get().withItalic(true)));
        tooltip.add(Text.translatable("item.alboe_mythicarmory.divinity_shrapnel.tooltip4")
                .setStyle(Styles.TOOLTIP.get().withItalic(true)));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
