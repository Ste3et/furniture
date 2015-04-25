package de.Ste3et_C0st.Furniture.Main.Manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.jcdesimp.landlord.Landlord;
import com.jcdesimp.landlord.persistantData.Friend;
import com.jcdesimp.landlord.persistantData.OwnedLand;

public class ILandLord {
	Boolean enabled = false;
	PluginManager pm = null;
	Landlord wg = null;
	public ILandLord(PluginManager pm){
		if(!pm.isPluginEnabled("Landlord")){return;}
		this.pm = pm;
		this.enabled = true;
		wg = (Landlord) pm.getPlugin("Landlord");
	}
	
	public boolean check(Player p, Location l){
		if(p.isOp()){return true;}
		if(!this.enabled){return true;}
		if(this.wg == null){return true;}
		try{
			OwnedLand ow = OwnedLand.getApplicableLand(l);
			if(ow==null){return true;}
			if(ow.getOwnerUUID().equals(p.getUniqueId())){return true;}
			if(ow.getFriends().contains(Friend.friendFromPlayer(p))){return true;}
			return false;
		}catch(Exception e){
			return true;
		}
	}
}
