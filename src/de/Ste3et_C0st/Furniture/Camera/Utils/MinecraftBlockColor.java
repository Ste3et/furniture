package de.Ste3et_C0st.Furniture.Camera.Utils;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public abstract class MinecraftBlockColor {

    public static String getBukkitVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public static String getMainVersion() {
        return "v1_" + getBukkitVersion().split("_")[1];
    }

    public abstract Byte getBlockColor(Block b);

}
