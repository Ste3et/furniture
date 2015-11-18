package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
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
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.ColorType;
import de.Ste3et_C0st.FurnitureLib.main.Type.EventType;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class sofa extends Furniture implements Listener {

	public sofa(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		place = .3;
		spawn(id.getStartLocation());
	}
	
	ItemStack is;
	Double place;
	
	public void spawn(Location loc){
		List<fArmorStand> aspList = new ArrayList<fArmorStand>();
		List<fArmorStand> sitz = new ArrayList<fArmorStand>();
		Integer lengt = 3;
		is = new ItemStack(Material.CARPET);
		BlockFace b = getLutil().yawToFace(loc.getYaw()).getOppositeFace();
		
		Integer x = (int) loc.getX();
		Integer y = (int) loc.getY();
		Integer z = (int) loc.getZ();
		loc.setX(x);
		loc.setY(y);
		loc.setZ(z);
		
		if(b.equals(BlockFace.WEST)){loc = getLutil().getRelativ(loc, b, .0, -1.0);}
		if(b.equals(BlockFace.SOUTH)){loc = getLutil().getRelativ(loc, b, -1.0, -1.0);}
		if(b.equals(BlockFace.EAST)){loc = getLutil().getRelativ(loc, b, -1.0, .0);}
			Location looking = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() -1.4 , loc.getBlockZ());
			Location feet1 = getLutil().getRelativ(looking, b, place, .2D);
			Location feet2 = getLutil().getRelativ(looking, b, place, lengt.doubleValue()-.2D);
			Location feet3 = getLutil().getRelativ(looking, b, place + .5, .2D);
			Location feet4 = getLutil().getRelativ(looking, b, place + .5, lengt.doubleValue()-.2D);
			
			fArmorStand asp = getManager().createArmorStand(getObjID(), feet1);
			asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
			asp.setMarker(false);
			aspList.add(asp);
			asp = getManager().createArmorStand(getObjID(), feet2);
			asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
			asp.setMarker(false);
			aspList.add(asp);
			asp = getManager().createArmorStand(getObjID(), feet3);
			asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
			asp.setMarker(false);
			aspList.add(asp);
			asp = getManager().createArmorStand(getObjID(), feet4);
			asp.getInventory().setHelmet(new ItemStack(Material.LEVER));
			asp.setMarker(false);
			aspList.add(asp);
			Location carpetHight = new Location(looking.getWorld(), loc.getBlockX(), loc.getBlockY() -1 , loc.getBlockZ());
			carpetHight.setYaw(getLutil().FaceToYaw(b));
			carpetHight = getLutil().getRelativ(carpetHight, b, .25,.3);
			Double d = .02;
			float facing = getLutil().FaceToYaw(b);
			Integer j = 0;
			for(Double i = .0; i<=lengt; i+=0.65){
				Location carpet = getLutil().getRelativ(carpetHight, b, place,(double) d);
				carpet.setYaw(facing);
				String s = "";
				if(j==0||j==1){s="#SITZPOS:1#";}
				if(j==2){s="#SITZPOS:2#";}
				if(j==3||j==4){s="#SITZPOS:3#";}
				asp = getManager().createArmorStand(getObjID(), carpet);
				asp.getInventory().setHelmet(is);
				asp.setName(s);
				aspList.add(asp);
				sitz.add(asp);
				Location location = getLutil().getRelativ(carpetHight, b, place-.25,(double) d);
				location.setYaw(facing);
				
				asp = getManager().createArmorStand(getObjID(), location);
				asp.setPose(new EulerAngle(1.57, .0, .0), BodyPart.HEAD);
				asp.getInventory().setHelmet(is);
				asp.setName(s);
				aspList.add(asp);
				if(d<=0D){d = 0.00;}
				d+=.58;
				j++;
			}
			
			Float yaw1= facing;
			Float yaw2= facing;
			Location last = getLutil().getRelativ(sitz.get(sitz.size()-1).getLocation(), b, 0D, 0.26D);
			last.setYaw(yaw1+90);
			Location first = getLutil().getRelativ(new Location(loc.getWorld(), loc.getX(), last.getY(), loc.getZ()), b, place+.25, 0.07D);
			first.setYaw(yaw2-90);
			
			asp = getManager().createArmorStand(getObjID(), first.add(0,-.05,0));
			asp.getInventory().setHelmet(is);
			asp.setPose(new EulerAngle(1.57, .0, .0), BodyPart.HEAD);
			asp.setMarker(false);
			asp.setName("#SITZPOS:1#");
			aspList.add(asp);
			
			asp = getManager().createArmorStand(getObjID(), last.add(0,-.05,0));
			asp.getInventory().setHelmet(is);
			asp.setPose(new EulerAngle(1.57, .0, .0), BodyPart.HEAD);
			asp.setMarker(false);
			asp.setName("#SITZPOS:3#");
			aspList.add(asp);
			
			Location start = getLutil().getRelativ(looking, b, .45, .55);
			
			for(int i = 0; i<=2;i++){
				Location location = getLutil().getRelativ(start, b, place, i*.95D);
				location.setYaw(getLutil().FaceToYaw(b));
				location.add(0,.2,0);
				asp = getManager().createArmorStand(getObjID(), location);
				asp.setName("#SITZ" + i + "#");
				aspList.add(asp);
			}
			
			for(fArmorStand asps : aspList){
				asps.setInvisible(true);
				asps.setGravity(false);
				asps.setBasePlate(false);
			}
			send();
			Bukkit.getPluginManager().registerEvents(this, getPlugin());
		}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		e.setCancelled(true);
		final Player p = e.getPlayer();
		if(p.getItemInHand().getType().equals(Material.INK_SACK)){
			getLib().getColorManager().color(p, e.canBuild(), Material.CARPET, getObjID(), ColorType.BLOCK, 12);
		}else{
			fArmorStand packet = e.getfArmorStand();
			switch (packet.getName()) {
			case "#SITZPOS:1#": sit("#SITZ0#", p);break;
			case "#SITZPOS:2#": sit("#SITZ1#", p);break;
			case "#SITZPOS:3#": sit("#SITZ2#", p);break;
			case "#SITZ0#" : sit("#SITZ0#", p);break;
			case "#SITZ1#" : sit("#SITZ1#", p);break;
			case "#SITZ2#" : sit("#SITZ2#", p);break;
			default: sit("#SITZ0#", p);break;
			}
			
		}
	}
	
	private void sit(String s, Player p){
		for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(packet.getName().equalsIgnoreCase(s) && packet.getPassanger() == null){
				packet.setPassanger(p);
				packet.update();
				return;
			}
		}
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(getObjID()==null){return;}
		if(e.isCancelled()){return;}
		if(e.getID()==null){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!getLib().canBuild(e.getPlayer(), getObjID(), EventType.BREAK)){return;}
		e.setCancelled(true);
		for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(packet.getPassanger()!=null){
				packet.eject();
				packet.update();
			}
		}
		e.remove();
		delete();
	}
}
