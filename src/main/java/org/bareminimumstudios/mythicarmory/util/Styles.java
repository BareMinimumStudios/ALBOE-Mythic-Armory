package org.bareminimumstudios.mythicarmory.util;

import net.minecraft.text.Style;

public enum Styles {
    TOOLTIP(Style.EMPTY.withColor(8421504)),
    DIVINE_NAME(Style.EMPTY.withColor(HelperMethods.toDecimalColor(253, 220, 92))),
    DIVINE_EFFECT(Style.EMPTY.withColor(HelperMethods.toDecimalColor(255, 123, 0)).withUnderline(true).withBold(true));

    private final Style style;

    Styles(Style style) {
        this.style = style;
    }

    public Style get() {
        return this.style;
    }
}
