package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

public class Trunk extends Furniture implements Listener {

	public Trunk(ObjectID id){
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
		e.remove();
		delete();
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.getPlayer().getInventory().getItemInMainHand().getType().isBlock()||
			e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR)){setPassanger(e.getfArmorStand(), e.getPlayer());return;}
		if(e.getPlayer().isSneaking()){setPassanger(e.getfArmorStand(), e.getPlayer());return;}
		if(!e.canBuild()){return;}
		ItemStack stack = e.getPlayer().getInventory().getItemInMainHand();
		stack.setAmount(1);
		for(fArmorStand stand : getfAsList()){
			if(stand.getName().startsWith("#TO")){
				stand.setHelmet(stack);
			}
		}
		update();
		
		if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE) && getLib().useGamemode()) return;
		Integer i = e.getPlayer().getInventory().getHeldItemSlot();
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		item.setAmount(item.getAmount()-1);
		e.getPlayer().getInventory().setItem(i, item);
		e.getPlayer().updateInventory();
	}
	
	private void setPassanger(fArmorStand stand, Player p){
		int i = Integer.parseInt(stand.getName().split(":")[1]);
		for(fArmorStand as : getfAsList()){
			if(as.getName().equalsIgnoreCase("#Sitz:" + i)){
				if(as.getPassanger()==null){
					as.setPassanger(p);
					return;
				}
			}
		}
	}

	public void spawn(Location loc) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		
		for(int i = 1; i<=5;i++){
			Location loc1 = getRelative(getCenter(), getLutil().yawToFace(getLutil().FaceToYaw(getBlockFace())), 0d,.1 -i*.62).add(0, -1.6, 0);
			fArmorStand stand = spawnArmorStand(loc1);
			stand.setHelmet(new ItemStack(Material.LOG));
			stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(0, 0, 90)));
			if(i==1||i==2){
				stand.setName("#TO:1");
			}else if(i==4||i==5){
				stand.setName("#TO:3");
			}else{
				stand.setName("#TO:2");
			}
			asList.add(stand);
		}
		
		for(int i = 1; i<=3;i++){
			Location loc1 = getRelative(getCenter(), getLutil().yawToFace(getLutil().FaceToYaw(getBlockFace())), 0d,.3 -i*.9).add(0, -1.5, 0);
			loc1.setYaw(getYaw()+180);
			fArmorStand stand = spawnArmorStand(loc1);
			asList.add(stand);
			stand.setName("#SITZ:" + i);
		}
		
		for(fArmorStand stand : asList){
			stand.setInvisible(true);
			stand.setGravity(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
}
