package wehavecookies56.kk.entities.magic;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBlizzard extends EntityThrowable
{
	public EntityLivingBase shootingEntity;

	public EntityBlizzard(World world) {
		super(world);
	}

	public EntityBlizzard(World world, EntityLivingBase entity) {
		super(world, entity);
	}

	public EntityBlizzard(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected float getGravityVelocity() {
		return 0.0F;
	}

	@Override
	public void onUpdate() {
		int rotation = 0;
		this.worldObj.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		this.rotationYaw = (rotation + 1) % 360;
		if(ticksExisted > 60){
			setDead();
		}
		super.onUpdate();
	}
	@Override
	protected void onImpact(MovingObjectPosition movingObject) {
		if (!this.worldObj.isRemote)
		{
			boolean flag;

			if (movingObject.entityHit != null)
			{
				flag = movingObject.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 8);
				if (flag)
				{
					this.func_174815_a(this.shootingEntity, movingObject.entityHit);
					if(movingObject.entityHit.isBurning())
					{
						movingObject.entityHit.extinguish();
					}
					else
					{
						movingObject.entityHit.attackEntityFrom(DamageSource.magic, 4);
					}
				}
			}
			  else
            {
                flag = true;

                if (this.shootingEntity != null && this.shootingEntity instanceof EntityLiving)
                {
                    flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
                }

                if (flag)
                {
                    BlockPos blockpos = movingObject.func_178782_a().offset(movingObject.field_178784_b);

                    if (this.worldObj.getBlockState(blockpos).getBlock() == Blocks.water)
                    {
                        this.worldObj.setBlockState(blockpos, Blocks.ice.getDefaultState());
                    }
                    else if (this.worldObj.getBlockState(blockpos).getBlock() == Blocks.fire){
                        this.worldObj.setBlockState(blockpos, Blocks.air.getDefaultState());
                    }
                }
            }
			this.setDead();
		}
	}


}