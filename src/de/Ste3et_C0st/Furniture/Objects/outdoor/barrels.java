package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBlockBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBlockClickEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class barrels extends Furniture implements Listener {
	Integer id;
	Block block;
	
	public barrels(ObjectID id){
		super(id);
		if(id.isFinish()){
			this.block = getLocation().getBlock();
			this.block.setType(Material.CAULDRON);
			getObjID().addBlock(Arrays.asList(block));
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}
	
	public void spawn(Location loc){
		this.block = loc.getBlock();
		this.block.setType(Material.CAULDRON);
		getObjID().addBlock(Arrays.asList(block));
		fArmorStand packet = getManager().createArmorStand(getObjID(), getLutil().getCenter(loc).add(0,-1.5,0));
		packet.setInvisible(true);
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(getObjID()==null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		Player p = e.getPlayer();
		if(!p.getItemInHand().getType().isBlock()&&!p.getItemInHand().getType().equals(Material.AIR)){return;}
		fArmorStand packet = getManager().getfArmorStandByObjectID(getObjID()).get(0);
		if(packet.getInventory().getHelmet()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
			ItemStack is = packet.getInventory().getHelmet();
			is.setAmount(1);
			getWorld().dropItem(getLocation(), is);
		}
		
		packet.getInventory().setHelmet(p.getItemInHand());
		
		getManager().updateFurniture(getObjID());
		
		if(p.getGameMode().equals(GameMode.CREATIVE) && getLib().useGamemode()) return;
		Integer i = e.getPlayer().getInventory().getHeldItemSlot();
		ItemStack item = e.getPlayer().getItemInHand();
		item.setAmount(item.getAmount()-1);
		e.getPlayer().getInventory().setItem(i, item);
		e.getPlayer().updateInventory();
	}
	
	@EventHandler
	private void onBlockBreak(FurnitureBlockBreakEvent e){
		if(getObjID()==null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		fArmorStand packet = getManager().getfArmorStandByObjectID(getObjID()).get(0);
		if(packet.getInventory().getHelmet()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
			ItemStack is = packet.getInventory().getHelmet();
			is.setAmount(1);
			getWorld().dropItem(getLocation(), is);
		}
		e.remove();
		delete();
	}
	
	@EventHandler
	private void onBlockClick(FurnitureBlockClickEvent e){
		if(getObjID()==null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		if(!e.getPlayer().getItemInHand().getType().isBlock()&&!e.getPlayer().getItemInHand().getType().equals(Material.AIR)){return;}
		e.setCancelled(true);
		ItemStack Itemstack = e.getPlayer().getItemInHand().clone();
		Itemstack.setAmount(1);
		fArmorStand packet = getManager().getfArmorStandByObjectID(getObjID()).get(0);
		
		if(packet.getInventory().getHelmet()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
			ItemStack is = packet.getInventory().getHelmet();
			is.setAmount(1);
			getWorld().dropItem(getLocation(), is);
		}
		
		packet.getInventory().setHelmet(Itemstack);
		
		getManager().updateFurniture(getObjID());
		
		if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE) && getLib().useGamemode()) return;
		Integer i = e.getPlayer().getInventory().getHeldItemSlot();
		ItemStack item = e.getPlayer().getItemInHand();
		item.setAmount(item.getAmount()-1);
		e.getPlayer().getInventory().setItem(i, item);
		e.getPlayer().updateInventory();
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(getObjID()==null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		fArmorStand packet = getManager().getfArmorStandByObjectID(getObjID()).get(0);
		if(packet.getInventory().getHelmet()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
			ItemStack is = packet.getInventory().getHelmet();
			is.setAmount(1);
			getWorld().dropItem(getLocation(), is);
		}
		e.remove();
		delete();
	}
}