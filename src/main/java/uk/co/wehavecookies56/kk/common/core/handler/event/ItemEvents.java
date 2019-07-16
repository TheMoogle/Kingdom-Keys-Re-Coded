package uk.co.wehavecookies56.kk.common.core.handler.event;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Multimap;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import uk.co.wehavecookies56.kk.api.munny.MunnyRegistry;
import uk.co.wehavecookies56.kk.client.core.helper.GuiHelper;
import uk.co.wehavecookies56.kk.client.core.helper.KeyboardHelper;
import uk.co.wehavecookies56.kk.common.block.ModBlocks;
import uk.co.wehavecookies56.kk.common.capability.DriveStateCapability.IDriveState;
import uk.co.wehavecookies56.kk.common.capability.ModCapabilities;
import uk.co.wehavecookies56.kk.common.capability.MunnyCapability;
import uk.co.wehavecookies56.kk.common.capability.PlayerStatsCapability.IPlayerStats;
import uk.co.wehavecookies56.kk.common.core.handler.MainConfig;
import uk.co.wehavecookies56.kk.common.entity.magic.DamageCalculation;
import uk.co.wehavecookies56.kk.common.item.ItemHpOrb;
import uk.co.wehavecookies56.kk.common.item.ItemMunny;
import uk.co.wehavecookies56.kk.common.item.ModItems;
import uk.co.wehavecookies56.kk.common.item.base.ItemDriveForm;
import uk.co.wehavecookies56.kk.common.item.base.ItemKeyblade;
import uk.co.wehavecookies56.kk.common.item.base.ItemKeychain;
import uk.co.wehavecookies56.kk.common.item.base.ItemSpellOrb;
import uk.co.wehavecookies56.kk.common.item.base.ItemSynthesisMaterial;
import uk.co.wehavecookies56.kk.common.item.org.IOrgWeapon;
import uk.co.wehavecookies56.kk.common.lib.Strings;
import uk.co.wehavecookies56.kk.common.lib.Tutorials;
import uk.co.wehavecookies56.kk.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.kk.common.network.packet.client.OpenTutorialGUI;
import uk.co.wehavecookies56.kk.common.network.packet.client.ShowOverlayPacket;
import uk.co.wehavecookies56.kk.common.network.packet.client.SyncDriveData;
import uk.co.wehavecookies56.kk.common.network.packet.client.SyncDriveInventory;
import uk.co.wehavecookies56.kk.common.network.packet.client.SyncKeybladeData;
import uk.co.wehavecookies56.kk.common.network.packet.client.SyncMagicData;
import uk.co.wehavecookies56.kk.common.network.packet.client.SyncMunnyData;
import uk.co.wehavecookies56.kk.common.network.packet.client.SyncOrgXIIIData;
import uk.co.wehavecookies56.kk.common.network.packet.client.SyncTutorials;
import uk.co.wehavecookies56.kk.common.network.packet.server.HpOrbPickup;
import uk.co.wehavecookies56.kk.common.network.packet.server.MunnyPickup;
import uk.co.wehavecookies56.kk.common.network.packet.server.TutorialsPacket;
import uk.co.wehavecookies56.kk.common.util.Utils;

/**
 * Created by Toby on 19/07/2016.
 */
public class ItemEvents {

