package de.Ste3et_C0st.Furniture.Objects.trap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import de.Ste3et_C0st.Furniture.Main.FurnitureHook;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class BearTrap extends Furniture implements Listener{
	
	fArmorStand stand1, stand2, stand3, stand4;
	boolean b;
	
	public BearTrap(ObjectID id){
		super(id);
		for(fEntity stand : getfAsList()){
			if(stand.getName().equalsIgnoreCase("#IRON1#")){
				stand1 = (fArmorStand) stand;
			}else if(stand.getName().equalsIgnoreCase("#IRON2#")){
				stand2 = (fArmorStand) stand;
			}else if(stand.getName().equalsIgnoreCase("#IRON3#")){
				stand3 = (fArmorStand) stand;
			}else if(stand.getName().equalsIgnoreCase("#IRON4#")){
				stand4 = (fArmorStand) stand;
			}
		}
		Bukkit.getPluginManager().registerEvents(this, main.getInstance());
	}

	@Override
	public void spawn(Location loc) {
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}

	@Override
	public void onBreak(Player player) {
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			this.destroy(player);
		}
	}

	@Override
	public void onClick(Player player){
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			if(b){
				setStatus(false);
				getWorld().playSound(getLocation(), Sound.BLOCK_ANVIL_LAND, 10, 1);
			}
		}
	}
	
	private void setStatus(boolean b){
		if(!b){
			stand1.setHelmet(new ItemStack(Material.valueOf(FurnitureHook.isNewVersion() ? "IRON_BARS" : "IRON_FENCE")));
			stand2.setHelmet(new ItemStack(Material.valueOf(FurnitureHook.isNewVersion() ? "IRON_BARS" : "IRON_FENCE")));
			stand3.setHelmet(new ItemStack(Material.AIR));
			stand4.setHelmet(new ItemStack(Material.AIR));
		}else{
			stand3.setHelmet(new ItemStack(Material.valueOf(FurnitureHook.isNewVersion() ? "IRON_BARS" : "IRON_FENCE")));
			stand4.setHelmet(new ItemStack(Material.valueOf(FurnitureHook.isNewVersion() ? "IRON_BARS" : "IRON_FENCE")));
			stand1.setHelmet(new ItemStack(Material.AIR));
			stand2.setHelmet(new ItemStack(Material.AIR));
			getWorld().playSound(getLocation(), Sound.ENTITY_ITEM_BREAK, 5, 1);
		}
		this.b = b;
		update();
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if(b) return;
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		
		if (e.getFrom().getWorld() == e.getTo().getWorld() &&
				e.getFrom().getBlockX() == e.getTo().getBlockX() &&
				e.getFrom().getBlockY() == e.getTo().getBlockY() &&
				e.getFrom().getBlockZ() == e.getTo().getBlockZ())
			return;

		Player player = e.getPlayer();
		if (player.getHealth() <= 0.0D) return;
		Location loc = e.getTo().getBlock().getLocation();
		Location loc2 = getLocation();
		if(loc.toVector().distance(loc2.toVector())<1){
			setStatus(true);
			player.damage(main.damage);
		}
	}
}
