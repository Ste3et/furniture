package de.Ste3et_C0st.Furniture.Objects.light;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.ShematicLoader.Events.ProjectClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureHelper;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;

public class WaxCandle extends FurnitureHelper implements Listener{
	
	public WaxCandle(ObjectID id){
		super(id);
		Bukkit.getPluginManager().registerEvents(this, main.instance);
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
	public void onClick(ProjectClickEvent e){
		if(!e.getID().equals(getObjID())){return;}
		Block b = getWorld().getBlockAt(getObjID().getBlockList().toArray(new Location[getObjID().getBlockList().size()])[0]);
		ItemStack stack = e.getPlayer().getInventory().getItemInMainHand();
		if(stack==null) return;
		if(stack.getType().equals(Material.AIR)) return;
		if(b!=null){
			if(b.getType().equals(Material.TORCH) && stack.getType().equals(Material.WATER_BUCKET)){
				b.setType(Material.REDSTONE_TORCH_OFF);
			}else if(b.getType().equals(Material.REDSTONE_TORCH_OFF) && stack.getType().equals(Material.FLINT_AND_STEEL)){
				b.setType(Material.TORCH);
			}
		}
	}
}