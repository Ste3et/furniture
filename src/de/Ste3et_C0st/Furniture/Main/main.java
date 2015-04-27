package de.Ste3et_C0st.Furniture.Main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import net.milkbowl.vault.Metrics;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.Manager.CheckManager;
import de.Ste3et_C0st.Furniture.Main.Manager.FurnitureManager;
import de.Ste3et_C0st.Furniture.Main.Manager.Manager;
import de.Ste3et_C0st.Furniture.Main.Type.FurnitureType;
import de.Ste3et_C0st.Furniture.Listener.IPistonExtendEvent;
import de.Ste3et_C0st.Furniture.Listener.OnInteract;

public class main extends JavaPlugin {
	private static main Main;
	private Logger log = Logger.getLogger("Minecraft");

	public HashMap<String, ItemStack> crafting = new HashMap<String, ItemStack>();
	public Manager mgr;
	public FurnitureManager Fmgr;
	public Boolean isCrafting = true;
	public CheckManager check;
	public StringPage sp;
	//public Factions factionsAPI = null;
	@SuppressWarnings({ "deprecation" })
	@Override
	public void onEnable(){
			Main = this;
			getServer().getPluginManager().registerEvents(new OnInteract(), this);
			getServer().getPluginManager().registerEvents(new IPistonExtendEvent(), this);
			getCommand("furniture").setExecutor(new command());
			
			try{
				getConfig().addDefaults(YamlConfiguration.loadConfiguration(getResource("config.yml")));
				getConfig().options().copyDefaults(true);
				saveConfig();
			}catch(Exception e){
				shutdown("Config");
			}
			
			if(getServer().getPluginManager().isPluginEnabled("Vault") && getConfig().getBoolean("config.UseMetrics")){
			    try
			    {
			      Metrics metrics = new Metrics(this);
			      metrics.start();
			    }
			    catch (IOException localIOException) {}
			}
			this.check = new CheckManager(this.getServer().getPluginManager());
			this.sp = new StringPage();
			this.mgr = new Manager();
			this.Fmgr = new FurnitureManager();
			mgr.load("chair");
			mgr.load("lantern");
			mgr.load("table");
			mgr.load("largeTable");
			mgr.load("sofa");
			mgr.load("tent1");
			mgr.load("tent2");
			mgr.load("tent3");
			mgr.load("campfire1");
			mgr.load("barrels");
			mgr.load("campfire2");
			mgr.load("camera");
			mgr.defaultCrafting();
			addCrafting();
			
			isCrafting = getConfig().getBoolean("config.CraftingPermissions");	
	}

	public Double distance(Location loc1, Location loc2){
		Vector v1 = loc1.toVector();
		Vector v2 = loc1.toVector();
		return v1.distance(v2);
	}
	
	public void removeAll(){
		Fmgr.RemoveType(FurnitureType.CHAIR, false);
		Fmgr.RemoveType(FurnitureType.LARGE_TABLE, false);
		Fmgr.RemoveType(FurnitureType.LATERN, false);
		Fmgr.RemoveType(FurnitureType.SOFA, false);
		Fmgr.RemoveType(FurnitureType.TABLE, false);
		Fmgr.RemoveType(FurnitureType.BARRELS, false);
		Fmgr.RemoveType(FurnitureType.CAMPFIRE_1, false);
		Fmgr.RemoveType(FurnitureType.CAMPFIRE_2, false);
		Fmgr.RemoveType(FurnitureType.TENT_1, false);
		Fmgr.RemoveType(FurnitureType.TENT_2, false);
		Fmgr.RemoveType(FurnitureType.TENT_3, false);
		Fmgr.RemoveType(FurnitureType.CAMERA, false);
	}
	
