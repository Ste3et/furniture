package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
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

public class chair implements Listener {

	List<UUID> idList = new ArrayList<UUID>();
	Location loc = null;
	BlockFace b = null;
	World w = null;
	private String id;
	public String getID(){return this.id;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public chair(Location location, Plugin plugin, String ID, List<UUID> uuids){
		this.b = Utils.yawToFace(location.getYaw());
		this.loc = location.getBlock().getLocation();
		this.loc.setYaw(location.getYaw());
		this.id = ID;
		this.w = location.getWorld();
		FurnitureCreateEvent event = new FurnitureCreateEvent(FurnitureType.CHAIR, this.id, location);
		Bukkit.getPluginManager().callEvent(event);
		if(!event.isCancelled()){
			if(uuids==null){uuids = idList;}
			spawn(uuids, location, plugin);
		}
	}
	
	public void spawn(List<UUID> uuidList, Location location, Plugin plugin){
		BlockFace b = Utils.yawToFace(loc.getYaw()).getOppositeFace();
		Location center = Utils.getCenter(loc);
		Location sitz = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location feet1 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location feet2 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location feet3 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location feet4 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
		Location lehne = main.getNew(center.add(0,-1.1,0), b, -.25, .0);
		feet1.add(-.25,-1.8,-.25);
		feet2.add(.25,-1.8,-.25);
		feet3.add(.25,-1.8,.25);
		feet4.add(-.25,-1.8,.25);
		
		sitz.add(0,-1.45,0);
		sitz.setYaw(Utils.FaceToYaw(b));
		lehne.setYaw(Utils.FaceToYaw(b));
		
		Utils.setArmorStand(sitz, null, new ItemStack(Material.TRAP_DOOR), false,false,false,getID(),idList);
		Utils.setArmorStand(lehne, new EulerAngle(1.57, .0, .0), new ItemStack(Material.TRAP_DOOR), false,false,false,getID(),idList);
		Utils.setArmorStand(feet1, null, new ItemStack(Material.LEVER), false,false,false,getID(),idList);
		Utils.setArmorStand(feet2, null, new ItemStack(Material.LEVER), false,false,false,getID(),idList);
		Utils.setArmorStand(feet3, null, new ItemStack(Material.LEVER), false,false,false,getID(),idList);
		Utils.setArmorStand(feet4, null, new ItemStack(Material.LEVER), false,false,false,getID(),idList);
		Utils.setArmorStand(sitz.add(0,-.2,0), null, null, false,false,false,getID(),idList);
		main.getInstance().getManager().chairList.add(this);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void save(){
		main.getInstance().mgr.saveStuhl(this);
	}
	
	public List<String> getList(){
		return Utils.UUIDListToStringList(idList);
	}
	
	@EventHandler
	public void onWaterFlow(BlockFromToEvent e){
		Location locTo = e.getToBlock().getLocation();
		if(loc!=null && locTo.equals(loc)){
			e.setCancelled(true);
		}
	}
	
	/*
	@EventHandler
	public void onHit(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(!p.isInsideVehicle() && loc!=null){
			if(e.getTo().getBlock().getLocation().equals(loc.getBlock().getLocation())){
				p.teleport(e.getFrom());
			}
		}
	}
	*/

	public void delete(Boolean b, Boolean a){
		if(b){
			if(a){getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("chair"));}
			if(idList!=null&&!idList.isEmpty()){
				for(UUID ids : idList){
					if(ids==null){continue;}
					ArmorStand as = Utils.getArmorStandAtID(w, ids);
					if(as!=null){
						if(a){getLocation().getWorld().playEffect(as.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());}	
						as.remove();
						main.getInstance().mgr.deleteFromConfig(getID(), "chair");
					}
				}
			}
		}
		this.loc = null;
		idList.clear();
		main.getInstance().getManager().chairList.remove(this);
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
	
	@EventHandler(priority = EventPriority.HIGHEST)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() == null){return;}
		if(e.getRightClicked() instanceof ArmorStand == false){return;}
		if(idList==null||idList.isEmpty()){return;}
		if(!idList.contains(e.getRightClicked().getUniqueId())){return;}
		e.setCancelled(true);
		Utils.getArmorStandAtID(w, idList.get(6)).setPassenger(player);
	}
}
