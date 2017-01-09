package de.Ste3et_C0st.Furniture.Objects.outdoor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.ShematicLoader.Events.ProjectBreakEvent;
import de.Ste3et_C0st.FurnitureLib.ShematicLoader.Events.ProjectClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureHelper;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class barrels extends FurnitureHelper implements Listener {
	Integer id;
	Block block;
	
	public barrels(ObjectID id){
		super(id);
		Bukkit.getPluginManager().registerEvents(this, main.instance);
	}
	
	@EventHandler
	public void onFurnitureClick(ProjectClickEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		Player p = e.getPlayer();
		if(!p.getInventory().getItemInMainHand().getType().isBlock()&&!p.getInventory().getItemInMainHand().getType().equals(Material.AIR)){return;}
		fEntity packet = getManager().getfArmorStandByObjectID(getObjID()).get(0);
		if(packet.getInventory().getHelmet()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
			ItemStack is = packet.getInventory().getHelmet();
			is.setAmount(1);
			getWorld().dropItem(getLocation(), is);
		}
		packet.getInventory().setHelmet(p.getInventory().getItemInMainHand());
		update();
		consumeItem(e.getPlayer());
	}
	
	@EventHandler
	private void onBlockBreak(ProjectBreakEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		fEntity packet = getManager().getfArmorStandByObjectID(getObjID()).get(0);
		if(packet.getInventory().getHelmet()!=null&&!packet.getInventory().getHelmet().getType().equals(Material.AIR)){
			ItemStack is = packet.getInventory().getHelmet();
			is.setAmount(1);
			getWorld().dropItem(getLocation(), is);
		}
	}
}