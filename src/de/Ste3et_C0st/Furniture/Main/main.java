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

import de.Ste3et_C0st.Furniture.Objects.RPG.weaponStand;
import de.Ste3et_C0st.Furniture.Objects.electric.billboard;
import de.Ste3et_C0st.Furniture.Objects.electric.camera;
import de.Ste3et_C0st.Furniture.Objects.electric.streetlamp;
import de.Ste3et_C0st.Furniture.Objects.electric.tv;
import de.Ste3et_C0st.Furniture.Objects.garden.fance;
import de.Ste3et_C0st.Furniture.Objects.garden.graveStone;
import de.Ste3et_C0st.Furniture.Objects.garden.mailBox;
import de.Ste3et_C0st.Furniture.Objects.garden.sunshade;
import de.Ste3et_C0st.Furniture.Objects.indoor.chair;
import de.Ste3et_C0st.Furniture.Objects.indoor.largeTable;
import de.Ste3et_C0st.Furniture.Objects.indoor.lantern;
import de.Ste3et_C0st.Furniture.Objects.indoor.sofa;
import de.Ste3et_C0st.Furniture.Objects.indoor.table;
import de.Ste3et_C0st.Furniture.Objects.outdoor.barrels;
import de.Ste3et_C0st.Furniture.Objects.outdoor.campfire_1;
import de.Ste3et_C0st.Furniture.Objects.outdoor.campfire_2;
import de.Ste3et_C0st.Furniture.Objects.outdoor.hammock;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_1;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_2;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_3;
import de.Ste3et_C0st.FurnitureLib.Crafting.CraftingFile;
import de.Ste3et_C0st.FurnitureLib.Crafting.Project;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureItemEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;

public class main extends JavaPlugin implements Listener{
	
	FurnitureLib lib;
	FurnitureManager manager;
	config c;
	FileConfiguration file;
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
		
		new Project("Camera", new CraftingFile("Camera", getResource("Crafting/Camera.yml")),this, camera.class);
		new Project("TV", new CraftingFile("TV", getResource("Crafting/TV.yml")),this, tv.class);
		new Project("GraveStone", new CraftingFile("GraveStone", getResource("Crafting/GraveStone.yml")),this, graveStone.class);
		new Project("Chair", new CraftingFile("Chair", getResource("Crafting/Chair.yml")),this, chair.class);
		new Project("LargeTable", new CraftingFile("LargeTable", getResource("Crafting/LargeTable.yml")),this, largeTable.class).setSize(2, 1, 2);
		new Project("Lantern", new CraftingFile("Lantern", getResource("Crafting/Lantern.yml")),this, lantern.class);
		new Project("Sofa", new CraftingFile("Sofa", getResource("Crafting/Sofa.yml")),this, sofa.class).setSize(0, 1, 2);
		new Project("Table", new CraftingFile("Table", getResource("Crafting/Table.yml")),this, table.class);
		new Project("Barrels", new CraftingFile("Barrels", getResource("Crafting/Barrels.yml")),this, barrels.class);
        new Project("Campfire1", new CraftingFile("Campfire1", getResource("Crafting/Campfire1.yml")),this, campfire_1.class);
		new Project("Campfire2", new CraftingFile("Campfire2", getResource("Crafting/Campfire2.yml")),this, campfire_2.class).setSize(1, 1, 1);
		new Project("Tent1", new CraftingFile("Tent1", getResource("Crafting/Tent1.yml")),this, tent_1.class).setSize(3, 4, 4);
		new Project("Tent2", new CraftingFile("Tent2", getResource("Crafting/Tent2.yml")),this, tent_2.class).setSize(3, 3, 5);
		new Project("Tent3", new CraftingFile("Tent3", getResource("Crafting/Tent3.yml")),this, tent_3.class).setSize(1, 2, 3);
		new Project("Fence", new CraftingFile("Fence", getResource("Crafting/fence.yml")), this, fance.class);
		new Project("Sunshade", new CraftingFile("Sunshade", getResource("Crafting/Sunshade.yml")), this, sunshade.class);
		new Project("Streetlamp", new CraftingFile("Streetlamp", getResource("Crafting/Streetlamp.yml")), this, sunshade.class);
		new Project("Billboard", new CraftingFile("Billboard", getResource("Crafting/Billboard.yml")), this, billboard.class);
		new Project("Mailbox", new CraftingFile("Mailbox", getResource("Crafting/Mailbox.yml")), this, mailBox.class);
		new Project("WeaponStand", new CraftingFile("WeaponStand", getResource("Crafting/WeaponStand.yml")), this, weaponStand.class);
		new Project("Hammock", new CraftingFile("Hammock", getResource("Crafting/Hammock.yml")), this, hammock.class);
		
