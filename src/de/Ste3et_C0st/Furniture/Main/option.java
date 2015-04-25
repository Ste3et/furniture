package de.Ste3et_C0st.Furniture.Main;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Objects.indoor.largeTable;
import de.Ste3et_C0st.Furniture.Objects.indoor.latern;
import de.Ste3et_C0st.Furniture.Objects.indoor.sofa;
import de.Ste3et_C0st.Furniture.Objects.indoor.table;
import de.Ste3et_C0st.Furniture.Objects.outdoor.barrels;
import de.Ste3et_C0st.Furniture.Objects.outdoor.campfire_1;
import de.Ste3et_C0st.Furniture.Objects.outdoor.campfire_2;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_1;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_2;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_3;

public class option {
	

	public void saveOption(FileConfiguration fc, campfire_1 l) {
		fc.set("Furniture.campfire1."+l.getID()+".settings", l.getFire());
	}
	
	public void saveOption(FileConfiguration fc, campfire_2 l) {
		fc.set("Furniture.campfire2."+l.getID()+".settings", l.isFire());
	}
	
	  public void saveOption(FileConfiguration fc, latern l)
	  {
	    fc.set("Furniture.lantern." + l.getID() + ".settings", Boolean.valueOf(l.getLight()));
	  }
	
	public void saveOption(FileConfiguration fc, largeTable l){
		for(Integer is : l.getTeller().keySet()){
			saveItem(fc, "Furniture.largeTable."+l.getID()+".settings.items." + is, l.getTeller().get(is));
		}
		for(Integer is : l.getColor().keySet()){
			saveColor(fc, "Furniture.largeTable."+l.getID()+".settings.colors."+is, l.getColor().get(is));
		}
	}
	
	public void saveOption(FileConfiguration fc, tent_1 l){
		for(Integer is : l.getColor().keySet()){
			saveColor(fc, "Furniture.tent1."+l.getID()+".settings.colors."+is, l.getColor().get(is));
		}
	}
	
	public void saveOption(FileConfiguration fc, tent_2 l){
		for(Integer is : l.getColor().keySet()){
			saveColor(fc, "Furniture.tent2."+l.getID()+".settings.colors."+is, l.getColor().get(is));
		}
	}
	
	public void saveOption(FileConfiguration fc, tent_3 l) {
		for(Integer is : l.getColor().keySet()){
			saveColor(fc, "Furniture.tent3."+l.getID()+".settings.colors."+is, l.getColor().get(is));
		}
	}
	
	public void saveOption(FileConfiguration fc, sofa l){
		for(Integer is : l.getColor().keySet()){
			saveColor(fc, "Furniture.sofa."+l.getID()+".settings.colors."+is, l.getColor().get(is));
		}
	}
	
	public void saveOption(FileConfiguration fc, table l){
		saveItem(fc, "Furniture.table."+l.getID()+".settings.item", l.getItemStack());
	}
	
	public void saveOption(FileConfiguration fc, barrels l){
		saveItem(fc, "Furniture.barrels."+l.getID()+".settings.item", l.getItemStack());
	}
	
	public void loadOptions(FileConfiguration fc, tent_1 l){
		if(fc.isSet("Furniture.tent1."+l.getID()+".settings")){
				if(fc.isConfigurationSection("Furniture.tent1."+l.getID()+".settings.colors")){
					Integer current = 0;
					HashMap<Integer, Short> colors = new HashMap<Integer, Short>();
					for(String str : fc.getConfigurationSection("Furniture.tent1."+l.getID()+".settings.colors").getKeys(false)){
						Integer i = fc.getInt("Furniture.tent1."+l.getID()+".settings.colors."+str);
						colors.put(current, i.shortValue());
						current++;
					}
					l.setColor(colors);
				}
		}
	}
	
	public void loadOptions(FileConfiguration fc, tent_2 l){
		if(fc.isSet("Furniture.tent2."+l.getID()+".settings")){
				if(fc.isConfigurationSection("Furniture.tent2."+l.getID()+".settings.colors")){
					Integer current = 0;
					HashMap<Integer, Short> colors = new HashMap<Integer, Short>();
					for(String str : fc.getConfigurationSection("Furniture.tent2."+l.getID()+".settings.colors").getKeys(false)){
						Integer i = fc.getInt("Furniture.tent2."+l.getID()+".settings.colors."+str);
						colors.put(current, i.shortValue());
						current++;
					}
					l.setColor(colors);
				}
		}
	}
	
