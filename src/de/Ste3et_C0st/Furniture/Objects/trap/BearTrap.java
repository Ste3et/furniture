package de.Ste3et_C0st.Furniture.Objects.trap;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class BearTrap extends Furniture implements Listener{
	
	fArmorStand stand1, stand2;
	boolean b;
	
	public BearTrap(ObjectID id){
		super(id);
		if(isFinish()){
			for(fArmorStand stand : getfAsList()){
				if(stand.getName().equalsIgnoreCase("#IRON1#")){
					stand1 = stand;
				}else if(stand.getName().equalsIgnoreCase("#IRON2#")){
					stand2 = stand;
				}
			}
			setStatus(false);
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}

	@Override
	public void spawn(Location loc) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		fArmorStand stand = spawnArmorStand(getRelative(getCenter(), 0, 0).add(0, -1.9, 0));
		stand.setHelmet(new ItemStack(Material.IRON_TRAPDOOR));
		asList.add(stand);
		
		Location location = getRelative(getCenter(), 0, .04).add(0, -1.8, 0);
		location.setYaw(getYaw()+90);
		stand = spawnArmorStand(location);
		stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(90, 0, 0)));
		stand.setHelmet(new ItemStack(Material.IRON_FENCE));
		stand.setName("#IRON1#");
		stand1 = stand;
		asList.add(stand);
		
		location = getRelative(getCenter(), 0, -.04).add(0, -1.8, 0);
		location.setYaw(getYaw()+90);
		stand = spawnArmorStand(location);
		stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(-90, 0, 0)));
		stand.setHelmet(new ItemStack(Material.IRON_FENCE));
		stand.setName("#IRON2#");
		stand2 = stand;
		asList.add(stand);
		
		for(fArmorStand packet : asList){
			packet.setInvisible(true);
			packet.setGravity(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}

	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.remove();
		delete();
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		if(b){
			setStatus(false);
			getWorld().playSound(getLocation(), Sound.ANVIL_LAND, 10, 1);
		}
	}
	
	private void setStatus(boolean b){
		if(b){
			stand1.setHeadPose(getLutil().degresstoRad(new EulerAngle(0, 0, 0)));
			stand2.setHeadPose(getLutil().degresstoRad(new EulerAngle(0, 0, 0)));
			getWorld().playSound(getLocation(), Sound.ITEM_BREAK, 5, 1);
		}else{
			stand1.setHeadPose(getLutil().degresstoRad(new EulerAngle(90, 0, 0)));
			stand2.setHeadPose(getLutil().degresstoRad(new EulerAngle(-90, 0, 0)));
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
