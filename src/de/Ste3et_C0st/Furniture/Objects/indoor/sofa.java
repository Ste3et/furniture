package de.Ste3et_C0st.Furniture.Objects.indoor;

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

public class sofa implements Listener {
	private List<String> idList = new ArrayList<String>();
	private ItemStack is;
	private Double place;
	private BlockFace b;
	private short color = 0;
	private Location loc = null;
	private String id;
	private World w;
	public String getID(){return this.id;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public sofa(Location loc, Integer lengt, Plugin plugin, List<ItemStack> list, String id){
		List<Entity> sitz = new ArrayList<Entity>();
		this.w = loc.getWorld();
		this.loc = loc;
		this.place = 0.2;
		this.id = id;
		this.b = Utils.yawToFace(loc.getYaw());
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
			
			Utils.setArmorStand(feet1, null, new ItemStack(Material.LEVER), false,getID(),idList);
			Utils.setArmorStand(feet2, null, new ItemStack(Material.LEVER), false,getID(),idList);
			Utils.setArmorStand(feet3, null, new ItemStack(Material.LEVER), false,getID(),idList);
			Utils.setArmorStand(feet4, null, new ItemStack(Material.LEVER), false,getID(),idList);

			Location carpetHight = new Location(looking.getWorld(), loc.getBlockX(), loc.getBlockY() -1 , loc.getBlockZ());
			carpetHight.setYaw(Utils.FaceToYaw(b));
			carpetHight = main.getNew(carpetHight, b, .25,.3);
			Double d = .02;
			float facing = Utils.FaceToYaw(b);
			int l = 0;
			for(Double i = .0; i<=lengt; i+=0.65){
				Location carpet = main.getNew(carpetHight, b, place,(double) d);
				carpet.setYaw(facing);
				ArmorStand as = null;
				if(list!=null&&!list.isEmpty() && list.get(l)!=null){
					as = Utils.setArmorStand(carpet, null, list.get(l), false,getID(),idList);
				}else{
					as = Utils.setArmorStand(carpet, null, is, false,getID(),idList);
				}
				sitz.add(as);
				l++;
				
				//OBERER TEIL
				Location location = main.getNew(carpetHight, b, place-.25,(double) d);
				location.setYaw(facing);
				
				if(list!=null&&!list.isEmpty() && list.get(l)!=null){
					Utils.setArmorStand(location, new EulerAngle(1.57, .0, .0), list.get(l), false,getID(),idList);
				}else{
					Utils.setArmorStand(location, new EulerAngle(1.57, .0, .0), is, false,getID(),idList);
				}
				l++;
				if(d<=0D){d = 0.00;}
				d+=.58;
			}
			
			Float yaw1= facing;
			Float yaw2= facing;
			Location last = main.getNew(sitz.get(sitz.size()-1).getLocation(), b, 0D, 0.26D);
			last.setYaw(yaw1+90);
			Location first = main.getNew(new Location(loc.getWorld(), loc.getX(), last.getY(), loc.getZ()), b, place+.25, 0.07D);
			first.setYaw(yaw2-90);
			
			Utils.setArmorStand(first.add(0,-.05,0), new EulerAngle(1.57, .0, .0), is, false,getID(),idList);
			Utils.setArmorStand(last.add(0,-.05,0), new EulerAngle(1.57, .0, .0), is, false,getID(),idList);
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
			main.getInstance().sofas.add(this);
	}
	
	/*@EventHandler
	private void onWaterFlow(BlockFromToEvent e){
		Location locTo = e.getToBlock().getLocation();
		if(location!=null && !location.isEmpty()){
			if(location.contains(locTo) || location.contains(locTo.add(0,1,0))){
				e.setCancelled(true);
			}
		}
	}*/
	
	public void delete(Boolean b){
		if(b){
			getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("sofa"));
			for(String s : idList){
				ArmorStand as = Utils.getArmorStandAtID(this.w, s);
				as.getWorld().playEffect(as.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());
				as.remove();
				main.getInstance().mgr.deleteFromConfig(getID(), "sofa");
			}
		}
		idList.clear();
		main.getInstance().sofas.remove(this);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand){
			if(idList.contains(e.getRightClicked().getCustomName())){
				e.setCancelled(true);
				ItemStack is = player.getItemInHand();
				ArmorStand armorStand = Utils.getArmorStandAtID(w, e.getRightClicked().getCustomName());
				if(is!=null){
					if(is.getType().equals(Material.INK_SACK)){
						e.getRightClicked().sendMessage("test#2");
						Short druability = is.getDurability();
						Integer amount = is.getAmount() + 4;
						if(amount>idList.size() || player.getGameMode().equals(GameMode.CREATIVE)){amount=idList.size();}
						List<Entity> list = new ArrayList<Entity>();
						for(String s : idList){
								ArmorStand as = Utils.getArmorStandAtID(w, s);
								if(as.getHelmet().getType().equals(Material.CARPET)){
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
						}catch(IndexOutOfBoundsException ex){}
						
						if(!player.getGameMode().equals(GameMode.CREATIVE)){
							is.setAmount(is.getAmount()+4-amount);
							player.getInventory().setItem(player.getInventory().getHeldItemSlot(), is);
							player.updateInventory();
						}
					}else if(!armorStand.getHeadPose().equals(new EulerAngle(1.57, .0, .0)) && armorStand.getHelmet().getType().equals(Material.CARPET)){
						e.getRightClicked().setPassenger(player);
					}
				}else if(!armorStand.getHeadPose().equals(new EulerAngle(1.57, .0, .0)) && armorStand.getHelmet().getType().equals(Material.CARPET)){
					e.getRightClicked().setPassenger(player);
				}
			}
		}
	}
	
	public List<ItemStack> getItemListTisch(){
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(String s : idList){
			ArmorStand as = Utils.getArmorStandAtID(w, s);
			if(as.getHelmet()!=null&&as.getHelmet().getType().equals(is.getType())){
				items.add(as.getHelmet());
			}
		}
		return items;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void damage(EntityDamageByEntityEvent e){
		if(e.isCancelled()){return;}
		if(e.getDamager() instanceof Player){
			if(e.getEntity() instanceof ArmorStand){
				if(idList.contains(e.getEntity().getCustomName())){
					delete(true);
				}
			}
		}
	}
}
