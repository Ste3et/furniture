package de.Ste3et_C0st.Furniture.Main.Manager;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import de.Ste3et_C0st.Furniture.Main.main;

public class IGriefPrevention {
	Boolean enabled = false;
	PluginManager pm = null;
	GriefPrevention wg = null;
	public IGriefPrevention(PluginManager pm){
		if(!main.getInstance().getConfig().getBoolean("config.Protection.GriefPrevention.HookIFExist")){return;}
		main.getInstance().getLogger().info("[Furniture] Hook into GriefPrevention");
		this.pm = pm;
		this.enabled = true;
		wg = (GriefPrevention) pm.getPlugin("GriefPrevention");
	}
	
	public boolean check(Player p, Location l){
		if(p.isOp()){return true;}
		if(!this.enabled){return true;}
		if(this.wg == null){return true;}
		try{
			Claim claim = GriefPrevention.instance.dataStore.getClaimAt(l, false, null);
			if(claim==null || claim.ownerID == null){return true;}
			if(claim.ownerID.equals(p.getUniqueId())){return true;}
			return false;
		}catch(Exception e){
			return true;
		}
	}
}
