package org.bareminimumstudios.mythicarmory.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.bareminimumstudios.mythicarmory.networking.C2S.C2SDashPacket;
import org.bareminimumstudios.mythicarmory.networking.C2S.C2SGlidePacket;
import org.bareminimumstudios.mythicarmory.networking.S2C.S2CBurstParticles;
import org.bareminimumstudios.mythicarmory.networking.S2C.S2CSquareParticles;

public class PacketHandler {
    public static void registerServerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(C2SGlidePacket.TYPE, C2SGlidePacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(C2SDashPacket.TYPE, C2SDashPacket::receive);
    }

    public static void registerClientReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(S2CSquareParticles.TYPE, S2CSquareParticles::receive);
        ClientPlayNetworking.registerGlobalReceiver(S2CBurstParticles.TYPE, S2CBurstParticles::receive);
    }
}
