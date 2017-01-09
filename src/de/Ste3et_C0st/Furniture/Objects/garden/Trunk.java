package de.Ste3et_C0st.Furniture.Objects.garden;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.ShematicLoader.Events.ProjectClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureHelper;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class Trunk extends FurnitureHelper implements Listener{
	
	public Trunk(ObjectID id){
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
		ItemStack stack = e.getPlayer().getInventory().getItemInMainHand();
		if(stack==null) return;
		if(main.materialWhiteList.contains(stack.getType())){
			if(stack.getType().equals(Material.AIR)) return;
			for(fEntity entity : e.getID().getPacketList()){
				if(entity.getName().startsWith("#TO")){
					entity.setHelmet(stack);
				}
			}
			update();
		}else{
			for(fEntity entity : e.getID().getPacketList()){
				if(entity.getName().startsWith("#SITZ:")){
					if(entity.getPassanger()==null){
						entity.setPassanger(e.getPlayer());
						return;
					}
				}
			}
		}
	}
}