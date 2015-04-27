package de.Ste3et_C0st.Furniture.Main.Manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.worldcretornica.plotme_core.Plot;
import com.worldcretornica.plotme_core.PlotMeCoreManager;
import com.worldcretornica.plotme_core.api.ILocation;
import com.worldcretornica.plotme_core.api.IWorld;
import com.worldcretornica.plotme_core.bukkit.api.BukkitLocation;
import com.worldcretornica.plotme_core.bukkit.api.BukkitWorld;

import de.Ste3et_C0st.Furniture.Main.main;

public class IPlotMeCheck {
	Boolean enabled = false;
	PluginManager pm = null;
	PlotMeCoreManager wg = null;
	public IPlotMeCheck(PluginManager pm){
		if(!main.getInstance().getConfig().getBoolean("config.Protection.PlotME.HookIFExist")){return;}
		main.getInstance().getLogger().info("[Furniture] Hook into PlotMe");
		this.pm = pm;
		this.enabled = true;
		wg = PlotMeCoreManager.getInstance();
	}
	
	public boolean check(Player p, Location l){
		if(p.isOp()){return true;}
		if(!this.enabled){return true;}
		if(this.wg == null){return true;}
		try{
			IWorld iworld = new BukkitWorld(p.getWorld());
			if(!wg.isPlotWorld(iworld)){return true;}
			ILocation plotLoc = new BukkitLocation(l);
		    String id = wg.getPlotId(plotLoc);
		    if (id.isEmpty()){return false;}
		    Plot plot = wg.getPlotById(id, iworld);
		    if(plot == null){return false;}
		    if(plot.isAllowed(p.getUniqueId())){return true;}else{return false;}
		}catch(Exception e){
			return true;
		}
	}
}
