package uk.co.wehavecookies56.kk.common.container.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import uk.co.wehavecookies56.kk.common.item.base.ItemSpellOrb;
import uk.co.wehavecookies56.kk.common.lib.Strings;
import uk.co.wehavecookies56.kk.common.util.Utils;

public class InventorySpells {
    public static final String name = Utils.translateToLocal(Strings.SpellsInventory);
    public static final String SAVE_KEY = "SpellsInvKey";
    public static final int INV_SIZE = 7;
}