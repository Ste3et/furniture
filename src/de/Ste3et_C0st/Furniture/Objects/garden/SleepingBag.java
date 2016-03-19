package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class SleepingBag extends Furniture implements Listener {

	public SleepingBag(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}

	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		delete();
		e.remove();
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(packet.getName().equalsIgnoreCase("#SITZ#")){
				if(packet.getPassanger()==null){
					packet.setPassanger(e.getPlayer());
				}
			}
		}
	}

	public void spawn(Location loc) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		double offset = .3;
		fArmorStand stand = spawnArmorStand(getRelative(getCenter(), 0 - offset, 0).add(0, -2.15, 0));
		asList.add(stand);
		stand.setHelmet(new ItemStack(Material.IRON_TRAPDOOR));
		stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(90, 0, 0)));
		
		stand = spawnArmorStand(getRelative(getCenter(), 2 - offset, 0).add(0, -1.7, 0));
		asList.add(stand);
		stand.setHelmet(new ItemStack(Material.BANNER, 1, (short) 3));
		stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(-90, 0, 0)));

		stand = spawnArmorStand(getRelative(getCenter(), -.5 - offset, 0).add(0, -1.68, 0));
		asList.add(stand);
		stand.setHelmet(new ItemStack(Material.BANNER, 1, (short) 3));
		stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(-90, 180, 0)));
		
		stand = spawnArmorStand(getRelative(getCenter(), .3 - offset, 0).add(0, -2.1, 0));
		stand.setName("#SITZ#");
		asList.add(stand);
		for(fArmorStand as : asList){
			as.setInvisible(true);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
}
