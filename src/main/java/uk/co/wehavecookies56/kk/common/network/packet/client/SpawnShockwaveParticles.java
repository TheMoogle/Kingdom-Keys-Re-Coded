package uk.co.wehavecookies56.kk.common.network.packet.client;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.kk.common.network.packet.AbstractMessage;

public class SpawnShockwaveParticles extends AbstractMessage.AbstractClientMessage<SpawnShockwaveParticles> {

    double x, y, z;

    public SpawnShockwaveParticles () {}

    public SpawnShockwaveParticles (Entity entity) {
        x = entity.posX;
        y = entity.posY;
        z = entity.posZ;
    }

    @Override
    protected void read (PacketBuffer buffer) throws IOException {
        x = buffer.readDouble();
        y = buffer.readDouble();
        z = buffer.readDouble();
    }

    @Override
    protected void write (PacketBuffer buffer) throws IOException {
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
    }

    @Override
    public void process (EntityPlayer player, Side side) {
        double r = 1.5D;
        for (int a = 1; a <= 360; a += 20) {
            double x = this.x + (r * Math.cos(Math.toRadians(a)));
            double z = this.z + (r * Math.sin(Math.toRadians(a)));
            player.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, x, this.y, z, 0, 0.6, 0);
        }
    }

}