	public void reload(){
		//try{
			mgr.saveLargeTable(null);
			mgr.saveLatern(null);
			mgr.saveSofa(null);
			mgr.saveStuhl(null);
			mgr.saveTable(null);
			mgr.saveTent1(null);
			mgr.saveTent2(null);
			getServer().resetRecipes();
			removeAll();
			crafting.clear();
			isCrafting=false;

			mgr.load("chair");
			mgr.load("lantern");
			mgr.load("table");
			mgr.load("largeTable");
			mgr.load("sofa");
			mgr.load("tent1");
			mgr.load("tent2");
			mgr.load("tent3");
			mgr.load("campfire1");
			mgr.load("barrels");
			mgr.load("campfire2");
			mgr.load("camera");
			mgr.defaultCrafting();
			addCrafting();
			isCrafting = getConfig().getBoolean("config.CraftingPermissions");
		//}catch (Exception e) {
		//	shutdown("Reload");
		//}
	}
	
	public void shutdown(String s){
		PluginDescriptionFile pdfFile = this.getDescription();
		log.warning(pdfFile.getName() + s + " Exception");
	}
	
	@Override
	public void onDisable(){
		mgr.saveLargeTable(null);
		mgr.saveLatern(null);
		mgr.saveSofa(null);
		mgr.saveStuhl(null);
		mgr.saveTable(null);
		mgr.saveTent1(null);
		mgr.saveTent2(null);
		mgr.saveCamera(null);
		getServer().resetRecipes();
	}
	
	public void addCrafting(){
		mgr.loadCrafting("largeTable");
		mgr.loadCrafting("table");
		mgr.loadCrafting("lantern");
		mgr.loadCrafting("sofa");
		mgr.loadCrafting("chair");
		mgr.loadCrafting("tent1");
		mgr.loadCrafting("tent2");
		mgr.loadCrafting("tent3");
		mgr.loadCrafting("barrels");
		mgr.loadCrafting("campfire1");
		mgr.loadCrafting("campfire2");
		mgr.loadCrafting("camera");
	}
	
