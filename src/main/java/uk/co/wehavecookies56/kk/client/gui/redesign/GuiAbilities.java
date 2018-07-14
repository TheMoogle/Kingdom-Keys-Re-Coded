package uk.co.wehavecookies56.kk.client.gui.redesign;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.kk.api.abilities.Ability;
import uk.co.wehavecookies56.kk.api.abilities.AbilityEvent;
import uk.co.wehavecookies56.kk.client.gui.GuiMenu_Bars;
import uk.co.wehavecookies56.kk.common.ability.ModAbilities;
import uk.co.wehavecookies56.kk.common.capability.AbilitiesCapability.IAbilities;
import uk.co.wehavecookies56.kk.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.kk.common.network.packet.client.SyncAbilities;
import uk.co.wehavecookies56.kk.common.capability.ModCapabilities;

import java.io.IOException;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class GuiAbilities extends GuiScreen {

	GuiMenu_Bars background;

	// GuiMenuButton test, test2;

	public GuiAbilities() {
		mc = Minecraft.getMinecraft();
		this.background = new GuiMenu_Bars("Abilities");
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		background.drawBars();
		background.drawBiomeDim();
		background.drawMunnyTime();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void initGui() {
		background.width = width;
		background.height = height;
		background.init();

		int id = 0;
		IAbilities ABILITIES = mc.player.getCapability(ModCapabilities.ABILITIES, null);

		for (int i = 0; i < ABILITIES.getUnlockedAbilities().size(); i++) {
			Ability ability = ABILITIES.getUnlockedAbilities().get(i);
			String text = "";
			if (ABILITIES.getEquippedAbility(ability)) {
				text = "Equip " + ability.getName()+" ["+ability.getCategory()+"]";
			} else {
				text = "Unequip " + ability.getName()+" ["+ability.getCategory()+"]";
			}
			buttonList.add(new GuiMenuButton(id++, 0, id * 20, 100, text));
		}

		super.initGui();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		IAbilities ABILITIES = mc.player.getCapability(ModCapabilities.ABILITIES, null);
		for (int i = 0; i < ABILITIES.getUnlockedAbilities().size(); i++) {
			Ability ability = ABILITIES.getUnlockedAbilities().get(i);
			if (ABILITIES.getEquippedAbility(ability)) {
				MinecraftForge.EVENT_BUS.post(new AbilityEvent.Unequip(mc.player, ability));
				ABILITIES.equipAbility(ability, false);
			} else {
				MinecraftForge.EVENT_BUS.post(new AbilityEvent.Equip(mc.player, ability));
				ABILITIES.equipAbility(ability, true);
			}
			updateScreen();
		}

		super.actionPerformed(button);
	}
}
