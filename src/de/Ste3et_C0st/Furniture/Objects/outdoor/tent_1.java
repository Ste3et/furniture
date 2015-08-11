package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

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

public class tent_1 implements Listener{

	Location loc;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	Integer id;
	Block block;
	Plugin plugin;
	
	public tent_1(Location location, FurnitureLib lib, String name, Plugin plugin, ObjectID id, Player player){
		this.lutil = main.getLocationUtil();
		this.b = lutil.yawToFace(location.getYaw());
		this.loc = location.getBlock().getLocation();
		this.loc.setYaw(location.getYaw());
		this.w = location.getWorld();
		this.manager = lib.getFurnitureManager();
		this.lib = lib;
		this.plugin = plugin;
		if(b.equals(BlockFace.WEST)){loc=lutil.getRelativ(loc, b, 1D, 0D);}
		if(b.equals(BlockFace.NORTH)){loc=lutil.getRelativ(loc, b, 1D, 1D);}
		if(b.equals(BlockFace.EAST)){loc=lutil.getRelativ(loc, b, 0D, 1D);}
		
		if(id!=null){
			this.obj = id;
			setblock();
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}else{
			this.obj = new ObjectID(name, plugin.getName(), location);
			if(player!=null){
				FurnitureLateSpawnEvent lateSpawn = new FurnitureLateSpawnEvent(player, obj, obj.getProjectOBJ(), location);
				Bukkit.getServer().getPluginManager().callEvent(lateSpawn);
			}
		}
		spawn(loc);
	}
	
	public void setblock(){
		Location blockLocation = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		if(b.equals(BlockFace.SOUTH)){
			this.block = blockLocation.getWorld().getBlockAt(lutil.getRelativ(blockLocation, b, 2D, -2D));
			this.block.setType(Material.WORKBENCH);
		}else if(b.equals(BlockFace.WEST)){
			this.block = blockLocation.getWorld().getBlockAt(lutil.getRelativ(blockLocation, b, 1D, -2D));
			this.block.setType(Material.WORKBENCH);
		}else if(b.equals(BlockFace.NORTH)){
			this.block = blockLocation.getWorld().getBlockAt(lutil.getRelativ(blockLocation, b, 1D, -3D));
			this.block.setType(Material.WORKBENCH);
		}else if(b.equals(BlockFace.EAST)){
			this.block = blockLocation.getWorld().getBlockAt(lutil.getRelativ(blockLocation, b, 2D, -3D));
			this.block.setType(Material.WORKBENCH);
		}
	}
	
	
	private Location getNew(Location loc, BlockFace b){
		if(b.equals(BlockFace.WEST)){loc=lutil.getRelativ(loc, b, -1D, 0D);}
		if(b.equals(BlockFace.NORTH)){loc=lutil.getRelativ(loc, b, -1D, -1D);}
		if(b.equals(BlockFace.EAST)){loc=lutil.getRelativ(loc, b, 0D, -1D);}
		return loc;
	}
	
