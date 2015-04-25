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
	
	public table(Location loc, Plugin plugin, String id){
		this.loc = loc.getBlock().getLocation();
		this.id = id;
		this.b = Utils.yawToFace(loc.getYaw());
		this.w = loc.getWorld();
		Location middle1 = Utils.getCenter(loc);
		Location middle2 = Utils.getCenter(loc);
		Utils.setArmorStand(middle1.add(0,-2.1,0),new EulerAngle(0, 0, 0) , new ItemStack(Material.WOOD_PLATE), false,false,false,getID(),idList);
		Utils.setArmorStand(middle2.add(0,-1.05,0),new EulerAngle(0, 0, 0) , new ItemStack(Material.TRAP_DOOR), false,false,false,getID(),idList);	
		loc.setYaw(0);
		ArmorStand as = Utils.setArmorStand(loc.add(.9,0.15,0.3),new EulerAngle(0,.0,.0), is, true,false,false,getID(),idList);	
		Utils.setArmorStand(loc.add(0,-.65,.68),new EulerAngle(1.38,.0,.0), new ItemStack(Material.STICK), true,false,false,getID(),idList);	
		this.armor = as;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main.getInstance().getManager().tableList.add(this);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked().getName()==null){return;}
		if(e.getRightClicked() instanceof ArmorStand == false){return;}
		if(idList==null||idList.isEmpty()){return;}
		if(!idList.contains(e.getRightClicked().getCustomName())){return;}
		e.setCancelled(true);
		if(!main.getInstance().getCheckManager().canBuild(player, getLocation())){return;}
		ItemStack is = player.getItemInHand();
		if(is!=null&&!is.getType().isBlock()||is.getType().equals(Material.AIR)){
				if(armor!=null){
					ArmorStand as = Utils.getArmorStandAtID(w,idList.get(2));
					if(as!=null&&armor.getItemInHand()!=null&&!armor.getItemInHand().getType().equals(Material.AIR)){as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());}
					as.setItemInHand(is);
					this.is = is;
					if(!player.getGameMode().equals(GameMode.CREATIVE)){player.getInventory().remove(is);player.updateInventory();}
					main.getInstance().mgr.saveTable(this);
			}
		}
	}
	
	public void delete(Boolean b, boolean a){
		if(b){
			if(a){getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("table"));}
			if(is!=null && !is.getType().equals(Material.AIR)){
				ArmorStand as = Utils.getArmorStandAtID(w,idList.get(2));
				if(as!=null){
					as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());
					this.is = null;
				}
				
			}
			for(String s : idList){
				ArmorStand as = Utils.getArmorStandAtID(w,s);
				if(as!=null){
					if(a){loc.getWorld().playEffect(loc, Effect.STEP_SOUND, as.getHelmet().getType());}
					as.remove();
				}
				
			}
			main.getInstance().mgr.deleteFromConfig(getID(), "table");
		}

		this.loc = null;
		this.idList.clear();
		main.getInstance().getManager().tableList.remove(this);
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
	
	public void setItem(ItemStack itemS) {
		is = itemS;
		ArmorStand as = Utils.getArmorStandAtID(w,idList.get(2));
		as.setItemInHand(itemS);
	}
	public void save() {
		main.getInstance().mgr.saveTable(this);
	}
}
