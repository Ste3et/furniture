package de.Ste3et_C0st.Furniture.Objects.outdoor;

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
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;

public class barrels extends Furniture implements Listener {
	
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
	
	public ObjectID getObjectID(){return this.obj;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public barrels(FurnitureLib lib, Plugin plugin, ObjectID id){
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
			this.block = loc.getBlock();
			this.block.setType(Material.CAULDRON);
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(id.getStartLocation());
	}
	
	public void spawn(Location loc){
		this.block = loc.getBlock();
		this.block.setType(Material.CAULDRON);
		
		ArmorStandPacket packet = manager.createArmorStand(obj, lutil.getCenter(loc).add(0,-1.5,0));
		packet.setInvisible(true);
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
		if(!p.getItemInHand().getType().isBlock()&&!p.getItemInHand().getType().equals(Material.AIR)){return;}
		ArmorStandPacket packet = manager.getArmorStandPacketByObjectID(obj).get(0);
		if(packet.getInventory().getHelmet()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
			ItemStack is = packet.getInventory().getHelmet();
			is.setAmount(1);
			w.dropItem(loc, is);
		}
		
		packet.getInventory().setHelmet(p.getItemInHand());
		
		manager.updateFurniture(obj);
		
		if(p.getGameMode().equals(GameMode.CREATIVE) && lib.useGamemode()) return;
		Integer i = e.getPlayer().getInventory().getHeldItemSlot();
		ItemStack item = e.getPlayer().getItemInHand();
		item.setAmount(item.getAmount()-1);
		e.getPlayer().getInventory().setItem(i, item);
		e.getPlayer().updateInventory();
	}
	
	@EventHandler
	private void onInteract(PlayerInteractEvent e){
		if(obj==null){return;}
		if(block==null || obj==null){return;}
		if(e.getClickedBlock()==null){return;}
		if(!e.getClickedBlock().getLocation().equals(block.getLocation())){return;}
		if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){return;}
		if(!lib.canBuild(e.getPlayer(), obj, EventType.INTERACT)){return;}
		Player p = e.getPlayer();
		if(!p.getItemInHand().getType().isBlock()&&!p.getItemInHand().getType().equals(Material.AIR)){return;}
		e.setCancelled(true);
		ItemStack Itemstack = p.getItemInHand().clone();
		Itemstack.setAmount(1);
		ArmorStandPacket packet = manager.getArmorStandPacketByObjectID(obj).get(0);
		
		if(packet.getInventory().getHelmet()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
			ItemStack is = packet.getInventory().getHelmet();
			is.setAmount(1);
			w.dropItem(loc, is);
		}
		
		packet.getInventory().setHelmet(Itemstack);
		
		manager.updateFurniture(obj);
		
		if(p.getGameMode().equals(GameMode.CREATIVE) && lib.useGamemode()) return;
		Integer i = e.getPlayer().getInventory().getHeldItemSlot();
		ItemStack item = e.getPlayer().getItemInHand();
		item.setAmount(item.getAmount()-1);
		e.getPlayer().getInventory().setItem(i, item);
		e.getPlayer().updateInventory();
	}
	
	@EventHandler
	private void onBlockBreak(BlockBreakEvent e){
		if(obj==null){return;}
		if(obj.getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(block==null){return;}
		if(!e.getBlock().getLocation().equals(block.getLocation())){return;}
		if(!lib.canBuild(e.getPlayer(), obj, EventType.BREAK)){return;}
		ArmorStandPacket packet = manager.getArmorStandPacketByObjectID(obj).get(0);
		if(packet.getInventory().getHelmet()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
			ItemStack is = packet.getInventory().getHelmet();
			w.dropItem(loc, is);
		}
		
		this.block.setType(Material.AIR);
		block=null;
		this.obj.remove(e.getPlayer());
		obj=null;
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		e.remove();
		obj=null;
		block.setType(Material.AIR);
		block=null;
	}
}