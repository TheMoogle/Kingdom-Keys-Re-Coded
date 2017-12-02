package uk.co.wehavecookies56.kk.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import uk.co.wehavecookies56.kk.common.lib.Reference;

public class ItemEraqusArmor extends ItemArmor /*implements ISpecialArmor*/ {

    public ItemEraqusArmor (ArmorMaterial material, int renderIndex, EntityEquipmentSlot slot, int armorType, String name) {
        super(material, renderIndex, slot);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(ModItems.tabKingdomKeys);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if (stack.getItem() == ModItems.Eraqus_Leggings)
            return Reference.MODID + ":textures/armour/eraqus_2.png";
        else
            return Reference.MODID + ":textures/armour/eraqus_1.png";
    }

    @Override
    public CreativeTabs[] getCreativeTabs () {
        return new CreativeTabs[] { ModItems.tabKingdomKeys };
    }

    @Override
    public boolean getIsRepairable (ItemStack armor, ItemStack stack) {
        return stack.getItem() == ModItems.DarkLeather;
    }

}
