package de.Ste3et_C0st.Furniture.Objects.School;

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
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class SchoolTable extends Furniture implements Listener {

	public SchoolTable(ObjectID id){
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
		e.setCancelled(true);
		for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(packet.getName().equalsIgnoreCase("#ITEM#")){
				if(packet.getInventory().getItemInHand()!=null&&!packet.getInventory().getItemInHand().getType().equals(Material.AIR)){
					ItemStack is = packet.getInventory().getItemInHand();
					getWorld().dropItem(getLocation(), is);
				}
			}
		}
		e.remove();
		delete();
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		Player p = e.getPlayer();
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		if(p.getItemInHand().getType().isBlock()&&!p.getItemInHand().getType().equals(Material.AIR)){return;}
		for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(packet.getName().equalsIgnoreCase("#ITEM#")){
				ItemStack Itemstack = p.getItemInHand().clone();
				Itemstack.setAmount(1);
				if(packet.getInventory().getItemInHand()!=null&&!packet.getInventory().getItemInHand().getType().equals(Material.AIR)){
					ItemStack is = packet.getInventory().getItemInHand();
					is.setAmount(1);
					getWorld().dropItem(getLocation(), is);
				}
				packet.getInventory().setItemInHand(Itemstack);
				if(p.getGameMode().equals(GameMode.CREATIVE) && getLib().useGamemode()) break;
				Integer i = p.getInventory().getHeldItemSlot();
				ItemStack is = p.getItemInHand();
				is.setAmount(is.getAmount()-1);
				p.getInventory().setItem(i, is);
				p.updateInventory();
				break;
			}
		}
		update();
	}

	@Override
	public void spawn(Location arg0) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		
		Location loc1 = getRelative(getCenter(), getBlockFace(), .16, .67).subtract(0, 1.9, 0);
		loc1.setYaw(getYaw()+90);
		Location loc2 = getRelative(getCenter(), getBlockFace(), .16+.41, .67).subtract(0, 1.9, 0);
		loc2.setYaw(getYaw()+90);
		Location loc3 = getRelative(getCenter(), getBlockFace(), .16, .67-.36).subtract(0, 1.9, 0);
		loc3.setYaw(getYaw()+90);
		Location loc4 = getRelative(getCenter(), getBlockFace(), .16+.41, .67-.36).subtract(0, 1.9, 0);
		loc4.setYaw(getYaw()+90);
		
		Location loc5 = getRelative(getCenter(), getBlockFace(), .37, .33).subtract(0, .22, 0);
		loc5.setYaw(getYaw()+90);
		
		fArmorStand stand = spawnArmorStand(loc1);
		stand.setItemInHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-100, 0, 0)));
		stand.setMarker(false);
		asList.add(stand);
		
		stand = spawnArmorStand(loc2);
		stand.setItemInHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-100, 0, 0)));
		stand.setMarker(false);
		asList.add(stand);
		
		stand = spawnArmorStand(loc3);
		stand.setItemInHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-100, 0, 0)));
		stand.setMarker(false);
		asList.add(stand);
		
		stand = spawnArmorStand(loc4);
		stand.setItemInHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-100, 0, 0)));
		stand.setMarker(false);
		asList.add(stand);
		
		stand = spawnArmorStand(getCenter().subtract(0, 1.2, 0));
		stand.setHelmet(new ItemStack(Material.WOOD_PLATE));
		asList.add(stand);
		
		stand = spawnArmorStand(loc5);
		stand.setPose(new EulerAngle(0.0,0.0,0.0), BodyPart.RIGHT_ARM);
		stand.setMarker(false);
		stand.setName("#ITEM#");
		asList.add(stand);
		
		for(fArmorStand as : asList){
			as.setInvisible(true);
			as.setGravity(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
}
