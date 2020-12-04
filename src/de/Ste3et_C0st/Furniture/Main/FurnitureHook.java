package de.Ste3et_C0st.Furniture.Main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import de.Ste3et_C0st.Furniture.Main.Event.redstoneEvent;
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

	private final boolean editModels = FurnitureLib.getInstance().getConfig().getBoolean("config.editDiceFurnitureModels", false);
	
	public FurnitureHook(Plugin pluginInstance) {
		super(pluginInstance);
		Bukkit.getPluginManager().registerEvents(new redstoneEvent(), pluginInstance);
	}

	@Override
	public void registerProjects() {
		try {
			String modelFolder = FurnitureHook.isNewVersion() ? "Models113/" : "Models109/";
			String ending = FurnitureHook.isNewVersion() ? ".dModel" : ".yml";
			new Project("HumanSkeleton", getPlugin(), getResource(modelFolder + "HumanSkeleton" + ending)).setSize(3, 1, 2, CenterType.RIGHT);
			new Project("CandyCane", getPlugin(), getResource(modelFolder + "CandyCane" + ending)).setSize(3, 4, 1, CenterType.RIGHT);
			new Project("SnowGolem", getPlugin(), getResource(modelFolder + "SnowGolem" + ending)).setSize(1, 2, 1, CenterType.RIGHT);
			new Project("TV", getPlugin(), getResource(modelFolder + "TV" + ending)).setSize(1, 2, 3, CenterType.CENTER);
			new Project("Chair", getPlugin(), getResource(modelFolder + "Chair" + ending)).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("CampChair", getPlugin(), getResource(modelFolder + "CampChair" + ending)).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("CactusPlant", getPlugin(), getResource(modelFolder + "CactusPlant" + ending)).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("SleepingBag", getPlugin(), getResource(modelFolder + "SleepingBag" + ending)).setSize(1, 1, 2, CenterType.RIGHT);
			new Project("ChristmasTree", getPlugin(), getResource(modelFolder + "ChristmasTree" + ending)).setSize(1, 1, 2, CenterType.RIGHT);
			new Project("Table", getPlugin(), getResource(modelFolder + "Table" + ending)).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("SchoolChair", getPlugin(), getResource(modelFolder + "SchoolChair" + ending)).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("SchoolTable", getPlugin(), getResource(modelFolder + "SchoolTable" + ending)).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("BlackBoard", getPlugin(), getResource(modelFolder + "BlackBoard" + ending)).setPlaceableSide(PlaceableSide.SIDE).setSize(1, 2, 3, CenterType.RIGHT);
			new Project("MailBox", getPlugin(), getResource(modelFolder + "MailBox" + ending)).setSize(1, 2, 1, CenterType.RIGHT);
			new Project("Sofa", getPlugin(), getResource(modelFolder + "Sofa" + ending)).setSize(1, 1, 3, CenterType.RIGHT);
			
			new Project("Catapult", getPlugin(), getResource(modelFolder + "Catapult" + ending), PlaceableSide.TOP, Catapult::new).setSize(3, 2, 3, CenterType.RIGHT);
			new Project("Barrels", getPlugin(), getResource(modelFolder + "Barrels" + ending), PlaceableSide.TOP, barrels::new).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("WaxCandle", getPlugin(), getResource(modelFolder + "WaxCandle" + ending), PlaceableSide.TOP, WaxCandle::new).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Lantern", getPlugin(), getResource(modelFolder + "Lantern" + ending), PlaceableSide.TOP, WaxCandle::new).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Log", getPlugin(), getResource(modelFolder + "Log" + ending), log::new).setSize(1, 1, 1, CenterType.CENTER);
			new Project("Fence", getPlugin(), getResource(modelFolder + "Fence" + ending),  fance::new).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Trunk", getPlugin(), getResource(modelFolder + "Trunk" + ending), Trunk::new).setSize(1, 1, 4, CenterType.RIGHT);
			new Project("Sunshade", getPlugin(), getResource(modelFolder + "Sunshade" + ending), sunshade::new).setSize(1, 3, 1, CenterType.RIGHT);
			new Project("Hammock", getPlugin(), getResource(modelFolder + "Hammock" + ending), hammock::new).setSize(1, 2, 7, CenterType.RIGHT);
			new Project("Crossbow", getPlugin(), getResource(modelFolder + "Crossbow" + ending), Crossbow::new).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Tent1", getPlugin(),getResource(modelFolder + "Tent1" + ending), tent_1::new).setSize(4, 3, 5, CenterType.RIGHT);
			new Project("GraveStone", getPlugin(),getResource(modelFolder + "GraveStone" + ending), graveStone::new).setSize(1, 2, 3, CenterType.CENTER);
			new Project("Camera", getPlugin(), getResource(modelFolder + "Camera" + ending), camera::new).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("LargeTable", getPlugin(),getResource(modelFolder + "LargeTable" + ending), largeTable::new).setSize(2, 1, 2, CenterType.RIGHT);
	        new Project("Campfire1", getPlugin(),getResource(modelFolder + "Campfire1" + ending), campfire_1::new).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Campfire2", getPlugin(),getResource(modelFolder + "Campfire2" + ending), campfire_2::new).setSize(2, 1, 2, CenterType.RIGHT);
			new Project("Tent2", getPlugin(),getResource(modelFolder + "Tent2" + ending), tent_2::new).setSize(6, 3, 5, CenterType.RIGHT);
			new Project("Tent3", getPlugin(),getResource(modelFolder + "Tent3" + ending), tent_3::new).setSize(3, 2, 3, CenterType.CENTER);
			new Project("Streetlamp", getPlugin(), getResource(modelFolder + "Streetlamp" + ending), streetlamp::new).setSize(2, 4, 1, CenterType.FRONT);
			new Project("Billboard", getPlugin(), getResource(modelFolder + "Billboard" + ending), billboard::new).setSize(1, 3, 3, CenterType.RIGHT);
			new Project("WeaponStand", getPlugin(), getResource(modelFolder + "WeaponStand" + ending), weaponStand::new).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Guillotine", getPlugin(), getResource(modelFolder + "Guillotine" + ending), Guillotine::new).setSize(1, 5, 2, CenterType.RIGHT);
			new Project("FlowerPot", getPlugin(), getResource(modelFolder + "FlowerPot" + ending), TFlowerPot::new).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("BearTrap", getPlugin(), getResource(modelFolder + "BearTrap" + ending), BearTrap::new).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("TrashCan", getPlugin(), getResource(modelFolder + "TrashCan" + ending), TrashCan::new).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Flag", getPlugin(), getResource(modelFolder + "Flag" + ending), flag::new).setSize(1, 3, 1, CenterType.RIGHT);
			new Project("AdventCalender", getPlugin(), getResource(modelFolder + "AdventCalender" + ending), PlaceableSide.TOP, AdventCalender::new).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("FireworkLauncher", getPlugin(), getResource(modelFolder + "FireworkLauncher" + ending), PlaceableSide.TOP, FireworkLauncher::new).setSize(1, 1, 1, CenterType.CENTER);
			
			FurnitureLib.getInstance().getFurnitureManager().getProjects().stream().filter(pro -> pro.getPlugin().equals(getPlugin())).forEach(pro -> pro.setEditorProject(editModels));
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
	
	public static boolean isNewVersion() {return FurnitureLib.isNewVersion();}

}
