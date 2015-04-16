package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;

public class largeTable implements Listener {

	private List<String> idList = new ArrayList<String>();
	private List<String> tellerIDS = new ArrayList<String>();
	private Location loc = null;
	private BlockFace b = null;
	private String id;
	private World w;
	public String getID(){return this.id;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public largeTable(Location loc, Plugin plugin, List<ItemStack> iL, HashMap<Integer, ItemStack> tellerItems, String id){
		List<ArmorStand> armorlist = new ArrayList<ArmorStand>();
		this.loc = loc;
		this.b = Utils.yawToFace(loc.getYaw());
		this.id = id;
		this.w = loc.getWorld();
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
			
			ArmorStand as = Utils.setArmorStand(l, new EulerAngle(winkel, 0, 0), iTemStack_1, false,getID(),idList);
			armorlist.add(as);
			l = main.getNew(location, this.b, 0.63, x*-.63);
			l.add(0,-1.2,0);
			l.setYaw(yaw);
			as = Utils.setArmorStand(l, new EulerAngle(winkel, 0, 0), iTemStack_2, false,getID(),idList);
			armorlist.add(as);
			l = main.getNew(location, this.b, 1.26, x*-.63);
			l.add(0,-1.2,0);
			l.setYaw(yaw);
			as = Utils.setArmorStand(l, new EulerAngle(winkel, 0, 0), iTemStack_3, false,getID(),idList);
			armorlist.add(as);
		}

		Location middle = Utils.getCenter(armorlist.get(0).getLocation());
		Location mitteTisch = Utils.getCenter(armorlist.get(4).getLocation().getBlock().getLocation());
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

		Utils.setArmorStand(feet1, new EulerAngle(-1.75, 0, 0), new ItemStack(Material.BONE), true, getID(), idList);
		Utils.setArmorStand(feet2, new EulerAngle(-1.75, 0, 0), new ItemStack(Material.BONE), true, getID(), idList);
		Utils.setArmorStand(feet3, new EulerAngle(-1.75, 0, 0), new ItemStack(Material.BONE), true, getID(), idList);
		Utils.setArmorStand(feet4, new EulerAngle(-1.75, 0, 0), new ItemStack(Material.BONE), true, getID(), idList);
		ArmorStand as1 = Utils.setArmorStand(t1, new EulerAngle(0, 0, 0), IS1, true, getID(), idList);
		ArmorStand as2 = Utils.setArmorStand(t2, new EulerAngle(0, 0, 0), IS2, true, getID(), idList);
		ArmorStand as3 = Utils.setArmorStand(t3, new EulerAngle(0, 0, 0), IS3, true, getID(), idList);
		ArmorStand as4 = Utils.setArmorStand(t4, new EulerAngle(0, 0, 0), IS4, true, getID(), idList);
		
		tellerIDS.add(as1.getCustomName());
		tellerIDS.add(as2.getCustomName());
		tellerIDS.add(as3.getCustomName());
		tellerIDS.add(as4.getCustomName());
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main.getInstance().tische2.add(this);
	}
	
	public void delete(boolean b){
		if(b){
			getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("largeTable"));
			
			for(String s : tellerIDS){
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				if(as!=null){
					if(as.getItemInHand()!=null&&!as.getItemInHand().getType().equals(Material.AIR)){
						getLocation().getWorld().dropItem(getLocation(), as.getItemInHand());
					}
				}
			}
			
			for(String s : this.idList){
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				if(as!=null && as.getHelmet()!= null && as.getHelmet().getType()!=null){
					loc.getWorld().playEffect(loc, Effect.STEP_SOUND, as.getHelmet().getType());
				}	
				as.remove();
			}
			main.getInstance().mgr.deleteFromConfig(getID(), "largeTable");
		}
		
		this.tellerIDS.clear();
		this.idList.clear();
		main.getInstance().tische2.remove(this);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand){
			if(this.idList.contains(e.getRightClicked().getCustomName())){
				e.setCancelled(true);
				ItemStack is = player.getItemInHand();
				if(is!=null){
					if(is.getType().equals(Material.INK_SACK)){
						Short druability = is.getDurability();
						Integer amount = is.getAmount();
						if(amount>this.idList.size()-4 || player.getGameMode().equals(GameMode.CREATIVE)){amount=this.idList.size()-4;}
						List<Entity> list = new ArrayList<Entity>();
						
						for(String s : this.idList){
								ArmorStand as = Utils.getArmorStandAtID(w, s);
								if(as!=null){
									ItemStack item = as.getHelmet();
									if(item.getDurability() != main.getFromDey(druability)){
										list.add(as);
									}
								}
						}

						try{
							for(int i = 0; i<=amount-1;i++){
								Entity entity = list.get(i);
								if(entity instanceof ArmorStand){
									ArmorStand as = (ArmorStand) entity;
									ItemStack item = as.getHelmet();
									item.setDurability(main.getFromDey(druability));
									as.setHelmet(item);
								}
							}
						}catch(Exception ex){}
						
						
						if(!player.getGameMode().equals(GameMode.CREATIVE)){
							is.setAmount(is.getAmount()-amount);
							player.getInventory().setItem(player.getInventory().getHeldItemSlot(), is);
							player.updateInventory();
						}
					}else if(!is.getType().isBlock() || is.getType().equals(Material.AIR)){
						BlockFace b = Utils.yawToFace(player.getLocation().getYaw());
						ArmorStand as = null;
						
						for(String s : this.tellerIDS){
							ArmorStand armorStand = Utils.getArmorStandAtID(w, s);
							BlockFace b2 = Utils.yawToFace(armorStand.getLocation().getYaw());
							if(b2.equals(b)){
								as = armorStand;
								break;
							}
						}
						
						if(as.getItemInHand()!=null&&!as.getItemInHand().getType().equals(Material.AIR)){as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());}
						as.setItemInHand(is);
						
						player.getInventory().clear(player.getInventory().getHeldItemSlot());
						player.updateInventory();
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void damage(EntityDamageByEntityEvent e){
		if(e.isCancelled()){return;}
		if(e.getDamager() instanceof Player){
			if(e.getEntity() instanceof ArmorStand){
				if(this.idList.contains(e.getEntity().getCustomName())){
					delete(true);
				}
			}
		}
	}
	
	public List<ItemStack> getItemListTisch(){
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(int i = 0; i<=8;i++){
			items.add(Utils.getArmorStandAtID(w, idList.get(i)).getHelmet());
		}
		return items;
	}
	
	public HashMap<Integer, ItemStack> getTeller(){
		HashMap<Integer, ItemStack> teller = new HashMap<Integer, ItemStack>();
		for(String s : tellerIDS){
			try{
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				teller.put(teller.size(), as.getItemInHand());
			}catch(Exception e){
				teller.put(teller.size(), new ItemStack(Material.AIR));
			}
		}
		return teller;
	}
}
