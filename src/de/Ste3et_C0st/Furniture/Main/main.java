package de.Ste3et_C0st.Furniture.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
import de.Ste3et_C0st.Furniture.Objects.electric.billboard;
import de.Ste3et_C0st.Furniture.Objects.electric.camera;
import de.Ste3et_C0st.Furniture.Objects.electric.streetlamp;
import de.Ste3et_C0st.Furniture.Objects.electric.tv;
import de.Ste3et_C0st.Furniture.Objects.garden.SleepingBag;
import de.Ste3et_C0st.Furniture.Objects.garden.TFlowerPot;
import de.Ste3et_C0st.Furniture.Objects.garden.Trunk;
import de.Ste3et_C0st.Furniture.Objects.garden.WaterBottle;
import de.Ste3et_C0st.Furniture.Objects.garden.campchair;
import de.Ste3et_C0st.Furniture.Objects.garden.fance;
import de.Ste3et_C0st.Furniture.Objects.garden.graveStone;
import de.Ste3et_C0st.Furniture.Objects.garden.log;
import de.Ste3et_C0st.Furniture.Objects.garden.mailBox;
import de.Ste3et_C0st.Furniture.Objects.garden.sunshade;
import de.Ste3et_C0st.Furniture.Objects.indoor.chair;
import de.Ste3et_C0st.Furniture.Objects.indoor.largeTable;
import de.Ste3et_C0st.Furniture.Objects.indoor.lantern;
import de.Ste3et_C0st.Furniture.Objects.indoor.sofa;
import de.Ste3et_C0st.Furniture.Objects.indoor.table;
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
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureItemEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.CenterType;
import de.Ste3et_C0st.FurnitureLib.main.Type.PlaceableSide;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;

public class main extends JavaPlugin implements Listener{
	
