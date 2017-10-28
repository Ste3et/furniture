package de.Ste3et_C0st.Furniture.Objects.electric;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.map.MapView;

import de.Ste3et_C0st.Furniture.Camera.Utils.RenderClass;
import de.Ste3et_C0st.Furniture.Camera.Utils.RenderClass.ScaleMode;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.ShematicLoader.Events.ProjectClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureHelper;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.entity.Relative;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class camera extends FurnitureHelper implements Listener{
	
	private fEntity entity = null, entity2 = null;
	private String zoom = "#ZOOM0#";
	private ScaleMode mode = ScaleMode.NORMAL;
	
	public camera(ObjectID id){
		super(id);
		Bukkit.getPluginManager().registerEvents(this, main.instance);
		
		boolean b = false;
		for(fEntity stand : id.getPacketList()){
			if(stand.getCustomName().startsWith("#ZOOM")){
				if(stand.isCustomNameVisible()){
					stand.setNameVasibility(false);
					b = true;
				}
				this.entity2 = stand;
				zoom = stand.getCustomName();
			}
			if(stand.getItemInMainHand() != null){
				if(stand.getItemInMainHand().getType().equals(Material.TRIPWIRE_HOOK)){
					this.entity = stand;
				}
			}
			
		}
		
		if(b){
			update();
		}
	}

	public void setZoom(){
		if(entity == null){return;}
		Relative r = new Relative(getCenter(), -0.07, 0.0625, -0.13, getBlockFace().getOppositeFace());
		Location loc = r.getSecondLocation();
		if(zoom.equalsIgnoreCase("#ZOOM1#")){
			loc = r.getSecondLocation().subtract(0, .15, 0);
		}else if(zoom.equalsIgnoreCase("#ZOOM2#")){
			loc = r.getSecondLocation().subtract(0, .3, 0);
		}else if(zoom.equalsIgnoreCase("#ZOOM3#")){
			loc = r.getSecondLocation().subtract(0, .4, 0);
		}
		loc.setYaw(entity.getLocation().getYaw());
		this.entity.teleport(loc);
		update();
	}
	

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFurnitureClick(ProjectClickEvent e) {
		if(!e.getID().equals(getObjID())){return;}
		Player p = e.getPlayer();
		Location pLocation = getLutil().getRelativ(p.getLocation().getBlock().getLocation(), getBlockFace(), -1D, 0D).clone();
		Location locCopy = getLocation().getBlock().getLocation().clone();
		pLocation.setYaw(locCopy.getYaw());
		if(pLocation.equals(locCopy)){
			if(getLutil().yawToFace(p.getLocation().getYaw()).getOppositeFace().equals(getBlockFace())){
				if(e.canBuild()){
					if(!p.getInventory().getItemInMainHand().getType().equals(Material.MAP)){
						if(entity  == null || entity2 == null)return;
						if(this.zoom.equalsIgnoreCase("#ZOOM0#")){
							this.mode = ScaleMode.FAR;
							this.zoom = "#ZOOM1#";
						}else if(this.zoom.equalsIgnoreCase("#ZOOM1#")){
							this.mode = ScaleMode.FAHRTEST;
							this.zoom = "#ZOOM2#";
						}else if(this.zoom.equalsIgnoreCase("#ZOOM2#")){
							this.mode = ScaleMode.COMPLETE;
							this.zoom = "#ZOOM3#";
						}else if(this.zoom.equalsIgnoreCase("#ZOOM3#")){
							this.mode = ScaleMode.NORMAL;
							this.zoom = "#ZOOM0#";
						}
						this.entity2.setName(zoom);
						setZoom();
						update();
						return;
					}
				}else{
					if(!p.getInventory().getItemInMainHand().getType().equals(Material.MAP)){
						return;
					}
				}
				MapView view = Bukkit.getMap(p.getInventory().getItemInMainHand().getDurability());
				Location l = getLocation().clone();
				l.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
				view.getRenderers().clear();
				try{view.addRenderer(new RenderClass(l, mode));}catch (Exception ex){ex.printStackTrace();}
			}
		}
	}
}
