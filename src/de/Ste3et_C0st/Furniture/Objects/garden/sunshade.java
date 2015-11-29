package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
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

public class sunshade extends Furniture implements Listener{
	List<Material> matList = Arrays.asList(
			Material.SPRUCE_FENCE,
			Material.BIRCH_FENCE,
			Material.JUNGLE_FENCE,
			Material.DARK_OAK_FENCE,
			Material.ACACIA_FENCE,
			Material.COBBLE_WALL,
			Material.NETHER_FENCE);
	Material m;
	Integer timer;
	Block block;
	
	public sunshade(ObjectID id){
		super(id);
		if(isFinish()){
			setblock();
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		setblock();
		spawn(id.getStartLocation());
	}
	
	private void setblock(){
		Location loc = getLocation().clone();
		loc.add(0, 2, 0);
		block = loc.getBlock();
		block.setType(Material.BARRIER);
		getObjID().addBlock(Arrays.asList(block));
	}
	
	public void spawn(Location location){
		Location center = getLutil().getCenter(location).clone();
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		center.add(0, -1.1, 0);
		
		for(int i = 0; i<=2;i++){
			Location loc = getLutil().getRelativ(center.clone(), getBlockFace(), .47, .38).add(0, .88*i, 0);
			fArmorStand packet = getManager().createArmorStand(getObjID(), loc);
			packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
			packet.setPose(new EulerAngle(1.39, 0, 0), BodyPart.RIGHT_ARM);
			asList.add(packet);
		}
		
		center.add(0, 1.758, 0);
		fArmorStand aspacket = getManager().createArmorStand(getObjID(), center);
		aspacket.getInventory().setHelmet(new ItemStack(Material.CARPET));
		aspacket.setName("#TOP#");
		asList.add(aspacket);
		
		Location loc = center.clone(); 
		loc.add(0, 0.3, 0);
		ItemStack is = new ItemStack(Material.BANNER);
		BannerMeta banner = (BannerMeta) is.getItemMeta();
		banner.setBaseColor(DyeColor.LIME);
		banner.addPattern(new Pattern(DyeColor.YELLOW, PatternType.TRIANGLE_BOTTOM));
		banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM));
		is.setItemMeta(banner);
		
		for(int i = 0; i<=17; i++){
			loc.setYaw(i*21);
			fArmorStand packet = getManager().createArmorStand(getObjID(), loc.clone());
			packet.getInventory().setHelmet(is);
			packet.setPose(new EulerAngle(-3.054, 0, 0), BodyPart.HEAD);
			packet.setName("#ELEMENT#" + i);
			asList.add(packet);
		}
		
		for(fArmorStand packet : asList){
			packet.setInvisible(true);
			packet.setGravity(false);
			packet.setBasePlate(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		Player p = e.getPlayer();
		ItemStack is = p.getItemInHand();
		if(!getLib().canBuild(e.getPlayer(), getObjID(), EventType.INTERACT)){return;}
		if(is.getType().equals(Material.BANNER)){
			e.setCancelled(true);
			if(isRunning()){return;}
			for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
				if(packet.getInventory().getHelmet()!=null&&packet.getInventory().getHelmet().getType().equals(Material.BANNER)){
					packet.getInventory().setHelmet(is);
				}else if(packet.getInventory().getHelmet()!=null&&packet.getInventory().getHelmet().getType().equals(Material.CARPET)){
					ItemStack item = new ItemStack(Material.CARPET);
					item.setDurability(getLutil().getFromDey((short) is.getDurability()));
					packet.getInventory().setHelmet(item);
				}
			}
			removeItem(p);
			update();
		}else{
			if(isRunning()){return;}
			if(!isOpen()){
				open();
			}else{
				close();
			}
		}
	}
	
	private boolean isRunning(){
		if(timer!=null)return true;
		return false;
	}
	
