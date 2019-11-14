package de.Ste3et_C0st.Furniture.Objects.School;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class TrashCan extends Furniture{

	public TrashCan(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
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
	}
}
