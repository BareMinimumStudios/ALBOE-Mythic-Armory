package org.bareminimumstudios.mythicarmory.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;

public class SoundRegistry {

    public static void register() {
    }

    public static final SoundEvent MISTRAL = register("mistral");
    public static final SoundEvent MISTRAL_FADE = register("mistral_fade");
    public static final SoundEvent NEBULA_STORM = register("nebula_windup");
    public static final SoundEvent NEBULA_EXPLOSION = register("nebula_explosion");

    public static SoundEvent register(String id) {
        SoundEvent soundEvent = SoundEvent.of(HelperMethods.identifierOf(id));
        Registry.register(Registries.SOUND_EVENT, HelperMethods.identifierOf(id), soundEvent);
        return soundEvent;
    }
}
