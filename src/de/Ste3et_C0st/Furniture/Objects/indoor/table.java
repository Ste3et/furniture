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

public class table implements Listener {

	private List<UUID> idList = new ArrayList<UUID>();
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
	
	public table(Location location, Plugin plugin, String ID, List<UUID> uuids){
		this.loc = location.getBlock().getLocation();
		this.loc.setYaw(location.getYaw());
		this.id = ID;
		this.b = Utils.yawToFace(location.getYaw());
		this.w = location.getWorld();
		
		FurnitureCreateEvent event = new FurnitureCreateEvent(FurnitureType.TABLE, this.id, location);
		Bukkit.getPluginManager().callEvent(event);
		if(!event.isCancelled()){
			if(uuids==null){uuids = idList;}
			spawn(uuids, location, plugin);
		}
	}
	
	public void spawn(List<UUID> uuidList, Location location, Plugin plugin){
		Location middle1 = Utils.getCenter(getLocation());
		Location middle2 = Utils.getCenter(getLocation());
		Utils.setArmorStand(middle1.add(0,-2.1,0),new EulerAngle(0, 0, 0) , new ItemStack(Material.WOOD_PLATE), false,false,false,getID(),idList);
		Utils.setArmorStand(middle2.add(0,-1.05,0),new EulerAngle(0, 0, 0) , new ItemStack(Material.TRAP_DOOR), false,false,false,getID(),idList);	
		Location l = getLocation();
		l.setYaw(0);
		ArmorStand as = Utils.setArmorStand(l.add(.9,0.15,0.3),new EulerAngle(0,.0,.0), is, true,false,false,getID(),idList);	
		Utils.setArmorStand(l.add(0,-.65,.68),new EulerAngle(1.38,.0,.0), new ItemStack(Material.STICK), true,false,false,getID(),idList);	
		this.armor = as;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main.getInstance().getManager().tableList.add(this);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand == false){return;}
		if(idList==null||idList.isEmpty()){return;}
		if(!idList.contains(e.getRightClicked().getUniqueId())){return;}
		e.setCancelled(true);
		if(!main.getInstance().getCheckManager().canBuild(player, getLocation())){return;}
		ItemStack is = player.getItemInHand();
		if(is!=null&&!is.getType().isBlock()||is.getType().equals(Material.AIR)){
				if(armor!=null){
					ArmorStand as = Utils.getArmorStandAtID(w,idList.get(2));
					if(as!=null&&as.getItemInHand()!= null && as.getItemInHand().equals(is)){return;}
					if(as!=null&&armor.getItemInHand()!=null&&!armor.getItemInHand().getType().equals(Material.AIR)){as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());}
					as.setItemInHand(is);
					this.is = is;
					if(!player.getGameMode().equals(GameMode.CREATIVE)){player.getInventory().remove(is);player.updateInventory();}
					main.getInstance().mgr.saveTable(this);
			}
		}
	}
	
	public List<String> getList(){
		return Utils.UUIDListToStringList(idList);
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
			for(UUID s : idList){
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

	@EventHandler(priority = EventPriority.HIGHEST)
	public void damage(EntityDamageByEntityEvent e){
		if(e.isCancelled()){return;}
		if(e.getDamager() instanceof Player == false){return;}
		if(e.getEntity() instanceof ArmorStand == false){return;}
		if(e.getEntity() == null){return;}
		if(!idList.contains(e.getEntity().getUniqueId())){return;}
		e.setCancelled(true);
		if(!Permissions.check((Player) e.getDamager(), FurnitureType.TABLE, "destroy.")){return;}
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
