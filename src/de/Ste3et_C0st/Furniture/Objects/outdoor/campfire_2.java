package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
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
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.FurnitureCreateEvent;
import de.Ste3et_C0st.Furniture.Main.Permissions;
import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.Furniture.Main.Type.FurnitureType;

public class campfire_2 implements Listener {

	private Location loc;
	private Location l;
	private String ID;
	private BlockFace b;
	private List<UUID> idList = new ArrayList<UUID>();
	private List<UUID> fire = new ArrayList<UUID>();
	private List<Material> items = new ArrayList<Material>(
			Arrays.asList(
					Material.RAW_BEEF,
					Material.RAW_CHICKEN,
					Material.RAW_FISH,
					Material.POTATO_ITEM,
					Material.PORK,
					Material.RABBIT,
					Material.MUTTON
				)
			);
	private List<Material> items2 = new ArrayList<Material>(
			Arrays.asList(
					Material.COOKED_BEEF,
					Material.COOKED_CHICKEN,
					Material.COOKED_FISH,
					Material.BAKED_POTATO,
					Material.GRILLED_PORK,
					Material.COOKED_RABBIT,
					Material.COOKED_MUTTON
				)
			);
	private World w;
	private Integer timer;
	public String getID(){return this.ID;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	private ArmorStand armorS;
	private EulerAngle[] angle = {
			new EulerAngle(-1.5, -.5, 0),
			new EulerAngle(-1.9, -.3, .7),
			new EulerAngle(-1.5, .3, 1.9),
			new EulerAngle(-0.7, -.5, -1.2)
			
	};
	
	public campfire_2(Location location, Plugin plugin, String ID, List<UUID> uuids) {
		this.loc = location.getBlock().getLocation();
		this.ID = ID;
		this.b = Utils.yawToFace(location.getYaw());
		this.w = location.getWorld();
		this.loc.setYaw(location.getYaw());
		
		FurnitureCreateEvent event = new FurnitureCreateEvent(FurnitureType.CAMPFIRE_2, this.ID, location);
		Bukkit.getPluginManager().callEvent(event);
		if(!event.isCancelled()){
			if(uuids==null){uuids = idList;}
			spawn(uuids, location, plugin);
		}
	}
	
	public List<String> getList(){
		return Utils.UUIDListToStringList(idList);
	}
	
	public void spawn(List<UUID> uuidList, Location location, Plugin plugin){
		Location middle = Utils.getCenter(getLocation());
		middle = main.getNew(middle, b, .5D, -.5D);
		middle.add(0,-1.2,0);
		Location stick1 = main.getNew(middle, getBlockFace(), .47D, -.05D);
		Location stick2 = main.getNew(middle, getBlockFace(), .47D, .85D);
		Location grill = main.getNew(middle,b, .0D, .5D);
		Location bone = main.getNew(middle, getBlockFace(), .5D, .82D);
		stick2.setYaw(Utils.FaceToYaw(b));
		grill.setYaw(Utils.FaceToYaw(b)+90);
		stick1.setYaw(Utils.FaceToYaw(b));
		bone.setYaw(Utils.FaceToYaw(b));
		stick1.add(0,.3,0);
		stick2.add(0,.3,0);
		bone.add(0,.17,0);
		Integer yaw = 90;
		for(int i = 0; i<=7;i++){
			Location loc = null;
			if(Utils.axisList.contains(Utils.yawToFaceRadial(yaw))){
				loc = main.getNew(middle, Utils.yawToFaceRadial(yaw), 0D, .5D);
			}else{
				loc = main.getNew(middle, Utils.yawToFaceRadial(yaw), 0D, .35D);
			}
			
			loc.setYaw(90+yaw);
			Utils.setArmorStand(loc, new EulerAngle(1.568, 0, 0), new ItemStack(Material.STEP,1,(short)3), false, true, false, ID, idList);
			yaw+=45;
		}
		
		yaw = 90;
		for(int i = 0; i<=3;i++){
			Location loc = main.getNew(middle, Utils.yawToFace(yaw), .4, -.5D);
			loc.add(0,-.5,0);
			loc.setYaw(90+yaw);
			Utils.setArmorStand(loc, new EulerAngle(2, 0, 0), new ItemStack(Material.STICK), true, false, false, ID, idList);
			yaw+=90;
		}
		
		Utils.setArmorStand(stick1, new EulerAngle(1.38,.0,.0), new ItemStack(Material.STICK), true, false, false, ID, idList);
		Utils.setArmorStand(stick2, new EulerAngle(1.38,.0,.0), new ItemStack(Material.STICK), true, false, false, ID, idList);
		Utils.setArmorStand(bone, new EulerAngle(1.38,.0,1.57), new ItemStack(Material.BONE), true, false, false, ID, idList);
		this.l = grill;
		
		ArmorStand as = Utils.setArmorStand(middle.add(0,-1.3,0), null, null, false, true, false, ID, idList);
		fire.add(as.getUniqueId());
		
		main.getInstance().getManager().campfire2List.add(this);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBurn(EntityDamageEvent e){
		if(e.getEntity() instanceof ArmorStand && (e.getCause().name().equalsIgnoreCase("FIRE") || e.getCause().name().equalsIgnoreCase("FIRE_TICK"))){
				if(fire!=null&&fire.contains(e.getEntity().getUniqueId())){
					e.setCancelled(true);
				}
		}
	}
	
	public boolean isFire(){
		if(fire==null){return false;}
		for(UUID s : fire){
			ArmorStand as = Utils.getArmorStandAtID(w, s);
			if(as==null){return false;}
			if(as.getFireTicks()>0){return true;}
		}
		return false;
	}
	
	@SuppressWarnings("static-access")
	public void setLight(Boolean b, Boolean a){
		if(b){
			if(fire==null){return;}
			for(UUID s : fire){
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				if(as==null){return;}
				as.setFireTicks(Integer.MAX_VALUE);
			}
			if(main.getInstance().getCheckManager().getLightAPI()!=null){
				main.getInstance().getCheckManager().getLightAPI().getLightAPI().createLight(getLocation().toVector().toLocation(w), 15);
			}
			
			w.playSound(getLocation(), Sound.FIRE_IGNITE, 1, 1);
		}else{
			if(fire==null){return;}
			for(UUID s : fire){
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				if(as==null){return;}
				as.setFireTicks(0);
			}
			if(main.getInstance().getCheckManager().getLightAPI()!=null){
				main.getInstance().getCheckManager().getLightAPI().getLightAPI().deleteLight(getLocation().toVector().toLocation(w));
			}
			if(a){
				w.playSound(getLocation(), Sound.FIZZ, 1, 1);
			}
		}
	}
	
	public void save(){
		main.getInstance().mgr.saveCampFire2(this);
	}
	
	public void delete(Boolean b, Boolean a){
		if(b){
			setLight(false, false);
			if(a){getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("campfire2"));}
			
			main.getInstance().mgr.deleteFromConfig(getID(), "campfire2");
			for(UUID s : idList){
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				if(as!=null){
					if(a){as.getWorld().playEffect(as.getLocation(), Effect.STEP_SOUND, Material.LOG);}
					as.remove();
				}
			}
		}
		removeGrill();
		this.loc = null;
		this.idList.clear();
		this.ID = null;
		main.getInstance().getManager().campfire2List.remove(this);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand){
			if(idList.contains(e.getRightClicked().getUniqueId())){
				e.setCancelled(true);
				if(!main.getInstance().getCheckManager().canBuild(player, getLocation())){return;}
				ItemStack is = player.getItemInHand();
				if(is!=null&&!is.getType().isBlock()||is.getType().equals(Material.AIR)){
					if(is.getType().equals(Material.FLINT_AND_STEEL)){
						setLight(true, true);
					}
					if(is.getType().equals(Material.WATER_BUCKET)){
						setLight(false, true);
					}
					if(!isFire()){return;}
					if(armorS==null&&getItem(is)!=null){
						setGrill(is);
						ItemStack item = is;
						item.setAmount(is.getAmount()-1);
						player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
						player.updateInventory();
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void damage(EntityDamageByEntityEvent e){
		if(e.isCancelled()){return;}
		if(e.getDamager() instanceof Player == false){return;}
		if(e.getEntity() instanceof ArmorStand == false){return;}
		if(e.getEntity() == null){return;}
		if(!idList.contains(e.getEntity().getUniqueId())){return;}
		e.setCancelled(true);
		if(!Permissions.check((Player) e.getDamager(), FurnitureType.CAMPFIRE_2, "destroy.")){return;}
		if(!main.getInstance().getCheckManager().canBuild((Player) e.getDamager(), getLocation())){return;}
		if(((Player) e.getDamager()).getGameMode().equals(GameMode.CREATIVE)){delete(true, false);return;}
		delete(true, true);
	}
	
	public void setGrill(ItemStack is){
		this.armorS = Utils.setArmorStand(l, null , is, true, false, false, ID, idList);
		this.timer = main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(main.getInstance(), new Runnable() {
			Integer rounds = Utils.randInt(15, 30);
			Integer labs = 0;
			Integer position = 0;
			@Override
			public void run() {
				if(labs>=rounds){removeGrill();}
				if(position>3){position=0;}
				if(armorS!=null){
					armorS.setRightArmPose(angle[position]);
				}
				position++;
				labs++;
			}
		}, 0, 4);
	}
	
	public boolean check(){
		if(armorS==null){return true;}
		return false;
	}
	
	public void removeGrill(){
		if(isRunning()){
			Bukkit.getScheduler().cancelTask(timer);
			timer=null;
			if(armorS!=null&&armorS.getItemInHand()!=null&&getItem(armorS.getItemInHand())!=null){
				w.dropItem(getLocation(), getCooked(armorS.getItemInHand()));
				armorS.remove();
				armorS=null;
			}
		}
		if(armorS!=null){
			if(armorS.getItemInHand()!=null){w.dropItem(getLocation(), getCooked(armorS.getItemInHand()));}
			armorS.remove();
			armorS=null;
		}
	}
	
	public boolean isRunning(){
		if(timer==null){return false;}
		return true;
	}
	
	public ItemStack getItem(ItemStack is){
		if(is==null){return null;}
		if(is.getType()==null){return null;}
		if(items.contains(is.getType())){
			return is;
		}
		return null;
	}
	
	public ItemStack getCooked(ItemStack is){
		if(is==null){return null;}
		if(is.getType()==null){return null;}
		if(items.contains(is.getType())){
			return new ItemStack(items2.get(items.indexOf(is.getType())));
		}
		return is;
	}
}
