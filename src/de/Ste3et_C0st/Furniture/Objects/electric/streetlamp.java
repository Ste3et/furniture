package de.Ste3et_C0st.Furniture.Objects.electric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import de.Ste3et_C0st.Furniture.Main.FurnitureHook;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class streetlamp extends Furniture{
	
	private Location light, redstoneBlock;
	public static HashMap<Location, streetlamp> locationSet = new HashMap<Location, streetlamp>();
	
	public streetlamp(ObjectID id){
		super(id);
		setBlock();
		
		this.light = getLutil().getRelative(getLocation(), getBlockFace(), -1D, 0D);
		this.redstoneBlock = getCenter().getBlock().getLocation();
		if(!locationSet.containsKey(this.redstoneBlock)) locationSet.put(this.redstoneBlock, this);
		spawn(id.getStartLocation());
	}
	
	public void spawn(Location location){}
	
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
				Location loc =getLutil().getRelative(location, getBlockFace(), -1D, 0D);
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
			if(locationSet.containsKey(this.redstoneBlock)) locationSet.remove(this.redstoneBlock);
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
	
	public void setLight(Boolean b){
		if(b == false){
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
	
	public boolean isOn(){
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
