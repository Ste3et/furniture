package de.Ste3et_C0st.Furniture.Objects.christmas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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

public class AdventWreath extends Furniture implements Listener  {
	double sub = .9;
	
	List<Location> locationList = Arrays.asList(
			getRelative(getCenter(), getLutil().yawToFace(0), 0, .7),
			getRelative(getCenter(), getLutil().yawToFace(90), .7, 0),
			getRelative(getCenter(), getLutil().yawToFace(180), 0, -.7),
			getRelative(getCenter(), getLutil().yawToFace(270),-.7, 0)
	);
	
	public AdventWreath(ObjectID id){
		super(id);
		if(isFinish()){
			load();
			Bukkit.getPluginManager().registerEvents(this, getPlugin());
			return;
		}
		spawn(id.getStartLocation());
	}

	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!canBuild(e.getPlayer())){return;}
		e.remove(true,false);
		delete();
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(getObjID()==null){return;}
		if(e.isCancelled()){return;}
		if(e.getID()==null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(e.getID() == null || getObjID() == null) return;
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		fArmorStand stand = e.getfArmorStand();
		String str = stand.getName();
		if(str.startsWith("Fire") || str.startsWith("Torch")){
			int i = Integer.parseInt(str.split(":")[1]);
			for(fArmorStand fstand : getfAsList()){
				if(fstand.getName().equalsIgnoreCase("Fire:" + i)){
					if(!fstand.getName().endsWith("Burn")){
						fstand.sendParticle(fstand.getLocation().clone().add(0, .93, 0), 26, true);
						fstand.setName("Fire:" + i + ":Burn");
						update();
					}
				}
			}
		}
	}
	
	private void load(){
		for(fArmorStand fstand : getfAsList()){
			if(fstand.getName().startsWith("Fire:")){
				if(fstand.getName().endsWith("Burn")){
					if(!fstand.isParticlePlayed()){
						fstand.sendParticle(fstand.getLocation().clone().add(0, .93, 0), 26, true);
					}
				}
			}
		}
		update();
	}

	@Override
	public void spawn(Location location) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		
		int j = 15;
		double l = 0;
		double o = getDegress(j);
		for(int i = 0; i<=j;i++){
			Location loc = getCenter();
			loc.setYaw((float) l);
			fArmorStand stand = spawnArmorStand(loc.subtract(0, 1.2+sub, 0));
			stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(210, 190, 305)));
			stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(60, 0, 0)));
			stand.setHelmet(new ItemStack(Material.GOLD_BLOCK));
			stand.setItemInMainHand(new ItemStack(Material.LEAVES));
			asList.add(stand);
			l+=o;
			
		}
		
		l = 0;
		for(int i = 0; i<=j;i++){
			Location loc = getCenter();
			loc.setYaw((float) l);
			fArmorStand stand = spawnArmorStand(loc.subtract(0, .3+sub, 0));
			stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(210, 190, 305)));
			stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(60, 0, 0)));
			stand.setHelmet(new ItemStack(Material.LEAVES));
			stand.setItemInMainHand(new ItemStack(Material.LEAVES));
			stand.setSmall(true);
			asList.add(stand);
			l+=o;
		}
		
		double d = .7;
		float y = 0;
		List<Location> locL = Arrays.asList(
				getRelative(getCenter(), getLutil().yawToFace(0), 0, d),
				getRelative(getCenter(), getLutil().yawToFace(90), d, 0),
				getRelative(getCenter(), getLutil().yawToFace(180), 0, -d),
				getRelative(getCenter(), getLutil().yawToFace(270),-d, 0)
		);
		
		for(int i = 0; i<=3;i++){
			Location loc = locL.get(i);
			loc.subtract(0, .1+sub, 0);
			loc.setYaw(y);
			fArmorStand stand = spawnArmorStand(loc);
			stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-41,-37.5f,18)));
			stand.setSmall(true);
			stand.setName("Torch:" + i);
			stand.setHelmet(new ItemStack(Material.STAINED_CLAY, 1, (short) 14));
			asList.add(stand);
			
			stand = spawnArmorStand(loc.clone().subtract(0, .4, 0));
			stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-41,-37.5f,18)));
			stand.setSmall(true);
			stand.setName("Torch:" + i);
			stand.setHelmet(new ItemStack(Material.STAINED_CLAY, 1, (short) 13));
			asList.add(stand);
			
			
			stand = spawnArmorStand(loc.clone().add(0, .4, 0));
			stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-41,-37.5f,18)));
			stand.setSmall(true);
			stand.setName("Torch:" + i);
			stand.setHelmet(new ItemStack(Material.LEVER, 1, (short) 13));
			asList.add(stand);
			y=y+90;
		}
		
		ItemStack is = new ItemStack(Material.RED_ROSE, 1, (short) 8);
		spawnFlower(getRelative(getCenter().subtract(0, .7, 0), getLutil().yawToFace(0), .5, .5), is);
		spawnFlower(getRelative(getCenter().subtract(0, .7, 0), getLutil().yawToFace(0), -.5, -.5), is);
		spawnFlower(getRelative(getCenter().subtract(0, .7, 0), getLutil().yawToFace(0), -.5, .5), is);
		spawnFlower(getRelative(getCenter().subtract(0, .7, 0), getLutil().yawToFace(0), .5, -.5), is);
		
		is = new ItemStack(Material.RED_ROSE, 1, (short) 4);
		spawnFlower(getRelative(getCenter().subtract(0, .7, 0), getLutil().yawToFace(0), .25, .25), is);
		spawnFlower(getRelative(getCenter().subtract(0, .7, 0), getLutil().yawToFace(0), -.25, -.25), is);
		spawnFlower(getRelative(getCenter().subtract(0, .7, 0), getLutil().yawToFace(0), -.25, .25), is);
		spawnFlower(getRelative(getCenter().subtract(0, .7, 0), getLutil().yawToFace(0), .25, -.25), is);
		
		y=0;
		for(int i = 0; i<=3;i++){
			Location loc = locationList.get(i);
			loc.add(0, .3-sub, 0);
			loc.setYaw(y);
			fArmorStand stand = spawnArmorStand(loc.add(0, -1.5, 0));
			stand.setSmall(true);
			stand.setName("Fire:" + i);
			stand.setFire(false);
			asList.add(stand);
			
			stand = spawnArmorStand(loc.add(0, 1.6, 0));
			stand.setName("Torch:" + i);
			stand.setNameVasibility(false);
			asList.add(stand);
			y=y+90;
		}
		
		for(fArmorStand pack : asList){
			pack.setInvisible(true);
			pack.setBasePlate(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	private void spawnFlower(Location loc, ItemStack stack){
		int j = 8;
		double l = 0;
		double o = getDegress(j);
		loc = loc.add(0, -sub, 0);
		loc.subtract(0, .5, 0);
		
		for(int i = 0; i<j;i++){
			loc.setYaw((float) l);
			fArmorStand stand = spawnArmorStand(loc);
			stand.setHelmet(stack);
			stand.setInvisible(true);
			stand.setBasePlate(false);
			l+=o;
		}
	}
	
	private int getDegress(int j){
		return 360/j;
	}
}
