package de.Ste3et_C0st.Furniture.Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.Ste3et_C0st.DiceEaster.config;
import de.Ste3et_C0st.Furniture.Objects.laterne;
import de.Ste3et_C0st.Furniture.Objects.stuhl;
import de.Ste3et_C0st.Furniture.Objects.tisch;

public class Manager {
	private static config cc;
	private static FileConfiguration fc;
	private String folder = "/furniture/";
	
	public void loadStuhl(){
		cc = new config();
		if(cc.ExistConfig(folder, "chair.yml")){
			fc = cc.getConfig("chair.yml", folder);
			if(fc.isSet("Furniture.chair")){
				for(String s : fc.getConfigurationSection("Furniture.chair").getKeys(false)){
					String path = "Furniture.chair";
					path+= "." + s;
					
					Double x = fc.getDouble(path+".Location.x");
					Double y = fc.getDouble(path+".Location.x");
					Double z = fc.getDouble(path+".Location.x");
					World w = Bukkit.getWorld(fc.getString(path+".Location.w"));
					Float yaw = (float) fc.getInt(fc.getString(path+".Location.Yaw"));
					
					Location l = new Location(w, x, y, z);
					l.setYaw(yaw);
					new stuhl(l, main.getInstance());
				}
			}
		}
	}
	
	public void loadLatern(){
		cc = new config();
		if(cc.ExistConfig(folder, "latern.yml")){
			fc = cc.getConfig("latern.yml", folder);
			if(fc.isSet("Furniture.latern")){
				for(String s : fc.getConfigurationSection("Furniture.latern").getKeys(false)){
					String path = "Furniture.latern";
					path+= "." + s;
					
					Double x = fc.getDouble(path+".Location.x");
					Double y = fc.getDouble(path+".Location.x");
					Double z = fc.getDouble(path+".Location.x");
					World w = Bukkit.getWorld(fc.getString(path+".Location.w"));
					Float yaw = (float) fc.getInt(fc.getString(path+".Location.Yaw"));
					Boolean b = fc.getBoolean(path+".settings.Light");
					Location l = new Location(w, x, y, z);
					l.setYaw(yaw);
					new laterne(l, main.getInstance(), b);
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void loadTisch(){
		cc = new config();
		if(cc.ExistConfig(folder, "table.yml")){
			fc = cc.getConfig("table.yml", folder);
			if(fc.isSet("Furniture.table")){
				for(String s : fc.getConfigurationSection("Furniture.table").getKeys(false)){
					String path = "Furniture.table";
					path+= "." + s;
					
					Double x = fc.getDouble(path+".Location.x");
					Double y = fc.getDouble(path+".Location.x");
					Double z = fc.getDouble(path+".Location.x");
					World w = Bukkit.getWorld(fc.getString(path+".Location.w"));
					Float yaw = (float) fc.getInt(fc.getString(path+".Location.Yaw"));
					Location l = new Location(w, x, y, z);
					l.setYaw(yaw);
					ItemStack is = null;
					if(fc.isSet(path+".settings.ItemStack")){
						Material m = Material.getMaterial(fc.getInt(path+".settings.ItemStack.Material"));
						Short subID = (short) fc.getInt(path+".settings.ItemStack.Durability");
						Integer amount = fc.getInt(path+".settings.ItemStack.Amount");
						is = new ItemStack(m,amount,subID);
						ItemMeta im = is.getItemMeta();
						
						List<String> lore = new ArrayList<String>();
						if(fc.isSet(path+".settings.ItemStack.Lore")){
							lore = fc.getStringList(path+".settings.ItemStack.Lore");
							im.setLore(lore);
						}
						
						if(fc.isSet(path+".settings.ItemStack.Enchantment")){
							for(String enchant : fc.getConfigurationSection(path+".settings.ItemStack.Enchantment").getKeys(false)){
								Enchantment en = Enchantment.getByName(enchant);
								im.addEnchant(en, fc.getInt(path+".settings.ItemStack.Enchantment." + enchant + ".id"), true);
							}
						}
						is.setItemMeta(im);
						if(is.getItemMeta() instanceof LeatherArmorMeta){
							LeatherArmorMeta lim = (LeatherArmorMeta) is.getItemMeta();
							if(fc.isSet(path+".settings.ItemStack.LeatherMeta")){
								lim.setColor(Color.fromBGR(
										fc.getInt(path+".settings.ItemStack.LeatherMeta.Color.blue"), 
										fc.getInt(path+".settings.ItemStack.LeatherMeta.Color.green"),
										fc.getInt(path+".settings.ItemStack.LeatherMeta.Color.red")));
							}
							is.setItemMeta(lim);
						}
					}
					new tisch(l, main.getInstance(), is);
				}
			}
		}
	}
}
