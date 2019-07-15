package uk.co.wehavecookies56.kk.common.synthesis.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import uk.co.wehavecookies56.kk.api.materials.Material;
import uk.co.wehavecookies56.kk.api.materials.MaterialRegistry;
import uk.co.wehavecookies56.kk.api.recipes.Recipe;
import uk.co.wehavecookies56.kk.common.item.ModItems;

public class RecipeCombinedKeyblade extends Recipe {

    public String name;

    public RecipeCombinedKeyblade (String name) {
        this.name = name;
    }

    @Override
    public String getName () {
        return name;
    }

    @Override
    public ItemStack getResult () {
        return new ItemStack(ModItems.Chain_NightmaresEndandMirageSplit);
    }

    @Override
    public Map<Material, Integer> getRequirements () {
        Map<Material, Integer> reqs = new HashMap<Material, Integer>();
        reqs.put(MaterialRegistry.get(ModItems.Chain_NightmaresEnd.getTranslationKey()), 1);
        reqs.put(MaterialRegistry.get(ModItems.Chain_MirageSplit.getTranslationKey()), 1);
        return reqs;
    }

}
