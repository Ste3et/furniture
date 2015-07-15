package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.ArmorStandPacket;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;

public class campfire_2 implements Listener{
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
	
	Location loc;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	Integer id;
	Plugin plugin;
	
	Location middle;
	Location grill;
	Integer timer;
	ArmorStandPacket armorS;
	ItemStack is;
	public campfire_2(Location location, FurnitureLib lib, String name, Plugin plugin, ObjectID id){
		this.lutil = main.getLocationUtil();
		this.b = lutil.yawToFace(location.getYaw());
		this.loc = location.getBlock().getLocation();
		this.loc.setYaw(location.getYaw());
		this.w = location.getWorld();
		this.manager = lib.getFurnitureManager();
		this.lib = lib;
		this.plugin = plugin;
	    middle = lutil.getCenter(loc);
		middle = lutil.getRelativ(middle, b, .5D, -.5D);
		middle.add(0,-1.2,0);
		
	    grill = lutil.getRelativ(middle,b, .0D, .5D);
		grill.setYaw(lutil.FaceToYaw(b)+90);
		
		if(id!=null){
			this.obj = id;
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}else{
			this.obj = new ObjectID(name, plugin.getName(), location);
		}
		spawn(location);
	}
	
	public void spawn(Location loc){
		List<ArmorStandPacket> packetList = new ArrayList<ArmorStandPacket>();
		Location stick1 = lutil.getRelativ(middle, b, .47D, -.05D);
		Location stick2 = lutil.getRelativ(middle, b, .47D, .85D);
		
		Location bone = lutil.getRelativ(middle, b, .5D, .82D);
		stick2.setYaw(lutil.FaceToYaw(b));
		
		stick1.setYaw(lutil.FaceToYaw(b));
		bone.setYaw(lutil.FaceToYaw(b));
		stick1.add(0,.3,0);
		stick2.add(0,.3,0);
		bone.add(0,.17,0);
		Integer yaw = 90;
		for(int i = 0; i<=7;i++){
			Location location = null;
			if(lutil.axisList.contains(lutil.yawToFaceRadial(yaw))){
				location = lutil.getRelativ(middle, lutil.yawToFaceRadial(yaw), 0D, .5D);
			}else{
				location = lutil.getRelativ(middle, lutil.yawToFaceRadial(yaw), 0D, .35D);
			}
			
			location.setYaw(90+yaw);
			
			ArmorStandPacket asp = manager.createArmorStand(obj, location);
			asp.setPose(new EulerAngle(1.568, 0, 0), BodyPart.HEAD);
			asp.setSmall(true);
			asp.getInventory().setHelmet(new ItemStack(Material.STEP,1,(short)3));
			packetList.add(asp);
			yaw+=45;
		}
		
		yaw = 90;
		for(int i = 0; i<=3;i++){
			Location location = lutil.getRelativ(middle, lutil.yawToFace(yaw), .4, -.5D);
			location.add(0,-.5,0);
			location.setYaw(90+yaw);
			
			ArmorStandPacket asp = manager.createArmorStand(obj, location);
			asp.setPose( new EulerAngle(2, 0, 0), BodyPart.RIGHT_ARM);
			asp.getInventory().setItemInHand(new ItemStack(Material.STICK));
			
			packetList.add(asp);
			yaw+=90;
		}
		
		ArmorStandPacket asp = manager.createArmorStand(obj, stick1);
		
		asp.setPose(new EulerAngle(1.38,.0,.0), BodyPart.RIGHT_ARM);
		asp.getInventory().setItemInHand(new ItemStack(Material.STICK));
		packetList.add(asp);
		asp = manager.createArmorStand(obj, stick2);
		
		asp.setPose(new EulerAngle(1.38,.0,.0), BodyPart.RIGHT_ARM);
		asp.getInventory().setItemInHand(new ItemStack(Material.STICK));
		packetList.add(asp);
		asp = manager.createArmorStand(obj, bone);
		asp.setPose(new EulerAngle(1.38,.0,1.57), BodyPart.RIGHT_ARM);
		asp.getInventory().setItemInHand(new ItemStack(Material.BONE));
		packetList.add(asp);
		
		asp = manager.createArmorStand(obj, middle.add(0,-1.3,0));
		asp.setSmall(true);
		packetList.add(asp);
		
		for(ArmorStandPacket packet : packetList){
			packet.setInvisible(true);
			packet.setGravity(false);
		}
		
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	private void onClick(FurnitureClickEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.canBuild(null)){return;}
		if(!e.getID().equals(obj)){return;}
		e.setCancelled(true);
		List<ArmorStandPacket> aspList = manager.getArmorStandPacketByObjectID(obj);
		final ItemStack itemStack = e.getPlayer().getItemInHand();
		ArmorStandPacket packet = null;
		for(ArmorStandPacket pack : aspList){
			if(pack.isMini() && pack.isInvisible()){
				packet = pack;
			}
		}

		if(itemStack.getType().equals(Material.WATER_BUCKET) && packet.isFire()){
			 packet.setFire(false);
			 manager.updateFurniture(obj);
			 Location loc = middle.clone();
			 loc.add(0, 1.3, 0);
			 lib.getLightManager().removeLight(loc);
		}else if(itemStack.getType().equals(Material.FLINT_AND_STEEL) && !packet.isFire()){
			 packet.setFire(true);
			 manager.updateFurniture(obj);
			 Location loc = middle.clone();
			 loc.add(0, 1.3, 0);
			 lib.getLightManager().addLight(loc, 15);
		}else if(items.contains(itemStack.getType()) && packet.isFire() && armorS==null){
			is = itemStack.clone();
			is.setAmount(1);
			setGrill();
		}
		
	}
	
