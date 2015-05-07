package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.bukkit.entity.Entity;
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

public class largeTable implements Listener {

	private List<UUID> idList = new ArrayList<UUID>();
	private List<UUID> tellerIDS = new ArrayList<UUID>();
	private Location loc = null;
	private BlockFace b = null;
	private String id;
	private World w;
	public String getID(){return this.id;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public largeTable(Location location, Plugin plugin, String ID, List<UUID> uuids){
		this.loc = location;
		this.b = Utils.yawToFace(location.getYaw());
		this.id = ID;
		this.w = location.getWorld();
		
		FurnitureCreateEvent event = new FurnitureCreateEvent(FurnitureType.LARGE_TABLE, this.id, location);
		Bukkit.getPluginManager().callEvent(event);
		if(!event.isCancelled()){
			if(uuids==null){uuids = idList;}
			spawn(uuids, location, plugin);
		}
	}
	
	public void spawn(List<UUID> uuidList, Location loc, Plugin plugin){
		List<ArmorStand> armorlist = new ArrayList<ArmorStand>();
		Location location = Utils.getCenter(loc.getBlock().getLocation());
		float yaw = Utils.FaceToYaw(this.b);
		location = main.getNew(location, this.b, 0.1, 0.28);
		location.add(0,.2,0);
		Double winkel = 1.57;
		for(int x=1; x<=3;x++){
			Location l = null;
			l = main.getNew(location, this.b, 0.0, x*-.63);
			l.add(0,-1.2,0);
			l.setYaw(yaw);
			ItemStack iTemStack_1 = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemStack iTemStack_2 = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemStack iTemStack_3 = new ItemStack(Material.STAINED_GLASS_PANE);
			ArmorStand as = Utils.setArmorStand(l, new EulerAngle(winkel, 0, 0), iTemStack_1, false,false,false,getID(),idList);
			armorlist.add(as);
			l = main.getNew(location, this.b, 0.63, x*-.63);
			l.add(0,-1.2,0);
			l.setYaw(yaw);
			as = Utils.setArmorStand(l, new EulerAngle(winkel, 0, 0), iTemStack_2, false,false,false,getID(),idList);
			armorlist.add(as);
			l = main.getNew(location, this.b, 1.26, x*-.63);
			l.add(0,-1.2,0);
			l.setYaw(yaw);
			as = Utils.setArmorStand(l, new EulerAngle(winkel, 0, 0), iTemStack_3, false,false,false,getID(),idList);
			armorlist.add(as);
		}

		Location middle = Utils.getCenter(armorlist.get(0).getLocation());
		Location mitteTisch = Utils.getCenter(armorlist.get(4).getLocation().getBlock().getLocation());
		middle.add(0, -.9, 0);
		Location feet1 = main.getNew(middle, this.b, -.2, .1);
		Location feet2 = main.getNew(middle, this.b, -.2, -1.3);
		Location feet3 = main.getNew(middle, this.b, 1.1, .1);
		Location feet4 = main.getNew(middle, this.b, 1.1, -1.3);
		
		
		double hight = .67;
		
		Location t1 = main.getNew(mitteTisch, this.b, -.95, .4).add(0,hight,0);
		Location t2 = main.getNew(mitteTisch, this.b, -.4, -.92).add(0,hight,0);
		Location t3 = main.getNew(mitteTisch, this.b, .92, -.36).add(0,hight,0);
		Location t4 = main.getNew(mitteTisch, this.b, .4, .92).add(0,hight,0);
		
		float yaw1 = yaw;
		float yaw2 = yaw1-90;
		float yaw3 = yaw2-90;
		float yaw4 = yaw3-90;
		
		t1.setYaw(yaw1);
		t2.setYaw(yaw2);
		t3.setYaw(yaw3);
		t4.setYaw(yaw4);
		
		feet1.setYaw(yaw);
		feet2.setYaw(yaw);
		feet3.setYaw(yaw);
		feet4.setYaw(yaw);

		Utils.setArmorStand(feet1, new EulerAngle(-1.75, 0, 0), new ItemStack(Material.BONE), true,false,false, getID(), idList);
		Utils.setArmorStand(feet2, new EulerAngle(-1.75, 0, 0), new ItemStack(Material.BONE), true,false,false, getID(), idList);
		Utils.setArmorStand(feet3, new EulerAngle(-1.75, 0, 0), new ItemStack(Material.BONE), true,false,false, getID(), idList);
		Utils.setArmorStand(feet4, new EulerAngle(-1.75, 0, 0), new ItemStack(Material.BONE), true,false,false, getID(), idList);
		ArmorStand as1 = Utils.setArmorStand(t1, new EulerAngle(0, 0, 0), null, true,false,false, getID(), idList);
		ArmorStand as2 = Utils.setArmorStand(t2, new EulerAngle(0, 0, 0), null, true,false,false, getID(), idList);
		ArmorStand as3 = Utils.setArmorStand(t3, new EulerAngle(0, 0, 0), null, true,false,false, getID(), idList);
		ArmorStand as4 = Utils.setArmorStand(t4, new EulerAngle(0, 0, 0), null, true,false,false, getID(), idList);
		
		tellerIDS.add(as1.getUniqueId());
		tellerIDS.add(as2.getUniqueId());
		tellerIDS.add(as3.getUniqueId());
		tellerIDS.add(as4.getUniqueId());
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main.getInstance().getManager().largeTableList.add(this);
	}
	
	public List<String> getList(){
		return Utils.UUIDListToStringList(idList);
	}
	
	public void setTeller(HashMap<Integer, ItemStack> itemList){
		int i = 0;
		for(UUID id : tellerIDS){
			ArmorStand as = Utils.getArmorStandAtID(w, id);
			as.setItemInHand(itemList.get(i));
			i++;
		}
	}
	
	public void setColor(HashMap<Integer, Short> durabilityList){
		int i = 0;
		for(UUID id: idList){
			ArmorStand as = Utils.getArmorStandAtID(w, id);
			if(as.getHelmet()!=null&&!as.getHelmet().getType().equals(Material.AIR)&&as.getHelmet().getType().equals(Material.STAINED_GLASS_PANE)){
				ItemStack is = as.getHelmet();
				is.setDurability(durabilityList.get(i));
				as.setHelmet(is);
				i++;
			}
		}
	}
	
	public HashMap<Integer, Short> getColor(){
		HashMap<Integer, Short> colorList = new HashMap<Integer, Short>();
		Integer i = 0;
		
		for(UUID id: idList){
			try{i=colorList.size();}catch(Exception e){return colorList;}
			ArmorStand as = Utils.getArmorStandAtID(w, id);
			if(as!=null){
				if(as.getHelmet()!=null&&!as.getHelmet().getType().equals(Material.AIR)&&as.getHelmet().getType().equals(Material.STAINED_GLASS_PANE)){
					ItemStack is = as.getHelmet();
					colorList.put(i, is.getDurability());
				}
			}
		}
		return colorList;
	}
	
	public void delete(boolean b, boolean a){
		if(b){
			if(a){getLocation().getWorld().dropItem(getLocation(), main.getInstance().crafting.get("largeTable"));}
			for(UUID s : tellerIDS){
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				if(as!=null){
					if(as.getItemInHand()!=null&&!as.getItemInHand().getType().equals(Material.AIR)){
						getLocation().getWorld().dropItem(getLocation(), as.getItemInHand());
					}
				}
			}
			
			for(UUID s : this.idList){
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				if(as!=null && as.getHelmet()!= null && as.getHelmet().getType()!=null){
					if(a){loc.getWorld().playEffect(loc, Effect.STEP_SOUND, as.getHelmet().getType());}
				}	
				as.remove();
			}
			main.getInstance().mgr.deleteFromConfig(getID(), "largeTable");
		}
		
		this.tellerIDS.clear();
		this.idList.clear();
		main.getInstance().getManager().largeTableList.remove(this);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	private void onInteract(PlayerInteractAtEntityEvent e){
		if(e.isCancelled()){return;}
		Player player = e.getPlayer();
		if(e.getRightClicked() == null){return;}
		if(e.getRightClicked() instanceof ArmorStand == false){return;}
		if(idList==null||idList.isEmpty()){return;}
		if(!this.idList.contains(e.getRightClicked().getUniqueId())){return;}
		e.setCancelled(true);
		if(!main.getInstance().getCheckManager().canBuild(player, getLocation())){return;}
		ItemStack is = player.getItemInHand();
		if(is!=null){
			if(is.getType().equals(Material.INK_SACK)){
					Short druability = is.getDurability();
					Integer amount = is.getAmount();
					if(amount>this.idList.size()-4 || player.getGameMode().equals(GameMode.CREATIVE)){amount=this.idList.size()-4;}
					List<Entity> list = new ArrayList<Entity>();
					for(UUID s : this.idList){
						ArmorStand as = Utils.getArmorStandAtID(w, s);
						if(as!=null){
							ItemStack item = as.getHelmet();
							if(item.getDurability() != main.getFromDey(druability)){
							list.add(as);
							}
							}
					}
					for(Entity entity : list){
						if(list.indexOf(entity)>amount-1){break;}
							if(entity instanceof ArmorStand){
								ArmorStand as = (ArmorStand) entity;
								ItemStack item = as.getHelmet();
								item.setDurability(main.getFromDey(druability));
								as.setHelmet(item);
							}
						}
						
						
						if(!player.getGameMode().equals(GameMode.CREATIVE)){
							is.setAmount(is.getAmount()-amount);
							player.getInventory().setItem(player.getInventory().getHeldItemSlot(), is);
							player.updateInventory();
						}
					}else if(!is.getType().isBlock() || is.getType().equals(Material.AIR)){
						BlockFace b = Utils.yawToFace(player.getLocation().getYaw());
						ArmorStand as = null;
						if(tellerIDS == null || tellerIDS.isEmpty()){return;}
						for(UUID s : this.tellerIDS){
							if(s!=null){
								ArmorStand armorStand = Utils.getArmorStandAtID(w, s);
								if(armorStand!=null){
									BlockFace b2 = Utils.yawToFace(armorStand.getLocation().getYaw());
									if(b2.equals(b)){
										as = armorStand;
										break;
									}
								}
							}
						}
						if(as!=null&&as.getItemInHand()!= null && as.getItemInHand().equals(is)){return;}
						if(as.getItemInHand()!=null&&!as.getItemInHand().getType().equals(Material.AIR)){as.getLocation().getWorld().dropItem(as.getLocation(), as.getItemInHand());}
						as.setItemInHand(is);
						if(!player.getGameMode().equals(GameMode.CREATIVE)){player.getInventory().remove(is);player.updateInventory();}
						player.updateInventory();
					}
					main.getInstance().mgr.saveLargeTable(this);
				}
	}
	
	public void save(){
		main.getInstance().mgr.saveLargeTable(this);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void damage(EntityDamageByEntityEvent e){
		if(e.isCancelled()){return;}
		if(e.getDamager() instanceof Player == false){return;}
		if(e.getEntity() instanceof ArmorStand == false){return;}
		if(e.getEntity() == null){return;}
		if(!idList.contains(e.getEntity().getUniqueId())){return;}
		e.setCancelled(true);
		if(!Permissions.check((Player) e.getDamager(), FurnitureType.LARGE_TABLE, "destroy.")){return;}
		if(!main.getInstance().getCheckManager().canBuild((Player) e.getDamager(), getLocation())){return;}
		if(((Player) e.getDamager()).getGameMode().equals(GameMode.CREATIVE)){delete(true, false);return;}
		delete(true, true);
	}
	
	public HashMap<Integer, ItemStack> getTeller(){
		HashMap<Integer, ItemStack> teller = new HashMap<Integer, ItemStack>();
		for(UUID s : tellerIDS){
			try{
				ArmorStand as = Utils.getArmorStandAtID(w, s);
				teller.put(teller.size(), as.getItemInHand());
			}catch(Exception e){
				teller.put(teller.size(), new ItemStack(Material.AIR));
			}
		}
		return teller;
	}
}
