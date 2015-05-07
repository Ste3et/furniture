package de.Ste3et_C0st.Furniture.Main.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.config;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.Furniture.Main.option;
import de.Ste3et_C0st.Furniture.Objects.electric.camera;
import de.Ste3et_C0st.Furniture.Objects.indoor.chair;
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

public class Manager {
	private static config cc;
	private static FileConfiguration fc;
	private String folder = "/objects/";
	
	public void load(String s){
		cc = new config();
		option o = new option();
		try{
			if(cc.ExistConfig(folder, s+ ".yml")){
				fc = cc.getConfig(s+".yml", folder);
				if(fc.isSet("Furniture."+s)){
					for(String str : fc.getConfigurationSection("Furniture." + s).getKeys(false)){
						String path = "Furniture." + s;
						path+= "." + str;
						Double x = fc.getDouble(path+".Location.x");
						Double y = fc.getDouble(path+".Location.y");
						Double z = fc.getDouble(path+".Location.z");
						World w = Bukkit.getWorld(fc.getString(path+".Location.w"));
						String face = fc.getString(path+".Location.face");
						Location l = new Location(w, x, y, z);
						List<UUID> uuidList = Utils.StringListToUUIDList(fc.getStringList(path+".UUUIDs"));
						if(uuidList==null){
							main.getInstance().getLogger().warning("Unknow data found pls remove ["+ s +".yml]");
							main.getInstance().getLogger().warning("If you use a HealthBar plugin pls use the command");
							main.getInstance().getLogger().warning("/kill @e[type=ArmorStand] {Invisible:1b,NoBasePlate:1b,NoGravity:1b}");
							main.getInstance().getLogger().warning("Then you dont use a healthbar plugin run /furniture killall");
						}else{
							for(Entity e : w.getEntities()){
								if(e!=null && e instanceof ArmorStand && uuidList.contains(e.getUniqueId())){
									e.remove();
								}
							}
							uuidList.clear();
						}
						
						float yaw = Utils.FaceToYaw(Utils.StringToFace(face));
						l.setYaw(yaw);
						
						if(s.equalsIgnoreCase("chair")){
							new chair(l, main.getInstance(), str, uuidList);
						}
						
						if(s.equalsIgnoreCase("camera")){
							l.setYaw(Utils.FaceToYaw(Utils.StringToFace(face).getOppositeFace()));
							new camera(l, main.getInstance(), str, uuidList);
						}
						
						if(s.equalsIgnoreCase("largeTable")){
							largeTable large = new largeTable(l, main.getInstance(), str, uuidList);
							o.loadOptions(fc, large);
						}
						
						if(s.equalsIgnoreCase("lantern")){
							latern later = new latern(l, main.getInstance(), str, uuidList);
							o.loadOptions(fc, later);
						}
						
						if(s.equalsIgnoreCase("sofa")){
							sofa sof = new sofa(l, main.getInstance(), str, uuidList);
							o.loadOptions(fc, sof);
						}
						
						
						if(s.equalsIgnoreCase("table")){
							table tabl = new table(l, main.getInstance(), str, uuidList);
							o.loadOptions(fc, tabl);
						}
						
						if(s.equalsIgnoreCase("tent1")){
							tent_1 tent = new tent_1(l, main.getInstance(), str, uuidList);
							o.loadOptions(fc, tent);
						}
						
						if(s.equalsIgnoreCase("tent2")){
							tent_2 tent = new tent_2(l, main.getInstance(), str, uuidList);
							o.loadOptions(fc, tent);
						}
						
						if(s.equalsIgnoreCase("tent3")){
							tent_3 tent = new tent_3(l, main.getInstance(), str, uuidList);
							o.loadOptions(fc, tent);
						}
						
						if(s.equalsIgnoreCase("campfire1")){
							campfire_1 fire = new campfire_1(l, main.getInstance(), str, uuidList);
							o.loadOptions(fc, fire);
						}
						
						if(s.equalsIgnoreCase("campfire2")){
							campfire_2 fire = new campfire_2(l, main.getInstance(), str, uuidList);
							o.loadOptions(fc, fire);
						}
						
						if(s.equalsIgnoreCase("barrels")){
							barrels fire = new barrels(l, main.getInstance(), str, uuidList);
							o.loadOptions(fc, fire);
						}
					}
				}
			}
		}catch(Exception e){
			main.getInstance().shutdown("Config " + s);
		}
	}
	
