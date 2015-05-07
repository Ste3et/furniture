package de.Ste3et_C0st.Furniture.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.Type.HeadArmType;

public class Utils {

    public static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
    public static List<BlockFace> axisList = Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
    public static final BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };
    public static BlockFace yawToFaceRadial(float yaw) {
            return radial[Math.round(yaw / 45f) & 0x7];
    }
    
    public static BlockFace yawToFace(float yaw) {
            return axis[Math.round(yaw / 90f) & 0x3];
    }
    
    public static BlockFace yawToFace(float yaw, float pitch) {
        if(pitch<-80){
        	return BlockFace.UP;
        }else if(pitch>80){
        	return BlockFace.DOWN;
        }
    	return axis[Math.round(yaw / 90f) & 0x3];
    }
    
    public static EulerAngle degresstoRad(EulerAngle degressAngle){
    	return new EulerAngle(degressAngle.getX() * Math.PI / 180, degressAngle.getY() * Math.PI / 180, degressAngle.getZ() * Math.PI / 180);
    }
    
    public static EulerAngle Radtodegress(EulerAngle degressAngle){
    	return new EulerAngle(degressAngle.getX() * 180 / Math.PI, degressAngle.getY() * 180 / Math.PI, degressAngle.getZ() * 180 / Math.PI);
    }
    
    public static int FaceToYaw(final BlockFace face) {
        switch (face) {
            case NORTH: return 0;
            case NORTH_EAST: return 45;
            case EAST: return 90;
            case SOUTH_EAST: return 135;
            case SOUTH: return 180;
            case SOUTH_WEST: return 225;
            case WEST: return 270;
            case NORTH_WEST: return 315;
            default: return 0;
        }
    }
    
    public static List<String> UUIDListToStringList(List<UUID> list){
    	if(list==null){return null;}
    	List<String> stringList = new ArrayList<String>();
    	for(UUID uuid : list){
    		stringList.add(uuid.toString());
    	}
    	return stringList;
    }
    
    public static List<UUID> StringListToUUIDList(List<String> list){
    	if(list==null){return null;}
       	List<UUID> stringList = new ArrayList<UUID>();
    	for(String s : list){
    		stringList.add(UUID.fromString(s));
    	}
    	return stringList;
    }
    
	public static List<UUID> replace(Entity e, List<UUID> uuid){
		if(e==null){return null;}
		if(e.getName()==null){return null;}
		if(e instanceof ArmorStand == false){return null;}
		if(e.getName().length()>=13){
			String[] split = e.getName().split("-");
			if(split != null && split.length>=1){
				if(!uuid.contains(e.getUniqueId())){
					uuid.add(e.getUniqueId());
					return uuid;
				}
			}
		}
		return uuid;
	}

    public static boolean day(World w) {
        long time = w.getTime();
     
        if(time > 0 && time < 12300) {
            return true;
        } else {
            return false;
        }
    }
    
    public static Vector getRelative(Vector v1, Vector v2){
    	return new Vector(v1.getX()-v2.getX(), v1.getY()-v2.getY(), v1.getZ()-v2.getZ());
    }
    
    public static Vector getRelativ(Vector v1, Double x, BlockFace bf){
    	//       N
    	//    W     E
    	//       S
    	switch(bf){
    	case NORTH: v1.add(new Vector(0, 0,x)); break;
    	/*case NORTH_EAST: 
    		v1.add(new Vector(0, 0, x)); 
    		v1.add(new Vector(-x, 0, 0));
    		break;
    	case NORTH_NORTH_EAST:
    		v1.add(new Vector(0, 0, x)); 
    		v1.add(new Vector(0, 0, x));
    		v1.add(new Vector(-x, 0, 0));
    		break;
    	case NORTH_NORTH_WEST:
    		v1.add(new Vector(0, 0, x)); 
    		v1.add(new Vector(0, 0, x));
    		v1.add(new Vector(x, 0, 0));
    		break;
    	case NORTH_WEST: 
    		v1.add(new Vector(0, 0, x));
    		v1.add(new Vector(x, 0, 0));
    		break;*/
    	case EAST: v1.add(new Vector(x, 0, 0)); break;
    	/*case EAST_NORTH_EAST:
    		v1.add(new Vector(-x, 0, 0));
    		v1.add(new Vector(0, 0, x));
    		v1.add(new Vector(-x, 0, 0));
    		break;
    	case EAST_SOUTH_EAST:
    		v1.add(new Vector(-x, 0, 0));
    		v1.add(new Vector(0, 0, -x));
    		v1.add(new Vector(-x, 0, 0));
    		break;*/
    	case SOUTH: v1.add(new Vector(0, 0,-x)); break;
    	/*case SOUTH_EAST:
    		v1.add(new Vector(0, 0, -x));
    		v1.add(new Vector(-x, 0, 0));
    		break;
    	case SOUTH_SOUTH_EAST:
    		v1.add(new Vector(0, 0, -x));
    		v1.add(new Vector(0, 0, -x));
    		v1.add(new Vector(-x, 0, 0));
    		break;
    	case SOUTH_SOUTH_WEST:
    		v1.add(new Vector(0, 0, -x));
    		v1.add(new Vector(0, 0, -x));
    		v1.add(new Vector(x, 0, 0));
    		break;
    	case SOUTH_WEST:
    		v1.add(new Vector(0, 0, -x));
    		v1.add(new Vector(x, 0, 0));
    		break;*/
    	case WEST: v1.add(new Vector(x, 0, 0)); break;
    	/*case WEST_NORTH_WEST:
    		v1.add(new Vector(x, 0, 0));
    		v1.add(new Vector(0, 0, x));
    		v1.add(new Vector(x, 0, 0));
    		break;
    	case WEST_SOUTH_WEST:
    		v1.add(new Vector(x, 0, 0));
    		v1.add(new Vector(0, 0, -x));
    		v1.add(new Vector(x, 0, 0));
    		break;*/
    	case DOWN:v1.add(new Vector(0, -x, 0));break;
    	case UP:v1.add(new Vector(0, x, 0));break;
		default: v1.add(new Vector(x, 0, 0)); break;
    	}
    	
    	return v1;
    }
    
    public static HeadArmType haTgetByString(String s){
    	if(s.equalsIgnoreCase("ARM")){return HeadArmType.ARM;}
    	if(s.equalsIgnoreCase("HEAD")){return HeadArmType.HEAD;}
		return HeadArmType.HEAD;
    }
    
    public static BlockFace StringToFace(final String face) {
        switch (face) {
            case "NORTH": return BlockFace.NORTH;
            case "EAST": return BlockFace.EAST;
            case "SOUTH": return BlockFace.SOUTH;
            case "WEST": return BlockFace.WEST;
            case "UP": return BlockFace.UP;
            case "DOWN": return BlockFace.DOWN;
            case "NORTH_NORTH_EAST": return BlockFace.NORTH_NORTH_EAST;
            case "NORTH_NORTH_WEST": return BlockFace.NORTH_NORTH_WEST;
            case "NORTH_WEST": return BlockFace.NORTH_WEST;
            case "EAST_NORTH_EAST": return BlockFace.EAST_NORTH_EAST;
            case "EAST_SOUTH_EAST": return BlockFace.EAST_SOUTH_EAST;
            case "SOUTH_EAST": return BlockFace.SOUTH_EAST;
            case "SOUTH_SOUTH_EAST": return BlockFace.SOUTH_SOUTH_EAST;
            case "SOUTH_SOUTH_WEST": return BlockFace.SOUTH_SOUTH_WEST;
            case "SOUTH_WEST": return BlockFace.SOUTH_WEST;
            case "WEST_NORTH_WEST": return BlockFace.WEST_NORTH_WEST;
            case "WEST_SOUTH_WEST": return BlockFace.WEST_SOUTH_WEST;
            default: return BlockFace.NORTH;
        }
    }
    
    @SuppressWarnings("deprecation")
    public static void setBed(BlockFace face, Location l) {
    	if(face == BlockFace.NORTH){
    		l.getBlock().setType(Material.AIR);
    		l.getBlock().setType(Material.BED_BLOCK);
    		Block block = l.getBlock();
            BlockState bedFoot = block.getState();
            BlockState bedHead = bedFoot.getBlock().getRelative(BlockFace.SOUTH).getState();
            bedFoot.setType(Material.BED_BLOCK);
            bedHead.setType(Material.BED_BLOCK);
            bedFoot.setRawData((byte) 0);
            bedHead.setRawData((byte) 8);
            bedFoot.update(true, false);
            bedHead.update(true, true);
    	}else if(face == BlockFace.EAST){
    		l.getBlock().setType(Material.AIR);
    		l.getBlock().setType(Material.BED_BLOCK);
    		Block block = l.getBlock();
    		BlockState bedFoot = block.getState();
            BlockState bedHead = bedFoot.getBlock().getRelative(BlockFace.WEST).getState();
            bedFoot.setType(Material.BED_BLOCK);
            bedHead.setType(Material.BED_BLOCK);
            bedFoot.setRawData((byte) 1);
            bedHead.setRawData((byte) 9);
            bedFoot.update(true, false);
            bedHead.update(true, true);
    	}else if(face == BlockFace.SOUTH){
    		l.getBlock().setType(Material.AIR);
    		l.getBlock().setType(Material.BED_BLOCK);
    		Block block = l.getBlock();
    		BlockState bedFoot = block.getState();
            BlockState bedHead = bedFoot.getBlock().getRelative(BlockFace.NORTH).getState();
            bedFoot.setType(Material.BED_BLOCK);
            bedHead.setType(Material.BED_BLOCK);
            bedFoot.setRawData((byte) 2);
            bedHead.setRawData((byte) 10);
            bedFoot.update(true, false);
            bedHead.update(true, true);
    	}else if(face == BlockFace.WEST){
    		l.getBlock().setType(Material.AIR);
    		l.getBlock().setType(Material.BED_BLOCK);
    		Block block = l.getBlock();
    		BlockState bedFoot = block.getState();
            BlockState bedHead = bedFoot.getBlock().getRelative(BlockFace.EAST).getState();
            bedFoot.setType(Material.BED_BLOCK);
            bedHead.setType(Material.BED_BLOCK);
            bedFoot.setRawData((byte) 3);
            bedHead.setRawData((byte) 11);
            bedFoot.update(true, false);
            bedHead.update(true, true);
    	}
    }
    
    @SuppressWarnings("deprecation")
    public static Block setHalfBed(BlockFace face, Location l) {
    	if(face == BlockFace.NORTH){
    		Block block = l.getBlock();
            BlockState bedHead = block.getState();
            bedHead.setType(Material.BED_BLOCK);
            bedHead.setRawData((byte) 9);
            bedHead.update(true, false);
            return block;
    	}else if(face == BlockFace.EAST){
    		Block block = l.getBlock();
    		BlockState bedHead = block.getState();
            bedHead.setType(Material.BED_BLOCK);
            bedHead.setRawData((byte) 10);
            bedHead.update(true, false);
            return block;
    	}else if(face == BlockFace.SOUTH){
    		Block block = l.getBlock();
    		BlockState bedHead = block.getState();
            bedHead.setType(Material.BED_BLOCK);
            bedHead.setRawData((byte) 11);
            bedHead.update(true, false);
            return block;
    	}else if(face == BlockFace.WEST){
    		Block block = l.getBlock();
    		BlockState bedHead = block.getState();
            bedHead.setType(Material.BED_BLOCK);
            bedHead.setRawData((byte) 8);
            bedHead.update(true, false);
            return block;
    	}
		return null;
    }

	public static Location getRelativ(Location loc, BlockFace b, Double z, Double x){
		Location l = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
		l.setYaw(Utils.FaceToYaw(b));
		switch (b) {
		case NORTH:
			l.add(x,0,z);
			break;
		case SOUTH:
			l.add(-x,0,-z);
			break;
		case WEST:
			l.add(z,0,-x);
			break;
		case EAST:
			l.add(-z,0,x);
			break;
		case NORTH_EAST:
			l.add(x,0,z);
			l.add(-z,0,x);
			break;
		case NORTH_NORTH_EAST:
			l.add(x,0,z);
			l.add(x,0,z);
			l.add(-z,0,x);
			break;
		case NORTH_NORTH_WEST:
			l.add(x,0,z);
			l.add(x,0,z);
			l.add(z,0,-x);
			break;
		case NORTH_WEST:
			l.add(x,0,z);
			l.add(z,0,-x);
			break;
		case EAST_NORTH_EAST:
			l.add(-z,0,x);
			l.add(x,0,z);
			l.add(-z,0,x);
			break;
		case EAST_SOUTH_EAST:
			l.add(-z,0,x);
			l.add(-x,0,-z);
			l.add(-z,0,x);
			break;
		case SOUTH_EAST:
			l.add(-x,0,-z);
			l.add(-z,0,x);
			break;
		case SOUTH_SOUTH_EAST:
			l.add(-x,0,-z);
			l.add(-x,0,-z);
			l.add(-z,0,x);
			break;
		case SOUTH_SOUTH_WEST:
			l.add(-x,0,-z);
			l.add(-x,0,-z);
			l.add(z,0,-x);
			break;
		case SOUTH_WEST:
			l.add(-x,0,-z);
			l.add(z,0,-x);
			break;
		case WEST_NORTH_WEST:
			l.add(z,0,-x);
			l.add(x,0,z);
			l.add(z,0,-x);
			break;
		case WEST_SOUTH_WEST:
			l.add(z,0,-x);
			l.add(-x,0,-z);
			l.add(z,0,-x);
			break;
		case DOWN:
			l.add(0,-z,0);
			break;
		case UP:
			l.add(0,z,0);
		default:
			l.add(x,0,z);
			break;
		}
		return l;
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
    
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static EulerAngle FaceEuler(final BlockFace face, Double x, Double y, Double z) {
    	return new EulerAngle(x,y,z);
    }
    
    public static Location getCenter(Location loc) {
        return new Location(loc.getWorld(),
            getRelativeCoord(loc.getBlockX()),
            getRelativeCoord(loc.getBlockY()),
            getRelativeCoord(loc.getBlockZ()));
    }
    
    public static Location getLocationCopy(Location l){
    	return new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
    }
     
    private static double getRelativeCoord(int i) {
        double d = i;
        if(d<0){d+=.5;}else{d+=.5;}
        return d;
    }
	
	public static ArmorStand setArmorStand(Location location, EulerAngle angle, ItemStack is, Boolean Arm, Boolean mini, Boolean invisible, String ID, List<UUID> iDList){
		ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
		if(Arm){
			if(angle!=null){as.setRightArmPose(angle);}
			if(is!=null){as.setItemInHand(is);}
		}else{
			if(angle!=null){as.setHeadPose(angle);}
			if(is!=null){as.setHelmet(is);}
		}
		as.setSmall(mini);
		as.setVisible(invisible);
		as.setGravity(false);
		as.setBasePlate(false);
		iDList.add(as.getUniqueId());
		return as;
	}
	
	public static ArmorStand check(Location location, EulerAngle angle, ItemStack is, Boolean arm, Boolean mini, Boolean invisible, List<UUID> iDList){
		Boolean hand = true;
		Boolean item = true;
		Boolean min = true;
		Boolean visible = true;
		for(Entity e : location.getWorld().getEntities()){
			if(e instanceof ArmorStand){
				if(e!=null){
					ArmorStand as = (ArmorStand) e;
					if(arm){
						if(angle != null && as.getRightArmPose()!=null && angle.equals(as.getRightArmPose())){hand = true;}else{hand=false;}
						if(is != null && as.getItemInHand() != null && as.getItemInHand().equals(is)){item = true;}else{item = false;}
					}else{
						if(angle != null && as.getHeadPose()!=null && angle.equals(as.getHeadPose())){hand = true;}else{hand=false;}
						if(is != null && as.getHelmet() != null && as.getHelmet().equals(is)){item = true;}else{item = false;}
					}
					if(as.isSmall() != mini){min = false;}
					if(as.isVisible() != invisible){visible = false;}
					if(hand&&item&&min&&visible){
						if(!iDList.contains(e.getUniqueId())){iDList.add(e.getUniqueId());}
						return as;
					}else if(location.equals(as.getLocation())){as.remove();return null;}
				}
			}
		}
		return null;
	}
	
	public static Integer getRound(float f){
		float circle = 360;
		return (int) (circle/f);
	}

	public static ArmorStand getArmorStandAtID(World w, UUID ids){
		for(Entity e : w.getEntities()){
			if(e instanceof ArmorStand){
				if(e!= null && e.getUniqueId().equals(ids)){
					return (ArmorStand) e;
				}
			}
		}
		return null;
	}

	  public static Boolean isDouble(String s){
		  try{
			  Double.parseDouble(s);
			  return true;
		  }catch(NumberFormatException e){
			  return false;
		  }
	  }
	  
	  public static Boolean isBoolean(String s){
		  try {
			  s = s.toLowerCase();
			  Boolean.parseBoolean(s);
			  return true;
		} catch (Exception e) {
			return false;
		}
	  }
	  
	  public static Boolean isInt(String s){
		  try{
			  Integer.parseInt(s);
			  return true;
		  }catch(NumberFormatException e){
			  return false;
		  }
	  }
}
