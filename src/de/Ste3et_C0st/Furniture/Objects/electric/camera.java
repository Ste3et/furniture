package de.Ste3et_C0st.Furniture.Objects.electric;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Camera.Utils.RenderClass;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class camera extends Furniture implements Listener{
	public camera(Plugin plugin, ObjectID id){
		super(plugin, id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(id.getStartLocation());
	}
	
	public void spawn(Location location){
		List<fArmorStand> aspList = new ArrayList<fArmorStand>();
		BlockFace b = getLutil().yawToFace(location.getYaw()).getOppositeFace();
		Location center = getLutil().getCenter(location);
		Location gehäuse = getLutil().getRelativ(center, b, 0D, 0D).add(0,-1.0,0);
		Location gehäuse2 = getLutil().getRelativ(center, b, 0D, 0D).add(0,-0.4,0);
		Location fokus = getLutil().getRelativ(center, b, .15D, 0D).add(0,-.24,0);
		Location search = getLutil().getRelativ(center, b, .15D, 0D).add(0,-.7,0);
		Location button = getLutil().getRelativ(center, b, -.15D, -.15D).add(0,.08,0);
		
		Location feet1 = getLutil().getRelativ(center, b, .5D, .4D).add(0,-.9,0);
		Location feet2 = getLutil().getRelativ(center, b, -.2D, -.7D).add(0,-.9,0);
		Location feet3 = getLutil().getRelativ(center, b, -.7D, .2D).add(0,-.9,0);
		
		gehäuse.setYaw(getLutil().FaceToYaw(b));
		fokus.setYaw(getLutil().FaceToYaw(b));
		search.setYaw(getLutil().FaceToYaw(b));
		button.setYaw(getLutil().FaceToYaw(b));
		feet1.setYaw(getLutil().FaceToYaw(b));
		feet2.setYaw(getLutil().FaceToYaw(b) + 180 - 45);
		feet3.setYaw(getLutil().FaceToYaw(b) + 180 + 45);
		
		fArmorStand as = getManager().createArmorStand(getObjID(), gehäuse);
		as.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 15));
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), gehäuse2);
		as.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 15));
		as.setSmall(true);
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), fokus);
		as.getInventory().setHelmet(new ItemStack(Material.DISPENSER));
		as.setSmall(true);
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), search);
		as.getInventory().setHelmet(new ItemStack(Material.TRIPWIRE_HOOK));
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), button);
		as.getInventory().setHelmet(new ItemStack(Material.WOOD_BUTTON));
		as.setSmall(true);
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), feet1);
		as.getInventory().setItemInHand(new ItemStack(Material.STICK));
		as.setPose(new EulerAngle(1.2, 0, 0), BodyPart.RIGHT_ARM);
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), feet2);
		as.getInventory().setItemInHand(new ItemStack(Material.STICK));
		as.setPose(new EulerAngle(1.2, 0, 0), BodyPart.RIGHT_ARM);
		aspList.add(as);
		
		as = getManager().createArmorStand(getObjID(), feet3);
		as.getInventory().setItemInHand(new ItemStack(Material.STICK));
		as.setPose(new EulerAngle(1.2, 0, 0), BodyPart.RIGHT_ARM);
		aspList.add(as);
		
		for(fArmorStand asp : aspList){
			asp.setInvisible(true);
			asp.setGravity(false);
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
		e.remove();
		delete();
	}
	

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		Player p = e.getPlayer();
		Location pLocation = getLutil().getRelativ(p.getLocation().getBlock().getLocation(), getBlockFace(), -1D, 0D).clone();
		Location locCopy = getLocation().getBlock().getLocation().clone();
		pLocation.setYaw(locCopy.getYaw());
		if(pLocation.equals(locCopy)){
			if(getLutil().yawToFace(p.getLocation().getYaw()).getOppositeFace().equals(getBlockFace())){
				if(!p.getInventory().getItemInHand().getType().equals(Material.MAP)){return;}
				
				MapView view = Bukkit.getMap(p.getItemInHand().getDurability());
				Location l = getLocation().clone();
				l.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
				Iterator<MapRenderer> iter = view.getRenderers().iterator();
	            while(iter.hasNext()){
	                view.removeRenderer(iter.next());
	            }
	            try{
	                RenderClass renderer = new RenderClass(l);
	                view.addRenderer(renderer);
	            }catch (Exception ex){}
			}
		}
	}
	
}
