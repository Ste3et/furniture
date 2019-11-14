package de.Ste3et_C0st.Furniture.Objects.RPG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.FurnitureHook;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.EventType;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class Guillotine extends Furniture implements Listener{

	Boolean soundPlaying = false, isFinish = false;
	fArmorStand packet1, packet2, packet3;
	Inventory invI, invII, invIII;
	Integer timer;
	Player p;
	List<Integer> intList = Arrays.asList(10,16,19,28,37,43);
	private static List<Material> matList,  matListI, matListII, matListIII;
	
	static {
		List<Material> listMaterial = Arrays.asList(Material.values());
		matList = listMaterial.stream().filter(mat -> mat.name().contains("SWORD") || mat.name().contains("_AXE") || mat.name().contains("HOE")).collect(Collectors.toList());
		matListI = listMaterial.stream().filter(mat -> mat.name().contains("CHESTPLATE")).collect(Collectors.toList());
		matListII = listMaterial.stream().filter(mat -> mat.name().contains("LEGGINGS")).collect(Collectors.toList());
		matListIII = listMaterial.stream().filter(mat -> mat.name().contains("BOOTS")).collect(Collectors.toList());
	}
	
	
	
	List<fEntity> armorStandList = new ArrayList<fEntity>();
	ItemStack pane = FurnitureHook.isNewVersion() ? new ItemStack(Material.valueOf("BLACK_STAINED_GLASS_PANE")) : new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 15);
	public Guillotine(ObjectID id) {
		super(id);
		if(isFinish()){
			setDefault();
			initializeInventory();
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		setDefault();
		initializeInventory();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	private void initializeInventory(){
		ItemMeta im = pane.getItemMeta();
		im.setDisplayName("§c");
		pane.setItemMeta(im);
		
		
		String s = getObjID().getProjectOBJ().getCraftingFile().getRecipe().getResult().getItemMeta().getDisplayName();
		invI = Bukkit.createInventory(null, 9, s + "I");
		invII = Bukkit.createInventory(null, 54, s + "II");
		invIII = Bukkit.createInventory(null, 54, s + "III");
	}
	
	private void setDefault(){
		armorStandList.clear();
		
		this.packet1 = (fArmorStand) entityByCustomName("#Executioner#");
		this.packet3 = (fArmorStand) entityByCustomName("#Head#");
		this.packet2 = (fArmorStand) getfAsList().stream().filter(entity -> entity.getCustomName().startsWith("#Oblation#")).findFirst().orElse(null);
		
		getfAsList().stream().filter(entity -> entity.getCustomName().startsWith("iron")).forEach(entity -> {
			armorStandList.add(entity);
			entity.teleport(getStartLocation(entity.getCustomName()));
		});
		
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
		
	}

	private boolean canDrop(ItemStack stack){
		if(stack==null){return false;}
		if(stack.getType().equals(Material.AIR)){return false;}
		return true;
	}

	@Override
	public void onBreak(Player player) {
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
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
			this.destroy(player);
		}
	}

	@Override
	public void onClick(Player player){
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			if(!getLib().canBuild(player, getObjID(), EventType.INTERACT)){return;}
			if(player.isSneaking()){
				if(isFinish){return;}
				player.openInventory(invI);
				for(int i = 0; i<9;i++){invI.setItem(i, pane);}	
				
				ItemStack is = FurnitureHook.isNewVersion() ? new ItemStack(Material.valueOf("ZOMBIE_HEAD")) : new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short) 1);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName("§2Executioner");
				is.setItemMeta(im);
				invI.setItem(2, is);
				
				is = FurnitureHook.isNewVersion() ? new ItemStack(Material.valueOf("PLAYER_HEAD")) : new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short) 3);
				im = is.getItemMeta();
				im.setDisplayName("§cOblation");
				is.setItemMeta(im);
				invI.setItem(6, is);
				this.p = player;
			}else{
				if(isRunning()){return;}
				if(canStart()&&!isFinish){
					move();
				}else if(canStart()&&isFinish){
					setDefault();
				}
			}
		}
	}
	
	@EventHandler
	private void onClick(InventoryClickEvent e){
		if(e.getClickedInventory()==null){return;}
		if(e.getCurrentItem()==null){return;}
		Player p = (Player) e.getWhoClicked();
		if(p==null) return;
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
			Material m = e.getCurrentItem().getType();
			if(m==null||m.equals(Material.AIR)){return;}
			switch (e.getSlot()) {
			case 10:
				if(!m.equals(Material.valueOf(FurnitureHook.isNewVersion() ? "PLAYER_HEAD" : "SKULL_ITEM"))){e.setCancelled(true);}
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
			Material m = e.getCurrentItem().getType();
			if(m==null||m.equals(Material.AIR)){return;}
			switch (e.getSlot()) {
			case 10:
				if(!m.equals(Material.valueOf(FurnitureHook.isNewVersion() ? "PLAYER_HEAD" : "SKULL_ITEM"))){e.setCancelled(true);}
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
		a = !packet1.isInvisible();
		b = !packet2.isInvisible();
		
		if(packet1.getItemInMainHand() == null) {
			return false;
		}
		
		if(packet1.getHelmet() == null) {
			return false;
		}
		
		if(packet1.getItemInMainHand()!=null&&!packet1.getItemInMainHand().getType().equals(Material.AIR)){
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
										for(fEntity stand : armorStandList){
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
