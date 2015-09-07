package de.Ste3et_C0st.Furniture.Objects.RPG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;

public class weaponStand extends Furniture {

	Location loc;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	Plugin plugin;

	List<Integer> slotList1 = Arrays.asList(6,11,14,16,19,21,24,29,32,34,42);
	List<Integer> slotList2 = Arrays.asList(20, 15, 33);
	
	
	List<Material> matList = Arrays.asList(
			Material.FENCE_GATE,
			Material.SPRUCE_FENCE_GATE,
			Material.BIRCH_FENCE_GATE,
			Material.JUNGLE_FENCE_GATE,
			Material.DARK_OAK_FENCE_GATE,
			Material.ACACIA_FENCE_GATE);
	
	public weaponStand(FurnitureLib lib, Plugin plugin, ObjectID id) {
		super(lib, plugin, id);
		this.lutil = main.getLocationUtil();
		this.b = lutil.yawToFace(id.getStartLocation().getYaw());
		this.loc = id.getStartLocation().getBlock().getLocation();
		this.loc.setYaw(id.getStartLocation().getYaw());
		this.w = id.getStartLocation().getWorld();
		this.manager = lib.getFurnitureManager();
		this.lib = lib;
		this.plugin = plugin;
		this.obj = id;
		if(id.isFinish()){
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(id.getStartLocation());
	}

	Player p = null;
	Inventory inv = null;
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(obj==null){return;}
		if(!e.getID().equals(obj)){return;}
		if(e.isCancelled()){return;}
		if(!e.canBuild()){return;}
		if(p!=null){
			p.closeInventory();
		}
		List<ArmorStandPacket> asList = manager.getArmorStandPacketByObjectID(obj);
		for(ArmorStandPacket packet : asList){
			if(packet.getName()!=null&&!packet.getName().equalsIgnoreCase("")){
				if(packet.getInventory().getItemInHand()!=null){
					if(!packet.getInventory().getItemInHand().getType().equals(Material.AIR)){
						w.dropItem(loc, packet.getInventory().getItemInHand());
					}
				}
			}
		}
		e.remove();
		inv = null;
		obj=null;
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(obj==null){return;}
		if(obj.getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(p!=null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		if(!e.canBuild()){return;}
		ItemStack is1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemStack is3 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		
		ItemMeta im1 = is1.getItemMeta();
		ItemMeta im3 = is3.getItemMeta();
		im1.setDisplayName("§c");
		im3.setDisplayName("§c");
		is1.setItemMeta(im1);
		is3.setItemMeta(im3);
		
		this.p = e.getPlayer();
		inv = Bukkit.createInventory(null, 45, "§cWeaponBox");
		
		ItemStack itemstack = p.getItemInHand();
		if(itemstack!=null&&matList.contains(itemstack.getType())){
			for(ArmorStandPacket packet : manager.getArmorStandPacketByObjectID(obj)){
				if(packet.getInventory().getHelmet()!=null&&packet.getInventory().getHelmet().getType().toString().toLowerCase().endsWith("gate")){
					ItemStack itemStack = new ItemStack(itemstack.getType(), 1, (short) 0);
					packet.getInventory().setHelmet(itemStack);
				}
			}
			manager.updateFurniture(obj);
			return;
		}
		
		List<ArmorStandPacket> asList = manager.getArmorStandPacketByObjectID(obj);
		
		int j = 1;
		for(int i = 0; i<inv.getSize();i++){
			inv.setItem(i, is1);
			if(slotList1.contains(i)){
				inv.setItem(i, is3);
			}else if(slotList2.contains(i)){
				for(ArmorStandPacket packet : asList){
					if(packet.getName()!=null&&!packet.getName().equalsIgnoreCase("")){
						if(packet.getName().equalsIgnoreCase("#SLOT"+j+"#")){
							ItemStack is = new ItemStack(Material.AIR);
							if(packet.getInventory().getItemInHand()!=null){is = packet.getInventory().getItemInHand();}
							inv.setItem(i, is);
						}
					}
				}
				j++;
			}
		}
		
		this.p.openInventory(inv);
		this.p.updateInventory();
	}
	
	@EventHandler
	private void onClick(InventoryClickEvent e){
		if(obj==null){return;}
		if(obj.getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(p==null){return;}
		if(inv==null){return;}
		if(e.getInventory()==null){return;}
		if(e.getCurrentItem()==null){return;}
		if(!e.getInventory().equals(inv)){return;}
		ItemStack is = e.getCurrentItem();
		Material m = is.getType();
		if(!isValid(m)){e.setCancelled(true);}
	}
	
	public boolean isValid(Material m){
		String matName = m.toString().toLowerCase();
		boolean b = false;
		if(matName.endsWith("axe")){b=true;}
		if(matName.endsWith("hoe")){b=true;}
		if(matName.endsWith("pickaxe")){b=true;}
		if(matName.endsWith("spade")){b=true;}
		if(matName.endsWith("sword")){b=true;}
		if(matName.equalsIgnoreCase("air")){b=true;}
		return b;
	}
	
	@EventHandler
	private void onClose(InventoryCloseEvent e){
		if(obj==null){return;}
		if(obj.getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(p==null){return;}
		if(inv==null){return;}
		if(!e.getInventory().getTitle().equalsIgnoreCase(inv.getTitle())){return;}
		
		List<ArmorStandPacket> asList = manager.getArmorStandPacketByObjectID(obj);
		int j = 1;
		for(int i = 0; i<inv.getSize();i++){
			if(slotList2.contains(i)){
				for(ArmorStandPacket packet : asList){
					if(packet.getName()!=null&&!packet.getName().equalsIgnoreCase("")){
						if(packet.getName().equalsIgnoreCase("#SLOT"+j+"#")){
							ItemStack is = inv.getItem(i);
							if(is==null){is = new ItemStack(Material.AIR);}
							packet.getInventory().setItemInHand(is);
						}
					}
				}
				j++;
			}
		}
		
		if(e.getPlayer().equals(p)){
			this.p = null;
			this.inv = null;
			manager.updateFurniture(obj);
		}
	}

	@Override
	public void spawn(Location loc) {
		List<ArmorStandPacket> packList = new ArrayList<ArmorStandPacket>();
		Location center = lutil.getCenter(loc);
		center = center.add(0, -2.2, 0);
		
		Location center2 = center;
		Location center3 = center.clone();
		
		ArmorStandPacket as = manager.createArmorStand(obj, center2);
		as.getInventory().setHelmet(new ItemStack(Material.WOOD_PLATE, 1, (short) 0));
		as.setPose(lutil.degresstoRad(new EulerAngle(0, 45, 0)), BodyPart.HEAD);
		packList.add(as);
		
		as = manager.createArmorStand(obj, center3.add(0, .2, 0));
		as.getInventory().setHelmet(new ItemStack(Material.WOOD_PLATE, 1, (short) 0));
		as.setPose(lutil.degresstoRad(new EulerAngle(0, 45, 0)), BodyPart.HEAD);
		packList.add(as);
		
		Location location = center;
		location = location.add(0, 0, 0);
		
		float yaw = 0;
		for(int i = 0; i<4;i++){
			BlockFace face = lutil.yawToFace(yaw);
			Location locat = lutil.getRelativ(location, face, .185D, .185D);
			
			as = manager.createArmorStand(obj, locat);
			as.getInventory().setHelmet(new ItemStack(Material.FENCE_GATE, 1, (short) 0));
			as.setPose(lutil.degresstoRad(new EulerAngle(0, -45, 0)), BodyPart.HEAD);
			packList.add(as);
			
			as = manager.createArmorStand(obj, locat.add(0, .37, 0).clone());
			as.getInventory().setHelmet(new ItemStack(Material.FENCE_GATE, 1, (short) 0));
			as.setPose(lutil.degresstoRad(new EulerAngle(0, -45, 0)), BodyPart.HEAD);
			packList.add(as);
			
			as = manager.createArmorStand(obj, locat.add(0, .37, 0).clone());
			as.getInventory().setHelmet(new ItemStack(Material.FENCE_GATE, 1, (short) 0));
			as.setPose(lutil.degresstoRad(new EulerAngle(0, -45, 0)), BodyPart.HEAD);
			packList.add(as);
			
			yaw +=90;
		}
		
		as = manager.createArmorStand(obj, lutil.getRelativ(location, b, .5, .4d).add(0, 1.8, 0));
		as.setName("#SLOT1#");
		//as.getInventory().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
		as.setPose(lutil.degresstoRad(new EulerAngle(80, 30, 15)), BodyPart.RIGHT_ARM);
		as.setMarker(false);
		packList.add(as);
		
		BlockFace face = lutil.yawToFace(lutil.FaceToYaw(b) + 90);
		as = manager.createArmorStand(obj, lutil.getRelativ(location, face, .5, .45d).add(0, 1.75, 0));
		as.setName("#SLOT2#");
		//as.getInventory().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
		as.setPose(lutil.degresstoRad(new EulerAngle(80, 30, 20)), BodyPart.RIGHT_ARM);
		as.setMarker(false);
		packList.add(as);

		as = manager.createArmorStand(obj, lutil.getRelativ(location, b.getOppositeFace(), -.30, .65d).add(0, 1.0, 0));
		as.setName("#SLOT3#");
		//as.getInventory().setItemInHand(new ItemStack(Material.DIAMOND_AXE));
		as.setPose(lutil.degresstoRad(new EulerAngle(-80, 30, 15)), BodyPart.RIGHT_ARM);
		as.setMarker(false);
		packList.add(as);
		
		for(ArmorStandPacket asp : packList){
			asp.setGravity(false);
			asp.setInvisible(true);
		}
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

}
