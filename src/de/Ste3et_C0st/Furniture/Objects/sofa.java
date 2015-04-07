package de.Ste3et_C0st.Furniture.Objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
		BlockFace playerLookingDirection = Utils.yawToFace(loc.getYaw()).getOppositeFace();
		create(loc, lengt, playerLookingDirection, loc.getYaw(), plugin);
	}
	
	public void create(Location loc, Integer lengt, BlockFace b, Float yaw, Plugin plugin){
		loc = loc.getBlock().getLocation();
		loc.setYaw(yaw);
		Integer x = (int) loc.getX();
		Integer y = (int) loc.getY();
		Integer z = (int) loc.getZ();
		loc.setX(x);
		loc.setY(y);
		loc.setZ(z);
		
		if(b.equals(BlockFace.WEST)){loc = main.getNew(loc, b, .0, -1.0);}
		if(b.equals(BlockFace.SOUTH)){loc = main.getNew(loc, b, -1.0, -1.0);}
		if(b.equals(BlockFace.EAST)){loc = main.getNew(loc, b, -1.0, .0);}
			World w = loc.getWorld();
			Location looking = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() -1.4 , loc.getBlockZ());
			Location feet1 = main.getNew(looking, b, place, .2D);
			Location feet2 = main.getNew(looking, b, place, lengt.doubleValue()-.2D);
			Location feet3 = main.getNew(looking, b, place + .5, .2D);
			Location feet4 = main.getNew(looking, b, place + .5, lengt.doubleValue()-.2D);
			ArmorStand armor1 = (ArmorStand) w.spawnEntity(feet1, EntityType.ARMOR_STAND);
			ArmorStand armor2 = (ArmorStand) w.spawnEntity(feet2, EntityType.ARMOR_STAND);
			ArmorStand armor3 = (ArmorStand) w.spawnEntity(feet3, EntityType.ARMOR_STAND);
			ArmorStand armor4 = (ArmorStand) w.spawnEntity(feet4, EntityType.ARMOR_STAND);
			armor1.setHelmet(new ItemStack(Material.LEVER));
			armor2.setHelmet(new ItemStack(Material.LEVER));
			armor3.setHelmet(new ItemStack(Material.LEVER));
			armor4.setHelmet(new ItemStack(Material.LEVER));
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
				ArmorStand armorcarpet = (ArmorStand) w.spawnEntity(carpet, EntityType.ARMOR_STAND);
				armorcarpet.setHelmet(is);
				armorList.add(armorcarpet);
				sitz.add(armorcarpet);
				
				//OBERER TEIL
				
				
				Location location = main.getNew(carpetHight, b, place-.25,(double) d);
				location.setYaw(facing);
				armorcarpet = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
				armorcarpet.setHeadPose(new EulerAngle(1.57, .0, .0));
				armorcarpet.setHelmet(is);
				armorList.add(armorcarpet);
				
				if(d<=0D){d = 0.00;}
				d+=.58;
				
				
			}
			Float yaw1= facing;
			Float yaw2= facing;
			Location last = main.getNew(sitz.get(sitz.size()-1).getLocation(), b, 0D, 0.26D);
			last.setYaw(yaw1+90);
			Location first = main.getNew(new Location(loc.getWorld(), loc.getX(), last.getY(), loc.getZ()), b, place+.25, 0.07D);
			first.setYaw(yaw2-90);
			ArmorStand leftCarpet = (ArmorStand) w.spawnEntity(first.add(0,-.05,0), EntityType.ARMOR_STAND);
			ArmorStand RightCarpet = (ArmorStand) w.spawnEntity(last.add(0,-.05,0), EntityType.ARMOR_STAND);
			leftCarpet.setHelmet(is);
			RightCarpet.setHelmet(is);
			leftCarpet.setHeadPose(new EulerAngle(1.57, 0.0, 0.0));
			RightCarpet.setHeadPose(new EulerAngle(1.57, 0.0, 0.0));
			
			armorList.add(leftCarpet);
			armorList.add(RightCarpet);
			armorList.add(armor1);
			armorList.add(armor2);
			armorList.add(armor3);
			armorList.add(armor4);
			for(Entity as : armorList){
				ArmorStand a = (ArmorStand) as;
				a.setVisible(false);
				a.setGravity(false);
				a.setBasePlate(false);
			}
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
	
	public void delete(){
		armorList.get(0).getLocation().getWorld().dropItem(armorList.get(0).getLocation().getBlock().getLocation().add(0, 1, 0), main.getInstance().itemse.Sofa);
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
					delete();
				}
			}
		}
	}
}
