package de.Ste3et_C0st.Furniture.Objects.outdoor;

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
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class CactusPlant extends Furniture implements Listener {

	public CactusPlant(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
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
	public void onFurnitureClick(FurnitureClickEvent e) {}

	
	public void spawn(Location location) {
		List<fArmorStand> aspList = new ArrayList<fArmorStand>();
		Location middle = getLutil().getCenter(location);
		middle.add(0, -1.9, 0);
		middle.setYaw(getLutil().FaceToYaw(getBlockFace()));
		
		fArmorStand packet = getManager().createArmorStand(getObjID(), middle);
		packet.getInventory().setHelmet(new ItemStack(Material.CACTUS));
		aspList.add(packet);
		
		packet = getManager().createArmorStand(getObjID(), middle.clone().add(0, .63, 0));
		packet.getInventory().setHelmet(new ItemStack(Material.CACTUS));
		aspList.add(packet);
		
		packet = getManager().createArmorStand(getObjID(), middle.clone().add(0, 1.18, 0));
		packet.getInventory().setHelmet(new ItemStack(Material.CACTUS));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(20, 0, 0)), BodyPart.HEAD);
		aspList.add(packet);
		
		Location l1 = getLutil().getRelativ(middle.clone().add(0, 1.665, 0), getBlockFace(), .16d, 0d);
		l1.setYaw(getLutil().FaceToYaw(getBlockFace()));
		packet = getManager().createArmorStand(getObjID(), l1);
		packet.getInventory().setHelmet(new ItemStack(Material.CACTUS));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(5, 0, 0)), BodyPart.HEAD);
		aspList.add(packet);
		
		l1 = getLutil().getRelativ(middle.clone().add(0, 1.43, 0), getBlockFace(), .1d, 0d);
		l1.setYaw(getLutil().FaceToYaw(getBlockFace()));
		packet = getManager().createArmorStand(getObjID(), l1);
		packet.getInventory().setHelmet(new ItemStack(Material.CACTUS));
		packet.setSmall(true);
		packet.setPose(getLutil().degresstoRad(new EulerAngle(60, 0, 0)), BodyPart.HEAD);
		aspList.add(packet);
		
		l1 = getLutil().getRelativ(middle.clone().add(0, 2.3, 0), getBlockFace(), -.07d, 0d);
		l1.setYaw(getLutil().FaceToYaw(getBlockFace()));
		packet = getManager().createArmorStand(getObjID(), l1);
		packet.getInventory().setHelmet(new ItemStack(Material.CACTUS));
		packet.setSmall(true);
		packet.setPose(getLutil().degresstoRad(new EulerAngle(-45, 0, 0)), BodyPart.HEAD);
		aspList.add(packet);
		
		l1 = getLutil().getRelativ(middle.clone().add(0, 2.0, 0), getBlockFace(), .07d, .06d);
		l1.setYaw(getLutil().FaceToYaw(getBlockFace()));
		packet = getManager().createArmorStand(getObjID(), l1);
		packet.getInventory().setHelmet(new ItemStack(Material.CACTUS));
		packet.setSmall(true);
		packet.setPose(getLutil().degresstoRad(new EulerAngle(0, 0, 45)), BodyPart.HEAD);
		aspList.add(packet);
		
		
		
		for(fArmorStand as : aspList){
			as.setInvisible(true);
			as.setGravity(false);
			as.setBasePlate(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}

}
