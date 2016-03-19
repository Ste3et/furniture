package de.Ste3et_C0st.Furniture.Objects.RPG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
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
import de.Ste3et_C0st.FurnitureLib.main.Type.EventType;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class guillotine extends Furniture implements Listener{

	Boolean soundPlaying = false, isFinish = false;
	fArmorStand packet1, packet2, packet3;
	Inventory invI, invII, invIII;
	Integer timer;
	Player p;
	List<Integer> intList = Arrays.asList(10,16,19,28,37,43);
	List<Material> matList = Arrays.asList(
			Material.WOOD_SWORD,Material.WOOD_AXE,Material.WOOD_HOE,
			Material.STONE_SWORD, Material.STONE_AXE, Material.STONE_HOE,
			Material.IRON_SWORD, Material.IRON_AXE, Material.IRON_HOE,
			Material.GOLD_SWORD, Material.GOLD_AXE, Material.GOLD_HOE,
			Material.DIAMOND_SWORD, Material.DIAMOND_AXE, Material.DIAMOND_HOE);
	List<Material> matListI = Arrays.asList(
			Material.LEATHER_CHESTPLATE,Material.IRON_CHESTPLATE,Material.GOLD_CHESTPLATE,
			Material.DIAMOND_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE);
	List<Material> matListII = Arrays.asList(
			Material.LEATHER_LEGGINGS,Material.IRON_LEGGINGS,Material.GOLD_LEGGINGS,
			Material.DIAMOND_LEGGINGS, Material.CHAINMAIL_LEGGINGS);
	List<Material> matListIII = Arrays.asList(
			Material.LEATHER_BOOTS,Material.IRON_BOOTS,Material.GOLD_BOOTS,
			Material.DIAMOND_BOOTS, Material.CHAINMAIL_BOOTS);
	List<fArmorStand> armorStandList = new ArrayList<fArmorStand>();
	ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE);
	public guillotine(ObjectID id) {
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			setDefault();
			initializeInventory();
			return;
		}
		spawn(id.getStartLocation());
		initializeInventory();
	}
	
	private void initializeInventory(){
		pane = new ItemStack(Material.STAINED_GLASS_PANE);
		pane.setDurability((short) 15);
		ItemMeta im = pane.getItemMeta();
		im.setDisplayName("�c");
		pane.setItemMeta(im);
		
		
		String s = getObjID().getProjectOBJ().getCraftingFile().getRecipe().getResult().getItemMeta().getDisplayName();
		invI = Bukkit.createInventory(null, 9, s + "I");
		invII = Bukkit.createInventory(null, 54, s + "II");
		invIII = Bukkit.createInventory(null, 54, s + "III");
	}
	
	private fArmorStand getByName(String s){
		for(fArmorStand packets : getManager().getfArmorStandByObjectID(getObjID())){
			if(!packets.getName().equalsIgnoreCase("")){
				if(packets.getName().startsWith("#Oblation#")){
					return packets;
				}
			}
		}
		return null;
	}
	
	private void setDefault(){
		armorStandList.clear();
		if(packet2==null){packet2 = getByName("#Oblation#");}
		if(packet3==null){packet3 = getByName("#Head#");}
		for(fArmorStand packets : getManager().getfArmorStandByObjectID(getObjID())){
			if(!packets.getName().equalsIgnoreCase("")){
				if(!(packets.getName().startsWith("#Oblation#") && packets.getName().startsWith("#Head#"))){
					if(packets.getName().startsWith("iron")){
						armorStandList.add(packets);
						Location loc = getStartLocation(packets.getName());
						packets.teleport(loc);
					}else{
						packet1 = packets;
					}
				}
			}
		}
		
		if(packet3!=null){
			if(packet3.getHelmet()!=null){
				packet2.setHelmet(packet3.getHelmet());
				packet3.setHelmet(new ItemStack(Material.AIR));
			}
		}

		
		soundPlaying = false;
		if(packet1!=null){
			packet1.setPose(getLutil().degresstoRad(new EulerAngle(190,0,329)), BodyPart.RIGHT_ARM);
			packet1.setLeftArmPose(BodyPart.LEFT_ARM.getDefAngle());
		}

		if(packet2!=null){
			Location loc13 = getStartLocation(packet2.getName());
			packet2.teleport(loc13);
			packet2.setPose(getLutil().degresstoRad(new EulerAngle(40,0,320)), BodyPart.RIGHT_ARM);
			packet2.setPose(getLutil().degresstoRad(new EulerAngle(33,0,41)), BodyPart.LEFT_ARM);
			packet2.setPose(getLutil().degresstoRad(new EulerAngle(40,25,0)), BodyPart.HEAD);
		}
		timer = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), new Runnable() {
			int x = 0;
			@Override
			public void run() {
				if(x!=3){
					update();
					x++;
				}else{
					stopTimer();
				}
			}
		}, 0, 10);
		isFinish=false;
	}
	
	private Location getStartLocation(String s){
		String[] split = s.split(":");
		double x = Double.parseDouble(split[1]);
		double y = Double.parseDouble(split[2]);
		double z = Double.parseDouble(split[3]);
		Location loc = new Location(getWorld(), x, y, z);
		loc.setYaw(getLutil().FaceToYaw(getBlockFace()));
		return loc;
	}
	
	@Override
	public void spawn(Location loc) {
		List<fArmorStand> packList = new ArrayList<fArmorStand>();
		Location center = getLutil().getCenter(loc);
		center.add(0, -2.3, 0);
		for(int i = 1;i<=7;i++){
			Location location = getLutil().getRelativ(center, getBlockFace(), 0d, .2d).add(0, i*.62, 0);
			location.setYaw(getLutil().FaceToYaw(getBlockFace())+90);
			fArmorStand packet = getManager().createArmorStand(getObjID(), location);
			packet.getInventory().setHelmet(new ItemStack(Material.WOOD_STEP));
			packet.setPose(getLutil().degresstoRad(new EulerAngle(90, 0, 0)), BodyPart.HEAD);
			packList.add(packet);
			
			location = getLutil().getRelativ(center, getBlockFace(), 0d, -1.2d).add(0, i*.62, 0);
			location.setYaw(getLutil().FaceToYaw(getBlockFace())-90);
			packet = getManager().createArmorStand(getObjID(), location);
			packet.getInventory().setHelmet(new ItemStack(Material.WOOD_STEP));
			packet.setPose(getLutil().degresstoRad(new EulerAngle(90, 0, 0)), BodyPart.HEAD);
			packList.add(packet);
		}
		double j = .44;
		Location loc1 = getLutil().getRelativ(center.clone(), getBlockFace(), 0d, -.32d).add(0, .9+j, 0);
		Location loc2 = getLutil().getRelativ(center.clone(), getBlockFace(), 0d, -.65d).add(0, .9+j, 0);
		Location loc3 = getLutil().getRelativ(center.clone(), getBlockFace(), 0d, -.32d).add(0, 1.53+j, 0);
		Location loc4 = getLutil().getRelativ(center.clone(), getBlockFace(), 0d, -.65d).add(0, 1.53+j, 0);
		Location loc5 = getLutil().getRelativ(center.clone(), getBlockFace(), 0d, -.22d).add(0, 4.2, 0);
		Location loc6 = getLutil().getRelativ(center.clone(), getBlockFace(), 0d, -.75d).add(0, 4.2, 0);
		Location loc7 = getLutil().getRelativ(center.clone(), getBlockFace(), -.43d, 0d).add(0, 4.5, 0);
		
		Location loc8 = getLutil().getRelativ(center.clone(), getBlockFace(), -.01d, -.22d).add(0, 3.6, 0);
		Location loc9 = getLutil().getRelativ(center.clone(), getBlockFace(), -.01d, -.75d).add(0, 3.6, 0);
		Location loc10 = getLutil().getRelativ(center.clone(), getBlockFace(), -.01d, -.33d).add(0, 3.5, 0);
		Location loc11 = getLutil().getRelativ(center.clone(), getBlockFace(), -.01d, -.82d).add(0, 3.3, 0);
		
		loc1.setYaw(getYaw()+90);
		loc2.setYaw(getYaw()-90);
		loc3.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
		loc4.setYaw(getLutil().FaceToYaw(getBlockFace()));
		loc5.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
		loc6.setYaw(getLutil().FaceToYaw(getBlockFace()));
		loc7.setYaw(getLutil().FaceToYaw(getBlockFace())-90);
		loc8.setYaw(getLutil().FaceToYaw(getBlockFace()));
		loc9.setYaw(getLutil().FaceToYaw(getBlockFace()));
		loc10.setYaw(getLutil().FaceToYaw(getBlockFace()));
		loc11.setYaw(getLutil().FaceToYaw(getBlockFace()));
		
		fArmorStand packet = getManager().createArmorStand(getObjID(), loc1);
		packet.getInventory().setHelmet(new ItemStack(Material.WOOD_STAIRS));
		packList.add(packet);
		
		packet = getManager().createArmorStand(getObjID(), loc2);
		packet.getInventory().setHelmet(new ItemStack(Material.WOOD_STAIRS));
		packList.add(packet);
		
		packet = getManager().createArmorStand(getObjID(), loc3);
		packet.getInventory().setHelmet(new ItemStack(Material.WOOD_STEP));
		packList.add(packet);
		
		packet = getManager().createArmorStand(getObjID(), loc4);
		packet.getInventory().setHelmet(new ItemStack(Material.WOOD_STEP));
		packList.add(packet);
		
		packet = getManager().createArmorStand(getObjID(), loc5);
		packet.getInventory().setHelmet(new ItemStack(Material.STONE_PLATE));
		packList.add(packet);
		
		packet = getManager().createArmorStand(getObjID(), loc6);
		packet.getInventory().setHelmet(new ItemStack(Material.STONE_PLATE));
		packList.add(packet);
		
		packet = getManager().createArmorStand(getObjID(), loc7);
		packet.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
		packet.setPose(new EulerAngle(1.39, 0, 0), BodyPart.RIGHT_ARM);
		packList.add(packet);
		
		packet = getManager().createArmorStand(getObjID(), loc8);
		packet.getInventory().setHelmet(new ItemStack(Material.IRON_PLATE));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(90, 0, 0)), BodyPart.HEAD);
		packet.setName("iron1:" + loc8.getX() + ":" + loc8.getY() + ":" + loc8.getZ());
		packList.add(packet);
		armorStandList.add(packet);
		
		packet = getManager().createArmorStand(getObjID(), loc9);
		packet.getInventory().setHelmet(new ItemStack(Material.IRON_PLATE));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(90, 0, 0)), BodyPart.HEAD);
		packet.setName("iron2:" + loc9.getX() + ":" + loc9.getY() + ":" + loc9.getZ());
		packList.add(packet);
		armorStandList.add(packet);
		
		packet = getManager().createArmorStand(getObjID(), loc10);
		packet.getInventory().setHelmet(new ItemStack(Material.IRON_PLATE));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(90, 0, -22)), BodyPart.HEAD);
		packet.setName("iron3:" + loc10.getX() + ":" + loc10.getY() + ":" + loc10.getZ());
		packList.add(packet);
		armorStandList.add(packet);
		
		packet = getManager().createArmorStand(getObjID(), loc11);
		packet.getInventory().setHelmet(new ItemStack(Material.IRON_PLATE));
		packet.setPose(getLutil().degresstoRad(new EulerAngle(90, 0, -22)), BodyPart.HEAD);
		packet.setName("iron4:" + loc11.getX() + ":" + loc11.getY() + ":" + loc11.getZ());
		packList.add(packet);
		armorStandList.add(packet);

		Location loc12 = getLutil().getRelativ(center, getBlockFace(), -.9d, .5d).add(0, 1.7, 0);
		loc12.setYaw(getLutil().FaceToYaw(getBlockFace())+135);
		packet1 = getManager().createArmorStand(getObjID(), loc12);
		packet1.setPose(getLutil().degresstoRad(new EulerAngle(190,0,329)), BodyPart.RIGHT_ARM);
		packet1.setName("#Executioner#");
		packet1.setArms(true);
		packet1.setMarker(false);
		packet1.setBasePlate(false);
		packList.add(packet1);

		Location loc13 = getLutil().getRelativ(center, getBlockFace(), -1d, -.5d).add(0, 1.7, 0);
		loc13.setYaw(getLutil().FaceToYaw(getBlockFace()));
		packet2 = getManager().createArmorStand(getObjID(), loc13);
		packet2.setPose(getLutil().degresstoRad(new EulerAngle(40,0,320)), BodyPart.RIGHT_ARM);
		packet2.setPose(getLutil().degresstoRad(new EulerAngle(33,0,41)), BodyPart.LEFT_ARM);
		packet2.setPose(getLutil().degresstoRad(new EulerAngle(40,25,0)), BodyPart.HEAD);
		packet2.setName("#Oblation#:" + loc13.getX() + ":" + loc13.getY() + ":" + loc13.getZ());
		packet2.setArms(true);
		packet2.setMarker(false);
		packet2.setBasePlate(false);
		packList.add(packet2);
		
		Location loc14 = getLutil().getRelativ(center, getBlockFace(), 1d, -1d).add(0, +.3, 0);
		loc14.setYaw(getLutil().FaceToYaw(getBlockFace())+45);
		packet3 = getManager().createArmorStand(getObjID(), loc14);
		packet3.setMarker(false);
		packet3.setName("#HEAD#");
		packList.add(packet3);

		for(fArmorStand packets : packList){
			packets.setInvisible(true);
			packets.setBasePlate(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}

	private boolean canDrop(ItemStack stack){
		if(stack==null){return false;}
		if(stack.getType().equals(Material.AIR)){return false;}
		return true;
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		if(!isRunning()){
			if(canDrop(packet1.getHelmet())){getWorld().dropItem(getLocation(), packet1.getHelmet());}
			if(canDrop(packet1.getChestPlate())){getWorld().dropItem(getLocation(), packet1.getChestPlate());}
			if(canDrop(packet1.getLeggings())){getWorld().dropItem(getLocation(), packet1.getLeggings());}
			if(canDrop(packet1.getBoots())){getWorld().dropItem(getLocation(), packet1.getBoots());}
			if(canDrop(packet1.getItemInMainHand())){getWorld().dropItem(getLocation(), packet1.getItemInMainHand());}
			if(canDrop(packet2.getHelmet())){getWorld().dropItem(getLocation(), packet2.getHelmet());}
			if(canDrop(packet2.getChestPlate())){getWorld().dropItem(getLocation(), packet2.getChestPlate());}
			if(canDrop(packet2.getLeggings())){getWorld().dropItem(getLocation(), packet2.getLeggings());}
			if(canDrop(packet2.getBoots())){getWorld().dropItem(getLocation(), packet2.getBoots());}
			if(canDrop(packet3.getHelmet())){getWorld().dropItem(getLocation(), packet3.getHelmet());}
			if(!packet1.getName().equalsIgnoreCase("#Executioner#")){
				ItemStack is = new ItemStack(Material.NAME_TAG);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(packet1.getName());
				getWorld().dropItem(getLocation(), is);
			}
			
			this.getObjID().remove(e.getPlayer());
			getManager().remove(getObjID());
			delete();
		}
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		Player p = e.getPlayer();
		if(!getLib().canBuild(e.getPlayer(), getObjID(), EventType.INTERACT)){return;}
		if(p.isSneaking()){
			if(isFinish){return;}
			p.openInventory(invI);
			for(int i = 0; i<9;i++){invI.setItem(i, pane);}	
			
			ItemStack is = new ItemStack(Material.SKULL_ITEM);
			is.setDurability((short) 1);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName("�2Executioner");
			is.setItemMeta(im);
			invI.setItem(2, is);
			
			is = new ItemStack(Material.SKULL_ITEM);
			is.setDurability((short) 3);
			im = is.getItemMeta();
			im.setDisplayName("�cOblation");
			is.setItemMeta(im);
			invI.setItem(6, is);
			this.p = p;
		}else{
			if(isRunning()){return;}
			if(canStart()&&!isFinish){
				move();
			}else if(canStart()&&isFinish){
				setDefault();
			}
		}
	}
	
	@EventHandler
	private void onClick(InventoryClickEvent e){
		if(e.getClickedInventory()==null){return;}
		if(e.getCurrentItem()==null){return;}
		Player p = (Player) e.getWhoClicked();
		if(this.p!=null&&this.p.equals(p)){if(!e.getClick().equals(ClickType.LEFT)&&!e.getClick().equals(ClickType.RIGHT)){e.setCancelled(true);}}
		if(e.getClickedInventory().equals(invI)){
			e.setCancelled(true);
			if(e.getSlot() == 2){
				p.closeInventory();
				p.openInventory(invII);
				openInv(1);
				p.updateInventory();
			}else if(e.getSlot() == 6){
				p.closeInventory();
				p.openInventory(invIII);
				openInv(2);
				p.updateInventory();
			}
		}else if(e.getClickedInventory().equals(invII)){
			if(e.getCurrentItem().equals(pane)){e.setCancelled(true);}
			Material m = e.getCursor().getType();
			if(m==null||m.equals(Material.AIR)){return;}
			switch (e.getSlot()) {
			case 10:
				if(!m.equals(Material.SKULL_ITEM)){e.setCancelled(true);}
				break;
			case 16:
				if(!matList.contains(m)){e.setCancelled(true);}
				break;
			case 19:
				if(!matListI.contains(m)){e.setCancelled(true);}
				break;
			case 28:
				if(!matListII.contains(m)){e.setCancelled(true);}
				break;
			case 37:
				if(!matListIII.contains(m)){e.setCancelled(true);}
				break;
			case 43:
				if(!m.equals(Material.NAME_TAG)){e.setCancelled(true);}
				break;
			}
			
		}else if(e.getClickedInventory().equals(invIII)){
			if(e.getCurrentItem().equals(pane)){e.setCancelled(true);}
			Material m = e.getCursor().getType();
			if(m==null||m.equals(Material.AIR)){return;}
			switch (e.getSlot()) {
			case 10:
				if(!m.equals(Material.SKULL_ITEM)){e.setCancelled(true);}
				break;
			case 19:
				if(!matListI.contains(m)){e.setCancelled(true);}
				break;
			case 28:
				if(!matListII.contains(m)){e.setCancelled(true);}
				break;
			case 37:
				if(!matListIII.contains(m)){e.setCancelled(true);}
				break;
			}
		}
	}
	
	private boolean isRunning(){
		if(timer!=null){return true;}
		return false;
	}
	
	private boolean canStart(){
		if(isRunning()) return false;
		boolean a = false,b = false,c = false;
		a = !packet1.isVisible();
		b = !packet2.isVisible();
		if(packet1.getItemInMainHand()!=null&&!packet1.getItemInMainHand().equals(Material.AIR)){
			c = true;
		}
		
		if(a&&b&&c){
			return true;
		}
		return false;
	}
	
	private void move(){
		if(isRunning()){return;}
		timer = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), new Runnable() {
			boolean b1 = false;
			boolean b2 = false;
			boolean b3 = false;
			double d1 = .0;
			double d2 = .7;
			double d3 = -1.7;
			double d4 = 0;
			int i = 0;
			int j = 0;
			@Override
			public void run() {
				try{
					EulerAngle angle = getLutil().Radtodegress(packet2.getHeadPose());
					if(angle.getX()<90){
						angle = angle.setX(angle.getX()+4);
					}else{b1=true;}
					if(angle.getY()>0){
						angle = angle.setY(angle.getY()-6);
					}else{b2=true;}
					packet2.setHeadPose(getLutil().degresstoRad(angle));
					update();
					if(b1&&b2){
						if(d1!=d2){
							packet2.teleport(getLutil().getRelativ(packet2.getLocation(), getBlockFace(), .1, 0d));
							update();
							d1+=.1;
						}else{
							if(!b3){
								packet1.setRightArmPose(getLutil().degresstoRad(new EulerAngle(0,0,15)));
								packet1.setLeftArmPose(getLutil().degresstoRad(new EulerAngle(0,0,289)));
								b3=true;
							}else{
								if(i!=10){
									playSound();
									i++;
								}else{
									if(d4>d3){
										for(fArmorStand stand : armorStandList){
											stand.teleport(stand.getLocation().add(0, -1.7, 0));
										}
										d4=-1.7;
										update();
									}else{
										if(j==0){
											packet3.getInventory().setHelmet(packet2.getInventory().getHelmet());
											packet2.getInventory().setHelmet(new ItemStack(Material.AIR));
											update();
										}else if(j!=0&&j<3){
											update();
										}else if(j == 3){
											getWorld().playEffect(getLutil().getRelativ(getLocation(), getBlockFace(), 0d, -.1d).add(0, 1.3, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
											stopTimer();
											isFinish=true;
											return;
										}
										j++;
									}
								}
							}
						}
					}
				}catch(Exception e){
					stopTimer();
				}

				
			}
		}, 0, 1);
	}
	
	private void stopTimer(){
		if(isRunning()){
			Bukkit.getScheduler().cancelTask(timer);
			timer=null;
		}
	}
	
	@EventHandler
	private void onClose(InventoryCloseEvent e){
		if(e.getInventory()==null){return;}
		if(e.getInventory().equals(invII)){
			boolean invisible = true;
			//10,16,19,28,37,43
			packet1.getInventory().setHelmet(invII.getItem(10));
			packet1.getInventory().setChestPlate(invII.getItem(19));
			packet1.getInventory().setLeggings(invII.getItem(28));
			packet1.getInventory().setBoots(invII.getItem(37));
			
			if(invII.getItem(10)!=null){invisible=false;}
			if(invII.getItem(19)!=null){invisible=false;}
			if(invII.getItem(28)!=null){invisible=false;}
			if(invII.getItem(37)!=null){invisible=false;}
			
			if(!invisible){
				if(invII.getItem(43)!=null){
					if(invII.getItem(43).hasItemMeta()&&invII.getItem(43).getItemMeta().hasDisplayName()){
						packet1.setName(ChatColor.translateAlternateColorCodes('&', invII.getItem(43).getItemMeta().getDisplayName()));
						packet1.setNameVasibility(true);
					}else{
						if(invII.getItem(43)!=null&&!invII.getItem(43).getType().equals(Material.AIR)){getWorld().dropItem(getLocation().clone().add(0, 1, 0), invII.getItem(43));}
						packet1.setName("#Executioner#");
						packet1.setNameVasibility(false);
						packet1.setInvisible(false);
					}
				}else{
					if(invII.getItem(43)!=null&&!invII.getItem(43).getType().equals(Material.AIR)){getWorld().dropItem(getLocation().clone().add(0, 1, 0), invII.getItem(43));}
					packet1.setName("#Executioner#");
					packet1.setNameVasibility(false);
				}
				
				if(invII.getItem(16)!=null){
					packet1.getInventory().setItemInMainHand(invII.getItem(16));
				}
			}else{
				if(invII.getItem(43)!=null&&!invII.getItem(43).getType().equals(Material.AIR)){getWorld().dropItem(getLocation().clone().add(0, 1, 0), invII.getItem(43));}
				if(invII.getItem(16)!=null&&!invII.getItem(16).getType().equals(Material.AIR)){getWorld().dropItem(getLocation().clone().add(0, 1, 0), invII.getItem(16));}
				packet1.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
				packet1.setName("#Executioner#");
				packet1.setNameVasibility(!invisible);
			}
			packet1.setArms(true);
			packet1.setBasePlate(false);
			packet1.setInvisible(invisible);
			update();
			this.p = null;
		}else if(e.getInventory().equals(invIII)){
			boolean invisible = true;
			packet2.getInventory().setHelmet(invIII.getItem(10));
			packet2.getInventory().setChestPlate(invIII.getItem(19));
			packet2.getInventory().setLeggings(invIII.getItem(28));
			packet2.getInventory().setBoots(invIII.getItem(37));
			
			if(invIII.getItem(10)!=null){invisible=false;}
			if(invIII.getItem(19)!=null){invisible=false;}
			if(invIII.getItem(28)!=null){invisible=false;}
			if(invIII.getItem(37)!=null){invisible=false;}
			
			packet2.setBasePlate(false);
			packet2.setArms(true);
			packet2.setInvisible(invisible);
			update();
			this.p = null;
		}
	}
	
	private void playSound(){
		if(!soundPlaying){
			getWorld().playSound(getLocation(), Sound.ENTITY_GHAST_DEATH, 2, 1);
			getWorld().playSound(getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
			getWorld().playSound(getLocation(), Sound.ENTITY_GHAST_WARN, 1, 1);
			soundPlaying= true;
		}
	}
	
	private void openInv(int i){
		if(i>2||i<1){return;}
		fArmorStand packet;
		Inventory inv;
		if(i==1){
			packet = packet1;
			inv = invII;
		}else{
			packet = packet2;
			inv = invIII;
		}
		for(int j = 0; j<inv.getSize();j++){
			Material m = Material.AIR;
			if(!packet.getName().equalsIgnoreCase("#Executioner#")){m = Material.NAME_TAG;}
			ItemStack stack = new ItemStack(Material.AIR);
			ItemMeta meta = stack.getItemMeta();
			if(!m.equals(Material.AIR)){
				if(packet.getName()!=null){
					if(meta!=null){
						meta.setDisplayName(packet.getName());
						stack.setItemMeta(meta);
					}
				}
			}
			switch(j){
			case 10:inv.setItem(j, packet.getHelmet());break;
			case 19:inv.setItem(j, packet.getChestPlate());break;
			case 28:inv.setItem(j, packet.getLeggings());break;
			case 37:inv.setItem(j, packet.getBoots());break;
			case 16:if(i==1){inv.setItem(j, packet.getItemInMainHand());break;}
			case 43:if(i==1){inv.setItem(j, stack);break;}
			default: inv.setItem(j, pane);break;
			}
		}
		
		if(i==1){
			invII = inv;
		}else{
			invIII = inv;
		}
	}
}
