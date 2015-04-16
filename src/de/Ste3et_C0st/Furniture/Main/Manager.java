package de.Ste3et_C0st.Furniture.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.Ste3et_C0st.Furniture.Main.config;
import de.Ste3et_C0st.Furniture.Objects.indoor.chair;
import de.Ste3et_C0st.Furniture.Objects.indoor.largeTable;
import de.Ste3et_C0st.Furniture.Objects.indoor.latern;
import de.Ste3et_C0st.Furniture.Objects.indoor.sofa;
import de.Ste3et_C0st.Furniture.Objects.indoor.table;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_1;

public class Manager {
	private static config cc;
	private static FileConfiguration fc;
	private String folder = "/objects/";
	
	public void loadStuhl(){
		cc = new config();
		if(cc.ExistConfig(folder, "chair.yml")){
			fc = cc.getConfig("chair.yml", folder);
			if(fc.isSet("Furniture.chair")){
				for(String s : fc.getConfigurationSection("Furniture.chair").getKeys(false)){
					String path = "Furniture.chair";
					path+= "." + s;
					
					Double x = fc.getDouble(path+".Location.x");
					Double y = fc.getDouble(path+".Location.y");
					Double z = fc.getDouble(path+".Location.z");
					World w = Bukkit.getWorld(fc.getString(path+".Location.w"));
					String face = fc.getString(path+".Location.face");
					float yaw = Utils.FaceToYaw(Utils.StringToFace(face));
					
					Location l = new Location(w, x, y, z);
					l.setYaw(yaw);
					new chair(l, main.getInstance(), s);
				}
			}
		}
	}
	
	public void loadLatern(){
		cc = new config();
		if(cc.ExistConfig(folder, "lantern.yml")){
			fc = cc.getConfig("lantern.yml", folder);
			if(fc.isSet("Furniture.lantern")){
				for(String s : fc.getConfigurationSection("Furniture.lantern").getKeys(false)){
					String path = "Furniture.lantern";
					path+= "." + s;
					
					Double x = fc.getDouble(path+".Location.x");
					Double y = fc.getDouble(path+".Location.y");
					Double z = fc.getDouble(path+".Location.z");
					World w = Bukkit.getWorld(fc.getString(path+".Location.w"));
					String face = fc.getString(path+".Location.face");
					Boolean b = fc.getBoolean(path+".settings.Light");
					Location l = new Location(w, x, y, z);
					float yaw = Utils.FaceToYaw(Utils.StringToFace(face));
					l.setYaw(yaw);
					new latern(l, main.getInstance(), b, s);
				}
			}
		}
	}
	
