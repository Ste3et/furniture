package de.Ste3et_C0st.Furniture.Main.Manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.Ste3et_C0st.Furniture.Main.main;

public class IWolrdGuardCheck {
	Boolean enabled = false;
	PluginManager pm = null;
	WorldGuardPlugin wg = null;
	public IWolrdGuardCheck(PluginManager pm){
		if(!main.getInstance().getConfig().getBoolean("config.Protection.WorldGuard.HookIFExist")){return;}
		this.pm = pm;
		this.enabled = true;
		wg = (WorldGuardPlugin) pm.getPlugin("WorldGuard");
	}
	
	public boolean check(Player p, Location l){
		if(p.isOp()){return true;}
		if(!this.enabled){return true;}
		if(this.wg == null){return true;}
		try{
			return this.wg.canBuild(p, l);
		}catch(Exception e){
			return true;
		}
	}
}
