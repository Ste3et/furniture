package de.Ste3et_C0st.Furniture.Main.Manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.Type.FurnitureType;
import de.Ste3et_C0st.Furniture.Objects.electric.camera;
import de.Ste3et_C0st.Furniture.Objects.indoor.chair;
import de.Ste3et_C0st.Furniture.Objects.indoor.largeTable;
import de.Ste3et_C0st.Furniture.Objects.indoor.latern;
import de.Ste3et_C0st.Furniture.Objects.indoor.sofa;
import de.Ste3et_C0st.Furniture.Objects.indoor.table;
import de.Ste3et_C0st.Furniture.Objects.outdoor.barrels;
import de.Ste3et_C0st.Furniture.Objects.outdoor.campfire_1;
import de.Ste3et_C0st.Furniture.Objects.outdoor.campfire_2;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_1;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_2;
import de.Ste3et_C0st.Furniture.Objects.outdoor.tent_3;

public class FurnitureManager {
	public List<sofa> sofaList = new ArrayList<sofa>();
	public List<latern> lanternList = new ArrayList<latern>();
	public List<chair> chairList = new ArrayList<chair>();
	public List<table> tableList = new ArrayList<table>();
	public List<largeTable> largeTableList = new ArrayList<largeTable>();
	public List<tent_1> tent1List = new ArrayList<tent_1>();
	public List<tent_2> tent2List = new ArrayList<tent_2>();
	public List<tent_3> tent3List = new ArrayList<tent_3>();
	public List<barrels> barrelList = new ArrayList<barrels>();
	public List<campfire_1> campfire1List = new ArrayList<campfire_1>();
	public List<campfire_2> campfire2List = new ArrayList<campfire_2>();
	public List<camera> cameraList = new ArrayList<camera>();
	
