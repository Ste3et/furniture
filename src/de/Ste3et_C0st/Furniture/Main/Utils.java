package de.Ste3et_C0st.Furniture.Main;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class Utils {

    public static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

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
    
    //NORDEN GEHT
    public static EulerAngle FaceEuler(final BlockFace face, Double x, Double y, Double z) {
    	return new EulerAngle(x,y,z);
    }
    
    public static Location getCenter(Location loc) {
        return new Location(loc.getWorld(),
            getRelativeCoord(loc.getBlockX()),
            getRelativeCoord(loc.getBlockY()),
            getRelativeCoord(loc.getBlockZ()));
    }
     
    private static double getRelativeCoord(int i) {
        double d = i;
        if(d<0){d+=.5;}else{d+=.5;}
        return d;
    }
    
	public static ArmorStand setArmorStand(Location location, EulerAngle angle, ItemStack is, Boolean Arm, List<Entity> entityList, List<Location> locationList){
		World w = location.getWorld();
		
		for(Entity entity : w.getEntities()){
			if(entity instanceof ArmorStand){
				if(location.equals(entity.getLocation())){
					entityList.add(entity);
					if(locationList!=null){locationList.add(location);}
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
		as.setVisible(false);
		as.setGravity(false);
		as.setBasePlate(false);
		entityList.add(as);
		if(locationList!=null){locationList.add(location);}
		return as;
	}
}