	public void loadTisch(){
		cc = new config();
		if(cc.ExistConfig(folder, "table.yml")){
			fc = cc.getConfig("table.yml", folder);
			if(fc.isSet("Furniture.table")){
				for(String s : fc.getConfigurationSection("Furniture.table").getKeys(false)){
					String path = "Furniture.table";
					path+= "." + s;
					
					Double x = fc.getDouble(path+".Location.x");
					Double y = fc.getDouble(path+".Location.y");
					Double z = fc.getDouble(path+".Location.z");
					World w = Bukkit.getWorld(fc.getString(path+".Location.w"));
					String face = fc.getString(path+".Location.face");
					Location l = new Location(w, x, y, z);
					float yaw = Utils.FaceToYaw(Utils.StringToFace(face));
					l.setYaw(yaw);
					ItemStack is = getItemStack(fc, path+".settings.ItemStack");
					new table(l, main.getInstance(), is, s);
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getItemStack(FileConfiguration fc, String s){
		String path = s;
		ItemStack is = null;
		if(fc.isSet(path)){
			Material m = Material.getMaterial(fc.getInt(path+".material"));
			Short subID = (short) fc.getInt(path+".durability");
			Integer amount = fc.getInt(path+".amount");
			is = new ItemStack(m,amount,subID);
			ItemMeta im = is.getItemMeta();
			
			List<String> lore = new ArrayList<String>();
			if(fc.isSet(path+".lore")){
				lore = fc.getStringList(path+".lore");
				im.setLore(lore);
			}
			
			if(fc.isSet(path+".displayName")){
				im.setDisplayName(fc.getString(path+".displayName"));
			}
			
			if(fc.isSet(path+".Enchantment")){
				for(String enchant : fc.getConfigurationSection(path+".Enchantment").getKeys(false)){
					Enchantment en = Enchantment.getByName(enchant);
					im.addEnchant(en, fc.getInt(path+".Enchantment." + enchant + ".lvl"), true);
				}
			}
			is.setItemMeta(im);
			if(is.getItemMeta() instanceof LeatherArmorMeta){
				LeatherArmorMeta lim = (LeatherArmorMeta) is.getItemMeta();
				if(fc.isSet(path+".LeatherMeta")){
					lim.setColor(Color.fromBGR(
							fc.getInt(path+".LeatherMeta.Color.blue"), 
							fc.getInt(path+".LeatherMeta.Color.green"),
							fc.getInt(path+".LeatherMeta.Color.red")));
				}
				is.setItemMeta(lim);
			}
		}
		return is;
	}

	public List<ItemStack> getList(FileConfiguration fc, String s){
		List<ItemStack> itemlist = new ArrayList<ItemStack>();
		if(fc.isSet(s)){
			for(String string : fc.getConfigurationSection(s).getKeys(false)){
				ItemStack is = new ItemStack(Material.getMaterial(fc.getString(s+"."+string+".material")));
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(main.createRandomRegistryId());
				is.setDurability((short) fc.getInt(s+"."+string+".durability"));
				is.setItemMeta(im);
				itemlist.add(is);
			}
		}
		return itemlist;
	}
	
	public void loadLargeTisch(){
		cc = new config();
		if(cc.ExistConfig(folder, "largeTable.yml")){
			fc = cc.getConfig("largeTable.yml", folder);
			if(fc.isSet("Furniture.largeTable")){
				for(String s : fc.getConfigurationSection("Furniture.largeTable").getKeys(false)){
					String path = "Furniture.largeTable";
					path+= "." + s;
					
					Double x = fc.getDouble(path+".Location.x");
					Double y = fc.getDouble(path+".Location.y");
					Double z = fc.getDouble(path+".Location.z");
					World w = Bukkit.getWorld(fc.getString(path+".Location.w"));
					String face = fc.getString(path+".Location.face");
					Location l = new Location(w, x, y, z);
					float yaw = Utils.FaceToYaw(Utils.StringToFace(face));
					l.setYaw(yaw);
					
					HashMap<Integer, ItemStack> teller = new HashMap<Integer, ItemStack>();
					teller.put(0, getItemStack(fc, path+".settings.ItemStack_place1"));
					teller.put(1, getItemStack(fc, path+".settings.ItemStack_place2"));
					teller.put(2, getItemStack(fc, path+".settings.ItemStack_place3"));
					teller.put(3, getItemStack(fc, path+".settings.ItemStack_place4"));
					
					new largeTable(l, main.getInstance(), getList(fc, path+".settings.ColorManager"), teller, s);
				}
			}
		}
	}
	
	public void loadSofa(){
		cc = new config();
		if(cc.ExistConfig(folder, "sofa.yml")){
			fc = cc.getConfig("sofa.yml", folder);
			if(fc.isSet("Furniture.sofa")){
				for(String s : fc.getConfigurationSection("Furniture.sofa").getKeys(false)){
					String path = "Furniture.sofa";
					path+= "." + s;
					
					Double x = fc.getDouble(path+".Location.x");
					Double y = fc.getDouble(path+".Location.y");
					Double z = fc.getDouble(path+".Location.z");
					World w = Bukkit.getWorld(fc.getString(path+".Location.w"));
					String face = fc.getString(path+".Location.face");
					Location l = new Location(w, x, y, z);
					float yaw = Utils.FaceToYaw(Utils.StringToFace(face));
					l.setYaw(yaw);
					
					new sofa(l, 3, main.getInstance(), getList(fc, path+".settings.ColorManager"), s);
				}
			}
		}
	}
	
	public void loadtent1(){
		cc = new config();
		if(cc.ExistConfig(folder, "tent1.yml")){
			fc = cc.getConfig("tent1.yml", folder);
			if(fc.isSet("Furniture.tent1")){
				for(String s : fc.getConfigurationSection("Furniture.tent1").getKeys(false)){
					String path = "Furniture.tent1";
					path+= "." + s;
					Double x = fc.getDouble(path+".Location.x");
					Double y = fc.getDouble(path+".Location.y");
					Double z = fc.getDouble(path+".Location.z");
					World w = Bukkit.getWorld(fc.getString(path+".Location.w"));
					String face = fc.getString(path+".Location.face");
					Location l = new Location(w, x, y, z);
					float yaw = Utils.FaceToYaw(Utils.StringToFace(face));
					l.setYaw(yaw);
					
					new tent_1(l, main.getInstance(), s);
				}
			}
		}
	}
	
	public void saveStuhl(){
		cc = new config();
		fc = cc.getConfig("chair.yml",folder );
		if(!main.getInstance().stuehle.isEmpty()){
			String path = "Furniture.chair";
			for(chair s : main.getInstance().stuehle){
				path = "Furniture.chair."+s.getID();
				fc.set(path+".Location.x",round(s.getLocation().getX(), 2));
				fc.set(path+".Location.y",round(s.getLocation().getY(), 2));
				fc.set(path+".Location.z",round(s.getLocation().getZ(), 2));
				fc.set(path+".Location.w", s.getLocation().getWorld().getName());
				fc.set(path+".Location.face", s.getBlockFace().name());
			}
			cc.saveConfig("chair.yml", fc, folder);
		}
	}
	
	public void saveLatern(){
		cc = new config();
		fc = cc.getConfig("lantern.yml",folder );
		if(!main.getInstance().laternen.isEmpty()){
			String path = "Furniture.lantern";
			for(latern s : main.getInstance().laternen){
				path = "Furniture.lantern."+s.getID();
				fc.set(path+".Location.x",round(s.getLocation().getX(), 2));
				fc.set(path+".Location.y",round(s.getLocation().getY(), 2));
				fc.set(path+".Location.z",round(s.getLocation().getZ(), 2));
				fc.set(path+".Location.w", s.getLocation().getWorld().getName());
				fc.set(path+".Location.face", s.getBlockFace().name());
				fc.set(path+".settings.Light", s.getBlockState());
			}
			cc.saveConfig("lantern.yml", fc, folder);
		}
	}
	
	
	public void saveSofa(){
		cc = new config();
		fc = cc.getConfig("sofa.yml",folder );
		if(!main.getInstance().sofas.isEmpty()){
			String path = "Furniture.sofa";
			for(sofa s : main.getInstance().sofas){
				path = "Furniture.sofa."+s.getID();
				fc.set(path+".Location.x",round(s.getLocation().getX(), 2));
				fc.set(path+".Location.y",round(s.getLocation().getY(), 2));
				fc.set(path+".Location.z",round(s.getLocation().getZ(), 2));
				fc.set(path+".Location.w", s.getLocation().getWorld().getName());
				fc.set(path+".Location.face", s.getBlockFace().name());
				if(!s.getItemListTisch().isEmpty()){
					int l = 0;
					for(ItemStack is : s.getItemListTisch()){	
						fc.set(path+".settings.ColorManager." + l + ".material", is.getType().name());
						fc.set(path+".settings.ColorManager." + l + ".durability", is.getDurability());
						l++;
					}
				}
			}
			cc.saveConfig("sofa.yml", fc, folder);
		}
	}
	
	public void saveItem(FileConfiguration fc, String s, ItemStack is){
		fc.set(s+".material", is.getType().name());
		fc.set(s+".durability", is.getDurability());
		fc.set(s+".amount", is.getAmount());
		
		if(is.hasItemMeta()){
			if(is.getItemMeta().hasDisplayName()){fc.set(s+".displayName", is.getType().name());}
			if(is.getItemMeta().hasLore()){fc.set(s+".lore", is.getItemMeta().getLore());}
			
			if(!is.getEnchantments().isEmpty()){
				for(Enchantment e : is.getEnchantments().keySet()){
					fc.set(s+".Enchantment." + e.getName() + ".lvl", e.getStartLevel());
				}
			}
			
			if(is.getItemMeta() instanceof LeatherArmorMeta){
				LeatherArmorMeta lm = (LeatherArmorMeta) is.getItemMeta();
				fc.set(s+".LeatherMeta.Color.blue", lm.getColor().getBlue());
				fc.set(s+".LeatherMeta.Color.green", lm.getColor().getGreen());
				fc.set(s+".LeatherMeta.Color.red", lm.getColor().getRed());		
			}
		}
	}
	
	public void saveTable(){
		cc = new config();
		fc = cc.getConfig("table.yml",folder );
		if(!main.getInstance().tische.isEmpty()){
			String path = "Furniture.table";
			for(table s : main.getInstance().tische){
				path = "Furniture.table."+s.getID();
				fc.set(path+".Location.x",round(s.getLocation().getX(), 2));
				fc.set(path+".Location.y",round(s.getLocation().getY(), 2));
				fc.set(path+".Location.z",s.getLocation().getZ());
				fc.set(path+".Location.w", s.getLocation().getWorld().getName());
				fc.set(path+".Location.face", s.getBlockFace().name());
				if(s.getItemStack()!=null){
					saveItem(fc, path, s.getItemStack());
				}
			}
			cc.saveConfig("table.yml", fc, folder);
		}
	}
	
	public void saveTent1(){
		cc = new config();
		fc = cc.getConfig("tent1.yml",folder );
		if(!main.getInstance().tents1.isEmpty()){
			String path = "Furniture.tent1";
			for(tent_1 s : main.getInstance().tents1){
				path = "Furniture.tent1."+s.getID();
				fc.set(path+".Location.x",round(s.getLocation().getX(), 2));
				fc.set(path+".Location.y",round(s.getLocation().getY(), 2));
				fc.set(path+".Location.z",round(s.getLocation().getZ(), 2));
				fc.set(path+".Location.w", s.getLocation().getWorld().getName());
				fc.set(path+".Location.face", s.getBlockFace().name());
			}
			cc.saveConfig("tent1.yml", fc, folder);
		}
	}
	
	public void saveLargeTable(){
		cc = new config();
		fc = cc.getConfig("largeTable.yml",folder );
		if(!main.getInstance().tische2.isEmpty()){
			String path = "Furniture.largeTable";
			for(largeTable s : main.getInstance().tische2){
				path = "Furniture.largeTable."+s.getID();
				fc.set(path+".Location.x",round(s.getLocation().getX(), 2));
				fc.set(path+".Location.y",round(s.getLocation().getY(), 2));
				fc.set(path+".Location.z",round(s.getLocation().getZ(), 2));
				fc.set(path+".Location.w", s.getLocation().getWorld().getName());
				fc.set(path+".Location.face", s.getBlockFace().name());
				if(!s.getItemListTisch().isEmpty()){
					int l = 0;
					for(ItemStack is : s.getItemListTisch()){
						fc.set(path+".settings.ColorManager." + l + ".material", is.getType().name());
						fc.set(path+".settings.ColorManager." + l + ".durability", is.getDurability());
						l++;
					}
				}
				
				if(!s.getTeller().isEmpty()){
					for(int l=0;l<=3;l++ ){
						try{
							ItemStack is = s.getTeller().get(l);
							saveItem(fc, path, is);
						}catch(Exception e){
							ItemStack is = new ItemStack(Material.AIR);
							saveItem(fc, path, is);
						}
					}
				}
			}
			cc.saveConfig("largeTable.yml", fc, folder);
		}
	}
	
	public void deleteFromConfig(String ID, String type){
		cc = new config();
		if(cc.ExistConfig(folder, type+".yml")){
			fc = cc.getConfig(type, folder);
			if(fc.isSet("Furniture." + type + "." + ID)){
				fc.set("Furniture." + type + "." + ID, null);
			}
			cc.saveConfig(type, fc, folder);
		}
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	@SuppressWarnings("deprecation")
	public void defaultCrafting(){
		cc = new config();
		fc = cc.getConfig("crafting.yml", "");
		fc.addDefaults(YamlConfiguration.loadConfiguration(main.getInstance().getResource("crafting.yml")));
		fc.options().copyDefaults(true);
		cc.saveConfig("crafting.yml", fc, "");
	}
	
	public void loadCrafting(String s){
			ShapedRecipe  recipe = new ShapedRecipe(returnResult(s)).shape(returnFragment(s)[0], returnFragment(s)[1], returnFragment(s)[2]);
			for(Character c : returnMaterial(s).keySet()){
				if(!returnMaterial(s).get(c).equals(Material.AIR)){
					recipe.setIngredient(c.charValue(), returnMaterial(s).get(c));
				}
			}
			main.getInstance().getServer().addRecipe(recipe);	
			main.getInstance().crafting.put(s, returnResult(s));
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack returnResult(String s){
		cc = new config();
		fc = cc.getConfig("crafting.yml", "");
		String path = "Items." + s;
		Integer MaterialID = fc.getInt(path+".material");
		Short shor = (short) fc.getInt(path+".durability");
		ItemStack is = new ItemStack(Material.getMaterial(MaterialID));
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', fc.getString(path+".name")));
		is.setItemMeta(im);
		is.setDurability(shor);
		is.setAmount(1);
		return is;
	}
	
	public String[] returnFragment(String s){
		cc = new config();
		fc = cc.getConfig("crafting.yml", "");
		String path = "Items." + s;
		String recipe = fc.getString(path+".crafting.recipe");
		String[] fragments = recipe.split(",");
		return fragments;
	}
	
	public List<String> returnCharacters(String s){
		List<String> stringList = new ArrayList<String>();
		for(String str: returnFragment(s)){
			String[] sl = str.split("(?!^)");
			for(String o : sl){
				if(!stringList.contains(o)){
					stringList.add(o);
				}
			}
		}
		return stringList;
	}
	
	@SuppressWarnings("deprecation")
	public HashMap<Character,Material> returnMaterial(String s){
		cc = new config();
		fc = cc.getConfig("crafting.yml", "");
		String path = "Items." + s;
		List<String> stringList = returnCharacters(s);
		HashMap<Character, Material> materialHash = new HashMap<Character, Material>();
		for(String str : stringList){
			Character chars = str.charAt(0);
			Integer MaterialID = fc.getInt(path+".crafting.index." + str);
			Material material = Material.getMaterial(MaterialID);
			materialHash.put(chars, material);
		}
		return materialHash;
	}
}
