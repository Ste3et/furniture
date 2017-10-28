package de.Ste3et_C0st.Furniture.Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.Ste3et_C0st.FurnitureLib.Crafting.Project;
import de.Ste3et_C0st.FurnitureLib.ShematicLoader.Events.ProjectBreakEvent;
import de.Ste3et_C0st.FurnitureLib.ShematicLoader.Events.ProjectClickEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.HiddenStringUtils;
import de.Ste3et_C0st.FurnitureLib.Utilitis.ManageInv;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureHelper;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class Log extends FurnitureHelper implements Listener{

	private int mode = 0;
	private Inventory inv = Bukkit.createInventory(null, 9, "§2Settings");
	private ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE);
	private ItemStack permissions = new ItemStack(Material.ARROW);
	private Player p;
	private List<ItemStack> isList = new ArrayList<ItemStack>();
	
	public Log(ObjectID id) {
		super(id);
		Bukkit.getPluginManager().registerEvents(this, main.instance);
		setList();
		for(fEntity entity : getObjID().getPacketList()){
			entity.setNameVasibility(false);
		}
		update();
	}
	
	@EventHandler
	private void onBlockBreak(ProjectBreakEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onBlockBreak(ProjectClickEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		if(e.getPlayer().isSneaking()){
			if(e.getPlayer().getInventory().getItemInMainHand().getType().isBlock()&&
				!e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR)){
				e.setCancelled(false);
				return;
			}
			openInventory(e.getPlayer());
		}else{
			if(e.getPlayer().getInventory().getItemInMainHand().getType().isBlock() && !e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR)){
				getCenter().getBlock().setType(e.getPlayer().getInventory().getItemInMainHand().getType());
				getCenter().getBlock().setData((byte) e.getPlayer().getInventory().getItemInMainHand().getDurability());
				removeItem(e.getPlayer());
				return;
			}else if(!e.getPlayer().getInventory().getItemInMainHand().getType().isBlock() && !e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR)){
				fEntity stand = null;
				if(getProjectByItem(e.getPlayer().getInventory().getItemInMainHand()) != null){return;}
				for(fEntity s : getObjID().getPacketList()){
					if(s.getName().equalsIgnoreCase(mode+"")){
						stand = s;
						break;
					}
				}
				
				if(stand==null){return;}
				if(stand.getInventory().getItemInMainHand()!=null&&!stand.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
					if(e.getPlayer().getInventory().getItemInMainHand()!=null){
						if(e.getPlayer().getInventory().getItemInMainHand().equals(stand.getItemInMainHand())){
							return;
						}
					}
					ItemStack is = stand.getInventory().getItemInMainHand();
					is.setAmount(1);
					getWorld().dropItem(getLocation(), is);
				}
				
				ItemStack is = e.getPlayer().getInventory().getItemInMainHand().clone();
				is.setAmount(1);
				stand.setItemInHand(is);
				update();
				removeItem(e.getPlayer());
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
				
				stand.setItemInHand(new ItemStack(Material.AIR));
				update();
				return;
			}
		}
	}
	
	private void setList(){
		ItemStack stack = new ItemStack(Material.BANNER);
		stack.setDurability((short) 1);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§6Mode: §cTop");
		stack.setItemMeta(meta);
		isList.add(stack);
		stack = new ItemStack(Material.BANNER);
		stack.setDurability((short) 2);
		meta = stack.getItemMeta();
		meta.setDisplayName("§6Mode: §cFront I");
		stack.setItemMeta(meta);
		isList.add(stack);
		stack = new ItemStack(Material.BANNER);
		stack.setDurability((short) 11);
		meta = stack.getItemMeta();
		meta.setDisplayName("§6Mode: §cFront II");
		stack.setItemMeta(meta);
		isList.add(stack);
		
		meta = permissions.getItemMeta();
		meta.setDisplayName("§cChange Permissions (Owner Only)");
		permissions.setItemMeta(meta);
	}
	
	private Project getProjectByItem(ItemStack is){
		ItemStack stack = is.clone();
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
		if(e.getCurrentItem().getType().equals(Material.BANNER)){
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
	
	
}
