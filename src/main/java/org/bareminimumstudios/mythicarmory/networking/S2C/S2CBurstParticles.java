package org.bareminimumstudios.mythicarmory.networking.S2C;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;
import org.bareminimumstudios.mythicarmory.util.ParticleHelper;

public class S2CBurstParticles implements FabricPacket {
    public static final PacketType<S2CBurstParticles> TYPE =
            PacketType.create(HelperMethods.identifierOf("burst_particles"), S2CBurstParticles::new);

    public final Identifier particle;
    public final double x;
    public final double y;
    public final double z;
    public final float angle;
    public final float arc;
    public final double speed;
    public final float updrift;
    public final float spacing;
    public final double dy;

    public S2CBurstParticles(PacketByteBuf buf) {
        this(buf.readIdentifier(),
            buf.readDouble(),  buf.readDouble(), buf.readDouble(),
            buf.readFloat(), buf.readFloat(), buf.readDouble(),
            buf.readFloat(), buf.readFloat(), buf.readDouble());
    }

    public S2CBurstParticles(Identifier particle, double x, double y, double z, float angle, float arc, double speed, float updrift, float spacing, double dy) {
        this.particle = particle;
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
        this.arc = arc;
        this.speed = speed;
        this.updrift = updrift;
        this.spacing = spacing;
        this.dy = dy;
    }

    public static void receive(S2CBurstParticles packet, ClientPlayerEntity client, PacketSender sender) {
        ParticleHelper.spawnHorizontalBurst(client.getWorld(), (DefaultParticleType) Registries.PARTICLE_TYPE.get(packet.particle),
                packet.x, packet.y, packet.z,
                packet.angle, packet.arc, packet.speed,
                packet.updrift, packet.spacing, packet.dy);
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeIdentifier(particle);
        packetByteBuf.writeDouble(x);
        packetByteBuf.writeDouble(y);
        packetByteBuf.writeDouble(z);
        packetByteBuf.writeFloat(angle);
        packetByteBuf.writeFloat(arc);
        packetByteBuf.writeDouble(speed);
        packetByteBuf.writeFloat(updrift);
        packetByteBuf.writeFloat(spacing);
        packetByteBuf.writeDouble(dy);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
