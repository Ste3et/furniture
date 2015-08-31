package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
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

public class sunshade extends Furniture implements Listener{

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
	
	List<Material> matList = Arrays.asList(
			Material.SPRUCE_FENCE,
			Material.BIRCH_FENCE,
			Material.JUNGLE_FENCE,
			Material.DARK_OAK_FENCE,
			Material.ACACIA_FENCE,
			Material.COBBLE_WALL,
			Material.NETHER_FENCE);
	Material m;
	Integer timer;
	Block block;
	
	public sunshade(FurnitureLib lib, Plugin plugin, ObjectID id){
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
			setblock();
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		setblock();
		spawn(id.getStartLocation());
	}
	
	private void setblock(){
		Location loc = this.loc.clone();
		loc.add(0, 2, 0);
		block = loc.getBlock();
		block.setType(Material.BARRIER);
	}
	
	public void spawn(Location location){
		Location center = lutil.getCenter(location).clone();
		List<ArmorStandPacket> asList = new ArrayList<ArmorStandPacket>();
		center.add(0, -1.1, 0);
		
		for(int i = 0; i<=2;i++){
			Location loc = lutil.getRelativ(center.clone(), b, .47, .38).add(0, .88*i, 0);
			ArmorStandPacket packet = manager.createArmorStand(obj, loc);
			packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
			packet.setPose(new EulerAngle(1.39, 0, 0), BodyPart.RIGHT_ARM);
			asList.add(packet);
		}
		
		center.add(0, 1.758, 0);
		ArmorStandPacket aspacket = manager.createArmorStand(obj, center);
		aspacket.getInventory().setHelmet(new ItemStack(Material.CARPET));
		aspacket.setName("#TOP#");
		asList.add(aspacket);
		
		Location loc = center.clone(); 
		loc.add(0, 0.3, 0);
		ItemStack is = new ItemStack(Material.BANNER);
		BannerMeta banner = (BannerMeta) is.getItemMeta();
		banner.setBaseColor(DyeColor.LIME);
		banner.addPattern(new Pattern(DyeColor.YELLOW, PatternType.TRIANGLE_BOTTOM));
		banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM));
		is.setItemMeta(banner);
		
		for(int i = 0; i<=17; i++){
			loc.setYaw(i*21);
			ArmorStandPacket packet = manager.createArmorStand(obj, loc.clone());
			packet.getInventory().setHelmet(is);
			packet.setPose(new EulerAngle(-3.054, 0, 0), BodyPart.HEAD);
			packet.setName("#ELEMENT#" + i);
			asList.add(packet);
		}
		
		for(ArmorStandPacket packet : asList){
			packet.setInvisible(true);
			packet.setGravity(false);
			packet.setBasePlate(false);
		}
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		Player p = e.getPlayer();
		ItemStack is = p.getItemInHand();
		if(is==null||!is.getType().equals(Material.BANNER)){
			if(isRunning()){return;}
			if(!isOpen()){
				open();
			}else{
				close();
			}
		}else{
			if(isRunning()){return;}
			if(!is.hasItemMeta()){return;}
			ItemStack itemstack = is.clone();
			BannerMeta banner = (BannerMeta) itemstack.getItemMeta();
			Short s = lutil.getfromDyeColor(banner.getBaseColor());
			Short newS = lutil.getFromDey(s);
			
			for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
				if(packet.getName().startsWith("#ELEMENT#")){
					packet.getInventory().setHelmet(itemstack);
				}else if(packet.getName().equalsIgnoreCase("#TOP#")){
					packet.getInventory().setHelmet(new ItemStack(Material.CARPET, 1, newS));
				}
			}
			
			manager.updateFurniture(obj);
			
			if(!p.getGameMode().equals(GameMode.CREATIVE)){
				Integer i = p.getInventory().getHeldItemSlot();
				is.setAmount(is.getAmount()-1);
				p.getInventory().setItem(i, is);
				p.updateInventory();
			}
		}
	}
	
	private boolean isRunning(){
		if(timer!=null)return true;
		return false;
	}
	
	@EventHandler
	private void onInteract(PlayerInteractEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(e.getAction()==null){return;}
		if(e.getClickedBlock()==null){return;}
		if(!e.getClickedBlock().getLocation().equals(block.getLocation())){return;}
		if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
				e.setCancelled(true);
				if(!lib.canBuild(e.getPlayer(), obj, EventType.BREAK)){return;}
				stopTimer();
				for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
					if(packet.getName().equalsIgnoreCase("#ITEM#")){
						if(packet.getInventory().getItemInHand()!=null&&!packet.getInventory().getItemInHand().getType().equals(Material.AIR)){
							ItemStack is = packet.getInventory().getItemInHand();
							w.dropItem(loc, is);
						}
					}
				}
				this.block.setType(Material.AIR);
				this.block = null;
				this.obj.remove(e.getPlayer());
				obj=null;
		}else if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(!lib.canBuild(e.getPlayer(), obj, EventType.INTERACT)){return;}
				Player p = e.getPlayer();
				ItemStack is = p.getItemInHand();
				if(is==null||!is.getType().equals(Material.BANNER)){
					if(isRunning()){return;}
					if(!isOpen()){
						open();
					}else{
						close();
					}
				}else{
					e.setCancelled(true);
					if(isRunning()){return;}
					if(!is.hasItemMeta()){return;}
					ItemStack itemstack = is.clone();
					BannerMeta banner = (BannerMeta) itemstack.getItemMeta();
					Short s = lutil.getfromDyeColor(banner.getBaseColor());
					Short newS = lutil.getFromDey(s);
					
					for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
						if(packet.getName().startsWith("#ELEMENT#")){
							packet.getInventory().setHelmet(itemstack);
						}else if(packet.getName().equalsIgnoreCase("#TOP#")){
							packet.getInventory().setHelmet(new ItemStack(Material.CARPET, 1, newS));
						}
					}
					manager.updateFurniture(obj);
					
					if(!p.getGameMode().equals(GameMode.CREATIVE)){
						Integer i = p.getInventory().getHeldItemSlot();
						is.setAmount(is.getAmount()-1);
						p.getInventory().setItem(i, is);
						p.updateInventory();
					}
				}
		}
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		stopTimer();
		for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
			if(packet.getName().equalsIgnoreCase("#ITEM#")){
				if(packet.getInventory().getItemInHand()!=null&&!packet.getInventory().getItemInHand().getType().equals(Material.AIR)){
					ItemStack is = packet.getInventory().getItemInHand();
					w.dropItem(loc, is);
				}
			}
		}
		e.remove();
		this.block.setType(Material.AIR);
		this.block = null;
		manager.remove(obj);
		obj=null;
	}
	
	private void close(){
		timer = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				try{
					for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
						if(packet.getName().startsWith("#ELEMENT#")){
							if(!isClose(packet)){
								Double x = packet.getAngle(BodyPart.HEAD).getX();
								packet.setPose(new EulerAngle(x-.32, 0, 0), BodyPart.HEAD);
							}else{
								stopTimer();
								return;
							}
							manager.updateFurniture(obj);
						}
					}
				}catch(Exception e){
					stopTimer();reset();
				}

				
			}
		}, 0, 10);
	}
	
	private void open(){
		timer = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				try{
					for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
						if(packet.getName().startsWith("#ELEMENT#")){
							if(!isOpen(packet)){
								Double x = packet.getAngle(BodyPart.HEAD).getX();
								packet.setPose(new EulerAngle(x+.32, 0, 0), BodyPart.HEAD);
							}else{
								stopTimer();
								return;
							}
							manager.updateFurniture(obj);
						}
					}
				}catch(Exception e){
					stopTimer();reset();
				}

				
			}
		}, 0, 10);
	}
	
	private void reset(){
		for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
			if(!isOpen(packet)){
				packet.setPose(new EulerAngle(-3.054, 0, 0), BodyPart.HEAD);
			}
		}
		manager.updateFurniture(obj);
	}
	
	private boolean isClose(ArmorStandPacket packet){
		if(packet.getAngle(BodyPart.HEAD).getX()> -3.054){
			return false;
		}return true;
	}
	
	private boolean isOpen(ArmorStandPacket packet){
		if(packet.getAngle(BodyPart.HEAD).getX()< -1.85){
			return false;
		}return true;
	}
	
	private boolean isOpen(){
		for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
			if(packet.getName().startsWith("#ELEMENT#")){
				if(packet.getAngle(BodyPart.HEAD).getX()< -1.85){
					return false;
				}
			}
		}
		return true;
	}
	
	private void stopTimer(){
		if(timer!=null){
			Bukkit.getScheduler().cancelTask(timer);
			timer=null;
		}
	}
}
