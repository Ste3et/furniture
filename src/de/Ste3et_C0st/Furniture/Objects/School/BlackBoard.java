package de.Ste3et_C0st.Furniture.Objects.School;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class BlackBoard extends Furniture implements Listener {
	public BlackBoard(Plugin plugin, ObjectID id){
		super(plugin, id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(id.getStartLocation());
	}

	@Override
	public void spawn(Location paramLocation) {
		List<fArmorStand> aspList = new ArrayList<fArmorStand>();
		double d = -.126;
		for(int i = 0; i<4;i++){
			Location loc1 = getRelative(getCenter(), getBlockFace(), -.27, d).add(0, -2.5, 0);
			fArmorStand stand = spawnArmorStand(loc1.clone().add(0, .1, 0));
			stand.setHelmet(new ItemStack(Material.BANNER));
			stand.setHeadPose(new EulerAngle(0, 0, 0));
			aspList.add(stand);
			d+=.75;
		}
		d = -.235;
		for(int i = 0; i<6;i++){
			Location loc1 = getRelative(getCenter(), getBlockFace(), -.27, d).add(0, -2.5, 0);
			fArmorStand stand = spawnArmorStand(loc1.clone().add(0, .4, 0));
			stand.setHelmet(new ItemStack(Material.WOOD_PLATE));
			stand.setHeadPose(new EulerAngle(0, 0, 0));
			aspList.add(stand);
			d+=.49;
		}
		Location loc = getRelative(getCenter(), getBlockFace(),.25d, 1.2d).add(0, -1.63, 0);
		loc.setYaw(getYaw()+180);
		fArmorStand stand = spawnArmorStand(loc);
		//stand.setItemInHand(new ItemStack(Material.BONE));
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-110, 0, 270)));
		stand.setMarker(false);
		stand.setName("#ITEM#");
		aspList.add(stand);
		for(fArmorStand as : aspList){
			as.setInvisible(true);
			as.setGravity(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}

	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		delete();
		e.remove();
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		Player p = e.getPlayer();
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		if(p.getItemInHand().getType().isBlock()&&!p.getItemInHand().getType().equals(Material.AIR)){return;}
		for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
			if(packet.getName().equalsIgnoreCase("#ITEM#")){
				ItemStack Itemstack = p.getItemInHand().clone();
				Itemstack.setAmount(1);
				if(packet.getInventory().getItemInHand()!=null&&!packet.getInventory().getItemInHand().getType().equals(Material.AIR)){
					ItemStack is = packet.getInventory().getItemInHand();
					is.setAmount(1);
					getWorld().dropItem(getLocation(), is);
				}
				packet.getInventory().setItemInHand(Itemstack);
				if(p.getGameMode().equals(GameMode.CREATIVE) && getLib().useGamemode()) break;
				Integer i = p.getInventory().getHeldItemSlot();
				ItemStack is = p.getItemInHand();
				is.setAmount(is.getAmount()-1);
				p.getInventory().setItem(i, is);
				p.updateInventory();
				break;
			}
		}
		update();
	}
}