	public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
         if (0 <= rotation && rotation < 22.5) {
            return "N";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "E";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "S";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "W";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "N";
        } else {
            return null;
        }
    }
	
	public static Location getNew(Location loc, BlockFace b, Double z, Double x){
		Location l = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
		if(b.equals(BlockFace.NORTH)){
			l.add(x,0,z);
		}
		
		if(b.equals(BlockFace.SOUTH)){
			l.add(-x,0,-z);
		}
		if(b.equals(BlockFace.EAST)){
			l.add(-z,0,x);
		}
		if(b.equals(BlockFace.WEST)){
			l.add(z,0,-x);
		}
		
		if(b.equals(BlockFace.NORTH_EAST)){
			l.add(x,0,z);
			l.add(-z,0,x);
		}
		
		if(b.equals(BlockFace.NORTH_WEST)){
			l.add(x,0,z);
			l.add(z,0,-x);
		}
		
		if(b.equals(BlockFace.SOUTH_EAST)){
			l.add(-x,0,-z);
			l.add(-z,0,x);
		}
		
		if(b.equals(BlockFace.SOUTH_WEST)){
			l.add(-x,0,-z);
			l.add(z,0,-x);
		}
		
		if(b.equals(BlockFace.UP)){
			l.add(0,z+x,0);
		}
		
		if(b.equals(BlockFace.DOWN)){
			l.add(0,-z-x,0);
		}
		
		return l;
	}
	
	
	public static EulerAngle getNewEuler(BlockFace b, Double z, Double x,Double y){
		EulerAngle l = new EulerAngle(0, 0, 0);
		if(b.equals(BlockFace.NORTH)){l = new EulerAngle(x,y,-z);
		}else if(b.equals(BlockFace.SOUTH)){l = new EulerAngle(-x,y,z);
		}else if(b.equals(BlockFace.EAST)){l = new EulerAngle(-z,y,x);
		}else if(b.equals(BlockFace.WEST)){l = new EulerAngle(z,y,x);}
		return l;
	}
	
	public static short getFromDey(short s){
		if(s==15){return 0;}
		if(s==14){return 1;}
		if(s==13){return 2;}
		if(s==12){return 3;}
		if(s==11){return 4;}
		if(s==10){return 5;}
		if(s==9){return 6;}
		if(s==8){return 7;}
		if(s==7){return 8;}
		if(s==6){return 9;}
		if(s==5){return 10;}
		if(s==4){return 11;}
		if(s==3){return 12;}
		if(s==2){return 13;}
		if(s==1){return 14;}
		if(s==0){return 15;}
		return 0;
	}
	
	public static Color getDyeFromDurability(short s){
		if(s==0){return Color.fromRGB(25, 25, 25);}
		if(s==1){return Color.fromRGB(153, 51, 51);}
		if(s==2){return Color.fromRGB(102, 127, 51);}
		if(s==3){return Color.fromRGB(102, 76, 51);}
		if(s==4){return Color.fromRGB(51, 76, 178);}
		if(s==5){return Color.fromRGB(127, 63, 178);}
		if(s==6){return Color.fromRGB(76, 127, 153);}
		if(s==7){return Color.fromRGB(153, 153, 153);}
		if(s==8){return Color.fromRGB(76, 76, 76);}
		if(s==9){return Color.fromRGB(242, 127, 165);}
		if(s==10){return Color.fromRGB(127, 204, 25);}
		if(s==11){return Color.fromRGB(229, 229, 51);}
		if(s==12){return Color.fromRGB(102, 153, 216);}
		if(s==13){return Color.fromRGB(178, 76, 216);}
		if(s==14){return Color.fromRGB(216, 127, 51);}
		if(s==15){return Color.fromRGB(255, 255, 255);}
		return Color.fromRGB(255, 255, 255);
	}
	
	public boolean canPlace(Player p, Location location, BlockFace b, Integer length){
		try{
			if(b==null&&length==null){
				if(!location.getBlock().getType().equals(Material.AIR)){
					return false;
				}
			}
			if(b!=null&&length!=null){
				for(int i = 0; i<= length-1; i++){
					Block bl = getNew(location, b, 0D,(double) i).getBlock();
					if(!(bl==null || bl.getType().equals(Material.AIR))){
						return false;
					}
				}
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean canPlaceLarge(Player p, Location location, BlockFace b, Integer length, Integer width){
		try{
			if(b==null&&length==null&&width==null){
				if(!location.getBlock().getType().equals(Material.AIR)){
					return false;
				}
			}
			location = main.getNew(location, b.getOppositeFace(), 2D, 0D);
			if(b!=null&&length!=null&&width!=null){
				for(int i = 0; i<= length-1; i++){
					for(int l = 0; l<= width-1; l++){
						Block bl = getNew(location, b,(double) l,(double) i).getBlock();
						if(!(bl==null || bl.getType().equals(Material.AIR))){
							return false;
						}
					}
				}
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public static String createRandomRegistryId()
	{  
	    String val = "";      
	    int ranChar = 65 + (new Random()).nextInt(90-65);
	    char ch = (char)ranChar;        
	    val += ch;      
	    Random r = new Random();
	    int numbers = 100000 + (int)(r.nextFloat() * 899900);
	    val += String.valueOf(numbers);
	    val += "-";
	    for(int i = 0; i<6;){
	        int ranAny = 48 + (new Random()).nextInt(90-65);
	        if(!(57 < ranAny && ranAny<= 65)){
	        char c = (char)ranAny;      
	        val += c;
	        i++;
	        }
	    }

	    return val;
	}
	
	public static main getInstance() {return Main;}

	public boolean canPlaceTent(Player p, Location location, BlockFace b, Integer length, Integer width, Integer height){
		try{
			if(b==null&&length==null&&width==null){
				if(!location.getBlock().getType().equals(Material.AIR)){
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("Config.Messages.Space")));
					return false;
				}
			}
			location = main.getNew(location, b.getOppositeFace(), 3D, 0D);
			if(b!=null&&length!=null&&width!=null){
				for(int i = 0; i<= length-1; i++){
					for(int l = 0; l<= width-1; l++){
						for(int y=0;y<=height-1;y++){
							if(!getNew(location, b,(double) l,(double) i).add(0,y,0).getBlock().getType().equals(Material.AIR)){
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("Config.Messages.Space")));
								return false;
							}
						}
						
					}
				}
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public FurnitureManager getManager() {return this.Fmgr;}
	public CheckManager getCheckManager(){return this.check;}
	public StringPage getStringPage(){return this.sp;}
}
