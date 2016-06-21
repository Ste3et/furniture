package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBlockBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBlockClickEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.EventType;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class hammock extends Furniture implements Listener{
	
	public hammock(ObjectID id) {
		super(id);
		setBlock();
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}
	
	private void setBlock(){
		BlockFace Right = getLutil().yawToFace(getLutil().FaceToYaw(getBlockFace())+90);
		Location center2 = getLutil().getRelativ(getLocation(), Right, 6D, 0D);
		Block b1 = getLocation().getBlock();
		Block b2 = getLocation().getBlock().getRelative(BlockFace.UP);
		Block b3 = center2.getBlock();
		Block b4 = center2.getBlock().getRelative(BlockFace.UP);

		if(!isFence(b1.getType())) b1.setType(Material.FENCE);
		if(!isFence(b2.getType())) b2.setType(Material.FENCE);
		if(!isFence(b3.getType())) b3.setType(Material.FENCE);
		if(!isFence(b4.getType())) b4.setType(Material.FENCE);

		getObjID().addBlock(Arrays.asList(b1,b2,b3,b4));
	}
	
	private boolean isFence(Material m){
		if(m==null){return false;}
		return m.toString().toLowerCase().endsWith("fence");
	}
	
	private boolean isMaterial(Material m){
		if(m==null){return false;}
		boolean b = false;
		if(m.toString().toLowerCase().startsWith("log")){
			b=true;
		}else if(m.toString().toLowerCase().equalsIgnoreCase("banner")){
			b=true;
		}
		
		return b;
	}

	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.remove();
		delete();
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBlockBreakEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
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
		List<fEntity> aspList = getManager().getfArmorStandByObjectID(getObjID());
		Player p = e.getPlayer();
		ItemStack stack = p.getInventory().getItemInMainHand();

		if(stack!=null){
			if(isMaterial(stack.getType())){
				if(setColor(p,stack, getLib().canBuild(e.getPlayer(), getObjID(), EventType.INTERACT), aspList)){return;}
			}
		}
		
		for(fEntity packet : aspList){
			if(packet.getName().equalsIgnoreCase("#SITZ#")){
				if(packet.getPassanger()==null){
					packet.setPassanger(e.getPlayer());
					return;
				}
			}
		}
	}
	
	public void removeItem(Player p){
		Boolean useGameMode = FurnitureLib.getInstance().useGamemode();
		if(useGameMode&&p.getGameMode().equals(GameMode.CREATIVE)){return;}
		Integer slot = p.getInventory().getHeldItemSlot();
		ItemStack itemStack = p.getInventory().getItemInMainHand().clone();
		itemStack.setAmount(itemStack.getAmount()-1);
		p.getInventory().setItem(slot, itemStack);
		p.updateInventory();
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureBlockClickEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		List<fEntity> aspList = getManager().getfArmorStandByObjectID(getObjID());
		Player p = e.getPlayer();
		ItemStack stack = p.getInventory().getItemInMainHand();
		if(stack!=null){
			if(isMaterial(stack.getType())){
				if(setColor(p,stack, e.canBuild(), aspList)){return;}
			}
		}
		
		for(fEntity packet : aspList){
			if(packet.getName().equalsIgnoreCase("#SITZ#")){
				if(packet.getPassanger()==null){
					packet.setPassanger(e.getPlayer());
					return;
				}
			}
		}
	}
	
	private boolean setColor(Player p,ItemStack stack, Boolean canbuild, List<fEntity> aspList){
		if(!canbuild){return true;}
		if(stack!=null){
			switch (stack.getType()) {
			case BANNER:
				for(fEntity packet : aspList){
					if(packet.getInventory().getHelmet()!=null&&packet.getInventory().getHelmet().getType().equals(Material.BANNER)){
						packet.getInventory().setHelmet(stack);
					}
				}
				removeItem(p);
				getManager().updateFurniture(getObjID());
				return true;
			case LOG:
				for(fEntity packet : aspList){
					if(packet.getName().equalsIgnoreCase("#PILLAR#")){
						packet.getInventory().setHelmet(stack);
					}
				}
				setPillar(stack.getDurability());
				removeItem(p);
				getManager().updateFurniture(getObjID());
				return true;
			case LOG_2:
				for(fEntity packet : aspList){
					if(packet.getName().equalsIgnoreCase("#PILLAR#")){
						packet.getInventory().setHelmet(stack);
					}
				}
				setPillar(4 +  stack.getDurability());
				removeItem(p);
				getManager().updateFurniture(getObjID());
				return true;
			default: break;
			}
		}
		return false;
	}
	
	private void setPillar(int i){
		Material m = null;
		switch (i) {
		case 0: m = Material.FENCE;break;
		case 1: m = Material.SPRUCE_FENCE;break;
		case 2: m = Material.BIRCH_FENCE;break;
		case 3: m = Material.JUNGLE_FENCE;break;
		case 4: m = Material.ACACIA_FENCE;break;
		case 5: m = Material.DARK_OAK_FENCE;break;
		}
		if(m!=null){
			for(Location b : getObjID().getBlockList()){
				getWorld().getBlockAt(b).setType(m);
			}
		}
	}

	
	public void spawn(Location location) {
		Location center1 = getLutil().getCenter(location);
		center1 = center1.add(0, -1.9, 0);
		
		BlockFace Right = getLutil().yawToFace(getLutil().FaceToYaw(getBlockFace())+90);
		Location center2 = getLutil().getRelativ(center1, Right, 6D, 0D);
		List<fArmorStand> aspList = new ArrayList<fArmorStand>();
		
		fArmorStand packet = getManager().createArmorStand(getObjID(), center1);
		packet.getInventory().setHelmet(new ItemStack(Material.LOG));
		packet.setName("#PILLAR#");
		aspList.add(packet);
		
		packet = getManager().createArmorStand(getObjID(), center2);
		packet.getInventory().setHelmet(new ItemStack(Material.LOG));
		packet.setName("#PILLAR#");
		aspList.add(packet);
		
		Location middle = getLutil().getRelativ(center1, Right, 2.85D, 0D);
		middle = middle.add(0, .75, 0);
		packet = getManager().createArmorStand(getObjID(), middle);
		packet.getInventory().setHelmet(new ItemStack(Material.BANNER, 1, (short) 0));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(-69f,0f,0f)), BodyPart.HEAD);
		aspList.add(packet);
		
		Location middle2 = getLutil().getRelativ(middle, Right, 1.5D, 0D);
		middle2 = middle2.add(0, .23, 0);
		packet = getManager().createArmorStand(getObjID(), middle2);
		packet.getInventory().setHelmet(new ItemStack(Material.BANNER, 1, (short) 0));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(-90f,0f,0f)), BodyPart.HEAD);
		aspList.add(packet);
		
		Location sitz = getLutil().getRelativ(center1, Right, 3D, 0D).add(0, .6, 0);
		sitz.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
		packet = getManager().createArmorStand(getObjID(), sitz);
		packet.setName("#SITZ#");
		aspList.add(packet);
		
		middle = getLutil().getRelativ(middle, Right, .3D, 0D);
		middle.setYaw(middle.getYaw()+180);
		packet = getManager().createArmorStand(getObjID(), middle);
		packet.getInventory().setHelmet(new ItemStack(Material.BANNER, 1, (short) 0));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(-69f,0f,0f)), BodyPart.HEAD);
		aspList.add(packet);
		
		Location stick = getLutil().getRelativ(center1, Right, 4.6d, .5d);
		stick = stick.add(0, 1.7, 0);
		stick.setYaw(getLutil().FaceToYaw(getBlockFace()));
		packet = getManager().createArmorStand(getObjID(), stick);
		packet.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(-40f,110f,0f)), BodyPart.RIGHT_ARM);
		aspList.add(packet);
		
		stick = getLutil().getRelativ(center1, Right, 4.6d, -.335d);
		stick = stick.add(0, 1.7, 0);
		stick.setYaw(getLutil().FaceToYaw(getBlockFace()));
		packet = getManager().createArmorStand(getObjID(), stick);
		packet.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(-40f,73f,0f)), BodyPart.RIGHT_ARM);
		packet.setArms(true);
		aspList.add(packet);
		
		stick = getLutil().getRelativ(center1, Right.getOppositeFace(), -1.37d, -.335d);
		stick = stick.add(0, 1.7, 0);
		stick.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
		packet = getManager().createArmorStand(getObjID(), stick);
		packet.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(-40f,73f,0f)), BodyPart.RIGHT_ARM);
		packet.setArms(true);
		aspList.add(packet);
		
		stick = getLutil().getRelativ(center1, Right.getOppositeFace(), -1.37d, .5d);
		stick = stick.add(0, 1.7, 0);
		stick.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
		packet = getManager().createArmorStand(getObjID(), stick);
		packet.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(-40f,110f,0f)), BodyPart.RIGHT_ARM);
		packet.setArms(true);
		aspList.add(packet);
		
		for(fArmorStand pack : aspList){
			pack.setInvisible(true);
			pack.setBasePlate(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
}
