package de.Ste3et_C0st.Furniture.Main.Manager;

import java.util.List;

import net.sacredlabyrinth.Phaed.PreciousStones.FieldFlag;
import net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones;
import net.sacredlabyrinth.Phaed.PreciousStones.vectors.Field;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import de.Ste3et_C0st.Furniture.Main.main;

public class IPreciousStones {
	Boolean enabled = false;
	PluginManager pm = null;
	PreciousStones wg = null;
	public IPreciousStones(PluginManager pm){
		if(!main.getInstance().getConfig().getBoolean("config.Protection.PreciousStones.HookIFExist")){return;}
		main.getInstance().getLogger().info("[Furniture] Hook into PreciousStones");
		this.pm = pm;
		this.enabled = true;
		wg = (PreciousStones) pm.getPlugin("PreciousStones");
	}
	
	public boolean check(Player p, Location l){
		if(p.isOp()){return true;}
		if(!this.enabled){return true;}
		if(this.wg == null){return true;}
		try{
			List<Field> fields = PreciousStones.API().getFieldsProtectingArea(FieldFlag.ALL, l);
			if ((fields == null) || (fields.size() == 0)){ return true; }else{
		    	Field stones = (Field)fields.get(0);
		    	if(stones.isBuyer(p)){
		    		return true;
		    	}
		    	return false;
		    }
		}catch(Exception e){
			return true;
		}
	}
}
