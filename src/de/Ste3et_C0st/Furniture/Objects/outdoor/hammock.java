package de.Ste3et_C0st.Furniture.Objects.outdoor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.ShematicLoader.Events.ProjectClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureHelper;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class hammock extends FurnitureHelper implements Listener{

	public hammock(ObjectID id) {
		super(id);
		Bukkit.getPluginManager().registerEvents(this, main.getInstance());
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onClick(ProjectClickEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(!e.getID().equals(getObjID())){return;}
		Player p = e.getPlayer();
		ItemStack stack = p.getInventory().getItemInMainHand();
		if(stack!=null){
			if(isMaterial(stack.getType())){
				if(stack.getType().equals(Material.BANNER)){
					for(fEntity entity : getfAsList()){
						if(entity.getHelmet()!=null){
							if(entity.getHelmet().getType().equals(Material.BANNER)){
								entity.setHelmet(stack.clone());
							}
						}
					}
					e.setCancelled(true);
					consumeItem(p);
					update();
				}else{
					for(fEntity entity : getfAsList()){
						if(entity.getHelmet()!=null){
							if(entity.getHelmet().getType().toString().toLowerCase().startsWith("log")){
								entity.setHelmet(stack.clone());
							}	
						}
					}
					e.setCancelled(true);
					consumeItem(p);
					update();
				}
			}
		}
	}
	
	private boolean isMaterial(Material m){
		if(m==null){return false;}
		boolean b = false;
		if(m.toString().toLowerCase().startsWith("log")){
			b=true;
		}else if(m.toString().toLowerCase().equalsIgnoreCase("banner")){
			b=true;
		}
		
		return b;
	}

}
