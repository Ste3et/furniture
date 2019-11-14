package de.Ste3et_C0st.Furniture.Objects.RPG;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class weaponStand extends Furniture implements Listener{

	List<Integer> slotList1 = Arrays.asList(6,11,14,16,19,21,24,29,32,34,42);
	List<Integer> slotList2 = Arrays.asList(20, 15, 33);
	List<Material> matList = Arrays.asList(
			Material.valueOf(FurnitureHook.isNewVersion() ? "OAK_FENCE_GATE" : "FENCE_GATE"),
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
	
	@Override
	public void onBreak(Player player) {
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			if(p!=null){
				p.closeInventory();
				inv = null;
			}
			List<fEntity> asList = getManager().getfArmorStandByObjectID(getObjID());
			for(fEntity packet : asList){
				if(packet.getName()!=null&&!packet.getName().equalsIgnoreCase("")){
					if(packet.getInventory().getItemInMainHand()!=null){
						if(!packet.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
							getWorld().dropItem(getLocation(), packet.getInventory().getItemInMainHand());
						}
					}
				}
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
			if(this.p != null) {return;}
			this.p = player;
			ItemStack itemstack = p.getInventory().getItemInMainHand();
			if(itemstack!=null&&matList.contains(itemstack.getType())){
				for(fEntity packet : getManager().getfArmorStandByObjectID(getObjID())){
					if(packet.getInventory().getHelmet()!=null){
						if(packet.getInventory().getHelmet().getType().name().toLowerCase().endsWith("gate")){
							ItemStack itemStack = new ItemStack(itemstack.getType(), 1);
							packet.getInventory().setHelmet(itemStack);
						}
					}
				}
				getManager().updateFurniture(getObjID());
				this.p = null;
				return;
			}
			
			ItemStack is1 = FurnitureHook.isNewVersion() ? new ItemStack(Material.valueOf("BLACK_STAINED_GLASS_PANE"), 1) : new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 15);
			ItemStack is3 = FurnitureHook.isNewVersion() ? new ItemStack(Material.valueOf("BLACK_STAINED_GLASS_PANE"), 1) : new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 14);
			
			ItemMeta im1 = is1.getItemMeta();
			ItemMeta im3 = is3.getItemMeta();
			im1.setDisplayName("§c");
			im3.setDisplayName("§c");
			is1.setItemMeta(im1);
			is3.setItemMeta(im3);
			
			inv = Bukkit.createInventory(null, 45, "§cWeaponBox");

			List<fEntity> asList = getManager().getfArmorStandByObjectID(getObjID());
			
			int j = 1;
			for(int i = 0; i<inv.getSize();i++){
				inv.setItem(i, is1);
				if(slotList1.contains(i)){
					inv.setItem(i, is3);
				}else if(slotList2.contains(i)){
					for(fEntity packet : asList){
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
		if(!e.getView().getTopInventory().equals(this.inv)){return;}
		
		List<fEntity> asList = getManager().getfArmorStandByObjectID(getObjID());
		int j = 1;
		for(int i = 0; i<inv.getSize();i++){
			if(slotList2.contains(i)){
				for(fEntity packet : asList){
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
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
}
