package de.Ste3et_C0st.Furniture.Main.Manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.palmergames.bukkit.towny.Towny;

import de.Ste3et_C0st.Furniture.Main.main;

public class ITowny {
	Boolean enabled = false;
	PluginManager pm = null;
	Towny wg = null;
	public ITowny(PluginManager pm){
		if(!main.getInstance().getConfig().getBoolean("config.Protection.Towny.HookIFExist")){return;}
		if(!pm.isPluginEnabled("Towny")){return;}
		this.pm = pm;
		this.enabled = true;
		wg = (Towny) pm.getPlugin("Towny");
	}
	
	public boolean check(Player p, Location l){
		if(p.isOp()){return true;}
		if(!this.enabled){return true;}
		if(this.wg == null){return true;}
		try{
			if (ITownyPermission.has(p, ITownyPermission.PROTECTION_BYPASS)) {
			      return true;
			}
			
			if(main.getInstance().getConfig().getBoolean("config.Towny.OnlyOwner")){
				return TownyUtils.isPlotOwner(p, new Location[] { l });
			}else{
				if(TownyUtils.isPlotOwner(p, new Location[] { l }) ||
				   TownyUtils.isResident(p, new Location[] { l })){
					return true;
				}
			}
			return false;
			}catch(Exception e){
			return true;
		}
	}
}
