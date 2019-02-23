package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureHelper;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class fance extends FurnitureHelper{
	List<Material> matList = Arrays.asList(
			Material.SPRUCE_FENCE,
			Material.BIRCH_FENCE,
			Material.JUNGLE_FENCE,
			Material.DARK_OAK_FENCE,
			Material.ACACIA_FENCE,
			Material.COBBLESTONE_WALL,
			Material.NETHER_BRICK_FENCE);
	
	public fance(ObjectID id){
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
			if(matList.contains(stack.getType())){
				b.setType(stack.getType());
				consumeItem(player);
				return;
			}else if(main.materialWhiteList.contains(stack.getType())){
				setTypes(stack);
				consumeItem(player);
				update();
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
	
	private void setTypes(ItemStack is){for(fEntity packet : getManager().getfArmorStandByObjectID(getObjID())){packet.getInventory().setHelmet(is);}}
}