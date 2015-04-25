package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import ru.BeYkeRYkt.LightAPI.LightAPI;
import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;

public class campfire_1 implements Listener {

	private Location loc;
	private String ID;
	private BlockFace b;
	private List<String> idList = new ArrayList<String>();
	private World w;
	public String getID(){return this.ID;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public campfire_1(Location location, Plugin plugin, String ID) {
		this.loc = location.getBlock().getLocation();
		this.loc.setYaw(location.getYaw());
		this.ID = ID;
		this.w = location.getWorld();
		this.b = Utils.yawToFace(location.getYaw());
		
		for(int i = 0;i<=3;i++){
			Location loc = Utils.getCenter(getLocation());
			loc.add(0,-1.9,0);
			loc.setYaw(i*60);
			Utils.setArmorStand(loc, null, null, false, false,true, getID(), idList);
		}
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main.getInstance().getManager().campfire1List.add(this);
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
	
	public void delete(boolean b, boolean a){
		setLight(false, false);
		if(b){
			if(a){getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("campfire1"));}
			for(String s : idList){
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				if(as!=null){
					if(a){as.getWorld().playEffect(as.getLocation(), Effect.STEP_SOUND, Material.LOG);}
					as.remove();
				}
			}
			main.getInstance().mgr.deleteFromConfig(getID(), "campfire1");
		}
		idList.clear();
		main.getInstance().getManager().campfire1List.remove(this);
	}
	
	public void save(){
		main.getInstance().mgr.saveCampFire1(this);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand){
			if(this.idList.contains(e.getRightClicked().getName())){
				e.setCancelled(true);
				if(!main.getInstance().getCheckManager().canBuild(player, getLocation())){return;}
				ItemStack is = player.getItemInHand();
				if(is!=null){
					if(is.getType().equals(Material.FLINT_AND_STEEL)){
						setLight(true, true);
					}else if(is.getType().equals(Material.WATER_BUCKET)){
						setLight(false, true);
					}
					save();
				}
			}
		}
	}
	
	@EventHandler
	public void onBurn(EntityDamageEvent e){
		if(e.getEntity() instanceof ArmorStand && (e.getCause().name().equalsIgnoreCase("FIRE") || e.getCause().name().equalsIgnoreCase("FIRE_TICK"))){
			if(idList.contains(e.getEntity().getCustomName())){
				e.setCancelled(true);
			}
		}
	}
	public void setLight(boolean boolean1, boolean a) {
		Location light = new Location(getLocation().getWorld(), getLocation().getX(), getLocation().getY(), getLocation().getZ());
		for(String s : idList){
			if(Utils.getArmorStandAtID(w, s) != null){
				setFire(Utils.getArmorStandAtID(w, s), boolean1);
			}
		}
		
		if(boolean1){
			if(main.getInstance().light){LightAPI.createLight(light, 15);}
			light.getWorld().playSound(light, Sound.FIRE_IGNITE, 1, 1);
		}else{
			if(main.getInstance().light){LightAPI.deleteLight(light);}
			if(a){light.getWorld().playSound(light, Sound.FIZZ, 1, 1);}
		}
	}
	
	public boolean getFire(){
		for(String s : idList){
			if(Utils.getArmorStandAtID(w, s) != null){
				if(Utils.getArmorStandAtID(w, s).getFireTicks()>=1){
					return true;
				}
			}
		}
		return false;
	}
	
	public void setFire(ArmorStand as, boolean b){
		if(as.getFireTicks() <= 0 && b){
			as.setFireTicks(Integer.MAX_VALUE);
		}else if(as.getFireTicks() >= 0 && !b){
			as.setFireTicks(0);
		}
	}
}