	public void spawn(Location loc){
		List<ArmorStandPacket> aspList = new ArrayList<ArmorStandPacket>();
		Location loc_1 = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		Location loc_2 = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		Location loc_3 = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		
		Location karabine1 = getNew(lutil.getRelativ(loc, b, 0D, 0D), b);
		Location karabine2 = getNew(lutil.getRelativ(loc, b, 3D, 0D), b);
		Location karabine3 = getNew(lutil.getRelativ(loc, b, 3D, -4D), b);
		Location karabine4 = getNew(lutil.getRelativ(loc, b, 0D, -4D), b);
		
		
		karabine1 = lutil.getCenter(karabine1);
		karabine2 = lutil.getCenter(karabine2);
		karabine3 = lutil.getCenter(karabine3);
		karabine4 = lutil.getCenter(karabine4);
		karabine1.setYaw(lutil.FaceToYaw(b)+90);
		karabine2.setYaw(lutil.FaceToYaw(b)+90);
		karabine3.setYaw(lutil.FaceToYaw(b)+90);
		karabine4.setYaw(lutil.FaceToYaw(b)+90);
		
		Location saveLoc = lutil.getRelativ(loc_1, b, -.55D, -0.6);
		saveLoc.add(0,-1.25,0);
		saveLoc.setYaw(lutil.FaceToYaw(b) -90);
		
		Location saveLoc2 = lutil.getRelativ(loc_2, b, -4.27, -4.4);
		saveLoc2.add(0,-1.25,0);
		saveLoc2.setYaw(lutil.FaceToYaw(b) -90);
		
		Location saveLoc3 = lutil.getRelativ(loc_3, b, -8D, -2.5D);
		saveLoc3.add(0,.64,0);
		saveLoc3.setYaw(lutil.FaceToYaw(b) -90);
		Double d = .0;

		setblock();
		
		for(int i = 0; i<=5;i++){
			Location loc1= lutil.getRelativ(saveLoc, b, d, 0D);
			Location loc2= lutil.getRelativ(saveLoc, b, d, -.48).add(0,.3,0);
			Location loc3= lutil.getRelativ(saveLoc, b, d, -.86).add(0,.81,0);
			Location loc4= lutil.getRelativ(saveLoc, b, d, -1.08).add(0,1.33,0);
			Location loc5= lutil.getRelativ(saveLoc, b, d, -1.38).add(0,1.86,0);
			
			loc1.setYaw(lutil.FaceToYaw(b.getOppositeFace()));
			loc2.setYaw(lutil.FaceToYaw(b.getOppositeFace()));
			loc3.setYaw(lutil.FaceToYaw(b.getOppositeFace()));
			loc4.setYaw(lutil.FaceToYaw(b.getOppositeFace()));
			loc5.setYaw(lutil.FaceToYaw(b.getOppositeFace()));
			
			loc.add(loc1);
			loc.add(loc2);
			loc.add(loc3);
			loc.add(loc4);
			loc.add(loc5);
			
			ArmorStandPacket asp = manager.createArmorStand(obj, loc1);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -.2), BodyPart.HEAD);
			aspList.add(asp);
			asp = manager.createArmorStand(obj, loc2);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -7.2), BodyPart.HEAD);
			aspList.add(asp);
			asp = manager.createArmorStand(obj, loc3);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -7.2), BodyPart.HEAD);
			aspList.add(asp);
			asp = manager.createArmorStand(obj, loc4);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -7.7), BodyPart.HEAD);
			aspList.add(asp);
			asp = manager.createArmorStand(obj, loc5);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -.7), BodyPart.HEAD);
			aspList.add(asp);
			d+=.62;
		}

		for(int i = 0; i<=5;i++){
			Location loc1= lutil.getRelativ(saveLoc2, b, d, .02D);
			Location loc2= lutil.getRelativ(saveLoc2, b, d, .48).add(0,.3,0);
			Location loc3= lutil.getRelativ(saveLoc2, b, d, .86).add(0,.81,0);
			Location loc4= lutil.getRelativ(saveLoc2, b, d, 1.08).add(0,1.33,0);
			Location loc5= lutil.getRelativ(saveLoc2, b, d, 1.38).add(0,1.86,0);
			
			loc1.setYaw(lutil.FaceToYaw(b));
			loc2.setYaw(lutil.FaceToYaw(b));
			loc3.setYaw(lutil.FaceToYaw(b));
			loc4.setYaw(lutil.FaceToYaw(b));
			loc5.setYaw(lutil.FaceToYaw(b));
			
			loc.add(loc1);
			loc.add(loc2);
			loc.add(loc3);
			loc.add(loc4);
			loc.add(loc5);
			
			ArmorStandPacket asp = manager.createArmorStand(obj, loc1);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -.2), BodyPart.HEAD);
			aspList.add(asp);
			asp = manager.createArmorStand(obj, loc2);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -7.2), BodyPart.HEAD);
			aspList.add(asp);
			asp = manager.createArmorStand(obj, loc3);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -7.2), BodyPart.HEAD);
			aspList.add(asp);
			asp = manager.createArmorStand(obj, loc4);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -7.7), BodyPart.HEAD);
			aspList.add(asp);
			asp = manager.createArmorStand(obj, loc5);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -.7), BodyPart.HEAD);
			aspList.add(asp);
			d+=.62;
		}
		
		//middle
		for(int i = 0; i<=5;i++){
			Location loc1= lutil.getRelativ(saveLoc3, b, d, 0D);
			loc1.setYaw(lutil.FaceToYaw(b.getOppositeFace()));
			loc.add(loc1);
			
			ArmorStandPacket asp = manager.createArmorStand(obj, loc1);
			asp.getInventory().setHelmet( new ItemStack(Material.WOOD_STEP));
			aspList.add(asp);

			d+=.62;
		}
		
		ArmorStandPacket asp = manager.createArmorStand(obj, karabine1.add(0,-1.9,0));
		asp.getInventory().setHelmet(new ItemStack(Material.TRIPWIRE_HOOK));
		aspList.add(asp);
		asp = manager.createArmorStand(obj, karabine2.add(0,-1.9,0));
		asp.getInventory().setHelmet(new ItemStack(Material.TRIPWIRE_HOOK));
		aspList.add(asp);
		asp = manager.createArmorStand(obj, karabine3.add(0,-1.9,0));
		asp.getInventory().setHelmet(new ItemStack(Material.TRIPWIRE_HOOK));
		aspList.add(asp);
		asp = manager.createArmorStand(obj, karabine4.add(0,-1.9,0));
		asp.getInventory().setHelmet(new ItemStack(Material.TRIPWIRE_HOOK));
		aspList.add(asp);
		
		Location crafting = lutil.getCenter(block.getLocation());
		crafting.setYaw(lutil.FaceToYaw(b)+90);
		
		asp = manager.createArmorStand(obj, crafting.add(0,-1,0));
		asp.getInventory().setHelmet(new ItemStack(Material.LADDER));
		aspList.add(asp);
		
		asp = manager.createArmorStand(obj, crafting.add(0,.62,0));
		asp.getInventory().setHelmet(new ItemStack(Material.LADDER));
		aspList.add(asp);
		
		for(ArmorStandPacket packet : aspList){
			packet.setInvisible(true);
			packet.setGravity(false);
		}
		
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void onBreak(FurnitureBreakEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.canBuild()){return;}
		if(!e.getID().equals(obj)){return;}
		e.setCancelled(true);
		block.setType(Material.AIR);
		e.remove();
		obj=null;
	}
	
	@EventHandler
	private void onClick(FurnitureClickEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		e.setCancelled(true);
		final Player p = e.getPlayer();
		if(!p.getItemInHand().getType().equals(Material.INK_SACK)){
			p.openWorkbench(this.block.getLocation(), true);
		}else{
			Boolean canBuild = lib.canBuild(p, e.getLocation());
			Material m = Material.CARPET;
			color(p, canBuild, m);
		}
	}
	
	@EventHandler
	private void onBlockBreak(BlockBreakEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!lib.canBuild(e.getPlayer(), loc)){return;}
		if(obj==null){return;}
		if(this.block!=null&&e.getBlock().equals(block)){this.block.setType(Material.AIR);this.block=null;}
		this.obj.remove(e.getPlayer());
		obj=null;
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
}
