package de.Ste3et_C0st.Furniture.Main.Event;

import java.util.Map.Entry;
import java.util.Objects;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import de.Ste3et_C0st.Furniture.Objects.electric.streetlamp;

public class redstoneEvent implements Listener {


	@EventHandler
	private void onBlockPowered(BlockRedstoneEvent e){
		World w = e.getBlock().getWorld();
		Block block = e.getBlock();
		Location location = block.getLocation();
		Entry<Location, streetlamp> object = streetlamp.locationSet.entrySet().stream()
				.filter(entry -> entry.getValue().getObjID().getWorldName().equalsIgnoreCase(w.getName()))
				.filter(entry -> entry.getKey().distance(location) <= 1).findFirst().orElse(null);
		if(Objects.nonNull(object)) {
			if(e.getNewCurrent()==0){
				object.getValue().setLight(false);
			}else {
				object.getValue().setLight(true);
			}
			
		}
	}

	
}
