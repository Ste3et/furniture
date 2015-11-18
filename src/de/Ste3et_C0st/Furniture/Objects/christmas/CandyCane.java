package de.Ste3et_C0st.Furniture.Objects.christmas;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class CandyCane extends Furniture implements Listener {

	public CandyCane(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, getPlugin());
			return;
		}
		spawn(id.getStartLocation());
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!canBuild(e.getPlayer())){return;}
		e.remove();
		delete();
	}

	@Override
	public void onFurnitureClick(FurnitureClickEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spawn(Location arg0) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		
		ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta im = (SkullMeta) is.getItemMeta();
		im.setOwner("CookieMaker2000");
		is.setItemMeta(im);
		
		double d = .0;
		double c = .0;
		for(int i = 1; i<7;i++){
			d = -2.6+ .59*i;
			Location loc = getCenter().clone().add(0,d, 0);
			fArmorStand stand = spawnArmorStand(loc);
			stand.setHelmet(is);
			asList.add(stand);
		}
		d = d + 3.5;
		for(int i = 1; i<4;i++){
			c = -.32+.59*i;
			Location loc = getRelative(getCenter(), c, 0).add(0,-2.9+d, 0);
			fArmorStand stand = spawnArmorStand(loc);
			stand.setHelmet(is);
			asList.add(stand);
		}
		
		Location loc = getRelative(getCenter(), c+.3, 0).add(0,-3.5+d, 0);
		fArmorStand stand = spawnArmorStand(loc);
		stand.setHelmet(is);
		asList.add(stand);
		
		for(fArmorStand pack : asList){
			pack.setInvisible(true);
			pack.setGravity(false);
			pack.setBasePlate(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
}
