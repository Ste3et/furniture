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
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;

public class sofa implements Listener {
	private List<Entity> armorList = new ArrayList<Entity>();
	private List<Entity> sitz = new ArrayList<Entity>();
	private List<Location> location = new ArrayList<Location>();
	
	private ItemStack is;
	private Double place;
	private short color = 0;
	
	public sofa(Location loc, Integer lengt, Plugin plugin){
		this.place = 0.2;
		is = new ItemStack(Material.CARPET);
		is.setDurability(color);
		BlockFace b = Utils.yawToFace(loc.getYaw()).getOppositeFace();

		loc = loc.getBlock().getLocation();
		loc.setYaw(Utils.FaceToYaw(b));
		Integer x = (int) loc.getX();
		Integer y = (int) loc.getY();
		Integer z = (int) loc.getZ();
		loc.setX(x);
		loc.setY(y);
		loc.setZ(z);
		
		if(b.equals(BlockFace.WEST)){loc = main.getNew(loc, b, .0, -1.0);}
		if(b.equals(BlockFace.SOUTH)){loc = main.getNew(loc, b, -1.0, -1.0);}
		if(b.equals(BlockFace.EAST)){loc = main.getNew(loc, b, -1.0, .0);}
			Location looking = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() -1.4 , loc.getBlockZ());
			Location feet1 = main.getNew(looking, b, place, .2D);
			Location feet2 = main.getNew(looking, b, place, lengt.doubleValue()-.2D);
			Location feet3 = main.getNew(looking, b, place + .5, .2D);
			Location feet4 = main.getNew(looking, b, place + .5, lengt.doubleValue()-.2D);
			
			Utils.setArmorStand(feet1, null, new ItemStack(Material.LEVER), false, armorList, null);
			Utils.setArmorStand(feet2, null, new ItemStack(Material.LEVER), false, armorList, null);
			Utils.setArmorStand(feet3, null, new ItemStack(Material.LEVER), false, armorList, null);
			Utils.setArmorStand(feet4, null, new ItemStack(Material.LEVER), false, armorList, null);

			Location carpetHight = new Location(looking.getWorld(), loc.getBlockX(), loc.getBlockY() -1 , loc.getBlockZ());
			carpetHight.setYaw(Utils.FaceToYaw(b));
			//sitz
			carpetHight = main.getNew(carpetHight, b, .25,.3);
			Double d = .02;
			float facing = Utils.FaceToYaw(b);
			for(Double i = .0; i<=lengt; i+=0.65){
				//SITZ
				location.add(main.getNew(carpetHight, b, place,(double) d).getBlock().getLocation().add(0, 1, 0));
				Location carpet = main.getNew(carpetHight, b, place,(double) d);
				carpet.setYaw(facing);
				ArmorStand as = Utils.setArmorStand(carpet, null, is, false, armorList, null);
				sitz.add(as);
				//OBERER TEIL
				Location location = main.getNew(carpetHight, b, place-.25,(double) d);
				location.setYaw(facing);
				Utils.setArmorStand(location, new EulerAngle(1.57, .0, .0), is, false, armorList, null);
				if(d<=0D){d = 0.00;}
				d+=.58;
			}
			Float yaw1= facing;
			Float yaw2= facing;
			Location last = main.getNew(sitz.get(sitz.size()-1).getLocation(), b, 0D, 0.26D);
			last.setYaw(yaw1+90);
			Location first = main.getNew(new Location(loc.getWorld(), loc.getX(), last.getY(), loc.getZ()), b, place+.25, 0.07D);
			first.setYaw(yaw2-90);
			
			Utils.setArmorStand(first.add(0,-.05,0), new EulerAngle(1.57, .0, .0), is, false, armorList, null);
			Utils.setArmorStand(last.add(0,-.05,0), new EulerAngle(1.57, .0, .0), is, false, armorList, null);
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
			main.getInstance().sofas.add(this);
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
	
	/*
	@EventHandler
	public void onHit(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(!p.isInsideVehicle() && location!=null){
			if(location.contains(e.getTo().getBlock().getLocation()) || location.contains(e.getTo().getBlock().getLocation().add(0,-1,0))){
					p.teleport(e.getFrom());
			}
		}
	}*/
	
	public void delete(Boolean b){
		if(b){
			armorList.get(0).getLocation().getWorld().dropItem(armorList.get(0).getLocation().getBlock().getLocation().add(0, 1, 0), main.getInstance().itemse.Sofa);
			for(Entity entity : armorList){
				ArmorStand as = (ArmorStand) entity;
				entity.getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());
				entity.remove();
			}
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
					}else if(sitz.contains(e.getRightClicked())){
						e.getRightClicked().setPassenger(player);
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
