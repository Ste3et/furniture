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

public class largeTable implements Listener {

	List<Entity> armorList = new ArrayList<Entity>();
	List<Location> location = new ArrayList<Location>();
	public largeTable(Location loc, Plugin plugin){
		BlockFace b = Utils.yawToFace(loc.getYaw());
		Location location = Utils.getCenter(loc.getBlock().getLocation());
		plugin.getServer().broadcastMessage(b.name());
		
		//East WORK
		//SOUT Z WORK
		location = main.getNew(location, b, 0.0, 0.0);
		
		Double d = .0;
		//Glass Platte
		for(int i = 0; i<=2;i++){
			Double winkel = 1.57;
			Location l = new Location(loc.getWorld(), location.getX()+d, location.getY()-1.0, location.getZ());
			l.setYaw(0);
			ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
			as.setVisible(false);
			as.setGravity(false);
			as.setHelmet(new ItemStack(Material.STAINED_GLASS_PANE));
			as.setHeadPose(new EulerAngle(winkel, 0, 0));
			this.armorList.add(as);
			this.location.add(as.getLocation());
			l = main.getNew(l, b, 0.0, -0.62);
			as = (ArmorStand) loc.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
			as.setVisible(false);
			as.setGravity(false);
			as.setHelmet(new ItemStack(Material.STAINED_GLASS_PANE));
			as.setHeadPose(new EulerAngle(winkel, 0, 0));
			this.armorList.add(as);
			this.location.add(as.getLocation());
			l = main.getNew(l, b, 0.0, -0.62);
			as = (ArmorStand) loc.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
			as.setVisible(false);
			as.setGravity(false);
			as.setHelmet(new ItemStack(Material.STAINED_GLASS_PANE));
			as.setHeadPose(new EulerAngle(winkel, 0, 0));
			d+=.62;
			this.armorList.add(as);
			this.location.add(as.getLocation());
		}
		
		Location middle = Utils.getCenter(armorList.get(4).getLocation()).add(1, -.88, -1.0);
		middle.setYaw(0);
		ArmorStand as = (ArmorStand) middle.getWorld().spawnEntity(middle, EntityType.ARMOR_STAND);
		as.setRightArmPose(new EulerAngle(-1.75, 0, 0));
		as.setItemInHand(new ItemStack(Material.BONE));
		as.setGravity(false);
		as.setVisible(false);
		this.armorList.add(as);
		
		middle = Utils.getCenter(armorList.get(4).getLocation()).add(1.5, -.88, 1.45);
		middle.setYaw(180);
		as = (ArmorStand) middle.getWorld().spawnEntity(middle, EntityType.ARMOR_STAND);
		as.setRightArmPose(new EulerAngle(-1.75, 0, 0));
		as.setItemInHand(new ItemStack(Material.BONE));
		as.setGravity(false);
		as.setVisible(false);
		this.armorList.add(as);
		
		middle = Utils.getCenter(armorList.get(4).getLocation()).add(1.5, -.88, 0);
		middle.setYaw(180);
		as = (ArmorStand) middle.getWorld().spawnEntity(middle, EntityType.ARMOR_STAND);
		as.setRightArmPose(new EulerAngle(-1.75, 0, 0));
		as.setItemInHand(new ItemStack(Material.BONE));
		as.setGravity(false);
		as.setVisible(false);
		this.armorList.add(as);
		
		middle = Utils.getCenter(armorList.get(4).getLocation()).add(1, -.88, .45);
		middle.setYaw(0);
		as = (ArmorStand) middle.getWorld().spawnEntity(middle, EntityType.ARMOR_STAND);
		as.setRightArmPose(new EulerAngle(-1.75, 0, 0));
		as.setItemInHand(new ItemStack(Material.BONE));
		as.setGravity(false);
		as.setVisible(false);
		this.armorList.add(as);
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main.getInstance().tische2.add(this);
	}
	
	public void delete(){
		armorList.get(0).getLocation().getWorld().dropItem(armorList.get(0).getLocation().getBlock().getLocation().add(0, 1, 0), main.getInstance().itemse.tisch2);
		for(Entity entity : armorList){
			ArmorStand as = (ArmorStand) entity;
			entity.getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());
			entity.remove();
		}
		location.clear();
		armorList.clear();
		main.getInstance().sofas.remove(this);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e){
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand){
			if(armorList.contains(e.getRightClicked())){
				e.setCancelled(true);
				ItemStack is = player.getItemInHand();
				if(is!=null){
					if(is.getType().equals(Material.INK_SACK)){
						Short druability = is.getDurability();
						Integer amount = is.getAmount();
						if(amount>armorList.size() || player.getGameMode().equals(GameMode.CREATIVE)){amount=armorList.size();}
						List<Entity> list = new ArrayList<Entity>();
						for(Entity entity : armorList){
							if(entity instanceof ArmorStand){
								ArmorStand as = (ArmorStand) entity;
								ItemStack item = as.getHelmet();
								if(item.getDurability() != main.getFromDey(druability)){
									list.add(entity);
								}
							}
						}

						for(int i = 0; i<=amount-1;i++){
							Entity entity = list.get(i);
							if(entity instanceof ArmorStand){
								ArmorStand as = (ArmorStand) entity;
								ItemStack item = as.getHelmet();
								item.setDurability(main.getFromDey(druability));
								as.setHelmet(item);
							}
						}
						if(!player.getGameMode().equals(GameMode.CREATIVE)){
							is.setAmount(is.getAmount()-amount);
							player.getInventory().setItem(player.getInventory().getHeldItemSlot(), is);
							player.updateInventory();
						}
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
					delete();
				}
			}
		}
	}
	
	@EventHandler
	public void onWaterFlow(BlockFromToEvent e){
		Location locTo = e.getToBlock().getLocation();
		if(location!=null && !location.isEmpty()){
			if(location.contains(locTo) || location.contains(locTo.add(0,1,0))){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onHit(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(!p.isInsideVehicle() && location!=null){
			if(location.contains(e.getTo().getBlock().getLocation()) || location.contains(e.getTo().getBlock().getLocation().add(0,-1,0))){
					p.teleport(e.getFrom());
			}
		}
	}
}
