package org.bareminimumstudios.mythicarmory.util;

import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;

import java.text.DecimalFormat;

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

    /**
     * Turns a time config option (in ticks) and turns it into a String value (of seconds).
     * @param ticks The time in ticks.
     * @return The time as a String in seconds.
     */
    public static String ticksToSeconds(float ticks) {
        float seconds = ticks / 20f;
        return new DecimalFormat("#.##").format(seconds);
    }

    /**
     * Turns a multiplier config option (in decimal) and turns it into a String value (of a percentage).
     * Does not include the % sign.
     * @param decimal The multiplier in decimal.
     * @return The multiplier as a String percentage.
     */
    public static String decimalToPercentage(float decimal) {
        float percentage = decimal * 100;
        return new DecimalFormat("#.##").format(percentage);
    }
}
