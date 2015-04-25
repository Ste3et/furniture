package de.Ste3et_C0st.Furniture.Main.Manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.palmergames.bukkit.towny.Towny;

public class ITowny {
	Boolean enabled = false;
	PluginManager pm = null;
	Towny wg = null;
	public ITowny(PluginManager pm){
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
