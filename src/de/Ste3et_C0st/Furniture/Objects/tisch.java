package de.Ste3et_C0st.Furniture.Objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;

public class tisch implements Listener {

	List<Entity> armorList = new ArrayList<Entity>();
	ArmorStand armor;
	ItemStack is = null;
	Location loc = null;
	BlockFace b = null;
	
	public Location getLocation(){return this.loc;}
	public ItemStack getItemStack(){return this.is;}
	public BlockFace getBlockFace(){return this.b;}
	
	public tisch(Location loc, Plugin plugin, ItemStack is){
		this.loc = loc;
		this.b = Utils.yawToFace(loc.getYaw());
		Location middle1 = Utils.getCenter(loc);
		Location middle2 = Utils.getCenter(loc);
		Utils.setArmorStand(middle1.add(0,-2.1,0),new EulerAngle(0, 0, 0) , new ItemStack(Material.WOOD_PLATE), false, armorList, null);
		Utils.setArmorStand(middle2.add(0,-1.05,0),new EulerAngle(0, 0, 0) , new ItemStack(Material.TRAP_DOOR), false, armorList, null);	
		loc.setYaw(0);
		ArmorStand as = Utils.setArmorStand(loc.add(.9,0.15,0.3),new EulerAngle(0,.0,.0), is, true, armorList, null);	
		Utils.setArmorStand(loc.add(0,-.65,.68),new EulerAngle(1.38,.0,.0), new ItemStack(Material.STICK), true, armorList, null);		
		this.armor = as;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main.getInstance().tische.add(this);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e){
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand){
			if(armorList.contains(e.getRightClicked())){
				e.setCancelled(true);
				ItemStack is = player.getItemInHand();
				if(is!=null&&!is.getType().isBlock()){
					if(armor!=null){
						ArmorStand as = (ArmorStand) armorList.get(2);
						if(armor.getItemInHand()!=null&&!armor.getItemInHand().getType().equals(Material.AIR)){as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());}
						as.setItemInHand(is);
						this.is = is;
						
						if(!player.getGameMode().equals(GameMode.CREATIVE)){player.getInventory().remove(is);player.updateInventory();}
					}
				}
			}
		}
	}
	
	public void delete(Boolean b){
		if(b){
			armorList.get(0).getLocation().getWorld().dropItem(armorList.get(0).getLocation().getBlock().getLocation().add(0, 2, 0), main.getInstance().itemse.tisch);
			if(is!=null && !is.getType().equals(Material.AIR)){
				ArmorStand as = (ArmorStand) armorList.get(2);
				as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());
				this.is = null;
			}
			for(Entity entity : armorList){
				ArmorStand as = (ArmorStand) entity;
				entity.getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());
				entity.remove();
			}
		}

		this.loc = null;
		armorList.clear();
		main.getInstance().tische.remove(this);
	}

	@EventHandler
	public void damage(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			if(e.getEntity() instanceof ArmorStand){
				if(armorList.contains(e.getEntity())){
					delete(true);
				}
			}
		}
	}
}
