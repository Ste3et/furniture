package de.Ste3et_C0st.Furniture.Main;
import org.bukkit.entity.Player;

import de.Ste3et_C0st.Furniture.Main.Type.FurnitureType;


public class Permissions {
	public static boolean check(Player p, FurnitureType type, String s){
		if(s == null){s="";}
		String typ = type.name().toLowerCase();
		typ = typ.replace("_", "");
		if(!main.getInstance().isDestroyable && s.contains("destroy")){return true;}
		if(p.hasPermission("furniture." + s + typ) || p.isOp() || p.hasPermission("furniture.player")){return true;}
		return false;
	}
}
