package org.bareminimumstudios.mythicarmory.util;

import net.minecraft.text.Style;

public enum Styles {
    TOOLTIP(Style.EMPTY.withColor(8421504)),
    DIVINE(Style.EMPTY.withColor(HelperMethods.toDecimalColor(253, 220, 92)));

    private final Style style;

    Styles(Style style) {
        this.style = style;
    }

    public Style get() {
        return this.style;
    }
}
