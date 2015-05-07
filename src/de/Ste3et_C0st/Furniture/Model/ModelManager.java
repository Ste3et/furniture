package de.Ste3et_C0st.Furniture.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.Type.HeadArmType;

public class ModelManager {
	
	private static ModelManager modelManager;
	private HashMap<Player, ModelCreator> modelCreator = new HashMap<Player, ModelCreator>();
	private List<Modele> modelListe = new ArrayList<Modele>();
	
	public ModelManager(){
		modelManager = this;
	}
	
	public static ModelManager getModelCreator(){
		return modelManager;
	}
	
	public void enterModelCreator(Player p, String ID){
		modelCreator.put(p, new ModelCreator(p, ID));
	}
	
	public void addModelListe(Modele m){
		if(m==null){return;}
		this.modelListe.add(m);
	}
	
	public void removeModelListe(Modele m){
		if(m==null){return;}
		if(modelListe.isEmpty()){return;}
		this.modelListe.remove(m);
	}
	
	public List<Modele> getModel(){
		return this.modelListe;
	}
	
	public Modele getModelByName(String name){
		if(name==null){return null;}
		if(modelListe.isEmpty()){return null;}
		for(Modele m : modelListe){
			if(m.getID().equals(name)){
				return m;
			}
		}
		return null;
	}
	
	public Integer getCopyIndex(String iD){
		int i = 1;
		if(!modelListe.isEmpty()){
			for(Modele m : modelListe){
				if(m.getID().startsWith(iD)){
					i++;
				}
			}
		}
		return i;
	}
	
	public Modele spawnCopy(String iD, Location loc){
		return new Modele(iD + "#" + getCopyIndex(iD), loc);
	}
	
	public void addArmorStand(Vector v, HeadArmType type, EulerAngle eular, ItemStack is, Boolean show, Boolean mini, Modele m, BlockFace b, BlockFace b2){
		m.addArmorStand(v, type, eular, is, show, mini, b, b2);
	}

	public boolean isInManager(Player p) {
		if(modelCreator.containsKey(p)){
			return true;
		}else{
			return false;
		}
	}
	
	public ModelCreator getModelCreator(Player p){
		return modelCreator.get(p);
	}

	public void removeall(String iD) {
		Modele m = getModelByName(iD);
		m.removeall();
		this.modelListe.remove(m);
	}
}
