package de.Ste3et_C0st.Furniture.Camera.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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
						l.addPixel(new Pixel(width - z, heigt - y, getByteFromBlock(loc.getBlock())));
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
		Object obj = CraftBlockClass.cast(b);
		Object NMSBlock = CraftMagicNumbersClass.getDeclaredMethod("getBlock", org.bukkit.block.Block.class).invoke(null, obj);
		Object IBlockData = NMSBlock.getClass().getSuperclass().getMethod("getBlockData", null).invoke(NMSBlock);
		java.lang.reflect.Method m = IBlockData.getClass().getMethod("g", null);
		m.setAccessible(true);
		Object o = m.invoke(IBlockData);
        int baseColor = (int) o.getClass().getField("M").get(o);
        int color = baseColor * 4 + 0;
		return (byte) color;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