	public void saveStuhl(chair c){
		cc = new config();
		fc = cc.getConfig("chair",folder );
		if(!main.getInstance().getManager().chairList.isEmpty()){
			if(c==null){
				for(chair s : main.getInstance().getManager().chairList){
					save(s.getLocation(), "chair", s.getID(),s.getList());
				}
			}else{
				save(c.getLocation(), "chair", c.getID(), c.getList());
			}
		}
		cc.saveConfig("chair", fc, folder);
	}
	
	public void saveCamera(camera c) {
		cc = new config();
		fc = cc.getConfig("camera",folder );
		if(!main.getInstance().getManager().cameraList.isEmpty()){
			if(c==null){
				for(camera s : main.getInstance().getManager().cameraList){
					save(s.getLocation(), "camera", s.getID(),s.getList());
				}
			}else{
				save(c.getLocation(), "camera", c.getID(), c.getList());
			}
		}
		cc.saveConfig("camera", fc, folder);
	}
	
	public void saveLatern(latern l){
		cc = new config();
		fc = cc.getConfig("lantern",folder );
		option o = new option();
		if(!main.getInstance().getManager().lanternList.isEmpty()){
			if(l==null){
				for(latern s : main.getInstance().getManager().lanternList){
					save(s.getLocation(), "lantern", s.getID(),s.getList());
					o.saveOption(fc, s);
				}
			}else{
				save(l.getLocation(), "lantern", l.getID(), l.getList());
				o.saveOption(fc, l);
			}
		}
		cc.saveConfig("lantern", fc, folder);
	}
	
	public void saveCampFire1(campfire_1 l){
		cc = new config();
		fc = cc.getConfig("campfire1",folder );
		option o = new option();
		if(!main.getInstance().getManager().campfire1List.isEmpty()){
			if(l==null){
				for(campfire_1 s : main.getInstance().getManager().campfire1List){
					save(s.getLocation(), "campfire1", s.getID(),s.getList());
					o.saveOption(fc, s);
				}
			}else{
				save(l.getLocation(), "campfire1", l.getID(), l.getList());
				o.saveOption(fc, l);
			}
		}
		cc.saveConfig("campfire1", fc, folder);
	}
	
	public void saveCampFire2(campfire_2 l){
		cc = new config();
		fc = cc.getConfig("campfire2",folder );
		option o = new option();
		if(!main.getInstance().getManager().campfire2List.isEmpty()){
			if(l==null){
				for(campfire_2 s : main.getInstance().getManager().campfire2List){
					save(s.getLocation(), "campfire2", s.getID(),s.getList());
					o.saveOption(fc, s);
				}
			}else{
				save(l.getLocation(), "campfire2", l.getID(), l.getList());
				o.saveOption(fc, l);
			}
		}
		cc.saveConfig("campfire2", fc, folder);
	}
	
	public void save(Location l, String file, String ID, List<String> list){
		String path = "Furniture." + file;
		path = "Furniture."+file+"."+ID;
		fc.set(path+".Location.x",round(l.getX(), 2));
		fc.set(path+".Location.y",round(l.getY(), 2));
		fc.set(path+".Location.z",round(l.getZ(), 2));
		fc.set(path+".Location.w", l.getWorld().getName());
		fc.set(path+".Location.face", Utils.yawToFace(l.getYaw()).name());
		fc.set(path+".UUUIDs", list);
	}
	

	
	
	public void saveSofa(sofa sof){
		cc = new config();
		fc = cc.getConfig("sofa",folder );
		option o = new option();
		if(!main.getInstance().getManager().sofaList.isEmpty()){
			if(sof==null){
				for(sofa s : main.getInstance().getManager().sofaList){
					save(s.getLocation(), "sofa", s.getID(),s.getList());
					o.saveOption(fc, s);
				}
			}else{
				save(sof.getLocation(), "sofa", sof.getID(), sof.getList());
				o.saveOption(fc, sof);
			}
		}
		cc.saveConfig("sofa", fc, folder);
	}
	
	public void saveTable(table t){
		cc = new config();
		fc = cc.getConfig("table",folder );
		option o = new option();
		if(!main.getInstance().getManager().tableList.isEmpty()){
			if(t==null){
				for(table s : main.getInstance().getManager().tableList){
					save(s.getLocation(), "table", s.getID(),s.getList());
					o.saveOption(fc, s);
				}
			}else{
				save(t.getLocation(), "table", t.getID(), t.getList());
				o.saveOption(fc, t);
			}
		}
		cc.saveConfig("table", fc, folder);
	}
	
