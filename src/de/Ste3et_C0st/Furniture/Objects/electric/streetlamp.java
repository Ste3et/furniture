package de.Ste3et_C0st.Furniture.Objects.electric;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.FurnitureHook;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class streetlamp extends Furniture implements Listener{
	
	Location light;
	Vector loc2, loc3;
	boolean redstone = false;

	public streetlamp(ObjectID id){
		super(id);
		setBlock();
		this.loc2 = id.getStartLocation().toVector();
		this.loc3 = id.getStartLocation().getBlock().getRelative(BlockFace.DOWN).getLocation().toVector();
		this.light = getLutil().getRelativ(getLocation(), getBlockFace(), -1D, 0D);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}
	
	public void spawn(Location location){
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	private void setBlock(){
		List<Block> blockLocation = new ArrayList<Block>();
		Location location = getLocation().getBlock().getLocation();
		location.setY(location.getY()-1);
		for(int i = 0; i<=3;i++){
			location.setY(location.getY()+1);
			Block block = location.getBlock();
			block.setType(Material.BARRIER);
			blockLocation.add(block);
			if(i==3){
				Location loc =getLutil().getRelativ(location, getBlockFace(), -1D, 0D);
				Block blocks = loc.getBlock();
				blocks.setType(Material.BARRIER);
				blockLocation.add(blocks);
			}
		}
		getObjID().addBlock(blockLocation);
	}
	
	@Override
	public void onBreak(Player player) {
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			FurnitureLib.getInstance().getLightManager().removeLight(light);
			this.destroy(player);
		}
	}
	
	@Override
	public void onClick(Player player){
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(!canBuild(player)) {return;}
		if(isOn()){
			setLight(false);
		}else{
			setLight(true);
		}
	}

	@EventHandler
	private void onBlockPowered(BlockRedstoneEvent e){
		if(getObjID()==null){return;} 
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.getBlock()==null){return;}
		Vector loc = e.getBlock().getLocation().toVector();
		if(loc2.distance(loc)<=1){
			if(e.getNewCurrent()==0){
				setLight(false);
				redstone = false;
			}else{
				setLight(true);
				redstone = true;
			}
			return;
		}
		if(loc3.distance(loc)<=1){
			if(e.getNewCurrent()==0){
				setLight(false);
				redstone = false;
			}else{
				setLight(true);
				redstone = true;
			}
			return;
		}
	}
	
	private void setLight(Boolean b){
		if(!b){
			fEntity packet = getPacket();
			if(packet==null) return;
			packet.getInventory().setHelmet(new ItemStack(Material.valueOf(FurnitureHook.isNewVersion() ? "REDSTONE_TORCH" : "REDSTONE_LAMP_OFF")));
			getManager().updateFurniture(getObjID());
			FurnitureLib.getInstance().getLightManager().removeLight(light);
			return;
		}else{
			fEntity packet = getPacket();
			if(packet==null) return;
			packet.getInventory().setHelmet(new ItemStack(Material.GLOWSTONE));
			getManager().updateFurniture(getObjID());
			FurnitureLib.getInstance().getLightManager().addLight(light, 15);
			return;
		}
	}
	
	private fEntity getPacket(){
		for(fEntity packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(packet.getName().equalsIgnoreCase("#LAMP#")){
				return packet;
			}
		}
		return null;
	}
	
	private boolean isOn(){
		for(fEntity as : getManager().getfArmorStandByObjectID(getObjID())){
			if(as.getName().equalsIgnoreCase("#LAMP#")){
				switch (as.getInventory().getHelmet().getType()) {
				case GLOWSTONE: return true;
				default: return false;
				}
			}
		}
		return false;
	}
}
