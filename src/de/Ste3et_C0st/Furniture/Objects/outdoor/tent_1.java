package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;
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
		Location karabine1 = main.getNew(getLocation(), getBlockFace(), 0D, 0D);
		Location karabine2 = main.getNew(getLocation(), getBlockFace(), 3D, 0D);
		Location karabine3 = main.getNew(getLocation(), getBlockFace(), 3D, -4D);
		Location karabine4 = main.getNew(getLocation(), getBlockFace(), 0D, -4D);
		karabine1 = Utils.getCenter(karabine1);
		karabine2 = Utils.getCenter(karabine2);
		karabine3 = Utils.getCenter(karabine3);
		karabine4 = Utils.getCenter(karabine4);
		karabine1.setYaw(Utils.FaceToYaw(b)+90);
		karabine2.setYaw(Utils.FaceToYaw(b)+90);
		karabine3.setYaw(Utils.FaceToYaw(b)+90);
		karabine4.setYaw(Utils.FaceToYaw(b)+90);
		
		
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
			
			Utils.setArmorStand(loc1, new EulerAngle(0, 0, -.2), new ItemStack(Material.CARPET), false,false,false, getID(),idList);
			Utils.setArmorStand(loc2, new EulerAngle(0, 0, -7.2), new ItemStack(Material.CARPET), false,false,false,getID(),idList);
			Utils.setArmorStand(loc3, new EulerAngle(0, 0, -7.2), new ItemStack(Material.CARPET), false,false,false,getID(),idList);
			Utils.setArmorStand(loc4, new EulerAngle(0, 0, -7.7), new ItemStack(Material.CARPET), false,false,false,getID(),idList);
			Utils.setArmorStand(loc5, new EulerAngle(0, 0, -.7), new ItemStack(Material.CARPET), false,false,false,getID(),idList);
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
			
			Utils.setArmorStand(loc1, new EulerAngle(0, 0, .2), new ItemStack(Material.CARPET), false,false,false,getID(),idList);
			Utils.setArmorStand(loc2, new EulerAngle(0, 0, 7.2), new ItemStack(Material.CARPET), false,false,false,getID(),idList);
			Utils.setArmorStand(loc3, new EulerAngle(0, 0, 7.2), new ItemStack(Material.CARPET), false,false,false,getID(),idList);
			Utils.setArmorStand(loc4, new EulerAngle(0, 0, 7.7), new ItemStack(Material.CARPET), false,false,false,getID(),idList);
			Utils.setArmorStand(loc5, new EulerAngle(0, 0, .7), new ItemStack(Material.CARPET), false,false,false,getID(),idList);
			d+=.62;
		}
		
		//middle
		for(int i = 0; i<=5;i++){
			Location loc1= main.getNew(saveLoc3, b, d, 0D);
			loc1.setYaw(Utils.FaceToYaw(b.getOppositeFace()));
			location.add(loc1);
			Utils.setArmorStand(loc1, null, new ItemStack(Material.WOOD_STEP), false,false,false,getID(),idList);

			d+=.62;
		}
		
		Utils.setArmorStand(karabine1.add(0,-1.9,0), null, new ItemStack(Material.TRIPWIRE_HOOK), false, false,false, ID, idList);
		Utils.setArmorStand(karabine2.add(0,-1.9,0), null, new ItemStack(Material.TRIPWIRE_HOOK), false, false,false, ID, idList);
		Utils.setArmorStand(karabine3.add(0,-1.9,0), null, new ItemStack(Material.TRIPWIRE_HOOK), false, false,false, ID, idList);
		Utils.setArmorStand(karabine4.add(0,-1.9,0), null, new ItemStack(Material.TRIPWIRE_HOOK), false, false,false, ID, idList);
		
		Location crafting = Utils.getCenter(block.getLocation());
		crafting.setYaw(Utils.FaceToYaw(b)+90);
		Utils.setArmorStand(crafting.add(0,-1,0), null, new ItemStack(Material.LADDER), false, false,false, ID, idList);
		Utils.setArmorStand(crafting.add(0,.62,0), null, new ItemStack(Material.LADDER), false, false,false, ID, idList);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main.getInstance().getManager().tent1List.add(this);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if(e.getBlock()!=null&&block!=null&&e.getBlock().equals(block)){
			e.setCancelled(true);
			delete(true, true);
		}
	}
	
	public void save(){
		main.getInstance().mgr.saveTent1(this);
	}
	
	public void delete(boolean b, boolean a){
		if(b){
			if(block!=null){block.setType(Material.AIR);}
			if(a){
				getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("tent1"));
			}
			for(String s : idList){
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				if(as!=null){
					if(a){
						as.getWorld().playEffect(as.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());
					}
					as.remove();
				}
			}
			main.getInstance().mgr.deleteFromConfig(getID(), "tent1");
		}
		idList.clear();
		main.getInstance().getManager().tent1List.remove(this);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void damage(EntityDamageByEntityEvent e){
		if(e.isCancelled()){return;}
		if(e.getDamager() instanceof Player == false){return;}
		if(e.getEntity() instanceof ArmorStand == false){return;}
		if(e.getEntity() == null){return;}
		if(e.getEntity().getName() == null){return;}
		if(!idList.contains(e.getEntity().getCustomName())){return;}
		e.setCancelled(true);
		if(!main.getInstance().getCheckManager().canBuild((Player) e.getDamager(), getLocation())){return;}
		if(((Player) e.getDamager()).getGameMode().equals(GameMode.CREATIVE)){delete(true, false);return;}
		delete(true, true);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked().getName()==null){return;}
		if(e.getRightClicked() instanceof ArmorStand == false){return;}
		if(!idList.contains(e.getRightClicked().getCustomName())){return;}
		e.setCancelled(true);
		ItemStack is = player.getItemInHand();
		if(is==null){return;}
		if(is!=null){
			if(is.getType().equals(Material.INK_SACK)){
				if(!main.getInstance().getCheckManager().canBuild(player, getLocation())){return;}
				Short druability = is.getDurability();
				Integer amount = is.getAmount();
				if(amount>idList.size() || player.getGameMode().equals(GameMode.CREATIVE)){amount=idList.size();}
				List<Entity> list = new ArrayList<Entity>();
				
				
				for(String s : this.idList){
						ArmorStand as = Utils.getArmorStandAtID(w, s);
						if(as!=null){
							ItemStack item = as.getHelmet();
							if(!as.getHelmet().getType().equals(Material.CARPET)){continue;}
							if(item.getDurability() != main.getFromDey(druability)){list.add(as);}
						}	
				}

				int neAmound = amount;
					for(Entity entity : list){
						if(list.indexOf(entity)>amount-1){break;}
							if(entity instanceof ArmorStand){
									ArmorStand as = (ArmorStand) entity;
									if(!as.getHelmet().getType().equals(Material.CARPET)){neAmound-=1;continue;}
									ItemStack item = as.getHelmet();
									item.setDurability(main.getFromDey(druability));
									as.setHelmet(item);
							}
					}
				if(!player.getGameMode().equals(GameMode.CREATIVE)){
					is.setAmount(is.getAmount()-neAmound);
					player.getInventory().setItem(player.getInventory().getHeldItemSlot(), is);
					player.updateInventory();
				}
				main.getInstance().mgr.saveTent1(this);
				return;
			}
		}
		
		if(block!=null){
			player.openWorkbench(block.getLocation(), true);
		}
	}
	
	public void setColor(HashMap<Integer, Short> durabilityList){
		int i = 0;
		for(String id: idList){
			ArmorStand as = Utils.getArmorStandAtID(w, id);
			if(as!=null){
				if(as.getHelmet()!=null&&!as.getHelmet().getType().equals(Material.AIR)&&as.getHelmet().getType().equals(Material.CARPET)){
					ItemStack is = as.getHelmet();
					is.setDurability(durabilityList.get(i));
					as.setHelmet(is);
					i++;
				}
			}
		}
	}
	
	public HashMap<Integer, Short> getColor(){
		HashMap<Integer, Short> colorList = new HashMap<Integer, Short>();
		Integer i = 0;
		
		for(String id: idList){
			try{i=colorList.size();}catch(Exception e){return colorList;}
			ArmorStand as = Utils.getArmorStandAtID(w, id);
			if(as!=null){
				if(as.getHelmet()!=null&&!as.getHelmet().getType().equals(Material.AIR)&&as.getHelmet().getType().equals(Material.CARPET)){
					ItemStack is = as.getHelmet();
					colorList.put(i, is.getDurability());
				}
			}
		}
		return colorList;
	}
}
