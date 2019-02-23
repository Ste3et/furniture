package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class campfire_2 extends Furniture{
	List<Material> items = new ArrayList<Material>(
			Arrays.asList(
					Material.BEEF,
					Material.CHICKEN,
					Material.COD,
					Material.POTATO,
					Material.PORKCHOP,
					Material.RABBIT,
					Material.MUTTON
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
			asp.getInventory().setHelmet(new ItemStack(Material.STONE_SLAB,1));
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
		
		asp = getManager().createArmorStand(getObjID(), middle.add(0,.1,0));
		asp.setSmall(true);
		packetList.add(asp);
		
		for(fArmorStand packet : packetList){
			packet.setInvisible(true);
		}
		getManager().send(getObjID());
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	@Override
	public void onClick(Player player){
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			List<fEntity> aspList = getManager().getfArmorStandByObjectID(getObjID());
			final ItemStack itemStack = player.getInventory().getItemInMainHand();
			fArmorStand packet = null;
			for(fEntity pack : aspList){
				if(pack instanceof fArmorStand){
					fArmorStand stand = (fArmorStand) pack;
					if(stand.isSmall() && pack.isInvisible()){
						packet = stand;
						if(packet.isFire()) {
							break;
						}
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
				
				if(player.getGameMode().equals(GameMode.CREATIVE) && getLib().useGamemode()) return;
				Integer i = player.getInventory().getHeldItemSlot();
				ItemStack item = player.getInventory().getItemInMainHand();
				item.setAmount(item.getAmount()-1);
				player.getInventory().setItem(i, item);
				player.updateInventory();
			}
		}
	}
	
	private void setfire(boolean b){
		Location loc = getCenter().clone();
		loc.add(0, 1.3, 0);
		if(b){
			getLib().getLightManager().addLight(loc, 15);
		}else{
			getLib().getLightManager().removeLight(loc);
		}
		
		for(fEntity entity : getfAsList()){
			if(entity instanceof fArmorStand){
				fArmorStand stand = (fArmorStand) entity;
				if((stand.getInventory().getHelmet() == null || stand.getInventory().getHelmet().getType().equals(Material.AIR)) &&
						   (stand.getInventory().getItemInMainHand() == null || stand.getInventory().getItemInMainHand().getType().equals(Material.AIR))){
					if(stand.isSmall() && entity.isInvisible()){
						stand.setFire(b);
					}
				}
			}
		}
		update();
	}
	
	@Override
	public void onBreak(Player player) {
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			if(isRunning()){
				Bukkit.getScheduler().cancelTask(timer);
				timer=null;
				getWorld().dropItemNaturally(middle.clone().add(0, 1, 0), is).setVelocity(new Vector().zero());
			}
			setfire(false);
			this.destroy(player);
		}
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
			Material mat = null;
			int rnd = (int)(Math.random()*100);
			if(rnd < 5) {
				mat = Material.COAL;
			}else {
				switch(is.getType()) {
					case BEEF: mat = Material.COOKED_BEEF; break;
					case CHICKEN: mat = Material.COOKED_CHICKEN; break;
					case COD: mat = Material.COOKED_COD; break;
					case POTATO: mat = Material.BAKED_POTATO; break;
					case PORKCHOP: mat = Material.COOKED_PORKCHOP; break;
					case RABBIT: mat = Material.RABBIT_STEW; break;
					case MUTTON: mat = Material.COOKED_MUTTON; break;
					default: mat = Material.COAL;break;
				}
			}
			return new ItemStack(mat);
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
