package org.bareminimumstudios.mythicarmory.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.math.Vec3d;
import org.bareminimumstudios.mythicarmory.MythicArmoryClient;
import org.bareminimumstudios.mythicarmory.networking.C2S.C2SDashPacket;
import org.bareminimumstudios.mythicarmory.networking.C2S.C2SGlidePacket;
import org.bareminimumstudios.mythicarmory.registry.ItemRegistry;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;
import org.lwjgl.glfw.GLFW;

import java.util.List;

@Environment(EnvType.CLIENT)
public class InputHandler {

    public static final KeyBinding dashKey = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.mythicarmory.dash",
                    GLFW.GLFW_KEY_LEFT_CONTROL,
                    "key.mythicarmory.category"));

    public static final List<KeyBinding> overrideKeybindings = List.of(dashKey);

    public static void register() {
        // Sky Thresher - Zephyr
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;
            if(player == null || !HelperMethods.isHolding(player, ItemRegistry.SKY_THRESHER, true)) return;

            // Glide
            if(client.options.jumpKey.isPressed()) {
                ClientPlayNetworking.send(new C2SGlidePacket());
            }

            // Dash
            if(dashKey.isPressed()) {
                switch (MythicArmoryClient.CLIENT_CONFIG.dashControl()) {
                    case MOVEMENT -> {
                        Vec3d velocity = player.getVelocity();
                        if(velocity.getX() != 0 || velocity.getZ() != 0) {
                            double angle = Math.atan2(-velocity.getX(), velocity.getZ());
                            ClientPlayNetworking.send(new C2SDashPacket((float) Math.toDegrees(angle)));
                        } else {
                            ClientPlayNetworking.send(new C2SDashPacket(player.getYaw()));
                        }
                    }
                    case CAMERA -> ClientPlayNetworking.send(new C2SDashPacket(player.getYaw()));
                }
            }
        });
    }
}
