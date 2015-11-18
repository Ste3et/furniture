package de.Ste3et_C0st.Furniture.Objects.electric;

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

public class tv extends Furniture implements Listener{
	public tv(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}
	
	public void spawn(Location location){
		List<fArmorStand> aspList = new ArrayList<fArmorStand>();
		BlockFace b = getLutil().yawToFace(location.getYaw()).getOppositeFace();
		Location center = getLutil().getCenter(location);
		center.add(0, -1.38, 0);
		center.setYaw(getLutil().FaceToYaw(b));
		Location iron = center.clone();
		fArmorStand as = getManager().createArmorStand(getObjID(), iron);
		as.getInventory().setHelmet(new ItemStack(Material.IRON_PLATE));
		as.setSmall(true);
		aspList.add(as);
		center.add(0, -.45, 0);
		center.setYaw(getLutil().FaceToYaw(b));

		as = getManager().createArmorStand(getObjID(), center);
		as.getInventory().setHelmet(new ItemStack(Material.LEVER));
		aspList.add(as);
		
		Location tv = getLutil().getRelativ(center.clone(), b, -.38, -.2);
		tv.add(0, -.1, 0);
		tv.setYaw(getLutil().FaceToYaw(b));
		Double l = .63;
		for(int i = 0;i<=1;i++){
			setRow(tv, .63, l, -.35,2,getLutil().degresstoRad(new EulerAngle(90, 0, 0)), aspList);
			l+=.63;
		}
		
		for(fArmorStand asp : aspList){
			asp.setGravity(false);
			asp.setBasePlate(false);
			asp.setInvisible(true);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	public void setRow(Location loc, double x,double y, double z, int replay, EulerAngle angle, List<fArmorStand> list){
		Double d = .0;
		for(int i = 0; i<=replay;i++){
			Location loc1= getLutil().getRelativ(loc, getBlockFace(), z, d-.825);
			loc1.setYaw(getLutil().FaceToYaw(getBlockFace()));
			loc1.add(0, y,0);
			fArmorStand packet = getManager().createArmorStand(getObjID(), loc1);
			packet.getInventory().setHelmet(new ItemStack(Material.CARPET, 1 , (short) 15));
			packet.setPose(angle, BodyPart.HEAD);
			list.add(packet);
			d+=x;
		}
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
	public void onFurnitureClick(FurnitureClickEvent e){}
}
