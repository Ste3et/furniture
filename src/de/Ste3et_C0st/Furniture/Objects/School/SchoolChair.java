package de.Ste3et_C0st.Furniture.Objects.School;

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

public class SchoolChair extends Furniture implements Listener {

	public SchoolChair(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}

	@Override
	public void spawn(Location paramLocation) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		
		Location loc1 = getRelative(getCenter(), getBlockFace(), .16, .67).subtract(0, 2.3, 0);
		loc1.setYaw(getYaw()+90);
		Location loc2 = getRelative(getCenter(), getBlockFace(), .16+.41, .67).subtract(0, 2.3, 0);
		loc2.setYaw(getYaw()+90);
		Location loc3 = getRelative(getCenter(), getBlockFace(), .16, .67-.36).subtract(0, 2.3, 0);
		loc3.setYaw(getYaw()+90);
		Location loc4 = getRelative(getCenter(), getBlockFace(), .16+.41, .67-.36).subtract(0, 2.3, 0);
		loc4.setYaw(getYaw()+90);
		
		fArmorStand stand = spawnArmorStand(loc1);
		stand.setItemInMainHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-100, 0, 0)));
		stand.setMarker(false);
		asList.add(stand);
		
		stand = spawnArmorStand(loc2);
		stand.setItemInMainHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-100, 0, 0)));
		stand.setMarker(false);
		asList.add(stand);
		
		stand = spawnArmorStand(loc3);
		stand.setItemInMainHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-100, 0, 0)));
		stand.setMarker(false);
		asList.add(stand);
		
		stand = spawnArmorStand(loc4);
		stand.setItemInMainHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-100, 0, 0)));
		stand.setMarker(false);
		asList.add(stand);
		
		stand = spawnArmorStand(getCenter().subtract(0, 1.35, 0));
		stand.setHelmet(new ItemStack(Material.IRON_PLATE));
		asList.add(stand);
		
		stand = spawnArmorStand(getCenter().subtract(0, 1.3, 0));
		stand.setName("#SITZ#");
		asList.add(stand);
		
		stand = spawnArmorStand(getRelative(getCenter().subtract(0, 1.1, 0), getBlockFace(), .25, 0));
		stand.setHelmet(new ItemStack(Material.IRON_PLATE));
		stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(-89, 0, 0)));
		asList.add(stand);
		
		for(fArmorStand as : asList){
			as.setInvisible(true);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}

	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(e.getID()==null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		delete();
		e.remove();
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(getObjID()==null){return;}
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
}
