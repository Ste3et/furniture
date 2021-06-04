package de.Ste3et_C0st.Furniture.Objects.outdoor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class hammock extends Furniture{

	public hammock(ObjectID id) {
		super(id);
	}
	
	@Override
	public void onClick(Player player){
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		ItemStack stack = player.getInventory().getItemInMainHand();
		
		if(stack!=null){
			if(isMaterial(stack.getType())){
				if(stack.getType().name().contains("BANNER")){
					if(canBuild(player)) {
						for(fEntity entity : getfAsList()){
							if(entity.getHelmet()!=null){
								if(entity.getHelmet().getType().name().contains("BANNER")){
									entity.setHelmet(stack.clone());
								}
							}
						}
						consumeItem(player);
						update();
						return;
					}
				}else{
					if(canBuild(player)) {
						for(fEntity entity : getfAsList()){
							if(entity.getHelmet()!=null){
								if(entity.getHelmet().getType().toString().toLowerCase().endsWith("log")){
									entity.setHelmet(stack.clone());
								}	
							}
						}
						consumeItem(player);
						update();
						return;
					}
				}
			}
		}
		
		for(fEntity entity : getfAsList()){
			if(entity.getName().startsWith("#SITZ#")){
				if(entity.getPassenger()==null||entity.getPassenger().isEmpty()){
					entity.setPassenger(player);
				}
				return;
			}
		}
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
	
	private boolean isMaterial(Material m){
		if(m==null){return false;}
		boolean b = false;
		if(m.toString().toLowerCase().endsWith("log")){
			b=true;
		}else if(m.toString().toLowerCase().endsWith("banner")){
			b=true;
		}
		
		return b;
	}

	@Override
	public void spawn(Location location) {}

}
