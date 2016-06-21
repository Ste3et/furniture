package de.Ste3et_C0st.Furniture.Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.Ste3et_C0st.Furniture.Objects.RPG.Crossbow;
import de.Ste3et_C0st.Furniture.Objects.RPG.HumanSkeleton;
import de.Ste3et_C0st.Furniture.Objects.RPG.catapult;
import de.Ste3et_C0st.Furniture.Objects.RPG.flag;
import de.Ste3et_C0st.Furniture.Objects.RPG.guillotine;
import de.Ste3et_C0st.Furniture.Objects.RPG.weaponStand;
import de.Ste3et_C0st.Furniture.Objects.School.BlackBoard;
import de.Ste3et_C0st.Furniture.Objects.School.SchoolChair;
import de.Ste3et_C0st.Furniture.Objects.School.SchoolTable;
import de.Ste3et_C0st.Furniture.Objects.School.TrashCan;
import de.Ste3et_C0st.Furniture.Objects.christmas.AdventCalender;
import de.Ste3et_C0st.Furniture.Objects.christmas.CandyCane;
import de.Ste3et_C0st.Furniture.Objects.christmas.ChristmasTree;
import de.Ste3et_C0st.Furniture.Objects.christmas.FireworkLauncher;
import de.Ste3et_C0st.Furniture.Objects.christmas.SnowGolem;
import de.Ste3et_C0st.Furniture.Objects.electric.billboard;
import de.Ste3et_C0st.Furniture.Objects.electric.camera;
import de.Ste3et_C0st.Furniture.Objects.electric.streetlamp;
import de.Ste3et_C0st.Furniture.Objects.electric.tv;
import de.Ste3et_C0st.Furniture.Objects.garden.SleepingBag;
import de.Ste3et_C0st.Furniture.Objects.garden.TFlowerPot;
import de.Ste3et_C0st.Furniture.Objects.garden.Trunk;
import de.Ste3et_C0st.Furniture.Objects.garden.campchair;
import de.Ste3et_C0st.Furniture.Objects.garden.fance;
import de.Ste3et_C0st.Furniture.Objects.garden.graveStone;
import de.Ste3et_C0st.Furniture.Objects.garden.log;
import de.Ste3et_C0st.Furniture.Objects.garden.mailBox;
import de.Ste3et_C0st.Furniture.Objects.garden.sunshade;
import de.Ste3et_C0st.Furniture.Objects.indoor.chair;
import de.Ste3et_C0st.Furniture.Objects.indoor.lantern;
import de.Ste3et_C0st.Furniture.Objects.indoor.largeTable;
import de.Ste3et_C0st.Furniture.Objects.indoor.sofa;
import de.Ste3et_C0st.Furniture.Objects.indoor.table;
import de.Ste3et_C0st.Furniture.Objects.light.WaxCandle;
import de.Ste3et_C0st.Furniture.Objects.outdoor.CactusPlant;
import de.Ste3et_C0st.Furniture.Objects.outdoor.barrels;
import de.Ste3et_C0st.Furniture.Objects.outdoor.campfire_1;
import de.Ste3et_C0st.Furniture.Objects.outdoor.campfire_2;
import de.Ste3et_C0st.Furniture.Objects.outdoor.hammock;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_1;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_2;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_3;
import de.Ste3et_C0st.Furniture.Objects.trap.BearTrap;
import de.Ste3et_C0st.FurnitureLib.Crafting.Project;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.Type.CenterType;
import de.Ste3et_C0st.FurnitureLib.main.Type.PlaceableSide;

public class main extends JavaPlugin{
	
