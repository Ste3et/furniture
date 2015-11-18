package de.Ste3et_C0st.Furniture.Objects.christmas;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class ChristmasTree extends Furniture implements Listener{

	public ChristmasTree(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, getPlugin());
			return;
		}
		spawn(id.getStartLocation());
	}

	@Override
	public void onFurnitureBreak(FurnitureBreakEvent arg0) {
		
	}

	@Override
	public void onFurnitureClick(FurnitureClickEvent arg0) {
		
	}

	@Override
	public void spawn(Location arg0) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		
		fArmorStand stand = spawnArmorStand(getCenter());
		stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(90f,180f,90f)));
		stand.setHelmet(new ItemStack(Material.GOLD_BLOCK));
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
