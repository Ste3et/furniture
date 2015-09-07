package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
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
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.ArmorStandPacket;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;

public class chair extends Furniture implements Listener{

	Location loc;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	Plugin plugin;
	
	public ObjectID getObjectID(){return this.obj;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public chair(FurnitureLib lib, Plugin plugin, ObjectID id){
		super(lib, plugin, id);
		this.lutil = main.getLocationUtil();
		this.b = lutil.yawToFace(id.getStartLocation().getYaw());
		this.loc = id.getStartLocation().getBlock().getLocation();
		this.loc.setYaw(id.getStartLocation().getYaw());
		this.w = id.getStartLocation().getWorld();
		this.manager = lib.getFurnitureManager();
		this.lib = lib;
		this.plugin = plugin;
		this.obj = id;
		if(id.isFinish()){
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(id.getStartLocation());
	}
	
	public void spawn(Location loc){
		List<ArmorStandPacket> aspList = new ArrayList<ArmorStandPacket>();
		BlockFace b = lutil.yawToFace(loc.getYaw()).getOppositeFace();
		Location center = lutil.getCenter(loc).add(0, -.05, 0);
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
		
		ArmorStandPacket as = manager.createArmorStand(obj, sitz.clone());
		as.getInventory().setHelmet(new ItemStack(Material.TRAP_DOOR));
		aspList.add(as);
		
		as = manager.createArmorStand(obj, lehne.clone());
		as.getInventory().setHelmet(new ItemStack(Material.TRAP_DOOR));
		as.setPose(new EulerAngle(1.57, .0, .0), BodyPart.HEAD);
		aspList.add(as);
		
		as = manager.createArmorStand(obj, feet1.clone());
		as.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(as);
		
		as = manager.createArmorStand(obj, feet2.clone());
		as.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(as);
		
		as = manager.createArmorStand(obj, feet3.clone());
		as.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(as);
		
		as = manager.createArmorStand(obj, feet4.clone());
		as.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(as);
		
		Location sit = sitz.clone().add(0,-.2,0);
		as = manager.createArmorStand(obj, sit.clone());
		as.setName("#SITZ#");
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
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(obj==null){return;}
		if(obj.getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		if(!e.canBuild()){return;}
		e.remove();
		obj=null;
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(obj==null){return;}
		if(obj.getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		ArmorStandPacket packet = null;
		for(ArmorStandPacket as : manager.getArmorStandPacketByObjectID(obj)){
			if(as.getName().equalsIgnoreCase("#SITZ#")){
				packet=as;
				break;
			}
		}
		if(packet!=null){
			if(packet.getPessanger()==null){
				packet.setPessanger(e.getPlayer());
				packet.update();
			}
		}
	}
}
