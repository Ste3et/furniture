package de.Ste3et_C0st.Furniture.Camera.Utils.v1_9;

import de.Ste3et_C0st.Furniture.Camera.Utils.MinecraftBlockColor;
import org.bukkit.block.Block;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BlockColor extends MinecraftBlockColor {

    private static Class<?> iBlockDataClazz, BlockClazz, MaterialMapColorClazz;

    static {
        try {
            BlockClazz = Class.forName("net.minecraft.server." + getBukkitVersion() + ".Block");
            iBlockDataClazz = Class.forName("net.minecraft.server." + getBukkitVersion() + ".IBlockData");
            MaterialMapColorClazz = Class.forName("net.minecraft.server." + getBukkitVersion() + ".MaterialMapColor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Byte getBlockColor(Block b) {
        try {
            int combined = b.getType().getId() + (b.getData() << 12);

            Method iBlockDataMethod = BlockClazz.getDeclaredMethod("getByCombinedId", int.class);
            Object iblockData = iBlockDataMethod.invoke(null, combined);

            Method getBlockMethod = iBlockDataClazz.getMethod("getBlock");
            Object nmsBlock = getBlockMethod.invoke(iblockData);

            Field BlockFieldy = BlockClazz.getDeclaredField("y");
            BlockFieldy.setAccessible(true);
            Object materialMapColor = BlockFieldy.get(nmsBlock);

            Field MaterialMapColorFieldM = MaterialMapColorClazz.getDeclaredField("M");
            MaterialMapColorFieldM.setAccessible(true);
            int color = MaterialMapColorFieldM.getInt(materialMapColor) * 4;

            return (byte) color;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