	@EventHandler
	public void onBlockBreak(FurnitureBlockBreakEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		stopTimer();
		for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(packet.getName().equalsIgnoreCase("#ITEM#")){
				if(packet.getInventory().getItemInHand()!=null&&!packet.getInventory().getItemInHand().getType().equals(Material.AIR)){
					ItemStack is = packet.getInventory().getItemInHand();
					getWorld().dropItem(getLocation(), is);
				}
			}
		}
		getObjID().remove(e.getPlayer());
		delete();
	}
	
	@EventHandler
	public void onBlockBreak(FurnitureBlockClickEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		Player p = e.getPlayer();
		ItemStack is = p.getItemInHand();
		if(is.getType().equals(Material.BANNER)){
			e.setCancelled(true);
			if(isRunning()){return;}
			for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
				if(packet.getInventory().getHelmet()!=null&&packet.getInventory().getHelmet().getType().equals(Material.BANNER)){
					packet.getInventory().setHelmet(is);
				}else if(packet.getInventory().getHelmet()!=null&&packet.getInventory().getHelmet().getType().equals(Material.CARPET)){
					ItemStack item = new ItemStack(Material.CARPET);
					item.setDurability(getLutil().getFromDey((short) is.getDurability()));
					packet.getInventory().setHelmet(item);
				}
			}
			removeItem(p);
			update();
		}else{
			if(isRunning()){return;}
			if(!isOpen()){
				open();
			}else{
				close();
			}
		}
	}
	
	public void removeItem(Player p){
		Boolean useGameMode = FurnitureLib.getInstance().useGamemode();
		if(useGameMode&&p.getGameMode().equals(GameMode.CREATIVE)){return;}
		Integer slot = p.getInventory().getHeldItemSlot();
		ItemStack itemStack = p.getItemInHand().clone();
		itemStack.setAmount(itemStack.getAmount()-1);
		p.getInventory().setItem(slot, itemStack);
		p.updateInventory();
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		stopTimer();
		for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(packet.getName().equalsIgnoreCase("#ITEM#")){
				if(packet.getInventory().getItemInHand()!=null&&!packet.getInventory().getItemInHand().getType().equals(Material.AIR)){
					ItemStack is = packet.getInventory().getItemInHand();
					getWorld().dropItem(getLocation(), is);
				}
			}
		}
		e.remove();
		this.block.setType(Material.AIR);
		this.block = null;
		getManager().remove(getObjID());
		delete();
	}
	
	private void close(){
		timer = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), new Runnable() {
			@Override
			public void run() {
				try{
					for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
						if(packet.getName().startsWith("#ELEMENT#")){
							if(!isClose(packet)){
								Double x = packet.getPose(BodyPart.HEAD).getX();
								packet.setPose(new EulerAngle(x-.32, 0, 0), BodyPart.HEAD);
							}else{
								stopTimer();
								return;
							}
							getManager().updateFurniture(getObjID());
						}
					}
				}catch(Exception e){
					stopTimer();reset();
				}

				
			}
		}, 0, 10);
	}
	
	private void open(){
		timer = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), new Runnable() {
			@Override
			public void run() {
				try{
					for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
						if(packet.getName().startsWith("#ELEMENT#")){
							if(!isOpen(packet)){
								Double x = packet.getPose(BodyPart.HEAD).getX();
								packet.setPose(new EulerAngle(x+.32, 0, 0), BodyPart.HEAD);
							}else{
								stopTimer();
								return;
							}
							getManager().updateFurniture(getObjID());
						}
					}
				}catch(Exception e){
					stopTimer();reset();
				}

				
			}
		}, 0, 10);
	}
	
	private void reset(){
		for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(!isOpen(packet)){
				packet.setPose(new EulerAngle(-3.054, 0, 0), BodyPart.HEAD);
			}
		}
		update();
	}
	
	private boolean isClose(fArmorStand packet){
		if(packet.getPose(BodyPart.HEAD).getX()> -3.054){
			return false;
		}return true;
	}
	
	private boolean isOpen(fArmorStand packet){
		if(packet.getPose(BodyPart.HEAD).getX()< -1.85){
			return false;
		}return true;
	}
	
	private boolean isOpen(){
		for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(packet.getName().startsWith("#ELEMENT#")){
				if(packet.getPose(BodyPart.HEAD).getX()< -1.85){
					return false;
				}
			}
		}
		return true;
	}
	
	private void stopTimer(){
		if(timer!=null){
			Bukkit.getScheduler().cancelTask(timer);
			timer=null;
		}
	}
}
