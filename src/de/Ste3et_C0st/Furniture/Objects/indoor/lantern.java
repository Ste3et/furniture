package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class lantern extends Furniture implements Listener{
	
	public lantern(ObjectID id){
		super(id);
		if(isFinish()){
			setBlock();
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}
	
	Block block;
	
	private void setBlock(){
		Location center = getLutil().getCenter(getLocation());
		block = center.getWorld().getBlockAt(center);
		if(!block.getType().equals(Material.AIR)){return;}
		block.setType(Material.TORCH);
		getObjID().addBlock(Arrays.asList(block));
	}
	
	public void spawn(Location loc){
		List<fArmorStand> aspList = new ArrayList<fArmorStand>();
		Location center = getLutil().getCenter(loc);
		setBlock();
		Location obsidian = center;
		Location l = new Location(center.getWorld(), center.getX(), center.getY() -1.43, center.getZ());
		obsidian.add(0D, -2.2, 0D);
		Location l1 = getRelative(center, getBlockFace(), +.05, +.21).add(0, .11, 0);
		Location l2 = getRelative(center, getBlockFace(), +.5, +.21).add(0, .11, 0);
		Location l3 = getRelative(center, getBlockFace(), +.5, -.21).add(0, .11, 0);
		Location l4 = getRelative(center, getBlockFace(), +.05, -.21).add(0, .11, 0);
		
		
		l1.setYaw(getYaw());
		l2.setYaw(getYaw());
		l3.setYaw(getYaw());
		l4.setYaw(getYaw());
		
		fArmorStand asp = getManager().createArmorStand(getObjID(), obsidian);
		asp.getInventory().setHelmet(new ItemStack(Material.OBSIDIAN));
		aspList.add(asp);
		
		asp = getManager().createArmorStand(getObjID(), getLutil().getRelativ(l.clone(), getBlockFace(), 0D, 0.01D).add(0, .23, 0));
		asp.getInventory().setHelmet(new ItemStack(Material.WOOD_PLATE));
		aspList.add(asp);
		
		asp = getManager().createArmorStand(getObjID(), l1);
		asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(asp);
		
		asp = getManager().createArmorStand(getObjID(), l2);
		asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(asp);
		
		asp = getManager().createArmorStand(getObjID(), l3);
		asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(asp);
		
		asp = getManager().createArmorStand(getObjID(), l4);
		asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(asp);
		
		for(fArmorStand packet : aspList){
			packet.setBasePlate(false);
			packet.setInvisible(true);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	
	
	@EventHandler
	public void onWaterFlow(BlockFromToEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		Location locTo = e.getToBlock().getLocation();
		if(getLocation()!=null && locTo.equals(getLocation().getBlock().getLocation())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()) return;
		if(block==null) return;
		if(!e.getID().equals(getObjID())) return;
		if(!e.canBuild()){return;}
		e.remove();
		block.setType(Material.AIR);
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()) return;
		if(block==null) return;
		if(!e.getID().equals(getObjID())) return;
		if(!e.canBuild()){return;}
		Player p = e.getPlayer();
		ItemStack is = p.getInventory().getItemInMainHand();
		if(is.getType().equals(Material.FLINT_AND_STEEL)){
			setLight(true);
		}else if(is.getType().equals(Material.WATER_BUCKET)){
			setLight(false);
		}
		
		
	}
	
	public void setLight(final boolean bool){
		if(bool){block.setType(Material.TORCH);}
		else{block.setType(Material.REDSTONE_TORCH_OFF);}
	}
	
	public boolean getLight(){
		if(block==null||block.getType().equals(Material.AIR)||block.getType().equals(Material.TORCH)){
			return true;
		}
		return false;
	}
}
