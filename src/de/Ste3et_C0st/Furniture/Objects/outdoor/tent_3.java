package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.FurnitureCreateEvent;
import de.Ste3et_C0st.Furniture.Main.Permissions;
import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.Furniture.Main.Type.FurnitureType;

public class tent_3 implements Listener {

	private Location loc;
	private String ID;
	private BlockFace b;
	private Block block;
	private List<UUID> idList = new ArrayList<UUID>();
	private World w;
	public String getID(){return this.ID;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	public ArmorStand armorstand;
	
	public tent_3(Location location, Plugin plugin, String ID, List<UUID> uuids) {
		this.b = Utils.yawToFace(location.getYaw());
		this.loc = location.getBlock().getLocation();
		this.ID = ID;
		this.w = location.getWorld();
		if(b.equals(BlockFace.WEST)){location=main.getNew(location, b, 1D, 0D);}
		if(b.equals(BlockFace.NORTH)){location=main.getNew(location, b, 1D, 1D);}
		if(b.equals(BlockFace.EAST)){location=main.getNew(location, b, 0D, 1D);}
		FurnitureCreateEvent event = new FurnitureCreateEvent(FurnitureType.TENT_3, this.ID, location);
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
		ItemStack banner = new ItemStack(Material.BANNER);
		BannerMeta meta = (BannerMeta) banner.getItemMeta();
		meta.setBaseColor(DyeColor.WHITE);
		banner.setItemMeta(meta);
		
		Location locstart = main.getNew(location, b, .2D, -.17D);
		locstart.add(0,.65,0);
		
		Location locstart2 = main.getNew(location, b, .2D, -.8D);
		locstart2.add(0,.65,0);
		
		for(int i = 0; i<=2;i++){
			Location loc = main.getNew(locstart, b, .74*i, 0D);
			loc.setYaw(Utils.FaceToYaw(b)+90);
			Utils.setArmorStand(loc, new EulerAngle(3.5, 0, 0), banner, false, false, false, getID(), idList);
			
			loc = main.getNew(locstart, b, .74*i, -.3D);
			loc.add(0,.9,0);
			loc.setYaw(Utils.FaceToYaw(b)+90);
			Utils.setArmorStand(loc, new EulerAngle(3.5, 0, 0), banner, false, false, false, getID(), idList);
		}
		
		for(int i = 0; i<=2;i++){
			Location loc = main.getNew(locstart2, b, .74*i, 0D);
			loc.setYaw(Utils.FaceToYaw(b)-90);
			Utils.setArmorStand(loc, new EulerAngle(3.5, 0, 0), banner, false, false, false, getID(), idList);
			
			loc = main.getNew(locstart2, b, .74*i, .32D);
			loc.add(0,.9,0);
			loc.setYaw(Utils.FaceToYaw(b)-90);
			Utils.setArmorStand(loc, new EulerAngle(3.5, 0, 0), banner, false, false, false, getID(), idList);
		}
		
		banner = new ItemStack(Material.BANNER);
		meta = (BannerMeta) banner.getItemMeta();
		meta.setBaseColor(DyeColor.WHITE);
		meta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_SMALL));
		banner.setItemMeta(meta);
		
		Location banner1 = main.getNew(location, b, 1.7D, -.1D);
		banner1.add(0,-1.2,0);
		Location banner2 = main.getNew(banner1, b, 0D, -.75);
		banner1.setYaw(Utils.FaceToYaw(b));
		banner2.setYaw(Utils.FaceToYaw(b));
		Utils.setArmorStand(banner1, new EulerAngle(-1.568, 0, 0), banner, false, false, false, getID(), idList);
		Utils.setArmorStand(banner2, new EulerAngle(-1.568, 0, 0), banner, false, false, false, getID(), idList);
		
		Location sit = Utils.getCenter(location);
		
		if(b.equals(BlockFace.WEST)){sit=main.getNew(sit, b, -1D, 0D);}
		if(b.equals(BlockFace.NORTH)){sit=main.getNew(sit, b, -1D, -1D);}
		if(b.equals(BlockFace.EAST)){sit=main.getNew(sit, b, 0D, -1D);}
		
		sit.setYaw(Utils.FaceToYaw(this.b.getOppositeFace()));
		Location locationsit = main.getNew(sit, b, 1D, 0D);
		locationsit.setYaw(Utils.FaceToYaw(this.b.getOppositeFace()));
		armorstand = Utils.setArmorStand(locationsit.add(0,-2,0), null, null, false, false, false, getID(), idList);
		
