package org.bareminimumstudios.mythicarmory.util;

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
}
