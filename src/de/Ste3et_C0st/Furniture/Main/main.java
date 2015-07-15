package de.Ste3et_C0st.Furniture.Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.Ste3et_C0st.Furniture.Objects.electric.camera;
import de.Ste3et_C0st.Furniture.Objects.electric.tv;
import de.Ste3et_C0st.Furniture.Objects.garden.graveStone;
import de.Ste3et_C0st.Furniture.Objects.indoor.chair;
import de.Ste3et_C0st.Furniture.Objects.indoor.largeTable;
import de.Ste3et_C0st.Furniture.Objects.indoor.lantern;
import de.Ste3et_C0st.Furniture.Objects.indoor.sofa;
import de.Ste3et_C0st.Furniture.Objects.indoor.table;
import de.Ste3et_C0st.Furniture.Objects.outdoor.barrels;
import de.Ste3et_C0st.Furniture.Objects.outdoor.campfire_1;
import de.Ste3et_C0st.Furniture.Objects.outdoor.campfire_2;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_1;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_2;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_3;
import de.Ste3et_C0st.FurnitureLib.Crafting.CraftingFile;
import de.Ste3et_C0st.FurnitureLib.Crafting.Project;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureItemEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.ArmorStandPacket;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;

public class main extends JavaPlugin implements Listener{
	
	FurnitureLib lib;
	FurnitureManager manager;
	static main instance;
	static LocationUtil util;
	
	public void onEnable(){
		if(!Bukkit.getPluginManager().isPluginEnabled("FurnitureLib")){Bukkit.getPluginManager().disablePlugin(this);}
		instance = this;
		lib = (FurnitureLib) Bukkit.getPluginManager().getPlugin("FurnitureLib");
		manager = lib.getFurnitureManager();
		util = lib.getLocationUtil();
		Bukkit.getPluginManager().registerEvents(this, this);
		
		new Project("Camera", new CraftingFile("Camera", getResource("Crafting/Camera.yml")),this, camera.class).setSize(1, 2, 1);
		new Project("TV", new CraftingFile("TV", getResource("Crafting/TV.yml")),this, tv.class).setSize(1, 2, 1);
		new Project("GraveStone", new CraftingFile("GraveStone", getResource("Crafting/GraveStone.yml")),this, graveStone.class).setSize(1, 2, 1);
		new Project("Chair", new CraftingFile("Chair", getResource("Crafting/Chair.yml")),this, chair.class).setSize(1, 1, 1);
		new Project("LargeTable", new CraftingFile("LargeTable", getResource("Crafting/LargeTable.yml")),this, largeTable.class).setSize(3, 1, 3);
		new Project("Lantern", new CraftingFile("Lantern", getResource("Crafting/Lantern.yml")),this, lantern.class).setSize(1, 1, 1);
		new Project("Sofa", new CraftingFile("Sofa", getResource("Crafting/Sofa.yml")),this, sofa.class).setSize(1, 2, 1);
		new Project("Table", new CraftingFile("Table", getResource("Crafting/Table.yml")),this, table.class).setSize(1, 1, 1);
		new Project("Barrels", new CraftingFile("Barrels", getResource("Crafting/Barrels.yml")),this, barrels.class).setSize(1,1,1);
        new Project("Campfire1", new CraftingFile("Campfire1", getResource("Crafting/Campfire1.yml")),this, campfire_1.class).setSize(1, 1, 1);
		new Project("Campfire2", new CraftingFile("Campfire2", getResource("Crafting/Campfire2.yml")),this, campfire_2.class).setSize(2, 1, 2);
		new Project("Tent1", new CraftingFile("Tent1", getResource("Crafting/Tent1.yml")),this, tent_1.class).setSize(5, 3, 4);
		new Project("Tent2", new CraftingFile("Tent2", getResource("Crafting/Tent2.yml")),this, tent_2.class).setSize(6, 3, 4);
		new Project("Tent3", new CraftingFile("Tent3", getResource("Crafting/Tent3.yml")),this, tent_3.class).setSize(1, 2, 3);
		
		List<ObjectID> objList = new ArrayList<ObjectID>();
		for(ObjectID obj : manager.getObjectList()){
			if(objList.contains(obj)) continue;
			if(!objList.contains(obj)) objList.add(obj);
			if(obj.getPlugin().equalsIgnoreCase(this.getName())){
				switch (obj.getProject()) {
				case "Camera":
					new camera(obj.getStartLocation(), lib, "Camera", this, obj);
					break;
				case "TV":
					new tv(obj.getStartLocation(), lib, "TV", this, obj);
					break;
				case "GraveStone":
					new graveStone(obj.getStartLocation(), lib, "GraveStone", this, obj);
					break;
				case "Chair":
					new chair(obj.getStartLocation(), lib, "Chair", this, obj);
					break;
				case "LargeTable":
					new largeTable(obj.getStartLocation(), lib, "LargeTable", this, obj);
					break;
				case "Lantern":
					new lantern(obj.getStartLocation(), lib, "Lantern", this, obj);
					break;
				case "Sofa":
					new sofa(obj.getStartLocation(), lib, "Sofa", this, obj);
					break;
				case "Table":
					new table(obj.getStartLocation(), lib, "Table", this, obj);
					break;
				case "Barrels":
					new barrels(obj.getStartLocation(), lib, "Barrels", this, obj);
					break;
				case "Campfire1":
					new campfire_1(obj.getStartLocation(), lib, "Campfire1", this, obj);
					break;
				case "Campfire2":
					new campfire_2(obj.getStartLocation(), lib, "Campfire2", this, obj);
					break;
				case "Tent1":
					new tent_1(obj.getStartLocation(), lib, "Tent1", this, obj);
					break;
				case "Tent2":
					new tent_2(obj.getStartLocation(), lib, "Tent2", this, obj);
					break;
				case "Tent3":
					new tent_3(obj.getStartLocation(), lib, "Tent3", this, obj);
					break;
				default:
					break;
				}
			}
		}
	}
	
