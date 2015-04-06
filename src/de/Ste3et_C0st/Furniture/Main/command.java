package de.Ste3et_C0st.Furniture.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class command implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,String[] arg3) {
		if(arg0 instanceof Player == false){return true;}
		Player p = (Player) arg0;
		if(arg1.getName().equalsIgnoreCase("Furniture")){
			p.getInventory().addItem(main.getInstance().itemse.Sofa);
			p.getInventory().addItem(main.getInstance().itemse.Laterne);
			p.getInventory().addItem(main.getInstance().itemse.stuhl);
			p.getInventory().addItem(main.getInstance().itemse.tisch);
			p.getInventory().addItem(main.getInstance().itemse.tisch2);
		}
		return false;
	}

	
}
