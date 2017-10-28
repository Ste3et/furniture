package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.ShematicLoader.Events.ProjectClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureHelper;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class fance extends FurnitureHelper implements Listener{
	List<Material> matList = Arrays.asList(
			Material.SPRUCE_FENCE,
			Material.BIRCH_FENCE,
			Material.JUNGLE_FENCE,
			Material.DARK_OAK_FENCE,
			Material.ACACIA_FENCE,
			Material.COBBLE_WALL,
			Material.NETHER_FENCE);
	
	public fance(ObjectID id){
		super(id);
		Bukkit.getPluginManager().registerEvents(this, main.instance);
	}
	
	@EventHandler
	public void onClick(ProjectClickEvent e){
		if(!e.getID().equals(getObjID())){return;}
		Block b = getWorld().getBlockAt(getObjID().getBlockList().toArray(new Location[getObjID().getBlockList().size()])[0]);
		ItemStack stack = e.getPlayer().getInventory().getItemInMainHand();
		if(stack==null) return;
		if(stack.getType().equals(Material.AIR)) return;
		if(matList.contains(stack.getType())){
			b.setType(stack.getType());
			consumeItem(e.getPlayer());
			return;
		}else if(main.materialWhiteList.contains(stack.getType())){
			setTypes(stack);
			consumeItem(e.getPlayer());
			update();
			return;
		}
	}
	
	private void setTypes(ItemStack is){for(fEntity packet : getManager().getfArmorStandByObjectID(getObjID())){packet.getInventory().setHelmet(is);}}
}