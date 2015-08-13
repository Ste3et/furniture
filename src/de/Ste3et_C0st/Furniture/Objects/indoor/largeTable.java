package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.ArmorStandPacket;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;

public class largeTable extends Furniture implements Listener{

	Location loc;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	Integer id;
	Plugin plugin;
	
	public ObjectID getObjectID(){return this.obj;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public largeTable(Location location, FurnitureLib lib, Plugin plugin, ObjectID id){
		super(location, lib, plugin, id);
		this.lutil = main.getLocationUtil();
		this.b = lutil.yawToFace(location.getYaw());
		this.loc = location.getBlock().getLocation();
		this.loc.setYaw(location.getYaw());
		this.w = location.getWorld();
		this.manager = lib.getFurnitureManager();
		this.lib = lib;
		this.plugin = plugin;
		this.obj = id;
		if(id.isFinish()){
			for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
				if(packet.getName().startsWith("#TELLER")){
					tellerIDs.add(packet.getEntityId());
				}
			}
			
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(location);
	}
	List<Integer> tellerIDs = new ArrayList<Integer>();
	
	public void spawn(Location loc){
		List<ArmorStandPacket> armorlist = new ArrayList<ArmorStandPacket>();
		
		Location location = lutil.getCenter(loc.getBlock().getLocation());
		float yaw = lutil.FaceToYaw(this.b);
		location = lutil.getRelativ(location, this.b, 0.1, 0.28);
		location.add(0,.2,0);
		Double winkel = 1.57;
		for(int x=1; x<=3;x++){
			Location l = null;
			l = lutil.getRelativ(location, this.b, 0.0, x*-.63);
			l.add(0,-1.2,0);
			l.setYaw(yaw);
			ItemStack iTemStack_1 = new ItemStack(Material.STAINED_GLASS_PANE);
			
			ArmorStandPacket as = manager.createArmorStand(obj, l);
			as.setPose(new EulerAngle(winkel, 0, 0), BodyPart.HEAD);
			as.getInventory().setHelmet(iTemStack_1);
			armorlist.add(as);
			
			l = lutil.getRelativ(location, this.b, 0.63, x*-.63);
			l.add(0,-1.2,0);
			l.setYaw(yaw);
			as = manager.createArmorStand(obj, l);
			as.setPose(new EulerAngle(winkel, 0, 0), BodyPart.HEAD);
			as.getInventory().setHelmet(iTemStack_1);
			armorlist.add(as);
			
			l = lutil.getRelativ(location, this.b, 1.26, x*-.63);
			l.add(0,-1.2,0);
			l.setYaw(yaw);
			as = manager.createArmorStand(obj, l);
			as.setPose(new EulerAngle(winkel, 0, 0), BodyPart.HEAD);
			as.getInventory().setHelmet(iTemStack_1);
			armorlist.add(as);
		}

		Location middle = lutil.getCenter(armorlist.get(0).getLocation());
		Location mitteTisch = lutil.getCenter(armorlist.get(4).getLocation().getBlock().getLocation());
		middle.add(0, -.9, 0);
		Location feet1 = lutil.getRelativ(middle, this.b, -.2, .1);
		Location feet2 = lutil.getRelativ(middle, this.b, -.2, -1.3);
		Location feet3 = lutil.getRelativ(middle, this.b, 1.1, .1);
		Location feet4 = lutil.getRelativ(middle, this.b, 1.1, -1.3);
		
		
		double hight = .67;
		
		Location t1 = lutil.getRelativ(mitteTisch, this.b, -.95, .4).add(0,hight,0);
		Location t2 = lutil.getRelativ(mitteTisch, this.b, -.4, -.92).add(0,hight,0);
		Location t3 = lutil.getRelativ(mitteTisch, this.b, .92, -.36).add(0,hight,0);
		Location t4 = lutil.getRelativ(mitteTisch, this.b, .4, .92).add(0,hight,0);
		
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
		
		ArmorStandPacket as = manager.createArmorStand(obj, feet1);
		as.setPose(new EulerAngle(-1.75, 0, 0), BodyPart.RIGHT_ARM);
		as.getInventory().setItemInHand(new ItemStack(Material.BONE));
		armorlist.add(as);
		
		as = manager.createArmorStand(obj, feet2);
		as.setPose(new EulerAngle(-1.75, 0, 0), BodyPart.RIGHT_ARM);
		as.getInventory().setItemInHand(new ItemStack(Material.BONE));
		armorlist.add(as);
		
		as = manager.createArmorStand(obj, feet3);
		as.setPose(new EulerAngle(-1.75, 0, 0), BodyPart.RIGHT_ARM);
		as.getInventory().setItemInHand(new ItemStack(Material.BONE));
		armorlist.add(as);
		
		as = manager.createArmorStand(obj, feet4);
		as.setPose(new EulerAngle(-1.75, 0, 0), BodyPart.RIGHT_ARM);
		as.getInventory().setItemInHand(new ItemStack(Material.BONE));
		armorlist.add(as);
		
		
		as = manager.createArmorStand(obj, t1);
		as.setName("#TELLER1#");
		as.setPose(new EulerAngle(0, 0, 0), BodyPart.RIGHT_ARM);
		armorlist.add(as);
		tellerIDs.add(as.getEntityId());
		as = manager.createArmorStand(obj, t2);
		as.setName("#TELLER2#");
		as.setPose(new EulerAngle(0, 0, 0), BodyPart.RIGHT_ARM);
		armorlist.add(as);
		tellerIDs.add(as.getEntityId());
		as = manager.createArmorStand(obj, t3);
		as.setName("#TELLER3#");
		as.setPose(new EulerAngle(0, 0, 0), BodyPart.RIGHT_ARM);
		armorlist.add(as);
		tellerIDs.add(as.getEntityId());
		as = manager.createArmorStand(obj, t4);
		as.setName("#TELLER4#");
		as.setPose(new EulerAngle(0, 0, 0), BodyPart.RIGHT_ARM);
		armorlist.add(as);
		tellerIDs.add(as.getEntityId());
		
		for(ArmorStandPacket asp : armorlist){
			asp.setGravity(false);
			asp.setInvisible(true);
			asp.setBasePlate(false);
		}
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	public void setTeller(HashMap<Integer, ItemStack> itemList){
		int i = 0;
		for(Integer id : tellerIDs){
			ArmorStandPacket as = manager.getArmorStandPacketByID(id);
			as.getInventory().setItemInHand(itemList.get(i));
			i++;
		}
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.canBuild()){return;}
		if(!e.getID().equals(obj)){return;}
		for(Integer id : tellerIDs){
			ArmorStandPacket asp = manager.getArmorStandPacketByID(id);
			if(asp!=null&&asp.getInventory().getItemInHand()!=null){
				if(asp.getName().startsWith("#TELLER")){
					ArmorStandPacket packet = asp;
					e.getLocation().getWorld().dropItem(e.getLocation(), packet.getInventory().getItemInHand());
				}
			}
		}
		e.remove();
		obj=null;
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		e.setCancelled(true);
		final Player p = e.getPlayer();
		if(p.getItemInHand().getType().equals(Material.INK_SACK)){
			if(!e.canBuild()){return;}
			ItemStack is = p.getItemInHand();
			Integer Amount = is.getAmount();
			List<ArmorStandPacket> asp = manager.getArmorStandPacketByObjectID(obj);
			short color = lutil.getFromDey(is.getDurability());
			for(ArmorStandPacket packet : asp){
				if(packet.getInventory().getHelmet()!=null){
					if(packet.getInventory().getHelmet().getType().equals(Material.STAINED_GLASS_PANE)){
						if(Amount>0){
							ItemStack is2 = packet.getInventory().getHelmet();
							if(is2.getDurability()!=color){
								if(p.getGameMode().equals(GameMode.SURVIVAL) || !lib.useGamemode()){Amount=Amount-1;}
								is2.setDurability(color);
								packet.getInventory().setHelmet(is2);
							}
						}
					}
				}
			}

			manager.updateFurniture(obj);
			if(p.getGameMode().equals(GameMode.CREATIVE) && lib.useGamemode()) return;
			Integer o = Amount;
			Integer i = p.getInventory().getHeldItemSlot();
			ItemStack itemStack = p.getItemInHand();
			itemStack.setAmount(o);
			p.getInventory().setItem(i, itemStack);
			p.updateInventory();

			return;
		}else{
			if(!e.canBuild()) return;
			setTeller(e.getPlayer(), e.getPlayer().getInventory().getItemInHand());
		}
	}
	
	public void setTeller(Player player, ItemStack is){
		BlockFace b = lutil.yawToFace(player.getLocation().getYaw());
		ArmorStandPacket as = null;
		if(tellerIDs == null || tellerIDs.isEmpty()){return;}
		for(Integer id : this.tellerIDs){
			if(id!=null){
				ArmorStandPacket armorStand = manager.getArmorStandPacketByID(id);
				if(armorStand!=null){
					BlockFace b2 = lutil.yawToFace(armorStand.getLocation().getYaw());
					if(b2.equals(b)){
						as = armorStand;
						break;
					}
				}
			}
		}
		
		
		if(as!=null&&as.getInventory().getItemInHand()!= null && as.getInventory().getItemInHand().equals(is)){return;}
		if(as.getInventory().getItemInHand()!=null&&!as.getInventory().getItemInHand().getType().equals(Material.AIR)){
			ArmorStandPacket asp = as;
			ItemStack item = asp.getInventory().getItemInHand();
			item.setAmount(1);
			asp.getLocation().getWorld().dropItem(asp.getLocation(), item);
		}
		
		ItemStack IS = is.clone();
		IS.setAmount(1);
		as.getInventory().setItemInHand(IS);
		
		manager.updateFurniture(obj);
		
		if(player.getGameMode().equals(GameMode.CREATIVE) && lib.useGamemode()) return;
		Integer i = player.getInventory().getHeldItemSlot();
		ItemStack itemstack = is.clone();
		itemstack.setAmount(itemstack.getAmount()-1);
		player.getInventory().setItem(i, itemstack);
		player.updateInventory();
	}
	
	public HashMap<Integer, ItemStack> getTeller(){
		HashMap<Integer, ItemStack> teller = new HashMap<Integer, ItemStack>();
		for(Integer id : tellerIDs){
			try{
				ArmorStandPacket as = manager.getArmorStandPacketByID(id);
				teller.put(teller.size(), as.getInventory().getItemInHand());
			}catch(Exception e){
				teller.put(teller.size(), new ItemStack(Material.AIR));
			}
		}
		return teller;
	}
}
