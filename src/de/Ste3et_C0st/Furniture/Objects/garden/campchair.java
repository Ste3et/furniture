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

import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class campchair extends Furniture implements Listener {
	public campchair(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, getPlugin());
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

	@Override
	public void spawn(Location location) {
		List<fArmorStand> aspList = new ArrayList<fArmorStand>();
		Location middle = getLutil().getCenter(location);
		middle.add(0, -1.05, 0);
		middle.setYaw(getLutil().FaceToYaw(getBlockFace()));
		
		Location l1 = getLutil().getRelativ(middle, getBlockFace(), .25, .4);
		fArmorStand packet = getManager().createArmorStand(getObjID(), l1);
		packet.getInventory().setItemInHand(new ItemStack(Material.LADDER));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(50, 0, 0)), BodyPart.RIGHT_ARM);
		aspList.add(packet);
		
		l1 = getLutil().getRelativ(middle, getBlockFace().getOppositeFace(), .25, .4);
	    packet = getManager().createArmorStand(getObjID(), l1);
		packet.getInventory().setItemInHand(new ItemStack(Material.LADDER));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(50, 0, 0)), BodyPart.RIGHT_ARM);
		aspList.add(packet);
		
		l1 = middle.clone();
		l1.add(0, -.65, 0);
		packet = getManager().createArmorStand(getObjID(), l1);
		packet.getInventory().setHelmet(new ItemStack(Material.WOOD_PLATE));
		aspList.add(packet);
		
		middle.add(0,-0.6,0);
		middle.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
		packet = getManager().createArmorStand(getObjID(), middle);
		packet.setName("#SITZ#");
		aspList.add(packet);
		
		for(fArmorStand as : aspList){
			as.setInvisible(true);
			as.setBasePlate(false);
			as.setGravity(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}

}
