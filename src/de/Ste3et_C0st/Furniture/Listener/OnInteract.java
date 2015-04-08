package de.Ste3et_C0st.Furniture.Listener;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.Furniture.Objects.largeTable;
import de.Ste3et_C0st.Furniture.Objects.laterne;
import de.Ste3et_C0st.Furniture.Objects.sofa;
import de.Ste3et_C0st.Furniture.Objects.stuhl;
import de.Ste3et_C0st.Furniture.Objects.tisch;

public class OnInteract implements Listener {

	@EventHandler
	public void onInterActEvent(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getItem() == null){return;}
		if(e.getClickedBlock() == null){return;}
		ItemStack is = e.getItem();
		if(!is.hasItemMeta()){return;}
		if(!is.getType().equals(Material.MONSTER_EGG)){return;}
		if(!is.getItemMeta().hasDisplayName()){return;}
		if(e.getClickedBlock()==null){return;}
		if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){return;}
		Integer hand = p.getInventory().getHeldItemSlot();
		Integer amount = is.getAmount();
		Location location = new Location(p.getWorld(), e.getClickedBlock().getLocation().getBlockX(), e.getClickedBlock().getLocation().getBlockY(), e.getClickedBlock().getLocation().getBlockZ());
		location.setYaw(p.getLocation().getYaw());
		location.setY(location.getY() + 1);
		if(ChatColor.stripColor(is.getItemMeta().getDisplayName()).equalsIgnoreCase("sofa")){
			if(main.getInstance().canPlace(p, location, Utils.yawToFace(location.getYaw()).getOppositeFace(), 3)){
				new sofa(location, 3, main.getInstance());
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}
		}
		if(ChatColor.stripColor(is.getItemMeta().getDisplayName()).equalsIgnoreCase("laterne")){
			if(main.getInstance().canPlace(p, location, null, null)){
				new laterne(location, main.getInstance(), true);
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}
		}
		if(ChatColor.stripColor(is.getItemMeta().getDisplayName()).equalsIgnoreCase("stuhl")){
			if(main.getInstance().canPlace(p, location, null, null)){
				new stuhl(location, main.getInstance());
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}
		}
		if(ChatColor.stripColor(is.getItemMeta().getDisplayName()).equalsIgnoreCase("tisch")){
			if(main.getInstance().canPlace(p, location, null, null)){
				new tisch(location, main.getInstance(), null);
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}
		}
		
		if(ChatColor.stripColor(is.getItemMeta().getDisplayName()).equalsIgnoreCase("tisch2")){
			if(main.getInstance().canPlace(p, location, null, null)){
				new largeTable(location, main.getInstance(), null, null);
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}
		}
	}
}
