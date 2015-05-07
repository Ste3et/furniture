package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
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

import de.Ste3et_C0st.Furniture.Main.FurnitureCreateEvent;
import de.Ste3et_C0st.Furniture.Main.Permissions;
import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.Furniture.Main.Type.FurnitureType;

public class tent_2 implements Listener {

	private String ID;
	private Location location;
	private List<UUID> idList = new ArrayList<UUID>();
	private BlockFace b;
	public List<Block> block = new ArrayList<Block>();
	public String getID(){return this.ID;}
	public Location getLocation(){return this.location;}
	public BlockFace getBlockFace(){return this.b;}
	private World w;
	
	public tent_2(Location location, Plugin plugin, String ID, List<UUID> uuids) {
		this.b = Utils.yawToFace(location.getYaw());
		this.location = location.getBlock().getLocation();
		this.location.setYaw(location.getYaw());
		this.ID = ID;
		this.w = location.getWorld();
		if(b.equals(BlockFace.WEST)){location=main.getNew(location, b, 1D, 0D);}
		if(b.equals(BlockFace.NORTH)){location=main.getNew(location, b, 1D, 1D);}
		if(b.equals(BlockFace.EAST)){location=main.getNew(location, b, 0D, 1D);}
		FurnitureCreateEvent event = new FurnitureCreateEvent(FurnitureType.TENT_2, this.ID, location);
		Bukkit.getPluginManager().callEvent(event);
		if(!event.isCancelled()){
			if(uuids==null){uuids = idList;}
			spawn(uuids, location, plugin);
		}
	}
	
	public List<String> getList(){
		return Utils.UUIDListToStringList(idList);
	}
	
	@SuppressWarnings("deprecation")
	public void spawn(List<UUID> uuidList, Location location, Plugin plugin){
		location=main.getNew(location, b, -.91D, -0.75D);
		Location LeftLocation = location;
		LeftLocation.add(0,-.75,0);
		Location RightLocation = main.getNew(LeftLocation, b, 0D, -4.55D);
		Location MiddleLocation = main.getNew(LeftLocation, b, 0D, -2.27D).add(0,2.4,0);
		Location LeftSart = main.getNew(LeftLocation, b, 3.87D, -.2D);
		Location RightSart = main.getNew(RightLocation, b, 3.87D, .2D);
		Double d = .0;
		
		for(int i = 0; i<=8;i++){
			Location loc1= main.getNew(LeftLocation, b, d, 0D);
			loc1.setYaw(Utils.FaceToYaw(b));
			Utils.setArmorStand(loc1, new EulerAngle(1.568, 0, 0), new ItemStack(Material.LOG), false,true,false, ID, idList);
			
			Location loc2= main.getNew(RightLocation, b, d, 0D);
			loc2.setYaw(Utils.FaceToYaw(b));
			Utils.setArmorStand(loc2, new EulerAngle(1.568, 0, 0), new ItemStack(Material.LOG), false,true,false, ID, idList);
			
			Location loc3= main.getNew(MiddleLocation, b, d, 0D);
			loc3.setYaw(Utils.FaceToYaw(b));
			Utils.setArmorStand(loc3, new EulerAngle(1.568, 0, 0), new ItemStack(Material.LOG), false,true,false, ID, idList);
			d+=.43;
		}

		d = .0;
		Double l = -.25;
		for(int i = 0;i<=4;i++){
			setRow(LeftSart, .62, l, d, 5,new EulerAngle(0, 0, .79));
			d-=.435;
			l+=.44;
		}
		
		d = .0;
		l = -.25;
		for(int i = 0;i<=4;i++){
			setRow(RightSart, .62, l, d, 5,new EulerAngle(0, 0, -.79));
			d+=.435;
			l+=.44;
		}
		
		Location b1 = main.getNew(getLocation(), b, 1D, -2D);
		Location b2 = main.getNew(getLocation(), b, 2D, -3D);
		b2.setYaw(Utils.FaceToYaw(b));
		b2.getBlock().setType(Material.CHEST);
		BlockState chest = b2.getBlock().getState(); 
		 switch (b){
		 
		 case SOUTH:
		 chest.setRawData((byte) 0x3);break;
		  
		 case NORTH:
		 chest.setRawData((byte) 0x2);break;
		  
		 case EAST:
		 chest.setRawData((byte) 0x5);break;
		  
		 case WEST:
		 chest.setRawData((byte) 0x4);break;
		 default: chest.setRawData((byte) 0x3);break;
		  
		 }
		chest.update(true, true);
		Utils.setBed(this.b, b1);
		
		block.add(b1.getWorld().getBlockAt(b1));
		block.add(b2.getWorld().getBlockAt(b2));
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main.getInstance().getManager().tent2List.add(this);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent e){
		if(e.getBlock()!=null&&block!=null&&block.contains(e.getBlock())){
			e.setCancelled(true);
			if(!Permissions.check(e.getPlayer(), FurnitureType.TENT_2, "destroy.")){return;}
			if(!main.getInstance().getCheckManager().canBuild(e.getPlayer(), getLocation())){return;}
			delete(true, true);
		}
	}
	
