package de.Ste3et_C0st.Furniture.Main;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import de.Ste3et_C0st.Furniture.Main.Type.FurnitureType;

public class command implements CommandExecutor {
	private static config cc;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(cmd.getName().equalsIgnoreCase("furniture")){
			String noPermissions = ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.NoPermissions"));
			String notFound = ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.notFound"));
			String PlayerNotFound = ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.PlayerNotFound"));
			String help = "§7================[§2Furniture§7]================\n";
			help += "§2/furniture list | §8list all available furniture\n";
			help += "§2/furniture give <PLAYER> <FURNITURE> | §8give one player one furniture\n";
			help += "§2/furniture give <FURNITURE> | §8give you an furniture\n";
			help += "§2/furniture killall | §8Killall Furnitures\n";
			help += "§2/furniture remove radius <DISTANCE> | §8Remove furniture in radius\n";
			help += "§2/furniture remove <type> | §8Remove all furnitures by type";
			help += "§2/furniture remove <ID> | §8Remove a furniture by id";
			help += "§2/furniture reload | §8Reload the plugin\n";
			help += "§7=========================================";
			if(sender instanceof Player){
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("killall")){
						if(sender.hasPermission("furniture.admin")){
							main.getInstance().removeAll();
							Integer i = 0;
							for(World w : main.getInstance().getServer().getWorlds()){
								for(Entity e : w.getEntities()){
									if(e!=null && e instanceof ArmorStand){
										String name = e.getName();
										if(name != null && name.length()>=13){
											String[] split = name.split("-");
											if(split != null && split.length>=1){
												i++;
												e.remove();
											}
										}
										
									}
								}
							}
							cc = new config();
							main.getInstance().removeAll();
							String s = main.getInstance().getConfig().getString("config.Messages.ArmorStandsKills");
							s = s.replace("@ARMORSTANDS", i.toString()).replace("@WOLRDS", main.getInstance().getServer().getWorlds().size() + "");
							((Player) sender).sendMessage(ChatColor.translateAlternateColorCodes('&', s));
							cc.deleteFolder(new File("plugins/Furniture/objects"));
							return true;
						}else{
							((Player) sender).sendMessage(noPermissions);
							return true;
						}
					}
				}else if(args.length == 2){
					if(args[0].equalsIgnoreCase("remove")){
						if(!sender.hasPermission("furniture.admin")){sender.sendMessage(noPermissions);return true;}
						if(getType(args[1])!=null){
							Boolean b = main.getInstance().getManager().RemoveType(getType(args[1]), true);
							String s = ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.ArmorStandsRemoveFromType"));
							String[] split = s.split(" ");
							Integer i = 0;
							String string = "";
							String stings = "";
							for(String str : split){
								if(str.startsWith("[") && str.endsWith("]")){
									str = str.replace("[", "");
									str = str.replace("]", "");
									stings = str;
									if(b){
										String[] l = stings.split("#");
										string = l[1];
									}else{
										String[] l = stings.split("#");
										string = l[0];
									}
								}
								i++;
							}
							sender.sendMessage(s.replace(stings, string).replace("[", "").replace("]", ""));
							return true;
						}else if(args[1] != null && args[1].length()>=13){
							String[] split = args[1].split("-");
							if(split != null && split.length>=1){
								Boolean b = main.getInstance().getManager().RemoveFromID(args[1]);
								String s = ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.ArmorStandsRemoveFromID"));
								String[] splits = s.split(" ");
								Integer i = 0;
								String string = "";
								String stings = "";
								for(String str : splits){
									if(str.startsWith("[") && str.endsWith("]")){
										str = str.replace("[", "");
										str = str.replace("]", "");
										stings = str;
										if(b){
											String[] l = stings.split("#");
											string = l[1];
										}else{
											String[] l = stings.split("#");
											string = l[0];
										}
									}
									i++;
								}
								sender.sendMessage(s.replace(stings, string).replace("[", "").replace("]", ""));
								return true;
							}
						}else{
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.CommandBadArguments")));
							return true;
						}
					}else if(args[0].equalsIgnoreCase("give")){
						if(!sender.hasPermission("furniture.give")){sender.sendMessage(noPermissions);return true;}
						if(isExist(args[1])){
							if(!sender.hasPermission("furniture.give." + args[1].toLowerCase())){sender.sendMessage(noPermissions);return true;}
							((Player) sender).getInventory().addItem(getIS(args[1]));
							return true;
						}else{
							sender.sendMessage(notFound);
							return true;
						}
					}
				}else if(args.length == 3){
					if(args[0].equalsIgnoreCase("remove")){
						if(!sender.hasPermission("furniture.admin")){sender.sendMessage(noPermissions);return true;}
						if(args[1].equalsIgnoreCase("radius")){
							Double d = 0.0;
							if(Utils.isInt(args[2])){
								d = (double) Integer.parseInt(args[2]);
							}
							if(Utils.isDouble(args[2])){
								d = Double.parseDouble(args[2]);
							}
							if(d!=0.0){
								Integer i = main.getInstance().getManager().RemoveFromDistance(((Player) sender).getLocation().toVector(), d);
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.ArmorStandsRemoveFromDistance")).replace("@ARMORSTANDS", i.toString()));
								return true;
							}
						}else{
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.CommandBadArguments")));
							return true;
						}
					}
				}
			}
			
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("list")){
					if(sender.hasPermission("furniture.list")){
						sender.sendMessage("§6=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
						main.getInstance().getStringPage().returnStringPage(sender, main.getInstance().crafting, 0, 10);
						sender.sendMessage("§6=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
						sender.sendMessage("§2Type /furniture list <page>");
						return true;
					}else{
						sender.sendMessage(noPermissions);
						return true;
					}
				}else if(args[0].equalsIgnoreCase("reload")){
					if(sender.hasPermission("furniture.admin")){
						main.getInstance().reload();
						sender.sendMessage("§bPlugin reloaded");
						return true;
					}else{
						sender.sendMessage(noPermissions);
						return true;
					}
				}else{
					sender.sendMessage(help);
					return true;
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
			}else if(args.length == 2){
				if(args[0].equalsIgnoreCase("remove")){
					if(!sender.hasPermission("furniture.admin")){sender.sendMessage(noPermissions);return true;}
					if(getType(args[1])!=null){
						Boolean b = main.getInstance().getManager().RemoveType(getType(args[1]), true);
						String s = ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.ArmorStandsRemoveFromType"));
						String[] split = s.split(" ");
						Integer i = 0;
						String string = "";
						String stings = "";
						for(String str : split){
							if(str.startsWith("[") && str.endsWith("]")){
								stings = str;
								if(b){
									string = str.split("|")[1];
								}
								if(!b){
									string = str.split("|")[0];
								}
							}
							i++;
						}
						sender.sendMessage(s.replace(stings, string));
						return true;
					}else if(args[1] != null && args[1].length()>=13){
						String[] split = args[1].split("-");
						if(split != null && split.length>=1){
							Boolean b = main.getInstance().getManager().RemoveFromID(args[1]);
							String s = ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.ArmorStandsRemoveFromID"));
							String[] splits = s.split(" ");
							Integer i = 0;
							String string = "";
							String stings = "";
							for(String str : splits){
								if(str.startsWith("[") && str.endsWith("]")){
									stings = str;
									if(b){
										string = str.split("|")[1];
									}
									if(!b){
										string = str.split("|")[0];
									}
								}
								i++;
							}
							sender.sendMessage(s.replace(stings, string));
							return true;
						}
					}else{
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.CommandBadArguments")));
						return true;
					}
				}else if(args[0].equalsIgnoreCase("list")){
					if(sender.hasPermission("furniture.list")){
						if(Utils.isInt(args[1])){
							if(main.getInstance().getStringPage().check(sender, main.getInstance().crafting, Integer.parseInt(args[1]), 10)){
								sender.sendMessage("§6=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
								main.getInstance().getStringPage().returnStringPage(sender, main.getInstance().crafting, Integer.parseInt(args[1]), 10);
								sender.sendMessage("§6=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
								sender.sendMessage("§2Type /furniture list <page>");
								return true;
							}else{
								sender.sendMessage("§cSide not found");
								return true;
							}
						}else{
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.CommandBadArguments")));
						}
						return true;
					}else{
						sender.sendMessage(noPermissions);
						return true;
					}
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
	
	public FurnitureType getType(String s){
		if(s.equalsIgnoreCase("chair")){return FurnitureType.CHAIR;}
		if(s.equalsIgnoreCase("largetable")){return FurnitureType.LARGE_TABLE;}
		if(s.equalsIgnoreCase("lantern")){return FurnitureType.LATERN;}
		if(s.equalsIgnoreCase("sofa")){return FurnitureType.SOFA;}
		if(s.equalsIgnoreCase("table")){return FurnitureType.TABLE;}
		if(s.equalsIgnoreCase("barrels")){return FurnitureType.BARRELS;}
		if(s.equalsIgnoreCase("campfire1")){return FurnitureType.CAMPFIRE_1;}
		if(s.equalsIgnoreCase("campfire2")){return FurnitureType.CAMPFIRE_2;}
		if(s.equalsIgnoreCase("tent1")){return FurnitureType.TENT_1;}
		if(s.equalsIgnoreCase("tent2")){return FurnitureType.TENT_2;}
		if(s.equalsIgnoreCase("tent3")){return FurnitureType.TENT_3;}
		return null;
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
