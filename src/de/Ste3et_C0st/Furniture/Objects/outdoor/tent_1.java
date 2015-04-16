package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
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
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;

public class tent_1 implements Listener {

	private Location loc;
	private String ID;
	private BlockFace b;
	private Block block;
	private List<String> idList = new ArrayList<String>();
	private World w;
	public String getID(){return this.ID;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public tent_1(Location location, Plugin plugin, String ID) {
		this.loc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
		this.loc.setYaw(location.getYaw());
		this.w = loc.getWorld();
		BlockFace b = Utils.yawToFace(location.getYaw());
		
		if(b.equals(BlockFace.WEST)){location=main.getNew(location, b, 1D, 0D);}
		if(b.equals(BlockFace.NORTH)){location=main.getNew(location, b, 1D, 1D);}
		if(b.equals(BlockFace.EAST)){location=main.getNew(location, b, 0D, 1D);}

		location.setYaw(Utils.FaceToYaw(b));
		this.ID = ID;
		this.b = b;
		
		Location blockLocation = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
		Location loc_1 = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
		Location loc_2 = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
		Location loc_3 = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
		
		if(b.equals(BlockFace.SOUTH)){
			block = blockLocation.getWorld().getBlockAt(main.getNew(blockLocation, b, 2D, -2D));
			block.setType(Material.WORKBENCH);
		}else if(b.equals(BlockFace.WEST)){
			block = blockLocation.getWorld().getBlockAt(main.getNew(blockLocation, b, 1D, -2D));
			block.setType(Material.WORKBENCH);
		}else if(b.equals(BlockFace.NORTH)){
			block = blockLocation.getWorld().getBlockAt(main.getNew(blockLocation, b, 1D, -3D));
			block.setType(Material.WORKBENCH);
		}else if(b.equals(BlockFace.EAST)){
			block = blockLocation.getWorld().getBlockAt(main.getNew(blockLocation, b, 2D, -3D));
			block.setType(Material.WORKBENCH);
		}
		
		
		Location saveLoc = main.getNew(loc_1, b, -.55D, -0.6);
		saveLoc.add(0,-1.25,0);
		saveLoc.setYaw(Utils.FaceToYaw(b) -90);
		
		Location saveLoc2 = main.getNew(loc_2, b, -4.27, -4.4);
		saveLoc2.add(0,-1.25,0);
		saveLoc2.setYaw(Utils.FaceToYaw(b) -90);
		
		Location saveLoc3 = main.getNew(loc_3, b, -8D, -2.5D);
		saveLoc3.add(0,.64,0);
		saveLoc3.setYaw(Utils.FaceToYaw(b) -90);
		Double d = .0;

		for(int i = 0; i<=5;i++){
			Location loc1= main.getNew(saveLoc, b, d, 0D);
			Location loc2= main.getNew(saveLoc, b, d, -.48).add(0,.3,0);
			Location loc3= main.getNew(saveLoc, b, d, -.86).add(0,.81,0);
			Location loc4= main.getNew(saveLoc, b, d, -1.08).add(0,1.33,0);
			Location loc5= main.getNew(saveLoc, b, d, -1.38).add(0,1.86,0);
			
			loc1.setYaw(Utils.FaceToYaw(b.getOppositeFace()));
			loc2.setYaw(Utils.FaceToYaw(b.getOppositeFace()));
			loc3.setYaw(Utils.FaceToYaw(b.getOppositeFace()));
			loc4.setYaw(Utils.FaceToYaw(b.getOppositeFace()));
			loc5.setYaw(Utils.FaceToYaw(b.getOppositeFace()));
			
			location.add(loc1);
			location.add(loc2);
			location.add(loc3);
			location.add(loc4);
			location.add(loc5);
			
			Utils.setArmorStand(loc1, new EulerAngle(0, 0, -.2), new ItemStack(Material.CARPET), false, getID(),idList);
			Utils.setArmorStand(loc2, new EulerAngle(0, 0, -7.2), new ItemStack(Material.CARPET), false,getID(),idList);
			Utils.setArmorStand(loc3, new EulerAngle(0, 0, -7.2), new ItemStack(Material.CARPET), false,getID(),idList);
			Utils.setArmorStand(loc4, new EulerAngle(0, 0, -7.7), new ItemStack(Material.CARPET), false,getID(),idList);
			Utils.setArmorStand(loc5, new EulerAngle(0, 0, -.7), new ItemStack(Material.CARPET), false,getID(),idList);
			d+=.62;
		}

		for(int i = 0; i<=5;i++){
			Location loc1= main.getNew(saveLoc2, b, d, .02D);
			Location loc2= main.getNew(saveLoc2, b, d, .48).add(0,.3,0);
			Location loc3= main.getNew(saveLoc2, b, d, .86).add(0,.81,0);
			Location loc4= main.getNew(saveLoc2, b, d, 1.08).add(0,1.33,0);
			Location loc5= main.getNew(saveLoc2, b, d, 1.38).add(0,1.86,0);
			
			loc1.setYaw(Utils.FaceToYaw(b.getOppositeFace()));
			loc2.setYaw(Utils.FaceToYaw(b.getOppositeFace()));
			loc3.setYaw(Utils.FaceToYaw(b.getOppositeFace()));
			loc4.setYaw(Utils.FaceToYaw(b.getOppositeFace()));
			loc5.setYaw(Utils.FaceToYaw(b.getOppositeFace()));
			
			location.add(loc1);
			location.add(loc2);
			location.add(loc3);
			location.add(loc4);
			location.add(loc5);
			
			Utils.setArmorStand(loc1, new EulerAngle(0, 0, .2), new ItemStack(Material.CARPET), false,getID(),idList);
			Utils.setArmorStand(loc2, new EulerAngle(0, 0, 7.2), new ItemStack(Material.CARPET), false,getID(),idList);
			Utils.setArmorStand(loc3, new EulerAngle(0, 0, 7.2), new ItemStack(Material.CARPET), false,getID(),idList);
			Utils.setArmorStand(loc4, new EulerAngle(0, 0, 7.7), new ItemStack(Material.CARPET), false,getID(),idList);
			Utils.setArmorStand(loc5, new EulerAngle(0, 0, .7), new ItemStack(Material.CARPET), false,getID(),idList);
			d+=.62;
		}
		
		//middle
		for(int i = 0; i<=5;i++){
			Location loc1= main.getNew(saveLoc3, b, d, 0D);
			loc1.setYaw(Utils.FaceToYaw(b.getOppositeFace()));
			location.add(loc1);
			Utils.setArmorStand(loc1, null, new ItemStack(Material.WOOD_STEP), false,getID(),idList);

			d+=.62;
		}
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main.getInstance().tents1.add(this);
	}
	
	public void delete(boolean b){
		if(b){
			getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("tent1"));
			for(String s : idList){
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				as.getWorld().playEffect(as.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());
				as.remove();
			}
			main.getInstance().mgr.deleteFromConfig(getID(), "tent1");
		}
		idList.clear();
		main.getInstance().tents1.remove(this);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void damage(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			if(e.getEntity() instanceof ArmorStand){
				if(idList.contains(e.getEntity().getCustomName())){
					delete(true);
					block.setType(Material.AIR);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand){
			if(idList.contains(e.getRightClicked().getCustomName())){
				e.setCancelled(true);
				ItemStack is = player.getItemInHand();
				if(is!=null){
					if(is.getType().equals(Material.INK_SACK)){
						Short druability = is.getDurability();
						Integer amount = is.getAmount();
						if(amount>idList.size() || player.getGameMode().equals(GameMode.CREATIVE)){amount=idList.size();}
						List<Entity> list = new ArrayList<Entity>();
						
						
						for(String s : this.idList){
								ArmorStand as = Utils.getArmorStandAtID(w, s);
								ItemStack item = as.getHelmet();
								if(!as.getHelmet().getType().equals(Material.CARPET)){continue;}
								if(item.getDurability() != main.getFromDey(druability)){
									list.add(as);
							}
						}

						int neAmound = amount;
						try{
								for(int i = 0; i<=amount-1;i++){
									Entity entity = list.get(i);
									if(entity instanceof ArmorStand){
											ArmorStand as = (ArmorStand) entity;
											if(!as.getHelmet().getType().equals(Material.CARPET)){neAmound-=1;continue;}
											ItemStack item = as.getHelmet();
											item.setDurability(main.getFromDey(druability));
											as.setHelmet(item);
									}
								}
						}catch(Exception ex){}
						if(!player.getGameMode().equals(GameMode.CREATIVE)){
							is.setAmount(is.getAmount()-neAmound);
							player.getInventory().setItem(player.getInventory().getHeldItemSlot(), is);
							player.updateInventory();
						}
						return;
					}
				}
			}
			Vector v1 = e.getRightClicked().getLocation().toVector();
			Vector v2 = block.getLocation().toVector();
			if(v1.distance(v2)<=2D){
				player.openWorkbench(block.getLocation(), true);
			}
		}
	}
}