	public void delete(boolean b, boolean a){
		if(b){
			if(a){getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("tent2"));}
			for(UUID s : idList){
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				if(as!=null){
					if(a){as.getWorld().playEffect(as.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());}
					as.remove();
				}
			}
			if(block!=null&&!block.isEmpty()){
				for(Block bl : block){
					if(bl!=null){
						bl.setType(Material.AIR);
					}
				}
			}
			
			main.getInstance().mgr.deleteFromConfig(getID(), "tent2");
		}
		idList.clear();
		block.clear();
		main.getInstance().getManager().tent2List.remove(this);
	}
	
	public void save(){
		main.getInstance().mgr.saveTent2(this);
	}
	
	

	public void setRow(Location loc, double x,double y, double z, int replay, EulerAngle angle){
		Double d = .0;
		for(int i = 0; i<=replay;i++){
			Location loc1= main.getNew(loc, b, -3.55+d, z);
			loc1.setYaw(Utils.FaceToYaw(b));
			loc1.add(0, y,0);
			Utils.setArmorStand(loc1, angle, new ItemStack(Material.CARPET), false,false,false, ID, idList);
			d+=x;
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand == false){return;}
		if(!idList.contains(e.getRightClicked().getUniqueId())){return;}
		e.setCancelled(true);
		ItemStack is = player.getItemInHand();
		if(is==null){return;}
		if(!main.getInstance().getCheckManager().canBuild(player, getLocation())){return;}

			if(is.getType().equals(Material.INK_SACK)){
				Short druability = is.getDurability();
				Integer amount = is.getAmount();
				if(amount>idList.size() || player.getGameMode().equals(GameMode.CREATIVE)){amount=idList.size();}
				List<Entity> list = new ArrayList<Entity>();
				for(UUID s : this.idList){
						ArmorStand as = Utils.getArmorStandAtID(w, s);
						if(as!=null){
							ItemStack item = as.getHelmet();
							if(!as.getHelmet().getType().equals(Material.CARPET)){continue;}
							if(item.getDurability() != main.getFromDey(druability)){list.add(as);
						}
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
				main.getInstance().mgr.saveTent2(this);
				return;
			}
			
		if(block==null||block.isEmpty()){return;}
			for(Block b : block){
				if(b.getType().equals(Material.CHEST)){
					Chest c = (Chest) b.getState();
					player.openInventory(c.getBlockInventory());
				}
			}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void damage(EntityDamageByEntityEvent e){
		if(e.isCancelled()){return;}
		if(e.getDamager() instanceof Player == false){return;}
		if(e.getEntity() instanceof ArmorStand == false){return;}
		if(e.getEntity() == null){return;}
		if(!idList.contains(e.getEntity().getUniqueId())){return;}
		e.setCancelled(true);
		if(!Permissions.check((Player) e.getDamager(), FurnitureType.TENT_2, "destroy.")){return;}
		if(!main.getInstance().getCheckManager().canBuild((Player) e.getDamager(), getLocation())){return;}
		if(((Player) e.getDamager()).getGameMode().equals(GameMode.CREATIVE)){delete(true, false);return;}
		delete(true, true);
	}
	
	public void setColor(HashMap<Integer, Short> durabilityList){
		int i = 0;
		for(UUID id: idList){
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
		
		for(UUID id: idList){
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
