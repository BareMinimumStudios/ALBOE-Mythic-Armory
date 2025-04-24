package org.bareminimumstudios.mythicarmory.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;
import org.jetbrains.annotations.NotNull;

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

    /**
     * Checks whether an entity is holding an item in one of their hands. If an item is two-handed, only checks if it is held in the Main Hand
     * @param entity The entity being tested.
     * @param item The item to check whether the entity is holding.
     * @param isTwoHanded Whether the item is one-handed (and CAN be held in the offhand) or is two-handed and cannot.
     * @return True if the entity is holding the item.
     */
    public static boolean isHolding(LivingEntity entity, Item item, boolean isTwoHanded) {
        return isTwoHanded ? entity.getMainHandStack().getItem() == item : entity.isHolding(item);
    }

    /**
     * Similar to the other <code>isHolding</code> method, except this one checks for an exact itemStack instead.
     * @param entity The entity being tested.
     * @param itemStack The exact itemStack to check whether the entity is holding.
     * @param isTwoHanded Whether the item is one-handed (and CAN be held in the offhand) or is two-handed and cannot.
     * @return True if the entity is holding the exact itemStack.
     */
    public static boolean isHolding(LivingEntity entity, ItemStack itemStack, boolean isTwoHanded) {
        if(entity.getMainHandStack().equals(itemStack)) return true;
        return !isTwoHanded && entity.getOffHandStack().equals(itemStack);
    }

    /**
     * Interpolates a value along a sine wave, oscillating between <code>middle - bound</code> and <code>middle + bound</code>.
     * @param progress A decimal value that determines the position along the sine wave. This value is normalized to the range [0,1).
     *                 <ul>
     *                   <li>At 0.00, the return value equals <code>middle</code></li>
     *                   <li>At 0.25, the return value equals <code>middle + bound</code></li>
     *                   <li>At 0.50, the return value equals <code>middle</code></li>
     *                   <li>At 0.75, the return value equals <code>middle - bound</code></li>
     *                   <li>At 1.00, the return value equals <code>middle</code></li>
     *                 </ul>
     * @param middle The midpoint of the sine wave.
     * @param bound The amplitude of the sine wave.
     * @return The interpolated value.
     */
    public static double sinInterpol(float progress, double middle, double bound) {
        double normalisedValue = Math.sin(progress * 2 * Math.PI);

        return middle + (normalisedValue * bound);
    }

    /**
     * Creates a multiplier from the player's spell scaling if the Spell Power Attributes mod is present (which it should almost always be).
     *
     * <p>If multiple spell schools are provided, their values are added together. Specifying the same school multiple times will apply its modifier multiple times.</p>
     *
     * @param player The player whose spell scaling attributes are being used to scale.
     * @param coefficient  A multiplier applied to each school's spell power before being added to the total.
     * @param schools The schools to scale from.
     * @return A double value acting as a multiplier, scaled using the player's spell attributes.
     */
    public static float getScale(PlayerEntity player, float coefficient, @NotNull SpellSchool... schools) {
        if(player == null) {
            return 1f;
        }

        if (FabricLoader.getInstance().isModLoaded("spell_power")) {
            double power = 1f;

            for(SpellSchool school : schools) {
                power += coefficient * SpellPower.getSpellPower(school, player).randomValue();
            }

            return (float) power;
        }

        return 1f;
    }
}
