package de.Ste3et_C0st.Furniture.Objects.RPG;

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

public class HumanSkeleton extends Furniture implements Listener {

	public HumanSkeleton(ObjectID id) {
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
		fArmorStand stand = spawnArmorStand(getRelative(getLocation().add(0, -1.4, 0), -.25, 0));
		stand.setItemInMainHand(new ItemStack(Material.BONE));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-40,0,90)));
		asList.add(stand);
		
		stand = spawnArmorStand(getRelative(getLocation().add(0, -1.4, 0), .2, 1.2));
		stand.setItemInMainHand(new ItemStack(Material.BONE));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(40,0,90)));
		asList.add(stand);
		
		stand = spawnArmorStand(getRelative(getLocation().add(0, -1.4, 0), .3, .75));
		stand.setItemInMainHand(new ItemStack(Material.BONE));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(0,0,90)));
		asList.add(stand);
		
		stand = spawnArmorStand(getRelative(getLocation().add(0, -1.4, 0), .9, .42));
		stand.setItemInMainHand(new ItemStack(Material.BONE));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-30,0,90)));
		asList.add(stand);
		
		stand = spawnArmorStand(getRelative(getLocation().add(0, -1.4, 0), 1.1, -.6));
		stand.setItemInMainHand(new ItemStack(Material.BONE));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-80,0,90)));
		asList.add(stand);
		
		stand = spawnArmorStand(getRelative(getLocation().add(0, -1.4, 0), 1.6, 1.3));
		stand.setItemInMainHand(new ItemStack(Material.BONE));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(30,0,90)));
		asList.add(stand);
		
		Location loc = getRelative(getLocation().add(0, -1.4, 0), getBlockFace(), 2.0, -.1);
		loc.setYaw(getYaw()+180);
		stand = spawnArmorStand(loc);
		stand.setHelmet(new ItemStack(Material.SKULL_ITEM));
		stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(-90,13,-5)));
		asList.add(stand);
		for(fArmorStand asp : asList){
			asp.setGravity(false);
			asp.setInvisible(true);
			asp.setBasePlate(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}

	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		delete();
		e.remove();
	}

	@Override
	public void onFurnitureClick(FurnitureClickEvent paramFurnitureClickEvent) {}
	
}
