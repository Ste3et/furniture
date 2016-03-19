package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.ArrayList;
import java.util.Arrays;
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
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class WaterBottle extends Furniture implements Listener {

	List<Material> matList = Arrays.asList(
			Material.YELLOW_FLOWER, 
			Material.RED_ROSE,
			Material.SAPLING,
			Material.LONG_GRASS,
			Material.DOUBLE_PLANT,
			Material.BROWN_MUSHROOM,
			Material.RED_MUSHROOM,
			Material.DEAD_BUSH);
	
	public WaterBottle(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(getLocation());
	}

	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(getObjID()==null){return;} 
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()) return;
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())) return;
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
		if(!canInteract(e.getPlayer())){return;}
		Material data = e.getPlayer().getInventory().getItemInMainHand().getType();
		fArmorStand stand = removeItem();
		if(matList.contains(data)){
			ItemStack is = e.getPlayer().getInventory().getItemInMainHand();
			is.setAmount(1);
			stand.setHelmet(is);
			update();
		}
	}
	
	private fArmorStand removeItem(){
		fArmorStand fstand = null;
		for(fArmorStand stand : getfAsList()){
			if(stand.getName().equalsIgnoreCase("#ITEM#")){
				fstand = stand;
				if(stand.getItemInMainHand()!=null&&!stand.getItemInMainHand().equals(Material.AIR)){
					ItemStack is = stand.getItemInMainHand();
					is.setAmount(1);
					getWorld().dropItem(getLocation(), is);
				}
				return stand;
			}
		}
		return fstand;
	}
	
	private int getInt(Location loc){
		for(int j = 0; j<10;j++){
			Location newLocation = loc.clone().add(0, j, 0);
			if(newLocation.getBlock().getType()!=null&&newLocation.getBlock().getType().equals(Material.AIR)){
				return j;
			}
		}
		return 0;
	}

	@Override
	public void spawn(Location loc) {
		double i = 0;
		if(getLocation().getBlock().getRelative(BlockFace.UP).getType()!=null&&getLocation().getBlock().getType().equals(Material.STATIONARY_WATER)){
			getObjID().setStartLocation(getLocation().add(0, getInt(getLocation()), 0));
			i-=.54;
		}
		List<fArmorStand> stand = new ArrayList<fArmorStand>();
		Location loc1 = getRelative(getCenter(), getBlockFace(), .125, -.125).add(0, i-1.5, 0);
		loc1.setYaw(getYaw()+45);
		Location loc2 = getRelative(getCenter(), getBlockFace(), -.125, -.125).add(0, i-1.5, 0);
		loc2.setYaw(getYaw()*2+45);
		Location loc3 = getLutil().getRelativ(getCenter(), getBlockFace(), .45, 0).add(0, i-1, 0);
		loc3.setYaw(getYaw()+180);
		Location loc4 = getLutil().getRelativ(getCenter(), getBlockFace(), -.29, .2).add(0, i-0.88, 0);
		loc4.setYaw(getYaw());
		Location loc5 = getLutil().getRelativ(getCenter(), getBlockFace(), .13, .284).add(0, i-0.88, 0);
		loc5.setYaw(getYaw()+90);
		
		Location loc6 = getCenter().add(0, i-.7, 0);
		loc6.setYaw(getYaw()+45+90);
		
		fArmorStand as = spawnArmorStand(loc1);
		as.setSmall(true);
		as.setHelmet(new ItemStack(Material.WEB));
		stand.add(as);
		
		as = spawnArmorStand(loc2);
		as.setSmall(true);
		as.setHelmet(new ItemStack(Material.WEB));
		stand.add(as);
		
		as = spawnArmorStand(loc3);
		as.setSmall(true);
		as.setHelmet(new ItemStack(Material.WATER_LILY));
		as.setHeadPose(getLutil().degresstoRad(new EulerAngle(90, 0, 0)));
		stand.add(as);
		
		as = spawnArmorStand(loc4);
		as.setSmall(true);
		as.setItemInMainHand(new ItemStack(Material.POTION));
		as.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-110,0,0)));
		as.setMarker(false);
		stand.add(as);
		
		as = spawnArmorStand(loc5);
		as.setSmall(true);
		as.setItemInMainHand(new ItemStack(Material.POTION));
		as.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-110,0,0)));
		as.setMarker(false);
		stand.add(as);
		
		as = spawnArmorStand(loc6);
		as.setSmall(true);
		//as.setItemInHand(new ItemStack(Material.RED_ROSE));
		as.setName("#ITEM#");
		as.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-110,0,0)));
		as.setMarker(false);
		stand.add(as);
		
		for(fArmorStand asp : stand){
			asp.setInvisible(true);
			asp.setBasePlate(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
}