	FurnitureLib lib;
	FurnitureManager manager;
	config c;
	FileConfiguration file;
	public static double damage = 0;
	static main instance;
	static LocationUtil util;
	public static List<Material> materialWhiteList = new ArrayList<Material>();
	@SuppressWarnings("unused")
	public void onEnable(){
		if(!Bukkit.getPluginManager().isPluginEnabled("FurnitureLib")){Bukkit.getPluginManager().disablePlugin(this);}
		instance = this;
		lib = (FurnitureLib) Bukkit.getPluginManager().getPlugin("FurnitureLib");
		manager = lib.getFurnitureManager();
		util = lib.getLocationUtil();
		Bukkit.getPluginManager().registerEvents(this, this);
		
		new Project("Camera", this,getResource("Crafting/Camera.yml"),PlaceableSide.TOP, camera.class);
		new Project("TV", this,getResource("Crafting/TV.yml"),PlaceableSide.TOP, tv.class).setSize(1, 2, 3, CenterType.CENTER);
		new Project("GraveStone", this,getResource("Crafting/GraveStone.yml"),PlaceableSide.TOP, graveStone.class).setSize(1, 2, 3, CenterType.CENTER);
		new Project("Chair", this,getResource("Crafting/Chair.yml"),PlaceableSide.TOP, chair.class);
		new Project("LargeTable", this,getResource("Crafting/LargeTable.yml"),PlaceableSide.TOP, largeTable.class).setSize(2, 1, 2, CenterType.RIGHT);
		new Project("Lantern", this,getResource("Crafting/Lantern.yml"),PlaceableSide.TOP, lantern.class);
		new Project("Sofa", this,getResource("Crafting/Sofa.yml"),PlaceableSide.TOP, sofa.class).setSize(1, 1, 3, CenterType.RIGHT);
		new Project("Table", this,getResource("Crafting/Table.yml"),PlaceableSide.TOP, table.class);
		new Project("Barrels", this,getResource("Crafting/Barrels.yml"),PlaceableSide.TOP, barrels.class);
        new Project("Campfire1", this,getResource("Crafting/Campfire1.yml"),PlaceableSide.TOP, campfire_1.class);
		new Project("Campfire2", this,getResource("Crafting/Campfire2.yml"),PlaceableSide.TOP, campfire_2.class).setSize(1, 1, 1, CenterType.RIGHT);
		new Project("Tent1", this,getResource("Crafting/Tent1.yml"),PlaceableSide.TOP, tent_1.class).setSize(4, 3, 5, CenterType.RIGHT);
		new Project("Tent2", this,getResource("Crafting/Tent2.yml"),PlaceableSide.TOP, tent_2.class).setSize(4, 3, 5, CenterType.RIGHT);
		new Project("Tent3", this,getResource("Crafting/Tent3.yml"),PlaceableSide.TOP, tent_3.class).setSize(3, 2, 3, CenterType.CENTER);
		new Project("Fence", this, getResource("Crafting/fence.yml"),PlaceableSide.TOP, fance.class);
		new Project("Sunshade", this, getResource("Crafting/Sunshade.yml"),PlaceableSide.TOP, sunshade.class).setSize(1, 3, 1, CenterType.RIGHT);
		new Project("Streetlamp", this, getResource("Crafting/Streetlamp.yml"),PlaceableSide.TOP, streetlamp.class).setSize(2, 4, 1, CenterType.FRONT);
		new Project("Billboard", this, getResource("Crafting/Billboard.yml"),PlaceableSide.TOP, billboard.class).setSize(1, 3, 3, CenterType.RIGHT);
		new Project("Mailbox", this, getResource("Crafting/Mailbox.yml"),PlaceableSide.TOP, mailBox.class).setSize(1, 2, 1, CenterType.RIGHT);
		new Project("WeaponStand", this, getResource("Crafting/WeaponStand.yml"),PlaceableSide.TOP, weaponStand.class).setSize(1, 1, 1, CenterType.RIGHT);
		new Project("Hammock", this, getResource("Crafting/Hammock.yml"),PlaceableSide.TOP, hammock.class).setSize(1, 2, 7, CenterType.RIGHT);
		new Project("CampChair", this, getResource("Crafting/CampChair.yml"),PlaceableSide.TOP, campchair.class);
		new Project("CactusPlant", this, getResource("Crafting/CactusPlant.yml"),PlaceableSide.TOP, CactusPlant.class);
		new Project("Guillotine", this, getResource("Crafting/guillotine.yml"),PlaceableSide.TOP, guillotine.class).setSize(1, 5, 2, CenterType.RIGHT);
		new Project("Log", this, getResource("Crafting/log.yml"), PlaceableSide.TOP, log.class);
		new Project("FlowerPot", this, getResource("Crafting/FlowerPot.yml"),PlaceableSide.BOTTOM, TFlowerPot.class);
		new Project("WaterBottle", this, getResource("Crafting/WaterBottle.yml"), PlaceableSide.WATER, WaterBottle.class);
		new Project("SleepingBag", this, getResource("Crafting/SleepingBag.yml"), PlaceableSide.TOP, SleepingBag.class).setSize(1, 1, 2, CenterType.RIGHT);
		new Project("BearTrap", this, getResource("Crafting/BearTrap.yml"), PlaceableSide.TOP, BearTrap.class);
		new Project("Trunk", this, getResource("Crafting/Trunk.yml"), PlaceableSide.TOP, Trunk.class).setSize(1, 1, 4, CenterType.RIGHT);
		new Project("SchoolTable", this, getResource("Crafting/SchoolTable.yml"), PlaceableSide.TOP, SchoolTable.class);
		new Project("SchoolChair", this, getResource("Crafting/SchoolChair.yml"), PlaceableSide.TOP, SchoolChair.class);
		new Project("BlackBoard", this, getResource("Crafting/BlackBoard.yml"), PlaceableSide.SIDE, BlackBoard.class).setSize(1, 2, 3, CenterType.RIGHT);
		new Project("TrashCan", this, getResource("Crafting/TrashCan.yml"), PlaceableSide.TOP, TrashCan.class);
		new Project("HumanSkeleton", this, getResource("Crafting/HumanSkeleton.yml"), PlaceableSide.TOP, HumanSkeleton.class).setSize(3, 1, 2, CenterType.RIGHT);
		new Project("Crossbow", this, getResource("Crafting/Crossbow.yml"), PlaceableSide.TOP, Crossbow.class);
		new Project("Catapult", this, getResource("Crafting/Catapult.yml"), PlaceableSide.TOP, catapult.class).setSize(3, 2, 3, CenterType.RIGHT);
		new Project("Flag", this, getResource("Crafting/flag.yml"), PlaceableSide.TOP, flag.class);
		List<ObjectID> objList = new ArrayList<ObjectID>();
		for(ObjectID obj : manager.getObjectList()){
			if(obj==null) continue;
			if(objList.contains(obj)) continue;
			if(!objList.contains(obj)) objList.add(obj);
			if(obj.getPlugin()==null) continue;
			if(obj.getSQLAction().equals(SQLAction.REMOVE)) continue;
			if(obj.getPlugin().equalsIgnoreCase(this.getName())){
				switch (obj.getProject()) {
				case "Camera":new camera(this, obj);break;
				case "TV":new tv(this, obj);break;
				case "GraveStone":new graveStone(this, obj);break;
				case "Chair":new chair(this, obj);break;
				case "LargeTable":new largeTable(this, obj);break;
				case "Lantern":new lantern(this, obj);break;
				case "Sofa":new sofa(this, obj);break;
				case "Table":new table(this, obj);break;
				case "Barrels":new barrels(this, obj);break;
				case "Campfire1":new campfire_1(this, obj);break;
				case "Campfire2":new campfire_2(this, obj);break;
				case "Tent1":new tent_1(this, obj);break;
				case "Tent2":new tent_2(this, obj);break;
				case "Tent3":new tent_3(this, obj);break;
				case "Fence":new fance(this, obj);break;
				case "Sunshade":new sunshade(this, obj);break;
				case "Streetlamp":new streetlamp(this, obj);break;
				case "Billboard": new billboard(this, obj); break;
				case "WeaponStand": new weaponStand(this, obj);break;
				case "Hammock": new hammock(this, obj); break;
				case "CampChair": new campchair(this, obj); break;
				case "CactusPlant": new CactusPlant(this, obj); break;
				case "Guillotine": new guillotine(this, obj); break;
				case "FlowerPot": new TFlowerPot(this, obj);break;
				case "WaterBottle": new WaterBottle(this, obj); break;
				case "SleepingBag": new SleepingBag(this, obj); break;
				case "BearTrap": new BearTrap(this, obj); break;
				case "Trunk": new Trunk(this, obj); break;
				case "SchoolTable": new SchoolTable(this, obj); break;
				case "SchoolChair": new SchoolChair(this, obj); break;
				case "BlackBoard": new BlackBoard(this, obj); break;
				case "TrashCan": new TrashCan(this, obj); break;
				case "HumanSkeleton": new HumanSkeleton(this, obj);break;
				case "Crossbow": new Crossbow(this, obj);break;
				case "Catapult": new catapult(this, obj); break;
				case "Flag": new flag(this, obj); break;
				case "Log": new log(this, obj);
				case "Mailbox" : 
					mailBox mail = new mailBox(lib, obj);
					/*try {
						mail.addMailbox(loadPlayerMailBox(obj));
					} catch (Exception e) {
						e.printStackTrace();
					}*/
				break;
				}
			}
		}
		addDefault();
	}
	
