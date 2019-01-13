package uk.co.wehavecookies56.kk.common.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.co.wehavecookies56.kk.client.sound.ModSounds;
import uk.co.wehavecookies56.kk.common.capability.FirstTimeJoinCapability.IFirstTimeJoin;
import uk.co.wehavecookies56.kk.common.capability.ModCapabilities;
import uk.co.wehavecookies56.kk.common.capability.PlayerStatsCapability.IPlayerStats;
import uk.co.wehavecookies56.kk.common.core.helper.TextHelper;
import uk.co.wehavecookies56.kk.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.kk.common.network.packet.client.SpawnCureParticles;
import uk.co.wehavecookies56.kk.common.network.packet.client.SyncMagicData;

public class BlockSavePoint extends Block {

	protected BlockSavePoint(Material material, String toolClass, int level, float hardness, float resistance, String name) {
		super(material);
		setHarvestLevel(toolClass, level);
		setHardness(hardness);
		setResistance(resistance);
		setCreativeTab(ModBlocks.tabKingdomKeysBlocks);
		setRegistryName(name);
		setUnlocalizedName(name);
		// setBlockBounds(0, 0, 0, 1, 0.1F, 1);
		setTickRandomly(true);
		// setSoundType(SoundType.STONE);
	}

	double a = 0;
	long ticks = 0;

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {
		/*
		 * double r = 0.6D; double cx = pos.getX() + 0.5; double cy = pos.getY(); double
		 * cz = pos.getZ() + 0.5;
		 * 
		 * // for (int a = 1; a <= 360; a += 7) { double x = cx + (r *
		 * Math.cos(Math.toRadians(a))); double z = cz + (r *
		 * Math.sin(Math.toRadians(a)));
		 * 
		 * double x2 = cx + (r * Math.cos(Math.toRadians(-a))); double z2 = cz + (r *
		 * Math.sin(Math.toRadians(-a)));
		 * 
		 * System.out.println(a/180/2);
		 * world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, x2, cy + 0.8D - a/360D,
		 * z2, 0.0D, 0.0D, 0.0D); world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
		 * x, cy + 0.2D + a/360D, z, 0.0D, 0.0D, 0.0D);
		 * 
		 * a++; if(a > 360) a = 0;
		 */
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		world.scheduleUpdate(pos, this, tickRate(world));
		super.onBlockAdded(world, pos, state);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entityIn) {
		if (!world.isRemote)
			updateState(world, pos);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (!world.isRemote)
			updateState(world, pos);
	}

	private void updateState(World world, BlockPos pos) {
		
		List list = world.getEntitiesWithinAABBExcludingEntity((Entity) null, new AxisAlignedBB(pos.add(-20, -20, -20), pos.add(20, 20, 20)));
		if (!list.isEmpty())
			for (int i = 0; i < list.size(); i++) {
				Entity e = (Entity) list.get(i);
				if (e instanceof EntityPlayer) {
					System.out.println(pos);
					ticks++;
					if (a > 360)
						a = 0;
					a += 10;
					PacketDispatcher.sendToAllAround(new SpawnCureParticles(pos, true, (int) a), world.provider.getDimension(), (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), 32);
				}
			}

		list = world.getEntitiesWithinAABBExcludingEntity((Entity) null, new AxisAlignedBB(pos.add(0, 0, 0), pos.add(1, 1, 1)));
		if (!list.isEmpty())
			for (int i = 0; i < list.size(); i++) {
				Entity e = (Entity) list.get(i);
				if (e instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) e;
					IPlayerStats STATS = player.getCapability(ModCapabilities.PLAYER_STATS, null);

					// System.out.println(player.getBedLocation());
					// System.out.println(pos.getX());
					boolean samePos;
					BlockPos bedPos;
					if (ticks % 10 == 0) {
						if (player.getBedLocation() == null) {
							IFirstTimeJoin originalPos = player.getCapability(ModCapabilities.FIRST_TIME_JOIN, null);
							bedPos = new BlockPos(originalPos.getPosX(), originalPos.getPosY(), originalPos.getPosZ());
						} else {
							bedPos = player.getBedLocation();
						}

						samePos = bedPos.getX() == pos.getX() && bedPos.getY() == pos.getY() && bedPos.getZ() == pos.getZ();

						if (player.isSneaking() && !samePos) {
							player.bedLocation = pos;
							player.setSpawnChunk(pos, true, 0);
							player.setSpawnPoint(pos, true);
							TextHelper.sendFormattedChatMessage("Spawn point saved!", TextFormatting.GREEN, player);
							world.playSound((EntityPlayer) null, player.getPosition(), ModSounds.savespawn, SoundCategory.BLOCKS, 1.0f, 1.0f);
						}
					}

					if (player.getHealth() < player.getMaxHealth() || STATS.getMP() < STATS.getMaxMP()) {
						player.heal(1);
						STATS.addMP(2);
						if (player.getFoodStats().getFoodLevel() < 20)
							player.getFoodStats().addStats(4, 0);

						if (ticks % 20 == 0) {
							world.playSound((EntityPlayer) null, player.getPosition(), ModSounds.savepoint, SoundCategory.BLOCKS, 1.0f, 1.0f);
							PacketDispatcher.sendToAllAround(new SpawnCureParticles(pos, true, 0), player, 64.0D);
						}
						PacketDispatcher.sendTo(new SyncMagicData(player.getCapability(ModCapabilities.MAGIC_STATE, null), STATS), (EntityPlayerMP) player);
					}
				}
			}
		world.scheduleUpdate(new BlockPos(pos), this, this.tickRate(world));
	}

	@Override
	public int tickRate(World worldIn) {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@SuppressWarnings("deprecation")
	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
		return new AxisAlignedBB(new BlockPos(0, 0, 0), new BlockPos(1, 0.1, 1));
	}

	@SuppressWarnings("deprecation")
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos) {
		return new AxisAlignedBB(new BlockPos(0, 0, 0), new BlockPos(1, 0.1, 1));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random r, int fortune) {
		return Item.getItemFromBlock(this);
	}

}
