package de.Ste3et_C0st.Furniture.Objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
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
	Boolean bool = true;
	
	BlockFace bFace = null;
	
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.bFace;}
	public Boolean getBlockState(){return this.bool;}
	
	public laterne(Location loc, Plugin plugin, Boolean bool){
			this.loc = loc;
			this.bool = bool;
			Location center = Utils.getCenter(loc);
			b = center.getWorld().getBlockAt(center);
			if(bool){
				b.setType(Material.TORCH);
			}else{
				b.setType(Material.REDSTONE_TORCH_OFF);
			}
			
			Location obsidian = center;
			Location location = new Location(center.getWorld(), center.getX(), center.getY() -1.43, center.getZ());
			obsidian.add(0D, -2.2, 0D);
			Location left_down = new Location(obsidian.getWorld(), obsidian.getX()+0.22, obsidian.getY() + .62, obsidian.getZ()+0.22);
			Location left_upper = new Location(obsidian.getWorld(), obsidian.getX() -0.21, obsidian.getY() + .62, obsidian.getZ() +0.22);
			Location right_upper = new Location(obsidian.getWorld(), obsidian.getX()-0.21, obsidian.getY()+.62, obsidian.getZ()-0.21);
			Location right_down = new Location(obsidian.getWorld(), obsidian.getX()+0.21, obsidian.getY() + .62, obsidian.getZ() -0.21);
			
			Utils.setArmorStand(obsidian, null, new ItemStack(Material.OBSIDIAN), false, armorList, null);
			Utils.setArmorStand(location.add(0,0,0), null, new ItemStack(Material.WOOD_PLATE), false, armorList, null);
			
			Utils.setArmorStand(left_down, null, new ItemStack(Material.LEVER), false, armorList, null);
			Utils.setArmorStand(left_upper, null, new ItemStack(Material.LEVER), false, armorList, null);
			Utils.setArmorStand(right_upper, null, new ItemStack(Material.LEVER), false, armorList, null);
			Utils.setArmorStand(right_down, null, new ItemStack(Material.LEVER), false, armorList, null);
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
	
	public void delete(Boolean b){
		
		if(b){
			armorList.get(0).getLocation().getWorld().dropItem(this.b.getLocation(), main.getInstance().itemse.Laterne);
			for(Entity entity : armorList){
				ArmorStand as = (ArmorStand) entity;
				entity.getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());
				entity.remove();
			}
		}
		if(this.b!=null&&!this.b.getType().equals(Material.AIR)){this.b.setType(Material.AIR);}
		this.bool = null;
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
