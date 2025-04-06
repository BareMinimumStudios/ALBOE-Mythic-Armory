package org.bareminimumstudios.mythicarmory.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.bareminimumstudios.mythicarmory.networking.C2S.C2SDashPacket;
import org.bareminimumstudios.mythicarmory.networking.C2S.C2SGlidePacket;

public class PacketHandler {
    public static void registerServerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(C2SGlidePacket.TYPE, C2SGlidePacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(C2SDashPacket.TYPE, C2SDashPacket::receive);
    }

    public static void registerClientReceivers() {
    }
}
