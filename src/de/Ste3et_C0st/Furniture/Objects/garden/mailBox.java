package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.EventType;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class mailBox extends Furniture implements Listener {
	List<Block> blockList = new ArrayList<Block>();
	UUID uuid;
	
	public mailBox(ObjectID id){
		super(id);
		setBlock();
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}
	
	public void spawn(Location location){
		List<fArmorStand> aspList = new ArrayList<fArmorStand>();
		Location middle = getLutil().getCenter(getLocation());
		middle.add(0, -1.4, 0);
		
		switch (getBlockFace()) {
		case NORTH:middle = getLutil().getRelativ(middle, getBlockFace(), 0D, 0.03D);break;
		case EAST:middle = getLutil().getRelativ(middle, getBlockFace(), 0D, 0.03D);break;
		default:break;
		}
		
		fArmorStand as = getManager().createArmorStand(getObjID(), middle);
		as.getInventory().setHelmet(new ItemStack(Material.STONE));
		as.setSmall(true);
		aspList.add(as);

		for(int i = 0; i<=1;i++){
			Location loc = getLutil().getRelativ(middle.clone(), getBlockFace(), .47, .38).add(0, .88*i, 0);
			fArmorStand packet = getManager().createArmorStand(getObjID(), loc);
			packet.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
			packet.setPose(new EulerAngle(1.39, 0, 0), BodyPart.RIGHT_ARM);
			aspList.add(packet);
		}
		
		as = getManager().createArmorStand(getObjID(), getLutil().getRelativ(middle.clone().add(0, 1.25, 0), getBlockFace(), -.21, -.0D));
		as.getInventory().setHelmet(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 0));
		as.setSmall(true);
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), getLutil().getRelativ(middle.clone().add(0, 1.25, 0), getBlockFace(), .21, 0D));
		as.getInventory().setHelmet(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 0));
		as.setSmall(true);
		aspList.add(as);
		
		for(int i = 0; i<=4;i++){
			Location loc = getLutil().getRelativ(middle.clone().add(0, 1.898, 0), getBlockFace(), -.44+.165*i, .462D);
			fArmorStand packet = getManager().createArmorStand(getObjID(), loc);
			packet.setSmall(true);
			//x z y
			//-.7 .73 -.3
			packet.setPose(new EulerAngle(-.716D, .71D, -.32D), BodyPart.RIGHT_ARM);
			packet.getInventory().setItemInMainHand(new ItemStack(Material.SMOOTH_STAIRS));
			aspList.add(packet);
			
			loc = getLutil().getRelativ(middle.clone().add(0, 1.898, 0), getBlockFace().getOppositeFace(), -.44+.165*i, .462D);
			packet = getManager().createArmorStand(getObjID(), loc);
			packet.setSmall(true);
			//x z y
			//-.7 .73 -.3
			packet.setPose(new EulerAngle(-.716D, .71D, -.32D), BodyPart.RIGHT_ARM);
			packet.getInventory().setItemInMainHand(new ItemStack(Material.SMOOTH_STAIRS));
			aspList.add(packet);
			
			
			loc = getLutil().getRelativ(middle.clone().add(0, 1.898, 0), getBlockFace().getOppositeFace(), -.44+.165*i, .362D);
			packet = getManager().createArmorStand(getObjID(), loc);
			packet.setSmall(true);
			//x z y
			//-.7 .73 -.3
			packet.setPose(new EulerAngle(-.716D, .71D, -.32D), BodyPart.RIGHT_ARM);
			packet.getInventory().setItemInMainHand(new ItemStack(Material.SMOOTH_BRICK, 1 ,(short) 0));
			aspList.add(packet);
		}
		
		BlockFace face = getBlockFace();
		face = getLutil().yawToFace(getLutil().FaceToYaw(getBlockFace()) + 90);
		as = getManager().createArmorStand(getObjID(), getLutil().getRelativ(middle.clone().add(0, 1.5, 0), face, -.04, -.55D));
		as.getInventory().setHelmet(new ItemStack(Material.REDSTONE_TORCH_ON, 1, (short) 0));
		as.setPose(getLutil().degresstoRad(new EulerAngle(0, 0, 90)), BodyPart.HEAD);
		as.setName("#ELEMENT#");
		as.setSmall(true);
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), getLutil().getRelativ(middle.clone().add(0, 1.10, 0), face.getOppositeFace(), -.0, .31D));
		as.getInventory().setItemInMainHand(new ItemStack(Material.PAPER, 1, (short) 0));
		as.setPose(getLutil().degresstoRad(new EulerAngle(0, -120, -90)), BodyPart.RIGHT_ARM);
		as.setSmall(true);
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), getLutil().getRelativ(middle.clone().add(0, 1.40, 0), face.getOppositeFace(), -.15, .33D));
		as.getInventory().setItemInMainHand(new ItemStack(Material.WOOD_BUTTON, 1, (short) 0));
		as.setPose(getLutil().degresstoRad(new EulerAngle(-15,-67, -90)), BodyPart.RIGHT_ARM);
		as.setSmall(true);
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), getLutil().getRelativ(middle.clone().add(0, 1.2, 0), getBlockFace(), .2, .07D));
		as.getInventory().setItemInMainHand(new ItemStack(Material.EMPTY_MAP, 1, (short) 0));
		as.setPose(getLutil().degresstoRad(new EulerAngle(0, -120, -90)), BodyPart.RIGHT_ARM);
		as.setSmall(true);
		aspList.add(as);
		
		
		for(fArmorStand asp : aspList){
			asp.setInvisible(true);
			asp.setBasePlate(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	public void setBlock(){
		for(int i = 0; i<=1;i++){
			Block b = getLocation().clone().add(0, i, 0).getBlock();
			b.setType(Material.BARRIER);
			blockList.add(b);
		}
		getObjID().addBlock(blockList);
	}
	
	/*public void addMailbox(Player p){
		if(Bukkit.getPluginManager().isPluginEnabled("PostalService")){
			try {
				PostalService.getMailboxManager().addMailboxAtLoc(blockList.get(1).getLocation(), p);
				this.uuid = p.getUniqueId();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setSendet(Boolean b){
		for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(packet!=null&&packet.getName().equalsIgnoreCase("#ELEMENT#")){
				if(b){
					packet.setPose(getLutil().degresstoRad(new EulerAngle(0, 0, 0)), BodyPart.HEAD);
					getManager().updateFurniture(getObjID());
					return;
				}
				packet.setPose(getLutil().degresstoRad(new EulerAngle(0, 0, 90)), BodyPart.HEAD);
				getManager().updateFurniture(getObjID());
				return;
			}
		}
	}
	
	@EventHandler
	private void onMove(PlayerSendMailEvent e){
		if(!Bukkit.getPluginManager().isPluginEnabled("PostalService")){return;}
		UUID p1 = this.uuid;
		UUID p2 = e.getRecipient().getUUID();
		if(!p1.equals(p2)){return;}
		setSendet(true);
	}*/
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(!e.getID().equals(getObjID())) return;
		if(!e.canBuild()){return;}
		
		for(Block b : blockList){
			b.setType(Material.AIR);
		}
		blockList.clear();
		e.remove();
		delete();
	}
	
	public void onFurnitureClick(FurnitureClickEvent e){}
	
	@EventHandler
	private void onInteract(PlayerInteractEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(e.getAction()==null){return;}
		if(e.getClickedBlock()==null){return;}
		if(!blockList.contains(e.getClickedBlock())){return;}
		if(!getLib().canBuild(e.getPlayer(), getObjID(), EventType.INTERACT)){return;}
		if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
				e.setCancelled(true);
				for(Block b : blockList){
					b.setType(Material.AIR);
				}
				blockList.clear();
				this.getObjID().remove(e.getPlayer());
				delete();
		}
	}
}
