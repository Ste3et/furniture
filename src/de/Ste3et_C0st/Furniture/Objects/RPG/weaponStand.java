package de.Ste3et_C0st.Furniture.Objects.RPG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class weaponStand extends Furniture {

	List<Integer> slotList1 = Arrays.asList(6,11,14,16,19,21,24,29,32,34,42);
	List<Integer> slotList2 = Arrays.asList(20, 15, 33);
	List<Material> matList = Arrays.asList(
			Material.FENCE_GATE,
			Material.SPRUCE_FENCE_GATE,
			Material.BIRCH_FENCE_GATE,
			Material.JUNGLE_FENCE_GATE,
			Material.DARK_OAK_FENCE_GATE,
			Material.ACACIA_FENCE_GATE);
	
	public weaponStand(ObjectID id) {
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}

	Player p = null;
	Inventory inv = null;
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(getObjID()==null){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(e.isCancelled()){return;}
		if(!e.canBuild()){return;}
		if(p!=null){
			p.closeInventory();
		}
		List<fArmorStand> asList = getManager().getfArmorStandByObjectID(getObjID());
		for(fArmorStand packet : asList){
			if(packet.getName()!=null&&!packet.getName().equalsIgnoreCase("")){
				if(packet.getInventory().getItemInMainHand()!=null){
					if(!packet.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
						getWorld().dropItem(getLocation(), packet.getInventory().getItemInMainHand());
					}
				}
			}
		}
		e.remove();
		inv = null;
		delete();
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(p!=null){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		this.p = e.getPlayer();
		
		ItemStack itemstack = p.getInventory().getItemInMainHand();
		if(itemstack!=null&&matList.contains(itemstack.getType())){
			for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
				if(packet.getInventory().getHelmet()!=null){
					if(packet.getInventory().getHelmet().getType().name().toLowerCase().endsWith("gate")){
						ItemStack itemStack = new ItemStack(itemstack.getType(), 1, (short) 0);
						packet.getInventory().setHelmet(itemStack);
					}
				}
			}
			getManager().updateFurniture(getObjID());
			this.p = null;
			return;
		}
		
		ItemStack is1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemStack is3 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		
		ItemMeta im1 = is1.getItemMeta();
		ItemMeta im3 = is3.getItemMeta();
		im1.setDisplayName("�c");
		im3.setDisplayName("�c");
		is1.setItemMeta(im1);
		is3.setItemMeta(im3);
		
		inv = Bukkit.createInventory(null, 45, "�cWeaponBox");
		List<fArmorStand> asList = getManager().getfArmorStandByObjectID(getObjID());
		
		int j = 1;
		for(int i = 0; i<inv.getSize();i++){
			inv.setItem(i, is1);
			if(slotList1.contains(i)){
				inv.setItem(i, is3);
			}else if(slotList2.contains(i)){
				for(fArmorStand packet : asList){
					if(packet.getName()!=null&&!packet.getName().equalsIgnoreCase("")){
						if(packet.getName().equalsIgnoreCase("#SLOT"+j+"#")){
							ItemStack is = new ItemStack(Material.AIR);
							if(packet.getInventory().getItemInMainHand()!=null){is = packet.getInventory().getItemInMainHand();}
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
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
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
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(p==null){return;}
		if(inv==null){return;}
		if(!e.getInventory().getTitle().equalsIgnoreCase(inv.getTitle())){return;}
		
		List<fArmorStand> asList = getManager().getfArmorStandByObjectID(getObjID());
		int j = 1;
		for(int i = 0; i<inv.getSize();i++){
			if(slotList2.contains(i)){
				for(fArmorStand packet : asList){
					if(packet.getName()!=null&&!packet.getName().equalsIgnoreCase("")){
						if(packet.getName().equalsIgnoreCase("#SLOT"+j+"#")){
							ItemStack is = inv.getItem(i);
							if(is==null){is = new ItemStack(Material.AIR);}
							packet.getInventory().setItemInMainHand(is);
						}
					}
				}
				j++;
			}
		}
		
		if(e.getPlayer().equals(p)){
			this.p = null;
			this.inv = null;
			getManager().updateFurniture(getObjID());
		}
	}

	@Override
	public void spawn(Location loc) {
		List<fArmorStand> packList = new ArrayList<fArmorStand>();
		Location center = getLutil().getCenter(loc);
		center = center.add(0, -2.2, 0);
		
		Location center2 = center;
		Location center3 = center.clone();
		double offsety = 0.4;
		fArmorStand as = getManager().createArmorStand(getObjID(), center2.add(0, offsety, 0));
		as.getInventory().setHelmet(new ItemStack(Material.WOOD_PLATE, 1, (short) 0));
		as.setPose(getLutil().degresstoRad(new EulerAngle(0, 45, 0)), BodyPart.HEAD);
		packList.add(as);
		
		as = getManager().createArmorStand(getObjID(), center3.add(0, .2, 0).add(0, offsety, 0));
		as.getInventory().setHelmet(new ItemStack(Material.WOOD_PLATE, 1, (short) 0));
		as.setPose(getLutil().degresstoRad(new EulerAngle(0, 45, 0)), BodyPart.HEAD);
		packList.add(as);
		
		Location location = center;
		location = location.add(0, -offsety, 0);
		
		float yaw = 0;
		for(int i = 0; i<4;i++){
			BlockFace face = getLutil().yawToFace(yaw);
			Location locat = getLutil().getRelativ(location, face, -.0D, -.0D);
			
			as = getManager().createArmorStand(getObjID(), locat.clone());
			as.getInventory().setHelmet(new ItemStack(Material.FENCE_GATE, 1, (short) 0));
			as.setPose(getLutil().degresstoRad(new EulerAngle(0, -45, 0)), BodyPart.HEAD);
			packList.add(as);
			
			as = getManager().createArmorStand(getObjID(), locat.add(0, .37, 0).clone());
			as.getInventory().setHelmet(new ItemStack(Material.FENCE_GATE, 1, (short) 0));
			as.setPose(getLutil().degresstoRad(new EulerAngle(0, -45, 0)), BodyPart.HEAD);
			packList.add(as);
			
			as = getManager().createArmorStand(getObjID(), locat.add(0, .37, 0).clone());
			as.getInventory().setHelmet(new ItemStack(Material.FENCE_GATE, 1, (short) 0));
			as.setPose(getLutil().degresstoRad(new EulerAngle(0, -45, 0)), BodyPart.HEAD);
			packList.add(as);
			
			yaw +=90;
		}
		
		as = getManager().createArmorStand(getObjID(), getLutil().getRelativ(location, getBlockFace(), .5, .4d).add(0, 1.8, 0));
		as.setName("#SLOT1#");
		as.setPose(getLutil().degresstoRad(new EulerAngle(80, 30, 15)), BodyPart.RIGHT_ARM);
		as.setMarker(false);
		packList.add(as);
		
		BlockFace face = getLutil().yawToFace(getLutil().FaceToYaw(getBlockFace()) + 90);
		as = getManager().createArmorStand(getObjID(), getLutil().getRelativ(location, face, .5, .45d).add(0, 1.75, 0));
		as.setName("#SLOT2#");
		as.setPose(getLutil().degresstoRad(new EulerAngle(80, 30, 20)), BodyPart.RIGHT_ARM);
		as.setMarker(false);
		packList.add(as);

		as = getManager().createArmorStand(getObjID(), getLutil().getRelativ(location, getBlockFace().getOppositeFace(), -.30, .65d).add(0, 1.0, 0));
		as.setName("#SLOT3#");
		//as.getInventory().setItemInHand(new ItemStack(Material.DIAMOND_AXE));
		as.setPose(getLutil().degresstoRad(new EulerAngle(-80, 30, 15)), BodyPart.RIGHT_ARM);
		as.setMarker(false);
		packList.add(as);
		
		for(fArmorStand asp : packList){
			asp.setInvisible(true);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
}
