package de.Ste3et_C0st.Furniture.Objects.electric;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

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
import de.Ste3et_C0st.FurnitureLib.main.Type.EventType;

public class streetlamp extends Furniture implements Listener{
	
	Location loc, light;
	Vector loc2;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	Plugin plugin;
	boolean redstone = false;
	List<Location> blockLocation = new ArrayList<Location>();
	
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public streetlamp(FurnitureLib lib, Plugin plugin, ObjectID id){
		super(lib, plugin, id);
		this.lutil = main.getLocationUtil();
		this.b = lutil.yawToFace(id.getStartLocation().getYaw());
		this.loc = id.getStartLocation().getBlock().getLocation();
		this.loc.setYaw(id.getStartLocation().getYaw());
		this.loc2 = id.getStartLocation().toVector();
		this.w = id.getStartLocation().getWorld();
		this.manager = lib.getFurnitureManager();
		this.lib = lib;
		this.plugin = plugin;
		this.light = lutil.getRelativ(loc, b, -1D, 0D);
		this.obj = id;
		setBlock();
		if(id.isFinish()){
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(id.getStartLocation());
	}
	
	public void spawn(Location location){
		List<ArmorStandPacket> asList = new ArrayList<ArmorStandPacket>();
		Location center = lutil.getCenter(location).clone();
		center.add(0, -1.1, 0);
		
		Location aloc = center.clone();
		aloc.add(0, -.2, 0);
		for(int i = 0; i<=3;i++){
			Location loc = aloc.clone().add(0, .215*i, 0);
			ArmorStandPacket packet = manager.createArmorStand(obj, loc);
			packet.getInventory().setHelmet(new ItemStack(Material.STEP));
			packet.setSmall(true);
			asList.add(packet);
		}
		
		for(int i = 0; i<=3;i++){
			Location loc = lutil.getRelativ(center.clone(), b, .47, .38).add(0, .88*i, 0);
			ArmorStandPacket packet = manager.createArmorStand(obj, loc);
			packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
			packet.setPose(new EulerAngle(1.39, 0, 0), BodyPart.RIGHT_ARM);
			asList.add(packet);
		}
		
		
		ArmorStandPacket packet = manager.createArmorStand(obj, lutil.getRelativ(center, b, -.9, .38).add(0, 3.1, 0));
		packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
		packet.setPose(new EulerAngle(-.17, 0, 0), BodyPart.RIGHT_ARM);
		asList.add(packet);
		
		float yaw = lutil.FaceToYaw(b);
		BlockFace face = lutil.yawToFace(yaw +90);
		packet = manager.createArmorStand(obj, lutil.getRelativ(center, b, -.4, .38).add(0, 2.68, 0));
		packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
		packet.setPose(new EulerAngle(1.39, 0, 0), BodyPart.RIGHT_ARM);
		asList.add(packet);
		
		packet = manager.createArmorStand(obj, lutil.getRelativ(center, face, 0.0, -0.9).add(0, 2.3, 0));
		packet.getInventory().setHelmet(new ItemStack(Material.REDSTONE_LAMP_OFF));
		packet.setName("#LAMP#");
		packet.setSmall(true);
		asList.add(packet);
		
		packet = manager.createArmorStand(obj, lutil.getRelativ(center, face, 0.0, -0.9).add(0, 1.8, 0));
		packet.getInventory().setHelmet(new ItemStack(Material.WOOD, 1,(short) 5));
		asList.add(packet);
		
		packet = manager.createArmorStand(obj, lutil.getRelativ(center, face, 0.0, -0.9).add(0, 2.3, 0));
		packet.getInventory().setHelmet(new ItemStack(Material.GLASS));
		packet.setSmall(true);
		asList.add(packet);
		
		for(ArmorStandPacket pack : asList){
			pack.setInvisible(true);
			pack.setGravity(false);
			pack.setBasePlate(false);
		}
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	private void setBlock(){
		Location location = loc.getBlock().getLocation();
		location.setY(location.getY()-1);
		for(int i = 0; i<=3;i++){
			location.setY(location.getY()+1);
			Block block = location.getBlock();
			block.setType(Material.BARRIER);
			blockLocation.add(block.getLocation());
			if(i==3){
				Location loc =lutil.getRelativ(location, b, -1D, 0D);
				Block blocks = loc.getBlock();
				blocks.setType(Material.BARRIER);
				blockLocation.add(blocks.getLocation());
			}
		}
	}
	
	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent e){
		if(obj==null) return;
		if(e.isCancelled()){return;}
		if(redstone) return;
		if(e.getAction()==null) return;
		if(e.getClickedBlock()==null) return;
		if(!blockLocation.contains(e.getClickedBlock().getLocation())){return;}
		if(!lib.canBuild(e.getPlayer(), obj, EventType.INTERACT)){return;}
		e.setCancelled(true);
		if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
				FurnitureLib.getInstance().getLightManager().removeLight(light);
				obj.remove(e.getPlayer());
				for(Location loc : blockLocation){
					loc.getBlock().setType(Material.AIR);
				}
				obj=null;
		}else if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				if(isOn()){
					setLight(false);
				}else{
					setLight(true);
				}
		}
	}

	@EventHandler
	private void onBlockPowered(BlockRedstoneEvent e){
		if(e.getBlock()==null) return;
		if(obj==null) return;
		Vector loc = e.getBlock().getLocation().toVector();
		if(loc2.distance(loc)<=1){
			if(e.getNewCurrent()==0){
				setLight(false);
				redstone = false;
			}else{
				setLight(true);
				redstone = true;
			}
		}
	}
	
	private void setLight(Boolean b){
		if(!b){
			ArmorStandPacket packet = getPacket();
			if(packet==null) return;
			packet.getInventory().setHelmet(new ItemStack(Material.REDSTONE_LAMP_OFF));
			FurnitureLib.getInstance().getLightManager().removeLight(light);
			manager.updateFurniture(obj);
		}else{
			ArmorStandPacket packet = getPacket();
			if(packet==null) return;
			packet.getInventory().setHelmet(new ItemStack(Material.GLOWSTONE));
			FurnitureLib.getInstance().getLightManager().addLight(light, 15);
			manager.updateFurniture(obj);
		}
	}
	
	private ArmorStandPacket getPacket(){
		for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
			if(packet.getName().equalsIgnoreCase("#LAMP#")){
				return packet;
			}
		}
		return null;
	}
	
	private boolean isOn(){
		for(ArmorStandPacket as : manager.getArmorStandPacketByObjectID(obj)){
			if(as.getName().equalsIgnoreCase("#LAMP#")){
				switch (as.getInventory().getHelmet().getType()) {
				case REDSTONE_LAMP_OFF: return false;
				case GLOWSTONE: return true;
				default: return false;
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		if(!e.canBuild()){return;}
		FurnitureLib.getInstance().getLightManager().removeLight(light);
		e.remove();
		obj=null;
		for(Location loc : blockLocation){
			loc.getBlock().setType(Material.AIR);
		}
		blockLocation.clear();
		
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		Boolean isOn = isOn();
		ArmorStandPacket packet = getPacket();
		if(packet==null) return;
		if(redstone) return;
		if(isOn){
			setLight(false);
		}else{
			setLight(true);
		}
	}
}
