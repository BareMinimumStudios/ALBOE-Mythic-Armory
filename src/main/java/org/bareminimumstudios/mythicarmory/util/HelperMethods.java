package org.bareminimumstudios.mythicarmory.util;

import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;

public class HelperMethods {

    /**
     * Creates a new identifier within this mod's namespace.<br>
     * For example, <code>alboe_mythicarmory:example</code>
     *
     * @param path The path to the identifier
     * @return An identifier within this mod's namespace
     */
    public static Identifier identifierOf(String path) {
        return Identifier.of(MythicArmoryMain.MOD_ID, path);
    }

    /**
     * Takes a rgb input and translates it to a singular decimal number
     *
     * @param red The red value of the color (0 -> 255)
     * @param green The green value of the pixel (0 -> 255)
     * @param blue The blue value of the pixel (0 -> 255)
     * @return A decimal value denoting the color input
     */
    public static int toDecimalColor(int red, int green, int blue) {
        return (red << 16) | (green << 8) | blue;
    }
}
