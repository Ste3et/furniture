package de.Ste3et_C0st.Furniture.Objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;

public class stuhl implements Listener {

	List<Entity> armorList = new ArrayList<Entity>();
	Location loc = null;
	BlockFace b = null;
	
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public stuhl(Location loc, Plugin plugin){
		this.loc = loc.getBlock().getLocation();
		BlockFace b = Utils.yawToFace(loc.getYaw()).getOppositeFace();
		Location center = Utils.getCenter(loc);
		Location sitz = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location feet1 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location feet2 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location feet3 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location feet4 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location lehne = main.getNew(center.add(0,-1.1,0), b, -.25, .0);
		feet1.add(-.25,-1.8,-.25);
		feet2.add(.25,-1.8,-.25);
		feet3.add(.25,-1.8,.25);
		feet4.add(-.25,-1.8,.25);
		
		sitz.add(0,-1.45,0);
		sitz.setYaw(Utils.FaceToYaw(b));
		lehne.setYaw(Utils.FaceToYaw(b));
		
		Utils.setArmorStand(sitz, null, new ItemStack(Material.TRAP_DOOR), false, armorList, null);
		Utils.setArmorStand(lehne, new EulerAngle(1.57, .0, .0), new ItemStack(Material.TRAP_DOOR), false, armorList, null);
		Utils.setArmorStand(feet1, null, new ItemStack(Material.LEVER), false, armorList, null);
		Utils.setArmorStand(feet2, null, new ItemStack(Material.LEVER), false, armorList, null);
		Utils.setArmorStand(feet3, null, new ItemStack(Material.LEVER), false, armorList, null);
		Utils.setArmorStand(feet4, null, new ItemStack(Material.LEVER), false, armorList, null);
		main.getInstance().stuehle.add(this);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
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

	public void delete(Boolean b){
		if(b){
			armorList.get(0).getLocation().getWorld().dropItem(armorList.get(0).getLocation().getBlock().getLocation().add(0, 1, 0), main.getInstance().itemse.stuhl);
			for(Entity entity : armorList){
				ArmorStand as = (ArmorStand) entity;
				entity.getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());
				entity.remove();
			}
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
					delete(true);
				}
			}
		}
	}
}
