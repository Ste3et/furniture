package de.Ste3et_C0st.Furniture.Main;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
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
}
