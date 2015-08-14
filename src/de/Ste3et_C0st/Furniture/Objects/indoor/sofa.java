package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
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
import de.Ste3et_C0st.FurnitureLib.main.Type.EventType;

public class sofa extends Furniture implements Listener {
	
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
	
	public sofa(Location location, FurnitureLib lib, Plugin plugin, ObjectID id){
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
		place = .3;
		spawn(location);
	}
	
	ItemStack is;
	Double place;
	
	public void spawn(Location loc){
		List<ArmorStandPacket> aspList = new ArrayList<ArmorStandPacket>();
		List<ArmorStandPacket> sitz = new ArrayList<ArmorStandPacket>();
		Integer lengt = 3;
		is = new ItemStack(Material.CARPET);
		BlockFace b = lutil.yawToFace(loc.getYaw()).getOppositeFace();
		
		Integer x = (int) loc.getX();
		Integer y = (int) loc.getY();
		Integer z = (int) loc.getZ();
		loc.setX(x);
		loc.setY(y);
		loc.setZ(z);
		
		if(b.equals(BlockFace.WEST)){loc = lutil.getRelativ(loc, b, .0, -1.0);}
		if(b.equals(BlockFace.SOUTH)){loc = lutil.getRelativ(loc, b, -1.0, -1.0);}
		if(b.equals(BlockFace.EAST)){loc = lutil.getRelativ(loc, b, -1.0, .0);}
			Location looking = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() -1.4 , loc.getBlockZ());
			Location feet1 = lutil.getRelativ(looking, b, place, .2D);
			Location feet2 = lutil.getRelativ(looking, b, place, lengt.doubleValue()-.2D);
			Location feet3 = lutil.getRelativ(looking, b, place + .5, .2D);
			Location feet4 = lutil.getRelativ(looking, b, place + .5, lengt.doubleValue()-.2D);
			
			ArmorStandPacket asp = manager.createArmorStand(obj, feet1);
			asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
			aspList.add(asp);
			asp = manager.createArmorStand(obj, feet2);
			asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
			aspList.add(asp);
			asp = manager.createArmorStand(obj, feet3);
			asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
			aspList.add(asp);
			asp = manager.createArmorStand(obj, feet4);
			asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
			aspList.add(asp);
			Location carpetHight = new Location(looking.getWorld(), loc.getBlockX(), loc.getBlockY() -1 , loc.getBlockZ());
			carpetHight.setYaw(lutil.FaceToYaw(b));
			carpetHight = lutil.getRelativ(carpetHight, b, .25,.3);
			Double d = .02;
			float facing = lutil.FaceToYaw(b);
			Integer j = 0;
			for(Double i = .0; i<=lengt; i+=0.65){
				Location carpet = lutil.getRelativ(carpetHight, b, place,(double) d);
				carpet.setYaw(facing);
				String s = "";
				if(j==0||j==1){s="#SITZPOS:1#";}
				if(j==2){s="#SITZPOS:2#";}
				if(j==3||j==4){s="#SITZPOS:3#";}
				asp = manager.createArmorStand(obj, carpet);
				asp.getInventory().setHelmet(is);
				asp.setName(s);
				aspList.add(asp);
				sitz.add(asp);
				Location location = lutil.getRelativ(carpetHight, b, place-.25,(double) d);
				location.setYaw(facing);
				
				asp = manager.createArmorStand(obj, location);
				asp.setPose(new EulerAngle(1.57, .0, .0), BodyPart.HEAD);
				asp.getInventory().setHelmet(is);
				asp.setName(s);
				aspList.add(asp);
				if(d<=0D){d = 0.00;}
				d+=.58;
				j++;
			}
			
			Float yaw1= facing;
			Float yaw2= facing;
			Location last = lutil.getRelativ(sitz.get(sitz.size()-1).getLocation(), b, 0D, 0.26D);
			last.setYaw(yaw1+90);
			Location first = lutil.getRelativ(new Location(loc.getWorld(), loc.getX(), last.getY(), loc.getZ()), b, place+.25, 0.07D);
			first.setYaw(yaw2-90);
			
