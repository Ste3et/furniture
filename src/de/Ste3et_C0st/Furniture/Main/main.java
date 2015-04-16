package de.Ste3et_C0st.Furniture.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.milkbowl.vault.Metrics;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.Manager;
import de.Ste3et_C0st.Furniture.Listener.OnInteract;
import de.Ste3et_C0st.Furniture.Objects.indoor.chair;
import de.Ste3et_C0st.Furniture.Objects.indoor.largeTable;
import de.Ste3et_C0st.Furniture.Objects.indoor.latern;
import de.Ste3et_C0st.Furniture.Objects.indoor.sofa;
import de.Ste3et_C0st.Furniture.Objects.indoor.table;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_1;

public class main extends JavaPlugin {
	private static main Main;
	public List<sofa> sofas = new ArrayList<sofa>();
	public List<latern> laternen = new ArrayList<latern>();
	public List<chair> stuehle = new ArrayList<chair>();
	public List<table> tische = new ArrayList<table>();
	public List<largeTable> tische2 = new ArrayList<largeTable>();
	public HashMap<String, ItemStack> crafting = new HashMap<String, ItemStack>();
	public Manager mgr;
	public List<tent_1> tents1 = new ArrayList<tent_1>();
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable(){
		Main = this;
		getServer().getPluginManager().registerEvents(new OnInteract(), this);
		getCommand("furniture").setExecutor(new command());
		getConfig().addDefaults(YamlConfiguration.loadConfiguration(getResource("config.yml")));
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		if(getServer().getPluginManager().isPluginEnabled("Vault") && getConfig().getBoolean("config.UseMetrics")){
		    try
		    {
		      Metrics metrics = new Metrics(this);
		      metrics.start();
		    }
		    catch (IOException localIOException) {}
		}
		
		this.mgr = new Manager();
		mgr.loadLargeTisch();
		mgr.loadLatern();
		mgr.loadSofa();
		mgr.loadStuhl();
		mgr.loadTisch();
		mgr.loadtent1();
		mgr.defaultCrafting();
		addCrafting();
	}
	
	@Override
	public void onDisable(){
		mgr.saveLargeTable();
		mgr.saveLatern();
		mgr.saveSofa();
		mgr.saveStuhl();
		mgr.saveTable();
		mgr.saveTent1();
		getServer().resetRecipes();
	}
	
	public void addCrafting(){
		mgr.loadCrafting("largeTable");
		mgr.loadCrafting("table");
		mgr.loadCrafting("lantern");
		mgr.loadCrafting("sofa");
		mgr.loadCrafting("chair");
		mgr.loadCrafting("tent1");
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
	
	public boolean canPlace(Player p, Location location, BlockFace b, Integer length){
		if(b==null&&length==null){
			if(!location.getBlock().getType().equals(Material.AIR)){
				return false;
			}
		}
		if(b!=null&&length!=null){
			for(int i = 0; i<= length-1; i++){
				if(!getNew(location, b, 0D,(double) i).getBlock().getType().equals(Material.AIR)){
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean canPlaceLarge(Player p, Location location, BlockFace b, Integer length, Integer width){
		if(b==null&&length==null&&width==null){
			if(!location.getBlock().getType().equals(Material.AIR)){
				return false;
			}
		}
		location = main.getNew(location, b.getOppositeFace(), 2D, 0D);
		if(b!=null&&length!=null&&width!=null){
			for(int i = 0; i<= length-1; i++){
				for(int l = 0; l<= width-1; l++){
					if(!getNew(location, b,(double) l,(double) i).getBlock().getType().equals(Material.AIR)){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public List<Location> getFunList(){
		List<Location> loc = new ArrayList<Location>();
		if(!this.stuehle.isEmpty()){
			for(chair s : stuehle){
				loc.add(s.getLocation());
			}
		}
		if(!this.laternen.isEmpty()){
			for(latern l : laternen){
				loc.add(l.getLocation());
			}
		}
		if(!this.tische2.isEmpty()){
			for(largeTable l : tische2){
				loc.add(l.getLocation());
			}
		}
		if(!this.sofas.isEmpty()){
			for(sofa s : sofas){
				loc.add(s.getLocation());
			}
		}
		if(!this.tische.isEmpty()){
			for(table t : tische){
				loc.add(t.getLocation());
			}
		}
		return loc;
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
		if(b==null&&length==null&&width==null){
			if(!location.getBlock().getType().equals(Material.AIR)){
				return false;
			}
		}
		location = main.getNew(location, b.getOppositeFace(), 3D, 0D);
		if(b!=null&&length!=null&&width!=null){
			for(int i = 0; i<= length-1; i++){
				for(int l = 0; l<= width-1; l++){
					for(int y=0;y<=height-1;y++){
						if(!getNew(location, b,(double) l,(double) i).add(0,y,0).getBlock().getType().equals(Material.AIR)){
							return false;
						}
					}
					
				}
			}
		}
		return true;
	}
}
