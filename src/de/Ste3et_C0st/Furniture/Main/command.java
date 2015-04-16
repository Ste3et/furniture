package de.Ste3et_C0st.Furniture.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class command implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(cmd.getName().equalsIgnoreCase("furniture")){
			String noPermissions = ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.NoPermissions"));
			String notFound = ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.notFound"));
			String PlayerNotFound = ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.PlayerNotFound"));
			String help = "§7================[§2Furniture§7]================\n";
			help += "§2/furniture list | §8list all available furniture\n";
			help += "§2/furniture give <PLAYER> <FURNITURE> | §8give one player one furniture\n";
			
			if(sender instanceof Player){
				help += "§2/furniture give <FURNITURE> | §8give you an furniture\n";
				if(args.length == 2){
					if(args[0].equalsIgnoreCase("give")){
						if(!sender.hasPermission("furniture.give")){sender.sendMessage(noPermissions);return true;}
						if(isExist(args[1])){
							if(!sender.hasPermission("furniture.give." + args[1].toLowerCase())){sender.sendMessage(noPermissions);return true;}
							((Player) sender).getInventory().addItem(getIS(args[1]));
							return true;
						}else{
							sender.sendMessage(notFound);
							return true;
						}
					}else{
						sender.sendMessage(help);
						return true;
					}
				}
			}
			help += "§7=========================================";
			
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("list")){
					if(sender.hasPermission("furniture.list")){
						sender.sendMessage("§7Furniture: ");
						for(String a : main.getInstance().crafting.keySet()){
							sender.sendMessage("§6- " + a);
						}
						return true;
					}else{
						sender.sendMessage(noPermissions);
						return true;
					}
				}else{
					sender.sendMessage(help);
				}
			}else if(args.length == 3){
				if(args[0].equalsIgnoreCase("give")){
					if(!sender.hasPermission("furniture.give")){sender.sendMessage(noPermissions);return true;}
					if(!isExist(args[2])){sender.sendMessage(notFound);return true;}
					if(!Bukkit.getPlayer(args[1]).isOnline()){sender.sendMessage(PlayerNotFound);return true;}
					Player player = Bukkit.getPlayer(args[1]);
					player.getInventory().addItem(getIS(args[2]));
					return true;
				}else{
					sender.sendMessage(help);
					return true;
				}
			}else{
				sender.sendMessage(help);
				return true;
			}
			
		}
		return false;
	}

	public boolean isExist(String s){
		for(String a : main.getInstance().crafting.keySet()){
			if(a.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
	
	public ItemStack getIS(String s ){
		for(String a : main.getInstance().crafting.keySet()){
			if(a.equalsIgnoreCase(s)){
				return main.getInstance().crafting.get(a);
			}
		}
		return null;
	}
}
