package wehavecookies56.kk.driveforms;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

import wehavecookies56.kk.api.driveforms.DriveForm;
import wehavecookies56.kk.entities.ExtendedPlayer;
import wehavecookies56.kk.lib.Constants;
import wehavecookies56.kk.lib.Reference;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.server.GlidePacket;

public class DriveFormFinal extends DriveForm {

	double cost;

	public static boolean jumpHeld = false;

	public DriveFormFinal(double cost) {
		this.cost = cost;
	}

	@Override
	public String getName() {
		return "Final";
	}

	@Override
	public ResourceLocation getTexture() {
		return new ResourceLocation(Reference.MODID, "textures/driveforms/final.png");
	}

	@Override
	public double getCost() {
		return this.cost;
	}

	@Override
	public void initDrive(EntityPlayer player) {
		ExtendedPlayer.get(player).setDriveInUse(getName());
		ExtendedPlayer.get(player).setInDrive(true);
	}

	@Override
	public void update(EntityPlayer player) {
		if(player.motionY > 0)
		{
			player.motionY *= Constants.FINAL_JUMP;
		}

		if(player.onGround && !player.isInWater()){
			player.motionX *= 1.2D;
			player.motionZ *= 1.2D;
		}

		else
		{
			if(player.motionY < 0)
			{
				if(player.worldObj.isRemote){
					if(Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown())
					{
						jumpHeld = true;
						player.motionY *= Constants.FINAL_GLIDE_1;
						PacketDispatcher.sendToServer(new GlidePacket(jumpHeld));
					}
					else{
						jumpHeld = false;
						PacketDispatcher.sendToServer(new GlidePacket(jumpHeld));
					}
				}else{
					if(jumpHeld){
						player.motionY *= Constants.FINAL_GLIDE_1;
					}
				}
			}
		}

		if(ExtendedPlayer.get(player).cheatMode == false)
		{
			if(ExtendedPlayer.get(player).dp > 0)
			{
				ExtendedPlayer.get(player).dp -= 0.1;
				if(ExtendedPlayer.get(player).dp < 0)
				{
					ExtendedPlayer.get(player).dp = 0;
				}
			}
			else
			{
				endDrive(player);
			}
		}
	}

	@Override
	public void endDrive(EntityPlayer player) {
		ExtendedPlayer.get(player).setDP(0);
		ExtendedPlayer.get(player).setInDrive(false);
		ExtendedPlayer.get(player).setDriveInUse("none");
	}

}
