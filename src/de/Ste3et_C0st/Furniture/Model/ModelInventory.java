package de.Ste3et_C0st.Furniture.Model;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ModelInventory {
	Player p;
	Inventory inv;
	HashMap<Integer, ItemStack> isList = new HashMap<Integer, ItemStack>();
	public ModelInventory(Player p){
		this.p = p;
		this.inv = p.getInventory();
	}
	
	public void setInventory(String id){
		this.p.getInventory().clear();
		Inventory inv = this.p.getInventory();
		
		ItemStack is = new ItemStack(Material.BEDROCK);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("§9" + id + " §6Editor");
		is.setItemMeta(im);
		inv.setItem(0, is);
		isList.put(0, is);
		
		is = new ItemStack(Material.BOOK);
		im = is.getItemMeta();
		im.setDisplayName("§cTutorial");
		is.setItemMeta(im);
		inv.setItem(2, is);
		isList.put(1, is);
		
		is = new ItemStack(Material.GOLD_AXE);
		im = is.getItemMeta();
		im.setDisplayName("§6DegressMode: §cRotate");
		is.setItemMeta(im);
		inv.setItem(4, is);
		isList.put(2, is);
		
		is = new ItemStack(Material.ARROW);
		im = is.getItemMeta();
		im.setDisplayName("§4exit editor");
		is.setItemMeta(im);
		inv.setItem(8, is);
		isList.put(3, is);
		this.p.updateInventory();
	}
	
	public void leaveEditor(){
		this.p.getInventory().clear();
		this.p.getInventory().setContents(this.inv.getContents());
		this.p.updateInventory();
		this.isList.clear();
		this.inv = null;
	}
	
	public ItemStack getItem(){
		return isList.get(2);
	}
	
	public void setItem(Integer i){
		if(i>5){i = 0;}
		Inventory inv = this.p.getInventory();
		ItemStack is = new ItemStack(Material.GOLD_AXE);
		ItemMeta im = is.getItemMeta();
		if(i.equals(0)){
			im.setDisplayName("§6DegressMode: §cRotate");
			is.setItemMeta(im);
		}else if(i.equals(1)){
			im.setDisplayName("§6DegressMode: §cX");
			is.setItemMeta(im);
		}else if(i.equals(2)){
			im.setDisplayName("§6DegressMode: §cY");
			is.setItemMeta(im);
		}else if(i.equals(3)){
			im.setDisplayName("§6DegressMode: §cZ");
			is.setItemMeta(im);
		}
		
		inv.setItem(4, is);
		isList.put(2, is);
		this.p.updateInventory();
	}
}
