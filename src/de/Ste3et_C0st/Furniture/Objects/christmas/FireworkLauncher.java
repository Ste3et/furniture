package de.Ste3et_C0st.Furniture.Objects.christmas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class FireworkLauncher extends Furniture implements Listener {

	public FireworkLauncher(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, getPlugin());
			return;
		}
		spawn(id.getStartLocation());
	}

	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!canBuild(e.getPlayer())){return;}
		for(fEntity packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(packet.getName().equalsIgnoreCase("#FIREWORK#")){
				if(packet.getInventory().getItemInMainHand()!=null&&!packet.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
					ItemStack is = packet.getInventory().getItemInMainHand();
					getWorld().dropItem(getLocation(), is);
				}
			}
		}
		e.remove(true,true);
		delete();
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!canBuild(e.getPlayer())){return;}
		Player p = e.getPlayer();
		fEntity stand = null;
		for(fEntity st : getfAsList()){
			if(st.getName().equalsIgnoreCase("#FIREWORK#")){
				stand = st;
				break;
			}
		}
		if(stand == null) return;
		if(p.getInventory().getItemInMainHand()!=null){
			if(p.getInventory().getItemInMainHand().getType()!=null){
				if(p.getInventory().getItemInMainHand().getType().equals(Material.FIREWORK)){
					drop(stand);
					setItem(stand, p.getInventory().getItemInMainHand());
					
					Bukkit.getScheduler().runTaskLater(getPlugin(), new Runnable() {
						
						@Override
						public void run() {
							update();
							
						}
					}, 5);
					
					if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE) && getLib().useGamemode()) return;
					Integer i = e.getPlayer().getInventory().getHeldItemSlot();
					ItemStack is = e.getPlayer().getInventory().getItemInMainHand();
					is.setAmount(is.getAmount()-1);
					p.getInventory().setItem(i, is);
					p.updateInventory();
					return;
				}
			}
		}
		
		if(canLaunch(stand)){
			Firework fw = (Firework) getWorld().spawnEntity(getCenter(), EntityType.FIREWORK);
			FireworkMeta meta = (FireworkMeta) stand.getItemInMainHand().getItemMeta();
			fw.setFireworkMeta(meta);
			setItem(stand, new ItemStack(Material.AIR));
		}
	}
	
	public double getRandom(double min, double max){
		return ThreadLocalRandom.current().nextDouble(min, max);
	}
	
	public boolean canLaunch(fEntity stand){
		if(stand.getItemInMainHand()!=null){
			if(stand.getItemInMainHand().getType().equals(Material.FIREWORK)){
				return true;
			}
		}
		return false;
	}
	
	public void setItem(fEntity stand, ItemStack is){
		ItemStack stack = is.clone();
		stack.setAmount(1);
		stand.setItemInMainHand(stack);
		update();
	}
	
	public void drop(fEntity stand){
		if(stand.getItemInMainHand()!=null){
			getWorld().dropItem(getCenter(), stand.getItemInMainHand());
			stand.setItemInMainHand(new ItemStack(Material.AIR));
			update();
		}
	}

	@Override
	public void spawn(Location arg0) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		
		double d = -1.2;
		fArmorStand stand = spawnArmorStand(getRelative(getCenter(), getBlockFace(), .5, .4).add(0, d+.2, 0));
		stand.setItemInMainHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(80, 0, 0)));
		asList.add(stand);
		
		stand = spawnArmorStand(getRelative(getCenter(), getBlockFace(), .5, .4).add(0, d+.9, 0));
		stand.setItemInMainHand(new ItemStack(Material.STICK));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(80, 0, 0)));
		asList.add(stand);
		
		stand = spawnArmorStand(getRelative(getCenter(), getBlockFace(), .05, 0.85).add(0, d+.6, 0));
		stand.setName("#FIREWORK#");
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-90, 90, 0)));
		asList.add(stand);
		
		for(fArmorStand pack : asList){
			pack.setInvisible(true);
			pack.setBasePlate(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
}
