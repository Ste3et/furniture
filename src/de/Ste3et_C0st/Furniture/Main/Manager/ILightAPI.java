package de.Ste3et_C0st.Furniture.Main.Manager;

import org.bukkit.plugin.PluginManager;

import de.Ste3et_C0st.Furniture.Main.main;
import ru.BeYkeRYkt.LightAPI.LightAPI;

public class ILightAPI {
	
	LightAPI lightAPI = null;
	public ILightAPI(PluginManager pm){
		main.getInstance().getLogger().info("[Furniture] Hook into LightAPI");
		this.lightAPI = (LightAPI) pm.getPlugin("LightAPI");
	}
	
	public LightAPI getLightAPI(){
		return this.lightAPI;
	}
}
