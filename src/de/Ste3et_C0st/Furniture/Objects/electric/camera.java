package de.Ste3et_C0st.Furniture.Objects.electric;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Camera.Utils.RenderClass;
import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;

public class camera implements Listener {
	List<String> IDList = new ArrayList<String>();
	Location loc = null;
	BlockFace b = null;
	World w = null;
	private String id;
	public String getID(){return this.id;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public camera(Location loc, Plugin plugin, String id){
		this.b = Utils.yawToFace(loc.getYaw());
		this.loc = loc.getBlock().getLocation();
		this.loc.setYaw(loc.getYaw());
		this.id = id;
		this.w = this.loc.getWorld();
		BlockFace b = Utils.yawToFace(loc.getYaw()).getOppositeFace();
		Location center = Utils.getCenter(loc);
		Location gehäuse = main.getNew(center, b, 0D, 0D).add(0,-1.0,0);
		Location gehäuse2 = main.getNew(center, b, 0D, 0D).add(0,-0.4,0);
		Location fokus = main.getNew(center, b, .15D, 0D).add(0,-.24,0);
		Location search = main.getNew(center, b, .15D, 0D).add(0,-.7,0);
		Location button = main.getNew(center, b, -.15D, -.15D).add(0,.08,0);
		
		Location feet1 = main.getNew(center, b, .5D, .4D).add(0,-.9,0);
		Location feet2 = main.getNew(center, b, -.2D, -.7D).add(0,-.9,0);
		Location feet3 = main.getNew(center, b, -.7D, .2D).add(0,-.9,0);
		
		gehäuse.setYaw(Utils.FaceToYaw(b));
		fokus.setYaw(Utils.FaceToYaw(b));
		search.setYaw(Utils.FaceToYaw(b));
		button.setYaw(Utils.FaceToYaw(b));
		feet1.setYaw(Utils.FaceToYaw(b));
		feet2.setYaw(Utils.FaceToYaw(b) + 180 - 45);
		feet3.setYaw(Utils.FaceToYaw(b) + 180 + 45);
		Utils.setArmorStand(gehäuse, null, new ItemStack(Material.WOOL, 1, (short) 15), false, false, false, getID(), IDList);
		Utils.setArmorStand(gehäuse2, null, new ItemStack(Material.WOOL, 1, (short) 15), false, true, false, getID(), IDList);
		Utils.setArmorStand(fokus, null, new ItemStack(Material.DISPENSER), false, true, false, getID(), IDList);
		Utils.setArmorStand(search, null, new ItemStack(Material.TRIPWIRE_HOOK, 1, (short) 15), false, false, false, getID(), IDList);
		Utils.setArmorStand(button, null, new ItemStack(Material.WOOD_BUTTON, 1, (short) 15), false, true, false, getID(), IDList);
		Utils.setArmorStand(feet1, new EulerAngle(1.2, 0, 0), new ItemStack(Material.STICK), true, false, false, getID(), IDList);
		Utils.setArmorStand(feet2, new EulerAngle(1.2, 0, 0), new ItemStack(Material.STICK), true, false, false, getID(), IDList);
		Utils.setArmorStand(feet3, new EulerAngle(1.2, 0, 0), new ItemStack(Material.STICK), true, false, false, getID(), IDList);
		main.getInstance().getManager().cameraList.add(this);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void damage(EntityDamageByEntityEvent e){
		if(e.isCancelled()){return;}
		if(e.getDamager() instanceof Player == false){return;}
		if(e.getEntity() instanceof ArmorStand == false){return;}
		if(e.getEntity() == null){return;}
		if(e.getEntity().getName() == null){return;}
		if(!IDList.contains(e.getEntity().getCustomName())){return;}
		e.setCancelled(true);
		if(!main.getInstance().getCheckManager().canBuild((Player) e.getDamager(), getLocation())){return;}
		if(((Player) e.getDamager()).getGameMode().equals(GameMode.CREATIVE)){delete(true, false);return;}
		delete(true, true);
	}
	
	public void save(){
		main.getInstance().mgr.saveCamera(this);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() == null){return;}
		if(e.getRightClicked() instanceof ArmorStand == false){return;}
		if(e.getRightClicked().getName() == null){return;}
		if(IDList==null||IDList.isEmpty()){return;}
		if(!IDList.contains(e.getRightClicked().getCustomName())){return;}
		e.setCancelled(true);
		Location playerLocation = Utils.getLocationCopy(main.getNew(player.getLocation().getBlock().getLocation(), b, -1D, 0D));
		Location location = Utils.getLocationCopy(getLocation());
		if(playerLocation.equals(location) && Utils.yawToFace(player.getLocation().getYaw()).getOppositeFace().equals(b)){
			if(!player.getInventory().getItemInHand().getType().equals(Material.MAP)){return;}
			MapView view = Bukkit.getMap(player.getItemInHand().getDurability());
			Location l = getLocation();
			l.setYaw(Utils.FaceToYaw(b.getOppositeFace()));
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
	
	public void delete(Boolean b, Boolean a){
		if(b){
			if(a){getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("camera"));}
			if(IDList!=null&&!IDList.isEmpty()){
				for(String ids : IDList){
					if(ids==null){continue;}
					ArmorStand as = Utils.getArmorStandAtID(w, ids);
					if(as!=null){
						if(a){getLocation().getWorld().playEffect(as.getLocation(), Effect.STEP_SOUND, as.getHelmet().getType());}	
						as.remove();
						main.getInstance().mgr.deleteFromConfig(getID(), "camera");
					}
				}
			}
		}
		this.loc = null;
		IDList.clear();
		main.getInstance().getManager().cameraList.remove(this);
	}
	
	@EventHandler
	public void onWaterFlow(BlockFromToEvent e){
		Location locTo = e.getToBlock().getLocation();
		if(loc!=null && locTo.equals(loc)){
			e.setCancelled(true);
		}
	}
}
