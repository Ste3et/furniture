package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
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
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class chair extends Furniture implements Listener{

	public chair(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}
	
	public void spawn(Location loc){
		List<fArmorStand> aspList = new ArrayList<fArmorStand>();
		BlockFace b = getLutil().yawToFace(loc.getYaw()).getOppositeFace();
		Location center = getLutil().getCenter(loc).add(0, -.05, 0);
		Location sitz = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location lehne = getLutil().getRelativ(center.add(0,-1.1,0), b, -.25, .0);
		double offsetX = -.25;
		Location feet1 = getRelative(center.clone(), getBlockFace(), -.24-offsetX, -.24).subtract(0, 1.2, 0);
		Location feet2 = getRelative(center.clone(), getBlockFace(), .24-offsetX, -.24).subtract(0, 1.2, 0);
		Location feet3 = getRelative(center.clone(), getBlockFace(), .24-offsetX, .24).subtract(0, 1.2, 0);
		Location feet4 = getRelative(center.clone(), getBlockFace(), -.24-offsetX, .24).subtract(0, 1.2, 0);
		
		feet1.setYaw(getYaw());
		feet2.setYaw(getYaw());
		feet3.setYaw(getYaw());
		feet4.setYaw(getYaw());
		
		sitz.add(0,-1.45,0);
		sitz.setYaw(getLutil().FaceToYaw(b));
		lehne.setYaw(getLutil().FaceToYaw(b));
		
		fArmorStand as = getManager().createArmorStand(getObjID(), sitz.clone());
		as.getInventory().setHelmet(new ItemStack(Material.TRAP_DOOR));
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), lehne.clone());
		as.getInventory().setHelmet(new ItemStack(Material.TRAP_DOOR));
		as.setPose(new EulerAngle(1.57, .0, .0), BodyPart.HEAD);
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), feet1.clone());
		as.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), feet2.clone());
		as.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), feet3.clone());
		as.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), feet4.clone());
		as.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(as);
		
		Location sit = sitz.clone().add(0,-.2,0);
		as = getManager().createArmorStand(getObjID(), sit.clone());
		as.setName("#SITZ#");
		aspList.add(as);
		
		for(fArmorStand asp : aspList){
			asp.setInvisible(true);
			asp.setBasePlate(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.remove();
		delete();
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		fEntity packet = null;
		for(fEntity as : getManager().getfArmorStandByObjectID(getObjID())){
			if(as.getName().equalsIgnoreCase("#SITZ#")){
				packet=as;
				break;
			}
		}
		if(packet!=null){
			if(packet.getPassanger()==null){
				packet.setPassanger(e.getPlayer());
				packet.update();
			}
		}
	}
}
