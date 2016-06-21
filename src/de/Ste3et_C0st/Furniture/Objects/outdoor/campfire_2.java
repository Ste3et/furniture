package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class campfire_2 extends Furniture implements Listener{
	List<Material> items = new ArrayList<Material>(
			Arrays.asList(
					Material.RAW_BEEF,
					Material.RAW_CHICKEN,
					Material.RAW_FISH,
					Material.POTATO_ITEM,
					Material.PORK,
					Material.RABBIT,
					Material.MUTTON
				)
			);
	List<Material> items2 = new ArrayList<Material>(
			Arrays.asList(
					Material.COOKED_BEEF,
					Material.COOKED_CHICKEN,
					Material.COOKED_FISH,
					Material.BAKED_POTATO,
					Material.GRILLED_PORK,
					Material.COOKED_RABBIT,
					Material.COOKED_MUTTON
				)
			);

	EulerAngle[] angle = {
			new EulerAngle(-1.5, -.5, 0),
			new EulerAngle(-1.9, -.3, .7),
			new EulerAngle(-1.5, .3, 1.9),
			new EulerAngle(-0.7, -.5, -1.2)
			
	};
	
	Location middle;
	Location grill;
	Integer timer;
	fArmorStand armorS;
	ItemStack is;
	
	public campfire_2(ObjectID id){
		super(id);
	    middle = getLutil().getCenter(getLocation());
		middle = getLutil().getRelativ(middle, getBlockFace(), .5D, -.5D);
		middle.add(0,-1.2,0);
		
	    grill = getLutil().getRelativ(middle,getBlockFace(), .0D, .5D);
		grill.setYaw(getLutil().FaceToYaw(getBlockFace())+90);
		if(id.isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}
	
	public void spawn(Location loc){
		List<fArmorStand> packetList = new ArrayList<fArmorStand>();
		Location stick1 = getLutil().getRelativ(middle, getBlockFace(), .47D, -.05D);
		Location stick2 = getLutil().getRelativ(middle, getBlockFace(), .47D, .85D);
		
		Location bone = getLutil().getRelativ(middle, getBlockFace(), .5D, .82D);
		stick2.setYaw(getLutil().FaceToYaw(getBlockFace()));
		
		stick1.setYaw(getLutil().FaceToYaw(getBlockFace()));
		bone.setYaw(getLutil().FaceToYaw(getBlockFace()));
		stick1.add(0,.3,0);
		stick2.add(0,.3,0);
		bone.add(0,.17,0);
		Integer yaw = 90;
		for(int i = 0; i<=7;i++){
			Location location = null;
			if(getLutil().axisList.contains(getLutil().yawToFaceRadial(yaw))){
				location = getLutil().getRelativ(middle, getLutil().yawToFaceRadial(yaw), 0D, .5D);
			}else{
				location = getLutil().getRelativ(middle, getLutil().yawToFaceRadial(yaw), 0D, .35D);
			}
			
			location.setYaw(90+yaw);
			
			fArmorStand asp = getManager().createArmorStand(getObjID(), location);
			asp.setPose(new EulerAngle(1.568, 0, 0), BodyPart.HEAD);
			asp.setSmall(true);
			asp.getInventory().setHelmet(new ItemStack(Material.STEP,1,(short)3));
			packetList.add(asp);
			yaw+=45;
		}
		
		yaw = 90;
		for(int i = 0; i<=3;i++){
			Location location = getLutil().getRelativ(middle, getLutil().yawToFace(yaw), .4, -.5D);
			location.add(0,-.5,0);
			location.setYaw(90+yaw);
			
			fArmorStand asp = getManager().createArmorStand(getObjID(), location);
			asp.setPose( new EulerAngle(2, 0, 0), BodyPart.RIGHT_ARM);
			asp.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
			
			packetList.add(asp);
			yaw+=90;
		}
		
		fArmorStand asp = getManager().createArmorStand(getObjID(), stick1);
		
		asp.setPose(new EulerAngle(1.38,.0,.0), BodyPart.RIGHT_ARM);
		asp.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
		packetList.add(asp);
		asp = getManager().createArmorStand(getObjID(), stick2);
		
		asp.setPose(new EulerAngle(1.38,.0,.0), BodyPart.RIGHT_ARM);
		asp.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
		packetList.add(asp);
		asp = getManager().createArmorStand(getObjID(), bone);
		asp.setPose(new EulerAngle(1.38,.0,1.57), BodyPart.RIGHT_ARM);
		asp.getInventory().setItemInMainHand(new ItemStack(Material.BONE));
		packetList.add(asp);
		
		asp = getManager().createArmorStand(getObjID(), middle.add(0,-1.3,0));
		asp.setSmall(true);
		packetList.add(asp);
		
		for(fArmorStand packet : packetList){
			packet.setInvisible(true);
		}
		getManager().send(getObjID());
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		List<fEntity> aspList = getManager().getfArmorStandByObjectID(getObjID());
		final ItemStack itemStack = e.getPlayer().getInventory().getItemInMainHand();
		fArmorStand packet = null;
		for(fEntity pack : aspList){
			if(pack instanceof fArmorStand){
				fArmorStand stand = (fArmorStand) pack;
				if(stand.isSmall() && pack.isInvisible()){
					packet = stand;
				}
			}
		}
		if(itemStack.getType().equals(Material.WATER_BUCKET) && packet.isFire()){
			 setfire(false);
		}else if(itemStack.getType().equals(Material.FLINT_AND_STEEL) && !packet.isFire()){
			 setfire(true);
		}else if(items.contains(itemStack.getType()) && packet.isFire() && armorS==null){
			is = itemStack.clone();
			is.setAmount(1);
			
			setGrill();
			
			if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE) && getLib().useGamemode()) return;
			Integer i = e.getPlayer().getInventory().getHeldItemSlot();
			ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
			item.setAmount(item.getAmount()-1);
			e.getPlayer().getInventory().setItem(i, item);
			e.getPlayer().updateInventory();
		}
		
	}
	
	private void setfire(boolean b){
		for(fEntity pack : getManager().getfArmorStandByObjectID(getObjID())){
			if(pack instanceof fArmorStand){
				fArmorStand stand = (fArmorStand) pack;
				if(stand.isSmall() && pack.isInvisible()){
					if((pack.getInventory().getHelmet() == null || pack.getInventory().getHelmet().getType().equals(Material.AIR)) &&
					   (pack.getInventory().getItemInMainHand() == null || pack.getInventory().getItemInMainHand().getType().equals(Material.AIR))){					
						pack.setFire(b);
						Location loc = middle.clone();
						loc.add(0, 1.3, 0);
						if(b) getLib().getLightManager().addLight(loc, 15);
						if(!b) getLib().getLightManager().removeLight(loc);
						getManager().updateFurniture(getObjID());
						return;
					}

				}
			}
		}
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		if(isRunning()){
			Bukkit.getScheduler().cancelTask(timer);
			timer=null;
			getWorld().dropItemNaturally(middle.clone().add(0, 1, 0), is).setVelocity(new Vector().zero());
		}
		setfire(false);
		e.remove();
		delete();
	}
	
	public void removeGrill(){
		if(isRunning()){
			Bukkit.getScheduler().cancelTask(timer);
			timer=null;
			if(armorS!=null&&armorS.getInventory().getItemInMainHand()!=null&&getItem(armorS.getInventory().getItemInMainHand())!=null){
				getWorld().dropItemNaturally(middle.clone().add(0, 1, 0), getCooked(is)).setVelocity(new Vector().zero());
				getObjID().getPacketList().remove(this.armorS);
				armorS.kill();
				armorS.delete();
				armorS=null;
			}
		}
		if(armorS!=null){
			if(armorS.getInventory().getItemInMainHand()!=null){getWorld().dropItemNaturally(middle.clone().add(0, 1, 0), getCooked(is)).setVelocity(new Vector().zero());}
			getObjID().getPacketList().remove(this.armorS);
			armorS.kill();
			armorS.delete();
			armorS=null;
		}
		update();
	}
	
	public ItemStack getItem(ItemStack is){
		if(is==null){return null;}
		if(is.getType()==null){return null;}
		if(items.contains(is.getType())){
			return is;
		}
		return null;
	}
	
	public ItemStack getCooked(ItemStack is){
		if(is==null){return null;}
		if(is.getType()==null){return null;}
		if(items.contains(is.getType())){
			return new ItemStack(items2.get(items.indexOf(is.getType())));
		}
		return is;
	}
	
	public boolean isRunning(){
		if(timer==null){return false;}
		return true;
	}
	
	public void setGrill(){
		this.armorS = getManager().createArmorStand(getObjID(), grill);
		this.armorS.setInvisible(true);
		this.armorS.getInventory().setItemInMainHand(is);
		getObjID().getPlayerList().clear();
		send();
		this.timer = main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(main.getInstance(), new Runnable() {
			Integer rounds = getLutil().randInt(15, 30);
			Integer labs = 0;
			Integer position = 0;
			@Override
			public void run() {
				if(labs>=rounds){removeGrill();return;}
				if(position>3){position=0;}
				if(armorS!=null){
					armorS.setPose(angle[position], BodyPart.RIGHT_ARM);
					update();
				}
				position++;
				labs++;
			}
		}, 0, 4);
	}
}
