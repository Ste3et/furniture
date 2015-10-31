package de.Ste3et_C0st.Furniture.Objects.RPG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBlockBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBlockClickEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class Crossbow extends Furniture implements Listener  {

	public Crossbow(Plugin plugin, ObjectID id){
		super(plugin, id);
		setBlock();
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(id.getStartLocation());
	}

	private void setBlock(){
		Block b = getLocation().getBlock();
		if(b.getType()==null||!b.getType().equals(Material.STEP)){
			b.setType(Material.STEP);
		}
		getObjID().addBlock(Arrays.asList(b));
	}
	
	@Override
	public void spawn(Location paramLocation) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		
		float yaw = 90;
		for(int i = 0; i<4;i++){
			Location loc = getLutil().getRelativ(getCenter().add(0, -1.7, 0), getLutil().yawToFace(yaw), .34, .4);
			loc.setYaw(yaw+45);
			fArmorStand stand = spawnArmorStand(loc);
			stand.setItemInHand(new ItemStack(Material.STICK));
			stand.setMarker(false);
			stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(90,270,135)));
			asList.add(stand);
			yaw+=90;
		}
		
		fArmorStand stand = spawnArmorStand(getCenter().add(0, -0.7, 0));
		stand.setHelmet(new ItemStack(Material.LOG));
		stand.setSmall(true);
		asList.add(stand);
		
		Location loc = getRelative(getCenter().add(0, -.74, 0), getBlockFace(), -.6, .48);
		loc.setYaw(getYaw()-90);
		stand = spawnArmorStand(loc);
		stand.setItemInHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(80,0,90)));
		stand.setMarker(false);
		asList.add(stand);
		
		loc = getRelative(getCenter().add(0, -.62, 0),getBlockFace(), -.4,.07);
		loc.setYaw(getYaw()-90);
		stand = spawnArmorStand(loc);
		stand.setItemInHand(new ItemStack(Material.BOW));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(0,10,90)));
		stand.setMarker(false);
		asList.add(stand);
		
		loc = getRelative(getCenter().add(0, -.08, 0), getBlockFace(), -.1, .25);
		loc.setYaw(getYaw()-90);
		stand = spawnArmorStand(loc);
		stand.setName("#ARROW#");
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(0,135,0)));
		stand.setMarker(false);
		asList.add(stand);
		
		for(fArmorStand asp : asList){
			asp.setGravity(false);
			asp.setInvisible(true);
			asp.setBasePlate(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
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
	public void onFurnitureBreak(FurnitureBlockBreakEvent e) {
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
		fArmorStand stand = getArmorStand();
		if(stand==null){return;}
		ItemStack is = e.getPlayer().getItemInHand();
		if(!hasArrow()||(is!=null&&is.getType()!=null&&is.getType().equals(Material.ARROW))){
			if(e.getPlayer().getItemInHand()==null){return;}
			if(e.getPlayer().getItemInHand().getType()==null){return;}
			if(!e.getPlayer().getItemInHand().getType().equals(Material.ARROW)){return;}
			addArmor(is, stand);
		}else{
			spawnArrow();
			removeArrow(stand);
		}
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureBlockClickEvent e) {
		if(getObjID()==null){return;} 
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()) return;
		if(!e.getID().equals(getObjID())) return;
		if(!e.canBuild()){return;}
		fArmorStand stand = getArmorStand();
		if(stand==null){return;}
		ItemStack is = e.getPlayer().getItemInHand();
		if(!hasArrow()||(is!=null&&is.getType()!=null&&is.getType().equals(Material.ARROW))){
			if(e.getPlayer().getItemInHand()==null){return;}
			if(e.getPlayer().getItemInHand().getType()==null){return;}
			if(!e.getPlayer().getItemInHand().getType().equals(Material.ARROW)){return;}
			addArmor(is, stand);
		}else{
			spawnArrow();
			removeArrow(stand);
		}
	}
	
	private void spawnArrow(){
		Location loc = getRelative(getCenter(), getBlockFace(), 0,18);
		loc.setYaw(getYaw());
		Vector v=null;
		switch (getBlockFace()) {
		case NORTH:v= new Vector(0, 0.5, 1.2);break;
		case SOUTH:v= new Vector(0, 0.5, -1.2);break;
		case EAST: v= new Vector(-1.2, 0.5, 0);break;
		case WEST: v= new Vector(1.2, 0.5, 0);break;
		default:break;
		}
		getWorld().playSound(getLocation(), Sound.SHOOT_ARROW, 1, 1);
		Location start = getRelative(getCenter(), getBlockFace(), 0,0);
		start.setYaw(getYaw());
		start = start.add(0, 1.8, 0);
		Arrow a = getWorld().spawnArrow(start, v.multiply(2), 2F, 3F);
		a.setCritical(true);
	}
	
	public static Set<Entity> getEntitiesInChunks(Location l, int chunkRadius) {
	    Block b = l.getBlock();
	    Set<Entity> entities = new HashSet<Entity>();
	    for (int x = -16 * chunkRadius; x <= 16 * chunkRadius; x += 16) {
	        for (int z = -16 * chunkRadius; z <= 16 * chunkRadius; z += 16) {
	            for (Entity e : b.getRelative(x, 0, z).getChunk().getEntities()) {
	                entities.add(e);
	            }
	        }
	    }
	    return entities;
	}
	
	private void removeArrow(fArmorStand stand){
		ItemStack is = stand.getItemInHand();
		if(is.getAmount()-1<=0){
			stand.setItemInHand(new ItemStack(Material.AIR));
			update();
			return;
		}
		is.setAmount(is.getAmount()-1);
		stand.setItemInHand(is);
		update();
	}
	
	private void addArmor(ItemStack is, fArmorStand stand){
		if(getArrow()!=null){is.setAmount(getArrow().getAmount());}
		stand.setItemInHand(is);
		update();
	}
	
	
	
	private fArmorStand getArmorStand(){
		for(fArmorStand stand : getfAsList()){
			if(stand.getName().equalsIgnoreCase("#ARROW#")){
				return stand;
			}
		}
		return null;
	}
	
	private ItemStack getArrow(){
		for(fArmorStand stand : getfAsList()){
			if(stand.getName().equalsIgnoreCase("#ARROW#")){
				if(!(stand.getItemInHand()==null||stand.getItemInHand().getType()==null||stand.getItemInHand().getType().equals(Material.AIR))){
					return stand.getItemInHand();
				}
			}
		}
		return null;
	}

	private boolean hasArrow(){
		for(fArmorStand stand : getfAsList()){
			if(stand.getName().equalsIgnoreCase("#ARROW#")){
				if(stand.getItemInHand()==null||stand.getItemInHand().getType()==null||stand.getItemInHand().getType().equals(Material.AIR)){
					return false;
				}else{
					return true;
				}
			}
		}
		return false;
	}
}
