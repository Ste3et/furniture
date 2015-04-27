package de.Ste3et_C0st.Furniture.Main;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    
    public static int FaceToYaw(final BlockFace face) {
        switch (face) {
            case NORTH: return 0;
            case EAST: return 90;
            case SOUTH: return 180;
            case WEST: return -90;
            default: return 0;
        }
    }
    
    public static boolean day(World w) {
        long time = w.getTime();
     
        if(time > 0 && time < 12300) {
            return true;
        } else {
            return false;
        }
    }
    
    public static BlockFace StringToFace(final String face) {
        switch (face) {
            case "NORTH": return BlockFace.NORTH;
            case "EAST": return BlockFace.EAST;
            case "SOUTH": return BlockFace.SOUTH;
            case "WEST": return BlockFace.WEST;
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
	
	public static ArmorStand setArmorStand(Location location, EulerAngle angle, ItemStack is, Boolean Arm, Boolean mini, Boolean invisible, String ID, List<String> idList){
		World w = location.getWorld();
		String id = ID+"-"+idList.size();
		for(Entity entity : w.getEntities()){
			if(entity instanceof ArmorStand){
				if(entity.getCustomName()!=null&&entity.getCustomName().equals(id)){
					idList.add(id);
					return (ArmorStand) entity;
				}
			}
		}
		
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
		as.setCustomName(id);
		idList.add(id);
		return as;
	}
	
	public static Integer getRound(float f){
		float circle = 360;
		return (int) (circle/f);
	}

	public static ArmorStand getArmorStandAtID(World w, String string){
		for(Entity e : w.getEntities()){
			if(e instanceof ArmorStand){
				if(e!= null && e.getCustomName()!=null && e.getCustomName().equalsIgnoreCase(string)){
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
	  
	  public static Boolean isInt(String s){
		  try{
			  Integer.parseInt(s);
			  return true;
		  }catch(NumberFormatException e){
			  return false;
		  }
	  }
}
