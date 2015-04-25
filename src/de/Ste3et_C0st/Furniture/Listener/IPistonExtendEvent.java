package de.Ste3et_C0st.Furniture.Listener;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

import de.Ste3et_C0st.Furniture.Main.main;

public class IPistonExtendEvent implements Listener {
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onPistionExtend(BlockPistonExtendEvent e){
		if(e.getBlock()==null){return;}
		for(int i = 13;i>=0;i--){
			Location l = main.getInstance().getNew(e.getBlock().getLocation(), e.getDirection(),(double)- i,(double) 0 );
			if(main.getInstance().Fmgr.isAtLocation(l.toVector(), 1D)){e.setCancelled(true);}
			if(main.getInstance().Fmgr.isAtLocation(l.getBlock().getRelative(BlockFace.UP).getLocation().toVector(), 1D)){e.setCancelled(true);}
		}
	}
}
