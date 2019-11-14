package de.Ste3et_C0st.Furniture.Main;

import java.lang.reflect.Field;
import java.util.Objects;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import de.Ste3et_C0st.Furniture.Objects.RPG.Catapult;
import de.Ste3et_C0st.Furniture.Objects.RPG.Crossbow;
import de.Ste3et_C0st.Furniture.Objects.RPG.Guillotine;
import de.Ste3et_C0st.Furniture.Objects.RPG.flag;
import de.Ste3et_C0st.Furniture.Objects.RPG.weaponStand;
import de.Ste3et_C0st.Furniture.Objects.School.TrashCan;
import de.Ste3et_C0st.Furniture.Objects.christmas.AdventCalender;
import de.Ste3et_C0st.Furniture.Objects.christmas.FireworkLauncher;
import de.Ste3et_C0st.Furniture.Objects.electric.billboard;
import de.Ste3et_C0st.Furniture.Objects.electric.camera;
import de.Ste3et_C0st.Furniture.Objects.electric.streetlamp;
import de.Ste3et_C0st.Furniture.Objects.garden.TFlowerPot;
import de.Ste3et_C0st.Furniture.Objects.garden.Trunk;
import de.Ste3et_C0st.Furniture.Objects.garden.fance;
import de.Ste3et_C0st.Furniture.Objects.garden.graveStone;
import de.Ste3et_C0st.Furniture.Objects.garden.log;
import de.Ste3et_C0st.Furniture.Objects.garden.sunshade;
import de.Ste3et_C0st.Furniture.Objects.indoor.largeTable;
import de.Ste3et_C0st.Furniture.Objects.light.WaxCandle;
import de.Ste3et_C0st.Furniture.Objects.outdoor.barrels;
import de.Ste3et_C0st.Furniture.Objects.outdoor.campfire_1;
import de.Ste3et_C0st.Furniture.Objects.outdoor.campfire_2;
import de.Ste3et_C0st.Furniture.Objects.outdoor.hammock;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_1;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_2;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_3;
import de.Ste3et_C0st.Furniture.Objects.trap.BearTrap;
import de.Ste3et_C0st.FurnitureLib.Crafting.Project;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurniturePlugin;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.CenterType;
import de.Ste3et_C0st.FurnitureLib.main.Type.PlaceableSide;

public class FurnitureHook extends FurniturePlugin{

	public FurnitureHook(Plugin pluginInstance) {
		super(pluginInstance);
	}