		List<ObjectID> objList = new ArrayList<ObjectID>();
		for(ObjectID obj : manager.getObjectList()){
			if(obj==null) continue;
			if(objList.contains(obj)) continue;
			if(!objList.contains(obj)) objList.add(obj);
			if(obj.getPlugin()==null) continue;
			if(obj.getSQLAction().equals(SQLAction.REMOVE)) continue;
			if(obj.getPlugin().equalsIgnoreCase(this.getName())){
				switch (obj.getProject()) {
				case "Camera":new camera(lib, this, obj);break;
				case "TV":new tv(lib, this, obj);break;
				case "GraveStone":new graveStone(lib, this, obj);break;
				case "Chair":new chair(lib, this, obj);break;
				case "LargeTable":new largeTable(lib, this, obj);break;
				case "Lantern":new lantern(lib, this, obj);break;
				case "Sofa":new sofa(lib, this, obj);break;
				case "Table":new table(lib, this, obj);break;
				case "Barrels":new barrels(lib, this, obj);break;
				case "Campfire1":new campfire_1(lib, this, obj);break;
				case "Campfire2":new campfire_2(lib, this, obj);break;
				case "Tent1":new tent_1(lib, this, obj);break;
				case "Tent2":new tent_2(lib, this, obj);break;
				case "Tent3":new tent_3(lib, this, obj);break;
				case "Fence":new fance(lib, this, obj);break;
				case "Sunshade":new sunshade(lib, this, obj);break;
				case "Streetlamp":new streetlamp(lib, this, obj);break;
				case "Billboard": new billboard(lib, this, obj); break;
				case "WeaponStand": new weaponStand(lib, this, obj);break;
				case "Hammock": new hammock(lib, this, obj); break;
				case "Mailbox" : 
					mailBox mail = new mailBox(lib, this, obj);
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
			case "Camera" : new camera(lib, instance, obj);break;
			case "TV" : new tv(lib, instance, obj);break;
			case "GraveStone" : new graveStone(lib, instance, obj);break;
			case "Chair" : new chair(lib, instance, obj);  break;
			case "LargeTable" : new largeTable(lib, instance, obj);break;
			case "Lantern" : new lantern(lib, instance, obj);break;
			case "Sofa" : new sofa(lib, instance, obj);break;
			case "Table" : new table(lib, instance, obj);break;
			case "Barrels" : new barrels(lib, instance, obj);break;
			case "Campfire1" : new campfire_1(lib, instance, obj);break;
			case "Campfire2" : new campfire_2(lib, instance, obj);break;
			case "Tent1" : new tent_1(lib, instance, obj);break;
			case "Tent2" : new tent_2(lib, instance, obj);break;
			case "Tent3" : new tent_3(lib, instance, obj);break;
			case "Fence" : new fance(lib, instance, obj);break;
			case "Sunshade": new sunshade(lib, instance, obj);break;
			case "Streetlamp": new streetlamp(lib, this, obj);break;
			case "Billboard": new billboard(lib, instance, obj);break;
			case "Mailbox" : new mailBox(lib, instance, obj); break;
			case "WeaponStand": new weaponStand(lib, instance, obj); break;
			case "Hammock": new hammock(lib, instance, obj); break;
		}
		e.finish();
		e.removeItem();
	}
	
	public static LocationUtil getLocationUtil(){return util;}

	public static Plugin getInstance() {
		return instance;
	}
}