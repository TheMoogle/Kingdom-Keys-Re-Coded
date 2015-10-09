package wehavecookies56.kk.network.packet.client;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.entities.ExtendedPlayer;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractClientMessage;

public class SpawnFireParticles extends AbstractClientMessage<SpawnFireParticles> {

	double x, y, z;

	public SpawnFireParticles() {}

	public SpawnFireParticles(Entity entity) {
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
		double r;
		switch(ExtendedPlayer.get(player).getMagicLevel("Aero"))
		{
			case 1:
				r = 1.5D;
				for(int a = 1; a <= 360; a+=7){
					double x = this.x + (r * Math.cos(Math.toRadians(a)));
					double z = this.z + (r * Math.sin(Math.toRadians(a)));

					player.worldObj.spawnParticle(EnumParticleTypes.FLAME, x, this.y + 1.25D, z, 0.0D, 0.0D, 0.0D);
					player.worldObj.spawnParticle(EnumParticleTypes.FLAME, x, this.y + 1.05D, z, 0.0D, 0.0D, 0.0D);
				}
				break;
			
			case 2:
				r = 2.3D;
				for(int a = 1; a <= 360; a+=7){
					double x = this.x + (r * Math.cos(Math.toRadians(a)));
					double z = this.z + (r * Math.sin(Math.toRadians(a)));

					player.worldObj.spawnParticle(EnumParticleTypes.FLAME, x, this.y + 1.25D, z, 0.0D, 0.0D, 0.0D);
					player.worldObj.spawnParticle(EnumParticleTypes.FLAME, x, this.y + 1.05D, z, 0.0D, 0.0D, 0.0D);
				}
				break;
			
			case 3:
				r = 2D;
				for(int a = 1; a <= 360; a+=7){
					double x = this.x + (r * Math.cos(Math.toRadians(a)));
					double z = this.z + (r * Math.sin(Math.toRadians(a)));
					double x2 = this.x + (r/1.3 * Math.cos(Math.toRadians(a)));
					double z2 = this.z + (r/1.3 * Math.sin(Math.toRadians(a)));

					player.worldObj.spawnParticle(EnumParticleTypes.FLAME, x2, this.y + 2.0D, z2, 0.0D, 0.0D, 0.0D);
					player.worldObj.spawnParticle(EnumParticleTypes.FLAME, x, this.y + 1.5D, z, 0.0D, 0.0D, 0.0D);
					player.worldObj.spawnParticle(EnumParticleTypes.FLAME, x, this.y + 1D, z, 0.0D, 0.0D, 0.0D);
					player.worldObj.spawnParticle(EnumParticleTypes.FLAME, x, this.y + 0.5D, z, 0.0D, 0.0D, 0.0D);
					player.worldObj.spawnParticle(EnumParticleTypes.FLAME, x, this.y + 0.0D, z, 0.0D, 0.0D, 0.0D);
					
					double d = r - x;
				 /*for(double i = 1; i <= r; i = i+0.1)
					{
						player.worldObj.spawnParticle(EnumParticleTypes.FLAME, i, this.y + 0.1D, z/i, 0.0D, 0.0D, 0.0D);
					}*/					
				}
				break;
			}
		}
	}


