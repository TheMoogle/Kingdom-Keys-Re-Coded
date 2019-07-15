package uk.co.wehavecookies56.kk.common.item.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import uk.co.wehavecookies56.kk.api.menu.IItemCategory;
import uk.co.wehavecookies56.kk.api.menu.ItemCategory;
import uk.co.wehavecookies56.kk.client.sound.ModSounds;
import uk.co.wehavecookies56.kk.common.capability.ModCapabilities;
import uk.co.wehavecookies56.kk.common.item.ModItems;
import uk.co.wehavecookies56.kk.common.lib.Strings;
import uk.co.wehavecookies56.kk.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.kk.common.network.packet.server.GiveItemInSlot;
import uk.co.wehavecookies56.kk.common.network.packet.server.PotionConsume;
import uk.co.wehavecookies56.kk.common.network.packet.server.RemoveItemInSlotAndGiveEffect;

public abstract class ItemKKPotion extends ItemFood implements IItemCategory{

    String potionType, name;

    public ItemKKPotion (int food, boolean wolf, String type, String name) {
        super(food, wolf);
        setRegistryName(name);
        setTranslationKey(name);
        setCreativeTab(ModItems.tabKingdomKeys);
        setAlwaysEdible();
        //setMaxStackSize(64);
        this.potionType = type;
    }

    @Override
    public EnumAction getItemUseAction (ItemStack item) {
        return EnumAction.DRINK;
    }

    public String getItemName () {
        return name;
    }

    public void setItemName (String unlocalizedName) {
        this.name = unlocalizedName;
    }

