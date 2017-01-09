package de.Ste3et_C0st.Furniture.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Objects.RPG.Crossbow;
import de.Ste3et_C0st.Furniture.Objects.RPG.Catapult;
import de.Ste3et_C0st.Furniture.Objects.RPG.flag;
import de.Ste3et_C0st.Furniture.Objects.RPG.guillotine;
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
import de.Ste3et_C0st.Furniture.Objects.indoor.sofa;
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
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureLateSpawnEvent;
import de.Ste3et_C0st.FurnitureLib.ShematicLoader.ProjectLoader;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.CenterType;
import de.Ste3et_C0st.FurnitureLib.main.Type.PlaceableSide;

public class main extends JavaPlugin implements Listener{
	
	FurnitureLib lib;
	config c;
	FileConfiguration file;
	public static double damage = 0;
	
	public static main instance;
	static LocationUtil util;
	public static List<Material> materialWhiteList = new ArrayList<Material>();
	public static HashMap<String, Vector> catapultRange = new HashMap<String, Vector>();
	
	public void onEnable(){
		if(!Bukkit.getPluginManager().isPluginEnabled("FurnitureLib")){Bukkit.getPluginManager().disablePlugin(this);}
		instance = this;
		lib = (FurnitureLib) Bukkit.getPluginManager().getPlugin("FurnitureLib");
		util = lib.getLocationUtil();
		if(lib.getDescription().getVersion().startsWith("1.8")){
			new Project("Camera", this,getResource("Crafting/Camera.yml"),PlaceableSide.TOP, camera.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("GraveStone", this,getResource("Crafting/GraveStone.yml"),PlaceableSide.TOP, graveStone.class).setSize(1, 2, 3, CenterType.CENTER);
			new Project("LargeTable", this,getResource("Crafting/LargeTable.yml"),PlaceableSide.TOP, largeTable.class).setSize(2, 1, 2, CenterType.RIGHT);
			new Project("Sofa", this,getResource("Crafting/Sofa.yml"),PlaceableSide.TOP, sofa.class).setSize(1, 1, 3, CenterType.RIGHT);
	        new Project("Campfire1", this,getResource("Crafting/Campfire1.yml"),PlaceableSide.TOP, campfire_1.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Campfire2", this,getResource("Crafting/Campfire2.yml"),PlaceableSide.TOP, campfire_2.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Tent1", this,getResource("Crafting/Tent1.yml"),PlaceableSide.TOP, tent_1.class).setSize(4, 3, 5, CenterType.RIGHT);
			new Project("Tent2", this,getResource("Crafting/Tent2.yml"),PlaceableSide.TOP, tent_2.class).setSize(4, 3, 5, CenterType.RIGHT);
			new Project("Tent3", this,getResource("Crafting/Tent3.yml"),PlaceableSide.TOP, tent_3.class).setSize(3, 2, 3, CenterType.CENTER);
			new Project("Sunshade", this, getResource("Crafting/Sunshade.yml"),PlaceableSide.TOP, sunshade.class).setSize(1, 3, 1, CenterType.RIGHT);
			new Project("Streetlamp", this, getResource("Crafting/Streetlamp.yml"),PlaceableSide.TOP, streetlamp.class).setSize(2, 4, 1, CenterType.FRONT);
			new Project("Billboard", this, getResource("Crafting/Billboard.yml"),PlaceableSide.TOP, billboard.class).setSize(1, 3, 3, CenterType.RIGHT);
			new Project("WeaponStand", this, getResource("Crafting/WeaponStand.yml"),PlaceableSide.TOP, weaponStand.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Hammock", this, getResource("Crafting/Hammock.yml"),PlaceableSide.TOP, hammock.class).setSize(1, 2, 7, CenterType.RIGHT);
			new Project("Guillotine", this, getResource("Crafting/guillotine.yml"),PlaceableSide.TOP, guillotine.class).setSize(1, 5, 2, CenterType.RIGHT);
			new Project("Log", this, getResource("Crafting/log.yml"), PlaceableSide.TOP, log.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("FlowerPot", this, getResource("Crafting/FlowerPot.yml"),PlaceableSide.BOTTOM, TFlowerPot.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("BearTrap", this, getResource("Crafting/BearTrap.yml"), PlaceableSide.TOP, BearTrap.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("TrashCan", this, getResource("Crafting/TrashCan.yml"), PlaceableSide.TOP, TrashCan.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Crossbow", this, getResource("Crafting/Crossbow.yml"), PlaceableSide.TOP, Crossbow.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Flag", this, getResource("Crafting/flag.yml"), PlaceableSide.TOP, flag.class).setSize(1, 3, 1, CenterType.RIGHT);
			new Project("AdventCalender", this, getResource("Crafting/AdventCalender.yml"), PlaceableSide.TOP, AdventCalender.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("FireworkLauncher", this, getResource("Crafting/FireworkLauncher.yml"), PlaceableSide.TOP, FireworkLauncher.class).setSize(1, 1, 1, CenterType.CENTER);
	
			new Project("Catapult", this, getResource("Models/Catapult.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(3, 2, 3, CenterType.RIGHT).setEditorProject(false);
			new Project("HumanSkeleton", this, getResource("Models/HumanSkeleton.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(3, 1, 2, CenterType.RIGHT).setEditorProject(false);
			new Project("CandyCane", this, getResource("Models/CandyCane.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(3, 4, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("SnowGolem", this, getResource("Models/SnowGolem.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 2, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("TV", this, getResource("Models/TV.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 2, 3, CenterType.CENTER).setEditorProject(false);
			new Project("Chair", this, getResource("Models/Chair.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("CampChair", this, getResource("Models/CampChair.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("CactusPlant", this, getResource("Models/CactusPlant.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("SleepingBag", this, getResource("Models/SleepingBag.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 1, 2, CenterType.RIGHT).setEditorProject(false);
			new Project("ChristmasTree", this, getResource("Models/ChristmasTree.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 1, 2, CenterType.RIGHT).setEditorProject(false);
			new Project("Table", this, getResource("Models/Table.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("SchoolChair", this, getResource("Models/SchoolChair.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("SchoolTable", this, getResource("Models/SchoolTable.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("BlackBoard", this, getResource("Models/BlackBoard.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 2, 3, CenterType.RIGHT).setEditorProject(false);
			new Project("Barrels", this, getResource("Models/Barrels.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("WaxCandle", this, getResource("Models/WaxCandle.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Lantern", this, getResource("Models/Lantern.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Mailbox", this, getResource("Models/Mailbox.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 2, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Fence", this, getResource("Models/Fence.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 1, 1, CenterType.RIGHT).setEditorProject(false);
			new Project("Trunk", this, getResource("Models/Trunk.yml"), PlaceableSide.TOP, ProjectLoader.class).setSize(1, 1, 4, CenterType.RIGHT).setEditorProject(false);
			
			addDefault("fence", "whiteList", "config.yml");
			addDefault("bearTrap", "damage", "damage.yml");
			addDefault("catapult", "range", "range.yml");
			
			setDefaults();
			setDefaults_2();
			lib.registerPluginFurnitures(this);
			loadModels();
			
			Bukkit.getPluginManager().registerEvents(this, this);
		}else{
			lib.send("FurnitureLib Version > 1.6.x not found");
			lib.send("DiceFurniture deos not load");
		}
	}
	
	@SuppressWarnings("deprecation")
	private void addDefault(String a, String b, String d){
		c = new config();
		this.file = c.getConfig(b, "plugin/"+a+"/");
		this.file.addDefaults(YamlConfiguration.loadConfiguration(getResource(d)));
		this.file.options().copyDefaults(true);
		this.c.saveConfig(b, this.file, "plugin/"+a+"/");
	}

	private void setDefaults_2(){
		c = new config();
		this.file = c.getConfig("damage", "plugin/bearTrap/");
		damage = this.file.getDouble("damage");
		
		c = new config();
		this.file = c.getConfig("range", "plugin/catapult/");
		for(String str : this.file.getConfigurationSection("rangeOptions").getKeys(false)){
			Vector v = new Vector(
					0, 
					this.file.getDouble("rangeOptions." + str + ".height"), 
					this.file.getDouble("rangeOptions." + str + ".width"));
			catapultRange.put(str, v);
		}
	}
	
	private void setDefaults(){
		c = new config();
		this.file = c.getConfig("whiteList", "plugin/fence/");
		List<String> intList = this.file.getStringList("MaterialData");
		for(String i : intList){
			if(Material.getMaterial(i)!=null){
				Material m = Material.getMaterial(i);
				materialWhiteList.add(m);
			}
		}
	}
	
	public static LocationUtil getLocationUtil(){return util;}

	public static Plugin getInstance() {
		return instance;
	}
	
	public void loadModels(){
		for(ObjectID id : FurnitureLib.getInstance().getFurnitureManager().getObjectList()){
			if(id==null) continue;
			if(id.getProjectOBJ() == null) return;
			switch (id.getProjectOBJ().getName()) {
			case "Catapult":new Catapult(id);break;
			case "Barrels": new barrels(id);break;
			case "WaxCandle": new WaxCandle(id);break;
			case "Lantern": new WaxCandle(id);break;
			case "Fence": new fance(id);break;
			case "Trunk": new Trunk(id);break;
			default:break;
			}
		}
	}
	
	@EventHandler
	public void onFurnitureLateSpawn(FurnitureLateSpawnEvent event){
		if(event.getProject()==null) return;
		if(event.getProject().getName()==null) return;
		switch (event.getProject().getName()) {
		case "Catapult":new Catapult(event.getID());break;
		case "Barrels": new barrels(event.getID());break;
		case "WaxCandle": new WaxCandle(event.getID());break;
		case "Lantern": new WaxCandle(event.getID());break;
		case "Fence": new fance(event.getID());break;
		case "Trunk": new Trunk(event.getID());break;
		default:break;
		}
	}
}