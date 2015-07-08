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
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;

public class barrels implements Listener {
	
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
	
	public barrels(Location location, FurnitureLib lib, String name, Plugin plugin, ObjectID id){
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
			this.block = loc.getBlock();
			this.block.setType(Material.CAULDRON);
			this.manager.send(obj);
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}else{
			this.obj = new ObjectID(name, plugin.getName(), location);
		}
		spawn(location);
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
	private void onClick(FurnitureClickEvent e){
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		if(!e.canBuild(null)){return;}
		e.setCancelled(true);
		Player p = e.getPlayer();
		if(!p.getItemInHand().getType().isBlock()&&!p.getItemInHand().getType().equals(Material.AIR)){return;}
		ArmorStandPacket packet = manager.getArmorStandPacketByObjectID(obj).get(0);
		if(packet.getInventory().getHelmet()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
			ItemStack is = packet.getInventory().getHelmet();
			w.dropItem(loc, is);
		}
		
		packet.getInventory().setHelmet(p.getItemInHand());
		
		packet.update();
	}
	
	@EventHandler
	private void onInteract(PlayerInteractEvent e){
		if(block==null){return;}
		if(!e.getClickedBlock().getLocation().equals(block.getLocation())){return;}
		if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){return;}
		if(!lib.canBuild(e.getPlayer(), e.getClickedBlock().getLocation(), Material.CAULDRON)){return;}
		Player p = e.getPlayer();
		if(!p.getItemInHand().getType().isBlock()&&!p.getItemInHand().getType().equals(Material.AIR)){e.getPlayer().sendMessage("03");return;}
		e.setCancelled(true);
		ItemStack Itemstack = p.getItemInHand().clone();
		Itemstack.setAmount(1);
		ArmorStandPacket packet = manager.getArmorStandPacketByObjectID(obj).get(0);
		if(packet.getInventory().getHelmet()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
			ItemStack is = packet.getInventory().getHelmet();
			w.dropItem(loc, is);
		}
		
		if(!p.getGameMode().equals(GameMode.CREATIVE)){
			Integer i = p.getInventory().getHeldItemSlot();
			ItemStack is = p.getItemInHand();
			is.setAmount(is.getAmount()-1);
			p.getInventory().setItem(i, is);
			p.updateInventory();
		}
		
		packet.getInventory().setHelmet(Itemstack);
		
		packet.update();
	}
	
	@EventHandler
	private void onBlockBreak(BlockBreakEvent e){
		if(block==null){return;}
		if(!e.getBlock().getLocation().equals(block.getLocation())){return;}
		if(!lib.canBuild(e.getPlayer(), e.getBlock().getLocation(), Material.CAULDRON)){return;}
		ArmorStandPacket packet = manager.getArmorStandPacketByObjectID(obj).get(0);
		if(packet.getInventory().getHelmet()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
			ItemStack is = packet.getInventory().getHelmet();
			w.dropItem(loc, is);
		}
		
		this.block.setType(Material.AIR);
		block=null;
		manager.remove(obj);
		obj=null;
	}
	
	@EventHandler
	private void onBreak(FurnitureBreakEvent e){
		if(e.isCancelled()){return;}
		if(!e.canBuild(null)){return;}
		if(!e.getID().equals(obj)){return;}
		e.setCancelled(true);
		manager.remove(obj);
		obj=null;
		block.setType(Material.AIR);
		block=null;
	}
}
