package de.Ste3et_C0st.Furniture.Objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;

public class laterne implements Listener {
	List<Entity> armorList = new ArrayList<Entity>();
	Block b;
	Location loc = null;
	public laterne(Location loc, Plugin plugin){
			this.loc = loc;
			Location center = Utils.getCenter(loc);
			b = center.getWorld().getBlockAt(center);
			b.setType(Material.TORCH);
			Location obsidian = center;
			Bukkit.getServer().broadcastMessage(obsidian.getX() + ";" + obsidian.getY() + ";" + obsidian.getZ());
			obsidian.add(0D, -2.2, 0D);
			ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(obsidian, EntityType.ARMOR_STAND);
			as.setBasePlate(false);
			as.setGravity(false);
			as.setVisible(false);
			as.setHelmet(new ItemStack(Material.OBSIDIAN));
			
			armorList.add(as);
			
			Location location = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
			as = (ArmorStand) loc.getWorld().spawnEntity(location.add(0,.75,0), EntityType.ARMOR_STAND);
			as.setBasePlate(false);
			as.setGravity(false);
			as.setVisible(false);
			as.setHelmet(new ItemStack(Material.WOOD_PLATE));
			
			armorList.add(as);
			
			//Links u
			location = new Location(obsidian.getWorld(), obsidian.getX(), obsidian.getY(), obsidian.getZ());
			as = (ArmorStand) loc.getWorld().spawnEntity(location.add(0.22,.62,0.22), EntityType.ARMOR_STAND);
			as.setBasePlate(false);
			as.setGravity(false);
			as.setVisible(false);
			as.setHelmet(new ItemStack(Material.LEVER));
			
			armorList.add(as);
			//Rechts o
			location = new Location(obsidian.getWorld(), obsidian.getX(), obsidian.getY(), obsidian.getZ());
			as = (ArmorStand) loc.getWorld().spawnEntity(location.add(-0.21,.62,-0.21), EntityType.ARMOR_STAND);
			as.setBasePlate(false);
			as.setGravity(false);
			as.setVisible(false);
			as.setHelmet(new ItemStack(Material.LEVER));
			
			armorList.add(as);
			
			//Rechts U
			location = new Location(obsidian.getWorld(), obsidian.getX(), obsidian.getY(), obsidian.getZ());
			as = (ArmorStand) loc.getWorld().spawnEntity(location.add(0.21,.62,-0.21), EntityType.ARMOR_STAND);
			as.setBasePlate(false);
			as.setGravity(false);
			as.setVisible(false);
			as.setHelmet(new ItemStack(Material.LEVER));
			
			armorList.add(as);
			
			//Links O
			location = new Location(obsidian.getWorld(), obsidian.getX(), obsidian.getY(), obsidian.getZ());
			as = (ArmorStand) loc.getWorld().spawnEntity(location.add(-0.21,.62,0.22), EntityType.ARMOR_STAND);
			as.setBasePlate(false);
			as.setGravity(false);
			as.setVisible(false);
			as.setHelmet(new ItemStack(Material.LEVER));
			
			armorList.add(as);
			Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
			main.getInstance().laternen.add(this);
	}
	
	public boolean check(Player player, Location loc){
		if(!loc.getBlock().equals(Material.AIR)){
			return false;
		}
		return true;
	}
	
	@EventHandler
	public void onWaterFlow(BlockFromToEvent e){
		Location locTo = e.getToBlock().getLocation();
		if(loc!=null && locTo.equals(loc.getBlock().getLocation())){
			e.setCancelled(true);
		}
	}
	
	/*
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
	*/
	
	public void delete(){
		armorList.get(0).getLocation().getWorld().dropItem(b.getLocation(), main.getInstance().itemse.Laterne);
		for(Entity entity : armorList){
			ArmorStand as = (ArmorStand) entity;
			entity.getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());
			entity.remove();
		}
		if(b!=null&&!b.getType().equals(Material.AIR)){b.setType(Material.AIR);}
		this.loc = null;
		armorList.clear();
		main.getInstance().laternen.remove(this);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e){
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand){
			if(armorList.contains(e.getRightClicked())){
				e.setCancelled(true);
				ItemStack is = player.getItemInHand();
				if(is!=null){
					if(is.getType().equals(Material.FLINT_AND_STEEL)){
						b.setType(Material.TORCH);
					}else if(is.getType().equals(Material.WATER_BUCKET)){
						b.setType(Material.REDSTONE_TORCH_OFF);
					}
				}
			}
		}
	}
	
	public Location getLocation(){
		return this.loc;
	}

	@EventHandler
	public void damage(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			if(e.getEntity() instanceof ArmorStand){
				if(armorList.contains(e.getEntity())){
					delete();
				}
			}
		}
	}
}
