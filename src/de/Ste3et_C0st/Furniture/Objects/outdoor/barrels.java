package de.Ste3et_C0st.Furniture.Objects.outdoor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class barrels extends Furniture{
	Integer id;
	Block block;
	
	public barrels(ObjectID id){
		super(id);
	}
	
	@Override
	public void onClick(Player player){
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			if(!player.getInventory().getItemInMainHand().getType().isBlock()&&!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)){return;}
			fEntity packet = getManager().getfArmorStandByObjectID(getObjID()).get(0);
			if(packet.getInventory().getHelmet()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
				ItemStack is = packet.getInventory().getHelmet();
				is.setAmount(1);
				getWorld().dropItem(getLocation(), is);
			}
			packet.getInventory().setHelmet(player.getInventory().getItemInMainHand());
			update();
			consumeItem(player);
		}
	}
	
	@Override
	public void onBreak(Player player) {
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			fEntity packet = getManager().getfArmorStandByObjectID(getObjID()).get(0);
			if(packet.getInventory().getHelmet()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
				ItemStack is = packet.getInventory().getHelmet();
				is.setAmount(1);
				getWorld().dropItem(getLocation(), is);
			}
			this.destroy(player);
		}
	}

	@Override
	public void spawn(Location location) {}
}