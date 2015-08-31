package de.Ste3et_C0st.Furniture.Objects.electric;

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

public class tv extends Furniture implements Listener{
	
	Location loc;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	Plugin plugin;

	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public tv(FurnitureLib lib, Plugin plugin, ObjectID id){
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
	
	public void spawn(Location location){
		List<ArmorStandPacket> aspList = new ArrayList<ArmorStandPacket>();
		if(b.equals(BlockFace.WEST)){location = lutil.getRelativ(location, b, .0, -1.02);}
		if(b.equals(BlockFace.SOUTH)){location = lutil.getRelativ(location, b, -1.0, -1.02);}
		if(b.equals(BlockFace.EAST)){location = lutil.getRelativ(location, b, -1.0, .0);}
		BlockFace b = lutil.yawToFace(location.getYaw()).getOppositeFace();
		Location center = lutil.getCenter(getLocation());
		center.add(0, -1.38, 0);
		center.setYaw(lutil.FaceToYaw(b));
		Location iron = center.clone();
		ArmorStandPacket as = manager.createArmorStand(obj, iron);
		as.getInventory().setHelmet(new ItemStack(Material.IRON_PLATE));
		as.setSmall(true);
		aspList.add(as);
		center.add(0, -.45, 0);
		center.setYaw(lutil.FaceToYaw(b));

		as = manager.createArmorStand(obj, center);
		as.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(as);
		
		Location tv = lutil.getRelativ(location, getBlockFace(), .85, .7);
		tv.add(0, -1.33, 0);
		tv.setYaw(lutil.FaceToYaw(b));
		Double l = .63;
		for(int i = 0;i<=1;i++){
			setRow(tv, .63, l, -.35,2,lutil.degresstoRad(new EulerAngle(90, 0, 0)), aspList);
			l+=.63;
		}
		
		for(ArmorStandPacket asp : aspList){
			asp.setGravity(false);
			asp.setBasePlate(false);
			asp.setInvisible(true);
		}
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	public void setRow(Location loc, double x,double y, double z, int replay, EulerAngle angle, List<ArmorStandPacket> list){
		Double d = .0;
		for(int i = 0; i<=replay;i++){
			Location loc1= lutil.getRelativ(loc, b, z, d-.825);
			loc1.setYaw(lutil.FaceToYaw(b));
			loc1.add(0, y,0);
			ArmorStandPacket packet = manager.createArmorStand(obj, loc1);
			packet.getInventory().setHelmet(new ItemStack(Material.CARPET, 1 , (short) 15));
			packet.setPose(angle, BodyPart.HEAD);
			list.add(packet);
			d+=x;
		}
	}
	
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(obj==null){return;} 
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		if(!e.canBuild()){return;}
		e.remove();
		obj=null;
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){}
}
