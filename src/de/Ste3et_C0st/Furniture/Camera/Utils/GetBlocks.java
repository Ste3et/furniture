package de.Ste3et_C0st.Furniture.Camera.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.reflect.MethodUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.entity.Relative;

public class GetBlocks {

	Class<?> CraftBlockClass = null;
	Class<?> CraftMagicNumbersClass = null;
	
	public List<Layer> returnBlocks(Location startLocation, int width, int heigt, int depth, int offsetZ){
		try{
			String str = FurnitureLib.getInstance().getBukkitVersion();
			this.CraftBlockClass = Class.forName("org.bukkit.craftbukkit."+str+".block.CraftBlock");
			this.CraftMagicNumbersClass = Class.forName("org.bukkit.craftbukkit."+str+".util.CraftMagicNumbers");
			
			BlockFace face = main.getLocationUtil().yawToFace(startLocation.getYaw()).getOppositeFace();
			List<Layer> layerList = new ArrayList<Layer>();
			for(int i = depth; i>=0; i--){
				Layer l = new Layer(i);
				Location start = new Relative(startLocation, - offsetZ - i, 0, (width/2), face).getSecondLocation();
				for(int z = 0; z < width; z++){
					for(int y = 0; y < heigt; y++){
						Location loc = new Relative(start, 0, y, - z, face).getSecondLocation();
						Byte b = getByteFromBlock(loc.getBlock());
						l.addPixel(new Pixel(width - z, heigt - y, b));
					}
				}
				layerList.add(l);
			}
			return layerList;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public Byte getByteFromBlock(Block b){
		try {
			Object o = CraftMagicNumbersClass.getMethod("getBlock", org.bukkit.block.Block.class).invoke(null, b);
			Object iBlockData = o.getClass().getMethod("getBlockData").invoke(o);
			Object Material = MethodUtils.invokeMethod(iBlockData, "getMaterial", null);
			Object MaterialMapColor = MethodUtils.invokeMethod(Material, "r", null);
			int color = MaterialMapColor.getClass().getField("ad").getInt(MaterialMapColor) * 4;
//		        if(color == 28){
//		        color += randInt(0, 3);
//		     }
			return (byte) color;
		} catch (Exception e) {
			//e.printStackTrace();
			return 0;
		}
	}
	
	public static int randInt(int min, int max) {
	    int randomNum = new Random().nextInt((max - min) + 1) + min;
	    return randomNum;
	}
}
