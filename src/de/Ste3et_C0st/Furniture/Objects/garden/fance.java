package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBlockBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBlockClickEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class fance extends Furniture implements Listener{
	
	private void setTypes(ItemStack is){for(fEntity packet : getManager().getfArmorStandByObjectID(getObjID())){packet.getInventory().setHelmet(is);}}
	List<Material> matList = Arrays.asList(
			Material.SPRUCE_FENCE,
			Material.BIRCH_FENCE,
			Material.JUNGLE_FENCE,
			Material.DARK_OAK_FENCE,
			Material.ACACIA_FENCE,
			Material.COBBLE_WALL,
			Material.NETHER_FENCE);
	Block block;
	Material m;
	
	public fance(ObjectID id){
		super(id);
		if(isFinish()){
			setBlock();
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
		setBlock();
	}
	
	public void spawn(Location location){
		this.m = Material.STONE;
		Location locat = getLocation().clone();
		locat=getLutil().getCenter(locat);
		locat.add(0, -1.2, 0);
		locat.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
		for(int i = 0; i<=2;i++){
			Location loc = locat.clone();
			fArmorStand packet = getManager().createArmorStand(getObjID(), loc);
			packet.getInventory().setHelmet(new ItemStack(m,0,(short) 0));
			packet.setInvisible(true);
			packet.setBasePlate(false);
			packet.setSmall(true);
			locat.add(0, .43, 0);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	
	@EventHandler
	private void onBlockBreak(FurnitureBlockBreakEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.remove();
		delete();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onBlockBreak(FurnitureBlockClickEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		Player p = e.getPlayer();
		ItemStack is = p.getItemInHand();
		if(is==null||!is.getType().isBlock()||is.getType().equals(Material.AIR)) return;
		ItemStack itemStack = is.clone();
		if(matList.contains(itemStack.getType())){
			this.block.setType(itemStack.getType()); 
			this.block.setData((byte) itemStack.getDurability());
			remove(p, is);
			return;
		}else if(main.materialWhiteList.contains(itemStack.getType())){
			setTypes(itemStack);
			remove(p, is);
			getManager().updateFurniture(getObjID());
			return;
		}
	}
	
	
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.remove();
		delete();
	}
	
	private void remove(Player p, ItemStack is){
		if(!p.getGameMode().equals(GameMode.CREATIVE)){
			Integer i = p.getInventory().getHeldItemSlot();
			is.setAmount(is.getAmount()-1);
			p.getInventory().setItem(i, is);
			p.updateInventory();
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(this.block==null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		Player p = e.getPlayer();
		ItemStack is = p.getItemInHand();
		if(is==null || !is.getType().isBlock()||is.getType().equals(Material.AIR)) return;
		ItemStack itemStack = is.clone();
		if(matList.contains(itemStack.getType())){
			this.block.setType(itemStack.getType()); 
			this.block.setData((byte) itemStack.getDurability());
			remove(p, is);
			return;
		}else if(main.materialWhiteList.contains(itemStack.getType())){
			setTypes(itemStack);
			remove(p, is);
			getManager().updateFurniture(getObjID());
			return;
		}
	}
	
	private void setBlock(){
		Location location = getLocation().clone();
		this.block = location.getBlock();
		if(this.block.getType()==null||this.block.getType().equals(Material.AIR)||!this.block.getType().equals(Material.FENCE)){
			if(!this.matList.contains(this.block.getType())){
				this.block.setType(Material.FENCE);
			}
		}
		getObjID().addBlock(Arrays.asList(this.block));
	}
}
