package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
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

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;

public class chair implements Listener {

	List<String> IDList = new ArrayList<String>();
	Location loc = null;
	BlockFace b = null;
	World w = null;
	private String id;
	public String getID(){return this.id;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public chair(Location loc, Plugin plugin, String id){
		this.loc = loc.getBlock().getLocation();
		this.id = id;
		this.b = Utils.yawToFace(loc.getYaw());
		this.w = this.loc.getWorld();
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
		
		Utils.setArmorStand(sitz, null, new ItemStack(Material.TRAP_DOOR), false,getID(),IDList);
		Utils.setArmorStand(lehne, new EulerAngle(1.57, .0, .0), new ItemStack(Material.TRAP_DOOR), false,getID(),IDList);
		Utils.setArmorStand(feet1, null, new ItemStack(Material.LEVER), false,getID(),IDList);
		Utils.setArmorStand(feet2, null, new ItemStack(Material.LEVER), false,getID(),IDList);
		Utils.setArmorStand(feet3, null, new ItemStack(Material.LEVER), false,getID(),IDList);
		Utils.setArmorStand(feet4, null, new ItemStack(Material.LEVER), false,getID(),IDList);
		main.getInstance().stuehle.add(this);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
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
	
	@EventHandler(priority = EventPriority.NORMAL)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand){
			if(IDList.contains(e.getRightClicked().getCustomName())){
				e.setCancelled(true);
				Utils.getArmorStandAtID(w, IDList.get(0)).setPassenger(player);
			}
		}
	}

	public void delete(Boolean b){
		if(b){
			getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("chair"));
			for(String ids : IDList){
				ArmorStand as = Utils.getArmorStandAtID(w, ids);
				getLocation().getWorld().playEffect(as.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());
				as.remove();
				main.getInstance().mgr.deleteFromConfig(getID(), "chair");
			}
		}
		this.loc = null;
		IDList.clear();
		main.getInstance().stuehle.remove(this);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void damage(EntityDamageByEntityEvent e){
		if(e.isCancelled()){return;}
		if(e.getDamager() instanceof Player){
			if(e.getEntity() instanceof ArmorStand){
				if(IDList.contains(e.getEntity().getCustomName())){
					delete(true);
				}
			}
		}
	}
}