	@Override
	public void registerProjects() {
		try {
			String modelFolder = FurnitureHook.isNewVersion() ? "Models113/" : "Models109/";
			String ending = FurnitureHook.isNewVersion() ? ".dModel" : ".yml";
			
			new Project("Catapult", getPlugin(), getResource(modelFolder + "Catapult" + ending), Catapult.class).setSize(3, 2, 3, CenterType.RIGHT).setEditorProject(false);
			new Project("HumanSkeleton", getPlugin(), getResource(modelFolder + "HumanSkeleton" + ending)).setSize(3, 1, 2, CenterType.RIGHT).setEditorProject(false);
			new Project("CandyCane", getPlugin(), getResource(modelFolder + "CandyCane" + ending)).setSize(3, 4, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("SnowGolem", getPlugin(), getResource(modelFolder + "SnowGolem" + ending)).setSize(1, 2, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("TV", getPlugin(), getResource(modelFolder + "TV" + ending)).setSize(1, 2, 3, CenterType.CENTER).setEditorProject(false);
			new Project("Chair", getPlugin(), getResource(modelFolder + "Chair" + ending)).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("CampChair", getPlugin(), getResource(modelFolder + "CampChair" + ending)).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("CactusPlant", getPlugin(), getResource(modelFolder + "CactusPlant" + ending)).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("SleepingBag", getPlugin(), getResource(modelFolder + "SleepingBag" + ending)).setSize(1, 1, 2, CenterType.RIGHT).setEditorProject(false);
			new Project("ChristmasTree", getPlugin(), getResource(modelFolder + "ChristmasTree" + ending)).setSize(1, 1, 2, CenterType.RIGHT).setEditorProject(false);
			new Project("Table", getPlugin(), getResource(modelFolder + "Table" + ending)).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("SchoolChair", getPlugin(), getResource(modelFolder + "SchoolChair" + ending)).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("SchoolTable", getPlugin(), getResource(modelFolder + "SchoolTable" + ending)).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("BlackBoard", getPlugin(), getResource(modelFolder + "BlackBoard" + ending)).setPlaceableSide(PlaceableSide.SIDE).setSize(1, 2, 3, CenterType.RIGHT).setEditorProject(false);
			new Project("Barrels", getPlugin(), getResource(modelFolder + "Barrels" + ending), barrels.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("WaxCandle", getPlugin(), getResource(modelFolder + "WaxCandle" + ending), WaxCandle.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Lantern", getPlugin(), getResource(modelFolder + "Lantern" + ending)).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("MailBox", getPlugin(), getResource(modelFolder + "MailBox" + ending)).setSize(1, 2, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Fence", getPlugin(), getResource(modelFolder + "Fence" + ending), fance.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Trunk", getPlugin(), getResource(modelFolder + "Trunk" + ending), Trunk.class).setSize(1, 1, 4, CenterType.RIGHT).setEditorProject(false);
			new Project("Sunshade", getPlugin(), getResource(modelFolder + "Sunshade" + ending), sunshade.class).setSize(1, 3, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Hammock", getPlugin(), getResource(modelFolder + "Hammock" + ending), hammock.class).setSize(1, 2, 7, CenterType.RIGHT).setEditorProject(false);
			new Project("Crossbow", getPlugin(), getResource(modelFolder + "Crossbow" + ending), Crossbow.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Tent1", getPlugin(),getResource(modelFolder + "Tent1" + ending), tent_1.class).setSize(4, 3, 5, CenterType.RIGHT).setEditorProject(false);
			new Project("GraveStone", getPlugin(),getResource(modelFolder + "GraveStone" + ending), graveStone.class).setSize(1, 2, 3, CenterType.CENTER).setEditorProject(false);
			new Project("Camera", getPlugin(), getResource(modelFolder + "Camera" + ending), camera.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Sofa", getPlugin(), getResource(modelFolder + "Sofa" + ending)).setSize(1, 1, 3, CenterType.RIGHT).setEditorProject(false);
			new Project("Log", getPlugin(), getResource(modelFolder + "Log" + ending), log.class).setSize(1, 1, 1, CenterType.CENTER).setEditorProject(false);
			new Project("LargeTable", getPlugin(),getResource(modelFolder + "LargeTable" + ending),PlaceableSide.TOP, largeTable.class).setSize(2, 1, 2, CenterType.RIGHT).setEditorProject(false);
	        new Project("Campfire1", getPlugin(),getResource(modelFolder + "Campfire1" + ending),PlaceableSide.TOP, campfire_1.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Campfire2", getPlugin(),getResource(modelFolder + "Campfire2" + ending),PlaceableSide.TOP, campfire_2.class).setSize(2, 1, 2, CenterType.RIGHT).setEditorProject(false);
			new Project("Tent2", getPlugin(),getResource(modelFolder + "Tent2" + ending),PlaceableSide.TOP, tent_2.class).setSize(6, 3, 5, CenterType.RIGHT).setEditorProject(false);
			new Project("Tent3", getPlugin(),getResource(modelFolder + "Tent3" + ending),PlaceableSide.TOP, tent_3.class).setSize(3, 2, 3, CenterType.CENTER).setEditorProject(false);
			new Project("Streetlamp", getPlugin(), getResource(modelFolder + "Streetlamp" + ending),PlaceableSide.TOP, streetlamp.class).setSize(2, 4, 1, CenterType.FRONT).setEditorProject(false);
			new Project("Billboard", getPlugin(), getResource(modelFolder + "Billboard" + ending),PlaceableSide.TOP, billboard.class).setSize(1, 3, 3, CenterType.RIGHT).setEditorProject(false);
			new Project("WeaponStand", getPlugin(), getResource(modelFolder + "WeaponStand" + ending),PlaceableSide.TOP, weaponStand.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Guillotine", getPlugin(), getResource(modelFolder + "Guillotine" + ending),PlaceableSide.TOP, Guillotine.class).setSize(1, 5, 2, CenterType.RIGHT).setEditorProject(false);
			new Project("FlowerPot", getPlugin(), getResource(modelFolder + "FlowerPot" + ending),PlaceableSide.BOTTOM, TFlowerPot.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("BearTrap", getPlugin(), getResource(modelFolder + "BearTrap" + ending), PlaceableSide.TOP, BearTrap.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("TrashCan", getPlugin(), getResource(modelFolder + "TrashCan" + ending), PlaceableSide.TOP, TrashCan.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Flag", getPlugin(), getResource(modelFolder + "Flag" + ending), PlaceableSide.TOP, flag.class).setSize(1, 3, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("AdventCalender", getPlugin(), getResource(modelFolder + "AdventCalender" + ending), PlaceableSide.TOP, AdventCalender.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("FireworkLauncher", getPlugin(), getResource(modelFolder + "FireworkLauncher" + ending), PlaceableSide.TOP, FireworkLauncher.class).setSize(1, 1, 1, CenterType.CENTER).setEditorProject(false);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void applyPluginFunctions() {
		FurnitureLib.getInstance().getFurnitureManager().getProjects().stream()
		.filter(pro -> pro.getPlugin().getName().equals(getPlugin().getDescription().getName()))
		.forEach(Project::applyFunction);
	}

	@Override
	public void onFurnitureLateSpawn(ObjectID obj) {
		
	}
	
	private static Boolean newVersion = null;
	public static boolean isNewVersion() {
		if(Objects.isNull(newVersion)) {
			try {
				Class<?> descriptionClass = PluginDescriptionFile.class;
				Field field = descriptionClass.getDeclaredField("apiVersion");
				boolean bool = Objects.nonNull(field);
				newVersion = bool;
				return bool;
			}catch (Exception e) {
				newVersion = false;
				return false;
			}
		}else {
			return newVersion;
		}
	}

}
