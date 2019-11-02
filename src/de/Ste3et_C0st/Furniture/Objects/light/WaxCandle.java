package de.Ste3et_C0st.Furniture.Objects.light;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;

public class WaxCandle extends Furniture{
	
	public WaxCandle(ObjectID id){
		super(id);
	}
	
	@Override
	public void onClick(Player player){
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			Block b = getWorld().getBlockAt(getObjID().getBlockList().toArray(new Location[getObjID().getBlockList().size()])[0]);
			ItemStack stack = player.getInventory().getItemInMainHand();
			if(stack==null) return;
			if(stack.getType().equals(Material.AIR)) return;
			if(b!=null){
				if(b.getType().equals(Material.TORCH) && stack.getType().equals(Material.WATER_BUCKET)){
					b.setType(Material.REDSTONE_TORCH);
				}else if(b.getType().equals(Material.REDSTONE_TORCH) && stack.getType().equals(Material.FLINT_AND_STEEL)){
					b.setType(Material.TORCH);
				}
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

	@Override
	public void spawn(Location location) {}
}