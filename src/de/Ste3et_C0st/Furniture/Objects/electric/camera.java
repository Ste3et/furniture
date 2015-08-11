package de.Ste3et_C0st.Furniture.Objects.electric;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Camera.Utils.RenderClass;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureLateSpawnEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.ArmorStandPacket;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;

public class camera implements Listener{
	Location loc;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	Plugin plugin;
	
	private String id;
	public String getID(){return this.id;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public camera(Location location, FurnitureLib lib, String name, Plugin plugin, ObjectID id, Player player){
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
			if(player!=null){
				FurnitureLateSpawnEvent lateSpawn = new FurnitureLateSpawnEvent(player, obj, obj.getProjectOBJ(), location);
				Bukkit.getServer().getPluginManager().callEvent(lateSpawn);
			}
		}
		spawn(location);
	}
	
	public void spawn(Location location){
		List<ArmorStandPacket> aspList = new ArrayList<ArmorStandPacket>();
		BlockFace b = lutil.yawToFace(location.getYaw()).getOppositeFace();
		Location center = lutil.getCenter(location);
		Location gehäuse = lutil.getRelativ(center, b, 0D, 0D).add(0,-1.0,0);
		Location gehäuse2 = lutil.getRelativ(center, b, 0D, 0D).add(0,-0.4,0);
		Location fokus = lutil.getRelativ(center, b, .15D, 0D).add(0,-.24,0);
		Location search = lutil.getRelativ(center, b, .15D, 0D).add(0,-.7,0);
		Location button = lutil.getRelativ(center, b, -.15D, -.15D).add(0,.08,0);
		
		Location feet1 = lutil.getRelativ(center, b, .5D, .4D).add(0,-.9,0);
		Location feet2 = lutil.getRelativ(center, b, -.2D, -.7D).add(0,-.9,0);
		Location feet3 = lutil.getRelativ(center, b, -.7D, .2D).add(0,-.9,0);
		
		gehäuse.setYaw(lutil.FaceToYaw(b));
		fokus.setYaw(lutil.FaceToYaw(b));
		search.setYaw(lutil.FaceToYaw(b));
		button.setYaw(lutil.FaceToYaw(b));
		feet1.setYaw(lutil.FaceToYaw(b));
		feet2.setYaw(lutil.FaceToYaw(b) + 180 - 45);
		feet3.setYaw(lutil.FaceToYaw(b) + 180 + 45);
		
		ArmorStandPacket as = manager.createArmorStand(obj, gehäuse);
		as.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 15));
		aspList.add(as);
		
		as = manager.createArmorStand(obj, gehäuse2);
		as.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 15));
		as.setSmall(true);
		aspList.add(as);
		
		as = manager.createArmorStand(obj, fokus);
		as.getInventory().setHelmet(new ItemStack(Material.DISPENSER));
		as.setSmall(true);
		aspList.add(as);
		
		as = manager.createArmorStand(obj, search);
		as.getInventory().setHelmet(new ItemStack(Material.TRIPWIRE_HOOK));
		aspList.add(as);
		
		as = manager.createArmorStand(obj, button);
		as.getInventory().setHelmet(new ItemStack(Material.WOOD_BUTTON));
		as.setSmall(true);
		aspList.add(as);
		
		as = manager.createArmorStand(obj, feet1);
		as.getInventory().setItemInHand(new ItemStack(Material.STICK));
		as.setPose(new EulerAngle(1.2, 0, 0), BodyPart.RIGHT_ARM);
		aspList.add(as);
		
		as = manager.createArmorStand(obj, feet2);
		as.getInventory().setItemInHand(new ItemStack(Material.STICK));
		as.setPose(new EulerAngle(1.2, 0, 0), BodyPart.RIGHT_ARM);
		aspList.add(as);
		
		as = manager.createArmorStand(obj, feet3);
		as.getInventory().setItemInHand(new ItemStack(Material.STICK));
		as.setPose(new EulerAngle(1.2, 0, 0), BodyPart.RIGHT_ARM);
		aspList.add(as);
		
		for(ArmorStandPacket asp : aspList){
			asp.setInvisible(true);
			asp.setGravity(false);
		}
		
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
		//lib.saveObjToDB(obj);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onClick(FurnitureClickEvent e){
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		if(obj==null){return;}
		Player p = e.getPlayer();
		Location pLocation = lutil.getRelativ(p.getLocation().getBlock().getLocation(), b, -1D, 0D).clone();
		Location locCopy = getLocation().clone();
		if(pLocation.equals(locCopy) && lutil.yawToFace(p.getLocation().getYaw()).getOppositeFace().equals(b)){
			if(!p.getInventory().getItemInHand().getType().equals(Material.MAP)){return;}
			MapView view = Bukkit.getMap(p.getItemInHand().getDurability());
			Location l = getLocation();
			l.setYaw(lutil.FaceToYaw(b.getOppositeFace()));
			Iterator<MapRenderer> iter = view.getRenderers().iterator();
            while(iter.hasNext()){
                view.removeRenderer(iter.next());
            }
            try{
                RenderClass renderer = new RenderClass(l);
                view.addRenderer(renderer);
            }catch (Exception ex){}
		}
	}
	
	@EventHandler
	private void onBreak(FurnitureBreakEvent e){
		if(e.isCancelled()){return;}
		if(!e.canBuild()){return;}
		if(!e.getID().equals(obj)){return;}
		if(obj==null){return;}
		e.remove();
		obj=null;
	}
	
}
