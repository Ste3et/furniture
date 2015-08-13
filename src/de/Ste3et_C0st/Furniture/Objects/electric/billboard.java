package de.Ste3et_C0st.Furniture.Objects.electric;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemFrame;
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

public class billboard extends Furniture implements Listener{
	
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
	
	public billboard(Location location, FurnitureLib lib, Plugin plugin, ObjectID id){
		super(location, lib, plugin, id);
		this.lutil = main.getLocationUtil();
		this.b = lutil.yawToFace(location.getYaw());
		this.loc = location.getBlock().getLocation();
		this.loc.setYaw(location.getYaw());
		this.w = location.getWorld();
		this.manager = lib.getFurnitureManager();
		this.lib = lib;
		this.plugin = plugin;
		if(id.isFinish()){
			this.obj = id;
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(location);
	}

	public void spawn(Location location){
		List<ArmorStandPacket> asList = new ArrayList<ArmorStandPacket>();
		Location center = lutil.getCenter(location).add(0, -1.2, 0);
		Location center2 = lutil.getRelativ(center, b, 0D, -4D);
		Location center3 = lutil.getRelativ(center, b, 0D, -3.3D);
		
		for(int i = 0; i<=3;i++){
			Location loc = lutil.getRelativ(center.clone(), b, -.1, -.5).add(0, .88*i, 0);
			loc.setYaw(loc.getYaw()+90);
			ArmorStandPacket packet = manager.createArmorStand(obj, loc);
			packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
			packet.setPose(new EulerAngle(1.39, 0, 0), BodyPart.RIGHT_ARM);
			asList.add(packet);
		}
		
		for(int i = 0; i<=3;i++){
			Location loc = lutil.getRelativ(center2.clone(), b, -.1, -.5).add(0, .88*i, 0);
			loc.setYaw(loc.getYaw()+90);
			ArmorStandPacket packet = manager.createArmorStand(obj, loc);
			packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
			packet.setPose(new EulerAngle(1.39, 0, 0), BodyPart.RIGHT_ARM);
			asList.add(packet);
		}
		
		for(int i = 0; i<=4;i++){
			Location loc = lutil.getRelativ(center3.clone(), b, -.1, .88*i).add(0, .7D, 0);
			loc.setYaw(loc.getYaw()+90);
			ArmorStandPacket packet = manager.createArmorStand(obj, loc);
			packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
			packet.setPose(new EulerAngle(-.17, 0, 0), BodyPart.RIGHT_ARM);
			asList.add(packet);
		}
		
		for(int i = 0; i<=4;i++){
			Location loc = lutil.getRelativ(center3.clone(), b, -.1, .88*i).add(0, 2.9D, 0);
			loc.setYaw(loc.getYaw()+90);
			ArmorStandPacket packet = manager.createArmorStand(obj, loc);
			packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
			packet.setPose(new EulerAngle(-.17, 0, 0), BodyPart.RIGHT_ARM);
			asList.add(packet);
		}
		
		for(int x = 0; x<=1;x++){
			for(int y = 0;y<=2;y++){
				Location loc = lutil.getRelativ(location, b,0D,(double) -y-1).add(0, x+1, 0);
				Location loc2 = lutil.getRelativ(location, b,-1D,(double) -y-1).add(0, x+1, 0);
				loc.getBlock().setType(Material.BARRIER);
				ItemFrame frame = (ItemFrame) w.spawn(loc2, ItemFrame.class);
				frame.setFacingDirection(b);
			}
		}
		
		for(ArmorStandPacket pack : asList){
			pack.setInvisible(true);
			pack.setGravity(false);
			pack.setBasePlate(false);
		}
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(e.isCancelled()){return;}
		if(!e.canBuild()){return;}
		if(!e.getID().equals(obj)){return;}
		if(obj==null){return;}
		e.remove();
		obj=null;
		
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {}
}
