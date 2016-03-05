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

public class TrashCan extends Furniture implements Listener  {

	public TrashCan(ObjectID id){
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
		if(e.isCancelled()) return;
		if(!e.getID().equals(getObjID())) return;
		if(!e.canBuild()){return;}
		e.remove();
		delete();
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(getObjID()==null){return;} 
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()) return;
		if(!e.getID().equals(getObjID())) return;
		if(!e.canBuild()){return;}
		ItemStack is = e.getPlayer().getInventory().getItemInMainHand();
		fArmorStand stand = null;
		for(fArmorStand s : getfAsList()){
			if(s.getName().equalsIgnoreCase("#TRASH#")){
				stand = s;break;
			}
		}
		if(stand==null){return;}
		if(is==null||is.getType()==null||is.getType().equals(Material.AIR)){
			if(stand.getItemInMainHand()!=null&&!stand.getItemInMainHand().getType().equals(Material.AIR)){
				getWorld().dropItem(getCenter(), stand.getItemInMainHand());
				stand.setItemInMainHand(new ItemStack(Material.AIR));
				update();
				return;
			}
		}
		stand.setItemInMainHand(is);
		e.getPlayer().getInventory().clear(e.getPlayer().getInventory().getHeldItemSlot());
		e.getPlayer().updateInventory();
		update();
		return;
	}

	@Override
	public void spawn(Location loc) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		Location l = getLutil().getRelativ(getCenter(), getBlockFace(), -.29, .2).add(0, -1.1, 0);
		l.setYaw(getYaw());
		fArmorStand stand = spawnArmorStand(getCenter().add(0, -1.2, 0));
		stand.setHelmet(new ItemStack(Material.CARPET, 1,(short) 15));
		stand.setSmall(true);
		asList.add(stand);
		
		float yaw = 0;
		for(int i = 0; i<4;i++){
			Location location = getLutil().getRelativ(getCenter(), getLutil().yawToFace(yaw), .25, 0);
			location.add(0,-1.88,0);
			location.setYaw(yaw);
			stand = spawnArmorStand(location);
			stand.setHelmet(new ItemStack(Material.IRON_FENCE));
			stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(10, 0, 0)));
			asList.add(stand);
			yaw+=90;
		}
		
		stand = spawnArmorStand(l);
		stand.setSmall(true);
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-110,0,0)));
		stand.setMarker(false);
		stand.setName("#TRASH#");
		asList.add(stand);
		
		for(fArmorStand as : asList){
			as.setInvisible(true);
			as.setGravity(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
}