	public void saveBarrel(barrels barrel) {
		cc = new config();
		fc = cc.getConfig("barrels",folder );
		option o = new option();
		if(!main.getInstance().getManager().barrelList.isEmpty()){
			if(barrel==null){
				for(barrels s : main.getInstance().getManager().barrelList){
					save(s.getLocation(), "barrels", s.getID(),s.getList());
					o.saveOption(fc, s);
				}
			}else{
				save(barrel.getLocation(), "barrels", barrel.getID(), barrel.getList());
				o.saveOption(fc, barrel);
			}
		}
		cc.saveConfig("barrels", fc, folder);
	}
	
	public void saveTent1(tent_1 tent){
		cc = new config();
		fc = cc.getConfig("tent1",folder );
		option o = new option();
		if(!main.getInstance().getManager().tent1List.isEmpty()){
			if(tent == null){
				for(tent_1 s : main.getInstance().getManager().tent1List){
					save(s.getLocation(), "tent1", s.getID(),s.getList());
					o.saveOption(fc, s);
				}
			}else{
				save(tent.getLocation(), "tent1", tent.getID(),tent.getList());
				o.saveOption(fc, tent);
			}
			
		}
		cc.saveConfig("tent1", fc, folder);
	}
	
	public void saveLargeTable(largeTable table){
		cc = new config();
		fc = cc.getConfig("largeTable",folder );
		option o = new option();
		if(!main.getInstance().getManager().largeTableList.isEmpty()){
			if(table == null){
				for(largeTable s : main.getInstance().getManager().largeTableList){
					save(s.getLocation(), "largeTable", s.getID(),s.getList());
					o.saveOption(fc, s);
				}
			}else{
				save(table.getLocation(), "largeTable", table.getID(),table.getList());
				o.saveOption(fc, table);
			}
		}
		cc.saveConfig("largeTable", fc, folder);
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
		try{
			if(!check(s)){
				ShapedRecipe recipe = new ShapedRecipe(returnResult(s)).shape(returnFragment(s)[0], returnFragment(s)[1], returnFragment(s)[2]);
				for(Character c : returnMaterial(s).keySet()){
					if(!returnMaterial(s).get(c).equals(Material.AIR)){
						recipe.setIngredient(c.charValue(), returnMaterial(s).get(c));
					}
				}
				Bukkit.getServer().addRecipe(recipe);
			}
			
			main.getInstance().crafting.put(s, returnResult(s));
		}catch(Exception e){
			main.getInstance().shutdown("Crafting");
		}
	}
	
	public boolean check(String s){
		cc = new config();
		fc = cc.getConfig("crafting.yml", "");
		String path = "Items." + s;
		return fc.getBoolean(path + ".crafting.disable");
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack returnResult(String s){
		cc = new config();
		fc = cc.getConfig("crafting.yml", "");
		String path = "Items." + s;
		String MaterialSubID = path+".material";
		short durability = 0;
		if(MaterialSubID.contains(":")){
			String[] split = MaterialSubID.split(":");
			durability = (short) Integer.parseInt(split[1]);
		}
		Integer MaterialID = fc.getInt(path+".material");
		ItemStack is = new ItemStack(Material.getMaterial(MaterialID));
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', fc.getString(path+".name")));
		is.setItemMeta(im);
		is.setDurability(durability);
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

	public void saveTent2(tent_2 tent) {
		cc = new config();
		fc = cc.getConfig("tent2",folder );
		option o = new option();
		if(!main.getInstance().getManager().tent2List.isEmpty()){
			if(tent == null){
				for(tent_2 s : main.getInstance().getManager().tent2List){
					save(s.getLocation(), "tent2", s.getID(),s.getList());
					o.saveOption(fc, s);
				}
			}else{
				save(tent.getLocation(), "tent2", tent.getID(),tent.getList());
				o.saveOption(fc, tent);
			}
			
		}
		cc.saveConfig("tent2", fc, folder);
	}

	public void saveTent3(tent_3 tent) {
		cc = new config();
		fc = cc.getConfig("tent3",folder );
		option o = new option();
		if(!main.getInstance().getManager().tent3List.isEmpty()){
			if(tent == null){
				for(tent_3 s : main.getInstance().getManager().tent3List){
					save(s.getLocation(), "tent3", s.getID(),s.getList());
					o.saveOption(fc, s);
				}
			}else{
				save(tent.getLocation(), "tent3", tent.getID(),tent.getList());
				o.saveOption(fc, tent);
			}
			
		}
		cc.saveConfig("tent3", fc, folder);
	}
}
