package de.Ste3et_C0st.Furniture.Main.Manager;

import me.kyle.plotz.api.Plotz;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import de.Ste3et_C0st.Furniture.Main.main;

public class IPlotz {
	Boolean enabled = false;
	PluginManager pm = null;
	Plotz wg = null;
	public IPlotz(PluginManager pm){
		if(!main.getInstance().getConfig().getBoolean("config.Protection.Plotz.HookIFExist")){return;}
		main.getInstance().getLogger().info("[Furniture] Hook into Plotz");
		this.pm = pm;
		this.enabled = true;
		wg = (Plotz) pm.getPlugin("PLotz");
	}
	
	@SuppressWarnings("static-access")
	public boolean check(Player p, Location l){
		if(p.isOp()){return true;}
		if(!this.enabled){return true;}
		if(this.wg == null){return true;}
		try{
			if(wg.getPlotByLocation(l)==null){return false;}
			if(wg.getPlotByLocation(l).isOwner(p)){return true;}
			if(wg.getPlotByLocation(l).isAllowed(p)){return true;}
			return false;
		}catch(Exception e){
			return true;
		}
	}
}