	@SubscribeEvent
	public void onEntityItemPickUp(EntityItemPickupEvent event) {
		IPlayerStats STATS = event.getEntityPlayer().getCapability(ModCapabilities.PLAYER_STATS, null);
		IDriveState DRIVE = event.getEntityPlayer().getCapability(ModCapabilities.DRIVE_STATE, null);

		if (event.getItem().getItem().getItem() instanceof ItemMunny) {
			final MunnyCapability.IMunny munny = event.getEntityPlayer().getCapability(ModCapabilities.MUNNY, null);
			MunnyPickup packet = new MunnyPickup(event.getItem().getItem());
			event.getItem().getItem().setCount(event.getItem().getItem().getCount() - 1);

			munny.addMunny(event.getItem().getItem().getTagCompound().getInteger("amount"));
			PacketDispatcher.sendTo(new SyncMunnyData(munny), (EntityPlayerMP) event.getEntityPlayer());
			PacketDispatcher.sendTo(new ShowOverlayPacket("munny", event.getItem().getItem().getTagCompound().getInteger("amount")), (EntityPlayerMP) event.getEntityPlayer());

		} else if (event.getItem().getItem().getItem() instanceof ItemHpOrb) {
			if (!ItemStack.areItemStacksEqual(event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND), ItemStack.EMPTY))
				if (event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.EmptyBottle)
					return;
			HpOrbPickup packet = new HpOrbPickup(event.getItem().getItem());
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
				if (event.getEntityPlayer().getHealth() >= event.getEntityPlayer().getMaxHealth()) {
					event.getItem().getItem().setCount(event.getItem().getItem().getCount() - 1);
					;
					return;
				}
				if (event.getEntityPlayer().getHealth() < event.getEntityPlayer().getMaxHealth() - 1)
					event.getEntityPlayer().heal(2);
				else
					event.getEntityPlayer().heal(1);
				event.getItem().getItem().setCount(event.getItem().getItem().getCount() - 1);

			}
		} else if (event.getItem().getItem().getItem() == ModItems.DriveOrb) {
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
				event.getItem().getItem().setCount(event.getItem().getItem().getCount() - 1);
				EntityPlayer player = event.getEntityPlayer();
				System.out.println(event.getItem().getItem().getTagCompound().getInteger("amount"));
				if (DRIVE.getInDrive()) {
					DRIVE.addFP(event.getItem().getItem().getTagCompound().getInteger("amount"));
				} else {
					DRIVE.addDP(event.getItem().getItem().getTagCompound().getInteger("amount"));
				}

				PacketDispatcher.sendTo(new SyncDriveData(player.getCapability(ModCapabilities.DRIVE_STATE, null)), (EntityPlayerMP) player);
				PacketDispatcher.sendTo(new SyncDriveInventory(player.getCapability(ModCapabilities.DRIVE_STATE, null)), (EntityPlayerMP) event.getEntityPlayer());
			}

		} else if (event.getItem().getItem().getItem() == ModItems.MagicOrb) {
			double mp = STATS.getMP();
			if (!ItemStack.areItemStacksEqual(event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND), ItemStack.EMPTY))
				if (event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.EmptyBottle)
					return;
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
				event.getItem().getItem().setCount(event.getItem().getItem().getCount() - 1);

				STATS.addMP(event.getItem().getItem().getTagCompound().getInteger("amount"));
				PacketDispatcher.sendTo(new SyncMagicData(event.getEntityPlayer().getCapability(ModCapabilities.MAGIC_STATE, null), STATS), (EntityPlayerMP) event.getEntityPlayer());
			}
		} else if (event.getItem().getItem().getItem() instanceof ItemSynthesisMaterial) {
			for (int i = 0; i < event.getEntityPlayer().inventory.getSizeInventory(); i++) {
				if (!ItemStack.areItemStacksEqual(event.getEntityPlayer().inventory.getStackInSlot(i), ItemStack.EMPTY)) {
					if (event.getEntityPlayer().inventory.getStackInSlot(i).getItem() == ModItems.SynthesisBagL) {
						IItemHandler inv = event.getEntityPlayer().inventory.getStackInSlot(i).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
						addSynthesisMaterialToBag(inv, event);
					} else if (event.getEntityPlayer().inventory.getStackInSlot(i).getItem() == ModItems.SynthesisBagM) {
						IItemHandler inv = event.getEntityPlayer().inventory.getStackInSlot(i).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
						addSynthesisMaterialToBag(inv, event);
					}
					if (event.getEntityPlayer().inventory.getStackInSlot(i).getItem() == ModItems.SynthesisBagS) {
						IItemHandler inv = event.getEntityPlayer().inventory.getStackInSlot(i).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
						addSynthesisMaterialToBag(inv, event);
					}
				}
			}
		} else if (event.getItem().getItem().getItem() instanceof ItemSpellOrb) {
			//PacketDispatcher.sendTo(new OpenTutorialGUI(Tutorials.TUTORIAL_MAGIC_1), (EntityPlayerMP) event.getEntityPlayer());
			//event.getEntityPlayer().getCapability(ModCapabilities.TUTORIALS, null).setKnownTutorial(Tutorials.getTutorialById(Tutorials.TUTORIAL_MAGIC_1).getRoot().getTutorialID(), true);
			//PacketDispatcher.sendTo(new SyncTutorials(event.getEntityPlayer().getCapability(ModCapabilities.TUTORIALS, null)), (EntityPlayerMP) event.getEntityPlayer());
			PacketDispatcher.sendToServer(new TutorialsPacket(Tutorials.getTutorialById(Tutorials.TUTORIAL_MAGIC_1).getRoot().getTutorialID()));
		} else if (event.getItem().getItem().getItem() instanceof ItemDriveForm) {
			PacketDispatcher.sendToServer(new TutorialsPacket(Tutorials.getTutorialById(Tutorials.TUTORIAL_DRIVE_1).getRoot().getTutorialID()));
		}

	}

	public void addSynthesisMaterialToBag(IItemHandler inv, EntityItemPickupEvent event) {
		for (int j = 0; j < inv.getSlots(); j++) {
			ItemStack bagItem = inv.getStackInSlot(j);
			ItemStack pickUp = event.getItem().getItem();
			if (!ItemStack.areItemStacksEqual(bagItem, ItemStack.EMPTY)) {
				if (bagItem.getItem().equals(pickUp.getItem())) {
					if (bagItem.hasTagCompound() && pickUp.hasTagCompound()) {
						if (bagItem.getTagCompound().hasKey("material") && pickUp.getTagCompound().hasKey("material")) {
							if (bagItem.getTagCompound().getString("material").equals(pickUp.getTagCompound().getString("material"))) {
								if (bagItem.getCount() < 64) {
									if (bagItem.getCount() + pickUp.getCount() <= 64) {
										ItemStack stack = new ItemStack(pickUp.copy().getItem(), pickUp.copy().getCount());
										stack.setTagCompound(new NBTTagCompound());
										stack.getTagCompound().setString("material", bagItem.getTagCompound().getString("material"));
										stack.getTagCompound().setString("rank", bagItem.getTagCompound().getString("rank"));
										inv.insertItem(j, stack, false);
										pickUp.setCount(0);
										return;
									}
								}
							}
						}
					}
				}
			} else if (ItemStack.areItemStacksEqual(bagItem, ItemStack.EMPTY)) {
				inv.insertItem(j, pickUp.copy(), false);
				pickUp.setCount(0);
				return;
			}
		}
	}

	public static boolean isItemStackEqualExcludingStackSize(ItemStack stack, ItemStack other) {
		if (stack.getItem() != other.getItem())
			return false;
		if (stack.getItemDamage() != other.getItemDamage())
			return false;
		if (!ItemStack.areItemStackTagsEqual(stack, other))
			return false;
		return true;
	}

	public static boolean areItemStacksEqual(@Nullable ItemStack stackA, @Nullable ItemStack stackB) {
		return ItemStack.areItemStacksEqual(stackA, ItemStack.EMPTY) && ItemStack.areItemStacksEqual(stackB, ItemStack.EMPTY) || ((!ItemStack.areItemStacksEqual(stackA, ItemStack.EMPTY) && !ItemStack.areItemStacksEqual(stackB, ItemStack.EMPTY)) && isItemStackEqualExcludingStackSize(stackA, stackB));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void addTooltip(ItemTooltipEvent event) {
		List<String> tooltip = event.getToolTip();

		for (ItemStack stack : MunnyRegistry.munnyValues.keySet()) {
			if (areItemStacksEqual(stack, event.getItemStack())) {
				event.getToolTip().add(TextFormatting.YELLOW + "Munny: " + MunnyRegistry.munnyValues.get(stack) * event.getItemStack().getCount());
			}
		}
		// TODO Localize all this
		if (event.getItemStack().getItem() instanceof ItemKeyblade && event.getEntityPlayer() != null) {
			// List<String> tooltip = event.getToolTip();
			ItemKeyblade keyblade = (ItemKeyblade) event.getItemStack().getItem();
			(tooltip.subList(1, tooltip.size())).clear();

			NBTTagList nbttaglist = event.getItemStack().getEnchantmentTagList();
			double sharpnessDamage = 0;
			for (int i = 0; i < nbttaglist.tagCount(); i++) {
				int id = nbttaglist.getCompoundTagAt(i).getShort("id");
				int lvl = nbttaglist.getCompoundTagAt(i).getShort("lvl");

				// System.out.println(Enchantment.getEnchantmentByID(id).getName());
				if (Enchantment.getEnchantmentByID(id).getName().equals("enchantment.damage.all")) {
					sharpnessDamage = getSharpnessDamage(lvl);
				}
			}

			double keyStrength = keyblade.getStrength() + sharpnessDamage;

			String magicSymbol = (keyblade.getMagic() > 0) ? "+" : "-";
			tooltip.add(TextFormatting.RED + "Strength: +" + keyStrength * MainConfig.items.damageMultiplier + " [" + (DamageCalculation.getStrengthDamage(event.getEntityPlayer(), keyblade) + sharpnessDamage) + "]");
			tooltip.add(TextFormatting.BLUE + "Magic: " + magicSymbol + keyblade.getMagic() * MainConfig.items.damageMultiplier + " [" + DamageCalculation.getMagicDamage(event.getEntityPlayer(), 1, keyblade) + "]");
//			tooltip.add(TextFormatting.GREEN + "Ability: " + keyblade.getAbility() == null ? "---" : Utils.translateToLocal(keyblade.getAbility().getName()));
			if (keyblade.getDescription() != null) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					tooltip.add("" + TextFormatting.WHITE + TextFormatting.UNDERLINE + "Description");
					tooltip.add(keyblade.description);
					tooltip.add("");
				} else {
					tooltip.add("Hold " + TextFormatting.GREEN + TextFormatting.ITALIC + "Shift" + TextFormatting.GRAY + " for description");
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
				if (event.getItemStack().hasTagCompound()) {
					tooltip.add("" + TextFormatting.WHITE + TextFormatting.UNDERLINE + "Stats");
					for (int i = 0; i < nbttaglist.tagCount(); i++) {
						int id = nbttaglist.getCompoundTagAt(i).getShort("id");
						int lvl = nbttaglist.getCompoundTagAt(i).getShort("lvl");

						if (Enchantment.getEnchantmentByID(id) != null) {
							tooltip.add(Enchantment.getEnchantmentByID(id).getTranslatedName(lvl));
						}
					}
				}
				for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
					Multimap<String, AttributeModifier> multimap = event.getItemStack().getAttributeModifiers(entityequipmentslot);

					if (!multimap.isEmpty()) {
						tooltip.add("");
						for (Map.Entry<String, AttributeModifier> entry : multimap.entries()) {
							AttributeModifier attributemodifier = (AttributeModifier) entry.getValue();
							double d0 = attributemodifier.getAmount();
							boolean flag = false;

							if (attributemodifier.getID() == UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF")) {
								d0 = d0 + event.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
								d0 = d0 + (double) EnchantmentHelper.getModifierForCreature(event.getItemStack(), EnumCreatureAttribute.UNDEFINED);
								flag = true;
							}

							double d1;

							if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2) {
								d1 = d0;
							} else {
								d1 = d0 * 100.0D;
							}

							if (entry.getKey() == "generic.attackDamage") {
								d1 += event.getEntityPlayer().getCapability(ModCapabilities.PLAYER_STATS, null).getStrength();
							}
							if (flag) {
								tooltip.add(Utils.translateToLocalFormatted("attribute.modifier.equals." + attributemodifier.getOperation(), new Object[] { ItemStack.DECIMALFORMAT.format(d1), Utils.translateToLocal("attribute.name." + (String) entry.getKey()) }));
							} else if (d0 > 0.0D) {
								tooltip.add(TextFormatting.BLUE + Utils.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier.getOperation(), new Object[] { ItemStack.DECIMALFORMAT.format(d1), Utils.translateToLocal("attribute.name." + (String) entry.getKey()) }));
							} else if (d0 < 0.0D) {
								d1 = d1 * -1.0D;
								tooltip.add(TextFormatting.RED + Utils.translateToLocalFormatted("attribute.modifier.take." + attributemodifier.getOperation(), new Object[] { ItemStack.DECIMALFORMAT.format(d1), Utils.translateToLocal("attribute.name." + (String) entry.getKey()) }));
							}
						}
					}
				}
			} else {
				tooltip.add("Hold " + TextFormatting.YELLOW + TextFormatting.ITALIC + "Alt" + TextFormatting.GRAY + " for more stats");
			}
		}

		if (event.getItemStack().getItem() instanceof ItemKeychain && event.getEntityPlayer() != null) {
			// List<String> tooltip = event.getToolTip();
			ItemKeyblade keyblade = ((ItemKeychain) event.getItemStack().getItem()).getKeyblade();
			if (keyblade != null) {
				(tooltip.subList(1, tooltip.size())).clear();

				NBTTagList nbttaglist = event.getItemStack().getEnchantmentTagList();
				double sharpnessDamage = 0;
				for (int i = 0; i < nbttaglist.tagCount(); i++) {
					int id = nbttaglist.getCompoundTagAt(i).getShort("id");
					int lvl = nbttaglist.getCompoundTagAt(i).getShort("lvl");
					if (Enchantment.getEnchantmentByID(id).getName().equals("enchantment.damage.all")) {
						sharpnessDamage = getSharpnessDamage(lvl);
					}
				}

				double keyStrength = keyblade.getStrength() + sharpnessDamage;

				String magicSymbol = (keyblade.getMagic() > 0) ? "+" : "";

				tooltip.add(TextFormatting.RED + "Strength: +" + keyStrength * MainConfig.items.damageMultiplier + " [" + (DamageCalculation.getStrengthDamage(event.getEntityPlayer(), keyblade) + sharpnessDamage) + "]");
				tooltip.add(TextFormatting.BLUE + "Magic: " + magicSymbol + keyblade.getMagic() * MainConfig.items.damageMultiplier + " [" + DamageCalculation.getMagicDamage(event.getEntityPlayer(), 1, keyblade) + "]");
				if (keyblade.getDescription() != null) {
					if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
						tooltip.add("" + TextFormatting.WHITE + TextFormatting.UNDERLINE + "Description");
						tooltip.add(keyblade.description);
						tooltip.add("");
					} else {
						tooltip.add("Hold " + TextFormatting.GREEN + TextFormatting.ITALIC + "Shift" + TextFormatting.GRAY + " for description");
					}
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
					if (event.getItemStack().hasTagCompound()) {
						tooltip.add("" + TextFormatting.WHITE + TextFormatting.UNDERLINE + "Stats");
						for (int i = 0; i < nbttaglist.tagCount(); i++) {
							int id = nbttaglist.getCompoundTagAt(i).getShort("id");
							int lvl = nbttaglist.getCompoundTagAt(i).getShort("lvl");

							if (Enchantment.getEnchantmentByID(id) != null) {
								tooltip.add(Enchantment.getEnchantmentByID(id).getTranslatedName(lvl));
							}
						}
					}

				} else {
					tooltip.add("Hold " + TextFormatting.YELLOW + TextFormatting.ITALIC + "Alt" + TextFormatting.GRAY + " for more stats");
				}
			}
		}
		// List<String> tooltip = event.getToolTip();

		if (event.getItemStack().getItem() instanceof IOrgWeapon) {
			IOrgWeapon weapon = (IOrgWeapon) event.getItemStack().getItem();
			(tooltip.subList(1, tooltip.size())).clear();

			NBTTagList nbttaglist = event.getItemStack().getEnchantmentTagList();
			double sharpnessDamage = 0;
			for (int i = 0; i < nbttaglist.tagCount(); i++) {
				int id = nbttaglist.getCompoundTagAt(i).getShort("id");
				int lvl = nbttaglist.getCompoundTagAt(i).getShort("lvl");

				// System.out.println(Enchantment.getEnchantmentByID(id).getName());
				if (Enchantment.getEnchantmentByID(id).getName().equals("enchantment.damage.all")) {
					sharpnessDamage = getSharpnessDamage(lvl);
				}
			}

			double keyStrength = weapon.getStrength() + sharpnessDamage;

			String magicSymbol = (weapon.getMagic() > 0) ? "+" : "";

			tooltip.add(TextFormatting.RED + "Strength: +" + keyStrength + " (" + (DamageCalculation.getOrgStrengthDamage(event.getEntityPlayer(), event.getItemStack()) + sharpnessDamage) + ")");
			tooltip.add(TextFormatting.BLUE + "Magic: " + magicSymbol + weapon.getMagic() + " (" + DamageCalculation.getMagicDamage(event.getEntityPlayer(), 1, weapon) + ")");
			if (weapon.getDescription() != null) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					tooltip.add("" + TextFormatting.WHITE + TextFormatting.UNDERLINE + "Description");
					tooltip.add(weapon.getDescription());
					tooltip.add("");
				} else {
					tooltip.add("Hold " + TextFormatting.GREEN + TextFormatting.ITALIC + "Shift" + TextFormatting.GRAY + " for description");
				}
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
				if (event.getItemStack().hasTagCompound()) {
					tooltip.add("" + TextFormatting.WHITE + TextFormatting.UNDERLINE + "Stats");
					for (int i = 0; i < nbttaglist.tagCount(); i++) {
						int id = nbttaglist.getCompoundTagAt(i).getShort("id");
						int lvl = nbttaglist.getCompoundTagAt(i).getShort("lvl");

						if (Enchantment.getEnchantmentByID(id) != null) {
							tooltip.add(Enchantment.getEnchantmentByID(id).getTranslatedName(lvl));
						}
					}
				}
				for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
					Multimap<String, AttributeModifier> multimap = event.getItemStack().getAttributeModifiers(entityequipmentslot);
					if (!multimap.isEmpty()) {
						tooltip.add("");
						for (Map.Entry<String, AttributeModifier> entry : multimap.entries()) {
							AttributeModifier attributemodifier = (AttributeModifier) entry.getValue();
							double d0 = attributemodifier.getAmount();
							boolean flag = false;

							if (attributemodifier.getID() == UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF")) {
								d0 = d0 + event.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
								d0 = d0 + (double) EnchantmentHelper.getModifierForCreature(event.getItemStack(), EnumCreatureAttribute.UNDEFINED);
								flag = true;
							}

							double d1;

							if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2) {
								d1 = d0;
							} else {
								d1 = d0 * 100.0D;
							}

							if (entry.getKey() == "generic.attackDamage") {
								d1 += event.getEntityPlayer().getCapability(ModCapabilities.PLAYER_STATS, null).getStrength();
							}
							if (flag) {
								tooltip.add(Utils.translateToLocalFormatted("attribute.modifier.equals." + attributemodifier.getOperation(), new Object[] { ItemStack.DECIMALFORMAT.format(d1), Utils.translateToLocal("attribute.name." + (String) entry.getKey()) }));
							} else if (d0 > 0.0D) {
								tooltip.add(TextFormatting.BLUE + Utils.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier.getOperation(), new Object[] { ItemStack.DECIMALFORMAT.format(d1), Utils.translateToLocal("attribute.name." + (String) entry.getKey()) }));
							} else if (d0 < 0.0D) {
								d1 = d1 * -1.0D;
								tooltip.add(TextFormatting.RED + Utils.translateToLocalFormatted("attribute.modifier.take." + attributemodifier.getOperation(), new Object[] { ItemStack.DECIMALFORMAT.format(d1), Utils.translateToLocal("attribute.name." + (String) entry.getKey()) }));
							}
						}
					}
				}
			} else {
				tooltip.add("Hold " + TextFormatting.YELLOW + TextFormatting.ITALIC + "Alt" + TextFormatting.GRAY + " for more stats");
			}
		}

		if (event.getItemStack().getItem() instanceof IOrgWeapon && event.getItemStack().getItem() != ModItems.DreamShield) {
			String member = ((IOrgWeapon) event.getItemStack().getItem()).getMember().toString();
			tooltip.add(member.substring(0, 1) + member.substring(1, member.length()).toLowerCase());
		}

		Item ghostBlox = Item.getItemFromBlock(ModBlocks.GhostBlox);
		if (event.getItemStack().getItem() == ghostBlox) {
			if (!KeyboardHelper.isShiftDown()) {
				event.getToolTip().add(TextFormatting.ITALIC + Utils.translateToLocal(Strings.HoldForInfo));
			} else {
				int x = 30;
				String s = Utils.translateToLocal(Strings.GhostBloxDesc).replace("%s", Utils.translateToLocal(ModBlocks.GhostBlox.getTranslationKey() + ".name"));
				s = s.replaceAll("(.{" + x + ",}?)\\s+", "$1\n");
				String[] splitS = s.split("\n");
				for (String element : splitS)
					event.getToolTip().add(element);
			}
		}
		Item dangerBlox = Item.getItemFromBlock(ModBlocks.DangerBlox);
		if (event.getItemStack().getItem() == dangerBlox) {
			if (!KeyboardHelper.isShiftDown()) {
				event.getToolTip().add(TextFormatting.ITALIC + Utils.translateToLocal(Strings.HoldForInfo));
			} else {
				int x = 30;
				String s = Utils.translateToLocal(Strings.DangerBloxDesc).replace("%s", Utils.translateToLocal(ModBlocks.DangerBlox.getTranslationKey() + ".name"));
				s = s.replaceAll("(.{" + x + ",}?)\\s+", "$1\n");
				String[] splitS = s.split("\n");
				for (String element : splitS)
					event.getToolTip().add(element);
			}
		}
		Item bounceBlox = Item.getItemFromBlock(ModBlocks.BounceBlox);
		if (event.getItemStack().getItem() == bounceBlox) {
			if (!KeyboardHelper.isShiftDown()) {
				event.getToolTip().add(TextFormatting.ITALIC + Utils.translateToLocal(Strings.HoldForInfo));
			} else {
				int x = 30;
				String s = Utils.translateToLocal(Strings.BounceBloxDesc).replace("%s", Utils.translateToLocal(ModBlocks.BounceBlox.getTranslationKey() + ".name"));
				s = s.replaceAll("(.{" + x + ",}?)\\s+", "$1\n");
				String[] splitS = s.split("\n");
				for (String element : splitS)
					event.getToolTip().add(element);
			}
		}
		Item magnetBlox = Item.getItemFromBlock(ModBlocks.MagnetBlox);
		if (event.getItemStack().getItem() == magnetBlox) {
			if (!KeyboardHelper.isShiftDown()) {
				event.getToolTip().add(TextFormatting.ITALIC + Utils.translateToLocal(Strings.HoldForInfo));
			} else {
				event.getToolTip().add("This Block is WIP and doesn't work at all.");
				event.getToolTip().add("It won't crash your game though.");
			}
		}
		Item kkchest = Item.getItemFromBlock(ModBlocks.KKChest);
		if (event.getItemStack().getItem() == kkchest) {
			// event.getToolTip().add(Utils.translateToLocal(Strings.KKChestDesc_1));
			if (!KeyboardHelper.isShiftDown())
				event.getToolTip().add(TextFormatting.ITALIC + Utils.translateToLocal(Strings.HoldForInfo));
			else
				event.getToolTip().add(Utils.translateToLocal(Strings.KKChestDesc_2));
		}

		Item savepoint = Item.getItemFromBlock(ModBlocks.SavePoint);
		if (event.getItemStack().getItem() == savepoint)
			if (!KeyboardHelper.isShiftDown())
				event.getToolTip().add(TextFormatting.ITALIC + Utils.translateToLocal(Strings.HoldForInfo));
			else
				event.getToolTip().add(Utils.translateToLocal(Strings.SavePointDesc));

	}

	private double getSharpnessDamage(int lvl) {
		return lvl / 2 + 0.5;
	}

	@SubscribeEvent
	public void onItemTossEvent(ItemTossEvent event) {
		if (!event.getPlayer().world.isRemote) {
			if (event.getPlayer().getCapability(ModCapabilities.DRIVE_STATE, null).getInDrive() && !event.getPlayer().getCapability(ModCapabilities.DRIVE_STATE, null).getActiveDriveName().equals(Strings.Form_Anti)) {
				event.setCanceled(true);
				return;
			}

			if (event.getEntityItem().getItem().getItem() instanceof ItemKeyblade && (event.getEntityItem().getItem().getItem() != ModItems.WoodenKeyblade && event.getEntityItem().getItem().getItem() != ModItems.WoodenStick && event.getEntityItem().getItem().getItem() != ModItems.DreamSword && event.getEntityItem().getItem().getItem() != ModItems.DreamStaff)) {
				event.getEntityItem().isDead = true;
				event.getPlayer().getCapability(ModCapabilities.SUMMON_KEYBLADE, null).setIsKeybladeSummoned(EnumHand.MAIN_HAND, false);
				if (!ItemStack.areItemStacksEqual(event.getPlayer().getCapability(ModCapabilities.SUMMON_KEYBLADE, null).getInventoryKeychain().getStackInSlot(0), ItemStack.EMPTY)) {
					PacketDispatcher.sendTo(new SyncKeybladeData(event.getPlayer().getCapability(ModCapabilities.SUMMON_KEYBLADE, null)), (EntityPlayerMP) event.getPlayer());
				}

			} else if (event.getEntityItem().getItem().getItem() instanceof ItemMunny) {
				event.setCanceled(true);
				if (!event.getPlayer().world.isRemote) {
					PacketDispatcher.sendTo(new ShowOverlayPacket("munny", event.getEntityItem().getItem().getTagCompound().getInteger("amount")), (EntityPlayerMP) event.getPlayer());

					event.getPlayer().getCapability(ModCapabilities.MUNNY, null).addMunny(event.getEntityItem().getItem().getTagCompound().getInteger("amount"));
				}
			}
			if (event.getPlayer().getCapability(ModCapabilities.ORGANIZATION_XIII, null).getMember() != Utils.OrgMember.NONE) {
				if (event.getEntityItem().getItem().getItem() == event.getPlayer().getCapability(ModCapabilities.ORGANIZATION_XIII, null).currentWeapon()) {
					event.getEntityItem().isDead = true;
					event.getPlayer().getCapability(ModCapabilities.ORGANIZATION_XIII, null).setWeaponSummoned(EnumHand.MAIN_HAND, false);
					PacketDispatcher.sendTo(new SyncOrgXIIIData(event.getPlayer().getCapability(ModCapabilities.ORGANIZATION_XIII, null)), (EntityPlayerMP) event.getPlayer());
				}
			}
		}
	}

	@SubscribeEvent
	public void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {

	}

}
