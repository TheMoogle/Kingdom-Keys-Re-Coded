package uk.co.wehavecookies56.kk.common.lib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import uk.co.wehavecookies56.kk.common.KingdomKeys;
import uk.co.wehavecookies56.kk.common.core.handler.SpawningConfig;
import uk.co.wehavecookies56.kk.common.entity.mobs.*;

public class Spawnings {
	public static void init() {
		Iterator<Biome> biomeRegistry = Biome.REGISTRY.iterator();
		List<Biome> heartlessBiomes = new ArrayList<>();
		List<Biome> nobodyBiomes = new ArrayList<>();
		List<Biome> othersBiomes = new ArrayList<>();
		
		while (biomeRegistry.hasNext()) { //Get all the biomes strings
			Biome biome = biomeRegistry.next();
			if (biome.getSpawnableList(KingdomKeys.HEARTLESS) != null && biome.getSpawnableList(KingdomKeys.NOBODY) != null && biome.getSpawnableList(KingdomKeys.MOOGLE) != null) {
				heartlessBiomes.add(biome);
			 	nobodyBiomes.add(biome);
			 	othersBiomes.add(biome);
			}
		}
		
		for (String b : SpawningConfig.heartless.heartlessBiomeExclusion) { //Check for banned biomes
			if (Biome.REGISTRY.containsKey(new ResourceLocation(b))) {
				KingdomKeys.logger.info("Removed heartless spawns for biome " + b);
				heartlessBiomes.remove(Biome.REGISTRY.getObject(new ResourceLocation(b)));
			}
		}
		
		for (String b : SpawningConfig.nobodies.nobodiesBiomeExclusion) { //Check for banned biomes
			if (Biome.REGISTRY.containsKey(new ResourceLocation(b))) {
				KingdomKeys.logger.info("Removed nobodies spawns for biome " + b);
				nobodyBiomes.remove(Biome.REGISTRY.getObject(new ResourceLocation(b)));
			}
		}
		
		for (String b : SpawningConfig.others.othersBiomeExclusion) { //Check for banned biomes
			if (Biome.REGISTRY.containsKey(new ResourceLocation(b))) {
				KingdomKeys.logger.info("Removed other mob spawns for biome " + b);
				othersBiomes.remove(Biome.REGISTRY.getObject(new ResourceLocation(b)));
			}
		}
		
		Biome[] heartlessBiomesArray = heartlessBiomes.toArray(new Biome[heartlessBiomes.size()]);
		Biome[] nobodyBiomesArray = nobodyBiomes.toArray(new Biome[nobodyBiomes.size()]);
		Biome[] othersBiomesArray = othersBiomes.toArray(new Biome[othersBiomes.size()]);

		if (SpawningConfig.spawnHeartless) {
			// Pureblood
			registerSpawn(EntityShadow.class, SpawningConfig.heartless.shadowRatio, 3, 5, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			//registerSpawn(EntityMegaShadow.class, SpawningConfig.heartless.megaShadowRatio, 2, 3, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			//registerSpawn(EntityGigaShadow.class, SpawningConfig.heartless.gigaShadowRatio, 1, 1, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			registerSpawn(EntityDarkball.class, SpawningConfig.heartless.darkballRatio, 1, 2, KingdomKeys.HEARTLESS, heartlessBiomesArray);

			// Emblem
			registerSpawn(EntityRedNocturne.class, SpawningConfig.heartless.redNocturneRatio, 1, 2, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			registerSpawn(EntityBlueRhapsody.class, SpawningConfig.heartless.blueRhapsodyRatio, 1, 2, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			registerSpawn(EntityYellowOpera.class, SpawningConfig.heartless.yellowOperaRatio, 1, 2, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			registerSpawn(EntityGreenRequiem.class, SpawningConfig.heartless.greenRequiemRatio, 1, 2, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			//registerSpawn(EntityEmeraldBlues.class, SpawningConfig.heartless.emeraldBluesRatio, 1, 1, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			//registerSpawn(EntitySilverRock.class, SpawningConfig.heartless.silverRockRatio, 1, 1, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			//registerSpawn(EntityCrimsonJazz.class, SpawningConfig.heartless.crimsonJazzRatio, 1, 1, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			//registerSpawn(EntityMinuteBomb.class, SpawningConfig.heartless.minuteBombRatio, 2, 4, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			//registerSpawn(EntitySkaterBomb.class, SpawningConfig.heartless.skaterBombRatio, 1, 3, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			//registerSpawn(EntityStormBomb.class, SpawningConfig.heartless.stormBombRatio, 1, 2, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			//registerSpawn(EntityDetonator.class, SpawningConfig.heartless.detonatorRatio, 1, 1, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			registerSpawn(EntityDirePlant.class, SpawningConfig.heartless.direPlantRatio, 3, 5, KingdomKeys.HEARTLESS, heartlessBiomesArray);
			registerSpawn(EntityLargeBody.class, SpawningConfig.heartless.largeBodyRatio, 1, 2, KingdomKeys.HEARTLESS, heartlessBiomesArray);

		}
		//Nobody
		if (SpawningConfig.spawnNobodies) {
			registerSpawn(EntityCreeper.class, SpawningConfig.nobodies.creeperRatio, 2, 5, KingdomKeys.NOBODY, nobodyBiomesArray);
		}

		// Others
		if (SpawningConfig.spawnMoogles) {
			registerSpawn(EntityMoogle.class, SpawningConfig.others.moogleRatio, 1, 1, KingdomKeys.MOOGLE, othersBiomesArray);
			registerSpawn(EntityOrgMoogle.class, SpawningConfig.others.moogleRatio, 1, 1, KingdomKeys.MOOGLE, othersBiomesArray);
		}
		KingdomKeys.logger.info("Entity spawning loaded");

	}

	public static void registerSpawn(Class<? extends EntityLiving> entityClass, int configRatio, int min, int max, EnumCreatureType type, Biome... biomes) {
		if (configRatio != 0)
			EntityRegistry.addSpawn(entityClass, configRatio, min, max, type, biomes);
	}
}
