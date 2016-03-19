package de.Ste3et_C0st.Furniture.Objects.christmas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBlockBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class SnowGolem extends Furniture implements Listener{

	public SnowGolem(ObjectID id){
		super(id);
		setBlock();
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, getPlugin());
			return;
		}
		spawn(id.getStartLocation());
	}
	
	private void setBlock(){
		Block b = getCenter().getBlock();
		b.setType(Material.SNOW_BLOCK);
		getObjID().addBlock(Arrays.asList(b));
	}

	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!canBuild(e.getPlayer())){return;}
		e.remove();
		delete();
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBlockBreakEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!canBuild(e.getPlayer())){return;}
		e.remove();
		delete();
	}

	@Override
	public void onFurnitureClick(FurnitureClickEvent arg0) {
		
	}

	@Override
	public void spawn(Location arg0) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		fArmorStand stand = spawnArmorStand(getCenter().add(0, -.87, 0));
		stand.setHelmet(new ItemStack(Material.SNOW_BLOCK));
		asList.add(stand);
		
		stand = spawnArmorStand(getCenter().add(0, .38, 0));
		stand.setHelmet(new ItemStack(Material.COAL_BLOCK));
		stand.setSmall(true);
		asList.add(stand);
		
		Location loc = getRelative(getCenter().add(0, -.75, 0), -.18, -.54);
		loc.setYaw(getYaw()+90);
		stand = spawnArmorStand(loc);
		stand.setItemInMainHand(new ItemStack(Material.CARROT_ITEM));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(90, 0, -150)));
		stand.setMarker(false);
		asList.add(stand);
		
		loc = getRelative(getCenter().add(0, .2, 0), .05, -.15);
		loc.setYaw(getYaw()+90);
		stand = spawnArmorStand(loc);
		stand.setItemInMainHand(new ItemStack(Material.COAL_BLOCK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(70f, 0f, 45f)));
		stand.setSmall(true);
		asList.add(stand);
		
		loc = getRelative(getCenter().add(0, .2, 0), .05, -.45);
		loc.setYaw(getYaw()+90);
		stand = spawnArmorStand(loc);
		stand.setItemInMainHand(new ItemStack(Material.COAL_BLOCK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(70f, 0f, 45f)));
		stand.setSmall(true);
		asList.add(stand);
		
		loc = getRelative(getCenter().add(0, -.55, 0), -.15, -.28);
		loc.setYaw(getYaw()+90);
		stand = spawnArmorStand(loc);
		stand.setItemInMainHand(new ItemStack(Material.COAL_BLOCK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(70f, 0f, 45f)));
		stand.setSmall(true);
		asList.add(stand);
		
		loc = getRelative(getCenter().add(0, -.95, 0), -.15, -.28);
		loc.setYaw(getYaw()+90);
		stand = spawnArmorStand(loc);
		stand.setItemInMainHand(new ItemStack(Material.COAL_BLOCK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(70f, 0f, 45f)));
		stand.setSmall(true);
		asList.add(stand);
		
		loc = getRelative(getCenter().add(0, -.45, 0), .0, 1.22);
		loc.setYaw(getYaw());
		stand = spawnArmorStand(loc);
		stand.setItemInMainHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(90f,90f,70f)));
		asList.add(stand);
		
		loc = getRelative(getCenter().add(0, -.45, 0), -.1, -1.22);
		loc.setYaw(getYaw()+180);
		stand = spawnArmorStand(loc);
		stand.setItemInMainHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(90f,90f,70f)));
		asList.add(stand);
		
		for(fArmorStand pack : asList){
			pack.setInvisible(true);
			pack.setBasePlate(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
}
