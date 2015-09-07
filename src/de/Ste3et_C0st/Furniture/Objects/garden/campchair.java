package de.Ste3et_C0st.Furniture.Objects.garden;

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

public class campchair extends Furniture implements Listener {
	
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
	
	public campchair(FurnitureLib lib, Plugin plugin, ObjectID id){
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

	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(obj==null){return;}
		if(obj.getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		if(!e.canBuild()){return;}
		e.remove();
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(obj==null){return;}
		if(obj.getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		if(!e.canBuild()){return;}
		for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
			if(packet.getName().equalsIgnoreCase("#SITZ#")){
				if(packet.getPessanger()==null){
					packet.setPessanger(e.getPlayer());
				}
			}
		}
	}

	@Override
	public void spawn(Location location) {
		List<ArmorStandPacket> aspList = new ArrayList<ArmorStandPacket>();
		Location middle = lutil.getCenter(location);
		middle.add(0, -1.05, 0);
		middle.setYaw(lutil.FaceToYaw(b));
		
		Location l1 = lutil.getRelativ(middle, b, .25, .4);
		ArmorStandPacket packet = manager.createArmorStand(obj, l1);
		packet.getInventory().setItemInHand(new ItemStack(Material.LADDER));
		packet.setPose(lutil.degresstoRad(new EulerAngle(50, 0, 0)), BodyPart.RIGHT_ARM);
		aspList.add(packet);
		
		l1 = lutil.getRelativ(middle, b.getOppositeFace(), .25, .4);
	    packet = manager.createArmorStand(obj, l1);
		packet.getInventory().setItemInHand(new ItemStack(Material.LADDER));
		packet.setPose(lutil.degresstoRad(new EulerAngle(50, 0, 0)), BodyPart.RIGHT_ARM);
		aspList.add(packet);
		
		l1 = middle.clone();
		l1.add(0, -.65, 0);
		packet = manager.createArmorStand(obj, l1);
		packet.getInventory().setHelmet(new ItemStack(Material.WOOD_PLATE));
		aspList.add(packet);
		
		middle.add(0,-0.6,0);
		middle.setYaw(lutil.FaceToYaw(b.getOppositeFace()));
		packet = manager.createArmorStand(obj, middle);
		packet.setName("#SITZ#");
		aspList.add(packet);
		
		for(ArmorStandPacket as : aspList){
			as.setInvisible(true);
			as.setBasePlate(false);
			as.setGravity(false);
		}
		
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

}
