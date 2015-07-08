package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.ArmorStandPacket;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;

public class latern implements Listener{

	Location loc;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	Integer id;
	Plugin plugin;
	
	public latern(Location location, FurnitureLib lib, String name, Plugin plugin, ObjectID id){
		this.lutil = main.getLocationUtil();
		this.b = lutil.yawToFace(location.getYaw());
		this.loc = location.getBlock().getLocation();
		this.loc.setYaw(location.getYaw());
		this.w = location.getWorld();
		this.manager = lib.getFurnitureManager();
		this.lib = lib;
		this.plugin = plugin;
		if(id!=null){
			this.obj = id;
			setBlock();
			this.manager.send(obj);
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}else{
			this.obj = new ObjectID(name, plugin.getName(), location);
		}
		spawn(location);
	}
	
	Block block;
	
	private void setBlock(){
		Location center = lutil.getCenter(loc);
		block = center.getWorld().getBlockAt(center);
		if(!block.getType().equals(Material.AIR)){return;}
		block.setType(Material.TORCH);
	}
	
	public void spawn(Location loc){
		List<ArmorStandPacket> aspList = new ArrayList<ArmorStandPacket>();
		Location center = lutil.getCenter(loc);
		setBlock();
		Location obsidian = center;
		Location l = new Location(center.getWorld(), center.getX(), center.getY() -1.43, center.getZ());
		obsidian.add(0D, -2.2, 0D);
		Location left_down = new Location(obsidian.getWorld(), obsidian.getX()+0.22, obsidian.getY() + .62, obsidian.getZ()+0.22);
		Location left_upper = new Location(obsidian.getWorld(), obsidian.getX() -0.21, obsidian.getY() + .62, obsidian.getZ() +0.22);
		Location right_upper = new Location(obsidian.getWorld(), obsidian.getX()-0.21, obsidian.getY()+.62, obsidian.getZ()-0.21);
		Location right_down = new Location(obsidian.getWorld(), obsidian.getX()+0.21, obsidian.getY() + .62, obsidian.getZ() -0.21);
		
		ArmorStandPacket asp = manager.createArmorStand(obj, obsidian);
		asp.getInventory().setHelmet(new ItemStack(Material.OBSIDIAN));
		aspList.add(asp);
		
		asp = manager.createArmorStand(obj, l.add(0,0,0));
		asp.getInventory().setHelmet(new ItemStack(Material.WOOD_PLATE));
		aspList.add(asp);
		
		asp = manager.createArmorStand(obj, left_down);
		asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(asp);
		
		asp = manager.createArmorStand(obj, left_upper);
		asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(asp);
		
		asp = manager.createArmorStand(obj, right_upper);
		asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(asp);
		
		asp = manager.createArmorStand(obj, right_down);
		asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(asp);
		
		for(ArmorStandPacket packet : aspList){
			packet.setBasePlate(false);
			packet.setGravity(false);
			packet.setInvisible(true);
		}
		
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	
	@EventHandler
	public void onWaterFlow(BlockFromToEvent e){
		Location locTo = e.getToBlock().getLocation();
		if(loc!=null && locTo.equals(loc.getBlock().getLocation())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	private void onBreak(FurnitureBreakEvent e){
		if(e.isCancelled()) return;
		if(block==null) return;
		if(!e.getID().equals(obj)) return;
		if(!e.canBuild(null)) return;
		block.setType(Material.AIR);
		manager.remove(obj);
	}
	
	@EventHandler
	private void onInteract(FurnitureClickEvent e){
		if(e.isCancelled()) return;
		if(block==null) return;
		if(!e.getID().equals(obj)) return;
		if(!e.canBuild(null)) return;
		Player p = e.getPlayer();
		ItemStack is = p.getItemInHand();
		if(is.getType().equals(Material.FLINT_AND_STEEL)){
			setLight(true);
		}else if(is.getType().equals(Material.WATER_BUCKET)){
			setLight(false);
		}
		
		
	}
	
	public void setLight(final boolean bool){
		if(bool){block.setType(Material.TORCH);}
		else{block.setType(Material.REDSTONE_TORCH_OFF);}
	}
	
	public boolean getLight(){
		if(block==null||block.getType().equals(Material.AIR)||block.getType().equals(Material.TORCH)){
			return true;
		}
		return false;
	}
}
