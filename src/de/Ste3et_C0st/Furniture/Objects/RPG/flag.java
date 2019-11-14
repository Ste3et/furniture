package de.Ste3et_C0st.Furniture.Objects.RPG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class flag extends Furniture {

	public flag(ObjectID id){
		super(id);
		setBlock();
		if(isFinish()){
			setState(3, getStand());
			return;
		}
		spawn(id.getStartLocation());
	}
	
	private void setBlock(){
		
	}

	@Override
	public void spawn(Location location) {
		
	}
	
	public int getState(){
		for(fEntity stand : getfAsList()){
			if(stand.getName().startsWith("#FLAG")){
				return Integer.parseInt(stand.getName().split(":")[1]);
			}
		}
		return 1;
	}
	
	public fEntity getStand(){
		for(fEntity stand : getfAsList()){
			if(stand.getName().startsWith("#FLAG")){
				return stand;
			}
		}
		return null;
	}
	
	public void setState(int i, fEntity stand){
		if(i<1||i>3){return;}
		if(stand==null){return;}
		switch (i) {
		case 3:
			Location loc = getRelative(getCenter().add(0, 0.7, 0), getBlockFace(), -.35,-.28);
			loc.setYaw(getYaw()+90);
			stand.teleport(loc);
			stand.setName("#FLAG:" + i);
			break;
		case 2:
			loc = getRelative(getCenter().add(0, -.2, 0), getBlockFace(), -.35,-.28);
			loc.setYaw(getYaw()+90);
			stand.teleport(loc);
			stand.setName("#FLAG:" + i);
			break;
		case 1:
			loc = getRelative(getCenter().add(0, -.9, 0), getBlockFace(), -.35,-.28);
			loc.setYaw(getYaw()+90);
			stand.teleport(loc);
			stand.setName("#FLAG:" + i);
			break;
		}
		update();
	}

	@Override
	public void onBreak(Player player) {
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			this.destroy(player);
		}
	}

	@Override
	public void onClick(Player player){
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			if(player.getInventory().getItemInMainHand()!=null&&player.getInventory().getItemInMainHand().getType()!=null){
				if(player.getInventory().getItemInMainHand().getType().name().contains("BANNER")){
					getStand().setHelmet(player.getInventory().getItemInMainHand());update();
					if(player.getGameMode().equals(GameMode.CREATIVE) && getLib().useGamemode()) return;
					Integer i = player.getInventory().getHeldItemSlot();
					ItemStack is = player.getInventory().getItemInMainHand();
					is.setAmount(is.getAmount()-1);
					player.getInventory().setItem(i, is);
					player.updateInventory();
					return;
				}
			}
			
			int state = getState();
			switch (state) {
			case 3:state=2;break;
			case 2:state=1;break;
			case 1:state=3;break;
			}
			setState(state, getStand());
		}
	}
}