	@EventHandler
	private void onBreak(FurnitureBreakEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.canBuild(null)){return;}
		if(!e.getID().equals(obj)){return;}
		e.setCancelled(true);
		if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
			w.dropItem(loc.add(0,1,0), manager.getProject(obj.getProject()).getCraftingFile().getRecipe().getResult());
		}
		main.deleteEffect(manager.getArmorStandPacketByObjectID(obj));
		if(isRunning()){
			Bukkit.getScheduler().cancelTask(timer);
			timer=null;
			w.dropItem(middle.clone().add(0, .5, 0), is);
		}
		manager.remove(obj);
		e.remove();
		obj=null;
	}
	
	public void removeGrill(){
		if(isRunning()){
			Bukkit.getScheduler().cancelTask(timer);
			timer=null;
			if(armorS!=null&&armorS.getInventory().getItemInHand()!=null&&getItem(armorS.getInventory().getItemInHand())!=null){
				w.dropItem(middle.clone().add(0, .5, 0), getCooked(is));
				armorS.destroy();
				armorS.delete();
				armorS=null;
			}
		}
		if(armorS!=null){
			if(armorS.getInventory().getItemInHand()!=null){w.dropItem(middle.clone().add(0, .5, 0), getCooked(is));}
			armorS.destroy();
			armorS.delete();
			armorS=null;
		}
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
		this.armorS = manager.createArmorStand(obj, grill);
		this.armorS.setInvisible(true);
		this.armorS.getInventory().setItemInHand(is);
		manager.send(armorS);
		this.timer = main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(main.getInstance(), new Runnable() {
			Integer rounds = lutil.randInt(15, 30);
			Integer labs = 0;
			Integer position = 0;
			@Override
			public void run() {
				if(labs>=rounds){removeGrill();return;}
				if(position>3){position=0;}
				if(armorS!=null){
					armorS.setPose(angle[position], BodyPart.RIGHT_ARM);
					armorS.update();
				}
				position++;
				labs++;
			}
		}, 0, 4);
	}
}
