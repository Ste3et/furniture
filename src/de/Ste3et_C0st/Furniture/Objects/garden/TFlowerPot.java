package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBlockBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class TFlowerPot extends Furniture implements Listener {

	Block pot;
	public TFlowerPot(ObjectID id){
		super(id);
		setPotState();
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}
	
	private void setPotState(){
		pot=getLocation().getBlock();
		if(pot.getType()==null||!pot.getType().equals(Material.FLOWER_POT)){pot.setType(Material.FLOWER_POT);}
		getObjID().addBlock(Arrays.asList(pot));
	}

	@Override
	public void spawn(Location paramLocation) {
		List<fArmorStand> packetList = new ArrayList<fArmorStand>();
		float yaw = 90;
		for(int i = 0; i<=3;i++){
			Location location = getLutil().getRelativ(getCenter(), getLutil().yawToFace(yaw), .53, .08);
			location.add(0,-1.7,0);
			location.setYaw(90+yaw);
			
			fArmorStand asp = getManager().createArmorStand(getObjID(), location);
			asp.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-115,45,0)));
			asp.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
			
			packetList.add(asp);
			yaw+=90;
		}
		
		for(fArmorStand asp : packetList){
			asp.setGravity(false);
			asp.setInvisible(true);
			asp.setBasePlate(false);
			asp.setMarker(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}

	@Override
	public void onFurnitureBreak(FurnitureBreakEvent paramFurnitureBreakEvent) {}

	@Override
	public void onFurnitureClick(FurnitureClickEvent paramFurnitureClickEvent) {}
	
	@EventHandler
	private void BlockBreak(FurnitureBlockBreakEvent e){
		if(getObjID()==null){return;}
		  if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		  if (pot==null) return;
		  if (e.getBlock() == null) return;
		  if (e.getBlock().getLocation() == null) return;
		  if(!e.getBlock().equals(pot)){return;}
		  e.setCancelled(true);
		  if(!canBuild(e.getPlayer())){return;}
		  destroy(e.getPlayer());
		  pot.setType(Material.AIR);
		  pot=null;
		  return;
	}
	
	@EventHandler
	private void onPhysiks(BlockPhysicsEvent e){
		 if(getObjID()==null){return;}
		  if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		  if (pot==null) return;
		  if (e.getBlock() == null) return;
		  if (!e.getBlock().equals(pot)) return;
		  e.setCancelled(true);
	}
}
