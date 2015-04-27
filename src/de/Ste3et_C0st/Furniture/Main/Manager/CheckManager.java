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
	IGriefPrevention grief = null;
	IPlotz plotz = null;
	ILightAPI lightAPI = null;
	IWolrdGuardCheck world = null;
	public CheckManager(PluginManager pm){
		if(isEnable("Landlord", pm)){this.landlord = new ILandLord(pm);}
		if(isEnable("PlotMe", pm)){this.plotme = new IPlotMeCheck(pm);}
		if(isEnable("PlotSquared", pm)){this.plotsquared = new IPlotSquaredCheck(pm);}
		if(isEnable("PreciousStones", pm)){this.stones = new IPreciousStones(pm);}
		if(isEnable("Residence", pm)){this.residence = new IResidence(pm);}
		if(isEnable("Towny", pm)){this.towny = new ITowny(pm);}
		if(isEnable("LightAPI", pm)){this.lightAPI = new ILightAPI(pm);}
		if(isEnable("Plotz", pm)){this.plotz = new IPlotz(pm);}
		if(isEnable("GriefPrevention", pm)){this.grief = new IGriefPrevention(pm);}
		if(isEnable("WorldGuard", pm)){this.world = new IWolrdGuardCheck(pm);}
	}
	
	public ILightAPI getLightAPI(){
		return this.lightAPI;
	}
	
	public boolean canBuild(Player p, Location l){
		if(p.isOp()){return true;}
		if((landlord == null || landlord.check(p, l)) && 
		   (plotme == null || plotme.check(p, l)) &&
		   (plotsquared == null || plotsquared.check(p, l)) &&
		   (stones == null || stones.check(p, l)) && 
		   (residence == null || residence.check(p, l)) &&
		   (towny == null || towny.check(p, l)) &&
		   (plotz == null || plotz.check(p, l)) && 
		   (grief == null || grief.check(p, l)) &&
		   (world == null || world.check(p, l))){
			return true;
		}
		return false;
	}
	
	public boolean isEnable(String plugin, PluginManager pm){
		return pm.isPluginEnabled(plugin);
	}
}
