package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.Arrays;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.ArmorStandPacket;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.EventType;

public class fance extends Furniture implements Listener{

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
	Block block;
	Material m;
	
	public fance(Location location, FurnitureLib lib, Plugin plugin, ObjectID id){
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
			setBlock();
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(location);
		setBlock();
	}
	
	public void spawn(Location location){
		this.m = Material.STONE;
		Location locat = loc.clone();
		locat=lutil.getCenter(locat);
		locat.add(0, -1.2, 0);
		locat.setYaw(lutil.FaceToYaw(b.getOppositeFace()));
		for(int i = 0; i<=2;i++){
			Location loc = locat.clone();
			ArmorStandPacket packet = manager.createArmorStand(obj, loc);
			packet.getInventory().setHelmet(new ItemStack(m,0,(short) 0));
			packet.setGravity(false);
			packet.setInvisible(true);
			packet.setBasePlate(false);
			packet.setSmall(true);
			locat.add(0, .44, 0);
		}
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	private void onBlockBreak(BlockBreakEvent e){
		if(obj==null){return;}
		if(block==null){return;}
		if(!e.getBlock().getLocation().equals(block.getLocation())){return;}
		if(!lib.canBuild(e.getPlayer(), e.getBlock().getLocation(), EventType.BREAK)){return;}
		this.block.setType(Material.AIR);
		this.obj.remove(e.getPlayer());
		block=null;
		manager.remove(obj);
		obj=null;
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.canBuild()){return;}
		if(!e.getID().equals(obj)){return;}
		e.setCancelled(true);
		if(this.block!=null) this.block.setType(Material.AIR);
		e.remove();
		obj=null;
		block.setType(Material.AIR);
		block=null;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onInteract(PlayerInteractEvent e){
		if(obj==null) return;
		if(e.isCancelled()) return;
		if(this.block==null) return;
		if(e.getAction()==null)return;
		if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if(!e.getClickedBlock().getLocation().equals(this.block.getLocation())) return;
		e.setCancelled(true);
		Player p = e.getPlayer();
		ItemStack is = p.getItemInHand();
		if(is==null||!is.getType().isBlock()) return;
		Material m = p.getItemInHand().getType();
		byte data = (byte) is.getDurability();
		if(matList.contains(m)){
			this.block.setType(m); 
			this.block.setData(data);
			remove(p, is);
			return;
		}else if(main.materialWhiteList.contains(m)){
			setTypes(is);
			remove(p, is);
			manager.updateFurniture(obj);
			return;
		}
	}
	
	private void remove(Player p, ItemStack is){
		if(!p.getGameMode().equals(GameMode.CREATIVE)){
			Integer i = p.getInventory().getHeldItemSlot();
			is.setAmount(is.getAmount()-1);
			p.getInventory().setItem(i, is);
			p.updateInventory();
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(this.block==null) return;
		if(!e.canBuild()){return;}
		if(!e.getID().equals(obj)){return;}
		e.setCancelled(true);
		Player p = e.getPlayer();
		ItemStack is = p.getItemInHand();
		if(is==null || !is.getType().isBlock()) return;
		Material m = p.getItemInHand().getType();
		byte data = (byte) is.getDurability();
		if(matList.contains(m)){
			this.block.setType(m); 
			this.block.setData(data);
			remove(p, is);
			return;
		}else if(main.materialWhiteList.contains(m)){
			setTypes(is);
			remove(p, is);
			manager.updateFurniture(obj);
			return;
		}
	}
	
	private void setTypes(ItemStack is){
		for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
			packet.getInventory().setHelmet(is);
		}
	}
	
	private void setBlock(){
		Location location = loc.clone();
		this.block = location.getBlock();
		if(this.block.getType()==null||this.block.getType().equals(Material.AIR)||!this.block.getType().equals(Material.FENCE)){
			if(!this.matList.contains(this.block.getType())){
				this.block.setType(Material.FENCE);
			}
		}
	}
	
	
}
