package de.Ste3et_C0st.Furniture.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Objects.garden.config;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;

public class main extends JavaPlugin{
	
	private config c;
	private FileConfiguration file;
	public static double damage = 0;
	
	public static main instance;
	static LocationUtil util;
	public static List<Material> materialWhiteList = new ArrayList<Material>();
	public static HashMap<String, Vector> catapultRange = new HashMap<String, Vector>();
	
	public void onEnable(){
		if(!Bukkit.getPluginManager().isPluginEnabled("FurnitureLib")){Bukkit.getPluginManager().disablePlugin(this);}
		instance = this;
		util = FurnitureLib.getInstance().getLocationUtil();
		if(FurnitureLib.getInstance().getDescription().getVersion().startsWith("2.")){
			FurnitureHook furniturePlugin = new FurnitureHook(getInstance());
			furniturePlugin.saveRessource("config.yml", "/fence/whiteList.yml");
			furniturePlugin.saveRessource("damage.yml", "/bearTrap/damage.yml");
			furniturePlugin.saveRessource("range.yml", "/catapult/range.yml");
			
			furniturePlugin.register();
			setDefaults();
			setDefaults_2();
		}else{
			FurnitureLib.getInstance().send("FurnitureLib Version > 2.x not found");
			FurnitureLib.getInstance().send("DiceFurniture deos not load");
		}
		
	}

	private void setDefaults_2(){
		c = new config();
		this.file = c.getConfig("damage", "plugin/bearTrap/");
		damage = this.file.getDouble("damage");
		
		c = new config();
		this.file = c.getConfig("range", "plugin/catapult/");
		for(String str : this.file.getConfigurationSection("rangeOptions").getKeys(false)){
			Vector v = new Vector(
					0, 
					this.file.getDouble("rangeOptions." + str + ".height"), 
					this.file.getDouble("rangeOptions." + str + ".width"));
			catapultRange.put(str, v);
		}
	}
	
	private void setDefaults(){
		c = new config();
		this.file = c.getConfig("whiteList", "plugin/fence/");
		List<String> intList = this.file.getStringList("MaterialData");
		for(String i : intList){
			if(Material.getMaterial(i)!=null){
				Material m = Material.getMaterial(i);
				materialWhiteList.add(m);
			}
		}
	}
	
	public static LocationUtil getLocationUtil(){return util;}

	public static Plugin getInstance() {
		return instance;
	}
}
