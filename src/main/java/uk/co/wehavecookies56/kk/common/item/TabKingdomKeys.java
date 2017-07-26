package uk.co.wehavecookies56.kk.common.item;

import java.io.IOException;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.co.wehavecookies56.kk.common.KingdomKeys;

public class TabKingdomKeys extends CreativeTabs {

    public TabKingdomKeys (int index, String label) {
        super(index, label);
    }

    @Override
    @SideOnly (Side.CLIENT)
    public ItemStack getTabIconItem () {
        return new ItemStack(ModItems.KingdomHearts);
    }

    @Override
    public void displayAllRelevantItems (NonNullList<ItemStack> list) {
        super.displayAllRelevantItems(list);
        ItemStack munny = new ItemStack(ModItems.Munny, 1);
        munny.setTagCompound(new NBTTagCompound());
        munny.getTagCompound().setInteger("amount", 1000000);
        list.add(munny);
        ItemStack driveOrb = new ItemStack(ModItems.DriveOrb, 1);
        driveOrb.setTagCompound(new NBTTagCompound());
        driveOrb.getTagCompound().setInteger("amount", 1000);
        list.add(driveOrb);
        ItemStack magicOrb = new ItemStack(ModItems.MagicOrb, 1);
        magicOrb.setTagCompound(new NBTTagCompound());
        magicOrb.getTagCompound().setInteger("amount", 100);
        list.add(magicOrb);
        try {
            getSkulls(list);
        } catch (IOException e) {
            KingdomKeys.logger.info("Unable to get player skulls");
        }
    }

    public void getSkulls (List list) throws IOException {
        ItemStack WHC56skull = new ItemStack(Items.SKULL, 1, 3);
        try {
            WHC56skull.setTagCompound(new NBTTagCompound());
            WHC56skull.getTagCompound().setTag("SkullOwner", new NBTTagString("Wehavecookies56"));
            list.add(WHC56skull);
            ItemStack AAskull = new ItemStack(Items.SKULL, 1, 3);
            AAskull.setTagCompound(new NBTTagCompound());
            AAskull.getTagCompound().setTag("SkullOwner", new NBTTagString("Abelatox"));
            list.add(AAskull);
        } catch (Exception e) {

        }

    }

}
