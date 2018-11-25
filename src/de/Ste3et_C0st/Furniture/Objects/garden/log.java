package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Crafting.Project;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBlockBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBlockClickEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.HiddenStringUtils;
import de.Ste3et_C0st.FurnitureLib.Utilitis.ManageInv;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class log extends Furniture {

	Block b;
	int mode = 0;
	Inventory inv = Bukkit.createInventory(null, 9, "§2Settings");
	ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
	ItemStack permissions = new ItemStack(Material.ARROW);
	List<ItemStack> isList = new ArrayList<ItemStack>();
	Player p;
	
	public log(ObjectID id){
		super(id);
		b = getLocation().getBlock();
		ItemMeta meta = pane.getItemMeta();
		meta.setDisplayName("§c");
		pane.setItemMeta(meta);
		pane.setDurability((short) 15);
		pane.setItemMeta(meta);
		setList();
		getObjID().addBlock(Arrays.asList(b));
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(id.getStartLocation());
	}

	private void setList(){
		ItemStack stack = new ItemStack(Material.ORANGE_WOOL);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§6Mode: §cTop");
		stack.setItemMeta(meta);
		isList.add(stack);
		stack = new ItemStack(Material.MAGENTA_WOOL);
		meta = stack.getItemMeta();
		meta.setDisplayName("§6Mode: §cFront I");
		stack.setItemMeta(meta);
		isList.add(stack);
		stack = new ItemStack(Material.BLUE_WOOL);
		meta = stack.getItemMeta();
		meta.setDisplayName("§6Mode: §cFront II");
		stack.setItemMeta(meta);
		isList.add(stack);
		
		meta = permissions.getItemMeta();
		meta.setDisplayName("§cChange Permissions (Owner Only)");
		permissions.setItemMeta(meta);
	}
	
	@Override
	public void spawn(Location loc) {
		b.setType(Material.OAK_LOG);
		List<fArmorStand> lStand = new ArrayList<fArmorStand>();
		
		Location loc1 = getLutil().getRelativ(getCenter(), getBlockFace(), .5, .35);
		loc1.setYaw(getYaw());
		Location loc2 = getLutil().getRelativ(getCenter(), getBlockFace(), -.7, .35d);
		loc2.setYaw(getYaw());
		
		Location loc3 = getLutil().getRelativ(getCenter(), getBlockFace(), -1.3, .35d).subtract(0, 1.0, 0);
		loc3.setYaw(getYaw());
		
		fArmorStand stand = spawnArmorStand(loc1);
		stand.setRightArmPose(new EulerAngle(1.35, 0, 0));
		stand.setName("0");
		lStand.add(stand);
		
		
		stand = spawnArmorStand(loc2);
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(15, 0, 0)));
		stand.setName("1");
		lStand.add(stand);
		
		stand = spawnArmorStand(loc3);
		stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(-30, 0, 0)));
		stand.setName("2");
		lStand.add(stand);
		
		for(fArmorStand asp : lStand){
			asp.setInvisible(false);
			asp.setBasePlate(false);
			asp.setMarker(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	private void openInventory(Player p){
		if(this.p !=null) return;
		this.p = p;
		inv.clear();
		int j = mode;
		for(int i = 0; i<inv.getSize();i++){
			if(i==2){
				inv.addItem(isList.get(j));
				continue;
			}else if(i==6){
				inv.addItem(permissions);
				continue;
			}
			inv.setItem(i,pane);
		}
		p.openInventory(inv);
	}
	
	@EventHandler
	private void onClick(InventoryClickEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.getClickedInventory()==null||!e.getClickedInventory().equals(inv)){return;}
		e.setCancelled(true);
		if(e.getCurrentItem()==null){return;}
		if(e.getCurrentItem().getType().equals(Material.WHITE_BANNER)){
			Integer i = isList.indexOf(e.getCurrentItem());
			ItemStack is = null;
			if(i>=2){i = -1;}
			i++;
			is = isList.get(i);
			mode = i;
			modeSwitch(e.getCurrentItem().getDurability());
			inv.setItem(e.getSlot(), is);
			p.updateInventory();
		}else if(e.getCurrentItem().getType().equals(Material.ARROW)){
			if(!getObjID().getUUID().equals(p.getUniqueId())){
				if(!getLib().getPermission().hasPerm(p, "furniture.admin") && !p.isOp() && !getLib().getPermission().hasPerm(p, "furniture.manage.other")){
					return;
				}
			}
			p.closeInventory();
			new ManageInv((Player) e.getWhoClicked(), getObjID());
		}
	}
	
	public void modeSwitch(short dura){
		int oldArmorStand = 0;
		int currentArmorStand = 0;
		
		switch (dura) {
		case 1:
			oldArmorStand = 0;
			currentArmorStand = 1;
			break;
		case 2:
			oldArmorStand = 1;
			currentArmorStand = 2;
			break;
		case 11:
			oldArmorStand = 2;
			currentArmorStand = 0;
			break;
		}
		
		fEntity standOld = null;
		fEntity standCurrent = null;
		for(fEntity s : getObjID().getPacketList()){
			if(s.getName().equalsIgnoreCase(oldArmorStand+"")){
				standOld=s;
			}else if(s.getName().equalsIgnoreCase(currentArmorStand+"")){
				standCurrent=s;
			}
		}
		
		if(standOld!=null&&standCurrent!=null){
			standCurrent.setItemInMainHand(standOld.getItemInMainHand());
			standOld.setItemInMainHand(new ItemStack(Material.AIR));
			update();
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.getInventory()==null){return;}
		if(e.getInventory().equals(inv)){this.p = null;}
	}
	

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockClick(FurnitureBlockClickEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		Player p = e.getPlayer();
		if(p.isSneaking()){
			if(p.getInventory().getItemInMainHand().getType().isBlock()&&!p.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
				e.setCancelled(false);
				return;
			}
			openInventory(p);
		}else{
			if(p.getInventory().getItemInMainHand().getType().isBlock() && !p.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
				this.b.setType(p.getInventory().getItemInMainHand().getType());
				removeItem(p);
				return;
			}else if(!p.getInventory().getItemInMainHand().getType().isBlock() && !p.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
				fEntity stand = null;
				if(getProjectByItem(p.getInventory().getItemInMainHand()) != null){return;}
				for(fEntity s : getObjID().getPacketList()){
					if(s.getName().equalsIgnoreCase(mode+"")){
						stand = s;
						break;
					}
				}
				
				if(stand==null){return;}
				if(stand.getInventory().getItemInMainHand()!=null&&!stand.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
					if(p.getInventory().getItemInMainHand()!=null){
						if(p.getInventory().getItemInMainHand().equals(stand.getItemInMainHand())){
							return;
						}
					}
					ItemStack is = stand.getInventory().getItemInMainHand();
					is.setAmount(1);
					getWorld().dropItem(getLocation(), is);
				}
				
				ItemStack is = p.getInventory().getItemInMainHand().clone();
				is.setAmount(1);
				stand.setItemInMainHand(is);
				update();
				removeItem(p);
				return;
			}else{
				fEntity stand = null;
				for(fEntity s : getObjID().getPacketList()){
					if(s.getName().equalsIgnoreCase(mode+"")){
						stand = s;
						break;
					}
				}
				
				if(stand==null){return;}
				
				if(stand.getInventory().getItemInMainHand()!=null&&!stand.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
					for(fEntity s : getObjID().getPacketList()){
						if(s.getName().equalsIgnoreCase(mode+"")){
							stand = s;
							break;
						}
					}
					
					ItemStack is = stand.getInventory().getItemInMainHand();
					is.setAmount(1);
					getWorld().dropItem(getLocation(), is);
				}
				
				stand.setItemInMainHand(new ItemStack(Material.AIR));
				update();
				return;
			}
		}
	}
	
	public void removeItem(Player p){
		Boolean useGameMode = FurnitureLib.getInstance().useGamemode();
		if(useGameMode&&p.getGameMode().equals(GameMode.CREATIVE)){return;}
		Integer slot = p.getInventory().getHeldItemSlot();
		ItemStack itemStack = p.getInventory().getItemInMainHand().clone();
		itemStack.setAmount(itemStack.getAmount()-1);
		p.getInventory().setItem(slot, itemStack);
		p.updateInventory();
	}
	
	@Override
	public void onFurnitureBreak(FurnitureBreakEvent paramFurnitureBreakEvent) {}

	@Override
	public void onFurnitureClick(FurnitureClickEvent paramFurnitureClickEvent) {}
	
	@EventHandler
	public void onBlockBreak(FurnitureBlockBreakEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		Player p = e.getPlayer();
		
		fEntity stand = null;
		for(fEntity s : getObjID().getPacketList()){
			if(s.getName().equalsIgnoreCase(mode+"")){
				stand = s;
				break;
			}
		}
		
		if(stand.getItemInMainHand()!=null&&!stand.getItemInMainHand().getType().equals(Material.AIR)){
			ItemStack is = stand.getItemInMainHand();
			is.setAmount(1);
			getWorld().dropItem(getLocation(), is);
		}
		
		destroy(p);
		b.setType(Material.AIR);
		b=null;
	}
	
	private Project getProjectByItem(ItemStack is){
		ItemStack stack = getItemStackCopy(is);
		if(stack==null) return null;
		String systemID = "";
		if(stack.hasItemMeta()){
			if(stack.getItemMeta().hasLore()){
				List<String> s = stack.getItemMeta().getLore();
				if(HiddenStringUtils.hasHiddenString(s.get(0))) systemID = HiddenStringUtils.extractHiddenString(s.get(0));
			}
		}
		
		for(Project pro : FurnitureLib.getInstance().getFurnitureManager().getProjects()){
			if(pro==null) continue;
			if(pro.getSystemID()==null) continue;
			if(pro.getSystemID().equalsIgnoreCase(systemID)){
				return pro;
			}
		}
		return null;
	}
	
	private ItemStack getItemStackCopy(ItemStack is){
		ItemStack copy = new ItemStack(is.getType());
		copy.setAmount(1);
		copy.setDurability(is.getDurability());
		copy.setItemMeta(is.getItemMeta());
		return copy;
	}
}
