package wehavecookies56.kk.network.packet.client;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractClientMessage;

public class SpawnCureParticles extends AbstractClientMessage<SpawnCureParticles> {

	double x, y, z;

	public SpawnCureParticles() {}

	public SpawnCureParticles(Entity entity) {
		x = entity.posX;
		y = entity.posY;
		z = entity.posZ;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		x = buffer.readDouble();
		y = buffer.readDouble();
		z = buffer.readDouble();

	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		player.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, player.posX, player.posY+2.5, player.posZ, 0.0D, 1.0D, 0.0D);
		player.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, player.posX, player.posY+2.8, player.posZ, 0.0D, 1.0D, 0.0D);
		player.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, player.posX, player.posY+2.2, player.posZ, 0.0D, 1.0D, 0.0D);
		player.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, player.posX+0.3, player.posY+2.5, player.posZ, 0.0D, 1.0D, 0.0D);
		player.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, player.posX-0.3, player.posY+2.5, player.posZ, 0.0D, 1.0D, 0.0D);
	}

}