	FurnitureLib lib;
	config c;
	FileConfiguration file;
	public static double damage = 0;
	public static main instance;
	static LocationUtil util;
	public static List<Material> materialWhiteList = new ArrayList<Material>();
	public void onEnable(){
		if(!Bukkit.getPluginManager().isPluginEnabled("FurnitureLib")){Bukkit.getPluginManager().disablePlugin(this);}
		instance = this;
		lib = (FurnitureLib) Bukkit.getPluginManager().getPlugin("FurnitureLib");
		util = lib.getLocationUtil();
		if(lib.getDescription().getVersion().startsWith("1.7") || lib.getDescription().getVersion().startsWith("1.6")){
			new Project("Camera", this,getResource("Crafting/Camera.yml"),PlaceableSide.TOP, camera.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("TV", this,getResource("Crafting/TV.yml"),PlaceableSide.TOP, tv.class).setSize(1, 2, 3, CenterType.CENTER);
			new Project("GraveStone", this,getResource("Crafting/GraveStone.yml"),PlaceableSide.TOP, graveStone.class).setSize(1, 2, 3, CenterType.CENTER);
			new Project("Chair", this,getResource("Crafting/Chair.yml"),PlaceableSide.TOP, chair.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("LargeTable", this,getResource("Crafting/LargeTable.yml"),PlaceableSide.TOP, largeTable.class).setSize(2, 1, 2, CenterType.RIGHT);
			new Project("Lantern", this,getResource("Crafting/Lantern.yml"),PlaceableSide.TOP, lantern.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Sofa", this,getResource("Crafting/Sofa.yml"),PlaceableSide.TOP, sofa.class).setSize(1, 1, 3, CenterType.RIGHT);
			new Project("Table", this,getResource("Crafting/Table.yml"),PlaceableSide.TOP, table.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Barrels", this,getResource("Crafting/Barrels.yml"),PlaceableSide.TOP, barrels.class).setSize(1, 1, 1, CenterType.RIGHT);
	        new Project("Campfire1", this,getResource("Crafting/Campfire1.yml"),PlaceableSide.TOP, campfire_1.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Campfire2", this,getResource("Crafting/Campfire2.yml"),PlaceableSide.TOP, campfire_2.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Tent1", this,getResource("Crafting/Tent1.yml"),PlaceableSide.TOP, tent_1.class).setSize(4, 3, 5, CenterType.RIGHT);
			new Project("Tent2", this,getResource("Crafting/Tent2.yml"),PlaceableSide.TOP, tent_2.class).setSize(4, 3, 5, CenterType.RIGHT);
			new Project("Tent3", this,getResource("Crafting/Tent3.yml"),PlaceableSide.TOP, tent_3.class).setSize(3, 2, 3, CenterType.CENTER);
			new Project("Fence", this, getResource("Crafting/fence.yml"),PlaceableSide.TOP, fance.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Sunshade", this, getResource("Crafting/Sunshade.yml"),PlaceableSide.TOP, sunshade.class).setSize(1, 3, 1, CenterType.RIGHT);
			new Project("Streetlamp", this, getResource("Crafting/Streetlamp.yml"),PlaceableSide.TOP, streetlamp.class).setSize(2, 4, 1, CenterType.FRONT);
			new Project("Billboard", this, getResource("Crafting/Billboard.yml"),PlaceableSide.TOP, billboard.class).setSize(1, 3, 3, CenterType.RIGHT);
			new Project("Mailbox", this, getResource("Crafting/Mailbox.yml"),PlaceableSide.TOP, mailBox.class).setSize(1, 2, 1, CenterType.RIGHT);
			new Project("WeaponStand", this, getResource("Crafting/WeaponStand.yml"),PlaceableSide.TOP, weaponStand.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Hammock", this, getResource("Crafting/Hammock.yml"),PlaceableSide.TOP, hammock.class).setSize(1, 2, 7, CenterType.RIGHT);
			new Project("CampChair", this, getResource("Crafting/CampChair.yml"),PlaceableSide.TOP, campchair.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("CactusPlant", this, getResource("Crafting/CactusPlant.yml"),PlaceableSide.TOP, CactusPlant.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Guillotine", this, getResource("Crafting/guillotine.yml"),PlaceableSide.TOP, guillotine.class).setSize(1, 5, 2, CenterType.RIGHT);
			new Project("Log", this, getResource("Crafting/log.yml"), PlaceableSide.TOP, log.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("FlowerPot", this, getResource("Crafting/FlowerPot.yml"),PlaceableSide.BOTTOM, TFlowerPot.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("SleepingBag", this, getResource("Crafting/SleepingBag.yml"), PlaceableSide.TOP, SleepingBag.class).setSize(1, 1, 2, CenterType.RIGHT);
			new Project("BearTrap", this, getResource("Crafting/BearTrap.yml"), PlaceableSide.TOP, BearTrap.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Trunk", this, getResource("Crafting/Trunk.yml"), PlaceableSide.TOP, Trunk.class).setSize(1, 1, 4, CenterType.RIGHT);
			new Project("SchoolTable", this, getResource("Crafting/SchoolTable.yml"), PlaceableSide.TOP, SchoolTable.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("SchoolChair", this, getResource("Crafting/SchoolChair.yml"), PlaceableSide.TOP, SchoolChair.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("BlackBoard", this, getResource("Crafting/BlackBoard.yml"), PlaceableSide.SIDE, BlackBoard.class).setSize(1, 2, 3, CenterType.RIGHT);
			new Project("TrashCan", this, getResource("Crafting/TrashCan.yml"), PlaceableSide.TOP, TrashCan.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("HumanSkeleton", this, getResource("Crafting/HumanSkeleton.yml"), PlaceableSide.TOP, HumanSkeleton.class).setSize(3, 1, 2, CenterType.RIGHT);
			new Project("Crossbow", this, getResource("Crafting/Crossbow.yml"), PlaceableSide.TOP, Crossbow.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("Catapult", this, getResource("Crafting/Catapult.yml"), PlaceableSide.TOP, catapult.class).setSize(3, 2, 3, CenterType.RIGHT);
			new Project("Flag", this, getResource("Crafting/flag.yml"), PlaceableSide.TOP, flag.class).setSize(1, 3, 1, CenterType.RIGHT);
			new Project("SnowGolem", this, getResource("Crafting/SnowGolem.yml"), PlaceableSide.TOP, SnowGolem.class).setSize(1, 2, 1, CenterType.RIGHT);
			new Project("CandyCane", this, getResource("Crafting/CandyCane.yml"), PlaceableSide.TOP, CandyCane.class).setSize(3, 4, 1, CenterType.RIGHT);
			new Project("AdventCalender", this, getResource("Crafting/AdventCalender.yml"), PlaceableSide.TOP, AdventCalender.class).setSize(1, 1, 1, CenterType.RIGHT);
			new Project("ChristmasTree", this, getResource("Crafting/ChristmasTree.yml"), PlaceableSide.TOP, ChristmasTree.class).setSize(1, 1, 1, CenterType.CENTER);
			new Project("FireworkLauncher", this, getResource("Crafting/FireworkLauncher.yml"), PlaceableSide.TOP, FireworkLauncher.class).setSize(1, 1, 1, CenterType.CENTER);
			new Project("WaxCandle", this, getResource("Crafting/WaxCandle.yml"), PlaceableSide.TOP, WaxCandle.class);
			addDefault("fence", "whiteList", "config.yml");
			addDefault("bearTrap", "damage", "damage.yml");
			setDefaults();
			setDefaults_2();
			lib.registerPluginFurnitures(this);
		}else{
			lib.send("FurnitureLib Version 1.6.x or 1.7.x not found");
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
}