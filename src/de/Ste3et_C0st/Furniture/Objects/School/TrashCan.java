package de.Ste3et_C0st.Furniture.Objects.School;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class TrashCan extends Furniture{

	public TrashCan(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
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
			ItemStack is = player.getInventory().getItemInMainHand();
			fEntity stand = null;
			for(fEntity s : getfAsList()){
				if(s.getName().equalsIgnoreCase("#TRASH#")){
					stand = s;break;
				}
			}
			if(stand==null){return;}
			if(is==null||is.getType()==null||is.getType().equals(Material.AIR)){
				if(stand.getItemInMainHand()!=null&&!stand.getItemInMainHand().getType().equals(Material.AIR)){
					getWorld().dropItem(getCenter(), stand.getItemInMainHand());
					stand.setItemInMainHand(new ItemStack(Material.AIR));
					update();
					return;
				}
			}
			stand.setItemInMainHand(is);
			player.getInventory().clear(player.getInventory().getHeldItemSlot());
			player.updateInventory();
			update();
			return;
		}
	}

	@Override
	public void spawn(Location loc) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		Location l = getLutil().getRelativ(getCenter(), getBlockFace(), -.29, .2).add(0, -1.1, 0);
		l.setYaw(getYaw());
		fArmorStand stand = spawnArmorStand(getCenter().add(0, -1.2, 0));
		stand.setHelmet(new ItemStack(Material.BLACK_CARPET, 1));
		stand.setSmall(true);
		asList.add(stand);
		
		float yaw = 0;
		for(int i = 0; i<4;i++){
			Location location = getLutil().getRelativ(getCenter(), getLutil().yawToFace(yaw), .43, 0);
			location.add(0,-2.45,0);
			location.setYaw(yaw);
			stand = spawnArmorStand(location);
			stand.setHelmet(new ItemStack(Material.IRON_BARS));
			stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(10, 0, 0)));
			asList.add(stand);
			yaw+=90;
		}
		
		stand = spawnArmorStand(l);
		stand.setSmall(true);
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-110,0,0)));
		stand.setMarker(false);
		stand.setName("#TRASH#");
		asList.add(stand);
		
		for(fArmorStand as : asList){
			as.setInvisible(true);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
}
