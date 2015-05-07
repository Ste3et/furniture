package de.Ste3et_C0st.Furniture.Model;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.Utils;
import de.Ste3et_C0st.Furniture.Main.Type.HeadArmType;

public class Modele {
	private List<String> IDList = new ArrayList<String>();
	private List<String> colorList = new ArrayList<String>();
	private List<String> sitable = new ArrayList<String>();
	private List<String> firelist = new ArrayList<String>();
	private String iD;
	private Location start;
	private BlockFace b;
	private World w;
	private int copyID;
	public List<String> getList(){return this.IDList;}
	public String getID(){return this.iD;}
	public World getWorld(){return this.w;}
	public BlockFace getDirection(){return this.b;}
	public Location getLocation(){return this.start;}
	
	public Modele(String iD, Location loc) {
		this.start = loc;
		this.iD = iD;
		this.w = loc.getWorld();
		this.b = Utils.yawToFace(loc.getYaw());
		ModelCreateEvent event = new ModelCreateEvent(this, start);
		if(iD.contains("#")){
			copyID = Integer.parseInt(iD.split("#")[1]);
		}else{
			copyID = 0;
		}
		
		if(!event.isCancelled()){
			ModelManager.getModelCreator().addModelListe(this);
		}
	}

	//Create create one Model
	//When the Model is create you can Spawn a clone to the Location
	//A model copy starts with the original ID like sofa#COPYID
	//A ArmorStand has the the copy id 123456-123456-COPYID
	public Integer addArmorStand(Vector v, HeadArmType type, EulerAngle eular, ItemStack is, boolean show, boolean mini, BlockFace bf, BlockFace bf2){
		String id = Utils.createRandomRegistryId() + "-" + copyID;
		Location l = v.toLocation(w);
		if(bf2!=null){
			l.setYaw(Utils.FaceToYaw(bf2));
		}
		ArmorStand armorstand = (ArmorStand) w.spawnEntity(l, EntityType.ARMOR_STAND);
		armorstand.setCustomName(id);
		switch (type) {
		case HEAD:
			armorstand.setArms(false);
			if(is!=null&&!is.getType().equals(Material.AIR)){armorstand.setHelmet(is);}
			if(eular!=null){armorstand.setHeadPose(eular);}
			break;
		case ARM:
			armorstand.setArms(true);
			if(is!=null&&!is.getType().equals(Material.AIR)){armorstand.setItemInHand(is);}
			if(eular!=null){armorstand.setRightArmPose(eular);}
			break;
		}
		armorstand.setVisible(show);
		armorstand.setBasePlate(show);
		armorstand.setSmall(mini);
		armorstand.setGravity(false);
		IDList.add(id);
		return IDList.indexOf(id);
	}
	
	public void editArmorStand(Vector v, HeadArmType type, EulerAngle eular, ItemStack is, boolean show, boolean mini, Integer index, BlockFace bf, BlockFace bf2){
		if(index==null){return;}
		if(index>=IDList.size()){return;}
		ArmorStand armorstand = getArmorStand(index, w);
		if(armorstand == null){return;}
		Location l = v.toLocation(w);
		if(bf2!=null){
			l.setYaw(Utils.FaceToYaw(bf2));
		}
		armorstand.teleport(l);
		switch (type) {
		case HEAD:
			armorstand.setArms(false);
			if(is!=null&&!is.getType().equals(Material.AIR)){armorstand.setHelmet(is);}
			if(eular!=null){armorstand.setHeadPose(eular);}
			break;
		case ARM:
			armorstand.setArms(true);
			if(is!=null&&!is.getType().equals(Material.AIR)){armorstand.setItemInHand(is);}
			if(eular!=null){armorstand.setRightArmPose(eular);}
			break;
		}
		
		armorstand.setVisible(show);
		armorstand.setBasePlate(show);
		armorstand.setSmall(mini);
		//Koordinaten hier rein             X        Y        Z         = Location
	}
	
	public void removeArmorStand(Integer index){
		if(index ==null){return;}
		if(getArmorStand(index, w)==null){return;}
		getArmorStand(index, w).remove();
		IDList.remove(index);
	}
	
	public BlockFace getArmorStandFace(Integer index){
		if(index ==null){return null;}
		if(getArmorStand(index, w)==null){return null;}
		return Utils.yawToFace(getArmorStand(index, w).getLocation().getYaw());
	}
	
	public String getID(Integer index){
		if(index==null){return null;}
		if(index>=IDList.size()){return null;}
		return IDList.get(index);
	}
	
	public Integer getIndex(String s){
		if(s==null){return null;}
		if(!IDList.contains(s)){return null;}
		return IDList.indexOf(s);
	}
	
	public ArmorStand getArmorStand(Integer index, World w){
		if(getID(index)==null){return null;}
		String ID = getID(index);
		for(Entity e : w.getEntities()){
			if(e instanceof ArmorStand){
				if(e!=null){
					if(e.getName() != null){
						if(e.getName().equals(ID)){
							return (ArmorStand) e;
						}
					}
				}
			}
		}
		return null;
	}

	public HeadArmType getType(Integer index) {
		if(getArmorStand(index, w) == null){return null;}
		ArmorStand as = getArmorStand(index, w);
		if(!as.hasArms()){
			return HeadArmType.HEAD;
		}else{
			return HeadArmType.ARM;
		}
	}
	public EulerAngle getEuler(Integer index, HeadArmType type) {
		if(getArmorStand(index, w) == null){return null;}
		ArmorStand as = getArmorStand(index, w);
		if(type.equals(HeadArmType.ARM)){return as.getRightArmPose();}
		return as.getHeadPose();
	}
	public ItemStack getItemStack(Integer index) {
		if(getArmorStand(index, w) == null){return null;}
		ArmorStand as = getArmorStand(index, w);
		if(as.getItemInHand()!=null){
			return as.getItemInHand();
		}else{
			return as.getHelmet();
		}
	}
	public void removeall() {
		for(String s : IDList){
			Integer i = getIndex(s);
			getArmorStand(i, w).remove();
		}
		IDList.clear();
	}
	
	public void setColorable(Integer j){
		if(getID(j)==null){return;}
		if(colorList.contains(getID(j))){return;}
		colorList.add(getID(j));
	}
	
	public void removeColorable(Integer j){
		if(getID(j)==null){return;}
		if(!colorList.contains(getID(j))){return;}
		colorList.remove(getID(j));
	}
	
	public void setSitable(Integer j){
		if(getID(j)==null){return;}
		if(sitable.contains(getID(j))){return;}
		sitable.add(getID(j));
	}
	
	public void setFireable(Integer j){
		if(getID(j)==null){return;}
		if(firelist.contains(getID(j))){return;}
		firelist.add(getID(j));
	}
	
	public void removeFireable(Integer j){
		if(getID(j)==null){return;}
		if(!firelist.contains(getID(j))){return;}
		firelist.remove(getID(j));
	}
	
	public void removeSitable(Integer j){
		if(getID(j)==null){return;}
		if(!sitable.contains(getID(j))){return;}
		sitable.remove(getID(j));
	}
	
	public Boolean getSitable(Integer j){
		if(getID(j)==null){return false;}
		if(sitable.contains(getID(j))){return true;}
		return false;
	}
	
	public Boolean getColorable(Integer j) {
		if(getID(j)==null){return false;}
		if(colorList.contains(getID(j))){return true;}
		return false;
	}
	
	
	public Boolean getFireable(Integer j) {
		if(getID(j)==null){return false;}
		if(firelist.contains(getID(j))){return true;}
		return false;
	}
}
