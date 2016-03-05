package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class campfire_1 extends Furniture implements Listener{

	public campfire_1(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
			packet.setFire(false);
			Location location = getLocation().clone();
			location.add(0, 1.2, 0);
			getLib().getLightManager().removeLight(location);
		}
		getManager().updateFurniture(getObjID());
		e.remove();
		delete();
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		List<fArmorStand> aspList = getManager().getfArmorStandByObjectID(getObjID());
		ItemStack is = e.getPlayer().getInventory().getItemInMainHand();
		if(is.getType().equals(Material.WATER_BUCKET)){
			for(fArmorStand packet : aspList){
				packet.setFire(false);
				Location location = getLocation().clone();
				location.add(0, 1.2, 0);
				getLib().getLightManager().removeLight(location);
			}
			getManager().updateFurniture(getObjID());
		}else if(is.getType().equals(Material.FLINT_AND_STEEL)){
			for(fArmorStand packet : aspList){
				packet.setFire(true);
				Location location = getLocation().clone();
				location.add(0, 1.2, 0);
				getLib().getLightManager().addLight(location,15);
			}
			getManager().updateFurniture(getObjID());
		}
	}
	
	
	public void spawn(Location loc){
		for(int i = 0;i<=3;i++){
			Location location = getLutil().getCenter(loc);
			location.add(0,-1.9,0);
			location.setYaw(i*60);
			fArmorStand packet = getManager().createArmorStand(getObjID(), location);
			packet.setGravity(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
}
