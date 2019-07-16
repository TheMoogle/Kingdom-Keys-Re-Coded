package uk.co.wehavecookies56.kk.common.block;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import uk.co.wehavecookies56.kk.common.block.base.BlockBlox;

public class BlockBounceBlox extends BlockBlox {

    protected BlockBounceBlox (Material material, String toolClass, int level, float hardness, float resistance, String name) {
        super(material, toolClass, level, hardness, resistance, name);
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!entityIn.isSneaking()) {
            entityIn.playSound(SoundEvents.ENTITY_SLIME_JUMP, 1, 1);
            double d0 = 0.4D + Math.abs(entityIn.motionY) * 0.2D;
            entityIn.motionX *= d0;
            entityIn.motionZ *= d0;
            entityIn.motionY++;
            entityIn.fallDistance = 0;
            super.onEntityCollision(worldIn, pos, state, entityIn);
        }
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);
    }
}
