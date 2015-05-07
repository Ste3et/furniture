package de.Ste3et_C0st.Furniture.Model;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.Type.HeadArmType;
import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.config;

public class ModelLoad {
	private static config cc;
	private static FileConfiguration fc;
	private static String folder = "/models/";
	
	public static void load(String ID){
		cc = new config();
		fc = cc.getConfig(ID, folder);
		World w = Bukkit.getServer().getWorld(fc.getString("Model.Location.World"));
		BlockFace b = Utils.StringToFace(fc.getString("Model.Location.BlockFace"));
		
		for(String id : fc.getConfigurationSection("Model.ArmorStand").getKeys(false)){
			String s = "Model.ArmorStand." + id + ".";
			Double RelativeX = fc.getDouble(s+"x");
			Double RelativeY = fc.getDouble(s+"y");
			Double RelativeZ = fc.getDouble(s+"z");
			BlockFace armorFace = Utils.StringToFace(fc.getString(s+"face"));
			HeadArmType type = Utils.haTgetByString(fc.getString(s+"type"));
			Boolean small = fc.getBoolean(fc.getString(s+"small"));
			Boolean Visible = fc.getBoolean(s+"Visible");
			Boolean colorable = fc.getBoolean(s+"colorable");
			Boolean sitable = fc.getBoolean(s+"sitable");
			Boolean fireable = fc.getBoolean(s+"fireable");
			
			ItemStack is = fc.getItemStack(s+"Item");
			EulerAngle ea = new EulerAngle(fc.getDouble(s+"Rad.X"), fc.getDouble(s+"Rad.Y"), fc.getDouble(s+"Rad.Z"));
		}
	}
}
