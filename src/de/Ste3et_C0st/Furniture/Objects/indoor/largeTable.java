package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class largeTable extends Furniture implements Listener{

	public largeTable(ObjectID id){
		super(id);
		if(isFinish()){
			for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
				if(packet.getName().startsWith("#TELLER")){
					tellerIDs.add(packet.getEntityID());
				}
			}
			
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}
	List<Integer> tellerIDs = new ArrayList<Integer>();
	
	public void spawn(Location loc){
		List<fArmorStand> armorlist = new ArrayList<fArmorStand>();
		
		Location location = getLutil().getCenter(loc.getBlock().getLocation());
		float yaw = getLutil().FaceToYaw(getBlockFace());
		location = getLutil().getRelativ(location, getBlockFace(), 0.1, 0.28);
		location.add(0,.2,0);
		Double winkel = 1.57;
		ItemStack iTemStack_1 = new ItemStack(Material.STAINED_GLASS_PANE);
		double off = .46;
		double off2 = off*2+.1;
		for(int x=1; x<=3;x++){
			Location l = getLutil().getRelativ(location.clone(), getBlockFace(), -off, x*-.62);
			l.add(0,-1.48,0);
			l.setYaw(yaw);
			
			fArmorStand as = getManager().createArmorStand(getObjID(), l.clone());
			as.setPose(new EulerAngle(winkel, 0, 0), BodyPart.HEAD);
			as.getInventory().setHelmet(iTemStack_1);
			armorlist.add(as);
		}
		
		for(int x=1; x<=3;x++){
			Location l = getLutil().getRelativ(location.clone(), getBlockFace(), 0.62-off, x*-.62);
			l.add(0,-1.48,0);
			l.setYaw(yaw);
			fArmorStand as = getManager().createArmorStand(getObjID(), l.clone());
			as.setPose(new EulerAngle(winkel, 0, 0), BodyPart.HEAD);
			as.getInventory().setHelmet(iTemStack_1);
			armorlist.add(as);
		}
		
		for(int x=1; x<=3;x++){
			Location l = getLutil().getRelativ(location.clone(), getBlockFace(), 1.24-off, x*-.62);
			l.add(0,-1.48,0);
			l.setYaw(yaw);
			fArmorStand as = getManager().createArmorStand(getObjID(), l.clone());
			as.setPose(new EulerAngle(winkel, 0, 0), BodyPart.HEAD);
			as.getInventory().setHelmet(iTemStack_1);
			armorlist.add(as);
		}

		Location middle = getLutil().getCenter(armorlist.get(0).getLocation());
		Location mitteTisch = getLutil().getCenter(armorlist.get(4).getLocation().getBlock().getLocation());
		middle.add(0, -.9, 0);
		Location feet1 = getLutil().getRelativ(middle, getBlockFace(), -.2, .1);
		Location feet2 = getLutil().getRelativ(middle, getBlockFace(), -.2, -1.3);
		Location feet3 = getLutil().getRelativ(middle, getBlockFace(), 1.1, .1);
		Location feet4 = getLutil().getRelativ(middle, getBlockFace(), 1.1, -1.3);
		
		
		double hight = .67;
		
		Location t1 = getLutil().getRelativ(mitteTisch, getBlockFace(), -.95+off2, .4).add(0,hight,0);
		Location t2 = getLutil().getRelativ(mitteTisch, getBlockFace(), -.4+off2, -.92).add(0,hight,0);
		Location t3 = getLutil().getRelativ(mitteTisch, getBlockFace(), .92+off2, -.36).add(0,hight,0);
		Location t4 = getLutil().getRelativ(mitteTisch, getBlockFace(), .4+off2, .92).add(0,hight,0);
		
		float yaw1 = yaw;
		float yaw2 = yaw1-90;
		float yaw3 = yaw2-90;
		float yaw4 = yaw3-90;
		
		t1.setYaw(yaw1);
		t2.setYaw(yaw2);
		t3.setYaw(yaw3);
		t4.setYaw(yaw4);
		
		feet1.setYaw(yaw);
		feet2.setYaw(yaw);
		feet3.setYaw(yaw);
		feet4.setYaw(yaw);
		
		fArmorStand as = getManager().createArmorStand(getObjID(), feet1);
		as.setPose(new EulerAngle(-1.75, 0, 0), BodyPart.RIGHT_ARM);
		as.getInventory().setItemInMainHand(new ItemStack(Material.BONE));
		armorlist.add(as);
		
		as = getManager().createArmorStand(getObjID(), feet2);
		as.setPose(new EulerAngle(-1.75, 0, 0), BodyPart.RIGHT_ARM);
		as.getInventory().setItemInMainHand(new ItemStack(Material.BONE));
		armorlist.add(as);
		
		as = getManager().createArmorStand(getObjID(), feet3);
		as.setPose(new EulerAngle(-1.75, 0, 0), BodyPart.RIGHT_ARM);
		as.getInventory().setItemInMainHand(new ItemStack(Material.BONE));
		armorlist.add(as);
		
		as = getManager().createArmorStand(getObjID(), feet4);
		as.setPose(new EulerAngle(-1.75, 0, 0), BodyPart.RIGHT_ARM);
		as.getInventory().setItemInMainHand(new ItemStack(Material.BONE));
		armorlist.add(as);
		
		
		as = getManager().createArmorStand(getObjID(), t1);
		as.setName("#TELLER1#");
		as.setPose(new EulerAngle(0, 0, 0), BodyPart.RIGHT_ARM);
		armorlist.add(as);
		tellerIDs.add(as.getEntityID());
		as = getManager().createArmorStand(getObjID(), t2);
		as.setName("#TELLER2#");
		as.setPose(new EulerAngle(0, 0, 0), BodyPart.RIGHT_ARM);
		armorlist.add(as);
		tellerIDs.add(as.getEntityID());
		as = getManager().createArmorStand(getObjID(), t3);
		as.setName("#TELLER3#");
		as.setPose(new EulerAngle(0, 0, 0), BodyPart.RIGHT_ARM);
		armorlist.add(as);
		tellerIDs.add(as.getEntityID());
		as = getManager().createArmorStand(getObjID(), t4);
		as.setName("#TELLER4#");
		as.setPose(new EulerAngle(0, 0, 0), BodyPart.RIGHT_ARM);
		armorlist.add(as);
		tellerIDs.add(as.getEntityID());
		
		for(fArmorStand packet : armorlist){
			packet.setInvisible(true);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	public void setTeller(HashMap<Integer, ItemStack> itemList){
		int i = 0;
		for(Integer id : tellerIDs){
			fArmorStand as = getManager().getfArmorStandByID(id);
			as.getInventory().setItemInMainHand(itemList.get(i));
			i++;
		}
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(e.getID()==null){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		for(Integer id : tellerIDs){
			fArmorStand asp = getManager().getfArmorStandByID(id);
			if(asp!=null&&asp.getInventory().getItemInMainHand()!=null){
				if(asp.getName().startsWith("#TELLER")){
					fArmorStand packet = asp;
					e.getLocation().getWorld().dropItem(e.getLocation(), packet.getInventory().getItemInMainHand());
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
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		Player p = e.getPlayer();
		if(p.getInventory().getItemInMainHand().getType().equals(Material.INK_SACK)){
			getLib().getColorManager().color(p, e.canBuild(), Material.STAINED_GLASS_PANE, getObjID(), ColorType.BLOCK, 3);
			update();
			return;
		}else{
			setTeller(p, p.getInventory().getItemInMainHand());
		}
	}
	
	public void setTeller(Player player, ItemStack is){
		BlockFace b = getLutil().yawToFace(player.getLocation().getYaw());
		fArmorStand as = null;
		if(tellerIDs == null || tellerIDs.isEmpty()){return;}
		for(Integer id : this.tellerIDs){
			if(id!=null){
				fArmorStand armorStand = getManager().getfArmorStandByID(id);
				if(armorStand!=null){
					BlockFace b2 = getLutil().yawToFace(armorStand.getLocation().getYaw());
					if(b2.equals(b)){
						as = armorStand;
						break;
					}
				}
			}
		}
		
		if(as!=null&&as.getInventory().getItemInMainHand()!= null && as.getInventory().getItemInMainHand().equals(is)){return;}
		if(as.getInventory().getItemInMainHand()!=null&&!as.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
			fArmorStand asp = as;
			ItemStack item = asp.getInventory().getItemInMainHand();
			item.setAmount(1);
			asp.getLocation().getWorld().dropItem(asp.getLocation(), item);
		}
		
		ItemStack IS = is.clone();
		IS.setAmount(1);
		as.getInventory().setItemInMainHand(IS);
		
		update();
		
		if(player.getGameMode().equals(GameMode.CREATIVE) && getLib().useGamemode()) return;
		Integer i = player.getInventory().getHeldItemSlot();
		ItemStack itemstack = is.clone();
		itemstack.setAmount(itemstack.getAmount()-1);
		player.getInventory().setItem(i, itemstack);
		player.updateInventory();
	}
	
	public HashMap<Integer, ItemStack> getTeller(){
		HashMap<Integer, ItemStack> teller = new HashMap<Integer, ItemStack>();
		for(Integer id : tellerIDs){
			try{
				fArmorStand as = getManager().getfArmorStandByID(id);
				teller.put(teller.size(), as.getInventory().getItemInMainHand());
			}catch(Exception e){
				teller.put(teller.size(), new ItemStack(Material.AIR));
			}
		}
		return teller;
	}
}