		block = Utils.setHalfBed(b, main.getNew(sit.add(0,-2,0).getBlock().getLocation().add(0,2,0), b, 2D, 0D));
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main.getInstance().getManager().tent3List.add(this);
		this.loc.setYaw(Utils.FaceToYaw(b));
	}
	

	@EventHandler(priority = EventPriority.HIGHEST)
	public void damage(EntityDamageByEntityEvent e){
		if(e.isCancelled()){return;}
		if(e.getDamager() instanceof Player == false){return;}
		if(e.getEntity() instanceof ArmorStand == false){return;}
		if(e.getEntity() == null){return;}
		if(!idList.contains(e.getEntity().getUniqueId())){return;}
		e.setCancelled(true);
		if(!Permissions.check((Player) e.getDamager(), FurnitureType.TENT_3, null)){return;}
		if(!main.getInstance().getCheckManager().canBuild((Player) e.getDamager(), getLocation())){return;}
		if(((Player) e.getDamager()).getGameMode().equals(GameMode.CREATIVE)){delete(true, false);return;}
		delete(true, true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand == false){return;}
		if(!idList.contains(e.getRightClicked().getUniqueId())){return;}
		e.setCancelled(true);
		ItemStack is = player.getItemInHand();
		if(is==null){return;}
		if(is.getType().equals(Material.INK_SACK)){
			if(!main.getInstance().getCheckManager().canBuild(player, getLocation())){return;}
			Short druability = is.getDurability();
			Integer amount = is.getAmount();
			Integer neAmound = amount;
			if(amount>idList.size() || player.getGameMode().equals(GameMode.CREATIVE)){amount=idList.size();}
			List<Entity> list = new ArrayList<Entity>();
			for(UUID s : this.idList){
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				if(as!=null){
					ItemStack item = as.getHelmet();
					if(!as.getHelmet().getType().equals(Material.BANNER)){continue;}
					BannerMeta im = (BannerMeta) item.getItemMeta();
					if(im.getBaseColor().getColor() != main.getDyeFromDurability(druability)){list.add(as);}
				}
			}
			for(Entity entity : list){
				if(list.indexOf(entity)>amount-1){break;}
				if(entity instanceof ArmorStand == false){break;}
				ArmorStand as = (ArmorStand) entity;
				if(as==null||as.getHelmet()==null){break;}
				if(!as.getHelmet().getType().equals(Material.BANNER)){neAmound-=1;continue;}
				ItemStack item = as.getHelmet();
				BannerMeta banner = (BannerMeta) item.getItemMeta();
				banner.setBaseColor(DyeColor.getByColor(main.getDyeFromDurability(druability)));
				item.setItemMeta(banner);
				as.setHelmet(item);	
			}
			if(!player.getGameMode().equals(GameMode.CREATIVE)){
			is.setAmount(is.getAmount()-neAmound);
			player.getInventory().setItem(player.getInventory().getHeldItemSlot(), is);
			player.updateInventory();
		}
		main.getInstance().mgr.saveTent3(this);
		return;
		}else{
			if(checkIfSitting()){
				armorstand.setPassenger(player);
			}
		}
	}
	
	public boolean checkIfSitting(){
		if(armorstand != null){
			if(armorstand.getPassenger()!= null){
				return false;
			}
		}
		return true;
	}
	
	public void delete(boolean b, boolean a){
		if(b){
			if(a){getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("tent3"));}
			for(UUID s : idList){
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				if(as!=null){
					if(a){as.getWorld().playEffect(as.getLocation(), Effect.STEP_SOUND, Material.LOG);}
					as.remove();
				}
			}
			if(this.block != null){
				block.setType(Material.AIR);
			}
			main.getInstance().mgr.deleteFromConfig(getID(), "tent3");
		}
		block = null;
		armorstand = null;
		idList.clear();
		main.getInstance().getManager().tent3List.remove(this);
	}
	
	public void save(){
		main.getInstance().mgr.saveTent3(this);
	}
	
	public HashMap<Integer, Integer> getColor(){
		HashMap<Integer, Integer> colorList = new HashMap<Integer, Integer>();
		Integer i = 0;
		
		for(UUID id: idList){
			try{i=colorList.size();}catch(Exception e){return colorList;}
			ArmorStand as = Utils.getArmorStandAtID(w, id);
			if(as!=null){
				if(as.getHelmet()!=null&&!as.getHelmet().getType().equals(Material.AIR)&&as.getHelmet().getType().equals(Material.BANNER)){
					ItemStack is = as.getHelmet();
					BannerMeta im = (BannerMeta) is.getItemMeta();
					colorList.put(i, im.getBaseColor().getColor().asRGB());
				}
			}
		}
		return colorList;
	}
	
	public void setColor(HashMap<Integer, Integer> durabilityList){
		int i = 0;
		for(UUID id: idList){
			ArmorStand as = Utils.getArmorStandAtID(w, id);
			if(as!=null){
				if(as.getHelmet()!=null&&!as.getHelmet().getType().equals(Material.AIR)&&as.getHelmet().getType().equals(Material.BANNER)){
					ItemStack is = as.getHelmet();
					BannerMeta im = (BannerMeta) is.getItemMeta();
					im.setBaseColor(DyeColor.getByColor(Color.fromRGB(durabilityList.get(i))));
					is.setItemMeta(im);
					as.setHelmet(is);
					i++;
				}
			}
		}
	}
}
