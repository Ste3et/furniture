package de.Ste3et_C0st.Furniture.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.Permissions;
import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.Furniture.Main.Manager.BlackList;
import de.Ste3et_C0st.Furniture.Main.Type.FurnitureType;
import de.Ste3et_C0st.Furniture.Objects.electric.camera;
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
				if((!p.hasPermission("furniture.craft." + getName(e.getRecipe().getResult()))) && (!p.hasPermission("furniture.player")) ){
					inv.setResult(null);
					return;
				}
			}
		}
		
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onDeath(EntityDeathEvent e){
		if(e == null){return;}
		if(e instanceof ArmorStand){
			if(((ArmorStand) e).getName()==null){return;}
			String name = ((ArmorStand) e).getName();
			if(name != null && name.length()>=13){
				String[] split = name.split("-");
				if(split != null && split.length>=1){
					ArmorStand as = (ArmorStand) e.getEntity();
					ArmorStand armorStand = (ArmorStand) ((ArmorStand) e).getWorld().spawnEntity(as.getLocation(), EntityType.ARMOR_STAND);
					if(as.getHelmet()!=null){armorStand.setHelmet(as.getHelmet());}
					if(as.getItemInHand()!=null){armorStand.setItemInHand(as.getItemInHand());}
					if(as.getRightArmPose()!=null){armorStand.setRightArmPose(as.getRightArmPose());}
					if(as.getLeftArmPose()!=null){armorStand.setLeftArmPose(as.getLeftArmPose());}
					if(as.getBodyPose()!=null){armorStand.setBodyPose(as.getBodyPose());}
					if(as.getLeftLegPose()!=null){armorStand.setLeftLegPose(as.getLeftLegPose());}
					if(as.getRightLegPose()!=null){armorStand.setRightArmPose(as.getRightArmPose());}
					if(as.isSmall()){armorStand.setSmall(true);}
					if(as.isVisible()){armorStand.setVisible(true);}
					if(as.getVelocity()!=null){armorStand.setVelocity(as.getVelocity());}
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
		if(!main.getInstance().getCheckManager().canBuild(p, location)){return;}
		if(location.getBlock().getRelative(BlockFace.DOWN).getType() != null && BlackList.materialBlackList.contains(location.getBlock().getRelative(BlockFace.DOWN).getType())){return;}
		List<UUID> emptyList = new ArrayList<UUID>();
		if(!main.getInstance().crafting.containsValue(saveIS)){return;}
		if(saveIS.equals(main.getInstance().crafting.get("sofa"))){
			if(!Permissions.check(p, FurnitureType.SOFA, null)){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, Utils.yawToFace(location.getYaw()).getOppositeFace(), 3)){
				sofa s = new sofa(location, main.getInstance(), main.createRandomRegistryId(), emptyList);
				s.save();
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("lantern"))){
			if(!Permissions.check(p, FurnitureType.LANTERN, null)){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, null, null)){
				new latern(location, main.getInstance(), main.createRandomRegistryId(), emptyList);
				p.playEffect(location, Effect.STEP_SOUND, Material.TORCH);
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("chair"))){
			if(!Permissions.check(p, FurnitureType.CHAIR, null)){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, null, null)){
				chair c = new chair(location, main.getInstance(), main.createRandomRegistryId(), emptyList);
				p.playEffect(location, Effect.STEP_SOUND, Material.WOOD);
				c.save();
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("table"))){
			if(!Permissions.check(p, FurnitureType.TABLE, null)){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, null, null)){
				table t = new table(location, main.getInstance(), main.createRandomRegistryId(), emptyList);
				p.playEffect(location, Effect.STEP_SOUND, Material.WOOD);
				t.save();
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("largeTable"))){
			if(!Permissions.check(p, FurnitureType.LARGE_TABLE, null)){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlaceLarge(p, location, Utils.yawToFace(location.getYaw()).getOppositeFace(), 3,3)){
				largeTable l = new largeTable(location, main.getInstance(), main.createRandomRegistryId(), emptyList);
				p.playEffect(location, Effect.STEP_SOUND, Material.GLASS);
				l.save();
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("tent1"))){
			if(!Permissions.check(p, FurnitureType.TENT_1, null)){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlaceTent(p, location, Utils.yawToFace(location.getYaw()).getOppositeFace(), 5,4, 3)){
				tent_1 tent = new tent_1(location, main.getInstance(), main.createRandomRegistryId(), emptyList);
				tent.save();
				p.playEffect(location, Effect.STEP_SOUND, Material.WOOL);
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("tent2"))){
			if(!Permissions.check(p, FurnitureType.TENT_2, null)){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlaceTent(p, location, Utils.yawToFace(location.getYaw()).getOppositeFace(), 6,4, 3)){
				tent_2 tent = new tent_2(location, main.getInstance(), main.createRandomRegistryId(), emptyList);
				tent.save();
				p.playEffect(location, Effect.STEP_SOUND, Material.WOOL);
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("campfire1"))){
			if(!Permissions.check(p, FurnitureType.CAMPFIRE_1, null)){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, null, null)){
				campfire_1 tent = new campfire_1(location, main.getInstance(), main.createRandomRegistryId(), emptyList);
				tent.save();
				p.playEffect(location, Effect.STEP_SOUND, Material.WOOD);
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("tent3"))){
			if(!Permissions.check(p, FurnitureType.TENT_3, null)){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlaceTent(p, location, Utils.yawToFace(location.getYaw()).getOppositeFace(), 3,4, 2)){
				tent_3 tent = new tent_3(location, main.getInstance(), main.createRandomRegistryId(), emptyList);
				tent.save();
				p.playEffect(location, Effect.STEP_SOUND, Material.WOOL);
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("barrels"))){
			if(!Permissions.check(p, FurnitureType.BARRELS, null)){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location,null,null)){
				barrels tent = new barrels(location, main.getInstance(), main.createRandomRegistryId(), emptyList);
				tent.save();
				p.playEffect(location, Effect.STEP_SOUND, Material.CAULDRON);
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("campfire2"))){
			if(!Permissions.check(p, FurnitureType.CAMPFIRE_2, null)){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlaceLarge(p, location, Utils.yawToFace(location.getYaw()), 2,2)){
				campfire_2 tent = new campfire_2(location, main.getInstance(), main.createRandomRegistryId(), emptyList);
				tent.save();
				p.playEffect(location, Effect.STEP_SOUND, Material.WOOD);
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}
		
		if(saveIS.equals(main.getInstance().crafting.get("camera"))){
			if(!Permissions.check(p, FurnitureType.CAMERA, null)){p.sendMessage(noPermissions);return;}
			if(main.getInstance().canPlace(p, location, null, null)){
				camera tent = new camera(location, main.getInstance(), main.createRandomRegistryId(), emptyList);
				tent.save();
				p.playEffect(location, Effect.STEP_SOUND, Material.WOOD);
			}else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getInstance().getConfig().getString("config.Messages.Space")));
				return;
			}
		}

		if(!p.getGameMode().equals(GameMode.CREATIVE)){
			is.setAmount(amount-1);
			p.getInventory().setItem(hand, is);
			p.updateInventory();
		}
		return;
	}
	
	public ItemStack getItemStackCopy(ItemStack is){
		ItemStack copy = new ItemStack(is.getType());
		copy.setAmount(1);
		copy.setDurability(is.getDurability());
		copy.setItemMeta(is.getItemMeta());
		return copy;
	}
}
