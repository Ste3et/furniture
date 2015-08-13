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
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
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

public class tent_2 extends Furniture implements Listener{

	Location loc;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	
	Integer id;
	List<Block> block = new ArrayList<Block>();
	Plugin plugin;
	
	Location bedLoc;
	
	public ObjectID getObjectID(){return this.obj;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public tent_2(Location location, FurnitureLib lib, Plugin plugin, ObjectID id){
		super(location, lib, plugin, id);
		this.lutil = main.getLocationUtil();
		this.b = lutil.yawToFace(location.getYaw());
		this.loc = location.getBlock().getLocation();
		this.loc.setYaw(location.getYaw());
		this.w = location.getWorld();
		this.manager = lib.getFurnitureManager();
		this.lib = lib;
		this.plugin = plugin;
		if(b.equals(BlockFace.WEST)){location=lutil.getRelativ(location, b, 1D, 0D);}
		if(b.equals(BlockFace.NORTH)){location=lutil.getRelativ(location, b, 1D, 1D);}
		if(b.equals(BlockFace.EAST)){location=lutil.getRelativ(location, b, 0D, 1D);}
		this.obj = id;
		if(id.isFinish()){
			Bukkit.broadcastMessage(b.name());
			
			Location loca = loc.clone();
			
			switch (b) {
			case NORTH: loca=lutil.getRelativ(loca, b, -1D, -1D); break;
			case EAST: loca=lutil.getRelativ(loca, b, 0D, -1D); break;
			case WEST: loca=lutil.getRelativ(loca, b, -1D, 0D); break;
			default:break;
			}
			
			setBlock(loca);
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(location);
	}
	
	
	public void spawn(Location loc){
		List<ArmorStandPacket> aspl = new ArrayList<ArmorStandPacket>();
		loc=lutil.getRelativ(loc, b, -.91D, -0.75D);
		Location LeftLocation = loc;
		LeftLocation.add(0,-.75,0);
		Location RightLocation = lutil.getRelativ(LeftLocation, b, 0D, -4.55D);
		Location MiddleLocation = lutil.getRelativ(LeftLocation, b, 0D, -2.27D).add(0,2.4,0);
		Location LeftSart = lutil.getRelativ(LeftLocation, b, 3.87D, -.2D);
		Location RightSart = lutil.getRelativ(RightLocation, b, 3.87D, .2D);
		Double d = .0;
		
		for(int i = 0; i<=8;i++){
			Location loc1= lutil.getRelativ(LeftLocation, b, d, 0D);
			loc1.setYaw(lutil.FaceToYaw(b));
			ArmorStandPacket as = manager.createArmorStand(obj, loc1);
			as.getInventory().setHelmet(new ItemStack(Material.LOG));
			as.setSmall(true);
			as.setPose(new EulerAngle(1.568, 0, 0), BodyPart.HEAD);
			aspl.add(as);
			
			Location loc2= lutil.getRelativ(RightLocation, b, d, 0D);
			loc2.setYaw(lutil.FaceToYaw(b));
			as = manager.createArmorStand(obj, loc2);
			as.getInventory().setHelmet(new ItemStack(Material.LOG));
			as.setSmall(true);
			as.setPose(new EulerAngle(1.568, 0, 0), BodyPart.HEAD);
			aspl.add(as);
			
			Location loc3= lutil.getRelativ(MiddleLocation, b, d, 0D);
			loc3.setYaw(lutil.FaceToYaw(b));
			
			as = manager.createArmorStand(obj, loc3);
			as.getInventory().setHelmet(new ItemStack(Material.LOG));
			as.setSmall(true);
			as.setPose(new EulerAngle(1.568, 0, 0), BodyPart.HEAD);
			aspl.add(as);

			d+=.43;
		}

		d = .0;
		Double l = -.25;
		for(int i = 0;i<=4;i++){
			setRow(LeftSart, .62, l, d, 5,new EulerAngle(0, 0, .79), aspl);
			d-=.435;
			l+=.44;
		}
		
		d = .0;
		l = -.25;
		for(int i = 0;i<=4;i++){
			setRow(RightSart, .62, l, d, 5,new EulerAngle(0, 0, -.79), aspl);
			d+=.435;
			l+=.44;
		}

		for(ArmorStandPacket packet : aspl){
			packet.setInvisible(true);
			packet.setGravity(false);
		}
		setBlock(this.loc);
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@SuppressWarnings("deprecation")
	public void setBlock(Location loc){
		Location b1 = lutil.getRelativ(loc, b, 1D, -2D).add(0, 0, 0);
		Location b2 = lutil.getRelativ(loc, b, 2D, -3D).add(0, 0, 0);
		if(!b2.getBlock().getType().equals(Material.CHEST)){
			b2.setYaw(lutil.FaceToYaw(b));
			b2.getBlock().setType(Material.CHEST);
			BlockState chest = b2.getBlock().getState(); 
			 switch (b){
			 
			 case SOUTH:
			 chest.setRawData((byte) 0x3);break;
			  
			 case NORTH:
			 chest.setRawData((byte) 0x2);break;
			  
			 case EAST:
			 chest.setRawData((byte) 0x5);break;
			  
			 case WEST:
			 chest.setRawData((byte) 0x4);break;
			 default: chest.setRawData((byte) 0x3);break;
			  
			 }
			chest.update(true, true);
		}

		
		
		bedLoc = lutil.setBed(this.b, b1);
		
		block.add(b1.getWorld().getBlockAt(b1));
		block.add(b2.getWorld().getBlockAt(b2));
	}
	
	public void setRow(Location loc, double x,double y, double z, int replay, EulerAngle angle, List<ArmorStandPacket> list){
		Double d = .0;
		for(int i = 0; i<=replay;i++){
			Location loc1= lutil.getRelativ(loc, b, -3.55+d, z);
			loc1.setYaw(lutil.FaceToYaw(b));
			loc1.add(0, y,0);
			ArmorStandPacket packet = manager.createArmorStand(obj, loc1);
			packet.getInventory().setHelmet(new ItemStack(Material.CARPET));
			packet.setPose(angle, BodyPart.HEAD);
			list.add(packet);
			d+=x;
		}
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.canBuild()){return;}
		if(!e.getID().equals(obj)){return;}
		e.setCancelled(true);
		for(Block bl : block){
			bl.setType(Material.AIR);
		}
		e.remove();
		obj=null;
	}
	
	@EventHandler
	public void onFurnitureClick(final FurnitureClickEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		e.setCancelled(true);
		Player p = e.getPlayer();
		
		if(!p.getItemInHand().getType().equals(Material.INK_SACK)){
			
			for(Block b : block){
				if(b.getType().equals(Material.CHEST)){
					Chest c = (Chest) b.getState();
					e.getPlayer().openInventory(c.getBlockInventory());
				}
			}
		}else{
			Boolean canBuild = lib.canBuild(p, e.getLocation());
			Material m = Material.CARPET;
			color(p, canBuild, m);
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
						if(!p.getGameMode().equals(GameMode.CREATIVE)){Amount--;}
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
