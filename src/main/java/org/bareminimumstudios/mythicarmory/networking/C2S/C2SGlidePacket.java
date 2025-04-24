package org.bareminimumstudios.mythicarmory.networking.C2S;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.bareminimumstudios.mythicarmory.registry.EffectRegistry;
import org.bareminimumstudios.mythicarmory.registry.ItemRegistry;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;

public class C2SGlidePacket implements FabricPacket {
    public static final PacketType<C2SGlidePacket> TYPE =
            PacketType.create(HelperMethods.identifierOf("glide"), C2SGlidePacket::new);

    public C2SGlidePacket(PacketByteBuf buf) {
    }

    public C2SGlidePacket() {
    }

    @SuppressWarnings("unused")
    public static void receive(C2SGlidePacket packet, ServerPlayerEntity player, PacketSender sender) {
        double fallSpeed = player.getVelocity().getY();
        if(fallSpeed < 0
                && !player.isFallFlying()
                && !player.isSpectator()
                && HelperMethods.isHolding(player, ItemRegistry.SKY_THRESHER, true)
                && !player.isSwimming()
                && player.hasStatusEffect(EffectRegistry.ZEPHYR_ENERGY)
        ) {
            player.addStatusEffect(new StatusEffectInstance(
                    EffectRegistry.GLIDE,
                    2,
                    0,
                    true,
                    false,
                    false
            ));
        }
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
