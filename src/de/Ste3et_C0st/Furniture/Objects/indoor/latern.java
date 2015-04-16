package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
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

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;

public class latern implements Listener {
	private List<String> idList = new ArrayList<String>();
	private Block b;
	private Location loc = null;
	private Boolean bool = true;
	private BlockFace bFace = null;
	private String id;
	private World w;
	public String getID(){return this.id;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.bFace;}
	public Boolean getBlockState(){return this.bool;}
	
	public latern(Location loc, Plugin plugin, Boolean bool, String id){
			this.loc = loc;
			this.bool = bool;
			this.id = id;
			this.bFace = Utils.yawToFace(loc.getYaw());
			this.w = loc.getWorld();
			Location center = Utils.getCenter(loc);
			b = center.getWorld().getBlockAt(center);
			if(bool){
				b.setType(Material.TORCH);
			}else{
				b.setType(Material.REDSTONE_TORCH_OFF);
			}
			
			Location obsidian = center;
			Location location = new Location(center.getWorld(), center.getX(), center.getY() -1.43, center.getZ());
			obsidian.add(0D, -2.2, 0D);
			Location left_down = new Location(obsidian.getWorld(), obsidian.getX()+0.22, obsidian.getY() + .62, obsidian.getZ()+0.22);
			Location left_upper = new Location(obsidian.getWorld(), obsidian.getX() -0.21, obsidian.getY() + .62, obsidian.getZ() +0.22);
			Location right_upper = new Location(obsidian.getWorld(), obsidian.getX()-0.21, obsidian.getY()+.62, obsidian.getZ()-0.21);
			Location right_down = new Location(obsidian.getWorld(), obsidian.getX()+0.21, obsidian.getY() + .62, obsidian.getZ() -0.21);
			
			Utils.setArmorStand(obsidian, null, new ItemStack(Material.OBSIDIAN), false,getID(),idList);
			Utils.setArmorStand(location.add(0,0,0), null, new ItemStack(Material.WOOD_PLATE), false,getID(), idList);
			
			Utils.setArmorStand(left_down, null, new ItemStack(Material.LEVER), false,getID(), idList);
			Utils.setArmorStand(left_upper, null, new ItemStack(Material.LEVER), false,getID(), idList);
			Utils.setArmorStand(right_upper, null, new ItemStack(Material.LEVER), false,getID(), idList);
			Utils.setArmorStand(right_down, null, new ItemStack(Material.LEVER), false,getID(), idList);
			Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
			main.getInstance().laternen.add(this);
	}
	
	public boolean check(Player player, Location loc){
		if(!loc.getBlock().equals(Material.AIR)){
			return false;
		}
		return true;
	}
	
	@EventHandler
	public void onWaterFlow(BlockFromToEvent e){
		Location locTo = e.getToBlock().getLocation();
		if(loc!=null && locTo.equals(loc.getBlock().getLocation())){
			e.setCancelled(true);
		}
	}
	
	public void delete(Boolean b){
		
		if(b){
			getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("lantern"));
			for(String s : idList){
				ArmorStand as = Utils.getArmorStandAtID(w,s);
				as.getWorld().playEffect(as.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());
				as.remove();
				main.getInstance().mgr.deleteFromConfig(getID(), "lantern");
			}
		}
		if(this.b!=null&&!this.b.getType().equals(Material.AIR)){this.b.setType(Material.AIR);}
		this.bool = null;
		this.loc = null;
		this.idList.clear();
		main.getInstance().laternen.remove(this);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if(e.getBlock()!=null&&e.getBlock().equals(b)){
			delete(true);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand){
			if(this.idList.contains(e.getRightClicked().getName())){
				e.setCancelled(true);
				ItemStack is = player.getItemInHand();
				if(is!=null){
					if(is.getType().equals(Material.FLINT_AND_STEEL)){
						b.setType(Material.TORCH);
						bool = true;
					}else if(is.getType().equals(Material.WATER_BUCKET)){
						b.setType(Material.REDSTONE_TORCH_OFF);
						bool = false;
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
				if(this.idList.contains(e.getEntity().getName())){
					delete(true);
				}
			}
		}
	}
}
