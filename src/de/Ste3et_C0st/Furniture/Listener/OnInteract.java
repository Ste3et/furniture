package de.Ste3et_C0st.Furniture.Listener;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.Furniture.Main.Manager.BlackList;
import de.Ste3et_C0st.Furniture.Objects.indoor.chair;
import de.Ste3et_C0st.Furniture.Objects.indoor.largeTable;
import de.Ste3et_C0st.Furniture.Objects.indoor.latern;
import de.Ste3et_C0st.Furniture.Objects.indoor.sofa;
import de.Ste3et_C0st.Furniture.Objects.indoor.table;
import de.Ste3et_C0st.Furniture.Objects.outdoor.barrels;
import de.Ste3et_C0st.Furniture.Objects.outdoor.campfire_1;
import de.Ste3et_C0st.Furniture.Objects.outdoor.campfire_2;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_1;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_2;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_3;

public class OnInteract implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void onCrafting(PrepareItemCraftEvent e){
		Player p = (Player) e.getView().getPlayer();
		CraftingInventory inv = e.getInventory();
		if(!main.getInstance().isCrafting){return;}
		if(main.getInstance().crafting.containsValue(e.getRecipe().getResult())){
			if(getName(e.getRecipe().getResult()) != null){
				
				if(!p.hasPermission("furniture.craft." + getName(e.getRecipe().getResult()))){
					inv.setResult(null);
					return;
				}
			}
		}
		
	}

	public String getName(ItemStack is){
		for(String s : main.getInstance().crafting.keySet()){
			if(main.getInstance().crafting.get(s).equals(is)){
				return s;
			}
		}
		return null;
	}
	
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
		if(!main.getInstance().check.canBuild(p, location)){return;}
		if(saveIS.equals(main.getInstance().crafting.get("sofa"))){
			if(!p.hasPermission("furniture.sofa")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, Utils.yawToFace(location.getYaw()).getOppositeFace(), 3)){
				sofa s = new sofa(location, main.getInstance(), main.createRandomRegistryId());
				s.save();
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}

		if(!main.getInstance().getCheckManager().canBuild(p, location)){
			return;
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("lantern"))){
			if(location.getBlock().getRelative(BlockFace.DOWN).getType() != null && BlackList.materialBlackList.contains(location.getBlock().getRelative(BlockFace.DOWN).getType())){return;}
			if(!p.hasPermission("furniture.lantern")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, null, null)){
				new latern(location, main.getInstance(), main.createRandomRegistryId());
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("chair"))){
			if(!p.hasPermission("furniture.chair")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, null, null)){
				chair c = new chair(location, main.getInstance(), main.createRandomRegistryId());
				c.save();
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("table"))){
			if(!p.hasPermission("furniture.table")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, null, null)){
				table t = new table(location, main.getInstance(), main.createRandomRegistryId());
				t.save();
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("largeTable"))){
			if(!p.hasPermission("furniture.largetable")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlaceLarge(p, location, Utils.yawToFace(location.getYaw()).getOppositeFace(), 3,3)){
				largeTable l = new largeTable(location, main.getInstance(), main.createRandomRegistryId());
				l.save();
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("tent1"))){
			if(!p.hasPermission("furniture.tent1")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlaceTent(p, location, Utils.yawToFace(location.getYaw()).getOppositeFace(), 5,4, 3)){
				tent_1 tent = new tent_1(location, main.getInstance(), main.createRandomRegistryId());
				tent.save();
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("tent2"))){
			if(!p.hasPermission("furniture.tent2")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlaceTent(p, location, Utils.yawToFace(location.getYaw()).getOppositeFace(), 6,4, 3)){
				tent_2 tent = new tent_2(location, main.getInstance(), main.createRandomRegistryId());
				tent.save();
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("campfire1"))){
			if(!p.hasPermission("furniture.campfire1")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, null, null)){
				campfire_1 tent = new campfire_1(location, main.getInstance(), main.createRandomRegistryId());
				tent.save();
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("tent3"))){
			if(!p.hasPermission("furniture.tent3")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlaceTent(p, location, Utils.yawToFace(location.getYaw()).getOppositeFace(), 3,4, 2)){
				tent_3 tent = new tent_3(location, main.getInstance(), main.createRandomRegistryId());
				tent.save();
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("barrels"))){
			if(!p.hasPermission("furniture.barrels")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location,null,null)){
				barrels tent = new barrels(location, main.getInstance(), main.createRandomRegistryId());
				tent.save();
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("campfire2"))){
			if(!p.hasPermission("furniture.campfire2")){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlaceLarge(p, location, Utils.yawToFace(location.getYaw()), 2,2)){
				campfire_2 tent = new campfire_2(location, main.getInstance(), main.createRandomRegistryId());
				tent.save();
				if(!p.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(amount-1);
					p.getInventory().setItem(hand, is);
					p.updateInventory();
				}
				return;
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
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
