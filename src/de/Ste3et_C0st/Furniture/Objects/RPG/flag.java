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
		Block b = getLocation().getBlock();
		if(b.getType()==null||!b.getType().equals(Material.STONE_SLAB)){
			b.setType(Material.STONE_SLAB);
		}
		getObjID().addBlock(Arrays.asList(b));
	}

	@Override
	public void spawn(Location location) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		Location center = getLutil().getCenter(location).clone();
		center.add(0, -0.45, 0);
		for(int i = 0; i<=2;i++){
			Location loc = getLutil().getRelativ(center.clone(), getBlockFace(), .47, .38).add(0, .88*i, 0);
			loc.setYaw(getYaw());
			fArmorStand packet = spawnArmorStand(loc);
			packet.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
			packet.setPose(new EulerAngle(1.39, 0, 0), BodyPart.RIGHT_ARM);
			asList.add(packet);
		}
		
		fArmorStand as = spawnArmorStand(getCenter().add(0, 1.9, 0));
		as.setHelmet(new ItemStack(Material.STONE_SLAB));
		as.setSmall(true);
		asList.add(as);
		
		Location loc = getRelative(getCenter().add(0, 0.7, 0), getBlockFace(), -.35,-.28);
		loc.setYaw(getYaw()+90);
		as = spawnArmorStand(loc);
		as.setHelmet(new ItemStack(Material.GREEN_BANNER));
		as.setHeadPose(getLutil().degresstoRad(new EulerAngle(0, 0, 90)));
		as.setName("#FLAG:3");
		asList.add(as);
		
		for(fArmorStand packet : asList){
			packet.setInvisible(true);
			packet.setBasePlate(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
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
				if(player.getInventory().getItemInMainHand().getType().name().contains("_BANNER")){
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
