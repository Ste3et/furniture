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
		float yaw = Utils.FaceToYaw(b);
		location = main.getNew(location, b, 0.1, 0.28);
		location.add(0,.2,0);
		Double winkel = 1.57;
		for(int x=1; x<=3;x++){
			Location l = null;
			l = main.getNew(location, b, 0.0, x*-.63);
			l.add(0,-1.2,0);
			l.setYaw(yaw);
			ArmorStand as = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
			as.setHelmet(new ItemStack(Material.STAINED_GLASS_PANE));
			as.setHeadPose(new EulerAngle(winkel, 0, 0));
			this.location.add(l.add(0,.5,0).getBlock().getLocation());
			armorList.add(as);
			
			l = main.getNew(location, b, 0.63, x*-.63);
			l.add(0,-1.2,0);
			l.setYaw(yaw);
			as = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
			as.setHelmet(new ItemStack(Material.STAINED_GLASS_PANE));
			as.setHeadPose(new EulerAngle(winkel, 0, 0));
			this.location.add(l.add(0,.5,0).getBlock().getLocation());
			armorList.add(as);
			
			l = main.getNew(location, b, 1.26, x*-.63);
			l.add(0,-1.2,0);
			l.setYaw(yaw);
			as = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
			as.setHelmet(new ItemStack(Material.STAINED_GLASS_PANE));
			as.setHeadPose(new EulerAngle(winkel, 0, 0));
			this.location.add(l.add(0,.5,0).getBlock().getLocation());
			
			armorList.add(as);
		}
		
		/*Teller {
		
			###
			###
			###
			
			3;7;5;1
		}*/
		
		Location middle = Utils.getCenter(armorList.get(0).getLocation());
		middle.add(0, -.9, 0);
		Location feet1 = main.getNew(middle, b, -.2, .1);
		Location feet2 = main.getNew(middle, b, -.2, -1.3);
		Location feet3 = main.getNew(middle, b, 1.1, .1);
		Location feet4 = main.getNew(middle, b, 1.1, -1.3);
		
		double hight = .68;
		
		Location t1 = new Location(middle.getWorld(), this.location.get(3).getX(), this.location.get(3).getY()+hight, this.location.get(3).getZ());
		Location t2 = new Location(middle.getWorld(), this.location.get(7).getX(), this.location.get(7).getY()+hight, this.location.get(7).getZ());
		Location t3 = new Location(middle.getWorld(), this.location.get(5).getX(), this.location.get(5).getY()+hight, this.location.get(5).getZ());
		Location t4 = new Location(middle.getWorld(), this.location.get(1).getX(), this.location.get(1).getY()+hight, this.location.get(1).getZ());
		
		t1 =main.getNew(t1, b, -.05D, .37D);
		t2 =main.getNew(t2, b, -.13D, -.32D);
		t3 =main.getNew(t3, b, .55D, -.38D);
		t4 =main.getNew(t4, b, .62D, .30D);
		
		float yaw1 = yaw;
		float yaw2 = yaw1-90;
		float yaw3 = yaw2-90;
		float yaw4 = yaw3-90;
		
		t1.setYaw(yaw1);
		t2.setYaw(yaw2);
		t3.setYaw(yaw3);
		t4.setYaw(yaw4);
		
		feet1.setYaw(yaw);
		feet2.setYaw(yaw);
		feet3.setYaw(yaw);
		feet4.setYaw(yaw);
		
		
		ArmorStand as = (ArmorStand) feet1.getWorld().spawnEntity(feet1, EntityType.ARMOR_STAND);
		as.setRightArmPose(new EulerAngle(-1.75, 0, 0));
		as.setItemInHand(new ItemStack(Material.BONE));
		this.armorList.add(as);
		
		as = (ArmorStand) feet2.getWorld().spawnEntity(feet2, EntityType.ARMOR_STAND);
		as.setRightArmPose(new EulerAngle(-1.75, 0, 0));
		as.setItemInHand(new ItemStack(Material.BONE));
		this.armorList.add(as);
		
		as = (ArmorStand) feet3.getWorld().spawnEntity(feet3, EntityType.ARMOR_STAND);
		as.setRightArmPose(new EulerAngle(-1.75, 0, 0));
		as.setItemInHand(new ItemStack(Material.BONE));
		this.armorList.add(as);
		
		as = (ArmorStand) feet4.getWorld().spawnEntity(feet4, EntityType.ARMOR_STAND);
		as.setRightArmPose(new EulerAngle(-1.75, 0, 0));
		as.setItemInHand(new ItemStack(Material.BONE));
		this.armorList.add(as);
		
		as = (ArmorStand) t1.getWorld().spawnEntity(t1, EntityType.ARMOR_STAND);
		as.setRightArmPose(new EulerAngle(0,.0,.0));
		this.armorList.add(as);
		
		as = (ArmorStand) t2.getWorld().spawnEntity(t2, EntityType.ARMOR_STAND);
		as.setRightArmPose(new EulerAngle(0,.0,.0));
		this.armorList.add(as);
		
		as = (ArmorStand) t3.getWorld().spawnEntity(t3, EntityType.ARMOR_STAND);
		as.setRightArmPose(new EulerAngle(0,.0,.0));
		this.armorList.add(as);
		
		as = (ArmorStand) t4.getWorld().spawnEntity(t4, EntityType.ARMOR_STAND);
		as.setRightArmPose(new EulerAngle(0,.0,.0));
		this.armorList.add(as);
		
		for(Entity e : armorList){
			ArmorStand armor = (ArmorStand) e;
			armor.setVisible(false);
			armor.setGravity(false);
		}
		
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
					}else{
						Integer i = armorList.size();
						if(armorList.get(3).equals(e.getRightClicked())){
							i-=4;
							ArmorStand as = (ArmorStand) armorList.get(i);
							if(as.getItemInHand()!=null&&!as.getItemInHand().getType().equals(Material.AIR)){as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());}
							as.setItemInHand(is);
						}
						if(armorList.get(7).equals(e.getRightClicked())){
							i-=3;
							ArmorStand as = (ArmorStand) armorList.get(i);
							if(as.getItemInHand()!=null&&!as.getItemInHand().getType().equals(Material.AIR)){as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());}
							as.setItemInHand(is);
						}
						if(armorList.get(5).equals(e.getRightClicked())){
							i-=2;
							ArmorStand as = (ArmorStand) armorList.get(i);
							if(as.getItemInHand()!=null&&!as.getItemInHand().getType().equals(Material.AIR)){as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());}
							as.setItemInHand(is);
						}
						if(armorList.get(1).equals(e.getRightClicked())){
							i-=1;
							ArmorStand as = (ArmorStand) armorList.get(i);
							if(as.getItemInHand()!=null&&!as.getItemInHand().getType().equals(Material.AIR)){as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());}
							as.setItemInHand(is);
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
			if(location.contains(locTo) || location.contains(locTo.add(0,-0.5,0))){
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
	}
	*/
}