			asp = manager.createArmorStand(obj, first.add(0,-.05,0));
			asp.getInventory().setHelmet(is);
			asp.setPose(new EulerAngle(1.57, .0, .0), BodyPart.HEAD);
			asp.setName("#SITZPOS:1#");
			aspList.add(asp);
			
			asp = manager.createArmorStand(obj, last.add(0,-.05,0));
			asp.getInventory().setHelmet(is);
			asp.setPose(new EulerAngle(1.57, .0, .0), BodyPart.HEAD);
			asp.setName("#SITZPOS:3#");
			aspList.add(asp);
			
			Location start = lutil.getRelativ(looking, b, .45, .55);
			
			for(int i = 0; i<=2;i++){
				Location location = lutil.getRelativ(start, b, place, i*.95D);
				location.setYaw(lutil.FaceToYaw(b));
				location.add(0,.2,0);
				asp = manager.createArmorStand(obj, location);
				asp.setName("#SITZ" + i + "#");
				aspList.add(asp);
			}
			
			for(ArmorStandPacket asps : aspList){
				asps.setInvisible(true);
				asps.setGravity(false);
				asps.setBasePlate(false);
			}
			manager.send(obj);
			Bukkit.getPluginManager().registerEvents(this, plugin);
		}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		e.setCancelled(true);
		final Player p = e.getPlayer();
		if(p.getItemInHand().getType().equals(Material.INK_SACK)){
			Boolean canBuild = lib.canBuild(p, e.getLocation(), EventType.INTERACT);
			Material m = Material.CARPET;
			color(p, canBuild, m);
		}else{
			ArmorStandPacket packet = e.getArmorStandPacket();
			switch (packet.getName()) {
			case "#SITZPOS:1#": sit("#SITZ0#", p);break;
			case "#SITZPOS:2#": sit("#SITZ1#", p);break;
			case "#SITZPOS:3#": sit("#SITZ2#", p);break;
			case "#SITZ0#" : sit("#SITZ0#", p);break;
			case "#SITZ1#" : sit("#SITZ1#", p);break;
			case "#SITZ2#" : sit("#SITZ2#", p);break;
			default: sit("#SITZ0#", p);break;
			}
			
		}
	}
	
	private void sit(String s, Player p){
		for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
			if(packet.getName().equalsIgnoreCase(s) && packet.getPessanger() == null){
				packet.setPessanger(p);
				packet.update();
				return;
			}
		}
	}
	
	private void color(Player p, boolean canBuild, Material m){
		if(!canBuild){return;}
		ItemStack is = p.getItemInHand();
		Integer Amount = is.getAmount();
		List<ArmorStandPacket> asp = manager.getArmorStandPacketByObjectID(obj);
		short color = lutil.getFromDey(is.getDurability());
		for(ArmorStandPacket packet : asp){
			if(packet.getInventory().getHelmet()!=null&&packet.getInventory().getHelmet().getType().equals(m)){
				if(Amount>0){
					ItemStack is2 = packet.getInventory().getHelmet();
					if(is2.getDurability() != color){
						is2.setDurability(color);
						packet.getInventory().setHelmet(is2);
						if(!p.getGameMode().equals(GameMode.CREATIVE) || !lib.useGamemode()){Amount--;}
					}
				}
			}
		}

		manager.updateFurniture(obj);
		if(p.getGameMode().equals(GameMode.CREATIVE) && lib.useGamemode()) return;
		Integer i = p.getInventory().getHeldItemSlot();
		ItemStack item = p.getItemInHand();
		item.setAmount(Amount);
		p.getInventory().setItem(i, item);
		p.updateInventory();
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.canBuild()){return;}
		if(!e.getID().equals(obj)){return;}
		e.setCancelled(true);
		for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
			if(packet.getPessanger()!=null){
				packet.unleash();
				packet.update();
			}
		}
		e.remove();
		obj=null;
	}
}
