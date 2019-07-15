package uk.co.wehavecookies56.kk.common.synthesis.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import uk.co.wehavecookies56.kk.api.materials.Material;
import uk.co.wehavecookies56.kk.api.materials.MaterialRegistry;
import uk.co.wehavecookies56.kk.api.recipes.Recipe;
import uk.co.wehavecookies56.kk.common.block.ModBlocks;
import uk.co.wehavecookies56.kk.common.item.ModItems;
import uk.co.wehavecookies56.kk.common.lib.Strings;

public class RecipeZeroOne extends Recipe {

    public String name;

    public RecipeZeroOne (String name) {
        this.name = name;
    }

    @Override
    public String getName () {
        return name;
    }

    @Override
    public ItemStack getResult () {
        return new ItemStack(ModItems.Chain_ZeroOne);
    }

    @Override
    public Map<Material, Integer> getRequirements () {
        Map<Material, Integer> reqs = new HashMap<Material, Integer>();
        reqs.put(MaterialRegistry.get(Strings.SM_PowerCrystal), 2);
        reqs.put(MaterialRegistry.get(Strings.SM_BrightGem), 3);
        reqs.put(MaterialRegistry.get(ModBlocks.NormalBlox.getTranslationKey()), 1);
        reqs.put(MaterialRegistry.get(ModBlocks.HardBlox.getTranslationKey()), 1);
        reqs.put(MaterialRegistry.get(ModBlocks.MetalBlox.getTranslationKey()), 1);
        return reqs;
    }

}
