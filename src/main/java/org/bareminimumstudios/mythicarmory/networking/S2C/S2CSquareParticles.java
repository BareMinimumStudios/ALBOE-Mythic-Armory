package org.bareminimumstudios.mythicarmory.networking.S2C;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.bareminimumstudios.mythicarmory.util.HelperMethods;
import org.bareminimumstudios.mythicarmory.util.ParticleHelper;

public class S2CSquareParticles implements FabricPacket {
    public static final PacketType<S2CSquareParticles> TYPE =
            PacketType.create(HelperMethods.identifierOf("square_particles"), S2CSquareParticles::new);

    public final Identifier particle;
    public final double x;
    public final double y;
    public final double z;
    public final double size;
    public final float yaw;
    public final float pitch;
    public final float roll;
    public final double speedMin;
    public final double speedMax;
    public final int count;
    public final boolean cropToCircle;

    public S2CSquareParticles(PacketByteBuf buf) {
        this(buf.readIdentifier(),
                buf.readDouble(), buf.readDouble(), buf.readDouble(),
                buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.readFloat(),
                buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readBoolean());
    }

    public S2CSquareParticles(Identifier particle, double x, double y, double z, double size, float yaw, float pitch, float roll, double speedMin, double speedMax, int count, boolean cropToCircle) {
        this.particle = particle;
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
        this.speedMin = speedMin;
        this.speedMax = speedMax;
        this.count = count;
        this.cropToCircle = cropToCircle;
    }

    public static void receive(S2CSquareParticles packet, ClientPlayerEntity client, PacketSender sender) {
        ParticleHelper.spawn2DSquare(client.getWorld(), (DefaultParticleType) Registries.PARTICLE_TYPE.get(packet.particle),
                packet.x, packet.y, packet.z,
                packet.size, packet.yaw, packet.pitch, packet.roll,
                packet.speedMin, packet.speedMax, packet.count, packet.cropToCircle);
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeIdentifier(particle);
        packetByteBuf.writeDouble(x);
        packetByteBuf.writeDouble(y);
        packetByteBuf.writeDouble(z);
        packetByteBuf.writeDouble(size);
        packetByteBuf.writeFloat(yaw);
        packetByteBuf.writeFloat(pitch);
        packetByteBuf.writeFloat(roll);
        packetByteBuf.writeDouble(speedMin);
        packetByteBuf.writeDouble(speedMax);
        packetByteBuf.writeInt(count);
        packetByteBuf.writeBoolean(cropToCircle);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
