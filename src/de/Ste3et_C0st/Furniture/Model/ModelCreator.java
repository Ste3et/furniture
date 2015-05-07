package de.Ste3et_C0st.Furniture.Model;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.Type.HeadArmType;
import de.Ste3et_C0st.Furniture.Main.Utils;

public class ModelCreator {

	String ID;
	Integer activeArmorStandID;
	Player p;
	Modele m;
	ModelInventory inv;
	Material mat;
	byte dru;
	Location l;
	//The Modele is the first model of the furniture
	//The Spawner class can spawn 1-1 copy of the furniture
	@SuppressWarnings("deprecation")
	public ModelCreator(Player p, String ID) {
		this.p = p;
		this.ID = ID;
		l = Utils.getCenter(p.getLocation().getBlock().getLocation().add(0, -.5, 0));
		l.setYaw(p.getLocation().getYaw());
		this.m = new Modele(ID, l);
		this.inv = new ModelInventory(p);
		this.inv.setInventory(ID);
		this.mat = l.getBlock().getType();
		this.dru = l.getBlock().getData();
		l.getBlock().setType(Material.REDSTONE_BLOCK);
	}
	
	public Integer addArmorStand(Vector v1, HeadArmType type, EulerAngle eular, ItemStack is, Boolean show, Boolean mini, BlockFace b, BlockFace b2){
		return m.addArmorStand(v1, type, eular, is, show, mini, b, b2);
	}
	
	public void removeArmorStand(Integer index){
		m.removeArmorStand(index);
	}
	
	public void editArmorStand(Vector v1, HeadArmType type, EulerAngle eular, ItemStack is, boolean show, boolean mini, Integer index, BlockFace b, BlockFace b2){
		m.editArmorStand(v1, type, eular, is, show, mini, index, b, b2);
	}

	public void setActiveArmorStandIndex(Integer i) {activeArmorStandID = i;}
	public Integer getActive() {return this.activeArmorStandID;}
	public ArmorStand getArmorStand(Integer index){return m.getArmorStand(index, p.getWorld());}
	public HeadArmType getType(Integer index) {return m.getType(index);}
	public EulerAngle getEuler(Integer index, HeadArmType type) {return m.getEuler(index, type);}
	public ItemStack getItemStack(Integer index) {return m.getItemStack(index);}
	public ModelInventory getModelInv(){return this.inv;}
	public Modele getModel() {return this.m;}
	public Location getStartLocation() {return l;}
	public Material getMaterial(){return mat;}

	@SuppressWarnings("deprecation")
	public void removeall() {
		m.removeall();
		this.l.getBlock().setType(this.mat);
		this.l.getBlock().setData(this.dru);
	}
}
