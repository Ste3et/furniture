package de.Ste3et_C0st.Furniture.Camera.Utils.v1_14;

import org.apache.commons.lang.reflect.MethodUtils;
import org.bukkit.block.Block;

import de.Ste3et_C0st.Furniture.Camera.Utils.MinecraftBlockColor;

public class BlockColor extends MinecraftBlockColor{
	
	private static Class<?> CraftMagicNumbersClass;
    
	static {
		try {
			CraftMagicNumbersClass = Class.forName("org.bukkit.craftbukkit." + getBukkitVersion() + ".util.CraftMagicNumbers");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Byte getBlockColor(Block b) {
		try {
			Object nmsBlock = CraftMagicNumbersClass.getMethod("getBlock", org.bukkit.Material.class).invoke(null, b.getType());
			Object iBlockData = nmsBlock.getClass().getMethod("getBlockData").invoke(nmsBlock);
			Object Material = MethodUtils.invokeMethod(iBlockData, "getMaterial", null);
			Object MaterialMapColor = MethodUtils.invokeMethod(Material, "i", null);
			int color = MaterialMapColor.getClass().getField("ac").getInt(MaterialMapColor) * 4;
	        return (byte) color;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
