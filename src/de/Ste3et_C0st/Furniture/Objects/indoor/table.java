package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.List;

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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;

public class table implements Listener {

	private List<String> idList = new ArrayList<String>();
	private ArmorStand armor;
	private ItemStack is = null;
	private Location loc = null;
	private BlockFace b = null;
	private String id;
	private World w;
	public String getID(){return this.id;}
	public Location getLocation(){return this.loc;}
	public ItemStack getItemStack(){return this.is;}
	public BlockFace getBlockFace(){return this.b;}
	
	public table(Location loc, Plugin plugin, ItemStack is, String id){
		this.loc = loc.getBlock().getLocation();
		this.id = id;
		this.b = Utils.yawToFace(loc.getYaw());
		this.w = loc.getWorld();
		Location middle1 = Utils.getCenter(loc);
		Location middle2 = Utils.getCenter(loc);
		Utils.setArmorStand(middle1.add(0,-2.1,0),new EulerAngle(0, 0, 0) , new ItemStack(Material.WOOD_PLATE), false,getID(),idList);
		Utils.setArmorStand(middle2.add(0,-1.05,0),new EulerAngle(0, 0, 0) , new ItemStack(Material.TRAP_DOOR), false,getID(),idList);	
		loc.setYaw(0);
		ArmorStand as = Utils.setArmorStand(loc.add(.9,0.15,0.3),new EulerAngle(0,.0,.0), is, true,getID(),idList);	
		Utils.setArmorStand(loc.add(0,-.65,.68),new EulerAngle(1.38,.0,.0), new ItemStack(Material.STICK), true,getID(),idList);	
		this.armor = as;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main.getInstance().tische.add(this);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand){
			if(idList.contains(e.getRightClicked().getCustomName())){
				e.setCancelled(true);
				ItemStack is = player.getItemInHand();
				if(is!=null&&!is.getType().isBlock()||is.getType().equals(Material.AIR)){
					if(armor!=null){
						ArmorStand as = Utils.getArmorStandAtID(w,idList.get(2));
						if(armor.getItemInHand()!=null&&!armor.getItemInHand().getType().equals(Material.AIR)){as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());}
						as.setItemInHand(is);
						this.is = is;
						
						if(!player.getGameMode().equals(GameMode.CREATIVE)){player.getInventory().remove(is);player.updateInventory();}
					}
				}
			}
		}
	}
	
	public void delete(Boolean b){
		if(b){
			getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("table"));
			if(is!=null && !is.getType().equals(Material.AIR)){
				ArmorStand as = Utils.getArmorStandAtID(w,idList.get(2));
				as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());
				this.is = null;
			}
			for(String s : idList){
				ArmorStand as = Utils.getArmorStandAtID(w,s);
				loc.getWorld().playEffect(loc, Effect.STEP_SOUND, as.getHelmet().getType());
				as.remove();
			}
			main.getInstance().mgr.deleteFromConfig(getID(), "table");
		}

		this.loc = null;
		this.idList.clear();
		main.getInstance().tische.remove(this);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void damage(EntityDamageByEntityEvent e){
		if(e.isCancelled()){return;}
		if(e.getDamager() instanceof Player){
			if(e.getEntity() instanceof ArmorStand){
				if(idList.contains(e.getEntity().getCustomName())){
					delete(true);
				}
			}
		}
	}
}
