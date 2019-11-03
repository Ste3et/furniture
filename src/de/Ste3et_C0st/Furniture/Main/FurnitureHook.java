package de.Ste3et_C0st.Furniture.Main;

import org.bukkit.plugin.Plugin;
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
			new Project("Catapult", getPlugin(), getResource("Models/Catapult.dModel"), Catapult.class).setSize(3, 2, 3, CenterType.RIGHT).setEditorProject(false);
			new Project("HumanSkeleton", getPlugin(), getResource("Models/HumanSkeleton.dModel")).setSize(3, 1, 2, CenterType.RIGHT).setEditorProject(false);
			new Project("CandyCane", getPlugin(), getResource("Models/CandyCane.dModel")).setSize(3, 4, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("SnowGolem", getPlugin(), getResource("Models/SnowGolem.dModel")).setSize(1, 2, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("TV", getPlugin(), getResource("Models/TV.dModel")).setSize(1, 2, 3, CenterType.CENTER).setEditorProject(false);
			new Project("Chair", getPlugin(), getResource("Models/Chair.dModel")).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("CampChair", getPlugin(), getResource("Models/CampChair.dModel")).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("CactusPlant", getPlugin(), getResource("Models/CactusPlant.dModel")).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("SleepingBag", getPlugin(), getResource("Models/SleepingBag.dModel")).setSize(1, 1, 2, CenterType.RIGHT).setEditorProject(false);
			new Project("ChristmasTree", getPlugin(), getResource("Models/ChristmasTree.dModel")).setSize(1, 1, 2, CenterType.RIGHT).setEditorProject(false);
			new Project("Table", getPlugin(), getResource("Models/Table.dModel")).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("SchoolChair", getPlugin(), getResource("Models/SchoolChair.dModel")).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("SchoolTable", getPlugin(), getResource("Models/SchoolTable.dModel")).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("BlackBoard", getPlugin(), getResource("Models/BlackBoard.dModel")).setPlaceableSide(PlaceableSide.SIDE).setSize(1, 2, 3, CenterType.RIGHT).setEditorProject(false);
			new Project("Barrels", getPlugin(), getResource("Models/Barrels.dModel"), barrels.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("WaxCandle", getPlugin(), getResource("Models/WaxCandle.dModel"), WaxCandle.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Lantern", getPlugin(), getResource("Models/Lantern.dModel")).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("MailBox", getPlugin(), getResource("Models/MailBox.dModel")).setSize(1, 2, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Fence", getPlugin(), getResource("Models/Fence.dModel"), fance.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Trunk", getPlugin(), getResource("Models/Trunk.dModel"), Trunk.class).setSize(1, 1, 4, CenterType.RIGHT).setEditorProject(false);
			new Project("Sunshade", getPlugin(), getResource("Models/Sunshade.dModel"), sunshade.class).setSize(1, 3, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Hammock", getPlugin(), getResource("Models/Hammock.dModel"), hammock.class).setSize(1, 2, 7, CenterType.RIGHT).setEditorProject(false);
			new Project("Crossbow", getPlugin(), getResource("Models/Crossbow.dModel"), Crossbow.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Tent1", getPlugin(),getResource("Models/Tent1.dModel"), tent_1.class).setSize(4, 3, 5, CenterType.RIGHT).setEditorProject(false);
			new Project("GraveStone", getPlugin(),getResource("Models/GraveStone.dModel"), graveStone.class).setSize(1, 2, 3, CenterType.CENTER).setEditorProject(false);
			new Project("Camera", getPlugin(), getResource("Models/Camera.dModel"), camera.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Sofa", getPlugin(), getResource("Models/Sofa.dModel")).setSize(1, 1, 3, CenterType.RIGHT).setEditorProject(false);
			new Project("Log", getPlugin(), getResource("Models/Log.dModel"), log.class).setSize(1, 1, 1, CenterType.CENTER).setEditorProject(false);
			new Project("LargeTable", getPlugin(),getResource("Models/LargeTable.dModel"),PlaceableSide.TOP, largeTable.class).setSize(2, 1, 2, CenterType.RIGHT).setEditorProject(false);
	        new Project("Campfire1", getPlugin(),getResource("Models/Campfire1.dModel"),PlaceableSide.TOP, campfire_1.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Campfire2", getPlugin(),getResource("Models/Campfire2.dModel"),PlaceableSide.TOP, campfire_2.class).setSize(2, 1, 2, CenterType.RIGHT).setEditorProject(false);
			new Project("Tent2", getPlugin(),getResource("Models/Tent2.dModel"),PlaceableSide.TOP, tent_2.class).setSize(6, 3, 5, CenterType.RIGHT).setEditorProject(false);
			new Project("Tent3", getPlugin(),getResource("Models/Tent3.dModel"),PlaceableSide.TOP, tent_3.class).setSize(3, 2, 3, CenterType.CENTER).setEditorProject(false);
			new Project("Streetlamp", getPlugin(), getResource("Models/Streetlamp.dModel"),PlaceableSide.TOP, streetlamp.class).setSize(2, 4, 1, CenterType.FRONT).setEditorProject(false);
			new Project("Billboard", getPlugin(), getResource("Models/Billboard.dModel"),PlaceableSide.TOP, billboard.class).setSize(1, 3, 3, CenterType.RIGHT).setEditorProject(false);
			new Project("WeaponStand", getPlugin(), getResource("Models/WeaponStand.dModel"),PlaceableSide.TOP, weaponStand.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Guillotine", getPlugin(), getResource("Models/Guillotine.dModel"),PlaceableSide.TOP, Guillotine.class).setSize(1, 5, 2, CenterType.RIGHT).setEditorProject(false);
			new Project("FlowerPot", getPlugin(), getResource("Models/FlowerPot.dModel"),PlaceableSide.BOTTOM, TFlowerPot.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("BearTrap", getPlugin(), getResource("Models/BearTrap.dModel"), PlaceableSide.TOP, BearTrap.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("TrashCan", getPlugin(), getResource("Models/TrashCan.dModel"), PlaceableSide.TOP, TrashCan.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Flag", getPlugin(), getResource("Models/Flag.dModel"), PlaceableSide.TOP, flag.class).setSize(1, 3, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("AdventCalender", getPlugin(), getResource("Models/AdventCalender.dModel"), PlaceableSide.TOP, AdventCalender.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("FireworkLauncher", getPlugin(), getResource("Models/FireworkLauncher.dModel"), PlaceableSide.TOP, FireworkLauncher.class).setSize(1, 1, 1, CenterType.CENTER).setEditorProject(false);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void applyPluginFunctions() {
		System.out.println("applyPluginFunctions");
		FurnitureLib.getInstance().getFurnitureManager().getProjects().stream()
		.filter(pro -> pro.getPlugin().getName().equals(getPlugin().getDescription().getName()))
		.forEach(Project::applyFunction);
	}

	@Override
	public void onFurnitureLateSpawn(ObjectID obj) {
		
	}

}
