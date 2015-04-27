package de.Ste3et_C0st.Furniture.Main.Manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.util.bukkit.BukkitPlayerFunctions;

import de.Ste3et_C0st.Furniture.Main.main;

public class IPlotSquaredCheck {
	Boolean enabled = false;
	PluginManager pm = null;
	/*http://share.gifyoutube.com/y0VOzz.gif*/
	Plugin wg = null;
	public IPlotSquaredCheck(PluginManager pm){
		if(!main.getInstance().getConfig().getBoolean("config.Protection.PlotSquared.HookIFExist")){return;}
		main.getInstance().getLogger().info("[Furniture] Hook into PlotSquared");
		this.pm = pm;
		this.enabled = true;
		wg =  pm.getPlugin("PlotSquared");
	}
	
	public boolean check(Player p, Location l){
		if(p.isOp()){return true;}
		if(!this.enabled){return true;}
		if(this.wg == null){return true;}
		try{
			Plot plot = BukkitPlayerFunctions.getCurrentPlot(p);
			if(plot==null){return false;}
			if(plot.isAdded(p.getUniqueId()) || plot.isOwner(p.getUniqueId())){
				return true;
			}
			return false;
		}catch(Exception e){
			return true;
		}
	}
}
