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
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.Ste3et_C0st.Furniture.Main.FurnitureCreateEvent;
import de.Ste3et_C0st.Furniture.Main.Permissions;
import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.Furniture.Main.Type.FurnitureType;

public class latern implements Listener {
	private List<UUID> idList = new ArrayList<UUID>();
	private Block b;
	private Location loc = null;
	private BlockFace bFace = null;
	private String id;
	private World w;
	public String getID(){return this.id;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.bFace;}
	
	public latern(Location location, Plugin plugin, String ID, List<UUID> uuids){
			this.loc = location;
			this.id = ID;
			this.bFace = Utils.yawToFace(location.getYaw());
			this.w = location.getWorld();
			
			FurnitureCreateEvent event = new FurnitureCreateEvent(FurnitureType.LANTERN, this.id, location);
			Bukkit.getPluginManager().callEvent(event);
			if(!event.isCancelled()){
				if(uuids==null){uuids = idList;}
				spawn(uuids, location, plugin);
			}
	}
	
	public void spawn(List<UUID> uuidList, Location location, Plugin plugin){
		Location center = Utils.getCenter(getLocation());
		b = center.getWorld().getBlockAt(center);
		b.setType(Material.TORCH);
		
		Location obsidian = center;
		Location l = new Location(center.getWorld(), center.getX(), center.getY() -1.43, center.getZ());
		obsidian.add(0D, -2.2, 0D);
		Location left_down = new Location(obsidian.getWorld(), obsidian.getX()+0.22, obsidian.getY() + .62, obsidian.getZ()+0.22);
		Location left_upper = new Location(obsidian.getWorld(), obsidian.getX() -0.21, obsidian.getY() + .62, obsidian.getZ() +0.22);
		Location right_upper = new Location(obsidian.getWorld(), obsidian.getX()-0.21, obsidian.getY()+.62, obsidian.getZ()-0.21);
		Location right_down = new Location(obsidian.getWorld(), obsidian.getX()+0.21, obsidian.getY() + .62, obsidian.getZ() -0.21);
		
		Utils.setArmorStand(obsidian, null, new ItemStack(Material.OBSIDIAN), false,false,false,getID(),idList);
		Utils.setArmorStand(l.add(0,0,0), null, new ItemStack(Material.WOOD_PLATE), false,false,false,getID(), idList);
		
		Utils.setArmorStand(left_down, null, new ItemStack(Material.LEVER), false,false,false,getID(), idList);
		Utils.setArmorStand(left_upper, null, new ItemStack(Material.LEVER), false,false,false,getID(), idList);
		Utils.setArmorStand(right_upper, null, new ItemStack(Material.LEVER), false,false,false,getID(), idList);
		Utils.setArmorStand(right_down, null, new ItemStack(Material.LEVER), false,false,false,getID(), idList);
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		main.getInstance().getManager().lanternList.add(this);
	}
	
	public List<String> getList(){
		return Utils.UUIDListToStringList(idList);
	}
	
	@EventHandler
	public void onWaterFlow(BlockFromToEvent e){
		Location locTo = e.getToBlock().getLocation();
		if(loc!=null && locTo.equals(loc.getBlock().getLocation())){
			e.setCancelled(true);
		}
	}
	
	public void delete(Boolean b, boolean a){
		if(b){
			if(a){getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("lantern"));}
			for(UUID s : idList){
				ArmorStand as = Utils.getArmorStandAtID(w,s);
				if(as!=null){
					as.getWorld().playEffect(as.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());
					as.remove();
				}
			}
			main.getInstance().mgr.deleteFromConfig(getID(), "lantern");
		}
		if(this.b!=null&&!this.b.getType().equals(Material.AIR)){this.b.setType(Material.AIR);}
		this.loc = null;
		this.b = null;
		this.idList.clear();
		main.getInstance().getManager().lanternList.remove(this);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if(e.getBlock()!=null&&b!=null&&e.getBlock().equals(b)){
			e.setCancelled(true);
			delete(true, true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() == null){return;}
		if(e.getRightClicked() instanceof ArmorStand == false){ return;}
		if(idList==null||idList.isEmpty()){return;}
		if(!this.idList.contains(e.getRightClicked().getUniqueId())){return;}
		e.setCancelled(true);
		if(!main.getInstance().getCheckManager().canBuild(player, getLocation())){return;}
			ItemStack is = player.getItemInHand();
			if(is!=null){
				if(is.getType().equals(Material.FLINT_AND_STEEL)){
					setLight(true);
				}else if(is.getType().equals(Material.WATER_BUCKET)){
					setLight(false);
				}
				main.getInstance().mgr.saveLatern(this);
			}
	}

	public void save(){
		main.getInstance().mgr.saveLatern(this);
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
		if(!Permissions.check((Player) e.getDamager(), FurnitureType.LANTERN, "destroy.")){return;}
		if(!main.getInstance().getCheckManager().canBuild((Player) e.getDamager(), getLocation())){return;}
		if(((Player) e.getDamager()).getGameMode().equals(GameMode.CREATIVE)){delete(true, false);return;}
		delete(true, true);
	}
	
	public void setLight(boolean bool){
		if(bool){b.setType(Material.TORCH);}
		else{b.setType(Material.REDSTONE_TORCH_OFF);}
	}
	
	public boolean getLight(){
		if(b==null||b.getType().equals(Material.AIR)||b.getType().equals(Material.TORCH)){
			return true;
		}
		return false;
	}
}
