package de.Ste3et_C0st.Furniture.Camera.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Utilitis.Relative;

public class GetBlocks {

	public static MinecraftBlockColor colorBlock;
	
	static {
		try {
			Class<?> color = Class.forName("de.Ste3et_C0st.Furniture.Camera.Utils." + MinecraftBlockColor.getMainVersion() + ".BlockColor");
			if(Objects.nonNull(color)) {
				colorBlock = (MinecraftBlockColor) color.newInstance();
			}else {
				colorBlock = de.Ste3et_C0st.Furniture.Camera.Utils.v1_13.BlockColor.class.newInstance();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getBukkitVersion() {return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];}
	
	public List<Layer> returnBlocks(Location startLocation, int width, int heigt, int depth, int offsetZ){
		try{
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
			return colorBlock.getBlockColor(b);
//			Object nmsBlock = CraftMagicNumbersClass.getMethod("getBlock", org.bukkit.Material.class).invoke(null, b.getType());
//			Object iBlockData = nmsBlock.getClass().getMethod("getBlockData").invoke(nmsBlock);
//			Object Material = MethodUtils.invokeMethod(iBlockData, "getMaterial", null);
//			Object MaterialMapColor = MethodUtils.invokeMethod(Material, "i", null);
//			int color = MaterialMapColor.getClass().getField("ac").getInt(MaterialMapColor) * 4;
////		        if(color == 28){
////		        color += randInt(0, 3);
////		     }
//			return (byte) color;
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
