package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.ArmorStandPacket;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;

public class chair implements Listener{

	Location loc;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	Integer id;
	Plugin plugin;
	
	public chair(Location location, FurnitureLib lib, String name, Plugin plugin, ObjectID id){
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
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}else{
			this.obj = new ObjectID(name, plugin.getName(), location);
		}
		spawn(location);
	}
	
	public void spawn(Location loc){
		List<ArmorStandPacket> aspList = new ArrayList<ArmorStandPacket>();
		BlockFace b = lutil.yawToFace(loc.getYaw()).getOppositeFace();
		Location center = lutil.getCenter(loc);
		Location sitz = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location feet1 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location feet2 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location feet3 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location feet4 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location lehne = lutil.getRelativ(center.add(0,-1.1,0), b, -.25, .0);
		feet1.add(-.25,-1.8,-.25);
		feet2.add(.25,-1.8,-.25);
		feet3.add(.25,-1.8,.25);
		feet4.add(-.25,-1.8,.25);
		
		sitz.add(0,-1.45,0);
		sitz.setYaw(lutil.FaceToYaw(b));
		lehne.setYaw(lutil.FaceToYaw(b));
		
		ArmorStandPacket as = manager.createArmorStand(obj, sitz);
		as.getInventory().setHelmet(new ItemStack(Material.TRAP_DOOR));
		aspList.add(as);
		
		as = manager.createArmorStand(obj, lehne);
		as.getInventory().setHelmet(new ItemStack(Material.TRAP_DOOR));
		as.setPose(new EulerAngle(1.57, .0, .0), BodyPart.HEAD);
		aspList.add(as);
		
		as = manager.createArmorStand(obj, feet1);
		as.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(as);
		
		as = manager.createArmorStand(obj, feet2);
		as.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(as);
		
		as = manager.createArmorStand(obj, feet3);
		as.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(as);
		
		as = manager.createArmorStand(obj, feet4);
		as.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(as);
		
		as = manager.createArmorStand(obj, sitz.add(0,-.2,0));
		id = as.getEntityId();
		aspList.add(as);
		
		for(ArmorStandPacket asp : aspList){
			asp.setGravity(false);
			asp.setInvisible(true);
			asp.setBasePlate(false);
		}
		
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void onBreak(FurnitureBreakEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.canBuild(null)){return;}
		if(!e.getID().equals(obj)){return;}
		if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
			w.dropItem(loc.add(0,1,0), manager.getProject(obj.getProject()).getCraftingFile().getRecipe().getResult());
		}
		main.deleteEffect(manager.getArmorStandPacketByObjectID(obj));
		manager.remove(obj);
		obj=null;
		e.remove();
	}
	
	@EventHandler
	private void onClick(FurnitureClickEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		ArmorStandPacket packet = manager.getArmorStandPacketByID(id);
		if(packet.getPessanger()==null){
			packet.setPessanger(e.getPlayer());
			packet.update();
		}
	}
}
