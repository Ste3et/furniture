package de.Ste3et_C0st.Furniture.Objects.indoor;

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

import de.Ste3et_C0st.Furniture.Main.FurnitureCreateEvent;
import de.Ste3et_C0st.Furniture.Main.Permissions;
import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.Furniture.Main.Type.FurnitureType;

public class sofa implements Listener {
	private List<UUID> idList = new ArrayList<UUID>();
	private List<UUID> sitzList = new ArrayList<UUID>();
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
	
	public sofa(Location location, Plugin plugin, String ID, List<UUID> uuids){
		this.w = location.getWorld();
		this.loc = location.getBlock().getLocation();
		this.loc.setYaw(location.getYaw());
		this.place = 0.2;
		this.id = ID;
		this.b = Utils.yawToFace(location.getYaw());
			FurnitureCreateEvent event = new FurnitureCreateEvent(FurnitureType.SOFA, this.id, location);
			Bukkit.getPluginManager().callEvent(event);
			if(!event.isCancelled()){
				if(uuids==null){uuids = idList;}
				spawn(uuids, location, plugin);
			}
	}
	
	public void spawn(List<UUID> uuidList, Location location, Plugin plugin){
		List<Entity> sitz = new ArrayList<Entity>();
		Integer lengt = 3;
		is = new ItemStack(Material.CARPET);
		is.setDurability(color);
		BlockFace b = Utils.yawToFace(location.getYaw()).getOppositeFace();
		
		Integer x = (int) location.getX();
		Integer y = (int) location.getY();
		Integer z = (int) location.getZ();
		location.setX(x);
		location.setY(y);
		location.setZ(z);
		
		if(b.equals(BlockFace.WEST)){location = main.getNew(location, b, .0, -1.0);}
		if(b.equals(BlockFace.SOUTH)){location = main.getNew(location, b, -1.0, -1.0);}
		if(b.equals(BlockFace.EAST)){location = main.getNew(location, b, -1.0, .0);}
			Location looking = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() -1.4 , location.getBlockZ());
			Location feet1 = main.getNew(looking, b, place, .2D);
			Location feet2 = main.getNew(looking, b, place, lengt.doubleValue()-.2D);
			Location feet3 = main.getNew(looking, b, place + .5, .2D);
			Location feet4 = main.getNew(looking, b, place + .5, lengt.doubleValue()-.2D);
			
			Utils.setArmorStand(feet1, null, new ItemStack(Material.LEVER), false,false,false,getID(),idList);
			Utils.setArmorStand(feet2, null, new ItemStack(Material.LEVER), false,false,false,getID(),idList);
			Utils.setArmorStand(feet3, null, new ItemStack(Material.LEVER), false,false,false,getID(),idList);
			Utils.setArmorStand(feet4, null, new ItemStack(Material.LEVER), false,false,false,getID(),idList);

			Location carpetHight = new Location(looking.getWorld(), location.getBlockX(), location.getBlockY() -1 , location.getBlockZ());
			carpetHight.setYaw(Utils.FaceToYaw(b));
			carpetHight = main.getNew(carpetHight, b, .25,.3);
			Double d = .02;
			float facing = Utils.FaceToYaw(b);
			for(Double i = .0; i<=lengt; i+=0.65){
				Location carpet = main.getNew(carpetHight, b, place,(double) d);
				carpet.setYaw(facing);
				ArmorStand as = null;
				as = Utils.setArmorStand(carpet, null, is, false,false,false,getID(),idList);
				sitz.add(as);
				//OBERER TEIL
				Location loc = main.getNew(carpetHight, b, place-.25,(double) d);
				loc.setYaw(facing);
				Utils.setArmorStand(loc, new EulerAngle(1.57, .0, .0), is, false,false,false,getID(),idList);
				if(d<=0D){d = 0.00;}
				d+=.58;
			}
			
			Float yaw1= facing;
			Float yaw2= facing;
			Location last = main.getNew(sitz.get(sitz.size()-1).getLocation(), b, 0D, 0.26D);
			last.setYaw(yaw1+90);
			Location first = main.getNew(new Location(location.getWorld(), location.getX(), last.getY(), location.getZ()), b, place+.25, 0.07D);
			first.setYaw(yaw2-90);
			
