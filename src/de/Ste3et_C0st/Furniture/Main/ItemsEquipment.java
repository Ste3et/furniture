package de.Ste3et_C0st.Furniture.Main;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsEquipment {

	public ItemStack Laterne = null;
	public ItemStack Sofa = null;
	public ItemStack stuhl = null;
	public ItemStack tisch = null;
	public ItemStack tisch2 = null;
	public ItemsEquipment(){
		ItemMeta im = null;
		this.Sofa = new ItemStack(Material.MONSTER_EGG);
		im = this.Sofa.getItemMeta();
		im.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		im.setDisplayName("§cSofa");
		this.Sofa.setItemMeta(im);
		
		this.Laterne = new ItemStack(Material.MONSTER_EGG);
		im = this.Laterne.getItemMeta();
		im.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		im.setDisplayName("§cLaterne");
		this.Laterne.setItemMeta(im);
		
		this.stuhl = new ItemStack(Material.MONSTER_EGG);
		im = this.stuhl.getItemMeta();
		im.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		im.setDisplayName("§cStuhl");
		this.stuhl.setItemMeta(im);
		
		this.tisch = new ItemStack(Material.MONSTER_EGG);
		im = this.tisch.getItemMeta();
		im.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		im.setDisplayName("§cTisch");
		this.tisch.setItemMeta(im);
		
		this.tisch2 = new ItemStack(Material.MONSTER_EGG);
		im = this.tisch2.getItemMeta();
		im.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		im.setDisplayName("§cTisch2");
		this.tisch2.setItemMeta(im);
	}
}
