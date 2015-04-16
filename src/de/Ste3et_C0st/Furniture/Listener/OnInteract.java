package de.Ste3et_C0st.Furniture.Listener;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.Furniture.Objects.indoor.chair;
import de.Ste3et_C0st.Furniture.Objects.indoor.largeTable;
import de.Ste3et_C0st.Furniture.Objects.indoor.latern;
import de.Ste3et_C0st.Furniture.Objects.indoor.sofa;
import de.Ste3et_C0st.Furniture.Objects.indoor.table;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_1;

public class OnInteract implements Listener {

	@EventHandler
	public void onInterActEvent(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getItem() == null){return;}
		if(e.getClickedBlock() == null){return;}
		if(e.getClickedBlock()==null){return;}
		if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){return;}
		ItemStack is = e.getItem();
		if(!(is!=null&&is.hasItemMeta()&&is.getItemMeta().hasDisplayName())){return;}
		
		Integer hand = p.getInventory().getHeldItemSlot();
		Integer amount = is.getAmount();
		Location location = new Location(p.getWorld(), e.getClickedBlock().getLocation().getBlockX(), e.getClickedBlock().getLocation().getBlockY(), e.getClickedBlock().getLocation().getBlockZ());
		location.setYaw(p.getLocation().getYaw());
		location.setY(location.getY() + 1);
		ItemStack saveIS = getItemStackCopy(is);
		String noPermissions = ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.NoPermissions"));
		if(saveIS.equals(main.getInstance().crafting.get("sofa"))){
			if(!p.hasPermission("furniture.sofa")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, Utils.yawToFace(location.getYaw()).getOppositeFace(), 3)){
				new sofa(location, 3, main.getInstance(), null, main.createRandomRegistryId());
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}
		}

		if(saveIS.equals(main.getInstance().crafting.get("lantern"))){
			if(!p.hasPermission("furniture.lantern")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, null, null)){
				new latern(location, main.getInstance(), true, main.createRandomRegistryId());
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("chair"))){
			if(!p.hasPermission("furniture.chair")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, null, null)){
				new chair(location, main.getInstance(), main.createRandomRegistryId());
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("table"))){
			if(!p.hasPermission("furniture.table")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, null, null)){
				new table(location, main.getInstance(), null, main.createRandomRegistryId());
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("largeTable"))){
			if(!p.hasPermission("furniture.largetable")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlaceLarge(p, location, Utils.yawToFace(location.getYaw()).getOppositeFace(), 3,3)){
				new largeTable(location, main.getInstance(), null, null, main.createRandomRegistryId());
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("tent1"))){
			if(!p.hasPermission("furniture.tent1")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlaceTent(p, location, Utils.yawToFace(location.getYaw()).getOppositeFace(), 5,4, 3)){
				new tent_1(location, main.getInstance(), main.createRandomRegistryId());
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}
		}
	}
	
	public ItemStack getItemStackCopy(ItemStack is){
		ItemStack copy = new ItemStack(is.getType());
		copy.setAmount(1);
		copy.setDurability(is.getDurability());
		copy.setItemMeta(is.getItemMeta());
		return copy;
	}
}
