package org.bareminimumstudios.mythicarmory.networking.C2S;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.bareminimumstudios.mythicarmory.MythicArmoryMain;
import org.bareminimumstudios.mythicarmory.registry.EffectRegistry;
import org.bareminimumstudios.mythicarmory.registry.ItemRegistry;
import org.bareminimumstudios.mythicarmory.registry.ParticleRegistry;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;
import org.bareminimumstudios.mythicarmory.util.ParticleHelper;

public class C2SDashPacket implements FabricPacket {
    public static final PacketType<C2SDashPacket> TYPE =
            PacketType.create(HelperMethods.identifierOf("dash"), C2SDashPacket::new);

    public final float yaw;

    public C2SDashPacket(PacketByteBuf buf) {
        this(buf.readFloat());
    }

    public C2SDashPacket(float yaw) {
        this.yaw = yaw;
    }


    @SuppressWarnings("unused")
    public static void receive(C2SDashPacket packet, ServerPlayerEntity player, PacketSender sender) {
        if(HelperMethods.isHolding(player, ItemRegistry.SKY_THRESHER, true)
                && !player.hasStatusEffect(EffectRegistry.ZEPHYR_DEFICIT)
                && player.getWorld().isNight()
        ) {
            double radians = Math.toRadians(packet.yaw + 90);
            Vec3d movement = new Vec3d(Math.cos(radians), 0.1, Math.sin(radians));
            movement = movement.multiply(MythicArmoryMain.WEAPONS_CONFIG.zephyr.dashStrength());
            player.addVelocity(movement);
            player.velocityModified = true;

            player.addStatusEffect(new StatusEffectInstance(
                    EffectRegistry.ZEPHYR_DEFICIT,
                    MythicArmoryMain.WEAPONS_CONFIG.zephyr.dashCooldown()
            ));

            ParticleHelper.spawn2DSquare(player.getWorld(), ParticleRegistry.SHORT_NEBULA,
                    player.getX(), player.getEyeY() - 0.5, player.getZ(),
                    1, packet.yaw + 180, 0, 0,
                    0.6, 1, 150, true);
        }
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeFloat(this.yaw);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
