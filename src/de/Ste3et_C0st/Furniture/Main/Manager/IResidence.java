package de.Ste3et_C0st.Furniture.Main.Manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public class IResidence {
	Boolean enabled = false;
	PluginManager pm = null;
	Residence wg = null;
	public IResidence(PluginManager pm){
		if(!pm.isPluginEnabled("Residence")){return;}
		this.pm = pm;
		this.enabled = true;
		wg = (Residence) pm.getPlugin("Residence");
	}
	
	public boolean check(Player p, Location l){
		if(p.isOp()){return true;}
		if(!this.enabled){return true;}
		if(this.wg == null){return true;}
		try{
			ClaimedResidence residence = Residence.getResidenceManager().getByLoc(l);
			if(residence==null||residence.getOwner().equalsIgnoreCase(p.getName())||residence.getOwner().equalsIgnoreCase(p.getUniqueId().toString())){
				return true;
			}
			return false;
			}catch(Exception e){
			return true;
		}
	}
}
