package de.Ste3et_C0st.Furniture.Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.DiceEaster.DiceEaster;
import de.Ste3et_C0st.Furniture.Listener.OnInteract;
import de.Ste3et_C0st.Furniture.Objects.largeTable;
import de.Ste3et_C0st.Furniture.Objects.laterne;
import de.Ste3et_C0st.Furniture.Objects.sofa;
import de.Ste3et_C0st.Furniture.Objects.stuhl;
import de.Ste3et_C0st.Furniture.Objects.tisch;

public class main extends JavaPlugin {
	private static main Main;
	public List<sofa> sofas = new ArrayList<sofa>();
	public List<laterne> laternen = new ArrayList<laterne>();
	public List<stuhl> stuehle = new ArrayList<stuhl>();
	public List<tisch> tische = new ArrayList<tisch>();
	public List<largeTable> tische2 = new ArrayList<largeTable>();
	public ItemsEquipment itemse;
	public DiceEaster dice;
	
	@Override
	public void onEnable(){
		getCommand("Furniture").setExecutor(new command());
		Main = this;
		this.itemse = new ItemsEquipment();
		getServer().getPluginManager().registerEvents(new OnInteract(), this);
		dice = (DiceEaster) getServer().getPluginManager().getPlugin("DiceEaster");
		addCrafting();
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public void addCrafting(){
		ShapedRecipe grinderRecipe = new ShapedRecipe(itemse.Sofa).shape("#0#", "###", "+0+").setIngredient('+', Material.FENCE).
		setIngredient('#', Material.WOOL);
		getServer().addRecipe(grinderRecipe);
		
		grinderRecipe = new ShapedRecipe(itemse.Laterne).shape("0#0", "-+-", "0E0").setIngredient('+', Material.TORCH).
		setIngredient('#', Material.WOOD_PLATE).setIngredient('-', Material.STICK).setIngredient('E', Material.OBSIDIAN);
		getServer().addRecipe(grinderRecipe);
		
		grinderRecipe = new ShapedRecipe(itemse.Laterne).shape("0#0", "-+-", "0E0").setIngredient('+', Material.TORCH).
		setIngredient('#', Material.WOOD_PLATE).setIngredient('-', Material.STICK).setIngredient('E', Material.OBSIDIAN);
		getServer().addRecipe(grinderRecipe);
		
		grinderRecipe = new ShapedRecipe(itemse.stuhl).shape("0#0", "0#0", "E0E").setIngredient('#', Material.TRAP_DOOR).
		setIngredient('E', Material.STICK);
		getServer().addRecipe(grinderRecipe);
		
		grinderRecipe = new ShapedRecipe(itemse.tisch).shape("0#0", "0E0", "0+0").setIngredient('#', Material.TRAP_DOOR).
		setIngredient('E', Material.STICK).setIngredient('+', Material.WOOD_STEP);
		getServer().addRecipe(grinderRecipe);
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

	 public static main getInstance() {return Main;}
}