	public void loadOptions(FileConfiguration fc, tent_3 l){
		if(fc.isSet("Furniture.tent3."+l.getID()+".settings")){
				if(fc.isConfigurationSection("Furniture.tent3."+l.getID()+".settings.colors")){
					Integer current = 0;
					HashMap<Integer, Integer> colors = new HashMap<Integer, Integer>();
					for(String str : fc.getConfigurationSection("Furniture.tent3."+l.getID()+".settings.colors").getKeys(false)){
						Integer i = fc.getInt("Furniture.tent3."+l.getID()+".settings.colors."+str);
						colors.put(current, i);
						current++;
					}
					l.setColor(colors);
				}
		}
	}
	
	public void loadOptions(FileConfiguration fc, table l){
		if(fc.isSet("Furniture.table."+l.getID()+".settings")){
				if(fc.isConfigurationSection("Furniture.table."+l.getID()+".settings.item")){
					ItemStack is = getItemStack(fc, "Furniture.table."+l.getID()+".settings.item");
					l.setItem(is);
				}
		}
	}
	
	public void loadOptions(FileConfiguration fc, barrels l){
		if(fc.isSet("Furniture.barrels."+l.getID()+".settings")){
				if(fc.isConfigurationSection("Furniture.barrels."+l.getID()+".settings.item")){
					ItemStack is = getItemStack(fc, "Furniture.barrels."+l.getID()+".settings.item");
					l.setItemstack(is);
				}
		}
	}
	
	
	public void loadOptions(FileConfiguration fc, latern l){
		if(fc.isSet("Furniture.lantern."+l.getID()+".settings")){
				if(fc.isBoolean("Furniture.lantern."+l.getID()+".settings.state")){
					l.setLight(fc.getBoolean("Furniture.lantern."+l.getID()+".settings.state"));
				}
		}
	}
	
	public void loadOptions(FileConfiguration fc, campfire_1 l) {
		if(fc.isSet("Furniture.campfire1."+l.getID()+".settings")){
			if(fc.isBoolean("Furniture.campfire1."+l.getID()+".settings.state")){
				l.setLight(fc.getBoolean("Furniture.campfire1."+l.getID()+".settings.state"), false);
			}
		}	
	}
	

	public void loadOptions(FileConfiguration fc, campfire_2 l) {
		if(fc.isSet("Furniture.campfire2."+l.getID()+".settings")){
			if(fc.isBoolean("Furniture.campfire2."+l.getID()+".settings.state")){
				l.setLight(fc.getBoolean("Furniture.campfire2."+l.getID()+".settings.state"), false);
			}
		}	
	}
	
	public void loadOptions(FileConfiguration fc, sofa l){
		if(fc.isSet("Furniture.sofa."+l.getID()+".settings")){
				if(fc.isConfigurationSection("Furniture.sofa."+l.getID()+".settings.colors")){
					Integer current = 0;
					HashMap<Integer, Short> colors = new HashMap<Integer, Short>();
					for(String str : fc.getConfigurationSection("Furniture.sofa."+l.getID()+".settings.colors").getKeys(false)){
						Integer i = fc.getInt("Furniture.sofa."+l.getID()+".settings.colors."+str);
						colors.put(current, i.shortValue());
						current++;
					}
					l.setColor(colors);
				}
		}
	}
	
	public void loadOptions(FileConfiguration fc, largeTable l){
		if(fc.isSet("Furniture.largeTable."+l.getID()+".settings")){
				HashMap<Integer, Short> colors = new HashMap<Integer, Short>();
				HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
				if(fc.isConfigurationSection("Furniture.largeTable."+l.getID()+".settings.colors")){
					Integer current = 0;
					for(String str : fc.getConfigurationSection("Furniture.largeTable."+l.getID()+".settings.colors").getKeys(false)){
						Integer i = fc.getInt("Furniture.largeTable."+l.getID()+".settings.colors."+str);
						colors.put(current, i.shortValue());
						current++;
					}
				}
				if(fc.isConfigurationSection("Furniture.largeTable."+l.getID()+".settings.items")){
					for(String str : fc.getConfigurationSection("Furniture.largeTable."+l.getID()+".settings.items").getKeys(false)){
						items.put(Integer.parseInt(str), getItemStack(fc, "Furniture.largeTable."+l.getID()+".settings.items."+str));
					}
				}
				l.setColor(colors);
				l.setTeller(items);
		}
	
	}
	
	
	public void saveColor(FileConfiguration fc, String s, Short sh){
		fc.set(s, sh);
	}
	
	public void saveColor(FileConfiguration fc, String s, Integer sh){
		fc.set(s, sh);
	}
	
	
	public void saveItem(FileConfiguration fc, String s, ItemStack is){
		fc.set(s, is);
	}
	
	public ItemStack getItemStack(FileConfiguration fc, String s){
		return fc.getItemStack(s);
	}
}