	@SuppressWarnings("unchecked")
	public boolean RemoveType(FurnitureType f, boolean b){
		switch (f) {
		case CHAIR:
			if(chairList.isEmpty()){break;}
			List<chair> chairL = ((List<chair>) ((ArrayList<chair>) chairList).clone());
			for(chair s : chairL){s.delete(b, false);return true;}
		case LARGE_TABLE:
			if(largeTableList.isEmpty()){break;}
			List<largeTable> LLT = ((List<largeTable>) ((ArrayList<largeTable>) largeTableList).clone());
			for(largeTable s : LLT){s.delete(b,false);return true;}
		case LANTERN:
			if(lanternList.isEmpty()){break;}
			List<latern> latetnL = ((List<latern>) ((ArrayList<latern>) lanternList).clone());
			for(latern s : latetnL){s.delete(b,false);return true;}
		case SOFA:
			if(sofaList.isEmpty()){break;}
			List<sofa> sofaL = ((List<sofa>) ((ArrayList<sofa>) sofaList).clone());
			for(sofa s : sofaL){s.delete(b,false);return true;}
		case TABLE:
			if(tableList.isEmpty()){break;}
			List<table> tableL = ((List<table>) ((ArrayList<table>) tableList).clone());
			for(table s : tableL){s.delete(b,false);return true;}
		case BARRELS:
			if(barrelList.isEmpty()){break;}
			List<barrels> barrelL = ((List<barrels>) ((ArrayList<barrels>) barrelList).clone());
			for(barrels s : barrelL){s.delete(b,false);return true;}
		case CAMPFIRE_1:
			if(campfire1List.isEmpty()){break;}
			List<campfire_1> camp1L = ((List<campfire_1>) ((ArrayList<campfire_1>) campfire1List).clone());
			for(campfire_1 s : camp1L){s.delete(b,false);return true;}
		case CAMPFIRE_2:
			if(campfire2List.isEmpty()){break;}
			List<campfire_2> camp2L = ((List<campfire_2>) ((ArrayList<campfire_2>) campfire2List).clone());
			for(campfire_2 s : camp2L){s.delete(b,false);return true;}
		case TENT_1:
			if(tent1List.isEmpty()){break;}
			List<tent_1> tent1L = ((List<tent_1>) ((ArrayList<tent_1>) tent1List).clone());
			for(tent_1 s : tent1L){s.delete(b,false);return true;}
		case TENT_2:
			if(tent2List.isEmpty()){break;}
			List<tent_2> tent2L = ((List<tent_2>) ((ArrayList<tent_2>) tent2List).clone());
			for(tent_2 s : tent2L){s.delete(b,false);return true;}
		case TENT_3:
			if(tent3List.isEmpty()){break;}
			List<tent_3> tent3L = ((List<tent_3>) ((ArrayList<tent_3>) tent3List).clone());
			for(tent_3 s : tent3L){s.delete(b,false);return true;}
		case CAMERA:
			if(cameraList.isEmpty()){break;}
			List<camera> cameraL = ((List<camera>) ((ArrayList<camera>) cameraList).clone());
			for(camera s : cameraL){s.delete(b,false);return true;}
		default: return false;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean RemoveFromID(String ID){
		if(ID==null){return false;}
		if(!sofaList.isEmpty()){
			List<sofa> sofaL = ((List<sofa>) ((ArrayList<sofa>) sofaList).clone());
			for(sofa s : sofaL){if(s!=null){if(s.getID().equalsIgnoreCase(ID))s.delete(true,false);return true;}}}
		if(!lanternList.isEmpty()){
			List<latern> sofaL = ((List<latern>) ((ArrayList<latern>) lanternList).clone());
			for(latern s : sofaL){if(s!=null){if(s.getID().equalsIgnoreCase(ID))s.delete(true,false);return true;}}}
		if(!chairList.isEmpty()){
			List<chair> sofaL = ((List<chair>) ((ArrayList<chair>) chairList).clone());
			for(chair s : sofaL){if(s!=null){if(s.getID().equalsIgnoreCase(ID))s.delete(true,false);return true;}}}
		if(!tableList.isEmpty()){
			List<table> sofaL = ((List<table>) ((ArrayList<table>) tableList).clone());
			for(table s : sofaL){if(s!=null){if(s.getID().equalsIgnoreCase(ID))s.delete(true,false);return true;}}}
		if(!largeTableList.isEmpty()){
			List<largeTable> sofaL = ((List<largeTable>) ((ArrayList<largeTable>) largeTableList).clone());
			for(largeTable s : sofaL){if(s!=null){if(s.getID().equalsIgnoreCase(ID))s.delete(true,false);return true;}}}
		if(!tent1List.isEmpty()){
			List<tent_1> sofaL = ((List<tent_1>) ((ArrayList<tent_1>) tent1List).clone());
			for(tent_1 s : sofaL){if(s!=null){if(s.getID().equalsIgnoreCase(ID))s.delete(true,false);return true;}}}
		if(!tent2List.isEmpty()){
			List<tent_2> sofaL = ((List<tent_2>) ((ArrayList<tent_2>) tent2List).clone());
			for(tent_2 s : sofaL){if(s!=null){if(s.getID().equalsIgnoreCase(ID))s.delete(true,false);return true;}}}
		if(!tent3List.isEmpty()){
			List<tent_3> sofaL = ((List<tent_3>) ((ArrayList<tent_3>) tent3List).clone());
			for(tent_3 s : sofaL){if(s!=null){if(s.getID().equalsIgnoreCase(ID))s.delete(true,false);return true;}}}
		if(!barrelList.isEmpty()){
			List<barrels> sofaL = ((List<barrels>) ((ArrayList<barrels>) barrelList).clone());
			for(barrels s : sofaL){if(s!=null){if(s.getID().equalsIgnoreCase(ID))s.delete(true,false);return true;}}}
		if(!campfire1List.isEmpty()){
			List<campfire_1> sofaL = ((List<campfire_1>) ((ArrayList<campfire_1>) campfire1List).clone());
			for(campfire_1 s : sofaL){if(s!=null){if(s.getID().equalsIgnoreCase(ID))s.delete(true,false);return true;}}}
		if(!campfire2List.isEmpty()){
			List<campfire_2> sofaL = ((List<campfire_2>) ((ArrayList<campfire_2>) campfire2List).clone());
			for(campfire_2 s : sofaL){if(s!=null){if(s.getID().equalsIgnoreCase(ID))s.delete(true,false);return true;}}}
		if(!cameraList.isEmpty()){
			List<camera> cameraL = ((List<camera>) ((ArrayList<camera>) cameraList).clone());
			for(camera s : cameraL){if(s!=null){if(s.getID().equalsIgnoreCase(ID))s.delete(true,false);return true;}}}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean isAtLocation(Vector v, Double distance){
		if(v==null){return false;}
		if(distance==null){return false;}
		if(!sofaList.isEmpty()){
			List<sofa> sofaL = ((List<sofa>) ((ArrayList<sofa>) sofaList).clone());
			for(sofa s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)return true;}
			}
		}
		if(!lanternList.isEmpty()){
			List<latern> sofaL = ((List<latern>) ((ArrayList<latern>) lanternList).clone());
			for(latern s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)return true;}
			}
		}
		if(!chairList.isEmpty()){
			List<chair> sofaL = ((List<chair>) ((ArrayList<chair>) chairList).clone());
			for(chair s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)return true;}
			}
		}
		if(!tableList.isEmpty()){
			List<table> sofaL = ((List<table>) ((ArrayList<table>) tableList).clone());
			for(table s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)return true;}
			}
		}
		if(!largeTableList.isEmpty()){
			List<largeTable> sofaL = ((List<largeTable>) ((ArrayList<largeTable>) largeTableList).clone());
			for(largeTable s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)return true;}
			}
		}
		if(!tent1List.isEmpty()){
			List<tent_1> sofaL = ((List<tent_1>) ((ArrayList<tent_1>) tent1List).clone());
			for(tent_1 s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)return true;}
			}
		}
		if(!tent2List.isEmpty()){
			List<tent_2> sofaL = ((List<tent_2>) ((ArrayList<tent_2>) tent2List).clone());
			for(tent_2 s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)return true;}
			}
		}
		if(!tent3List.isEmpty()){
			List<tent_3> sofaL = ((List<tent_3>) ((ArrayList<tent_3>) tent3List).clone());
			for(tent_3 s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)return true;}
			}
		}
		if(!barrelList.isEmpty()){
			List<barrels> sofaL = barrelList.subList(0, barrelList.size());
			for(barrels s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)return true;}
			}
		}
		if(!campfire1List.isEmpty()){
			List<campfire_1> sofaL = ((List<campfire_1>) ((ArrayList<campfire_1>) campfire1List).clone());
			for(campfire_1 s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)return true;}
			}
		}
		if(!campfire2List.isEmpty()){
			List<campfire_2> sofaL = ((List<campfire_2>) ((ArrayList<campfire_2>) campfire2List).clone());
			for(campfire_2 s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)return true;}
			}
		}
		if(!cameraList.isEmpty()){
			List<camera> sofaL = ((List<camera>) ((ArrayList<camera>) cameraList).clone());
			for(camera s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)return true;}
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public Integer RemoveFromDistance(Vector v, Double distance){
		if(v==null){return null;}
		if(distance==null){return null;}
		Integer i=0;
		if(!sofaList.isEmpty()){
			List<sofa> sofaL = ((List<sofa>) ((ArrayList<sofa>) sofaList).clone());
			for(sofa s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)s.delete(true,false);i++;}
			}
		}
		if(!lanternList.isEmpty()){
			List<latern> sofaL = ((List<latern>) ((ArrayList<latern>) lanternList).clone());
			for(latern s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)s.delete(true, false);i++;}
			}
		}
		if(!chairList.isEmpty()){
			List<chair> sofaL = ((List<chair>) ((ArrayList<chair>) chairList).clone());
			for(chair s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)s.delete(true, false);i++;}
			}
		}
		if(!tableList.isEmpty()){
			List<table> sofaL = ((List<table>) ((ArrayList<table>) tableList).clone());
			for(table s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)s.delete(true, false);i++;}
			}
		}
		if(!largeTableList.isEmpty()){
			List<largeTable> sofaL = ((List<largeTable>) ((ArrayList<largeTable>) largeTableList).clone());
			for(largeTable s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)s.delete(true, false);i++;}
			}
		}
		if(!tent1List.isEmpty()){
			List<tent_1> sofaL = ((List<tent_1>) ((ArrayList<tent_1>) tent1List).clone());
			for(tent_1 s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)s.delete(true, false);i++;}
			}
		}
		if(!tent2List.isEmpty()){
			List<tent_2> sofaL = ((List<tent_2>) ((ArrayList<tent_2>) tent2List).clone());
			for(tent_2 s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)s.delete(true, false);i++;}
			}
		}
		if(!tent3List.isEmpty()){
			List<tent_3> sofaL = ((List<tent_3>) ((ArrayList<tent_3>) tent3List).clone());
			for(tent_3 s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)s.delete(true, false);i++;}
			}
		}
		if(!barrelList.isEmpty()){
			List<barrels> sofaL = barrelList.subList(0, barrelList.size());
			for(barrels s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)s.delete(true, false);i++;}
			}
		}
		if(!campfire1List.isEmpty()){
			List<campfire_1> sofaL = ((List<campfire_1>) ((ArrayList<campfire_1>) campfire1List).clone());
			for(campfire_1 s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)s.delete(true, false);i++;}
			}
		}
		if(!campfire2List.isEmpty()){
			List<campfire_2> sofaL = ((List<campfire_2>) ((ArrayList<campfire_2>) campfire2List).clone());
			for(campfire_2 s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)s.delete(true, false);i++;}
			}
		}
		if(!cameraList.isEmpty()){
			List<camera> sofaL = ((List<camera>) ((ArrayList<camera>) cameraList).clone());
			for(camera s : sofaL){
				if(s!=null){if(s.getLocation().toVector().distance(v)<=distance)s.delete(true, false);i++;}
			}
		}
		return i;
	}
}
