package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.ArmorStandPacket;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;

public class campfire_1 extends Furniture implements Listener{

	Location loc;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	Integer id;
	Plugin plugin;
	
	public ObjectID getObjectID(){return this.obj;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public campfire_1(Location location, FurnitureLib lib, Plugin plugin, ObjectID id){
		super(location, lib, plugin, id);
		this.lutil = main.getLocationUtil();
		this.b = lutil.yawToFace(location.getYaw());
		this.loc = location.getBlock().getLocation();
		this.loc.setYaw(location.getYaw());
		this.w = location.getWorld();
		this.manager = lib.getFurnitureManager();
		this.lib = lib;
		this.plugin = plugin;
		this.obj = id;
		if(id.isFinish()){
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(location);
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.canBuild()){return;}
		if(!e.getID().equals(obj)){return;}
		for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
			packet.setFire(false);
			Location location = this.loc.clone();
			location.add(0, 1.2, 0);
			lib.getLightManager().removeLight(location);
		}
		manager.updateFurniture(obj);
		e.remove();
		obj=null;
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.canBuild()){return;}
		if(!e.getID().equals(obj)){return;}
		e.setCancelled(true);
		List<ArmorStandPacket> aspList = manager.getArmorStandPacketByObjectID(obj);
		ItemStack is = e.getPlayer().getItemInHand();
		if(is.getType().equals(Material.WATER_BUCKET)){
			for(ArmorStandPacket packet : aspList){
				packet.setFire(false);
				Location location = this.loc.clone();
				location.add(0, 1.2, 0);
				lib.getLightManager().removeLight(location);
			}
			manager.updateFurniture(obj);
		}else if(is.getType().equals(Material.FLINT_AND_STEEL)){
			for(ArmorStandPacket packet : aspList){
				packet.setFire(true);
				Location location = this.loc.clone();
				location.add(0, 1.2, 0);
				lib.getLightManager().addLight(location,15);
			}
			manager.updateFurniture(obj);
		}
	}
	
	
	public void spawn(Location loc){
		for(int i = 0;i<=3;i++){
			Location location = lutil.getCenter(loc);
			location.add(0,-1.9,0);
			location.setYaw(i*60);
			ArmorStandPacket packet = manager.createArmorStand(obj, location);
			packet.setGravity(false);
		}
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
}
