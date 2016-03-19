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

import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class ChristmasTree extends Furniture implements Listener{

	public ChristmasTree(ObjectID id){
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
		b.setType(Material.FLOWER_POT);
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
		e.remove(true,true);
		delete();
	}

	@Override
	public void onFurnitureClick(FurnitureClickEvent arg0) {
		
	}

	@Override
	public void spawn(Location arg0) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		
		double d = -1.1;
		
		setEbene(.3, d, .3, asList);
		setEbene(.2, d+.63, .2, asList);
		setEbene(.1, d+1.26, .1, asList);
		fArmorStand stand = spawnArmorStand(getCenter().add(0, d+1.89, 0));
		stand.setHelmet(new ItemStack(Material.LEAVES));
		asList.add(stand);
		
		stand = spawnArmorStand(getCenter().add(0, d+3.18, 0));
		stand.setHelmet(new ItemStack(Material.LEAVES));
		stand.setSmall(true);
		asList.add(stand);
		
		stand = spawnArmorStand(getRelative(getCenter(), getBlockFace(), .5, .4).add(0, d+.2, 0));
		stand.setItemInMainHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(80, 0, 0)));
		asList.add(stand);
		
		stand = spawnArmorStand(getRelative(getCenter(), getBlockFace(), .5, .4).add(0, d+3.3, 0));
		stand.setItemInMainHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(80, 0, 0)));
		asList.add(stand);
		
		Location location = getRelative(getCenter(), getBlockFace(), .15, 0);
		location.add(0, 2.9, 0);
		stand = spawnArmorStand(location);
		stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(90f,180f,90f)));
		stand.setHelmet(new ItemStack(Material.GOLD_BLOCK));
		stand.setSmall(true);
		asList.add(stand);
		
		stand = spawnArmorStand(location);
		stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(90f,180f,45f)));
		stand.setHelmet(new ItemStack(Material.GOLD_BLOCK));
		stand.setSmall(true);
		asList.add(stand);
		
		for(fArmorStand pack : asList){
			pack.setInvisible(true);
			pack.setBasePlate(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	private void setEbene(double x, double y, double z, List<fArmorStand> standL){
		float yaw = 0;
		for(int i = 1; i<=4;i++){
			Location loc = getLutil().getRelativ(getCenter(), getLutil().yawToFace(yaw), x, z);
			loc.add(0, y, 0);
			loc.setYaw(yaw);
			fArmorStand stand = spawnArmorStand(loc);
			stand.setHelmet(new ItemStack(Material.LEAVES));
			yaw+=90;
			standL.add(stand);
		}
	}
}
