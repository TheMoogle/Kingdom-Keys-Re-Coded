package uk.co.wehavecookies56.kk.common.item.base;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import uk.co.wehavecookies56.kk.common.item.ModItems;
import uk.co.wehavecookies56.kk.common.lib.Strings;
import uk.co.wehavecookies56.kk.common.util.Utils;

public class ItemKKRecord extends ItemRecord {

    float length;

    public ItemKKRecord (SoundEvent sound, String name, float length) {
        super(name, sound);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(ModItems.tabKingdomKeys);
        this.length = length;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String length = String.format("%.02f", this.length).replace("f", "").replace("F", "").replace(".", ":");
        tooltip.add(Utils.translateToLocal(Strings.Disc_Duration_Desc) + ": " + length + " " + Utils.translateToLocal(Strings.Disc_DurationUnits_Desc));
    }
}
