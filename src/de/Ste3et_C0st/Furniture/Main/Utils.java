package de.Ste3et_C0st.Furniture.Main;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class Utils {

    public static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

    public static BlockFace yawToFace(float yaw) {
            return axis[Math.round(yaw / 90f) & 0x3];
    }
    
    public static Float FaceToYaw(BlockFace face) {
    	Float yaw = 0F;
        if(face.equals(BlockFace.NORTH)){yaw = 0f;}
        if(face.equals(BlockFace.WEST)){yaw = 270f;}
        if(face.equals(BlockFace.SOUTH)){yaw = 180f;}
        if(face.equals(BlockFace.EAST)){yaw = 90f;}
        return yaw;
    }
    
    public static Location getCenter(Location loc) {
        return new Location(loc.getWorld(),
            getRelativeCoord(loc.getBlockX()),
            getRelativeCoord(loc.getBlockY()),
            getRelativeCoord(loc.getBlockZ()));
    }
     
    private static double getRelativeCoord(int i) {
        double d = i;
        d = d < 0 ? d - .5 : d + .5;
        return d;
    }
}