	public void onDisable(){
		
	}
	
	
	public static void deleteEffect(List<ArmorStandPacket> asList){
		try{
			if(asList==null||asList.isEmpty()) return;
			 for (ArmorStandPacket packet : asList) {
				if(packet!=null){
					if(packet.getInventory() != null && packet.getInventory().getHelmet()!=null){
						if(packet.getInventory().getHelmet().getType()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
							packet.getLocation().getWorld().playEffect(packet.getLocation(), Effect.STEP_SOUND, packet.getInventory().getHelmet().getType());
						}
					}
				}
			 }
		}catch(Exception e){}
	}
	
	@EventHandler
	private void onClick(FurnitureItemEvent e){
		if(e.isCancelled()){return;}
		if(!e.canBuild(null)){return;}
		if(!e.getProject().hasPermissions(e.getPlayer())){return;}
		if(!e.getProject().getPlugin().getName().equalsIgnoreCase(this.getName())){return;}
		Location loc = e.getLocation().getBlock().getLocation();
		loc.setYaw(e.getPlayer().getLocation().getYaw());
		loc.add(0, 1, 0);
		Player p = e.getPlayer();
		Project pro = e.getProject();
		lib.getLimitationManager().add(e.getLocation(), pro, p);
		switch(e.getProject().getName()){
			case "Camera" : new camera(loc, lib, "Camera", instance, null); removeEgge(p); return;
			case "TV" : new tv(loc, lib, "TV", instance, null); removeEgge(p); return;
			case "GraveStone" : new graveStone(loc, lib, "GraveStone", instance, null); removeEgge(p); return;
			case "Chair" : new chair(loc, lib, "Chair", instance, null); removeEgge(p); return;
			case "LargeTable" : new largeTable(loc, lib, "LargeTable", instance, null); removeEgge(p); return;
			case "Lantern" : new lantern(loc, lib, "Lantern", instance, null); removeEgge(p); return;
			case "Sofa" : new sofa(loc, lib, "Sofa", instance, null); removeEgge(p); return;
			case "Table" : new table(loc, lib, "Table", instance, null); removeEgge(p); return;
			case "Barrels" : new barrels(loc, lib, "Barrels", instance, null); removeEgge(p); return;
			case "Campfire1" : new campfire_1(loc, lib, "Campfire1", instance, null); removeEgge(p); return;
			case "Campfire2" : new campfire_2(loc, lib, "Campfire2", instance, null); removeEgge(p); return;
			case "Tent1" : new tent_1(loc, lib, "Tent1", instance, null); removeEgge(p); return;
			case "Tent2" : new tent_2(loc, lib, "Tent2", instance, null); removeEgge(p); return;
			case "Tent3" : new tent_3(loc, lib, "Tent3", instance, null); removeEgge(p); return;
			default: e.getPlayer().sendMessage("A error occorupted"); return;
		}
	}
	
	private void removeEgge(Player p){
		if(!p.getGameMode().equals(GameMode.CREATIVE)){
			Integer i = p.getInventory().getHeldItemSlot();
			ItemStack is1 = p.getInventory().getItemInHand();
			is1.setAmount(is1.getAmount()-1);
			p.getInventory().setItem(i, is1);
		}
	}
	
	public static LocationUtil getLocationUtil(){return util;}

	public static Plugin getInstance() {
		return instance;
	}
}