	@SuppressWarnings("deprecation")
	private void addDefault(){
		c = new config();
		this.file = c.getConfig("whiteList", "plugin/fence/");
		this.file.addDefaults(YamlConfiguration.loadConfiguration(getResource("config.yml")));
		this.file.options().copyDefaults(true);
		this.c.saveConfig("whiteList", this.file, "plugin/fence/");
		setDefaults();
		
		c = new config();
		this.file = c.getConfig("damage", "plugin/bearTrap/");
		this.file.addDefaults(YamlConfiguration.loadConfiguration(getResource("damage.yml")));
		this.file.options().copyDefaults(true);
		this.c.saveConfig("damage", this.file, "plugin/bearTrap/");
		setDefaults_2();
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
	
	public void onDisable(){
		
	}
	
	public Player loadPlayerMailBox(ObjectID objID){
		c = new config();
		file = c.getConfig("player", "plugin/mailbox/");
		ConfigurationSection section = file.getConfigurationSection("players");
		for(String s : section.getKeys(false)){
			for(String str : file.getStringList("players." + s)){
				if(str.equalsIgnoreCase(objID.getSerial())){
					return Bukkit.getPlayer(UUID.fromString(s));
				}
			}
		}
		return null;
	}
	
	@EventHandler
	private void onClick(FurnitureItemEvent e){
		if(e.isCancelled()){return;}
		if(!e.getProject().hasPermissions(e.getPlayer())){return;}
		if(!e.getProject().getPlugin().getName().equalsIgnoreCase(this.getName())){return;}
		ObjectID obj = e.getObjID();
		if(!e.canBuild()){return;}
		switch(e.getProject().getName()){
			case "Camera" : new camera(this, obj);break;
			case "TV" : new tv(this, obj);break;
			case "GraveStone" : new graveStone(this, obj);break;
			case "Chair" : new chair(this, obj);  break;
			case "LargeTable" : new largeTable(this, obj);break;
			case "Lantern" : new lantern(this, obj);break;
			case "Sofa" : new sofa(this, obj);break;
			case "Table" : new table(this, obj);break;
			case "Barrels" : new barrels(this, obj);break;
			case "Campfire1" : new campfire_1(this, obj);break;
			case "Campfire2" : new campfire_2(this, obj);break;
			case "Tent1" : new tent_1(this, obj);break;
			case "Tent2" : new tent_2(this, obj);break;
			case "Tent3" : new tent_3(this, obj);break;
			case "Fence" : new fance(this, obj);break;
			case "Sunshade": new sunshade(this, obj);break;
			case "Streetlamp": new streetlamp(this, obj);break;
			case "Billboard": new billboard(this, obj);break;
			case "Mailbox" : new mailBox(this, obj); break;
			case "WeaponStand": new weaponStand(this, obj); break;
			case "Hammock": new hammock(this, obj); break;
			case "CampChair": new campchair(this, obj); break;
			case "CactusPlant": new CactusPlant(this, obj); break;
			case "Guillotine": new guillotine(this, obj); break;
			case "FlowerPot":new TFlowerPot(this, obj);break;
			case "Log": new log(this, obj); break;
			case "WaterBottle": new WaterBottle(this,obj);break;
			case "SleepingBag": new SleepingBag(this, obj); break;
			case "BearTrap": new BearTrap(this, obj); break;
			case "Trunk": new Trunk(this, obj); break;
			case "SchoolTable": new SchoolTable(this, obj); break;
			case "SchoolChair": new SchoolChair(this, obj); break;
			case "BlackBoard": new BlackBoard(this, obj); break;
			case "TrashCan": new TrashCan(this, obj); break;
			case "HumanSkeleton": new HumanSkeleton(this, obj);break;
			case "Crossbow": new Crossbow(this, obj);break;
			case "Catapult": new catapult(this, obj); break;
			case "Flag": new flag(this, obj); break;
		}
		e.finish();
		e.removeItem();
	}
	
	public static LocationUtil getLocationUtil(){return util;}

	public static Plugin getInstance() {
		return instance;
	}
}