			Utils.setArmorStand(first.add(0,-.05,0), new EulerAngle(1.57, .0, .0), is, false,false,false,getID(),idList);
			Utils.setArmorStand(last.add(0,-.05,0), new EulerAngle(1.57, .0, .0), is, false,false,false,getID(),idList);
			
			Location start = main.getNew(looking, b, .45, .55);
			for(int i = 0; i<=2;i++){
				Location loc = main.getNew(start, b, 0D, i*.95D);
				loc.setYaw(Utils.FaceToYaw(b));
				loc.add(0,.2,0);
				ArmorStand as = Utils.setArmorStand(loc, null, null, false, false, false, getID(), idList);
				sitzList.add(as.getUniqueId());
			}
			
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
			main.getInstance().getManager().sofaList.add(this);
			
	}
	
	public List<String> getList(){
		return Utils.UUIDListToStringList(idList);
	}
	
	public void save(){
		main.getInstance().mgr.saveSofa(this);
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
	
	public void delete(Boolean b, boolean a){
		if(b){
			if(a){getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("sofa"));}
			
			for(UUID s : idList){
				ArmorStand as = Utils.getArmorStandAtID(this.w, s);
				if(as!=null){
					if(a){as.getWorld().playEffect(as.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());}
					
					as.remove();
					main.getInstance().mgr.deleteFromConfig(getID(), "sofa");
				}
			}
		}
		idList.clear();
		main.getInstance().getManager().sofaList.remove(this);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked()==null){return;}
		if(e.getRightClicked() instanceof ArmorStand == false){return;}
		if(idList==null||idList.isEmpty()){return;}
		e.setCancelled(true);
		ItemStack is = player.getItemInHand();
		if(is!=null){
			if(is.getType().equals(Material.INK_SACK)){
				if(!main.getInstance().getCheckManager().canBuild(player, getLocation())){return;}
				Short druability = is.getDurability();
				Integer amount = is.getAmount();
				if(amount>idList.size() || player.getGameMode().equals(GameMode.CREATIVE)){amount=idList.size();}
				List<Entity> list = new ArrayList<Entity>();
				for(UUID s : idList){
						ArmorStand as = Utils.getArmorStandAtID(w, s);
						if(as.getHelmet().getType().equals(Material.CARPET)){
							ItemStack item = as.getHelmet();
							if(item.getDurability() != main.getFromDey(druability)){
								list.add(as);
							}
						}
				}
				for(Entity entity : list){
					if(entity instanceof ArmorStand){
						if(list.indexOf(entity)>amount-1){break;}
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
				main.getInstance().mgr.saveSofa(this);
				return;
			}
		}
		
		Integer intSitz = Utils.randInt(0, 2); 
		for(UUID s : sitzList){
			if(s==null){return;}
			ArmorStand as = Utils.getArmorStandAtID(w, s);
			if(as==null){return;}
			if(sitzList.indexOf(s) == intSitz){
				if(as.getPassenger()==null){
					as.setPassenger(player);
					break;
				}
			}
		}
	}
	
	public boolean checkIfEmpty(){
		for(UUID id : idList){
			ArmorStand armorStand = Utils.getArmorStandAtID(w, id);
			if(armorStand!=null){
				if(armorStand.getHeadPose().equals(new EulerAngle(1.57, .0, .0)) && armorStand.getHelmet().getType().equals(Material.CARPET)){
					if(armorStand.getPassenger() != null && !armorStand.getPassenger().isEmpty()){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void damage(EntityDamageByEntityEvent e){
		if(e.isCancelled()){return;}
		if(e.getDamager() instanceof Player == false){return;}
		if(e.getEntity() instanceof ArmorStand == false){return;}
		if(e.getEntity() == null){return;}
		if(e.getEntity().getUniqueId() == null){return;}
		if(!idList.contains(e.getEntity().getUniqueId())){return;}
		e.setCancelled(true);
		if(!Permissions.check((Player) e.getDamager(), FurnitureType.TENT_2, "destroy.")){return;}
		if(!main.getInstance().getCheckManager().canBuild((Player) e.getDamager(), getLocation())){return;}
		if(((Player) e.getDamager()).getGameMode().equals(GameMode.CREATIVE)){delete(true, false);return;}
		delete(true, true);
	}
}
