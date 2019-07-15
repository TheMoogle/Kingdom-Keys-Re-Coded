package uk.co.wehavecookies56.kk.common.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetNBT;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import uk.co.wehavecookies56.kk.common.item.ModItems;
import uk.co.wehavecookies56.kk.common.lib.Reference;
import uk.co.wehavecookies56.kk.common.lib.Strings;

public class ChestGen {
    @SubscribeEvent
    public void loadLoot(LootTableLoadEvent event) {
        NBTTagCompound MythrilCrystal = new NBTTagCompound();
        MythrilCrystal.setString("material", Strings.SM_MythrilCrystal);
        MythrilCrystal.setString("rank", Strings.SM_Rank_S);
        LootFunction[] setMythrilCrystal = new LootFunction[] {new SetNBT(new LootCondition[0], MythrilCrystal), new SetCount(new LootCondition[0], new RandomValueRange(1, 5))};

        NBTTagCompound MythrilGem = new NBTTagCompound();
        MythrilGem.setString("material", Strings.SM_MythrilGem);
        MythrilGem.setString("rank", Strings.SM_Rank_A);
        LootFunction[] setMythrilGem = new LootFunction[] {new SetNBT(new LootCondition[0], MythrilGem), new SetCount(new LootCondition[0], new RandomValueRange(1, 5))};

        NBTTagCompound MythrilStone = new NBTTagCompound();
        MythrilStone.setString("material", Strings.SM_MythrilStone);
        MythrilStone.setString("rank", Strings.SM_Rank_B);
        LootFunction[] setMythrilStone = new LootFunction[] {new SetNBT(new LootCondition[0], MythrilStone), new SetCount(new LootCondition[0], new RandomValueRange(1, 5))};

        NBTTagCompound MythrilShard = new NBTTagCompound();
        MythrilShard.setString("material", Strings.SM_MythrilShard);
        MythrilShard.setString("rank", Strings.SM_Rank_C);
        LootFunction[] setMythrilShard = new LootFunction[] {new SetNBT(new LootCondition[0], MythrilShard), new SetCount(new LootCondition[0], new RandomValueRange(1, 5))};

        NBTTagCompound Orichalcum = new NBTTagCompound();
        Orichalcum.setString("material", Strings.SM_Orichalcum);
        Orichalcum.setString("rank", Strings.SM_Rank_A);
        LootFunction[] setOrichalcum = new LootFunction[] {new SetNBT(new LootCondition[0], Orichalcum), new SetCount(new LootCondition[0], new RandomValueRange(1, 5))};

        NBTTagCompound OrichalcumPlus = new NBTTagCompound();
        OrichalcumPlus.setString("material", Strings.SM_OrichalcumPlus);
        OrichalcumPlus.setString("rank", Strings.SM_Rank_S);
        LootFunction[] setOrichalcumPlus = new LootFunction[] {new SetNBT(new LootCondition[0], OrichalcumPlus), new SetCount(new LootCondition[0], new RandomValueRange(1, 5))};

        NBTTagCompound LostIllusion = new NBTTagCompound();
        LostIllusion.setString("material", Strings.SM_LostIllusion);
        LostIllusion.setString("rank", Strings.SM_Rank_S);
        LootFunction[] setLostIllusion = new LootFunction[] {new SetNBT(new LootCondition[0], LostIllusion), new SetCount(new LootCondition[0], new RandomValueRange(1, 5))};

        NBTTagCompound ManifestIllusion = new NBTTagCompound();
        ManifestIllusion.setString("material", Strings.SM_ManifestIllusion);
        ManifestIllusion.setString("rank", Strings.SM_Rank_A);
        LootFunction[] setManifestIllusion = new LootFunction[] {new SetNBT(new LootCondition[0], ManifestIllusion), new SetCount(new LootCondition[0], new RandomValueRange(1, 5))};

        if (event.getName().getPath().contains("chests")) {
            LootFunction[] setSingleStack = {new SetCount(new LootCondition[0], new RandomValueRange(1, 1))};
            LootEntry[] materialEntries = {
                    new LootEntryItem(ModItems.SynthesisMaterial, 5, 0, setMythrilCrystal, new LootCondition[0], Reference.MODID + ":" + ModItems.SynthesisMaterial.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.SynthesisMaterial, 10, 0, setMythrilGem, new LootCondition[0], Reference.MODID + ":" + ModItems.SynthesisMaterial.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.SynthesisMaterial, 20, 0, setMythrilStone, new LootCondition[0], Reference.MODID + ":" + ModItems.SynthesisMaterial.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.SynthesisMaterial, 30, 0, setMythrilShard, new LootCondition[0], Reference.MODID + ":" + ModItems.SynthesisMaterial.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.SynthesisMaterial, 10, 0, setOrichalcum, new LootCondition[0], Reference.MODID + ":" + ModItems.SynthesisMaterial.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.SynthesisMaterial, 5, 0, setOrichalcumPlus, new LootCondition[0], Reference.MODID + ":" + ModItems.SynthesisMaterial.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.SynthesisMaterial, 5, 0, setLostIllusion, new LootCondition[0], Reference.MODID + ":" + ModItems.SynthesisMaterial.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.SynthesisMaterial, 10, 0, setManifestIllusion, new LootCondition[0], Reference.MODID + ":" + ModItems.SynthesisMaterial.getTranslationKey().substring(5))
            };
            LootEntry[] musicDiscEntries = {
                    new LootEntryItem(ModItems.Disc_Birth_by_Sleep_A_Link_to_the_Future, 5, 0, setSingleStack, new LootCondition[0], Reference.MODID + ":" + ModItems.Disc_Birth_by_Sleep_A_Link_to_the_Future.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.Disc_Darkness_of_the_Unknown, 5, 0, setSingleStack, new LootCondition[0], Reference.MODID + ":" + ModItems.Disc_Darkness_of_the_Unknown.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.Disc_Dearly_Beloved_Symphony_Version, 5, 0, setSingleStack, new LootCondition[0], Reference.MODID + ":" + ModItems.Disc_Dearly_Beloved_Symphony_Version.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.Disc_Dream_Drop_Distance_The_Next_Awakening, 5, 0, setSingleStack, new LootCondition[0], Reference.MODID + ":" + ModItems.Disc_Dream_Drop_Distance_The_Next_Awakening.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.Disc_Hikari_KINGDOM_Instrumental_Version, 5, 0, setSingleStack, new LootCondition[0], Reference.MODID + ":" + ModItems.Disc_Hikari_KINGDOM_Instrumental_Version.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.Disc_L_Oscurita_Dell_Ignoto, 5, 0, setSingleStack, new LootCondition[0], Reference.MODID + ":" + ModItems.Disc_L_Oscurita_Dell_Ignoto.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.Disc_Musique_pour_la_tristesse_de_Xion, 5, 0, setSingleStack, new LootCondition[0], Reference.MODID + ":" + ModItems.Disc_Musique_pour_la_tristesse_de_Xion.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.Disc_No_More_Bugs_Bug_Version, 5, 0, setSingleStack, new LootCondition[0], Reference.MODID + ":" + ModItems.Disc_No_More_Bugs_Bug_Version.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.Disc_Organization_XIII, 5, 0, setSingleStack, new LootCondition[0], Reference.MODID + ":" + ModItems.Disc_Organization_XIII.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.Disc_Sanctuary, 5, 0, setSingleStack, new LootCondition[0], Reference.MODID + ":" + ModItems.Disc_Sanctuary.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.Disc_Simple_And_Clean_PLANITb_Remix, 5, 0, setSingleStack, new LootCondition[0], Reference.MODID + ":" + ModItems.Disc_Simple_And_Clean_PLANITb_Remix.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.Disc_Sinister_Sundown, 5, 0, setSingleStack, new LootCondition[0], Reference.MODID + ":" + ModItems.Disc_Sinister_Sundown.getTranslationKey().substring(5)),
                    new LootEntryItem(ModItems.Disc_The_13th_Anthology, 5, 0, setSingleStack, new LootCondition[0], Reference.MODID + ":" + ModItems.Disc_The_13th_Anthology.getTranslationKey().substring(5)),

            };
            String name = event.getName().getPath();
            switch (name) {
                case "spawn_bonus_chest":
                    event.getTable().getPool("main").addEntry(new LootEntryItem(ModItems.Chain_KingdomKey, 5, 10, setSingleStack, new LootCondition[0], Reference.MODID + ":" + ModItems.Chain_KingdomKey.getTranslationKey().substring(5)));
                    break;
                case "simple_dungeon":
                    LootPool musicDiscPool = new LootPool(musicDiscEntries, new LootCondition[0], new RandomValueRange(1, 2), new RandomValueRange(0), "kk_loot_music_discs");
                    event.getTable().addPool(musicDiscPool);
                    break;
                case "end_city_treasure":
                    LootEntry[] endCityEntries = {
                            new LootEntryItem(ModItems.LevelUpMagicThunder, 30, 1, new LootFunction[0], new LootCondition[0], Reference.MODID + ":" + ModItems.LevelUpMagicThunder.getTranslationKey().substring(5)),
                            new LootEntryEmpty(70, 1, new LootCondition[0], Reference.MODID + ":" + "end_city_empty")
                    };
                    LootPool endCityPool = new LootPool(endCityEntries, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0), "kk_end_city");
                    event.getTable().addPool(endCityPool);
                    break;
                case "igloo_chest":
                    LootEntry[] iglooEntries = {
                            new LootEntryItem(ModItems.LevelUpMagicBlizzard, 30, 1, new LootFunction[0], new LootCondition[0], Reference.MODID + ":" + ModItems.LevelUpMagicBlizzard.getTranslationKey().substring(5)),
                            new LootEntryEmpty(70, 1, new LootCondition[0], Reference.MODID + ":" + "igloo_empty")
                    };
                    LootPool iglooPool = new LootPool(iglooEntries, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0), "kk_igloo");
                    event.getTable().addPool(iglooPool);
                    break;
                case "nether_bridge":
                    LootEntry[] netherFortressEntries = {
                            new LootEntryItem(ModItems.LevelUpMagicFire, 30, 1, new LootFunction[0], new LootCondition[0], Reference.MODID + ":" + ModItems.LevelUpMagicFire.getTranslationKey().substring(5)),
                            new LootEntryEmpty(70, 1, new LootCondition[0], Reference.MODID + ":" + "nether_fortress_empty")
                    };
                    LootPool netherFortressPool = new LootPool(netherFortressEntries, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0), "kk_nether_fortress");
                    event.getTable().addPool(netherFortressPool);
                    break;
            }
            try {
                LootPool main = event.getTable().getPool("main");
                LootEntry[] recipeEntry = {
                        new LootEntryItem(ModItems.Recipe, 30, 1, new LootFunction[0], new LootCondition[0], Reference.MODID + ":" + ModItems.Recipe.getTranslationKey().substring(5)),
                        new LootEntryEmpty(70, 1, new LootCondition[0], Reference.MODID + ":" + "recipes_empty")

                };
                main.addEntry(recipeEntry[0]);
                main.addEntry(recipeEntry[1]);
            } catch (NullPointerException e) {
                LootEntry[] recipeEntry = {
                        new LootEntryItem(ModItems.Recipe, 30, 1, new LootFunction[0], new LootCondition[0], Reference.MODID + ":" + ModItems.Recipe.getTranslationKey().substring(5)),
                        new LootEntryEmpty(70, 1, new LootCondition[0], Reference.MODID + ":" + "recipes_empty")

                };
                LootPool main = new LootPool(recipeEntry, new LootCondition[0], new RandomValueRange(1, 2), new RandomValueRange(0, 0), "kk_recipes");
            }
            LootPool materialPool = new LootPool(materialEntries, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0), "kk_loot_rare_materials");
            event.getTable().addPool(materialPool);
        }
    }

}
