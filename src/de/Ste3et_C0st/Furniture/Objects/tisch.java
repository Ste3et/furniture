package de.Ste3et_C0st.Furniture.Objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;

public class tisch implements Listener {

	List<Entity> armorList = new ArrayList<Entity>();
	ArmorStand armor;
	ItemStack is;
	Location loc = null;
	public tisch(Location loc, Plugin plugin){
		Location middle1 = Utils.getCenter(loc);
		Location middle2 = Utils.getCenter(loc);
		this.loc = middle1.getBlock().getLocation().add(1,0,0);
			ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(middle1.add(1,-2.1,0), EntityType.ARMOR_STAND);
			as.setHelmet(new ItemStack(Material.WOOD_PLATE));
			as.setVisible(false);
			as.setGravity(false);
			armorList.add(as);
			as = (ArmorStand) loc.getWorld().spawnEntity(middle2.add(1,-1.05,0), EntityType.ARMOR_STAND);
			as.setHelmet(new ItemStack(Material.TRAP_DOOR));
			as.setVisible(false);
			as.setGravity(false);
			armorList.add(as);
			loc.setYaw(0);
			Location location = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
			as = (ArmorStand) loc.getWorld().spawnEntity(location.add(.9,0.15,0.3), EntityType.ARMOR_STAND);
			as.setRightArmPose(new EulerAngle(0,.0,.0));
			as.setVisible(false);
			as.setGravity(false);
			armor = as;
			armorList.add(as);
			loc.setYaw(0);
			as = (ArmorStand) loc.getWorld().spawnEntity(loc.add(.9,-.5,1.02), EntityType.ARMOR_STAND);
			as.setItemInHand(new ItemStack(Material.STICK));
			as.setRightArmPose(new EulerAngle(1.38,.0,.0));
			as.setVisible(false);
			as.setGravity(false);
			armorList.add(as);
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
			main.getInstance().tische.add(this);
	}
	
	public Location getLocation(){
		return this.loc;
	}
	
	
	@EventHandler
	public void onWaterFlow(BlockFromToEvent e){
		Location locTo = e.getToBlock().getLocation();
		if(loc!=null && locTo.equals(loc)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onHit(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(!p.isInsideVehicle() && loc!=null){
			if(e.getTo().getBlock().getLocation().equals(loc.getBlock().getLocation()) ||
			   e.getTo().getBlock().getLocation().equals(loc.getBlock().getLocation().add(0,1,0))){
				p.teleport(e.getFrom());
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e){
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand){
			if(armorList.contains(e.getRightClicked())){
				e.setCancelled(true);
				ItemStack is = player.getItemInHand();
				if(is!=null){
					if(armor!=null){
						ArmorStand as = (ArmorStand) armorList.get(2);
						if(armor.getItemInHand()!=null&&!armor.getItemInHand().getType().equals(Material.AIR)){as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());}
						as.setItemInHand(is);
						this.is = is;
					}
				}
			}
		}
	}
	
	public void delete(Boolean b){
		if(is!=null&&b){
			if(!is.getType().equals(Material.AIR)){
				ArmorStand as = (ArmorStand) armorList.get(2);
				as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());
				this.is = null;
			}
		}
		armorList.get(0).getLocation().getWorld().dropItem(armorList.get(0).getLocation().getBlock().getLocation().add(0, 2, 0), main.getInstance().itemse.tisch);
		for(Entity entity : armorList){
			ArmorStand as = (ArmorStand) entity;
			entity.getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());
			entity.remove();
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
