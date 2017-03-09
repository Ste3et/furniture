package de.Ste3et_C0st.Furniture.Objects.outdoor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.ShematicLoader.Events.ProjectClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureHelper;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.ColorType;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;

public class tent_1 extends FurnitureHelper implements Listener{

	public tent_1(ObjectID id){
		super(id);
		Bukkit.getPluginManager().registerEvents(this, main.getInstance());
	}
	
	@EventHandler
	public void onFurnitureClick(ProjectClickEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		final Player p = e.getPlayer();
		if(!p.getInventory().getItemInMainHand().getType().equals(Material.INK_SACK)){
			p.openWorkbench(null, true);
		}else{
			getLib().getColorManager().color(p, e.canBuild(), Material.CARPET, getObjID(), ColorType.BLOCK, 1);
		}
	}
}
