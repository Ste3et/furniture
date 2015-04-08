package de.Ste3et_C0st.Furniture.Objects;

import java.util.ArrayList;
import java.util.HashMap;
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

public class largeTable implements Listener {

	List<Entity> armorList = new ArrayList<Entity>();
	List<Location> location = new ArrayList<Location>();
	Location loc = null;
	BlockFace b = null;
	
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public largeTable(Location loc, Plugin plugin, List<ItemStack> iL, HashMap<Integer, ItemStack> tellerItems){
		this.loc = loc;
		this.b = Utils.yawToFace(loc.getYaw());
		ItemStack IS1 =null;
		ItemStack IS2 =null;
		ItemStack IS3 =null;
		ItemStack IS4 =null;
		
		if(tellerItems != null){
			if(tellerItems.containsKey(0)){IS1=tellerItems.get(0);}
			if(tellerItems.containsKey(1)){IS2=tellerItems.get(1);}
			if(tellerItems.containsKey(2)){IS3=tellerItems.get(2);}
			if(tellerItems.containsKey(3)){IS4=tellerItems.get(3);}
		}
		
		Location location = Utils.getCenter(loc.getBlock().getLocation());
		float yaw = Utils.FaceToYaw(this.b);
		location = main.getNew(location, this.b, 0.1, 0.28);
		location.add(0,.2,0);
		Double winkel = 1.57;
		int iTems = 0;
		for(int x=1; x<=3;x++){
			Location l = null;
			l = main.getNew(location, this.b, 0.0, x*-.63);
			l.add(0,-1.2,0);
			l.setYaw(yaw);
			ItemStack iTemStack_1 = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemStack iTemStack_2 = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemStack iTemStack_3 = new ItemStack(Material.STAINED_GLASS_PANE);
			if(iL!=null&&!iL.isEmpty()){
				if(iL.get(iTems)!=null){
					iTemStack_1=iL.get(iTems);
					iTems++;
				}
				if(iL.get(iTems)!=null){
					iTemStack_2=iL.get(iTems);
					iTems++;
				}
				if(iL.get(iTems)!=null){
					iTemStack_3=iL.get(iTems);
					iTems++;
				}
			}
			
			Utils.setArmorStand(l, new EulerAngle(winkel, 0, 0), iTemStack_1, false, armorList,this.location);
			l = main.getNew(location, this.b, 0.63, x*-.63);
			l.add(0,-1.2,0);
			l.setYaw(yaw);
			Utils.setArmorStand(l, new EulerAngle(winkel, 0, 0), iTemStack_2, false, armorList,this.location);
			l = main.getNew(location, this.b, 1.26, x*-.63);
			l.add(0,-1.2,0);
			l.setYaw(yaw);
			Utils.setArmorStand(l, new EulerAngle(winkel, 0, 0), iTemStack_3, false, armorList,this.location);
		}

		Location middle = Utils.getCenter(armorList.get(0).getLocation());
		Location mitteTisch = Utils.getCenter(armorList.get(4).getLocation().getBlock().getLocation());
		middle.add(0, -.9, 0);
		Location feet1 = main.getNew(middle, this.b, -.2, .1);
		Location feet2 = main.getNew(middle, this.b, -.2, -1.3);
		Location feet3 = main.getNew(middle, this.b, 1.1, .1);
		Location feet4 = main.getNew(middle, this.b, 1.1, -1.3);
		
		
		double hight = .67;
		
		Location t1 = main.getNew(mitteTisch, this.b, -.95, .4).add(0,hight,0);
		Location t2 = main.getNew(mitteTisch, this.b, -.4, -.92).add(0,hight,0);
		Location t3 = main.getNew(mitteTisch, this.b, .92, -.36).add(0,hight,0);
		Location t4 = main.getNew(mitteTisch, this.b, .4, .92).add(0,hight,0);
		
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

		Utils.setArmorStand(feet1, new EulerAngle(-1.75, 0, 0), new ItemStack(Material.BONE), true, armorList,null);
		Utils.setArmorStand(feet2, new EulerAngle(-1.75, 0, 0), new ItemStack(Material.BONE), true, armorList,null);
		Utils.setArmorStand(feet3, new EulerAngle(-1.75, 0, 0), new ItemStack(Material.BONE), true, armorList,null);
		Utils.setArmorStand(feet4, new EulerAngle(-1.75, 0, 0), new ItemStack(Material.BONE), true, armorList,null);
		Utils.setArmorStand(t1, new EulerAngle(0,0,0), IS1, true, armorList,null);
		Utils.setArmorStand(t2, new EulerAngle(0,0,0), IS2, true, armorList,null);
		Utils.setArmorStand(t3, new EulerAngle(0,0,0), IS3, true, armorList,null);
		Utils.setArmorStand(t4, new EulerAngle(0,0,0), IS4, true, armorList,null);
		
		for(Entity e : armorList){
			ArmorStand armor = (ArmorStand) e;
			armor.setVisible(false);
			armor.setGravity(false);
		}
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main.getInstance().tische2.add(this);
	}
	
	public void delete(boolean b){
		if(b){
			armorList.get(0).getLocation().getWorld().dropItem(armorList.get(0).getLocation().getBlock().getLocation().add(0, 1, 0), main.getInstance().itemse.tisch2);
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
					}else if(!is.getType().isBlock()){
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
					delete(true);
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
	
	public List<ItemStack> getItemListTisch(){
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(int i = 0; i<=8;i++){
			items.add((((ArmorStand)armorList.get(i)).getHelmet()));
		}
		return items;
	}
	
	public HashMap<Integer, ItemStack> getTeller(){
		HashMap<Integer, ItemStack> teller = new HashMap<Integer, ItemStack>();
		for(int i = 0;i<=3;i++){
			try{
				teller.put(i, ((ArmorStand) armorList.get(armorList.size()-1-i)).getItemInHand());
			}catch(NullPointerException e){
				teller.put(i, new ItemStack(Material.AIR));
			}
		}
		return teller;
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
