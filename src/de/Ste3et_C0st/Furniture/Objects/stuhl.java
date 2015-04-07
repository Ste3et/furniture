package de.Ste3et_C0st.Furniture.Objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;

public class stuhl implements Listener {

	List<Entity> armorList = new ArrayList<Entity>();
	Location loc = null;
	public stuhl(Location loc, Plugin plugin){
		this.loc = loc.getBlock().getLocation();
		BlockFace b = Utils.yawToFace(loc.getYaw()).getOppositeFace();
		Location center = Utils.getCenter(loc);
		Location sitz = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		sitz.add(0,-1.45,0);
		sitz.setYaw(Utils.FaceToYaw(b));
		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(sitz, EntityType.ARMOR_STAND);
		as.setBasePlate(false);
		as.setGravity(false);
		as.setVisible(false);
		as.setHelmet(new ItemStack(Material.TRAP_DOOR));
		
		armorList.add(as);
		
		Location feet1 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		feet1.add(-.25,-1.8,-.25);
		
		as = (ArmorStand) loc.getWorld().spawnEntity(feet1, EntityType.ARMOR_STAND);
		as.setBasePlate(false);
		as.setGravity(false);
		as.setVisible(false);
		as.setHelmet(new ItemStack(Material.LEVER));
		
		armorList.add(as);
		
		feet1 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		feet1.add(.25,-1.8,-.25);
		
		as = (ArmorStand) loc.getWorld().spawnEntity(feet1, EntityType.ARMOR_STAND);
		as.setBasePlate(false);
		as.setGravity(false);
		as.setVisible(false);
		as.setHelmet(new ItemStack(Material.LEVER));
		
		armorList.add(as);
		
		feet1 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		feet1.add(.25,-1.8,.25);
		
		as = (ArmorStand) loc.getWorld().spawnEntity(feet1, EntityType.ARMOR_STAND);
		as.setBasePlate(false);
		as.setGravity(false);
		as.setVisible(false);
		as.setHelmet(new ItemStack(Material.LEVER));
		
		armorList.add(as);
		
		armorList.add(as);
		
		feet1 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		feet1.add(-.25,-1.8,.25);
		
		as = (ArmorStand) loc.getWorld().spawnEntity(feet1, EntityType.ARMOR_STAND);
		as.setBasePlate(false);
		as.setGravity(false);
		as.setVisible(false);
		as.setHelmet(new ItemStack(Material.LEVER));
		
		armorList.add(as);
		
		
		feet1 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		feet1.add(0,-1.1,0);
		feet1 = main.getNew(feet1, b, -.25, .0);
		feet1.setYaw(Utils.FaceToYaw(b));
		as = (ArmorStand) loc.getWorld().spawnEntity(feet1, EntityType.ARMOR_STAND);
		as.setBasePlate(false);
		as.setGravity(false);
		as.setVisible(false);
		as.setHelmet(new ItemStack(Material.TRAP_DOOR));
		as.setHeadPose(new EulerAngle(1.57, .0, .0));
		armorList.add(as);
		main.getInstance().stuehle.add(this);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
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
	
	/*
	@EventHandler
	public void onHit(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(!p.isInsideVehicle() && loc!=null){
			if(e.getTo().getBlock().getLocation().equals(loc.getBlock().getLocation())){
				p.teleport(e.getFrom());
			}
		}
	}
	*/
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e){
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand){
			if(armorList.contains(e.getRightClicked())){
				e.setCancelled(true);
				ItemStack is = player.getItemInHand();
				if(is!=null){
					if(armorList.contains(e.getRightClicked())){
						armorList.get(0).setPassenger(player);
					}
				}
			}
		}
	}

	public void delete(){
		armorList.get(0).getLocation().getWorld().dropItem(armorList.get(0).getLocation().getBlock().getLocation().add(0, 1, 0), main.getInstance().itemse.stuhl);
		for(Entity entity : armorList){
			ArmorStand as = (ArmorStand) entity;
			entity.getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());
			entity.remove();
		}
		this.loc = null;
		armorList.clear();
		main.getInstance().stuehle.remove(this);
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
