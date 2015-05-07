package de.Ste3et_C0st.Furniture.Model;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;

import de.Ste3et_C0st.Furniture.Main.Type.HeadArmType;
import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.config;

public class ModelSave {
	private static config cc;
	private static FileConfiguration fc;
	private static String folder = "/models/";
	
	public static void save(String ID, Modele m){
		cc = new config();
		fc = cc.getConfig(ID, folder);
		World w = m.getLocation().getWorld();
		fc.set("Model.Location.X", m.getLocation().getX());
		fc.set("Model.Location.Y", m.getLocation().getY());
		fc.set("Model.Location.Z", m.getLocation().getZ());
		fc.set("Model.Location.World", m.getLocation().getWorld().getName());
		fc.set("Model.Location.BlockFace", Utils.yawToFace(m.getLocation().getYaw()).name());
		int i = 0;
		for(String id : m.getList()){
			Integer j = m.getIndex(id);
			HeadArmType type = m.getType(j);
			ArmorStand as = m.getArmorStand(j, w);
			String s = "Model.ArmorStand." + i + ".";
			fc.set(s+"face", m.getArmorStandFace(j).name());
			fc.set(s+"type", type.name());
			fc.set(s+"small", as.isSmall());
			fc.set(s+"Visible", as.isVisible());
			fc.set(s+"colorable", m.getColorable(j));
			fc.set(s+"sitable", m.getSitable(j));
			fc.set(s+"fireable", m.getFireable(j));
			if(type.equals(HeadArmType.ARM)){
				fc.set(s + "Item", as.getItemInHand());
				fc.set(s+"Rad.X", as.getRightArmPose().getX());
				fc.set(s+"Rad.Y", as.getRightArmPose().getX());
				fc.set(s+"Rad.Z", as.getRightArmPose().getX());
			}else{
				fc.set(s + "Item", as.getHelmet());
				fc.set(s+"Rad.X", as.getHeadPose().getX());
				fc.set(s+"Rad.Y", as.getHeadPose().getX());
				fc.set(s+"Rad.Z", as.getHeadPose().getX());
			}
			i++;
		}
		cc.saveConfig(ID, fc, folder);
	}
}
