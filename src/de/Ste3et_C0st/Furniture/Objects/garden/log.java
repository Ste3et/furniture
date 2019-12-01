package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.Ste3et_C0st.Furniture.Main.FurnitureHook;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Crafting.Project;
import de.Ste3et_C0st.FurnitureLib.Utilitis.HiddenStringUtils;
import de.Ste3et_C0st.FurnitureLib.Utilitis.ManageInv;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class log extends Furniture implements Listener{

	private int mode = 0;
	private Inventory inv = Bukkit.createInventory(null, 9, "§2Settings");
	private ItemStack pane = FurnitureHook.isNewVersion() ? new ItemStack(Material.valueOf("BLACK_STAINED_GLASS_PANE")) : new ItemStack(Material.valueOf("STAINED_GLASS_PANE"),1 ,(short) 15);
	private ItemStack permissions = new ItemStack(Material.ARROW);
	private Player p;
	private List<ItemStack> isList = new ArrayList<ItemStack>();
	
	public log(ObjectID id) {
		super(id);
		Bukkit.getPluginManager().registerEvents(this, main.instance);
		getObjID().getPacketList().stream().forEach(entity -> {
			entity.setNameVasibility(false);
			((fArmorStand) entity).setMarker(false);
		});
		update();
		setList();
	}
	
	@Override
	public void onBreak(Player player) {
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			this.destroy(player);
		}
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
		ItemStack copy = is.clone();
		copy.setAmount(1);
		return copy;
	}

	@Override
	public void onClick(Player player){
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			if(player.isSneaking()){
				if(player.getInventory().getItemInMainHand().getType().isBlock()&&
					!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
					return;
				}
				openInventory(player);
			}else{
				ItemStack stack = player.getInventory().getItemInMainHand();
				if(stack == null || stack.getType().equals(Material.AIR)){
					//dropItem
					fEntity entity = entityByCustomName(this.mode + "");
					if(entity != null) {
						if(entity.getItemInMainHand() != null) {
							if(!entity.getItemInMainHand().getType().equals(Material.AIR)) {
								if(entity.getItemInMainHand().equals(stack)) {return;}
								getWorld().dropItem(getLocation(), entity.getItemInMainHand());
								entity.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
								update();
								}
							}
						}
				}else{
					if(getProjectByItem(stack) != null) {return;}
					if(stack.getType().isBlock()) {
						Block b = getCenter().getBlock();
						b.setType(stack.getType());
					}else{
						fEntity entity = entityByCustomName(this.mode + "");
						if(entity != null) {
							if(entity.getItemInMainHand() != null) {
								if(!entity.getItemInMainHand().getType().equals(Material.AIR)) {
									if(entity.getItemInMainHand().equals(stack)) {return;}
									getWorld().dropItem(getLocation(), entity.getItemInMainHand());
								}
							}
							ItemStack placeItem = stack.clone();
							placeItem.setAmount(1);
							entity.getInventory().setItemInMainHand(placeItem);
							consumeItem(player);
							update();
						}
					}
				}
			}
		}
	}
	
	private void setList(){
		ItemStack stack = FurnitureHook.isNewVersion() ? new ItemStack(Material.valueOf("WHITE_BANNER")) : new ItemStack(Material.valueOf("BANNER"), 1, (short) 1);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§6Mode: §cTop");
		stack.setItemMeta(meta);
		isList.add(stack);
		stack = FurnitureHook.isNewVersion() ? new ItemStack(Material.valueOf("ORANGE_BANNER")) : new ItemStack(Material.valueOf("BANNER"), 1, (short) 2);
		meta = stack.getItemMeta();
		meta.setDisplayName("§6Mode: §cFront I");
		stack.setItemMeta(meta);
		isList.add(stack);
		stack = FurnitureHook.isNewVersion() ? new ItemStack(Material.valueOf("BLUE_BANNER")) : new ItemStack(Material.valueOf("BANNER"), 1, (short) 11);
		meta = stack.getItemMeta();
		meta.setDisplayName("§6Mode: §cFront II");
		stack.setItemMeta(meta);
		isList.add(stack);
		
		meta = permissions.getItemMeta();
		meta.setDisplayName("§cChange Permissions (Owner Only)");
		permissions.setItemMeta(meta);
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
		if(e.getCurrentItem().getType().name().contains("BANNER")){
			Integer i = isList.indexOf(e.getCurrentItem());
			ItemStack is = null;
			if(i>=2){i = -1;}
			i++;
			is = isList.get(i);
			mode = i;
			if(FurnitureHook.isNewVersion()) {modeSwitch(e.getCurrentItem().getType()); }else {modeSwitch(e.getCurrentItem().getDurability()); }
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
	
	public void modeSwitch(Material type){
		int oldArmorStand = 0;
		int currentArmorStand = 0;
		
		switch (type) {
			case WHITE_BANNER:
				oldArmorStand = 0;
				currentArmorStand = 1;
				break;
			case ORANGE_BANNER:
				oldArmorStand = 1;
				currentArmorStand = 2;
				break;
			case BLUE_BANNER:
				oldArmorStand = 2;
				currentArmorStand = 0;
				break;
			default: break;
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

	@Override
	public void spawn(Location location) {}
	
}