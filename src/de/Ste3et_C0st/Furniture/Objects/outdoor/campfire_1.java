package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class campfire_1 extends Furniture{

	public campfire_1(ObjectID id){
		super(id);
		if(isFinish()){return;}
		spawn(id.getStartLocation());
	}
	
	@Override
	public void onBreak(Player player) {
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			for(fEntity packet : getManager().getfArmorStandByObjectID(getObjID())){
				packet.setFire(false);
				Location location = getLocation().clone();
				location.add(0, 1.2, 0);
				getLib().getLightManager().removeLight(location);
			}
			this.destroy(player);
		}
	}
	
	@Override
	public void onClick(Player player){
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			List<fEntity> aspList = getManager().getfArmorStandByObjectID(getObjID());
			ItemStack is = player.getInventory().getItemInMainHand();
			if(is.getType().equals(Material.WATER_BUCKET)){
				for(fEntity packet : aspList){
					packet.setFire(false);
					Location location = getLocation().clone();
					location.add(0, 1.2, 0);
					getLib().getLightManager().removeLight(location);
				}
				getManager().updateFurniture(getObjID());
			}else if(is.getType().equals(Material.FLINT_AND_STEEL)){
				for(fEntity packet : aspList){
					packet.setFire(true);
					Location location = getLocation().clone();
					location.add(0, 1.2, 0);
					getLib().getLightManager().addLight(location,15);
				}
				getManager().updateFurniture(getObjID());
			}
		}
	}
	
	
	public void spawn(Location loc){
		for(int i = 0;i<=3;i++){
			Location location = getLutil().getCenter(loc);
			location.add(0,-1.9,0);
			location.setYaw(i*60);
			getManager().createArmorStand(getObjID(), location);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
}
