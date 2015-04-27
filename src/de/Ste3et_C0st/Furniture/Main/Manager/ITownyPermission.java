package de.Ste3et_C0st.Furniture.Main.Manager;

import org.bukkit.entity.Player;

public enum ITownyPermission
{
  PROTECTION_BYPASS("Furniture.towny.bypass");
  
  private final String permission;
  
  private ITownyPermission(String permission)
  {
    this.permission = permission;
  }
  
  public static boolean has(Player player, ITownyPermission permission)
  {
    return has(player, permission.permission);
  }
  
  public static boolean has(Player player, String node)
  {
    return (player.hasPermission(node)) || (player.hasPermission(node.toLowerCase()));
  }
  
  @SuppressWarnings("unused")
private static boolean hasPermissionSet(Player p, String perm)
  {
    return (p.isPermissionSet(perm)) && (p.hasPermission(perm));
  }
  
  public String toString()
  {
    return this.permission;
  }
}