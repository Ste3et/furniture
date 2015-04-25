package de.Ste3et_C0st.Furniture.Main.Manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class CheckManager {
	
	ILandLord landlord = null;
	IPlotMeCheck plotme = null;
	IPlotSquaredCheck plotsquared = null;
	IPreciousStones stones = null;
	IResidence residence = null;
	ITowny towny = null;
	
	public CheckManager(PluginManager pm) throws ClassNotFoundException{
		try {this.landlord = new ILandLord(pm);} catch (Exception e) {}
		this.plotme = new IPlotMeCheck(pm);
		try {this.stones = new IPreciousStones(pm);} catch (Exception e) {}
		try {this.stones = new IPreciousStones(pm);} catch (Exception e) {}
		try {this.residence = new IResidence(pm);} catch (Exception e) {}
		try {this.towny = new ITowny(pm);} catch (Exception e) {}
	}
	
	public boolean canBuild(Player p, Location l){
		if(p.isOp()){return true;}
		if((landlord == null || landlord.check(p, l)) && 
		   (plotme == null || plotme.check(p, l)) &&
		   (plotsquared == null || plotsquared.check(p, l)) &&
		   (stones == null || stones.check(p, l)) && 
		   (residence == null || residence.check(p, l)) &&
		   (towny == null || towny.check(p, l))){
			return true;
		}
		return false;
	}
}
