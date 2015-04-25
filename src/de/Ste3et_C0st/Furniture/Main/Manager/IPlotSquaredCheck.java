package de.Ste3et_C0st.Furniture.Main.Manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.intellectualcrafters.plot.PlotSquared;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.util.bukkit.BukkitPlayerFunctions;

public class IPlotSquaredCheck {
	Boolean enabled = false;
	PluginManager pm = null;
	PlotSquared wg = null;
	public IPlotSquaredCheck(PluginManager pm){
		if(!pm.isPluginEnabled("PlotSquared")){return;}
		this.pm = pm;
		this.enabled = true;
		wg = (PlotSquared) pm.getPlugin("PlotSquared");
	}
	
	public boolean check(Player p, Location l){
		if(p.isOp()){return true;}
		if(!this.enabled){return true;}
		if(this.wg == null){return true;}
		try{
			Plot plot = BukkitPlayerFunctions.getCurrentPlot(p);
			if(plot.isAdded(p.getUniqueId()) || plot.isOwner(p.getUniqueId())){
				return true;
			}
			return false;
		}catch(Exception e){
			return true;
		}
	}
}