    public static void getItem (EntityPlayer player, World world, String item, int slot) {
        while (ItemStack.areItemStacksEqual(player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().getStackInSlot(slot), ItemStack.EMPTY)) {
            slot++;
        }
        switch (item) {
            case Strings.Potion:
                ((ItemKKPotion) ModItems.Potion).getPotionEffect(player);
                player.world.playSound((EntityPlayer)null, player.getPosition(), ModSounds.potion, SoundCategory.MASTER, 1.0f, 1.0f);
                player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().setStackInSlot(slot, ItemStack.EMPTY);
                PacketDispatcher.sendToServer(new PotionConsume(Strings.Potion));
                PacketDispatcher.sendToServer(new RemoveItemInSlotAndGiveEffect(Strings.Potion, slot, true));
                break;
            case Strings.HiPotion:
                ((ItemKKPotion) ModItems.HiPotion).getPotionEffect(player);
                player.world.playSound((EntityPlayer)null, player.getPosition(), ModSounds.potion, SoundCategory.MASTER, 1.0f, 1.0f);
                player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().setStackInSlot(slot, ItemStack.EMPTY);
                PacketDispatcher.sendToServer(new PotionConsume(Strings.HiPotion));
                PacketDispatcher.sendToServer(new RemoveItemInSlotAndGiveEffect(Strings.Potion, slot, true));
                break;
            case Strings.MegaPotion:
                ((ItemKKPotion) ModItems.MegaPotion).getPotionEffect(player);
                player.world.playSound((EntityPlayer)null, player.getPosition(), ModSounds.potion, SoundCategory.MASTER, 1.0f, 1.0f);
                player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().setStackInSlot(slot, ItemStack.EMPTY);
                PacketDispatcher.sendToServer(new PotionConsume(Strings.MegaPotion));
                PacketDispatcher.sendToServer(new RemoveItemInSlotAndGiveEffect(Strings.Potion, slot, true));
                break;
            case Strings.Ether:
                ((ItemKKPotion) ModItems.Ether).getPotionEffect(player);
                player.world.playSound((EntityPlayer)null, player.getPosition(), ModSounds.potion, SoundCategory.MASTER, 1.0f, 1.0f);
                player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().setStackInSlot(slot, ItemStack.EMPTY);
                PacketDispatcher.sendToServer(new PotionConsume(Strings.Ether));
                PacketDispatcher.sendToServer(new RemoveItemInSlotAndGiveEffect(Strings.Potion, slot, true));
                break;
            case Strings.MegaEther:
                ((ItemKKPotion) ModItems.MegaEther).getPotionEffect(player);
                player.world.playSound((EntityPlayer)null, player.getPosition(), ModSounds.potion, SoundCategory.MASTER, 1.0f, 1.0f);
                player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().setStackInSlot(slot, ItemStack.EMPTY);
                PacketDispatcher.sendToServer(new PotionConsume(Strings.MegaEther));
                PacketDispatcher.sendToServer(new RemoveItemInSlotAndGiveEffect(Strings.Potion, slot, true));
                break;
            case Strings.Elixir:
                ((ItemKKPotion) ModItems.Elixir).getPotionEffect(player);
                player.world.playSound((EntityPlayer)null, player.getPosition(), ModSounds.potion, SoundCategory.MASTER, 1.0f, 1.0f);
                player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().setStackInSlot(slot, ItemStack.EMPTY);
                PacketDispatcher.sendToServer(new PotionConsume(Strings.Elixir));
                PacketDispatcher.sendToServer(new RemoveItemInSlotAndGiveEffect(Strings.Potion, slot, true));
                break;
            case Strings.Megalixir:
                ((ItemKKPotion) ModItems.Megalixir).getPotionEffect(player);
                player.world.playSound((EntityPlayer)null, player.getPosition(), ModSounds.potion, SoundCategory.MASTER, 1.0f, 1.0f);
                player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().setStackInSlot(slot, ItemStack.EMPTY);
                PacketDispatcher.sendToServer(new PotionConsume(Strings.Megalixir));
                PacketDispatcher.sendToServer(new RemoveItemInSlotAndGiveEffect(Strings.Potion, slot, true));
                break;
            case Strings.DriveRecovery:
                ((ItemKKPotion) ModItems.DriveRecovery).getPotionEffect(player);
                player.world.playSound((EntityPlayer)null, player.getPosition(), ModSounds.potion, SoundCategory.MASTER, 1.0f, 1.0f);
                player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().setStackInSlot(slot, ItemStack.EMPTY);
                PacketDispatcher.sendToServer(new PotionConsume(Strings.DriveRecovery));
                PacketDispatcher.sendToServer(new RemoveItemInSlotAndGiveEffect(Strings.Potion, slot, true));
                break;
            case Strings.HighDriveRecovery:
                ((ItemKKPotion) ModItems.HighDriveRecovery).getPotionEffect(player);
                player.world.playSound((EntityPlayer)null, player.getPosition(), ModSounds.potion, SoundCategory.MASTER, 1.0f, 1.0f);
                player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().setStackInSlot(slot, ItemStack.EMPTY);
                PacketDispatcher.sendToServer(new PotionConsume(Strings.HighDriveRecovery));
                PacketDispatcher.sendToServer(new RemoveItemInSlotAndGiveEffect(Strings.Potion, slot, true));
                break;
            case Strings.Panacea:
                ((ItemKKPotion) ModItems.Panacaea).getPotionEffect(player);
                player.world.playSound((EntityPlayer)null, player.getPosition(), ModSounds.potion, SoundCategory.MASTER, 1.0f, 1.0f);
                player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().setStackInSlot(slot, ItemStack.EMPTY);
                PacketDispatcher.sendToServer(new PotionConsume(Strings.Panacea));
                PacketDispatcher.sendToServer(new RemoveItemInSlotAndGiveEffect(Strings.Potion, slot, true));
                break;
            default:
                break;
        }
    }

    @Override
    public void onFoodEaten (ItemStack item, World world, EntityPlayer player) {
        potionEffect(player);
        if (!player.capabilities.isCreativeMode) 
        	PacketDispatcher.sendToServer(new GiveItemInSlot(new ItemStack(ModItems.EmptyBottle), player.inventory.getFirstEmptyStack(),false));
    }

    /**
     * Set what the potion does when used e.g. Healing, MP restore, etc.
     *
     * @return
     */
    public abstract void potionEffect (EntityPlayer player);

    public void getPotionEffect (EntityPlayer player) {
        potionEffect(player);
    }

    public void usePotion (EntityPlayer player, int slot) {
        ItemStackHandler inventory = player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu();
        if (!ItemStack.areItemStacksEqual(inventory.getStackInSlot(slot), ItemStack.EMPTY)) if (inventory.getStackInSlot(slot).getItem() instanceof ItemKKPotion) {
            inventory.setStackInSlot(slot, ItemStack.EMPTY);
            player.world.playSound((EntityPlayer)null, player.getPosition(), ModSounds.potion, SoundCategory.MASTER, 1.0f, 1.0f);
            PacketDispatcher.sendToServer(new RemoveItemInSlotAndGiveEffect("potion", slot, true));
        }
    }
    

	@Override
	public ItemCategory getCategory() {
		return ItemCategory.CONSUMABLE;
	}

}