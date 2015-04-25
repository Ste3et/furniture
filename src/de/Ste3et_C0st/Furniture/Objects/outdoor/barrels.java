package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import ru.BeYkeRYkt.LightAPI.LightAPI;
import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;

public class barrels implements Listener {

	private Location loc;
	private BlockFace b;
	private ArmorStand as;
	private Block block;
	private String id;
	private List<String> idList = new ArrayList<String>();
	public String getID(){return this.id;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public barrels(Location loc, Plugin plugin, String ID){
		this.id = ID;
		this.loc = loc.getBlock().getLocation();
		this.loc.setYaw(loc.getYaw());
		this.b = Utils.yawToFace(loc.getYaw());
		
		this.block = loc.getBlock();
		this.block.setType(Material.CAULDRON);

		this.as = Utils.setArmorStand(Utils.getCenter(loc).add(0,-1.5,0), null, null, false, false, false, ID, idList);
		
		main.getInstance().getManager().barrelList.add(this);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	public void save(){
		main.getInstance().mgr.saveBarrel(this);
	}
	
	public ItemStack getItemStack() {
		if(this.as != null && this.as.getHelmet()!=null){
			return this.as.getHelmet();
		}
		return null;
	}
	
	public void setItemstack(ItemStack is){
		if(is==null){return;}
		this.as.setHelmet(is);
	}
	
	public void delete(boolean b, boolean a){
		if(b){
			setLight(false);
			if(a){getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("barrels"));}
			if(as!=null&&as.getHelmet()!=null &&!as.getHelmet().getType().equals(Material.AIR)){
				as.getLocation().getWorld().dropItem(as.getLocation(), as.getHelmet());
			}
			if(this.block!=null&&!this.block.getType().equals(Material.AIR)){
				this.block.setType(Material.AIR);
			}
			as.remove();
			main.getInstance().mgr.deleteFromConfig(getID(), "barrels");
		}
		this.loc = null;
		this.idList.clear();
		this.id = null;
		this.as = null;
		main.getInstance().getManager().barrelList.remove(this);
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
	private void onBreak(BlockBreakEvent e){
		if(block!=null&&e.getBlock().equals(block)){
			e.setCancelled(true);
			if(((Player) e.getPlayer()).getGameMode().equals(GameMode.CREATIVE)){delete(true, false);return;}
			delete(true, true);
		}
	}
	
	public void setLight(Boolean b){
		if(main.getInstance().light){
			if(b){
				LightAPI.createLight(Utils.getCenter(getLocation()), 10);
			}else{
				LightAPI.deleteLight(Utils.getCenter(getLocation()));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked()==null){return;}
		if(e.getRightClicked() instanceof ArmorStand == false){return;}
		if(idList==null||idList.isEmpty()){return;}
		if(!idList.contains(e.getRightClicked().getCustomName())){return;}
			e.setCancelled(true);
			if(!main.getInstance().getCheckManager().canBuild(player, getLocation())){return;}
			ItemStack is = player.getItemInHand();
			if(is!=null&&is.getType().isBlock()||is.getType().equals(Material.AIR)){
				if(as!=null){
					if(as.getHelmet()!=null&&!as.getHelmet().getType().equals(Material.AIR)){as.getLocation().getWorld().dropItem(as.getLocation(), as.getHelmet());}
					setItemstack(is);
					if(is.getType().equals(Material.GLOWSTONE) || is.getType().equals(Material.BEACON) || is.getType().equals(Material.SEA_LANTERN) ){setLight(true);}else{setLight(false);}
					if(!player.getGameMode().equals(GameMode.CREATIVE)){player.getInventory().remove(is);player.updateInventory();}
					main.getInstance().mgr.saveBarrel(this);
				}
			}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	private void onInteract(PlayerInteractEvent e){
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(block!=null&&e.getClickedBlock()!=null&&e.getClickedBlock().equals(block)){
				e.setCancelled(true);
				Player player = e.getPlayer();
				if(!main.getInstance().getCheckManager().canBuild(player, getLocation())){return;}
				ItemStack is = player.getItemInHand();
				if(is!=null&&is.getType().isBlock()||is.getType().equals(Material.AIR)){
					if(as!=null){
						if(as.getHelmet()!=null&&!as.getHelmet().getType().equals(Material.AIR)){as.getLocation().getWorld().dropItem(as.getLocation(), as.getHelmet());}
						setItemstack(is);
						if(is.getType().equals(Material.GLOWSTONE) || is.getType().equals(Material.BEACON) || is.getType().equals(Material.SEA_LANTERN) ){setLight(true);}else{setLight(false);}
						if(!player.getGameMode().equals(GameMode.CREATIVE)){player.getInventory().remove(is);player.updateInventory();}
						main.getInstance().mgr.saveBarrel(this);
					}
				}
			}
		}
	}
}
